package com.bankcomm.novem.dao.user.impl;

import java.sql.Timestamp;
import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;









import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.bo.user.UserRoleBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.entry.user.UserEntry;
import com.bankcomm.novem.dao.user.IUserManageDao;
import com.bankcomm.novem.dao.user.IUserManageDaoMapper;

/**
 * 
 * @author jibojun
 *
 */

@Repository
public class UserManageDaoImpl implements IUserManageDao {
	
	@Autowired
	private IUserManageDaoMapper usermanagedaomapper;

	@Override
	public Boolean insertUser(UserEntry userentry) {
		// TODO Auto-generated method stub
		userentry.setIfLogin(0);
		userentry.setUpdator(0);
		userentry.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userentry.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return usermanagedaomapper.insertUser(userentry);
	}

	@Override
	public Boolean deleteUser(final List<String> username){
		// TODO Auto-generated method stub
		return usermanagedaomapper.deleteUser(username);
	}

	@Override
	public UserEntry queryUserInfo(final Integer userId) {
		// TODO Auto-generated method stub
		
		return usermanagedaomapper.queryUserInfo(userId);
	}

	@Override
	public Boolean updateUser(UserEntry userentry) {
		// TODO Auto-generated method stub
		userentry.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return usermanagedaomapper.updateUser(userentry);
	}

	@Override
	public List<UserEntry> queryUserList(final UserQueryBo userquerybo) {
		// TODO Auto-generated method stub
		final PageCond pageCond = userquerybo.getPageCond();
		if (pageCond != null) {
			final int count = usermanagedaomapper.queryCount(userquerybo);
			pageCond.setCOUNT(count);
			userquerybo.setPageCond(pageCond);
		}
		return usermanagedaomapper.queryUserList(userquerybo);
	}
	
	@Override
	public Boolean insertUserRole(final UserRoleBo userrolebo){
		// TODO Auto-generated method stub
		return usermanagedaomapper.insertUserRole(userrolebo);
	}

	@Override
	public Boolean updateUserRole(final UserRoleBo userrolebo){
		// TODO Auto-generated method stub
		return usermanagedaomapper.updateUserRole(userrolebo);
	}
	
	@Override
	public Boolean deleteUserRole(final List<Integer> userid){
		// TODO Auto-generated method stub
		return usermanagedaomapper.deleteUserRole(userid);
	}
}
