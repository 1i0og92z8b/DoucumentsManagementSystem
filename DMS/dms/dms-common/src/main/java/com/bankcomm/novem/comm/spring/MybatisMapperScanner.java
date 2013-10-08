/**
 * 
 */
package com.bankcomm.novem.comm.spring;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import lombok.SneakyThrows;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.stereotype.Repository;

/**
 * 提取Mybatis下所有的mapper接口类，参考了
 * {@link org.mybatis.spring.mapper.ClassPathMapperScanner}
 * 
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2013-3-16
 * 
 */
public final class MybatisMapperScanner extends
		ClassPathScanningCandidateComponentProvider {
	{
		addIncludeFilter(new AnnotationTypeFilter(Repository.class));
		// exclude package-info.java
		addExcludeFilter(new TypeFilter() {
			@Override
			public boolean match(final MetadataReader metadataReader,
					final MetadataReaderFactory metadataReaderFactory)
					throws IOException {
				final String className = metadataReader.getClassMetadata()
						.getClassName();
				return className.endsWith("package-info");
			}
		});
	}

	/** * */
	public MybatisMapperScanner() {
		super(false);
	}

	/**
	 *  搜索所有的mybatis mapper
	 * 
	 * @param packageName
	 *            包名，如"com.bankcomm.novem.dao"
	 * 
	 * @return 所有的mapper清单
	 */
	@SneakyThrows(ClassNotFoundException.class)
	public Set<Class<Object>> scanMapper(final String packageName) {
		final Set<BeanDefinition> components = findCandidateComponents(packageName);

		final Set<Class<Object>> mybatisMapperList = new HashSet<Class<Object>>();
		for (final BeanDefinition component : components) {
			@SuppressWarnings("unchecked")
			final Class<Object> cls = (Class<Object>) Class.forName(component
					.getBeanClassName());
			mybatisMapperList.add(cls);
		}
		return mybatisMapperList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.context.annotation.
	 * ClassPathScanningCandidateComponentProvider
	 * #isCandidateComponent(org.springframework
	 * .beans.factory.annotation.AnnotatedBeanDefinition)
	 */
	@Override
	protected boolean isCandidateComponent(
			final AnnotatedBeanDefinition beanDefinition) {
		return (beanDefinition.getMetadata().isInterface() && beanDefinition
				.getMetadata().isIndependent());
	}
}