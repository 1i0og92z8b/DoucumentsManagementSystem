package com.bankcomm.novem.dao.condition;

import java.util.List;

import com.bankcomm.novem.dao.condition.column.IColumnCondition;

/**
 * sql 条件
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-8
 * 
 */
public abstract class AbstractSqlArgumentWithColumns extends
		AbstractSqlArgument {
	private List<IColumnCondition> whereColumns;

	/**
	 * @param tableName
	 *            {@link #tableName}
	 */
	protected AbstractSqlArgumentWithColumns(final String tableName) {
		super(tableName);
	}

	/**
	 * @param tableName
	 *            {@link #tableName}
	 * @param whereColumns
	 *            {@link #whereColumns}
	 */
	protected AbstractSqlArgumentWithColumns(final String tableName,
			final List<IColumnCondition> whereColumns) {
		super(tableName);
		this.whereColumns = whereColumns;
	}

	/**
	 * @return {@link #whereColumns}
	 */
	public List<IColumnCondition> getWhereColumns() {
		return whereColumns;
	}

	/**
	 * @param whereColumns
	 *            {@link #whereColumns}
	 */
	public void setWhereColumns(final List<IColumnCondition> whereColumns) {
		this.whereColumns = whereColumns;
	}
}