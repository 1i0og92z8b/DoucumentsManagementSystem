package com.bankcomm.novem.biz.category;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.bo.category.*;
import com.bankcomm.novem.bo.search.FileBo;
import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.file.FileUpdateCatBo;
import com.bankcomm.novem.dao.category.*;
import com.bankcomm.novem.biz.search.IQueryMethodSelectBiz;
import com.bankcomm.novem.biz.file.IFileManageBiz;
import com.bankcomm.novem.comm.PageCond;

/**
 * 
 * @author Lynda
 * @date 2013-09-20
 * @description 分类管理Biz实现类
 *
 */
@Service
public class CategoryBizImpl extends BaseBiz implements ICategoryBiz{
	@Autowired
	private ICategoryDao categoryDaoImpl;
	
	@Autowired
	private IQueryMethodSelectBiz queryMethodSelectBizImpl;
	
	@Autowired
	private IFileManageBiz fileManageBizImpl;
	
	@Override
	public CategoryBo queryRoot(){
		return categoryDaoImpl.queryRoot();
	}

	@Override
	public List<CategoryBo> queryCategory(final int categoryId){
		return categoryDaoImpl.queryCategory(categoryId);
	}
	
	@Override
	public List<CategoryBo> queryCategoryState(final int categoryId){
		List<CategoryBo> list=categoryDaoImpl.queryCategory(categoryId);
		List<CategoryBo> list2=new ArrayList<CategoryBo>();
		for(CategoryBo bo:list){
			if(bo.getCategoryState()==true){
				list2.add(bo);
			}
		}
		return list2;
	}
	
	
	
	@Override
	public CategoryBo viewCategory(final int categoryId){
		return categoryDaoImpl.viewCategory(categoryId);
	}
	
	Map<String,Object> map = new HashMap<String,Object>();
	
	@Override
	public Map<String,Object> insertCategory(final CategoryBo categoryBo){
		//判断当前分类下分类名是否存在重复
		int parentId= categoryBo.getParentId();
		if (parentId<0){
			map.put("flag", false);
			map.put("message", "当前父分类不存在，或已被删除！");
			return map;
		}
		
		long updator=categoryBo.getModifyUser();
		String categoryName=categoryBo.getCategoryName();
		
		List<CategoryBo> categoryList=new ArrayList<CategoryBo>();
		categoryList=categoryDaoImpl.queryCategory(parentId);

		for(int i=0;i<categoryList.size();i++){
			//获取所有子类
			CategoryBo category=categoryList.get(i);
			//读取每个子类的类名
			String name=category.getCategoryName();
			//验证子类是否存在重名
			if(name.equals(categoryName)){
				//存在重名，返回错误信息
				map.put("flag", false);
				map.put("message", "存在同名分类，请更换插入分类名称！");
				return map;
			}
		}
		
		categoryDaoImpl.insertCategory(categoryBo);	
		
		//查看新增分类的父类
		CategoryBo categoryParent=categoryDaoImpl.viewCategory(parentId);
			
		//获取父类的isLeaf状态
		Boolean leafState=categoryParent.getIsLeaf();
		//父类为叶子节点
		if (leafState==false){
			//修改父类为非叶子
			categoryParent.setIsLeaf(true);
			categoryParent.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			categoryParent.setModifyUser(updator);
			//更新父类
			categoryDaoImpl.updateCategory(categoryParent);
		}
		
/**此处需要获取新增分类在表中的ID，从而填充categoryPath的最后一位。考虑如何实现**/
		CategoryInsertBo categoryInsertBo=new CategoryInsertBo();
		categoryInsertBo.setCategoryName(categoryName);
		categoryInsertBo.setParentId(parentId);
		int insertId=categoryDaoImpl.queryInsertCategoryId(categoryInsertBo);
		
		CategoryBo parentCategory=new CategoryBo();
		parentCategory=categoryDaoImpl.viewCategory(parentId);
		String parentPath=parentCategory.getCategoryPath();

//		此处为获取新增节点路径的另一种方法
//		String[]array=categoryPath.split("\\.");
//		String path="";		
//		for(int i=0;i<array.length-1;i++){
//			array[i]=array[i]+'.';
//			path+=array[i];
//		}
		
		String newPath=parentPath+"."+Integer.toString(insertId);
		categoryBo.setCategoryPath(newPath);
		categoryBo.setCategoryId(insertId);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryBo.setModifyUser(updator);
		categoryDaoImpl.updateCategory(categoryBo);
		
		map.put("flag", true);
		map.put("message", "分类成功插入！");
		return map;
}
	
	@Override
	public Map<String,Object> updateCategory(final boolean change, final int chosenId, final CategoryBo categoryBo){

		/**以下注释在前台实现，功能包括：
		 * 1. 获取要修改的上级分类树
		 * 2. 单击“修改分类”，根据选定的分类树节点判断是否为当前要修改分类的父分类,chosenId=parentId
		 * 3. 若否，单击事件调用queryCategory()，返回单击分类的所有子节点；若是，返回null
		 * 4. 回2
		 * 5. 修改分类，至parentId为所选的分类ID，修改Path**/
		
		//获取选中分类对象
		CategoryBo categoryChosen=categoryDaoImpl.viewCategory(chosenId);
		
		//获取当前分类的ID
		int currentId=categoryBo.getCategoryId();
		
		//获取当前分类的父分类ID
		int parentId=categoryBo.getParentId();
		
		//获取选中父分类的Path
		String pathChosen=categoryChosen.getCategoryPath();
		
		//当前分类原路径
		String origPath=categoryBo.getCategoryPath();
		
		//获取当前分类的更新人
		long updator=categoryBo.getModifyUser();
		
		String path=origPath+".";
		
		if(pathChosen.startsWith(path)){
			map.put("flag", false);
			map.put("message", "选择的上级分类为当前分类的父分类以下分类，不得修改。请重新选择上级分类！");
			return map;
		}
		
		//以下change主要用来记录前台是否修改了当前分类名和上级分类。若未修改，则不需要进行重名校验。
		if(change){
			//获取当前分类名
			String categoryName=categoryBo.getCategoryName();	
			CategoryNameBo categoryNameBo=new CategoryNameBo();
			categoryNameBo.setCategoryName(categoryName);
			categoryNameBo.setCategoryId(chosenId);	
			
			int rep=categoryDaoImpl.isCategoryNameExist(categoryNameBo);
			if(rep!=0){
				map.put("flag", false);
				map.put("message", "该父分类下存在当前分类名，请修改分类名或上级分类！");
				return map;
			}
		}

//		修改选中分类的父类isLeaf状态
		Boolean leaf=categoryChosen.getIsLeaf();
		if(leaf==false){
			categoryChosen.setIsLeaf(true);
		}
		categoryChosen.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryChosen.setModifyUser(updator);
		categoryDaoImpl.updateCategory(categoryChosen);

		//设置当前分类新的路径
		String newPath=pathChosen+"."+Integer.toString(currentId);
		categoryBo.setParentId(chosenId);
		categoryBo.setCategoryPath(newPath);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

		categoryDaoImpl.updateCategory(categoryBo);
		
		//获取路径为原分类的子分类列表
		List<CategoryBo> subCategoryList=new ArrayList<CategoryBo>();
		subCategoryList=categoryDaoImpl.selectSubByPath(origPath);
		
		for(int i=0;i<subCategoryList.size();i++){
			CategoryBo subCategory=subCategoryList.get(i);
			//子类原路径
			String origCategoryPath=subCategory.getCategoryPath();
			//子类新路径，替换：当前分类原路径->新路径（包含当前节点）
			String newCategoryPath=origCategoryPath.replaceAll(origPath, newPath);
			
			subCategory.setCategoryPath(newCategoryPath);
			subCategory.setUpdateTime(new Timestamp(System.currentTimeMillis()));
	
			categoryDaoImpl.updateCategory(subCategory);
		}
		
		//若修改后的父分类没有子类，则设置isLeaf为false
		CategoryBo categoryParentBo=new CategoryBo();
		categoryParentBo=categoryDaoImpl.viewCategory(parentId);
		String parentPath=categoryParentBo.getCategoryPath();
		List<CategoryBo> category=categoryDaoImpl.selectSubByPath(parentPath);
		if(category.size()==1){
		categoryParentBo.setIsLeaf(false);
		categoryParentBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryParentBo.setModifyUser(updator);
		
		categoryDaoImpl.updateCategory(categoryParentBo);
	}

		map.put("flag", true);
		map.put("message", "成功插入！");
		return map;
	}
	
	public boolean updateCategoryState(final CategoryStateBo categoryStateBo){
		categoryDaoImpl.updateCategoryState(categoryStateBo);
		return true;
	}

	
	public Map<String,Object> deleteCategory(final int categoryId){
		
		/**以下需要调用文件管理接口，找出当前分类及子分类下的所有文件，修改文件路径**/
		
		int flag=1;//调用fileQueryByCategoryId()方法
		
		/**根据分类ID搜索文件列表的实现，
		 * 已找出所有包含当前path的所有分类（含子类）的文件列表，
		 * 故不再需要根据子类ID查询子类对应的文件列表。**/
		
		//找出当前分类下的所有文件列表
		FileFieldBo fileFieldBo=new FileFieldBo();
		fileFieldBo.setCategoryId(categoryId);
		fileFieldBo.setPageCond(new PageCond(0,100));
	
		List<FileBo> fileList=queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
		if(fileList == null){
			fileList = new ArrayList<FileBo>();
		}
		
		//将原分类的文件至于未归类下
		FileUpdateCatBo fileUpdateCatBo=new FileUpdateCatBo();
	
		int unCatId=2;//指定“未归类”的ID未2，表中已设定
		int userId = 0;//判断用户角色，分类下含有非本人创建分类，不得删除。

		for(int i=0;i<fileList.size();i++){
			FileBo fileBo=fileList.get(i);
			int fileId=fileBo.getFileId();	//找出每个文件对应的ID
			String filePath=fileBo.getFilePath();//文件路径对应的是文件所在的物理路径，filePath不需要修改

			fileUpdateCatBo.setFileId(fileId);
			fileUpdateCatBo.setCategoryId(unCatId);//将该分类下的所有文件的caegoryId设为未归类ID
			fileUpdateCatBo.setFilePath(filePath);
			fileUpdateCatBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			
			fileManageBizImpl.updateFileByCat(fileUpdateCatBo);
			
			userId=fileBo.getUserId();
		}
		
		//获取当前分类的isLeaf状态，isLeaf=1表示为非叶子节点
		CategoryBo categoryBo=new CategoryBo();
		categoryBo=categoryDaoImpl.viewCategory(categoryId);
		
		Boolean isLeaf=categoryBo.getIsLeaf();
		String origPath=categoryBo.getCategoryPath();
		int parentId=categoryBo.getParentId();
		long updator=categoryBo.getModifyUser();
		
		if(isLeaf){
			//根据分类路径，获取子分类列表
			List<CategoryBo> subCategoryList=new ArrayList<CategoryBo>();
			subCategoryList=categoryDaoImpl.selectSubByPath(origPath);
			for(int i=0;i<subCategoryList.size();i++){
				CategoryBo subCategory=subCategoryList.get(i);
				int delId=subCategory.getCategoryId();
				
				//调用搜索接口，查询子类下所有文件
				fileFieldBo.setCategoryId(delId);
				List<FileBo> fileListSub=queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
				if(fileListSub == null){
					fileListSub = new ArrayList<FileBo>();
				}
				
				//修改子类下所有文件的categoryId
				for(int m=0;m<fileListSub.size();m++){
					FileBo fileBo=fileListSub.get(m);
					int userIdSub=fileBo.getUserId();
					
					if(userIdSub!=userId){
						map.put("flag", false);
						map.put("message", "当前分类含有非本人创建分类，不得删除！请重新选择。");
						return map;
					}
			}
				//删除该子类
				categoryDaoImpl.deleteCategory(delId);
			}
		}
		
		//删除当前分类
		categoryDaoImpl.deleteCategory(categoryId);
		
		//若删除后的父分类没有子类，则设置isLeaf为false
		CategoryBo categoryParentBo=new CategoryBo();
		categoryParentBo=categoryDaoImpl.viewCategory(parentId);
		String parentPath=categoryParentBo.getCategoryPath();
		List<CategoryBo> category=categoryDaoImpl.selectSubByPath(parentPath);
		if(category.size()==1){
		categoryParentBo.setIsLeaf(false);
		categoryParentBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryParentBo.setModifyUser(updator);
		
		categoryDaoImpl.updateCategory(categoryParentBo);
		}

		map.put("flag", true);
		map.put("message", "分类成功删除!");
		return map;
	}
}