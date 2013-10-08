package com.bankcomm.novem.bo.date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义时间戳类型
 * 
 * @author 张俊博<zhangjb@rionsoft.com> 砾阳 2010-12-9
 */
public class RiskTimestamp extends java.sql.Timestamp {

	private static final long serialVersionUID = 5317911736985496523L;

	private final SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	/**
	 * 带参数时间戳构造器
	 * 
	 * @param date
	 *            毫秒数
	 */
	public RiskTimestamp(final Date date) {
		super(date.getTime());
	}

	/**
	 * 带参构造器-把给定的日期格式化为期望的风格
	 * 
	 * @param time
	 *            Date型日期
	 */
	public RiskTimestamp(final java.sql.Timestamp time) {
		super(time.getTime());
	}

	/**
	 * 带参数时间戳构造器
	 * 
	 * @param time
	 *            毫秒数
	 */
	public RiskTimestamp(final long time) {
		super(time);
	}

	/**
	 * 自定义时间戳格式化样式
	 * 
	 * @param pattern
	 *            时间戳样式字符串
	 *            <ul>
	 *            <li><code>yyyy-MM-dd HH:mm:ss</code></li>
	 *            <li><code>yyyy-MM-dd HH:mm</code></li>
	 *            <li><code>yyyy-MM-dd HH:</code></li>
	 *            </ul>
	 */
	public void setPattern(final String pattern) {
		this.dateFormat.applyPattern(pattern);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.sql.Timestamp#toString()
	 */
	@Override
	public String toString() {
		return dateFormat.format(this);
	}
}