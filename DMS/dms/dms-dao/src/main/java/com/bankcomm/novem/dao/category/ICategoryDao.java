package com.bankcomm.novem.dao.category;

import java.util.List;

//import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.bo.category.*;

/**
 * 
 * @author Yanping
 * 
 * @date 2013-09-16
 * 
 * @desciption 分类Dao接口
 *
 */
public interface ICategoryDao{
	/**
	 * 返回根，根的父类ID为0000
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
	 * 根据分类名称及分类ID，判断是否有重名分类
	 * @param categoryName
	 * @param categoryId
	 * @return Boolean
	 */
	int isCategoryNameExist(final CategoryNameBo categoryNameBo);
	/**
	 * 新增分类，插入一条分类记录
	 * @param categoryBo
	 * @return 主键
	 */
	int insertCategory(final CategoryBo categoryBo);
	/**
	 * 修改分类
	 * @param categoryBo
	 * @return 主键
	 */
	void updateCategory(final CategoryBo categoryBo);
	/**
	 * 修改分类状态
	 * @param categoryId
	 * @param categoryBo
	 * @return Boolean
	 */
	boolean updateCategoryState(final CategoryStateBo categoryStateBo);
	/**
	 * 判断分类是否可删除，为ROOT或“未归类”不可删除
	 * @param categoryId
	 * @return Boolean
	 */
	int isCategoryDeletable(final int categoryId);
	/**
	 * 删除分类
	 * @param categoryId
	 * @return Boolean
	 */
	void deleteCategory(final int categoryId);
	/**
	 * 根据分类分类路径选择子节点
	 * @param path
	 * @return List<CategoryBo>
	 */
	List<CategoryBo> selectSubByPath(final String categoryPath);
	/**
	 * 根据parentId和categoryName查询插入的分类ID
	 * @param categoryBo
	 * @return categoryId
	 */
	int queryInsertCategoryId(final CategoryInsertBo categoryInsertBo);
}
