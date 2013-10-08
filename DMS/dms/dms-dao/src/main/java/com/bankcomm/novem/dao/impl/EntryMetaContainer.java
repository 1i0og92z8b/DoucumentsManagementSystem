/**
 * 
 */
package com.bankcomm.novem.dao.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.bankcomm.novem.dao.annote.DaoTypeEnum;
import com.bankcomm.novem.dao.utils.DBTransfer;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-11-8
 * 
 */
@Component
public class EntryMetaContainer {
	private final Map<Class<? extends BaseEntry>, String> pkNameBuffer = new HashMap<Class<? extends BaseEntry>, String>();
	private final Map<Class<? extends BaseEntry>, Map<DaoTypeEnum, String>> tableNameBuffer = new HashMap<Class<? extends BaseEntry>, Map<DaoTypeEnum, String>>();

	/**
	 * 提取表名
	 * 
	 * @param entryClass
	 *            实体类型信息
	 * @return 主键名
	 */
	public String extractPkName(final Class<? extends BaseEntry> entryClass) {
		if (pkNameBuffer.get(entryClass) != null) {
			return pkNameBuffer.get(entryClass);
		}

		final String pkName = DBTransfer.extractPkName(entryClass);
		Assert.hasLength(pkName, "主键名不能为空");
		pkNameBuffer.put(entryClass, pkName);
		return pkName;
	}

	/**
	 * 获取表名
	 * 
	 * @param classInfo
	 *            数据类型信息
	 * @param daoType
	 *            * dao类型
	 * @param dbSchema
	 *            * 数据库schema
	 * @return 该数据实体类型和dao类型对应的表名
	 */
	public String extractTableName(final Class<? extends BaseEntry> classInfo,
			final DaoTypeEnum daoType, final String dbSchema) {
		final Map<DaoTypeEnum, String> typeMap = getTypeBuffer(classInfo);
		if (typeMap.get(daoType) != null) {
			return typeMap.get(daoType);
		}

		final String tableName = DBTransfer.extractTableName(
				classInfo.getSimpleName(), daoType, dbSchema);
		Assert.hasLength(tableName, "表名不能为空");
		typeMap.put(daoType, tableName);
		return tableName;
	}

	/**
	 * @param classInfo
	 * @return
	 */
	private Map<DaoTypeEnum, String> getTypeBuffer(
			final Class<? extends BaseEntry> classInfo) {
		if (tableNameBuffer.get(classInfo) == null) {
			tableNameBuffer.put(classInfo, new HashMap<DaoTypeEnum, String>());
		}
		return tableNameBuffer.get(classInfo);
	}
}
