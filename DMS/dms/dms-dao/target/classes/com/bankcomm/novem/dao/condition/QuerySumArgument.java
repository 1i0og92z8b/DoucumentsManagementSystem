/**
 * 
 */
package com.bankcomm.novem.dao.condition;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.dao.condition.column.IColumnCondition;

/**
 * 求和参数
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-8-13
 * 
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySumArgument extends QueryArgument {
	/** 求和列 */
	private final String sumColumn;

	/**
	 * @param tableName
	 *            表名
	 * @param whereColumns
	 *            查询条件
	 * @param sumColumn
	 *            求和列
	 */
	public QuerySumArgument(final String tableName,
			final List<IColumnCondition> whereColumns, final String sumColumn) {
		super(tableName, whereColumns);
		this.sumColumn = sumColumn;
	}
}
