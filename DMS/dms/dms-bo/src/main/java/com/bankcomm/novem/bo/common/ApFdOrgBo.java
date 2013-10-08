/**
 * 
 */
package com.bankcomm.novem.bo.common;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 朱少秦<zhusq@rionsoft.com> 砾阳 2012-4-27
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ApFdOrgBo extends BaseBo {
	private List<ApFdOrgBo> children;
	private String ehrOrgPk;
	private String hxOrgCode;
	private String isLeaf;
	private String orgCode;
	private long orgId;
	private long orgLevel;
	private String orgName;
	private String orgNameSeq;
	private String orgSeq;
	private String orgType;
	private long parentOrgId;
}