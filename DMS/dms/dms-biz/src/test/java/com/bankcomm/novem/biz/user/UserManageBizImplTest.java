package com.bankcomm.novem.biz.user;




import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.biz.BaseBizTest;
import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserQueryBo;

public class UserManageBizImplTest extends BaseBizTest{
	@Resource
	private IUserManageBiz iusermanagebiz;
	
	@Test
	//@Rollback(false)
	public void insertUser(){
		final UserBo userbo=new UserBo();
		userbo.setUserName("tt2");
		userbo.setPassWord("test");
		userbo.setFullName("test");
		userbo.setSex("0");
		userbo.setEmail("555");
		userbo.setExtNo("6666");
		userbo.setIfLogin(0);
		iusermanagebiz.insertUser(userbo);
	}
	
	@Test
	@Rollback(false)
	public void updateUser(){
		final UserBo userbo=new UserBo();
		userbo.setUserName("test");
		userbo.setPassWord("test1");
		userbo.setFullName("test1");
		userbo.setSex("1");
		userbo.setEmail("666");
		userbo.setExtNo("555");
		userbo.setIfLogin(0);
		String p="test";
		iusermanagebiz.updateUser(userbo,p);
	}
	@Test
	@Rollback(false)
	public void queryUserInfo(){
		final UserQueryBo userquerybo=new UserQueryBo();
		//userquerybo.setUserName("test");
		userquerybo.setEmail("666");
		//userquerybo.setExtNo("555");
		List<UserBo> list=iusermanagebiz.queryUserList(userquerybo);
		Assert.assertEquals(list.size(),4);
		System.out.println(list.get(0).getUserId());
		System.out.println(list.get(0).getUserName());
	}
	
	@Test
	@Rollback(false)
	public void insertUserRole(){
		Integer userId=5;
		String userRole="admin";
		System.out.println(iusermanagebiz.insertUserRole(userId, userRole));
	}
	
	@Test
	@Rollback(false)
	public void updateUserRole(){
		Integer userId=2;
		String userRole="admin";
		System.out.println(iusermanagebiz.updateUserRole(userId, userRole));
	}
}
