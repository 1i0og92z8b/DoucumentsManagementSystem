package com.bankcomm.novem.dao;

import static org.junit.Assert.assertTrue;
import lombok.extern.slf4j.Slf4j;

import org.junit.Test;
import org.springframework.dao.DuplicateKeyException;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * 单表主键不递增的测试用例
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-9-28
 * 
 * @param <T>
 *            操作的数据实体类型
 * 
 */
@Slf4j
public abstract class SingleTableWithOutIncrementTest<T extends BaseEntry>
		extends RawDaoTest<T> {
	/**
	 * Test method for {@link ISingleTableDao#insert(BaseEntry)}
	 */
	@Test
	public void testInsertWithSamePrimaryKey() {
		final T entry = createMinEntry();
		final long entryID = insert(entry);

		entry.setPrimaryKey(entryID);

		try {
			insert(entry);
			checkExceptionRaise();
		} catch (final DuplicateKeyException e) {
			log.info(e.getMessage());
			assertTrue(true);
		}
	}
}