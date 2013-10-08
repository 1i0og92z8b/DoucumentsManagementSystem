/**
 * 
 */
package com.bankcomm.novem.dao.utils;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.dozer.converters.DateConverter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.bankcomm.novem.dao.BaseDaoTest;

/**
 * @author 张国明 zhanggm@rionsoft.com 砾阳 2011-2-21 下午04:13:43
 * 
 */
public class DBTransferTest extends BaseDaoTest<DBTransfer> {
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private Converter dateConverter;

	@Autowired
	private DBTransfer dbTransfer;

	/**
	 * Remove all registered Converters, and re-establish the standard
	 * Converters.
	 */
	@Before
	public void deregisterConverters() {
		dateConverter = ConvertUtils.lookup(java.util.Date.class);
		ConvertUtils.deregister(java.util.Date.class);
	}

	/**
	 * Register a custom Converter for the specified destination Class,
	 * replacing any previously registered Converter.
	 */
	@After
	public void registerConverters() {
		ConvertUtils.register(dateConverter, java.util.Date.class);
	}

	/**
	 * Test method for {@link DBTransfer#transferToEntry(java.util.Map, Class)}
	 * 
	 * @throws InvocationTargetException
	 *             异常
	 * @throws IllegalAccessException
	 *             异常
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDateForNotRegisterDateConverter()
			throws IllegalAccessException, InvocationTargetException {
		final SampleEntry result = new SampleEntry();

		BeanUtils.setProperty(result, "sampleDate", "2010-07-06");
	}

	/**
	 * Test method for {@link DBTransfer#transferToEntry(java.util.Map, Class)}
	 * 
	 * @throws InvocationTargetException
	 *             异常
	 * @throws IllegalAccessException
	 *             异常
	 */
	@Test
	public void testDateForRegisterDateConverter()
			throws IllegalAccessException, InvocationTargetException {
		ConvertUtils.register(new DateConverter(DATE_FORMAT),
				java.util.Date.class);

		final SampleEntry result = new SampleEntry();
		BeanUtils.setProperty(result, "sampleDate", "2010-07-06");
	}


	/**
	 * Test method for {@link DBTransfer#transferToEntry(java.util.Map, Class)}
	 * 
	 * @throws InvocationTargetException
	 *             异常
	 * @throws IllegalAccessException
	 *             异常
	 */
	@Test
	public void testString() throws IllegalAccessException,
	InvocationTargetException {
		final SampleEntry result = new SampleEntry();
		BeanUtils.setProperty(result, "sampleString", "2010-07-06");
	}
}
