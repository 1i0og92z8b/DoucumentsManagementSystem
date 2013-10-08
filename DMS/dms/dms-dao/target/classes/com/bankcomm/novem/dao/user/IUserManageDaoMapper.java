package com.bankcomm.novem.dao.user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.bo.user.UserRoleBo;
import com.bankcomm.novem.entry.user.UserEntry;

/**
 * 用户管理数据层接口
 * @author jibojun
 *
 */

@Repository
public interface IUserManageDaoMapper {
	
	 /**
	  * 插入用户信息
	 * @param userentry
	 * @return
	 */
	public Boolean insertUser(final UserEntry userentry);//插入用户信息
	 
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
	public Boolean updateUser(final UserEntry userentry);//更新用户信息
	/**
	 * 列出全部用户信息
	 * @param userquerybo
	 * @return
	 */
	public List<UserEntry> queryUserList(final UserQueryBo userquerybo);//列出全部用户信息 
	/**
	 * 分页
	 * @param userquerybo
	 * @return
	 */
	public int queryCount(final UserQueryBo userquerybo);
	public Boolean insertUserRole(final UserRoleBo userrolebo);
	public Boolean updateUserRole(final UserRoleBo userrolebo);
	public Boolean deleteUserRole(final List<Integer> userid);
}
