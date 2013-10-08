package com.bankcomm.novem.dao.condition;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.util.CollectionUtils;

import com.bankcomm.novem.dao.condition.column.IColumnCondition;

/**
 * 查询条件
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-9
 * 
 */
public class QueryArgument extends AbstractSqlArgumentWithColumns {
	private static Set<String> defaultColumns = new HashSet<String>();
	static {
		defaultColumns.add("*");
	}
	/**
	 * 结果列名
	 */
	private final Set<String> resultColumns = new HashSet<String>();

	/**
	 * @param tableName
	 *            {@link AbstractSqlArgumentWithColumns#tableName}
	 * @param whereColumns
	 *            {@link AbstractSqlArgumentWithColumns#whereColumns}
	 */
	public QueryArgument(final String tableName,
			final List<IColumnCondition> whereColumns) {
		super(tableName, whereColumns);
	}

	/**
	 * @param tableName
	 *            {@link AbstractSqlArgumentWithColumns#tableName}
	 * @param whereColumns
	 *            {@link AbstractSqlArgumentWithColumns#whereColumns}
	 * @param resultColumns
	 *            {@link #resultColumns}
	 */
	public QueryArgument(final String tableName,
			final List<IColumnCondition> whereColumns,
			final Set<String> resultColumns) {
		super(tableName, whereColumns);
		if (!CollectionUtils.isEmpty(resultColumns)) {
			this.resultColumns.addAll(resultColumns);
		}
	}

	/**
	 * @return {@link #resultColumns}
	 */
	public Set<String> getResultColumns() {
		if (CollectionUtils.isEmpty(resultColumns)) {
			return defaultColumns;
		}
		return resultColumns;
	}
}