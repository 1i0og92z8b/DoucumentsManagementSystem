package com.bankcomm.novem.dao.common;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.bankcomm.novem.dao.SingleTableDao;
import com.bankcomm.novem.dao.annote.DbSchemaType;
import com.bankcomm.novem.entry.common.PromptMessageTranslationEntry;

/**
 * 
 * @author 杨晓俊 yangxj@rionsoft.com 砾阳软件 2010-12-20
 * 
 * 
 * 
 */
@DbSchemaType("db2iport")
@Repository
class PromptMessageTranslationDaoImpl extends
		SingleTableDao<PromptMessageTranslationEntry> implements
		IPromptMessageTranslationDao {

	@Override
	public PromptMessageTranslationEntry queryPromptMsgTranByCode(
			final String promptMsgTranCode, final String languageCode) {
		Assert.notNull(promptMsgTranCode, "提示信息编码不能为空");
		final Map<String, Object> columnValues = new HashMap<String, Object>();
		columnValues.put("prompt_message_code", promptMsgTranCode);
		columnValues.put("language_code", languageCode);
		return queryByColumns(columnValues);
	}

}
