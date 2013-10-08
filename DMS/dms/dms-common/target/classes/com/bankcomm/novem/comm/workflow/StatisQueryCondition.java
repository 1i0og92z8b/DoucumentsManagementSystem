/**
 * 
 */
package com.bankcomm.novem.comm.workflow;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhouwei<risk_admin@bankcomm.com> 砾阳 Apr 24, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class StatisQueryCondition {
	/**
	 * 待办或者已办
	 */
	private String isHandled;
	/**
	 * 未领取或已领取
	 */
	private String isObtain;
	/**
	 * 历史或者日常
	 */
	private String sourceFlag;
	/**
	 * 用户ID
	 */
	private String userId;

}
