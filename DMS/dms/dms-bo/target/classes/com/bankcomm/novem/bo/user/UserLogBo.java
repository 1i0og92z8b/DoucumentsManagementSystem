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
public class UserLogBo {
	private String userName;
	private String passWord;
}
