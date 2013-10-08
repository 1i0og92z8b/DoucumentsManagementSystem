/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class EqualsCondition extends AbstractColumnCondition {

	/**
	 * @param columnName
	 *            {@link AbstractColumnCondition#columnName}
	 * @param value
	 *            {@link AbstractColumnCondition#value}
	 */
	public EqualsCondition(final String columnName, final Object value) {
		super(columnName, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bocom.egif.dao.condition.column.AbstractColumnCondition#getOperator()
	 */
	@Override
	public SqlOperand getOperand() {
		return SqlOperand.EQUALS;
	}

}
