package com.bankcomm.novem.bo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserUpdateBo {
	private String userName;
	private String fullName;
	private String sex;
	private String email;
	private String extNo;

}
