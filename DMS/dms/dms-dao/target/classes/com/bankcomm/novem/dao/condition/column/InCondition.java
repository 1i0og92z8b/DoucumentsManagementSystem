/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import java.util.Collection;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class InCondition extends AbstractColumnCondition {

	/**
	 * @param columnName
	 *            列名
	 * @param valueList
	 *            列值
	 */
	public InCondition(final String columnName, final Collection<?> valueList) {
		super(columnName, valueList);
		org.springframework.util.Assert.notEmpty(valueList, "不能处理空的值列表");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.condition.Condition#getOperator()
	 */
	@Override
	public SqlOperand getOperand() {
		return SqlOperand.IN;
	}
}
