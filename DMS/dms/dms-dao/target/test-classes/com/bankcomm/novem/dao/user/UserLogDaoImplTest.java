package com.bankcomm.novem.dao.user;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.dao.BaseDaoTest;

public class UserLogDaoImplTest extends BaseDaoTest {
	@Resource
	private IUserLogDao iuserlogdao;
	private IUserManageDao iusermanagedao;
	
	@Test
	public void testCheckUsername() {
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName("ji");
		UserLoginReturnInfoBo userloginreturninfobo=iuserlogdao.checkUsername(userlogbo);
		Assert.assertEquals(168, (long)userloginreturninfobo.getUserId());
		Assert.assertEquals("ji",userloginreturninfobo.getPassWord() );
		//Assert.assertEquals(111, userloginreturninfobo.getPassWord());
	}

	@Test
	public void testCheckPassword() {
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName("ji");
		userlogbo.setPassWord("ji");
		UserLoginReturnInfoBo userloginreturninfobo=iuserlogdao.checkPassword(userlogbo);
		Assert.assertEquals(168,(long)userloginreturninfobo.getUserId());
	}	
	
	@Test
	public void testGetUserRole(){
		UserLoginReturnInfoBo userloginreturninfobo=new UserLoginReturnInfoBo();
		userloginreturninfobo.setUserName("ji");
		userloginreturninfobo.setPassWord("ji");
		userloginreturninfobo.setUserRole(iuserlogdao.getUserRole(userloginreturninfobo));
		System.out.println(userloginreturninfobo.getUserRole());
		Assert.assertEquals("admin", userloginreturninfobo.getUserRole());
	}
	
	@Test
	//@Rollback(false)
	public void testSetLog(){
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName("ji");
		//userlogbo.setPassword("111");
		Integer a=iuserlogdao.setLog(userlogbo);
		Assert.assertEquals(1, (long)a);
	}

	@Test
	@Rollback(false)
	public void testLogout() {
		UserLogBo userlogbo=new UserLogBo();
		userlogbo.setUserName("ji");
		//userlogbo.setPassword("111");
		Integer a=iuserlogdao.logout(userlogbo);
		Assert.assertEquals(1, (long)a);
	}

}
