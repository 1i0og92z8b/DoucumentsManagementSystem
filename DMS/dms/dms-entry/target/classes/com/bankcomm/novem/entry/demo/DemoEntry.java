package com.bankcomm.novem.entry.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-8
 * 
 */
@Data
@EqualsAndHashCode(callSuper=true)
public class DemoEntry extends BaseEntry {
	@EntryPk
	private long demoId;
	private String demoName;
	private String demoNo;
//	private Timestamp createTime;
//	private Timestamp updateTime;
	private Integer updator;
}
