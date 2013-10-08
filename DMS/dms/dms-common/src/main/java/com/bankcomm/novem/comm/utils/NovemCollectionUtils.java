package com.bankcomm.novem.comm.utils;

import java.util.List;

import org.springframework.util.Assert;

import com.bankcomm.novem.comm.PageCond;

/**
 * @author 张国明 zhanggm@rionsoft.com 砾阳 2010-12-8 下午05:43:23
 * 
 */
public final class NovemCollectionUtils {

	/**
	 * 取出一定区间的子List，一般内存分页可以使用<Br>
	 * 输入列表的始终保持不改变
	 * 
	 * @param <T>
	 *            模板参数
	 * 
	 * @param inputList
	 *            输入列表
	 * @param pageCond
	 *            分页条件
	 * @return 分页后的列表
	 */
	public static <T> List<T> pageList(final List<T> inputList,
			final PageCond pageCond) {
		Assert.notEmpty(inputList, "输入列表不允许为空");
		if (pageCond == null) {
			return inputList;
		}

		final int size = inputList.size();
		// 保存总数
		pageCond.setCOUNT(size);

		int end = (pageCond.getSTART() - 1) + pageCond.getLIMIT();
		end = Math.max(end, pageCond.getLIMIT());

		int endIndex = Math.min(end, inputList.size());
		endIndex = Math.max(0, endIndex);

		int startIndex = Math.max(0, pageCond.getSTART() - 1);
		startIndex = Math.min(startIndex, inputList.size());

		return inputList.subList(startIndex, endIndex);
	}

	/**  */
	private NovemCollectionUtils() {
		super();
	}

}
