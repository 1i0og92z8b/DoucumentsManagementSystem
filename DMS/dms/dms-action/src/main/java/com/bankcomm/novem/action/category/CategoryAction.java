package com.bankcomm.novem.action.category;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.category.ICategoryBiz;
import com.bankcomm.novem.bo.category.*;
import com.bocom.jump.bp.core.Context;

/**
 * 
 * @author Lynda
 * 
 * @date 2013-09-16
 * 
 * @description 分类管理Action
 *
 */

@Component
public class CategoryAction extends BaseAction{
//	private static final String REPLY_MSG="replyMsg";

	@Autowired
	private ICategoryBiz categoryBizImpl;
	private CategoryBo result;
	private List<CategoryBo> list;


//	private static final String REPLY_MSG = "replyMsg";
	

	/**
	 * 
	 * @description 查询分类	 方法描述
	 *
	 * @param context  参数
	 * 
	 * @return void  返回类型 
	 *
	 */
	
	public void queryRoot(final Context context){
		result=categoryBizImpl.queryRoot();
		context.setData("root", result);
	}
	
	public void queryCategory(final Context context){
		final int categoryId=ContextExtractor.extractValue(context,"categoryId");
		list=categoryBizImpl.queryCategory(categoryId);
		context.setData("list", list);
	}
	
	/**
	 * 
	 * @description 查看分类	 方法描述
	 *
	 * @param context  参数
	 * 
	 * @return void  返回类型 
	 *
	 */
	public void viewCategory(final Context context){
		final int categoryId=ContextExtractor.extractValue(context,"categoryId");
		result=categoryBizImpl.viewCategory(categoryId);
		context.setData("result", result);
	}
	
	/**
	 * 
	 * @description 新增分类	 方法描述
	 *
	 * @param context  参数
	 * 
	 * @return void  返回类型 
	 *
	 */
	
	public void insertCategory(final Context context){
		final CategoryBo categoryBo=ContextExtractor.extractBean(context, "categoryBo", CategoryBo.class);
		Map<String,Object> map=categoryBizImpl.insertCategory(categoryBo);
		context.setData("map",map);
	}
	
	/**
	 * 
	 * @description 修改分类	 方法描述
	 *
	 * @param context  参数
	 * 
	 * @return void  返回类型 
	 *
	 */
	public void updateCategory(final Context context){
		final int chosenId=ContextExtractor.extractValue(context,"chosenId");
		final CategoryBo categoryBo=ContextExtractor.extractBean(context, "categoryBo", CategoryBo.class);
		Map<String,Object> map=categoryBizImpl.updateCategory(chosenId, categoryBo);
		context.setData("map",map);
	}
	
	/**
	 * 
	 * @description 删除分类	 方法描述
	 *
	 * @param context  参数
	 * 
	 * @return void  返回类型 
	 *
	 */
	public void deleteCategory(final Context context){
		final int categoryId=ContextExtractor.extractValue(context,"categoryId");
		Map<String,Object> map=categoryBizImpl.deleteCategory(categoryId);
		context.setData("map",map);
	}
}
