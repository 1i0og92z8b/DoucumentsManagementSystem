package com.bankcomm.novem.dao.condition;

import java.util.List;

import com.bankcomm.novem.dao.condition.column.IColumnCondition;

/**
 * 删除条件
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-8
 * 
 */
public class DeleteArgument extends AbstractSqlArgumentWithColumns {
	/**
	 * @param tableName
	 *            {@link AbstractSqlArgument#tableName}
	 * @param whereColumns
	 *            {@link AbstractSqlArgumentWithColumns#whereColumns}
	 */
	public DeleteArgument(final String tableName,
			final List<IColumnCondition> whereColumns) {
		super(tableName, whereColumns);
	}
}