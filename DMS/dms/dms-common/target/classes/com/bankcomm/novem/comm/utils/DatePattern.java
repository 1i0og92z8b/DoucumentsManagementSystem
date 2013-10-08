/**
 * 
 */
package com.bankcomm.novem.comm.utils;

import static org.joda.time.DateTimeConstants.MILLIS_PER_SECOND;
import static org.joda.time.DateTimeConstants.SECONDS_PER_HOUR;
import static org.joda.time.DateTimeConstants.SECONDS_PER_MINUTE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期格式
 * 
 * @author 张俊博<zhangjb@rionsoft.com> 砾阳
 * @date 2011-5-6
 */
public enum DatePattern {
	/** 带时区的格式： <i>HH:mm:ss</i> */
	HHMMSS("HH:mm:ss"),
	/** 不带时区的时间格式： <i>HH:mm:ss</i> */
	TIME("HH:mm:ss") {

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.bankcomm.novem.comm.utils.DatePattern#format(java.util.Date)
		 */
		@Override
		public String format(final Date date) {
			return format(date.getTime());
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.bankcomm.novem.comm.utils.DatePattern#format(long)
		 */
		@Override
		public String format(final long timeInMillis) {
			final long seconds = timeInMillis / MILLIS_PER_SECOND;

			final long hour = seconds / SECONDS_PER_HOUR;
			final long minute = (seconds % SECONDS_PER_HOUR)
					/ SECONDS_PER_MINUTE;
			final long second = seconds % SECONDS_PER_MINUTE;

			return String.format("%1$02d:%2$02d:%3$02d", hour, minute, second);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.bankcomm.novem.comm.utils.DatePattern#parse(java.lang.String)
		 */
		@Override
		public Date parse(final String dateString) {
			throw new UnsupportedOperationException(this.name() + "不支持parse");
		}
	},
	/** 格式： <i>yyyy-MM-dd</i> */
	YYYY_MM_DD("yyyy-MM-dd"),
	/** 格式： <i>yyyy-MM-dd HH:mm:ss.SSS</i> */
	YYYY_MM_DD_HH_MM_SS_SSS("yyyy-MM-dd HH:mm:ss.SSS"),
	/** * 格式： <i>yyyyMM</i> */
	YYYYMM("yyyyMM"),
	/** 格式： <i>yyyyMMdd</i> */
	YYYYMMDD("yyyyMMdd");

	private final SimpleDateFormat dateFormat;
	private final String format;

	private DatePattern(final String pattern) {
		this.format = pattern;
		dateFormat = new SimpleDateFormat(format);
	}

	/**
	 * 格式化日期
	 * 
	 * @param date
	 *            日期
	 * @return 按照模式格式化出对应的字符串
	 */
	public String format(final Date date) {
		return dateFormat.format(date);
	}

	/**
	 * 格式化日期
	 * 
	 * @param timeInMillis
	 *            毫秒数
	 * @return 按照模式格式化出对应的字符串
	 */
	public String format(final long timeInMillis) {
		return dateFormat.format(new Date(timeInMillis));
	}

	/**
	 * 解析日期
	 * 
	 * @param dateString
	 *            日期字符串
	 * @return 完成解析的日期
	 */
	public Date parse(final String dateString) {
		try {
			return dateFormat.parse(dateString);
		} catch (final ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}
}