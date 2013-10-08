package com.bankcomm.novem.dao.utils;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-3-3
 * 
 */
class UnboxNotNumericTester {
	private final boolean unboxBoolean = true;
	private final char unboxChar = 'a';

	/**
	 * @return {@link #unboxChar}
	 */
	public char getUnboxChar() {
		return unboxChar;
	}

	/**
	 * @return {@link #unboxBoolean}
	 */
	public boolean isUnboxBoolean() {
		return unboxBoolean;
	}
}