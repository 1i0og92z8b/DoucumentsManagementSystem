/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public enum SqlOperand {
	/**
	 * 
	 */
	EQUALS {

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Enum#toString()
		 */
		@Override
		public String toString() {
			return "=";
		}

	},
	/**
	 * 
	 */
	IN,
	/**
	 * 
	 */
	IS;
}
