/**
 * 
 */
package com.bankcomm.novem.comm.spring;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-10-19
 * 
 */
@Service
@Lazy(false)
public class ManageSpringBeans implements ApplicationContextAware {
	private static ApplicationContext context;

	/**
	 * {@link ApplicationContext#getBean(Class)} 获取spring beans
	 * 
	 * @param <T>
	 *            T
	 * 
	 * @param requiredType
	 *            bean类型
	 * @return 对应的bean
	 * 
	 */
	public static <T> T getBean(final Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	/**
	 * {@link ApplicationContext#getBean(Class)} 获取spring beans
	 * 
	 * @param <T>
	 *            T
	 * 
	 * @param requiredType
	 *            bean类型
	 * @return 对应的bean
	 * 
	 */
	public static <T> Map<String, T> getBeans(final Class<T> requiredType) {
		return context.getBeansOfType(requiredType);
	}

	/**
	 * 从annotation提取bean清单
	 * 
	 * @param annotationType
	 *            annotation类型
	 * @return 标注了该annotation的beans
	 */
	public static Map<String, Object> getBeansWithAnnotation(
			final Class<? extends Annotation> annotationType) {
		return context.getBeansWithAnnotation(annotationType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.context.ApplicationContextAware#setApplicationContext
	 * (org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(
			final ApplicationContext applicationContext) {
		this.context = applicationContext;
	}
}
