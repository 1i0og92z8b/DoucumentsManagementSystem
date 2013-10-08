package com.bankcomm.novem.entry.user;

import java.sql.Timestamp;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.entry.user.UserEntry;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 
 * @author jibojun
 *
 */

@Data
@EqualsAndHashCode(callSuper = false)
public class UserEntry extends BaseEntry{
	private Integer userId;//用户ID
	private String userName;//用户名
	private String passWord;//密码
	private String fullName;//员工姓名
	private String sex;//性别
	private String email;//邮箱地址
	private String extNo;//分机号
	private Integer ifLogin;//用户登录状态标识
	private Timestamp createTime;//创建时间
	private Timestamp updateTime;//更新时间
	private Integer updator; //更新人
}
