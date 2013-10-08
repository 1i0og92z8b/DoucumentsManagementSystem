package com.bankcomm.novem.bo.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;
import com.bankcomm.novem.bo.user.UserQueryBo;
import com.bankcomm.novem.comm.PageCond;
/**
 * 
 * @author jibojun
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQueryBo extends BaseBo{
	private String userName;
	private String email;
	private String extNo;
	private PageCond pageCond;
}
