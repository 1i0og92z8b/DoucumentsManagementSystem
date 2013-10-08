package com.bankcomm.novem.bo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ProcessLogBo extends BaseBo {
	/** 处理返回的错误信息 */
	private String processFlag;
	/** 操作ID */
	private String processId;
	/** 主键 */
	private long processLogId;
	/** 处理信息 */
	private String processMsg;
	/** 处理机构 */
	private long processOrg;
	/** 备注信息 */
	private String processRemark;
	/** 处理人 */
	private long processUser;

}
