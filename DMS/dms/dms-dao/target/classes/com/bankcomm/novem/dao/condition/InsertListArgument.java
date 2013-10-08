/**
 * 
 */
package com.bankcomm.novem.dao.condition;

import java.util.List;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-2-3
 * 
 */
public class InsertListArgument extends AbstractSqlArgument {
	private final List<String> columnNameList;
	private final List<List<Object>> valueList;

	/**
	 * @param tableName
	 *            表名
	 * @param columnNameList
	 *            列名列表
	 * @param valueList
	 *            值列表
	 */
	public InsertListArgument(final String tableName,
			final List<String> columnNameList,
			final List<List<Object>> valueList) {
		super(tableName);
		this.columnNameList = columnNameList;
		this.valueList = valueList;
	}

	/**
	 * @return {@link #columnNameList}
	 */
	public List<String> getColumnNameList() {
		return columnNameList;
	}

	/**
	 * @return {@link #valueList}
	 */
	public List<List<Object>> getValueList() {
		return valueList;
	}
}
