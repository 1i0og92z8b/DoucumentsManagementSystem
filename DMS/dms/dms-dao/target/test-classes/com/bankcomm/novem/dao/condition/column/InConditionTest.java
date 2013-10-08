/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import com.bankcomm.novem.dao.BaseDaoTest;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class InConditionTest extends BaseDaoTest<InCondition> {

	private static final String COLUMN_NAME = "12345";
	private static final String VALUE_IN_LIST = "InConditionTest";
	private static final List<String> VALUE_LIST = new ArrayList<String>();
	static {
		VALUE_LIST.add(VALUE_IN_LIST);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#getOperand()}.
	 */
	@Test
	public void testGetOperator() {
		Assert.assertEquals(SqlOperand.IN, new InCondition(COLUMN_NAME,
				VALUE_LIST).getOperand());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInCondition() {
		final InCondition condition = new InCondition(COLUMN_NAME, VALUE_LIST);

		Assert.assertEquals(COLUMN_NAME, condition.getColumnName());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInConditionForValue() {
		final InCondition condition = new InCondition(COLUMN_NAME, VALUE_LIST);

		Assert.assertEquals(VALUE_LIST, condition.getValue());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInConditionWithEmptyColumName() {
		try {
			@SuppressWarnings("unused")
			final InCondition inCondition = new InCondition("", VALUE_LIST);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInConditionWithEmptyValueList() {
		try {
			@SuppressWarnings("unused")
			final InCondition inCondition = new InCondition(COLUMN_NAME, null);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列值不能为null", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInConditionWithNullColumName() {
		try {
			@SuppressWarnings("unused")
			final InCondition inCondition = new InCondition(null, VALUE_LIST);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.InCondition#InCondition(java.lang.String, java.util.Collection)}
	 * .
	 */
	@Test
	public void testInConditionWithNullValueList() {
		try {
			@SuppressWarnings("unused")
			final InCondition inCondition = new InCondition(COLUMN_NAME,
					new ArrayList<Object>());
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("不能处理空的值列表", e.getMessage());
		}
	}

}
