/**
 * @author 韩国栋 <han_gd@bankcomm.com> Aug 16, 2012
 *
 */
package com.bankcomm.novem.bo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 韩国栋 <han_gd@bankcomm.com> Aug 16 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProvinceBranchBo extends BaseBo {
	/** 省行机构号 */
	private long orgId;
	/** 省行机构名称 */
	private String orgName;
}
