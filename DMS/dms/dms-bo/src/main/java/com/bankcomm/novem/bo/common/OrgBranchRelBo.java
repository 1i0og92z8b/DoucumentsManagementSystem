/**
 * 
 */
package com.bankcomm.novem.bo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author sunpu<sun.pu@bankcomm.com> Apr 11, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OrgBranchRelBo extends BaseBo {
	/** 主键 **/
	private long appId;
	/** 省直行ID **/
	private long directBrOrgId;
	/** 机构ID **/
	private long orgId;
	/** 省辖行ID **/
	private long ruleBrOrgId;
}
