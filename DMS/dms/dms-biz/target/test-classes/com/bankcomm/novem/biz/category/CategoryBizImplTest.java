package com.bankcomm.novem.biz.category;

//import java.util.ArrayList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.bo.category.*;

/**
 * 
 * @author Yanping
 * 
 * @date 2013-09-22
 * 
 * @description 分类管理Biz实现
 *
 */
public class CategoryBizImplTest extends BaseBizTest<CategoryBizImpl>{
	Logger LOG = Logger.getLogger(CategoryBizImplTest.class);
	
//	@Autowired
//	private ICategoryDao categoryDaoImpl;
	
	@Autowired
	private ICategoryBiz categoryBizImpl;
	
	@Test
	public void queryRoot(){
		CategoryBo categoryBo=new CategoryBo();
		categoryBo=categoryBizImpl.queryRoot();
		
		System.out.println(categoryBo);
		System.out.println("Success");
	}
	
	@Test
	public void testQueryCategory(){
		int category = 1;
		List<CategoryBo> categoryList=new ArrayList<CategoryBo>();
		categoryList=categoryBizImpl.queryCategory(category);
		
		Assert.assertNotNull(categoryList);
		
		for(final CategoryBo bo:categoryList)
			LOG.debug(bo);
		
		System.out.println("Success");
	}
	
	@Test
	@Rollback(false)
	public void testInsertCategory(){
		CategoryBo categoryBo=new CategoryBo();
		categoryBo.setCategoryName("testInsertBiz");
		categoryBo.setParentId(3);
		categoryBo.setCategoryPath("0.3.X");//分类路径实为需要自动获取，需要先得到插入分类的ID
		categoryBo.setIsLeaf(false);
		categoryBo.setCategoryDesc("testInsertBiz");
		categoryBo.setCategoryState(true);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryBo.setModifyUser(4);//更新人也需要从用户Session中自动获取
		
		Map<String,Object> map=categoryBizImpl.insertCategory(categoryBo);
		System.out.println(map);
		System.out.println("Success");
		
	}
	
	@Test
	public void testUpdateQuery(){
		int chosenId=3;
		
		CategoryBo categoryBo=new CategoryBo();
		categoryBo.setCategoryId(148);
		categoryBo.setParentId(3);
		categoryBo.setCategoryName("testUpdateBiz");
		categoryBo.setCategoryPath("0.1.3.8");
		categoryBo.setIsLeaf(true);
		categoryBo.setCategoryDesc("testUpdateBiz");
		categoryBo.setCategoryState(true);
		categoryBo.setModifyUser(8);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryBo.setModifyUser(4);//更新人也需要从用户Session中自动获取
		
		Map<String,Object> map=categoryBizImpl.updateCategory(chosenId, categoryBo);
		LOG.info(categoryBo);
		LOG.info(map);
		System.out.println("Success");		
	}
	
	
	@Test
	@Rollback(false)
	public void testDeleteCategory(){
		int categoryId=76;//可以调通
		categoryBizImpl.deleteCategory(categoryId);
		System.out.println("Success");
	}
	
	@Test
//	@Rollback(false)
	public void testUpdateCategoryState(){
		int categoryId=103;

		Boolean categoryState=false;
		CategoryStateBo categoryStateBo=new CategoryStateBo();
		categoryStateBo.setCategoryId(categoryId);
		categoryStateBo.setCategoryState(categoryState);
		categoryStateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		categoryStateBo.setModifyUser(4);//更新人也需要从用户Session中自动获取
		
		categoryBizImpl.updateCategoryState(categoryStateBo);
		
		System.out.println("Success");	
		
	}
}
