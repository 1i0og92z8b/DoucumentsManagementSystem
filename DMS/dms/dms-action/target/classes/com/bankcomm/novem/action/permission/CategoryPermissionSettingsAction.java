package com.bankcomm.novem.action.permission;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.Permission.ICategoryPermissionSettingsBiz;
import com.bankcomm.novem.bo.category.CategoryBo;
import com.bankcomm.novem.bo.category.CategoryStateBo;
import com.bocom.jump.bp.core.Context;

@Controller
public class CategoryPermissionSettingsAction extends BaseAction {

	@Autowired
	private ICategoryPermissionSettingsBiz CPSBizImpl;
	
	/**
	 * 查看一级分类权限
	 * @param context
	 *            上下文
	 */
	public void queryPermission(final Context context) {
		
//		final char fileState = (Character)ContextExtractor.extractValue(context, "fileState");  //必须先转换为包类型
//		final PageCond pageCond = ContextExtractor.extractPageCond(context);
//		System.out.println("OK");
		List<CategoryBo> categoryList = new ArrayList<CategoryBo>();
		categoryList.addAll(CPSBizImpl.queryPermission());
		
		for(CategoryBo c :categoryList){
			System.out.print(c.getCategoryId()+"	");
			System.out.print(c.getCategoryName()+"	");
			System.out.print(c.getCategoryState()+"	");
			
			System.out.println();
		}
		context.setData("categoryList", categoryList);
		
//		int pageCond = 1;
//		context.setData("PAGE_COND", pageCond);
		
	}
	
	public void updatePermission(final Context context) {
		
		final CategoryStateBo categoryStateBo = ContextExtractor.extractBean(context, "category", CategoryStateBo.class);
		final Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		categoryStateBo.setModifyUser(userId);
		categoryStateBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		
		System.out.print(categoryStateBo.getCategoryId()+"	");
		System.out.print(categoryStateBo.getCategoryState()+"	");
			
		System.out.println();

		CPSBizImpl.updatePermission(categoryStateBo);	
	}
}
