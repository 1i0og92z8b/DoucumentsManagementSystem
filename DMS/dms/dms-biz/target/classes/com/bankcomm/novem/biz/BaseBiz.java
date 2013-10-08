package com.bankcomm.novem.biz;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.bankcomm.novem.bo.BaseBo;
import com.bankcomm.novem.entry.BaseEntry;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-26
 * 
 */
public abstract class BaseBiz {
	/***/
	@Autowired
	private Mapper mapper;

	/**
	 * 映射数组
	 * 
	 * @param <T>
	 *            目标数据类型
	 * @param <S>
	 *            源数据类型
	 * @param sourceList
	 *            源数据列表
	 * @param targetInfo
	 *            目标数据类型信息
	 * @return 映射后的目标数据列表
	 */
	protected <T extends BaseBo, S extends BaseEntry> List<T> map(
			final List<S> sourceList, final Class<T> targetInfo) {
		Assert.notNull(sourceList, "不能使用空数据源进行映射");

		final List<T> targetList = new ArrayList<T>();
		for (final S entry : sourceList) {
			targetList.add(mapper.map(entry, targetInfo));
		}
		return targetList;
	}

	/**
	 * 将对象实体转换为业务对象
	 * 
	 * @param <T>
	 *            业务对象类型
	 * @param <S>
	 *            对象实体类型
	 * @param sourceEntry
	 *            实体对象
	 * @param targetBOClass
	 *            业务对象类
	 * @return 映射后的业务对象
	 */
	protected <T extends BaseBo, S extends BaseEntry> T map(
			final S sourceEntry, final Class<T> targetBOClass) {
		Assert.notNull(sourceEntry, "映射的实体对象不可为空");
		return mapper.map(sourceEntry, targetBOClass);
	}

	/**
	 * 将业务对象转换为对象实体
	 * 
	 * @param <T>
	 *            业务对象类型
	 * @param <S>
	 *            对象实体类型
	 * @param sourceBO
	 *            业务对象
	 * @param targetEntryClass
	 *            实体对象类
	 * @return 映射后的实体对象
	 */
	protected <T extends BaseBo, S extends BaseEntry> S map(final T sourceBO,
			final Class<S> targetEntryClass) {
		Assert.notNull(sourceBO, "映射的业务对象不可为空");
		return mapper.map(sourceBO, targetEntryClass);
	}

}
