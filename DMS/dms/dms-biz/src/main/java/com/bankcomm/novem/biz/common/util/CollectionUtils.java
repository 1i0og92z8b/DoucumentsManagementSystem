/**
 * 
 */
package com.bankcomm.novem.biz.common.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

import com.bankcomm.novem.comm.PageCond;

/**
 * @author 任兆琨<renzk@sundatasoft.com> 舜德Dec 3, 2012
 * 
 */
public class CollectionUtils {

	/**
	 * 取出一定区间的子List，一般内存分页可以使用 输入列表的始终保持不改变
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

		int endIndex = Math.min(pageCond.getEND() - 1, inputList.size());
		endIndex = Math.max(0, endIndex);

		int startIndex = Math.max(0, pageCond.getSTART() - 1);
		startIndex = Math.min(startIndex, inputList.size());

		List<T> resultList = new ArrayList<T>();
		if (endIndex == startIndex) {
			resultList.add(inputList.get(startIndex - 1));
		} else {
			resultList = inputList.subList(startIndex, endIndex);
		}
		return resultList;
	}

}
