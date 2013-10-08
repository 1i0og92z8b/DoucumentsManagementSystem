/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;

import org.junit.Test;

import com.bankcomm.novem.dao.BaseDaoTest;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public class ConditionFactoryTest extends BaseDaoTest<ConditionFactory> {

	/**
	 * 
	 */
	private static final String COLUMN_NAME = "test";
	/**
	 * 
	 */
	private static final String STRING_VALUE = "1";
	/**
	 * 
	 */
	private static final ArrayList<String> VALUE_LIST = new ArrayList<String>();

	static {
		VALUE_LIST.add(STRING_VALUE);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractList() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, STRING_VALUE);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(1, conditionList.size());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithEmptyColumnList() {
		try {
			ConditionFactory.extractList(new HashMap<String, Object>());
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("待解析的数据不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithList() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, VALUE_LIST);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(InCondition.class, conditionList.get(0).getClass());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithListForColumnName() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, VALUE_LIST);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(COLUMN_NAME, conditionList.get(0).getColumnName());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithListForValue() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, VALUE_LIST);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(VALUE_LIST, conditionList.get(0).getValue());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithNullColumnList() {
		try {
			ConditionFactory.extractList(null);
			checkExceptionRaise();
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("待解析的数据不能为空", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithNullValue() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, null);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(NullCondition.class, conditionList.get(0)
				.getClass());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithNullValueForColumnName() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, null);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(COLUMN_NAME, conditionList.get(0).getColumnName());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithSingleObject() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, STRING_VALUE);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(EqualsCondition.class, conditionList.get(0)
				.getClass());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithSingleObjectForColumnName() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, STRING_VALUE);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(COLUMN_NAME, conditionList.get(0).getColumnName());
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.condition.column.ConditionFactory#extractList(java.util.Map)}
	 * .
	 */
	@Test
	public void testExtractListWithSingleObjectForValue() {
		final Map<String, Object> columnsMap = new HashMap<String, Object>();
		columnsMap.put(COLUMN_NAME, STRING_VALUE);

		final List<IColumnCondition> conditionList = ConditionFactory
				.extractList(columnsMap);

		Assert.assertEquals(STRING_VALUE, conditionList.get(0).getValue());
	}
}
