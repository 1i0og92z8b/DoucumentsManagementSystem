/**
 * 
 */
package com.bankcomm.novem.dao.condition.column;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notEmpty;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

import com.bankcomm.novem.dao.utils.DBTransfer;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-16
 * 
 */
public final class ConditionFactory {
	/**
	 * 抽取条件列表
	 * 
	 * @param columnsMap
	 *            列名和值映射
	 * @return 对应的实体列表
	 */
	public static List<IColumnCondition> extractList(
			final Map<String, Object> columnsMap) {
		notEmpty(columnsMap, "待解析的数据不能为空");
		isTrue(!columnsMap.containsKey(null), "待解析的集合内不能包含空键值");

		final List<IColumnCondition> columnsList = new ArrayList<IColumnCondition>();
		for (final Entry<String, Object> entry : columnsMap.entrySet()) {
			final IColumnCondition condition = createCondition(entry);

			if (condition != null) {
				columnsList.add(condition);
			}
		}
		notEmpty(columnsList, "待解析的数据不能为空");
		return columnsList;
	}

	/**
	 * @param valueList
	 * @return
	 */
	private static Collection<?> assembleSqlValue(final Collection<?> valueList) {
		Assert.notEmpty(valueList, "不能处理空的值列表");
		final ArrayList<Object> resultList = new ArrayList<Object>();

		for (final Object value : valueList) {
			resultList.add(DBTransfer.assembleColumnValue(value));
		}

		return resultList;
	}

	/**
	 * @param entry
	 * @return
	 */
	private static IColumnCondition createCondition(
			final Entry<String, Object> entry) {
		if (entry.getValue() == null) {
			return new NullCondition(entry.getKey());
		} else if (entry.getValue() instanceof Collection<?>) {
			final Collection<?> values = (Collection<?>) entry.getValue();
			notEmpty(values, "放在in里的条件不允许为空");
			return new InCondition(entry.getKey(), assembleSqlValue(values));
		} else {
			return new EqualsCondition(entry.getKey(),
					DBTransfer.assembleColumnValue(entry.getValue()));
		}
	}

	/** */
	private ConditionFactory() {
		super();
	}
}
