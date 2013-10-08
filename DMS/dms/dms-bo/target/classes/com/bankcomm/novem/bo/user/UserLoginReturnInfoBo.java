package com.bankcomm.novem.bo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
/**
 * 
 * @author jibojun
 *
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class UserLoginReturnInfoBo {
	   private Integer userId;//返回信息：表中用户ID
	   private String userName;//返回信息：用户名
	   private String passWord;//返回信息：密码
	   private String userRole;//返回信息：用户角色
	   private Integer logState;//返回用户登录状态,1表示用户名存在,0表示库中不存在此用户名,2表示密码错误
}
