package com.bankcomm.novem.bo.user;

import lombok.Data;

import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;
import com.bankcomm.novem.bo.user.UserBo;
/**
 * 
 * @author jibojun
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UserBo extends BaseBo{
	private Integer userId;//用户ID
	private String userName;//用户名
	private String passWord;//密码
	private String fullName;//员工姓名
	private String sex;//性别
	private String email;//邮箱地址
	private String extNo;//分机号
	private Integer ifLogin;//用户登录状态标识
	//private String userRole;
}
