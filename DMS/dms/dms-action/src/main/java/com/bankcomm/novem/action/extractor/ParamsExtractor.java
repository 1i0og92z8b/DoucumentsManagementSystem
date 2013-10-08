/**
 * 
 */
package com.bankcomm.novem.action.extractor;

import java.util.Map;

import com.bocom.jump.bp.core.Context;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-2-18
 * 
 */
public final class ParamsExtractor {
	/**
	 * 得到从前台传来的参数
	 * 
	 * @param context
	 *            jump上下文
	 * @param key
	 *            关键字
	 * 
	 * @param <E>
	 *            类型
	 * 
	 * @return 列表
	 */
	public static <E> E extractValueByParams(final Context context,
			final String key) {
		final Map<String, Object> paramsMap = context.getData("PARAMS");
		@SuppressWarnings("unchecked")
		final E result = (E) paramsMap.get(key);
		return result;
	}

	/** */
	private ParamsExtractor() {
		super();
	}
}
