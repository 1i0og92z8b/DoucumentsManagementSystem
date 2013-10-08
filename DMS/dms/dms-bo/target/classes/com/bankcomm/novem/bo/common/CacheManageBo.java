package com.bankcomm.novem.bo.common;

import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.bankcomm.novem.bo.BaseBo;

/**
 * @author 韩国栋 <han_gd@bankcomm.com> Jan 6, 2013
 * 
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CacheManageBo extends BaseBo {
	private List<? extends BaseBo> cacheItems;
	private boolean isRreshing = false;
	private Date nextRefreshTime;

	/**
	 * 
	 */
	public CacheManageBo() {
		super();
		cacheItems = null;
		nextRefreshTime = new Date(0);
	}

	/**
	 * @param cacheItems
	 *            要缓存的列表
	 * @param nextRefreshTime
	 *            下次刷新时间
	 */
	public CacheManageBo(final List<? extends BaseBo> cacheItems,
			final Date nextRefreshTime) {
		super();
		this.cacheItems = cacheItems;
		this.nextRefreshTime = nextRefreshTime;
	}

	/**
	 * @param newItems
	 *            要更新的Items
	 * @param newRefreshTime
	 *            下次要刷新的时间
	 */
	public void refreshItems(final List<? extends BaseBo> newItems,
			final Date newRefreshTime) {
		this.cacheItems = newItems;
		this.nextRefreshTime = newRefreshTime;
	}

	/**
	 * 判断是否需要刷新
	 * 
	 * @return true-需要刷新，false-不需要刷新
	 */
	public boolean shouldRefresh() {
		if (isRreshing) {
			return false;
		}
		return nextRefreshTime.before(new Date());
	}
}
