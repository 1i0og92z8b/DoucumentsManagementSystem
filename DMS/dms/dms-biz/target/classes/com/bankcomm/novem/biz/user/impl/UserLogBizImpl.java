package com.bankcomm.novem.biz.user.impl;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.user.IUserLogBiz;
import com.bankcomm.novem.biz.user.IUserManageBiz;
import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
import com.bankcomm.novem.dao.user.IUserLogDao;
import com.bankcomm.novem.dao.user.IUserManageDao;
import com.bankcomm.novem.entry.user.UserEntry;

/**
 * 
 * @author jibojun
 *
 */

@Service
public class UserLogBizImpl extends BaseBiz implements IUserLogBiz {
	@Autowired
	private IUserLogDao iuserlogdao;
	
	private IUserManageDao iusermanagedao;
	
	private IUserLogBiz iuserlogbiz;


	@Override
	public UserLoginReturnInfoBo checkUsername(UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		UserLoginReturnInfoBo userloginreturninfobo=iuserlogdao.checkUsername(userlogbo);
		if(userloginreturninfobo==null){
			return userloginreturninfobo;
		}
		else if(!userloginreturninfobo.getPassWord().equals(userlogbo.getPassWord())){
			userloginreturninfobo.setLogState(1);
			//userloginreturninfobo.setUserRole(iuserlogdao.getUserRole(userloginreturninfobo));
			return userloginreturninfobo;
		}
		else{
			userloginreturninfobo.setLogState(2);
			userloginreturninfobo.setUserRole(iuserlogdao.getUserRole(userloginreturninfobo));
			iuserlogdao.setLog(userlogbo);
			return userloginreturninfobo;
		}
	}

	/**
	@Override
	public int checkPassword(UserLogBo userlogbo,UserLoginReturnInfoBo userloginreturninfobo) {
		// TODO Auto-generated method stub
		userloginreturninfobo=iuserlogdao.checkPassword(userlogbo);
		if(userloginreturninfobo.getPassWord()!=null)
		{
			userloginreturninfobo.setLogState(2);
			UserBo userbo=new UserBo();
			userbo.setUserName(userlogbo.getUserName());
			userbo.setIfLogin(1);
			iuserlogbiz.setLog(userlogbo);
			final UserEntry userentry = super.map(userbo, UserEntry.class);
			iusermanagedao.updateUser(userentry);
		}
		return userloginreturninfobo;
	}
	*/
	
	@Override
	public UserLoginReturnInfoBo getUserRole(UserLoginReturnInfoBo userloginreturninfobo){
		// TODO Auto-generated method stub
		String a=iuserlogdao.getUserRole(userloginreturninfobo);
		userloginreturninfobo.setUserRole(a);
		return userloginreturninfobo;
	}
	
	@Override
	public Integer setLog(UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return iuserlogdao.setLog(userlogbo);
	}


	@Override
	public Integer logout(UserLogBo userlogbo) {
		// TODO Auto-generated method stub
		return iuserlogdao.logout(userlogbo);
	}

}
