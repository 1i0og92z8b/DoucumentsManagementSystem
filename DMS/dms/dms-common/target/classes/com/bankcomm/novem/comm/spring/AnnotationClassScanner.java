/**
 * 
 */
package com.bankcomm.novem.comm.spring;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 注解类扫描
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-16
 * 
 */
public final class AnnotationClassScanner {
	/**
	 * 获取在指定包下使用了指定注解的class集合
	 * 
	 * @param annotationClass
	 *            注解类
	 * @param packagePath
	 *            指定包，格式如"com/bankcomm/risk"
	 * @return 所有加了该类注解的clss
	 */
	@SneakyThrows(ClassNotFoundException.class)
	public static <E> List<Class<E>> scan(
			final Class<? extends Annotation> annotationClass,
			final String packagePath) {
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		provider.addIncludeFilter(new AnnotationTypeFilter(annotationClass));
		final Set<BeanDefinition> components = provider
				.findCandidateComponents(packagePath);
		final List<Class<E>> subClasses = new ArrayList<Class<E>>();
		for (final BeanDefinition component : components) {
			@SuppressWarnings("unchecked")
			final Class<E> cls = (Class<E>) Class.forName(component
					.getBeanClassName());
			subClasses.add(cls);
		}
		return subClasses;
	}

	/**  */
	private AnnotationClassScanner() {
		super();
	}
}
