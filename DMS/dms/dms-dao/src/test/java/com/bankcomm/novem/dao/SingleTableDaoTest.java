/**
 * 
 */
package com.bankcomm.novem.dao;

import org.junit.Assert;
import org.junit.Test;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-9-28
 * @param <T>
 *            单表测试中的测试实体类型
 * 
 */
public abstract class SingleTableDaoTest<T extends BaseEntry> extends
		RawDaoTest<T> {

	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithSamePrimaryKey() {
		final T entry = createMinEntry();
		final long firstID = insert(entry);

		final T secondEntry = createMinEntry();
		secondEntry.setPrimaryKey(firstID);
		final long secondID = insert(secondEntry);

		Assert.assertTrue(secondID > firstID);
	}
}
