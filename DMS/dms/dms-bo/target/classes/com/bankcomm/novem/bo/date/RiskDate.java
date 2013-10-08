package com.bankcomm.novem.bo.date;

import java.text.SimpleDateFormat;

/**
 * 自定义日期类型
 * 
 * @author 张俊博<zhangjb@rionsoft.com> 砾阳 2010-11-29
 * 
 */
public class RiskDate extends java.util.Date {
	private static final long serialVersionUID = -6837839725185188718L;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd");

	/**
	 * 默认构造器<br/>
	 * 日期格式:yyyy-MM-dd
	 */
	public RiskDate() {
		super();
	}

	/**
	 * 带参构造器-把给定的日期格式化为期望的风格
	 * 
	 * @param date
	 *            Date型日期
	 */
	public RiskDate(final java.util.Date date) {
		super(date.getTime());
	}

	/**
	 * @param date
	 *            the milliseconds since January 1, 1970, 00:00:00 GMT.
	 */
	public RiskDate(final long date) {
		super(date);
	}

	/**
	 * 带参构造器-自定义日期格式化样式
	 * 
	 * @param pattern
	 *            日期样式字符串
	 *            <ul>
	 *            <li><code>yyyy-MM-dd</code></li>
	 *            <li><code>yyyy-MM</code></li>
	 *            <li><code>yyyy</code></li>
	 *            </ul>
	 */
	public RiskDate(final String pattern) {
		super();
		this.dateFormat.applyPattern(pattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Date#toString()
	 */
	@Override
	public String toString() {
		return dateFormat.format(this);
	}
}