package com.bankcomm.novem.bo.statistics;

import lombok.Data;

/**
 * 
 * @author 荆昊熠<jing_hy@bankcomm.com> 
 * 
 */
@Data
public class UserRankingBo {
	private int userId;
	private String userName;
	private String name;
	private String sex;
	private String email;
	private String extNo;
	private int counts;
}
