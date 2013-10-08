package com.bankcomm.novem.bo.common;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PromptMessageTranslationBo extends BaseBo {
	/** * 语种实体编码 */
	private String languageCode;
	/** * 模块编号 */
	private String modelCode;
	/** * 提示信息对象编码 */
	private String promptMessageCode;
	/** * 信息名称 */
	private String promptMessageName;
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
	 * @param sNo
	 *            {@link #sNo}
	 */
	public void setsNo(String sNo) {
		this.sNo = sNo;
	}
}