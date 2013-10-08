/**
 * 
 */
package com.bankcomm.novem.dao.condition;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-11
 * 
 */
public class AbstractSqlArgument {

	/**
	 * 表名
	 */
	private final String tableName;

	/**
	 * @param tableName
	 *            {@link #tableName}
	 * 
	 */
	protected AbstractSqlArgument(final String tableName) {
		super();
		this.tableName = tableName;
	}

	/**
	 * @return {@link #tableName}
	 */
	public String getTableName() {
		return tableName;
	}

}