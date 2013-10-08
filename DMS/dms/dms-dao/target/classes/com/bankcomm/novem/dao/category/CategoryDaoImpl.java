package com.bankcomm.novem.dao.category;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.category.*;
import com.bankcomm.novem.dao.BaseDao;
import com.bankcomm.novem.dao.category.ICategoryDao;

/**
 * 
 * @author Yanping
 * 
 * @date 2013-09-16
 * 
 * @desciption 分类Dao实现类
 *
 */


@Repository
public class CategoryDaoImpl extends BaseDao implements ICategoryDao{
	@Autowired
	private ICategoryMapper iCategoryDaoMapper;
	
	@Override
	public CategoryBo queryRoot(){
		return iCategoryDaoMapper.queryRoot();
	}

	@Override
	public List<CategoryBo> queryCategory(int categoryId) {
		return iCategoryDaoMapper.queryCategory(categoryId);
	}

	@Override
	public CategoryBo viewCategory(int categoryId) {
		return iCategoryDaoMapper.viewCategory(categoryId);
	}

	@Override
	public int isCategoryNameExist(CategoryNameBo categoryNameBo) {
		return iCategoryDaoMapper.isCategoryNameExist(categoryNameBo);
	}

	@Override
	public int insertCategory(CategoryBo categoryBo) {
		 return iCategoryDaoMapper.insertCategory(categoryBo);
		
	}

	@Override
	public void updateCategory(CategoryBo categoryBo) {
		iCategoryDaoMapper.updateCategory(categoryBo);
		
	}

	@Override
	public boolean updateCategoryState(CategoryStateBo categoryStateBo) {
		return iCategoryDaoMapper.updateCategoryState(categoryStateBo);
	}


	@Override
	public int isCategoryDeletable(int categoryId) {
		return iCategoryDaoMapper.isCategoryDeletable(categoryId);
	}

	@Override
	public void deleteCategory(int categoryId) {
		iCategoryDaoMapper.deleteCategory(categoryId);
	}

	@Override
	public List<CategoryBo> selectSubByPath(final String categoryPath){
		return iCategoryDaoMapper.selectSubByPath(categoryPath);
	}
	
	@Override
	public int queryInsertCategoryId(final CategoryInsertBo categoryInsertBo){
		return iCategoryDaoMapper.queryInsertCategoryId(categoryInsertBo);
	}
	
}