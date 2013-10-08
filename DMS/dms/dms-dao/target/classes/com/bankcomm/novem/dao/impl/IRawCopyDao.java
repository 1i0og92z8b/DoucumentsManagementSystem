package com.bankcomm.novem.dao.impl;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 可复制的dao
 * 
 * @author 娄东<loud@hongzhitech.com> 泓智 Jan 25, 2013
 */
public interface IRawCopyDao {

	/**
	 * 复制数据，不带历史操作
	 * 
	 * @param entry
	 *            数据实体
	 * @param daoType
	 *            用来操作的dao类型
	 * @param dbSchema
	 *            数据库schema
	 * @return 主键
	 */
	long copyWithoutHistory(final BaseEntry entry, final DaoTypeEnum daoType,
			final String dbSchema);

}