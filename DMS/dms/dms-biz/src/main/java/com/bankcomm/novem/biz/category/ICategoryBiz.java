package com.bankcomm.novem.biz.category;

import java.util.List;
import java.util.Map;

import com.bankcomm.novem.bo.category.*;


/**
 * 
 * @author Lynda
 * 
 * @date 2013-09-19
 * 
 * @description 分类管理Biz接口
 *
 */
public interface ICategoryBiz {
	/**
	 * 返回根，根的父类ID为0
	 * @return CategoryBo
	 */
	CategoryBo queryRoot();
	/**
	 * 根据父类ID，返回类的所有子类
	 * @param categoryId
	 * @return List<CategoryBo>
	 */
	List<CategoryBo> queryCategory(final int categoryId);
	/**
	 * 根据分类ID返回该类的明细信息
	 * @param categoryId
	 * @return CategoryBo
	 */
	CategoryBo viewCategory(final int categoryId);
	/**
	 * 新增分类，插入一条分类记录
	 * @param categoryBo
	 * @return 主键
	 */
	Map<String,Object> insertCategory(final CategoryBo categoryBo);
	/**
	 * 修改分类
	 * @param categoryBo
	 * @return 
	 */
	Map<String,Object> updateCategory(final int chosenId, final CategoryBo categoryBo);
	/**
	 * 修改分类状态
	 * @param categoryStateBo
	 */
	boolean updateCategoryState(final CategoryStateBo categoryStateBo);
	/**
	 * 删除分类
	 * @param categoryId
	 */
	Map<String,Object> deleteCategory(final int categoryId);
}

