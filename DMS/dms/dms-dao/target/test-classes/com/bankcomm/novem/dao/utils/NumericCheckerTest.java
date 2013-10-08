/**
 * 
 */
package com.bankcomm.novem.dao.utils;

import static com.bankcomm.novem.dao.utils.NumericChecker.isNumeric;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import lombok.SneakyThrows;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2011-3-3
 * 
 */
public class NumericCheckerTest {
	private final Set<Class<?>> notNumericClass = new HashSet<Class<?>>();
	private final Set<Class<?>> numericClass = new HashSet<Class<?>>();
	private final Set<Class<?>> unboxNotNumericClass = new HashSet<Class<?>>();
	private final Set<Class<?>> unboxNumericClass = new HashSet<Class<?>>();
	{
		notNumericClass.add(String.class);
		notNumericClass.add(Character.class);
		notNumericClass.add(Boolean.class);
		notNumericClass.add(Enum.class);
		notNumericClass.add(StringBuffer.class);
		notNumericClass.add(Number.class);

		numericClass.add(Byte.class);
		numericClass.add(Short.class);
		numericClass.add(Integer.class);
		numericClass.add(Long.class);
		numericClass.add(Float.class);
		numericClass.add(Double.class);
		numericClass.add(BigDecimal.class);

		unboxNumericClass.add(Byte.TYPE);
		unboxNumericClass.add(Short.TYPE);
		unboxNumericClass.add(Integer.TYPE);
		unboxNumericClass.add(Long.TYPE);
		unboxNumericClass.add(Float.TYPE);
		unboxNumericClass.add(Double.TYPE);

		unboxNotNumericClass.add(Character.TYPE);
		unboxNotNumericClass.add(Boolean.TYPE);

	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NumericChecker#isNumeric(java.lang.Class)}
	 * .
	 */
	@Test
	public void testIsNumeric() {
		for (final Class<?> checkEntry : numericClass) {
			assertTrue(checkEntry.toString(), isNumeric(checkEntry));
		}

		for (final Class<?> checkEntry : unboxNumericClass) {
			assertTrue(checkEntry.toString(), isNumeric(checkEntry));
		}

		for (final Class<?> checkEntry : notNumericClass) {
			assertFalse(checkEntry.toString(), isNumeric(checkEntry));
		}

		for (final Class<?> checkEntry : unboxNotNumericClass) {
			Assert.assertFalse(checkEntry.toString(), isNumeric(checkEntry));
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NumericChecker#isNumeric(java.lang.Class)}
	 */
	@Test
	@SneakyThrows({ IllegalArgumentException.class })
	public void testIsNumericForUnboxNotNumericTesterField() {
		for (final Field field : UnboxNotNumericTester.class
				.getDeclaredFields()) {
			// 部分覆盖率工具会生成以$开头的成员变量，一般不会自己去写这样的变量
			if (StringUtils.startsWith(field.getName(), "$")) {
				continue;
			}
			final boolean isAccessible = field.isAccessible();
			field.setAccessible(true);

			Assert.assertFalse(field.getType().toString(),
					NumericChecker.isNumeric(field.getType()));

			field.setAccessible(isAccessible);
		}
	}

	/**
	 * Test method for
	 * {@link com.bankcomm.novem.dao.utils.NumericChecker#isNumeric(java.lang.Class)}
	 */
	@Test
	@SneakyThrows({ IllegalArgumentException.class })
	public void testIsNumericForUnboxNumericField() {
		for (final Field field : UnboxNumericTester.class.getDeclaredFields()) {
			// 部分覆盖率工具会生成以$开头的成员变量，一般不会自己去写这样的变量
			if (StringUtils.startsWith(field.getName(), "$")) {
				continue;
			}

			final boolean isAccessible = field.isAccessible();
			field.setAccessible(true);

			Assert.assertTrue(field.getType().toString(),
					NumericChecker.isNumeric(field.getType()));

			field.setAccessible(isAccessible);
		}
	}
}