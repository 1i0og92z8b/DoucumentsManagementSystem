/**
 * 
 */
package com.bankcomm.novem.comm.spring;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.SneakyThrows;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;

/**
 * 子类扫描
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-16
 * 
 */
public final class SubCLassScanner {

	/**
	 * 获取在指定包下某个class的所有非抽象子类
	 * 
	 * @param parentClass
	 *            父类
	 * @param packagePath
	 *            指定包，格式如"com/bankcomm/risk"
	 * @return 该父类对应的所有子类列表
	 */
	@SneakyThrows(ClassNotFoundException.class)
	public static <E> List<Class<E>> scan(final Class<E> parentClass,
			final String packagePath) {
		final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
				false);
		provider.addIncludeFilter(new AssignableTypeFilter(parentClass));
		final Set<BeanDefinition> components = provider
				.findCandidateComponents(packagePath);
		final List<Class<E>> subClasses = new ArrayList<Class<E>>();
		for (final BeanDefinition component : components) {
			@SuppressWarnings("unchecked")
			final Class<E> cls = (Class<E>) Class.forName(component
					.getBeanClassName());
			if (Modifier.isAbstract(cls.getModifiers())) {
				continue;
			}
			subClasses.add(cls);
		}
		return subClasses;
	}

	/** * */
	private SubCLassScanner() {
		super();
	}
}
