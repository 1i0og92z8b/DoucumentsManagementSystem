package com.bankcomm.novem.dao.common;

import com.bankcomm.novem.dao.ISingleTableDao;
import com.bankcomm.novem.entry.common.PromptMessageTranslationEntry;

/**
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-15
 * 
 * 
 * 
 */
public interface IPromptMessageTranslationDao extends
		ISingleTableDao<PromptMessageTranslationEntry> {

	/**
	 * 根据提示信息编码和语种编码 查询提示信息entry
	 * 
	 * @param promptMsgTranCode
	 *            提示信息编码
	 * @param languageCode
	 *            语种编码
	 * @return 提示信息翻译对象
	 */
	PromptMessageTranslationEntry queryPromptMsgTranByCode(
			final String promptMsgTranCode, final String languageCode);

}
