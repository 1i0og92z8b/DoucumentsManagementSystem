/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public interface IColumnCondition {

	/**
	 * @return 列名
	 */
	String getColumnName();

	/**
	 * 获得操作符
	 * 
	 * @return 操作符
	 */
	SqlOperand getOperand();

	/**
	 * @return 列值
	 */
	Object getValue();
}