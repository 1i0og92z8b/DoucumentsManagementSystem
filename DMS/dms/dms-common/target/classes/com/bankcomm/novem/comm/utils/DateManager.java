package com.bankcomm.novem.comm.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

/**
 * 日期类型数据处理
 * 
 * @author song.yp <arms_admin@bankcomm.com> 金高 2011-11-16
 * 
 */
public final class DateManager {
	private static final Map<Integer, Integer> MONTH_MAP = new HashMap<Integer, Integer>();
	private static final int MONTHS_PER_SEASON = 3;
	private static final int SEASON_ADJUST = 1;

	static {
		MONTH_MAP.put(Calendar.JANUARY, Calendar.MARCH);
		MONTH_MAP.put(Calendar.FEBRUARY, Calendar.MARCH);
		MONTH_MAP.put(Calendar.MARCH, Calendar.MARCH);

		MONTH_MAP.put(Calendar.APRIL, Calendar.JUNE);
		MONTH_MAP.put(Calendar.MAY, Calendar.JUNE);
		MONTH_MAP.put(Calendar.JUNE, Calendar.JUNE);

		MONTH_MAP.put(Calendar.JULY, Calendar.SEPTEMBER);
		MONTH_MAP.put(Calendar.AUGUST, Calendar.SEPTEMBER);
		MONTH_MAP.put(Calendar.SEPTEMBER, Calendar.SEPTEMBER);

		MONTH_MAP.put(Calendar.OCTOBER, Calendar.DECEMBER);
		MONTH_MAP.put(Calendar.NOVEMBER, Calendar.DECEMBER);
		MONTH_MAP.put(Calendar.DECEMBER, Calendar.DECEMBER);
	}

	/**
	 * 判断日期是否为月初第一天
	 * 
	 * @param date
	 *            要处理的日期
	 * @return boolean
	 */
	public static boolean checkFirstDayOfMonth(final Date date) {
		Assert.notNull(date, "处理日期不能为空");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_MONTH) == 1;
	}

	/**
	 * 获取偏移后的日期
	 * 
	 * @param manageDate
	 *            要处理的日期
	 * @param excursionDays
	 *            偏移天数
	 * @return 季度日期
	 */
	public static Date getExcursionDate(final Date manageDate,
			final int excursionDays) {
		Assert.notNull(manageDate, "处理日期不能为空");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(manageDate);
		calendar.add(Calendar.DATE, excursionDays);
		return calendar.getTime();
	}

	/**
	 * 获取上月最后一天
	 * 
	 * @param date
	 *            要处理的日期
	 * @return 上月最后一天日期
	 */
	public static Date getLastDayOfLastMonth(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		return calendar.getTime();
	}

	/**
	 * 获取上季度年月
	 * 
	 * @param manageDate
	 *            要处理的日期
	 * @return 季度日期
	 */
	public static Date getLastSeason(final Date manageDate) {
		Assert.notNull(manageDate, "处理日期不能为空");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(manageDate);
		calendar.add(Calendar.MONTH, -MONTHS_PER_SEASON);

		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH,
				MONTH_MAP.get(calendar.get(Calendar.MONTH)));

		return calendar.getTime();
	}

	/**
	 * 获取所属季度
	 * 
	 * @param manageDate
	 *            要处理的日期
	 * @return 季度(S)
	 */
	public static String getSeason(final Date manageDate) {
		Assert.notNull(manageDate, "处理日期不能为空");
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(manageDate);
		final int season = (calendar.get(Calendar.MONTH) / MONTHS_PER_SEASON)
				+ SEASON_ADJUST;
		return String.valueOf(season);
	}

	/**
	 * 
	 */
	private DateManager() {
		super();
	}
}
