package com.bankcomm.novem.comm;

import lombok.Data;

/**
 * @author 杨超 <ychao@bankcomm.com> 交通银行
 * 
 *         2009-2-5
 */
@Data
public class PageCond {
	private static final int DEFAULT_COUNT = 0;
	private static final int DEFAULT_LIMIT = 10;
	private static final int DEFAULT_START = 0;

	/**
	 * 表示查询结果的总条数。缺省统计。
	 */
	private int COUNT;

	/**
	 * = START + LIMIT
	 */
	private int END;

	/**
	 * 表示每页显示数据的条数。缺省为“10”。
	 */
	private int LIMIT;

	/**
	 * 表示当前页中的数据在结果集中的起始位置。缺省为"0"。
	 */
	private int START;

	/**
	 * 默认设置，START=0,LIMIT=10,END=10
	 */
	public PageCond() {
		super();
		START = DEFAULT_START;
		LIMIT = DEFAULT_LIMIT;
		COUNT = DEFAULT_COUNT;
		END = START + LIMIT;
	}

	/**
	 * @param START
	 *            {@link #START}
	 * @param LIMIT
	 *            {@link #LIMIT}
	 */
	public PageCond(final int START, final int LIMIT) {
		super();
		this.START = START;
		this.LIMIT = LIMIT;
		this.END = START + LIMIT;
	}

	/**
	 * @param LIMIT
	 *            {@link #LIMIT}
	 */
	public void setLIMIT(final int LIMIT) {
		this.LIMIT = LIMIT;
		this.setEND(START + LIMIT);
	}

	/**
	 * @param START
	 *            {@link #START}
	 */
	public void setSTART(final int START) {
		this.START = START;
		this.setEND(START + LIMIT);
	}
}