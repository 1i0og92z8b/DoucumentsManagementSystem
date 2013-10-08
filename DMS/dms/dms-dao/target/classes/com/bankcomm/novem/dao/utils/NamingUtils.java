/**
 * 
 */
package com.bankcomm.novem.dao.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

/**
 * @author 杨超 <ychao@bankcomm.com> 交通银行
 * 
 *         2009-4-22
 */
public final class NamingUtils {
	private static final Map<String, String> CAMEL_CACHE = new HashMap<String, String>();
	private static final Map<String, String> DBFORMAT_CACHE = new HashMap<String, String>();
	private static final String UNDER_LINE = "_";

	/**
	 * 将字符串从数据库形式转换到驼峰形式
	 * 
	 * @param dbFormat
	 *            驼峰字符串
	 * @return 对应的数据库字符串
	 */
	public static String transferToCamelFormat(final String dbFormat) {
		Assert.notNull(dbFormat, "不能处理空字符串");

		final String result = CAMEL_CACHE.get(dbFormat);
		if (result != null) {
			return result;
		}

		final String afterTransfer = assembleCamelFormat(dbFormat);

		if (afterTransfer != null) {
			CAMEL_CACHE.put(dbFormat, afterTransfer);
		}
		return afterTransfer;
	}

	/**
	 * 将驼峰风格字符串转化为数据库风格
	 * 
	 * @param camelFormat
	 *            驼峰风格的字符串
	 * @return 数据库风格
	 */
	public static String transferToDbFormat(final String camelFormat) {
		Assert.notNull(camelFormat, "不能处理空字符串");

		final String result = DBFORMAT_CACHE.get(camelFormat);
		if (result != null) {
			return result;
		}

		final String afterTransfer = assembleDbFormat(camelFormat);

		if (afterTransfer != null) {
			DBFORMAT_CACHE.put(camelFormat, afterTransfer);
		}
		return afterTransfer;
	}

	/**
	 * @param dbFormat
	 * @return
	 */
	private static String assembleCamelFormat(final String dbFormat) {
		final StringBuffer camelFormatBuffer = new StringBuffer();
		for (final String word : StringUtils.split(dbFormat, UNDER_LINE)) {
			if (StringUtils.isEmpty(word)) {
				continue;
			}
			final String lowerWord = word.toLowerCase();
			if (StringUtils.isEmpty(camelFormatBuffer)) {
				camelFormatBuffer.append(lowerWord);
			} else {
				camelFormatBuffer.append(lowerWord.substring(0, 1)
						.toUpperCase());
				camelFormatBuffer.append(lowerWord.substring(1));
			}
		}

		return camelFormatBuffer.toString();
	}

	/**
	 * @param camelFormat
	 * @return
	 */
	private static String assembleDbFormat(final String camelFormat) {
		final StringBuffer dbFormatBuffer = new StringBuffer();

		for (int i = 0; i < camelFormat.length(); i++) {
			final char c = camelFormat.charAt(i);
			if (i > 0) {
				final boolean isUpperCase = (c >= 'A') && (c <= 'Z');
				if (isUpperCase) {
					dbFormatBuffer.append(UNDER_LINE);
				}
			}
			dbFormatBuffer.append(c);
		}
		return dbFormatBuffer.toString().toUpperCase();
	}

	/**  */
	private NamingUtils() {
		super();
	}

}
