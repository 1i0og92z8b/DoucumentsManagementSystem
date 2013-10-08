/**
 * 
 */
package com.bankcomm.novem.dao;

import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashSet;
import java.util.Set;

import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.util.ReflectionTestUtils;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行
 * @param <T>
 *            class
 * 
 */
@ContextConfiguration(locations = { "classpath:/config/jump-test.xml" })
public abstract class BaseDaoTest<T> extends
		AbstractTransactionalJUnit4SpringContextTests {

	private static Class<?> getClass(final Type type, final int i) {
		if (type instanceof ParameterizedType) { // 处理泛型类型
			return getGenericClass((ParameterizedType) type, i);
		} else if (type instanceof TypeVariable) {
			return getClass(((TypeVariable<?>) type).getBounds()[0], 0); // 处理泛型擦拭对象
		} else {// class本身也是type，强制转型
			return (Class<?>) type;
		}
	}

	private static Class<?> getGenericClass(
			final ParameterizedType parameterizedType, final int i) {
		final Type genericClass = parameterizedType.getActualTypeArguments()[i];
		if (genericClass instanceof ParameterizedType) { // 处理多级泛型
			return (Class<?>) ((ParameterizedType) genericClass).getRawType();
		} else if (genericClass instanceof GenericArrayType) { // 处理数组泛型
			return (Class<?>) ((GenericArrayType) genericClass)
					.getGenericComponentType();
		} else if (genericClass instanceof TypeVariable) { // 处理泛型擦拭对象
			return getClass(((TypeVariable<?>) genericClass).getBounds()[0], 0);
		} else {
			return (Class<?>) genericClass;
		}
	}

	private final Set<MockField> mockFields = new HashSet<MockField>();

	/**
	 * 既可 mock 接口也可以 mock 类
	 */
	protected final Mockery context = new JUnit4Mockery() {
		{
			setImposteriser(ClassImposteriser.INSTANCE);
		}
	};

	/**
	 * 
	 */
	@After
	public void restoreField() {
		for (final MockField mockField : mockFields) {
			ReflectionTestUtils.setField(mockField.getToMock(),
					mockField.getFieldName(), mockField.getNativeValue());
		}
		context.assertIsSatisfied();
	}

	/**
	 * @param mockHost
	 * @param typeToMock
	 * @return
	 */
	private <E> String extractFieldName(final Object mockHost,
			final Class<E> typeToMock) {
		String fieldName = null;
		for (Class<?> mockHostClass = mockHost.getClass(); mockHostClass != Object.class; mockHostClass = mockHostClass
				.getSuperclass()) {
			fieldName = extractFieldNameFromClass(typeToMock, mockHostClass);
			if (fieldName != null) {
				break;
			}
		}
		Assert.assertNotNull("无法从mock宿主" + mockHost.getClass() + "中定位mock实体"
				+ typeToMock.getCanonicalName(), fieldName);
		return fieldName;
	}

	/**
	 * @param typeToMock
	 * @param mockHostClass
	 * @return
	 */
	private <E> String extractFieldNameFromClass(final Class<E> typeToMock,
			final Class<? extends Object> mockHostClass) {
		String fieldName = null;
		final Field[] fields = mockHostClass.getDeclaredFields();
		for (final Field field : fields) {
			if (typeToMock.isAssignableFrom(field.getType())) {
				Assert.assertNull("重复的mock实体", fieldName);
				fieldName = field.getName();
			}
		}
		return fieldName;
	}

	/**
	 * @return
	 */
	private Object getMockHost() {
		Object toMock = getToMock();
		if (toMock != null) {
			return toMock;
		}

		final Class<T> mockHostClass = getMockHostClass();

		Assert.assertNotNull("无法提取到对应的参数模板信息，无法做mock", mockHostClass);

		for (Class<?> current = getClass(); (current != BaseDaoTest.class)
				&& (current != Object.class); current = current.getSuperclass()) {
			final Field[] fields = current.getDeclaredFields();

			for (final Field field : fields) {
				if (mockHostClass.isAssignableFrom(field.getType())) {
					Assert.assertNull("重复的mock宿主", toMock);
					toMock = ReflectionTestUtils
							.getField(this, field.getName());
				}
			}
		}

		Assert.assertNotNull("mock宿主不能为空", toMock);
		return toMock;
	}

	/**
	 * @return
	 */
	private Class<T> getMockHostClass() {
		for (Class<?> current = this.getClass(); (current != BaseDaoTest.class)
				|| (current != Object.class); current = current.getSuperclass()) {
			if (current.getSuperclass() == BaseDaoTest.class) {
				return getMockHostClass(current);
			}
		}
		return null;
	}

	/**
	 * @param current
	 * @return
	 */
	private Class<T> getMockHostClass(final Class<?> current) {
		final Type genericSuperType = current.getGenericSuperclass();
		@SuppressWarnings("unchecked")
		final Class<T> genericClass = (Class<T>) getClass(genericSuperType, 0);
		return genericClass;
	}

	/**
	 * 确认已有异常被抛出
	 */
	protected void checkExceptionRaise() {
		Assert.assertTrue("can't reach here, a exception should be raised",
				false);
	}

	/**
	 * 为本测试用例指定的mock宿主,通过<code>getToMock()</code>指定，设置指定类型的mock实体。
	 * 
	 * 脏mock，需要使用上下文恢复工具 {@linkplain DirtiesContext}
	 * 
	 * @param <E>
	 *            mock实体类型
	 * @param typeToMock
	 *            用来mock的实体类型
	 * @return mock实体
	 */
	protected <E> E dirtyMock(final Class<E> typeToMock) {
		final E mockObject = context.mock(typeToMock);
		final Object mockHost = getMockHost();

		final String fieldName = extractFieldName(mockHost, typeToMock);

		ReflectionTestUtils.setField(mockHost, fieldName, mockObject);
		return mockObject;
	}

	/**
	 * @return mock宿主
	 */
	protected Object getToMock() {
		return null;
	}

	/**
	 * 为本测试用例指定的mock宿主,通过<code>getToMock()</code>指定，设置指定类型的mock实体
	 * 
	 * @param <E>
	 *            mock实体类型
	 * @param typeToMock
	 *            用来mock的实体类型
	 * @return mock实体
	 */
	protected <E> E mock(final Class<E> typeToMock) {
		final E mockObject = context.mock(typeToMock);
		final Object mockHost = getMockHost();

		final String fieldName = extractFieldName(mockHost, typeToMock);
		mock(mockHost, fieldName, mockObject);
		return mockObject;
	}

	/**
	 * mock对象
	 * 
	 * @param <E>
	 *            mock对象类型
	 * 
	 * @param toMock
	 *            mock宿主
	 * @param fieldName
	 *            属性名称
	 * @param typeToMock
	 *            用来mock的对象类型
	 * @return mock对象
	 */
	protected <E> E mock(final Object toMock, final String fieldName,
			final Class<E> typeToMock) {
		final E mockObject = context.mock(typeToMock);
		mock(toMock, fieldName, mockObject);
		return mockObject;
	}

	/**
	 * @param toMock
	 *            用来插入mock对象的大对象
	 * @param fieldName
	 *            属性名称
	 * @param mockObject
	 *            mock对象
	 */
	protected void mock(final Object toMock, final String fieldName,
			final Object mockObject) {
		final MockField mockField = new MockField();
		mockField.setFieldName(fieldName);
		mockField.setNativeValue(ReflectionTestUtils
				.getField(toMock, fieldName));
		mockField.setToMock(toMock);

		Assert.assertTrue("不允许重复mock", !mockFields.contains(mockField));

		mockFields.add(mockField);

		ReflectionTestUtils.setField(toMock, fieldName, mockObject);
	}

	/**
	 * 为本测试用例指定的mock宿主,通过<code>getToMock()</code>指定，设置指定类型的mock实体
	 * 
	 * @param fieldName
	 *            指定的属性名称
	 * 
	 * @param <E>
	 *            mock实体类型
	 * @param typeToMock
	 *            用来mock的实体类型
	 * @return mock实体
	 */
	protected <E> E mock(final String fieldName, final Class<E> typeToMock) {
		final E mockObject = context.mock(typeToMock);
		mock(getToMock(), fieldName, mockObject);
		return mockObject;
	}
}