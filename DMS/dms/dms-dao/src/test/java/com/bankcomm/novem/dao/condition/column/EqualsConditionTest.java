/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import org.junit.Assert;

import org.junit.Test;

import com.bankcomm.novem.dao.BaseDaoTest;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class EqualsConditionTest extends BaseDaoTest<EqualsCondition> {
	/**
	 * 
	 */
	private static final String COLUMN_NAME = "12345";
	private static final String VALUE = "EqualsConditionTest";

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsCondition() {
		final EqualsCondition condition = new EqualsCondition(COLUMN_NAME,
				VALUE);

		Assert.assertEquals(COLUMN_NAME, condition.getColumnName());
	}

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsConditionForValue() {
		final EqualsCondition condition = new EqualsCondition(COLUMN_NAME,
				VALUE);

		Assert.assertEquals(VALUE, condition.getValue());
	}

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsConditionWithEmptyColumeName() {
		try {
			@SuppressWarnings("unused")
			final EqualsCondition equalsCondition = new EqualsCondition("",
					VALUE);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsConditionWithEmptyValue() {
		final EqualsCondition condition = new EqualsCondition(COLUMN_NAME, "");

		Assert.assertEquals("", condition.getValue());
	}

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsConditionWithNullColumeName() {
		try {
			@SuppressWarnings("unused")
			final EqualsCondition equalsCondition = new EqualsCondition(null,
					VALUE);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for {@link EqualsCondition#EqualsCondition(String, String)}
	 */
	@Test
	public void testEqualsConditionWithNullValue() {
		try {
			@SuppressWarnings("unused")
			final EqualsCondition equalsCondition = new EqualsCondition(
					COLUMN_NAME, null);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列值不能为null", e.getMessage());
		}
	}

	/**
	 * Test method for {@link EqualsCondition#getOperand()}.
	 */
	@Test
	public void testGetOperator() {
		Assert.assertEquals(SqlOperand.EQUALS, new EqualsCondition(COLUMN_NAME,
				VALUE).getOperand());
	}
}
