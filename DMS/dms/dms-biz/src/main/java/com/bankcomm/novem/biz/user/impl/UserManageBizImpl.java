package com.bankcomm.novem.biz.user.impl;

import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.python.modules.newmodule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.user.IUserManageBiz;
import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.bo.user.UserRoleBo;
import com.bankcomm.novem.dao.user.IUserManageDao;
import com.bankcomm.novem.entry.demo.DemoEntry;
import com.bankcomm.novem.entry.user.UserEntry;
import com.bocom.jump.component.euif.exception.BusinessException;

import org.apache.commons.beanutils.BeanUtils;  

/**
 * 
 * @author jibojun
 *
 */

@Service
public class UserManageBizImpl extends BaseBiz implements IUserManageBiz {
	@Autowired
	private IUserManageDao iusermanagedao;

	@Override
	public Boolean insertUser(UserBo userbo) {
		// TODO Auto-generated method stub
		final UserEntry userentry = super.map(userbo, UserEntry.class);
		UserQueryBo userquerybo=new UserQueryBo();
		userquerybo.setUserName(userbo.getUserName());
		List<UserEntry> list=iusermanagedao.queryUserList(userquerybo);
		if(list.size()==0){
			return iusermanagedao.insertUser(userentry);
		}else{
			return false;
		}
	}

	@Override
	public Boolean deleteUser(List<String> username) {
		// TODO Auto-generated method stub
		return iusermanagedao.deleteUser(username);
	}

	@Override
	public Boolean updateUser(UserBo userbo) {
		// TODO Auto-generated method stub
		final UserEntry userentry = super.map(userbo, UserEntry.class);
		UserQueryBo userquerybo=new UserQueryBo();
		userquerybo.setUserName(userbo.getUserName());
		List<UserEntry> list=iusermanagedao.queryUserList(userquerybo);
		if(list.size()!=0){
			return iusermanagedao.updateUser(userentry);
		}else{
			return false;
		}
	}

	@Override
	public UserBo queryUserInfo(Integer userId) {
		// TODO Auto-generated method stub
		final UserEntry userentry=iusermanagedao.queryUserInfo(userId);
		final UserBo userbo=super.map(userentry, UserBo.class);
		return userbo;
	}

	@Override
	public List<UserBo> queryUserList(UserQueryBo userquerybo) {
		// TODO Auto-generated method stub
		final List<UserEntry> userentry=iusermanagedao.queryUserList(userquerybo);
		final List<UserBo> userbo=super.map(userentry, UserBo.class);
		return userbo;
	}
	
	@Override
	public Boolean insertUserRole(Integer userId,String userRole) {
		// TODO Auto-generated method stub
		UserRoleBo userrolebo=new UserRoleBo();
		userrolebo.setUserId(userId);
		userrolebo.setUserRole(userRole);
		userrolebo.setUpdator(0);
		userrolebo.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userrolebo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return iusermanagedao.insertUserRole(userrolebo);
	}
	
	@Override
	public Boolean updateUserRole(Integer userId,String userRole) {
		// TODO Auto-generated method stub
		UserRoleBo userrolebo=new UserRoleBo();
		userrolebo.setUserId(userId);
		userrolebo.setUserRole(userRole);
		userrolebo.setUpdator(0);
		userrolebo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		return iusermanagedao.updateUserRole(userrolebo);
	}
	
	@Override
	public Boolean deleteUserRole(List<Integer> userid) {
		// TODO Auto-generated method stub
		return iusermanagedao.deleteUserRole(userid);
	}

}
