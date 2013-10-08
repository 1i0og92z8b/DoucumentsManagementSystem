package com.bankcomm.novem.biz.user;

import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;

/**
 * 
 * @author jibojun
 *
 */

public interface IUserLogBiz {
	public UserLoginReturnInfoBo checkUsername(UserLogBo userlogbo);
	//public int checkPassword(UserLogBo userlogbo,UserLoginReturnInfoBo userloginreturninfobo);
	public UserLoginReturnInfoBo getUserRole(UserLoginReturnInfoBo userloginreturninfobo);
	public Integer setLog(UserLogBo userlogbo);
	public Integer logout(UserLogBo userlogbo);

}
