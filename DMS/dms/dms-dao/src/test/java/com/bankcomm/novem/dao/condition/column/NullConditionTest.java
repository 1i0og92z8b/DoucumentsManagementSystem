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
public class NullConditionTest extends BaseDaoTest<NullCondition> {
	/**
	 * 
	 */
	private static final String COLUMN_NAME = "12345";

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.NullCondition#getOperand()}
	 * .
	 */
	@Test
	public void testGetOperator() {
		Assert.assertEquals(SqlOperand.IS,
				new NullCondition(COLUMN_NAME).getOperand());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.NullCondition#NullCondition(java.lang.String)}
	 * .
	 */
	@Test
	public void testNullCondition() {
		final NullCondition condition = new NullCondition(COLUMN_NAME);

		Assert.assertEquals(COLUMN_NAME, condition.getColumnName());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.NullCondition#NullCondition(java.lang.String)}
	 * .
	 */
	@Test
	public void testNullConditionForValue() {
		final NullCondition condition = new NullCondition(COLUMN_NAME);

		Assert.assertNull(condition.getValue());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.NullCondition#NullCondition(java.lang.String)}
	 * .
	 */
	@Test
	public void testNullConditionWithEmptyColumeName() {
		try {
			@SuppressWarnings("unused")
			final NullCondition nullCondition = new NullCondition("");
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.NullCondition#NullCondition(java.lang.String)}
	 * .
	 */
	@Test
	public void testNullConditionWithNullColumnName() {
		try {
			@SuppressWarnings("unused")
			final NullCondition nullCondition = new NullCondition(null);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("列名不能为空", e.getMessage());
		}
	}

}
