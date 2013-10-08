package com.bankcomm.novem.bo.user;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserRoleBo {
	private Integer userId;
	private String userRole;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Integer updator;
}
