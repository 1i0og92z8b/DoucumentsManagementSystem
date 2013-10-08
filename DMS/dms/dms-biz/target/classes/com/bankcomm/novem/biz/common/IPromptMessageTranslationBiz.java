/**
 * 
 */
package com.bankcomm.novem.biz.common;

import com.bankcomm.novem.bo.common.PromptMessageTranslationBo;

/**
 * 提示信息翻译接口
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳 2010-12-21
 * 
 */
public interface IPromptMessageTranslationBiz {

	/**
	 * @param messageCode
	 *            信息编码
	 * @param language
	 *            语种
	 * @return 提示信息bo
	 */
	PromptMessageTranslationBo queryPromptMsgTranByCode(
			final String messageCode, final String language);

}
