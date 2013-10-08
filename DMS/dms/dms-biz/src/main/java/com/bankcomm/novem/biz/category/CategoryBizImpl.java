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

		
		String categoryName=categoryBo.getCategoryName();
//		String categoryPath=categoryBo.getCategoryPath();
		
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
		categoryBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		categoryDaoImpl.updateCategory(categoryBo);
		
		map.put("flag", true);
		map.put("message", "分类成功插入！");
		return map;
}
	
	@Override
	public Map<String,Object> updateCategory(final int chosenId, final CategoryBo categoryBo){

		/**以下注释在前台实现，功能包括：
		 * 1. 获取要修改的上级分类树
		 * 2. 单击“修改分类”，根据选定的分类树节点判断是否为当前要修改分类的父分类,chosenId=parentId
		 * 3. 若否，单击事件调用queryCategory()，返回单击分类的所有子节点；若是，返回null
		 * 4. 回2
		 * 5. 修改分类，至parentId为所选的分类ID，修改Path**/
		
		//获取当前分类的父类ID
		int parentId=categoryBo.getParentId();
		if(chosenId==parentId){
			map.put("flag", false);
			map.put("message", "选中分类为当前分类的父分类，请修改分类名或选择其他上级分类！");
			return map;
//			return "选中分类为当前分类的父分类！";//考虑此处怎么实现，若是选中分类是当前分类的父类，点击树是不会返回子树的。
		}
		//获取选中分类对象
		CategoryBo categoryChosen=categoryDaoImpl.viewCategory(chosenId);
		int chosenParentId=categoryChosen.getParentId();
		  if(chosenParentId==parentId){
			  map.put("flag", false);
				map.put("message", "不可选择同级或以下分类，请重新选择上级分类！");
				return map; 
		 }
		
		//当前分类原路径
		String origPath=categoryBo.getCategoryPath();
		
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
		
		
		
		Boolean leaf=categoryChosen.getIsLeaf();
		if(leaf==false){
			categoryChosen.setIsLeaf(true);
		}
		categoryChosen.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryDaoImpl.updateCategory(categoryChosen);

		//获取当前分类的ID
		int currentId=categoryBo.getCategoryId();
		
		//获取选中父分类的Path
		String pathChosen=categoryChosen.getCategoryPath();
		
		//设置当前分类新的路径
		String newPath=pathChosen+"."+Integer.toString(currentId);
		categoryBo.setParentId(chosenId);
		categoryBo.setCategoryPath(newPath);
//		categoryBo.setIsLeaf(true);
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
		map.put("flag", true);
		map.put("message", "成功插入！");
		return map;
	}
	
	public boolean updateCategoryState(final CategoryStateBo categoryStateBo){
		categoryDaoImpl.updateCategoryState(categoryStateBo);
		return true;
	}
	
	public Map<String,Object> deleteCategory(final int categoryId){
		//此处需要判断用户角色，若是管理员则不调用此方法
//		int parentId=categoryDaoImpl.isCategoryDeletable(categoryId);
//		if(parentId==0){
//			map.put("flag", false);
//			map.put("message", "当前分类为ROOT，不得删除！");
//			return map;
//		}
//		if(parentId==1&&categoryId==2){
//			map.put("flag", false);
//			map.put("message", "当前分类为“未分类”，不得删除！");
//			return map;
//		}
		
		/**以下需要调用文件管理接口，找出当前分类及子分类下的所有文件，修改文件路径**/
		int flag=1;//调用fileQueryByCategoryId()方法
		
		//找出当前分类下的所有文件列表
		FileFieldBo fileFieldBo=new FileFieldBo();
		fileFieldBo.setCategoryId(categoryId);
		System.out.println("categoryId"+categoryId);
		fileFieldBo.setPageCond(new PageCond(0,100));
		List<FileBo> fileList=queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
		if(fileList == null){
			fileList = new ArrayList<FileBo>();
		}
//		System.out.println("categoryId"+categoryId);
		//将原分类的文件至于未归类下，需要注意：文件路径对应的是文件所在的物理路径，filePath不需要修改
		FileUpdateCatBo fileUpdateCatBo=new FileUpdateCatBo();
	
		int unCatId=2;//指定“未归类”的ID未2，表中已设定

		for(int i=0;i<fileList.size();i++){
			FileBo fileBo=fileList.get(i);
			int fileId=fileBo.getFileId();	//找出每个文件对应的ID
			String filePath=fileBo.getFilePath();

			fileUpdateCatBo.setFileId(fileId);
			fileUpdateCatBo.setCategoryId(unCatId);//将该分类下的所有文件的caegoryId设为未归类ID
			fileUpdateCatBo.setFilePath(filePath);
			fileUpdateCatBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			
			fileManageBizImpl.updateFileByCat(fileUpdateCatBo);
		}
		
		//获取当前分类的isLeaf状态，isLeaf=1表示为非叶子节点
		CategoryBo categoryBo=new CategoryBo();
		categoryBo=categoryDaoImpl.viewCategory(categoryId);
		
		Boolean isLeaf=categoryBo.getIsLeaf();
		String origPath=categoryBo.getCategoryPath();
		
		if(isLeaf){
			List<CategoryBo> subCategoryList=new ArrayList<CategoryBo>();
			subCategoryList=categoryDaoImpl.selectSubByPath(origPath);
			for(int i=0;i<subCategoryList.size();i++){
				CategoryBo subCategory=subCategoryList.get(i);
				int delId=subCategory.getCategoryId();
				//调用搜索接口，查询子类下所有文件
				fileFieldBo.setCategoryId(delId);
				List<FileBo> fileListSub=queryMethodSelectBizImpl.QueryMethodSelectByFlag(fileFieldBo, flag);
				
				//修改子类下所有文件的categoryId
				for(int m=0;m<fileListSub.size();m++){
					FileBo fileBo=fileListSub.get(m);
					int fileIdSub=fileBo.getFileId();
					String filePath=fileBo.getFilePath();
											
					//调用文件管理接口，更新文件的分类ID状态
					fileUpdateCatBo.setFileId(fileIdSub);
					fileUpdateCatBo.setCategoryId(unCatId);
					fileUpdateCatBo.setFilePath(filePath);
					fileUpdateCatBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));

					fileManageBizImpl.updateFileByCat(fileUpdateCatBo);
				}
				//删除该子类
				categoryDaoImpl.deleteCategory(delId);
			}
		}
		
		//删除当前分类
		categoryDaoImpl.deleteCategory(categoryId);

		map.put("flag", true);
		map.put("message", "分类成功删除!");
		return map;
	}

}