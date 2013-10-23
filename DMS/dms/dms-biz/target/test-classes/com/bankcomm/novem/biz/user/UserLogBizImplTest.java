package com.bankcomm.novem.biz.user;

import javax.annotation.Resource;

import junit.framework.Assert;

import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;

public class UserLogBizImplTest extends BaseBizTest {
	@Resource
	private IUserLogBiz iuserlogbiz;
	
	
	
	@Test
	@Rollback(false)
	public void checkLogout(){
		UserLogBo u=new UserLogBo();
		u.setUserName("ji");
		iuserlogbiz.logout(u);
	}
	
	@SuppressWarnings("deprecation")
	@Test
	public void testCheckUserName(){
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName("ji");
		userlogbo.setPassWord("ji");
		UserLoginReturnInfoBo i=iuserlogbiz.checkUsername(userlogbo);
		System.out.println(i.getUserId());
		System.out.println(i.getLogState());
		System.out.println(i.getUserRole());
	}

}
