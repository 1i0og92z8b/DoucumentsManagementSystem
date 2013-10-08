/**
 * 
 */
package com.bankcomm.novem.biz.common.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankcomm.novem.biz.BaseBiz;
import com.bankcomm.novem.biz.common.IPromptMessageTranslationBiz;
import com.bankcomm.novem.bo.common.PromptMessageTranslationBo;
import com.bankcomm.novem.dao.common.IPromptMessageTranslationDao;
import com.bankcomm.novem.entry.common.PromptMessageTranslationEntry;

/**
 * 提示信息翻译接口
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳 2010-12-21
 * 
 */
@Service
public class PromptMsgTranslationBizImpl extends BaseBiz implements
		IPromptMessageTranslationBiz {
	@Autowired
	private Mapper mapper;
	/**
	 * 带配置的翻译提示信息编码
	 */
	@Autowired
	private IPromptMessageTranslationDao promptMsgTranDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.bocom.egif.biz.language.IPromptMessageTranslationBiz#
	 * queryPromptMsgTranByCode(java.lang.String, java.lang.String)
	 */
	@Override
	public PromptMessageTranslationBo queryPromptMsgTranByCode(
			final String messageCode, final String language) {
		final PromptMessageTranslationEntry queryPromptMsgTranByCode = promptMsgTranDao
				.queryPromptMsgTranByCode(messageCode, language);
		if (queryPromptMsgTranByCode == null) {
			return null;
		}
		return mapper.map(queryPromptMsgTranByCode,
				PromptMessageTranslationBo.class);
	}

}
