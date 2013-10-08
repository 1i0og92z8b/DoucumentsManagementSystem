/**
 * 
 */
package com.bankcomm.novem.dao.utils;

import org.junit.Assert;

import org.junit.Test;

import com.bankcomm.novem.dao.utils.NamingUtils;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-8
 * 
 */
public class NamingUtilsTest {

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormat() {
		final String dbFormat = "TEST_STRING";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("testString", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithEmpty() {
		final String dbFormat = "";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithNull() {
		try {
			NamingUtils.transferToCamelFormat(null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("不能处理空字符串", e.getMessage());
		}
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithOneLetter() {
		final String dbFormat = "T";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("t", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithoutUnderLine() {
		final String dbFormat = "TESTSTRING";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("teststring", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithStartingUnderLine() {
		final String dbFormat = "_TESTSTRING";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("teststring", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithTrailingUnderLine() {
		final String dbFormat = "TESTSTRING_";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("teststring", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithTwoJointUnderLine() {
		final String dbFormat = "TEST__STRING";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("testString", camelFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToCamelFormat(String)}
	 */
	@Test
	public void testTransferToCamelFormatWithUnderLineOnly() {
		final String dbFormat = "_";
		final String camelFormat = NamingUtils.transferToCamelFormat(dbFormat);

		Assert.assertEquals("", camelFormat);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NamingUtils#transferToDbFormat(java.lang.String)}
	 * .
	 */
	@Test
	public void testTransferToDbFormat() {
		final String camelFormat = "TestString";

		final String dbFormat = NamingUtils.transferToDbFormat(camelFormat);

		Assert.assertEquals("Test_String".toUpperCase(), dbFormat);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NamingUtils#transferToDbFormat(java.lang.String)}
	 * .
	 */
	@Test
	public void testTransferToDbFormatWithLowerCaseStart() {
		final String camelFormat = "testString";

		final String dbFormat = NamingUtils.transferToDbFormat(camelFormat);

		Assert.assertEquals("test_String".toUpperCase(), dbFormat);
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NamingUtils#transferToDbFormat(java.lang.String)}
	 * .
	 */
	@Test
	public void testTransferToDbFormatWithLowerCaseWord() {
		final String camelFormat = "test";

		final String dbFormat = NamingUtils.transferToDbFormat(camelFormat);

		Assert.assertEquals("Test".toUpperCase(), dbFormat);
	}

	/**
	 * Test method for {@link NamingUtils#transferToDbFormat(String)}
	 */
	@Test
	public void testTransferToDbFormatWithNull() {
		try {
			NamingUtils.transferToDbFormat(null);
		} catch (final IllegalArgumentException e) {
			Assert.assertEquals("不能处理空字符串", e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NamingUtils#transferToDbFormat(java.lang.String)}
	 * .
	 */
	@Test
	public void testTransferToDbFormatWithOneWord() {
		final String camelFormat = "Test";

		final String dbFormat = NamingUtils.transferToDbFormat(camelFormat);

		Assert.assertEquals("Test".toUpperCase(), dbFormat);
	}

}
