package com.bankcomm.novem.dao.impl;

import java.util.Comparator;

import com.bankcomm.novem.entry.BaseEntry;

/**
 * 排序操作器，按照实体主键和版本号降序
 * 
 * @author 张国明 zhanggm@rionsoft.com 砾阳 2011-4-13 下午01:03:51
 * 
 */
public class HisEntryComparator implements Comparator<BaseEntry> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final BaseEntry o1, final BaseEntry o2) {
		if (o1.getPrimaryKey() == o2.getPrimaryKey()) {
			return o1.getVersion() - o2.getVersion();
		}
		return o1.getPrimaryKey() > o2.getPrimaryKey() ? 1 : -1;
	}
}
