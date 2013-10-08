package com.bankcomm.novem.bo.common;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author zhouwei<risk_admin@bankcomm.com> 砾阳 Jul 30, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class BaselLossEventTypeBo extends BaseBo {
	/** 子节点 */
	private List<BaselLossEventTypeBo> children;
	/** 代码 */
	private String relCode;
	/** 机构层次 */
	private long relLevel;
	/** 代码名称 */
	private String relName;
	/** 父代码 */
	private String relParentCode;
}
