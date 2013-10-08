package com.bankcomm.novem.dao.user;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.search.FileFieldBo;
import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
import com.bankcomm.novem.bo.user.UserQueryBo;

/**
 * 
 * @author jibojun
 *
 */

@Repository
public interface IUserLogDaoMapper {
	public UserLoginReturnInfoBo checkUsername(final UserLogBo userlogbo);//用户名验证
	public UserLoginReturnInfoBo checkPassword(final UserLogBo userlogbo);//密码验证
	public String getUserRole(UserLoginReturnInfoBo userloginreturninfobo);
	public Integer setLog(final UserLogBo userlogbo);
	public Integer logout(final UserLogBo userlogbo);//登出系统
	
}
