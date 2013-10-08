package com.bankcomm.novem.biz.batch;

import org.junit.Assert;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

/**
 * @author 曹臣<caoc@rionsoft.com> 砾阳 2011-7-19
 * 
 */
@ContextConfiguration(locations = { "classpath:spring-test.xml" })
public abstract class BaseServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	/**
	 * 确认已有异常被抛出
	 */
	protected void checkExceptionRaise() {
		Assert.assertTrue("can't reach here, a exception should be raised",
				false);
	}
}
