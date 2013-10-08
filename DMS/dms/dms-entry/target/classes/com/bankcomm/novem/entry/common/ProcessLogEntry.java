package com.bankcomm.novem.entry.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;
import com.bankcomm.novem.entry.annotation.VersionEntry;

/**
 * 
 * @author sunpu sun.pu@bankcomm.com Jul 18, 2012
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
@VersionEntry
public class ProcessLogEntry extends BaseEntry {
	/** 处理标志 */
	private String processFlag;
	/** 操作ID */
	private String processId;
	/** 主键 */
	@EntryPk
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
