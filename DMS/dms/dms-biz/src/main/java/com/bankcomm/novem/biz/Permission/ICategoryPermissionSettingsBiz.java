package com.bankcomm.novem.biz.Permission;

import java.util.List;

import com.bankcomm.novem.bo.category.CategoryBo;
import com.bankcomm.novem.bo.category.CategoryStateBo;

public interface ICategoryPermissionSettingsBiz {

	/**
	 * 查看一级分类权限
	 * @param
	 * @return 结果集
	 */
	List<CategoryBo> queryPermission();
	
	/**
	 * 更改一级分类权限
	 * @param categoryID
	 * @return 成功与否
	 */
	void updatePermission(CategoryStateBo categoryStateBo);
}
