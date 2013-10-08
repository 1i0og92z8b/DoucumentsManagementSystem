package com.bankcomm.novem.comm.strategy;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.Assert;

import com.bankcomm.novem.comm.spring.ManageSpringBeans;
import com.bankcomm.novem.comm.utils.GenericExtractor;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2009-12-25
 * @param <C>
 *            条件类型
 * @param <S>
 *            策略类型
 */
@Slf4j
public abstract class StrategyContainerImpl<C, S extends IStrategy<C>>
		implements IStrategyContainer<C, S> {
	private final Map<C, S> strategyMap = new HashMap<C, S>();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bankcomm.icms.biz.strategy.IStrategyContainer#getStrategy(C)
	 */
	@Override
	public S getStrategy(final C condition) {
		if (!strategyMap.containsKey(condition)) {
			initStrategyBean();
		}
		return strategyMap.get(condition);
	}

	/**
	 * @return the strategyMap
	 */
	public Map<C, S> getStrategyMap() {
		return strategyMap;
	}

	/**
	 * 从spring context中直接提取对应的策略处理器，用以取代手工的registStrategy
	 * 
	 * 只能应用于从StrategyContainerImpl继承的容器类，无法直接应用于StrategyContainerImpl对象
	 */
	protected synchronized void initStrategyBean() {
		if (strategyMap.size() > 0) {
			log.trace(this.getClass().getName() + "has already been initialed");
			return;
		}
		Assert.isTrue(this.getClass() != StrategyContainerImpl.class,
				"该方法无法直接应用于StrategyContainerImpl对象");

		@SuppressWarnings("unchecked")
		final Class<S> strategyClass = (Class<S>) GenericExtractor.getClass(
				this.getClass().getGenericSuperclass(), 1);
		Assert.isTrue(IStrategy.class.isAssignableFrom(strategyClass));

		@SuppressWarnings("unchecked")
		final Class<S> conditionClass = (Class<S>) GenericExtractor.getClass(
				this.getClass().getGenericSuperclass(), 0);
		final Collection<S> strategys = ManageSpringBeans.getBeans(
				strategyClass).values();
		log.info(conditionClass.getName() + "[C] has " + strategys.size() + " "
				+ strategyClass.getName() + "[S]");

		for (final S s : strategys) {
			log.debug("[" + s.getCondition() + "]" + "[" + s + "]");
			Assert.isTrue(!strategyMap.containsKey(s.getCondition()),
					"该类型已被注册过[" + s.getCondition() + "][" + s + "]");
			strategyMap.put(s.getCondition(), s);
		}
	}
}