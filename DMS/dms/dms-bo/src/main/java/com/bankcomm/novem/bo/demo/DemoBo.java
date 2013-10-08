package com.bankcomm.novem.bo.demo;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2012-3-9
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DemoBo extends BaseBo {
	private long demoId;
	private String demoName;
	private String demoNo;
}
