/**
 * 
 */
package com.bankcomm.novem.bo.user;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bocom.jump.bp.core.impl.DefaultUser;
import com.bocom.jump.component.euif.model.ApRole;

/**
 * @author sunpu<sun.pu@bankcomm.com> Apr 11, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NovemUser extends DefaultUser {
	private static final long serialVersionUID = -9022064242076959467L;
	private String firBranchOrgCode;
	/** 省直行 **/
	private long firBranchOrgId;
	private String firBranchOrgName;
	private String guipUserCode;
	private String guipUserId;

	/*** 登录用户名 */
	private String loginName;
	private String orgCode;
	private long orgId;
	private String orgName;
	/** 角色 **/
	private List<ApRole> roleList;
	private String secBranchOrgCode;
	/** 省辖行 **/
	private long secBranchOrgId;
	/** 用户ID **/
	private long uId;
	private String userCode;
	/** 用户名 **/
	private String userName;
}
