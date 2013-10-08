/**
 * 
 */
package com.bankcomm.novem.biz;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.bankcomm.novem.biz.GenericExtractorTest.Generic.GenericChild_2;
import com.bankcomm.novem.biz.GenericExtractorTest.Generic.GenericListChild;
import com.bankcomm.novem.biz.GenericExtractorTest.Generic.GenericParent;
import com.bankcomm.novem.biz.GenericExtractorTest.Generic.GenericParent_2;
import com.bankcomm.novem.biz.GenericExtractorTest.GenericLv.Lv2;
import com.bankcomm.novem.biz.GenericExtractorTest.Nest.ChildNest;
import com.bankcomm.novem.biz.GenericExtractorTest.Nest.ParentNest;
import com.bankcomm.novem.biz.GenericExtractorTest.Normal.NormalChild;
import com.bankcomm.novem.biz.GenericExtractorTest.Normal.NormalParent;
import com.bankcomm.novem.comm.utils.GenericExtractor;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-10-8
 * 
 */
public class GenericExtractorTest {

	/**  */
	static class Generic {
		/***/
		static class GenericChild_2 extends GenericParent_2<String> {
			//
		}

		/**  */
		static class GenericListChild extends GenericParent<List<String>> {
			//
		}

		/**
		 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-10-8
		 * @param <T>
		 *            模板参数
		 * 
		 */
		static class GenericParent<T> {
			//
		}

		/**
		 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2012-10-8
		 * @param <T>
		 *            模板参数
		 * 
		 */
		static class GenericParent_2<T> {
			//
		}

	}

	/** */
	static class GenericLv {
		/** * @param <T> * 模板参数 */
		static class Lv1<T> {
			//
		}

		/** * @param <T> 模板参数 */
		static class Lv2<T> extends Lv1<T> {
			//
		}

		/** */
		static class Lv3 extends Lv2<String> {
			//
		}
	}

	/***/
	static class Nest {
		/***/
		static class ChildNest extends GenericParent<GenericChild_2> {
			//
		}

		/***/
		static class ParentNest extends GenericParent<GenericParent_2<String>> {
			//
		}

	}

	/***/
	static class Normal {

		/***/
		static class NormalChild extends NormalParent {
			//
		}

		/***/
		static class NormalParent {
			//
		}
	}

	/** */
	static class UpperBoundsLv {
		/** * @param <T> * 模板参数 */
		static class Lv1<T extends NormalParent> {
			//
		}

		/** * @param <T> 模板参数 */
		static class Lv2<T extends NormalParent> extends Lv1<T> {
			//
		}

		/** */
		static class Lv3 extends Lv2<NormalChild> {
			//
		}
	}

	/**
	 * Test method for
	 * {@link GenericExtractor#getClass(java.lang.reflect.Type, int)} .
	 */
	@Test
	public void testGetClassTypeIntWithGenericListChild() {
		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClasses = Arrays.asList(
				GenericListChild.class, List.class, Object.class);
		checkClassTypeExtracter(GenericListChild.class, resultClasses);
	}

	/**
	 * Test method for
	 * {@link GenericExtractor#getClass(java.lang.reflect.Type, int)} .
	 */
	@Test
	public void testGetClassTypeIntWithGenericLv() {
		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv1 = Arrays.asList(
				GenericLv.Lv1.class, Object.class, null);
		checkClassTypeExtracter(GenericLv.Lv1.class, resultClassesLv1);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv2 = Arrays.asList(
				GenericLv.Lv2.class, Object.class, Object.class);
		checkClassTypeExtracter(GenericLv.Lv2.class, resultClassesLv2);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv3 = Arrays.asList(
				GenericLv.Lv3.class, String.class, Object.class);
		checkClassTypeExtracter(GenericLv.Lv3.class, resultClassesLv3);

		Assert.assertEquals(Object.class, GenericExtractor.getClass(
				new Lv2<Object>().getClass().getGenericSuperclass(), 0));
		Assert.assertEquals(
				Object.class,
				GenericExtractor.getClass(new Lv2<Object>().getClass()
						.getSuperclass().getGenericSuperclass(), 0));

		Assert.assertEquals(Object.class, GenericExtractor.getClass(
				new Lv2<String>().getClass().getGenericSuperclass(), 0));
		Assert.assertEquals(
				Object.class,
				GenericExtractor.getClass(new Lv2<String>().getClass()
						.getSuperclass().getGenericSuperclass(), 0));
	}

	/**
	 * Test method for
	 * {@link GenericExtractor#getClass(java.lang.reflect.Type, int)} .
	 */
	@Test
	public void testGetClassTypeIntWithNest() {
		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesParent = Arrays.asList(
				ParentNest.class, GenericParent_2.class, Object.class);
		checkClassTypeExtracter(ParentNest.class, resultClassesParent);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesChild = Arrays.asList(
				ChildNest.class, GenericChild_2.class, Object.class);
		checkClassTypeExtracter(ChildNest.class, resultClassesChild);
	}

	/**
	 * Test method for
	 * {@link GenericExtractor#getClass(java.lang.reflect.Type, int)} .
	 */
	@Test
	public void testGetClassTypeIntWithNormal() {
		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesParent = Arrays.asList(
				NormalParent.class, Object.class, null);
		checkClassTypeExtracter(NormalParent.class, resultClassesParent);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesChild = Arrays.asList(
				NormalChild.class, NormalParent.class, Object.class);
		checkClassTypeExtracter(NormalChild.class, resultClassesChild);
	}

	/**
	 * Test method for
	 * {@link GenericExtractor#getClass(java.lang.reflect.Type, int)} .
	 */
	@Test
	public void testGetClassTypeIntWithUpperBoundsLv() {
		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv1 = Arrays.asList(
				UpperBoundsLv.Lv1.class, Object.class, null);
		checkClassTypeExtracter(UpperBoundsLv.Lv1.class, resultClassesLv1);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv2 = Arrays.asList(
				UpperBoundsLv.Lv2.class, NormalParent.class, Object.class);
		checkClassTypeExtracter(UpperBoundsLv.Lv2.class, resultClassesLv2);

		@SuppressWarnings("unchecked")
		final List<Class<?>> resultClassesLv3 = Arrays.asList(
				UpperBoundsLv.Lv3.class, NormalChild.class, NormalParent.class);
		checkClassTypeExtracter(UpperBoundsLv.Lv3.class, resultClassesLv3);

		Assert.assertEquals(NormalParent.class, GenericExtractor.getClass(
				new UpperBoundsLv.Lv2<NormalParent>().getClass()
						.getGenericSuperclass(), 0));
		Assert.assertEquals(
				Object.class,
				GenericExtractor.getClass(new UpperBoundsLv.Lv2<NormalParent>()
						.getClass().getSuperclass().getGenericSuperclass(), 0));

		Assert.assertEquals(NormalParent.class, GenericExtractor.getClass(
				new UpperBoundsLv.Lv2<NormalChild>().getClass()
						.getGenericSuperclass(), 0));
		Assert.assertEquals(
				Object.class,
				GenericExtractor.getClass(new UpperBoundsLv.Lv2<NormalChild>()
						.getClass().getSuperclass().getGenericSuperclass(), 0));
	}

	/**
	 * @param extractType
	 * @param resultClasses
	 */
	private void checkClassTypeExtracter(final Class<?> extractType,
			final List<Class<?>> resultClasses) {
		Assert.assertEquals(resultClasses.get(0),
				GenericExtractor.getClass(extractType, 0));
		Assert.assertEquals(resultClasses.get(1), GenericExtractor.getClass(
				extractType.getGenericSuperclass(), 0));
		Assert.assertEquals(resultClasses.get(2), GenericExtractor.getClass(
				extractType.getSuperclass().getGenericSuperclass(), 0));
	}
}