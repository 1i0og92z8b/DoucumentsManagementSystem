package com.bankcomm.novem.dao.category;

//import java.util.*;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
//import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.category.*;
import com.bankcomm.novem.dao.BaseDaoTest;

public class CategoryDaoImplTest extends BaseDaoTest<CategoryBo>{
	
	@Autowired
	ICategoryDao categoryDaoImpl;
	
	@Test
	@Rollback(false)
	public void testInsertCategory(){	
		CategoryBo categoryBo=new CategoryBo();
		
		categoryBo.setCategoryName("testInsert");
		categoryBo.setParentId(1);
		categoryBo.setIsLeaf(true);
		categoryBo.setCategoryPath("0.1.4");
		categoryBo.setCategoryDesc("testInsert");
		categoryBo.setCategoryState(true);
		categoryBo.setModifyUser(4);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		int i = categoryDaoImpl.insertCategory(categoryBo);
		
		Assert.assertTrue(i!=0);
		
		System.out.println(i);
		System.out.println("Success");
	}
	


	@Test
	public void testIsCategoryNameExist(){  //未调通
		CategoryNameBo categoryNameBo=new CategoryNameBo();
		categoryNameBo.setCategoryId(1);
		categoryNameBo.setCategoryName("first");
		
		int result=categoryDaoImpl.isCategoryNameExist(categoryNameBo);
		System.out.println(result);
		System.out.println("Success");
	}

	
	@Test
	@Rollback(false)
	public void testUpdateCategoryState(){
		CategoryStateBo categoryStateBo=new CategoryStateBo();
		categoryStateBo.setCategoryId(5);
		categoryStateBo.setCategoryState(false);
		categoryStateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		categoryDaoImpl.updateCategoryState(categoryStateBo);
		
		System.out.println("Success");
	}
	
	
	@Test
	public void testIsCategoryDeletable(){
		int catId=2;
		int parId=categoryDaoImpl.isCategoryDeletable(catId);
		
		System.out.println(parId);
		System.out.println("Success");
	}
	
	@Test
	public void testDeleteCategory(){
		int categoryId=4;
		categoryDaoImpl.deleteCategory(categoryId);
		System.out.println("Success");
		
	}
	
	@Test
	public void testUpdateCategory(){
		CategoryBo categoryBo=new CategoryBo();
		
		categoryBo.setCategoryId(5);
		categoryBo.setCategoryName("testUpdate");
		categoryBo.setParentId(1);
		categoryBo.setIsLeaf(true);
		categoryBo.setCategoryPath("0.1.5");
		categoryBo.setCategoryDesc("testUpdate");
		categoryBo.setCategoryState(true);
		categoryBo.setModifyUser(40);
		categoryBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		categoryDaoImpl.updateCategory(categoryBo);
		
		System.out.println("Success");
	}
	

	
	@Test
	public void testQueryCategory(){
		List<CategoryBo> categoryBoList=new ArrayList<CategoryBo>();
		int category = 1;
//		categoryBoList.addAll(categoryDaoImpl.queryCategory(category));
//		System.out.println(categoryBoList.size());
		categoryBoList=categoryDaoImpl.queryCategory(category);
		System.out.println(categoryBoList);
		
		System.out.println("Success");
	}
	
	@Test
	public void testQueryRoot(){
		CategoryBo categoryBo=new CategoryBo();
		categoryBo=categoryDaoImpl.queryRoot();
		System.out.println(categoryBo);
		System.out.println("Success");
	}
	
	@Test
	public void testViewCategory(){
		CategoryBo categoryBo=new CategoryBo();
		categoryBo=categoryDaoImpl.viewCategory(2);
		
		System.out.println(categoryBo);
		System.out.println("Success");
	}
	
	@Test
	public void testSelectSubByPath(){
		String categoryPath="0.1.5%";
		List<CategoryBo> categoryList=new ArrayList<CategoryBo>();
		categoryList=categoryDaoImpl.selectSubByPath(categoryPath);
		System.out.println(categoryList);
	}
	
	@Test
	public void testQueryInsertCategoryId(){
		CategoryInsertBo categoryInsertBo=new CategoryInsertBo();
		categoryInsertBo.setCategoryName("未归类");
		categoryInsertBo.setParentId(1);
		
		int id=categoryDaoImpl.queryInsertCategoryId(categoryInsertBo);
		System.out.println(id);
		System.out.println("Success");
	}

}
