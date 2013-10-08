package com.bankcomm.novem.action.extractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.bankcomm.novem.bo.BaseBo;
import com.bankcomm.novem.comm.PageCond;
import com.bankcomm.novem.comm.spring.ManageSpringBeans;
import com.bocom.jump.bp.core.Context;
import com.bocom.jump.bp.core.User;

/**
 * 上下文数据提取器
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-2-18
 * 
 */
@SuppressWarnings("all")
public final class ContextExtractor {

	/** dozer映射器 */
	private static Mapper mapper = ManageSpringBeans.getBean(Mapper.class);
	
	
	/**
	 * 直接从context上取值，如String, Integer，无需做强制转换，前提是知道实际类型
	 * 
	 * @param <T>
	 * @param context
	 * @param name
	 * @return
	 */
	public static <T> T extractValue(final Context context, final String name) {
		final Map<String, Object> datamap = context.getDataMap();
		final Object value = datamap.get(name);

		return (T) value;
	}
	
	public static <T> Collection<T> extractArray(final Context context, final String name,
			final Class<T> clazz) {
		final Map<String, Object> datamap = context.getDataMap();

		final Collection<T> mapList = (Collection<T>) datamap.get(name);

		final List<T> list = new ArrayList<T>();

		for (final T value : mapList) {
			list.add(value);
		}

		return list;
	}
	
	/**
	 * 
	 * 将Context中Map转换为JavaBean<br/>
	 * 
	 * @param context
	 * @param key
	 * @param targetInfo
	 * @return
	 */
	public static <T> T extractBean(final Context context, final String key, final Class<T> targetInfo) {
		
		final Map<String, String> source = context.getData(key);
		if (source == null) {
			return null;
		}
		final T target = mapper.map(source, targetInfo);

		return target;
	}

	/**
	 * 从jump context得到bo对象列表
	 * 
	 * @param <T>
	 *            bo类型
	 * @param context
	 *            jump上下文
	 * @param key
	 *            参数key
	 * @param targetInfo
	 *            目标bo类型
	 * @return 目标bo类型对象列表
	 */
	public static <T extends BaseBo> T extractBo(final Context context,
			final String key, final Class<T> targetInfo) {
		final Map<String, String> source = context.getData(key);
		if (source == null) {
			return null;
		}

		final T target = mapper.map(source, targetInfo);
		final User user = context.getUser();
		if (user == null) {
			target.setModifyUser(0);
		} else {
			target.setModifyUser(Long.parseLong(user.getUserId()));
		}
		return target;
	}

	/**
	 * 从jump context得到bo对象列表
	 * 
	 * @param <T>
	 *            bo类型
	 * @param context
	 *            contextMap对象
	 * @param key
	 *            参数key
	 * @param targetClass
	 *            目标bo类型
	 * @return 目标bo类型对象列表
	 */
	public static <T extends BaseBo> List<T> extractBoList(
			final Context context, final String key, final Class<T> targetClass) {
		final List<Map<String, Object>> sourceList = context.getData(key);
		Assert.notNull(sourceList, "数据不能为空");
		final List<T> targetList = new ArrayList<T>(sourceList.size());
		for (final Map<String, Object> source : sourceList) {
			final T target = mapper.map(source, targetClass);
			final User user = context.getUser();
			if (user == null) {
				target.setModifyUser(0);
			} else {
				target.setModifyUser(Long.parseLong(user.getUserId()));
			}
			targetList.add(target);
		}
		return targetList;
	}

	/**
	 * 从jump context得到bo对象列表
	 * 
	 * @param <T>
	 *            condition类型
	 * @param context
	 *            jump上下文
	 * @param key
	 *            参数key
	 * @param targetInfo
	 *            目标conditionMap类型
	 * @return 目标condition类型对象列表
	 */
	public static <T> T extractCondition(final Context context,
			final String key, final Class<T> targetInfo) {
		final Map<String, String> source = context.getData(key);
		if (source == null) {
			return null;
		}

		return mapper.map(source, targetInfo);
	}

	/**
	 * 获取long型列表
	 * 
	 * @param context
	 *            上下文
	 * @param name
	 *            数据名称
	 * @return 对应的long型列表
	 */
	public static List<Long> extractLongList(final Context context,
			final String name) {
		final List<Object> idStrings = context.getData(name);
		if (CollectionUtils.isEmpty(idStrings)) {
			return new ArrayList<Long>();
		}
		final List<Long> idList = new ArrayList<Long>();
		for (final Object idInString : idStrings) {
			if (StringUtils.hasText(idInString.toString())) {
				idList.add(Long.valueOf(idInString.toString()));
			}
		}
		return idList;
	}

	/**
	 * 提取long类型数值
	 * 
	 * @param context
	 *            数据上下文
	 * @param name
	 *            属性名称
	 * 
	 * @return 对应该属性名称的long值
	 */
	public static Long extractLongValue(final Context context, final String name) {
		return context.getData(name) == null ? null : Long.valueOf(context
				.getData(name).toString());
	}

	/**
	 * 从contextMap中得到分页对象
	 * 
	 * @param context
	 *            jump上下文
	 * @return 分页对象
	 */
	public static PageCond extractPageCond(final Context context) {
		Assert.notNull(context.getData("PAGE_COND"), "无法获取分页条件");
		return mapper.map(context.getData("PAGE_COND"), PageCond.class);
	}

	/**
	 * 获取流水号
	 * 
	 * @param context
	 *            上下文
	 * @return 流水号
	 */
	public static String extractTraceNo(final Context context) {
		final Map<String, Object> requestInformation = context
				.getData("requestInformation");
		if (CollectionUtils.isEmpty(requestInformation)) {
			return null;
		}
		return (String) requestInformation.get("traceNo");
	}

	/**
	 * 从jump context得到Array列表
	 * 
	 * @param <T>
	 *            条件
	 * 
	 * @param context
	 *            contextMap对象
	 * @param name
	 *            列表字符串
	 * @param clazz
	 *            类型
	 * @return list类型对象列表
	 */
	public static <T> Collection<T> fetchArray(final Context context,
			final String name, final Class<T> clazz) {
		final Map<String, Object> datamap = context.getDataMap();

		@SuppressWarnings("unchecked")
		final Collection<T> mapList = (Collection<T>) datamap.get(name);

		final List<T> list = new ArrayList<T>();

		for (final T value : mapList) {
			list.add(value);
		}

		return list;
	}

	/**  */
	private ContextExtractor() {
		super();
	}
}