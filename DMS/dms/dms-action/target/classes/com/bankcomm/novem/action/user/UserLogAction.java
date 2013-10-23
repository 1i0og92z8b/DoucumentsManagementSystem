package com.bankcomm.novem.action.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bankcomm.novem.action.BaseAction;
import com.bankcomm.novem.biz.user.IUserLogBiz;
import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
import com.bocom.jump.bp.core.Context;

/**
 * 用户登录action
 * @author jibojun
 *
 */
@Component
public class UserLogAction extends BaseAction{
	@Autowired
	IUserLogBiz iuserlogbiz;
	
	public void loginAction(final Context context)
	{
		String username=context.getData("userName");
		String password=context.getData("password");
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName(username);
		userlogbo.setPassWord(password);
		UserLoginReturnInfoBo i=iuserlogbiz.checkUsername(userlogbo);
		if(i==null){
			context.setData("RESULT", "用户名不存在");
		}
		else{
			if(i.getLogState()==2)
			{
				context.setData("userInfo", i);
				context.setData("RESULT", "登陆成功");
			}
			else if(i.getLogState()==1)
			{
				context.setData("userinfo", i);
				context.setData("RESULT", "密码错误");
			}
		}
	}
	
	public void logoutAction(final Context context)
	{
		String username=context.getData("userName");
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName(username);
		iuserlogbiz.logout(userlogbo);
		context.setData("RESULT", "登出成功");
	}
}
