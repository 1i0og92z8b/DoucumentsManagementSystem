package com.bankcomm.novem.dao.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
import com.bankcomm.novem.dao.user.IUserLogDao;
import com.bankcomm.novem.dao.user.IUserLogDaoMapper;

/**
 * 用户登录数据层接口实现
 * @author jibojun
 *
 */

@Repository
public class UserLogDaoImpl implements IUserLogDao {
	
	@Autowired
	private IUserLogDaoMapper userlogdaomapper; 

	@Override
	public UserLoginReturnInfoBo checkUsername(final UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return userlogdaomapper.checkUsername(userlogbo);
	}

	@Override
	public UserLoginReturnInfoBo checkPassword(final UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return userlogdaomapper.checkPassword(userlogbo);
	}
	@Override
	public String getUserRole(UserLoginReturnInfoBo userloginreturninfobo) {
		return userlogdaomapper.getUserRole(userloginreturninfobo);
	}
	
	@Override
	public Integer setLog(final UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return userlogdaomapper.setLog(userlogbo);
	}	
	
	@Override
	public Integer logout(final UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return userlogdaomapper.logout(userlogbo);
	}

}
