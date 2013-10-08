package com.bankcomm.novem.action.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.action.extractor.ContextExtractor;
import com.bankcomm.novem.biz.user.IUserManageBiz;
import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.comm.PageCond;
//import com.bankcomm.novem.comm.PageCond;
import com.bocom.jump.bp.core.Context;

/**
 * 用户管理action
 * @author jibojun
 *
 */
@Component
public class UserManageAction extends BaseAction{
	@Autowired
	IUserManageBiz iusermanagebiz;
	
	public void insertAction(final Context context)
	{
		UserBo userbo=ContextExtractor.extractBean(context, "userBo", UserBo.class);
		String userRole=ContextExtractor.extractValue(context, "userRole");
		Boolean a=iusermanagebiz.insertUser(userbo);
		UserQueryBo querybo=new UserQueryBo();
		querybo.setUserName(userbo.getUserName());
		List<UserBo> list=iusermanagebiz.queryUserList(querybo);
		Integer userId=list.get(0).getUserId();
		Boolean b=false;
		if(userRole!=""){
			b=iusermanagebiz.insertUserRole(userId, userRole);
		}
		if(a.equals(true)&&b.equals(true)){
			context.setData("RESULT", "插入用户信息成功");
		}
		else if(a.equals(false)){
			context.setData("RESULT","用户名不能重复");
		}
		else if(a.equals(true)&&b.equals(false)){
			context.setData("RESULT", "创建角色信息失败");
		}
	}
	
	public void deleteAction(final Context context)
	{
		List<String> username=ContextExtractor.extractValue(context, "userNameList");
		List<Integer> userid=ContextExtractor.extractValue(context, "userIdList");
		Boolean a=iusermanagebiz.deleteUser(username);
		Boolean b=iusermanagebiz.deleteUserRole(userid);
		if(a.equals(true)&&b.equals(true)){
			context.setData("RESULT", "删除用户信息成功");
		}
		else if(a.equals(true)&&b.equals(false)){
			context.setData("RESULT", "删除角色信息失败");
		}
		else context.setData("RESULT", "操作失败");
	}
	
	public void updateAction(final Context context)
	{
		UserBo userbo=ContextExtractor.extractBean(context, "userBo", UserBo.class);
		String userRole=ContextExtractor.extractValue(context, "userRole");
		Boolean a=iusermanagebiz.updateUser(userbo);
		UserQueryBo querybo=new UserQueryBo();
		querybo.setUserName(userbo.getUserName());
		List<UserBo> list=iusermanagebiz.queryUserList(querybo);
		Integer userId=list.get(0).getUserId();
		Boolean b=false;
		if(userRole!=null){
			b=iusermanagebiz.updateUserRole(userId, userRole);
		}		
		if(a.equals(true)&&b.equals(true)){
			context.setData("RESULT", "更新用户信息成功");
		}
		else if(a.equals(false)){
			context.setData("RESULT","此用户名已存在");
		}
		else if(a.equals(true)&&b.equals(false)){
			context.setData("RESULT", "更新角色信息失败");
		}
	}
	
	public void queryAction(final Context context)
	{
		Integer userId = (Integer)ContextExtractor.extractValue(context, "userId");
		final UserBo userBo= iusermanagebiz.queryUserInfo(userId);
		context.setData("userBo", userBo);
	}
	
	public void queryAllAction(final Context context)
	{
		UserQueryBo queryBo = ContextExtractor.extractBean(context, "PARAMS", UserQueryBo.class);
		final PageCond pageCond = ContextExtractor.extractPageCond(context);
		if(queryBo == null){
			queryBo = new UserQueryBo();
		}
		queryBo.setPageCond(pageCond);
		final List<UserBo> list = iusermanagebiz.queryUserList(queryBo);
		context.setData("list", list);
		context.setData("PAGE_COND", queryBo.getPageCond());
	}

}
