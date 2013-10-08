/**
 * 
 */
package com.bankcomm.novem.comm.strategy;

/**
 * 策略接口
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2009-12-25
 * @param <C>
 *            策略条件
 * 
 */
public interface IStrategy<C> {
	/**
	 * 获得策略条件
	 * 
	 * @return 用来注册的策略处理条件
	 */
	C getCondition();
}
