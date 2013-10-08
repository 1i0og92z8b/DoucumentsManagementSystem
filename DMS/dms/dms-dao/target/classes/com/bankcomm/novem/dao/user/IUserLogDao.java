package com.bankcomm.novem.dao.user;


import com.bankcomm.novem.bo.user.UserLogBo;
import com.bankcomm.novem.bo.user.UserLoginReturnInfoBo;
/**
 * 用户登录数据层接口
 * @author jibojun
 *
 */


public interface IUserLogDao {
	/**
	 * 用户名验证
	 * @param userlogbo
	 * @return
	 */
	public UserLoginReturnInfoBo checkUsername(final UserLogBo userlogbo);//用户名验证
	/**
	 * 密码验证
	 * @param userlogbo
	 * @return
	 */
	public UserLoginReturnInfoBo checkPassword(final UserLogBo userlogbo);//密码验证
	/**
	 * 获取用户角色
	 * @param userloginreturninfobo
	 * @return
	 */
	public String getUserRole(UserLoginReturnInfoBo userloginreturninfobo);
	/**
	 * 设定登录状态值
	 * @param userlogbo
	 * @return
	 */
	public Integer setLog(final UserLogBo userlogbo); 
	/**
	 * 登出系统
	 * @return
	 */
	public Integer logout(final UserLogBo userlogbo);//登出系统
}
