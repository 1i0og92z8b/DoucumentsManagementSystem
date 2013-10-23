package com.bankcomm.novem.biz.user;

import java.util.List;



import com.bankcomm.novem.bo.user.UserBo;
import com.bankcomm.novem.bo.user.UserQueryBo;

/**
 * 
 * @author jibojun
 *
 */

public interface IUserManageBiz {
	public Boolean insertUser(final UserBo userbo);
	public Boolean insertUserRole(final Integer userId,final String userRole);
	public Boolean deleteUser(final List<String> username);
	public Boolean deleteUserRole(final List<Integer> userId);
	public Boolean updateUser(final UserBo userbo,final String pUserName);
	public Boolean updateUserRole(final Integer userId,final String userRole);
	public UserBo queryUserInfo(final Integer userId);
	public List<UserBo> queryUserList(final UserQueryBo userquerybo);
	public Boolean updatePassword(final UserBo userbo);

}
