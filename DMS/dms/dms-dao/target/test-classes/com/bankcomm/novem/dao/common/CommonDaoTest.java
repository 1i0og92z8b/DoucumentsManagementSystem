package com.bankcomm.novem.dao.common;

import java.sql.Date;
import java.sql.Timestamp;

import org.junit.Assert;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-3-30
 * 
 * 
 *         继承当前测试类为了解决测试用例testQueryCurrentTimeWithCompare中查询出来的时间全部相同的问题(查询只执行一次
 *         )
 */
@ContextConfiguration(locations = { "classpath*:/config/jump-test.xml" })
public class CommonDaoTest extends AbstractJUnit4SpringContextTests {
	@Autowired
	private CommonDao commonDao;

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.common.CommonDao#queryCurrentDate()}.
	 */
	@Test
	public void testQueryCurrentDate() {
		final Date today = commonDao.queryCurrentDate();
		Assert.assertNotNull(today);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.common.CommonDao#queryCurrentDate()}.
	 */
	@Test
	public void testQueryCurrentDateWithCompare() {
		final Date today = commonDao.queryCurrentDate();
		final Date secondQuery = commonDao.queryCurrentDate();

		Assert.assertEquals(today, secondQuery);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.common.CommonDao#queryCurrentTime()}.
	 */
	@Test
	public void testQueryCurrentTime() {
		final Timestamp before = commonDao.queryCurrentTime();
		Assert.assertNotNull(before);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.common.CommonDao#queryCurrentTime()}.
	 */
	@Test
	public void testQueryCurrentTimeWithCompare() {
		for (int i = 0; i < 10; i++) {
			final Timestamp before = commonDao.queryCurrentTime();

			final Timestamp after = commonDao.queryCurrentTime();

			Assert.assertTrue(String.valueOf(i), before.before(after));
		}
	}
}