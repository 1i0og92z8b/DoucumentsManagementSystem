package com.bankcomm.novem.biz.Permission;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.category.ICategoryBiz;
import com.bankcomm.novem.bo.category.CategoryBo;
import com.bankcomm.novem.bo.category.CategoryStateBo;

@Service
public class CategoryPermissionSettingsBizImpl extends BaseBiz implements ICategoryPermissionSettingsBiz {

	@Autowired
	private ICategoryBiz categoryBizImpl;
	
	@Override
	public List<CategoryBo> queryPermission(){
		
		final int categoryId = 1; //查询父节点为1的一级分类
		
		return categoryBizImpl.queryCategory(categoryId);
	}
	
	@Override
	public void updatePermission(CategoryStateBo categoryStateBo){
		
		List<CategoryBo> categoryBoList = categoryBizImpl.queryCategory(categoryStateBo.getCategoryId());
		
		CategoryStateBo subCategoryStateBo = new CategoryStateBo();
		for(CategoryBo categoryBo:categoryBoList)  //修改子分类状态
		{
			subCategoryStateBo.setCategoryId(categoryBo.getCategoryId());
			subCategoryStateBo.setCategoryState(categoryStateBo.getCategoryState());
			subCategoryStateBo.setModifyUser(categoryStateBo.getModifyUser());
			subCategoryStateBo.setUpdateTime(categoryStateBo.getUpdateTime());
			
			categoryBizImpl.updateCategoryState(subCategoryStateBo);
		}
		categoryBizImpl.updateCategoryState(categoryStateBo);
	}
}
