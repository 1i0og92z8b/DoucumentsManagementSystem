package com.bankcomm.novem.dao.user;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.annotation.Rollback;

import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.dao.BaseDaoTest;
import com.bankcomm.novem.entry.user.UserEntry;

public class UserManageDaoImplTest extends BaseDaoTest {
	@Resource
	private IUserManageDao iusermanagedao;
	
	@Test
	//@Rollback(false)
	public void insertUser() {
		final UserEntry userentry=new UserEntry();
		userentry.setUserName("a901");
		userentry.setPassWord("444");
		userentry.setFullName("");
		userentry.setSex("0");
		userentry.setEmail("555");
		userentry.setExtNo("6666");
		userentry.setIfLogin(0);
		userentry.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userentry.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		userentry.setUpdator(0);
		System.out.println(iusermanagedao.insertUser(userentry));
	}

	@Test
	@Rollback(false)
	public void deleteUser() {
		List<String> username=new ArrayList();
		username.add("a101");
		username.add("a111");
		iusermanagedao.deleteUser(username);
	}

	@Test
	public void queryUserInfo() {
		
	}

	@Test
	//@Rollback(false)
	public void updateUser() {
		final UserEntry userentry=new UserEntry();
		userentry.setUserId(2);
		userentry.setUserName("ttt");
		userentry.setPassWord("333333");
		userentry.setSex("0");
		userentry.setEmail("666");
		userentry.setExtNo("XXX");
		userentry.setIfLogin(0);
		iusermanagedao.updateUser(userentry);
	}

	@Test
	public void queryUserList() {
		final UserQueryBo userquerybo=new UserQueryBo();
		userquerybo.setUserName("a2");
		userquerybo.setEmail("321");
		userquerybo.setExtNo("4545");
		List<UserEntry> list=iusermanagedao.queryUserList(userquerybo);
		Assert.assertEquals(1,list.size());
		System.out.println(list);
	}

}
