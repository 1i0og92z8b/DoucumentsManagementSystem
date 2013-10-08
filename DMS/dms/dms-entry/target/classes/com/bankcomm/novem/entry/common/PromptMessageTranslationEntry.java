package com.bankcomm.novem.entry.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.entry.BaseEntry;
import com.bankcomm.novem.entry.annotation.EntryPk;

/**
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-16
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PromptMessageTranslationEntry extends BaseEntry {
	/** * 语种实体编码 */
	private String languageCode;
	/** * 模块编号 */
	private String modelCode;
	/** * 提示信息对象编码 */
	private String promptMessageCode;
	/** * 信息名称 */
	private String promptMessageName;
	/** * 翻译主键 */
	@EntryPk
	private long promptMessageTranslationId;
	/** * 顺序号 */
	private String sNo;
	/** * 系统编号 */
	private String systemCode;
	/**
	 * @return {@link #sNo}
	 */
	public String getsNo() {
		return sNo;
	}
	/**
	 * @param sNo {@link #sNo}
	 */
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
}
