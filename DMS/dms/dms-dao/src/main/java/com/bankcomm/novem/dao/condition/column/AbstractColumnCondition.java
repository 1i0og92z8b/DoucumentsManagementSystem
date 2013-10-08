/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import org.springframework.util.Assert;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public abstract class AbstractColumnCondition implements IColumnCondition {
	private final String columnName;
	private final Object value;

	/**
	 * @param columnName
	 *            列名
	 */
	AbstractColumnCondition(final String columnName) {
		Assert.hasLength(columnName, "列名不能为空");
		this.columnName = columnName;
		value = null;
	}

	/**
	 * @param columnName
	 *            列名
	 * @param value
	 *            列值
	 */
	AbstractColumnCondition(final String columnName, final Object value) {
		Assert.hasLength(columnName, "列名不能为空");
		Assert.notNull(value, "列值不能为null");
		this.columnName = columnName;
		this.value = value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.condition.column.IColumnCondition#getColumnName()
	 */
	@Override
	public String getColumnName() {
		return columnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.condition.column.IColumnCondition#getValue()
	 */
	@Override
	public Object getValue() {
		return value;
	}

}
