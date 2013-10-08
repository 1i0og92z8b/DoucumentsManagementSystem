package com.bankcomm.novem.dao.user;

import java.util.List;

import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.bo.user.UserRoleBo;
import com.bankcomm.novem.entry.user.UserEntry;
/**
 * 用户管理数据层接口
 * @author jibojun
 *
 */

public interface IUserManageDao {
	/**
	 * 插入用户信息
	 * @param userentry
	 * @return
	 */
	 public Boolean insertUser(UserEntry userentry);//插入用户信息
	 /**
	  * 删除用户信息
	  * @param userquerybo
	  * @return
	  */
	 public Boolean deleteUser(final List<String> username);//删除用户信息
	 /**
	  * 查询用户信息
	  * @param userquerybo
	  * @return
	  */
	 public UserEntry queryUserInfo(final Integer userId);//查询用户信息
	 /**
	  * 更新用户信息
	  * @param userentry
	  * @return
	  */
	 public Boolean updateUser(UserEntry userentry);//更新用户信息
	 /**
	  * 列出全部用户信息
	  * @param userquerybo
	  * @return
	  */
	 public List<UserEntry> queryUserList(final UserQueryBo userquerybo);//列出全部用户信息
	 public Boolean insertUserRole(final UserRoleBo userrolebo);
	 public Boolean updateUserRole(final UserRoleBo userrolebo);
	 public Boolean deleteUserRole(final List<Integer> userid);
}
