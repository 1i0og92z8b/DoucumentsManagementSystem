package com.bankcomm.novem.dao.condition.column;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class NullCondition extends AbstractColumnCondition {
	/**
	 * @param columnName
	 *            列名
	 */
	public NullCondition(final String columnName) {
		super(columnName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bocom.egif.dao.condition.Condition#getOperator()
	 */
	@Override
	public SqlOperand getOperand() {
		return SqlOperand.IS;
	}
}
