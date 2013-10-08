package com.bankcomm.novem.biz;

import lombok.Data;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-24
 * 
 */
@Data
public class MockField {
	private String fieldName;
	private Object nativeValue;
	private Object toMock;

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return (toMock.hashCode() * 2) + (fieldName.hashCode() * 1);
	}
}