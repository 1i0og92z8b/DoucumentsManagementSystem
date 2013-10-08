/**
 * 
 */
package com.bankcomm.novem.dao;

import lombok.Data;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.dao.annote.DaoTypeProcessor;
import com.bankcomm.novem.dao.utils.DBTransfer;
import com.bankcomm.novem.dao.utils.MetaDataExtractor;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * 表级别的元数据
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-11
 * @param <T>
 *            具体的entry类型
 * 
 */
@Data
public class TableMeta<T extends BaseEntry> {
	/** dao类型 */
	private final DaoTypeEnum daoType;
	/** schema */
	private final String dbSchema;
	/** entry 类信息 */
	private final Class<T> entryClassInfo;
	/** 表名 */
	private final String tableName;

	/**
	 * @param dbSchema
	 *            {@link #dbSchema}
	 * @param tableName
	 *            {@link #tableName}
	 * @param daoType
	 *            {@link #daoType}
	 * @param entryClassInfo
	 *            {@link #entryClassInfo}
	 */
	private TableMeta(final String dbSchema, final String tableName,
			final DaoTypeEnum daoType, final Class<T> entryClassInfo) {
		super();
		this.daoType = daoType;
		this.dbSchema = dbSchema;
		this.entryClassInfo = entryClassInfo;
		this.tableName = tableName;
	}

	/**
	 * 从dao中抽取元数据
	 * 
	 * @param daoClass
	 *            dao类型
	 * @return 该dao中的源数据
	 */
	public static <E extends BaseEntry> TableMeta<E> extractMeta(
			Class<? extends SingleTableDao<E>> daoClass) {
		final Class<E> entryClassInfo = MetaDataExtractor
				.extractEntryClassInfo(daoClass);
		final DaoTypeEnum daoType = DaoTypeProcessor.extractDaoType(daoClass);
		final String dbSchema = DaoTypeProcessor.extractDaoSchema(daoClass);
		final String tableName = DBTransfer.extractTableName(
				entryClassInfo.getSimpleName(), daoType, dbSchema);
		return new TableMeta<E>(dbSchema, tableName, daoType, entryClassInfo);
	}
}