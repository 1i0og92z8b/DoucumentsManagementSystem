/**
 * 
 */
package com.bankcomm.novem.dao.annote;

import java.util.HashSet;
import java.util.Set;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-10-29
 * 
 */
public final class DaoTypeProcessor {

	/**
	 * 判断DAO类操作的schema
	 * 
	 * @param classInfo
	 *            用来判断的类型信息
	 * @return schema信息
	 */
	public static String extractDaoSchema(final Class<?> classInfo) {
		final DbSchemaType dbSchemaType = classInfo
				.getAnnotation(DbSchemaType.class);
		if (dbSchemaType != null) {
			return dbSchemaType.value();
		}

		for (final Class<?> interFace : classInfo.getInterfaces()) {
			final DbSchemaType interFaceDbSchemaType = interFace
					.getAnnotation(DbSchemaType.class);
			if (interFaceDbSchemaType != null) {
				return interFaceDbSchemaType.value();
			}
		}

		return DbSchemaType.DEFAULT_SCHEMA;
	}

	/**
	 * 判断类是否已被使用copydao注解，包括父类和接口上
	 * 
	 * @param classInfo
	 *            用来判断的类型信息
	 * 
	 * @return 是否被注解
	 */
	public static DaoTypeEnum extractDaoType(final Class<?> classInfo) {
		final DaoType daoType = classInfo.getAnnotation(DaoType.class);
		if (daoType != null) {
			return daoType.type();
		}

		for (final Class<?> interFace : classInfo.getInterfaces()) {
			final DaoType interFaceDaoType = interFace
					.getAnnotation(DaoType.class);
			if (interFaceDaoType != null) {
				return interFaceDaoType.type();
			}
		}

		return null;
	}

	/**
	 * 判断类是否已被使用copydao注解，包括父类和接口上
	 * 
	 * @param classInfo
	 *            用来判断的类型信息
	 * 
	 * @return 是否被注解
	 */
	public static boolean isCopyDaoAnnoted(final Class<?> classInfo) {
		if (isCopyAnnoted(classInfo, DaoTypeEnum.COPY)) {
			return true;
		}

		for (final Class<?> interFace : classInfo.getInterfaces()) {
			if (isCopyAnnoted(interFace, DaoTypeEnum.COPY)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * 判断类是否已被使用copydao注解，包括父类和接口上
	 * 
	 * @param classInfo
	 *            用来判断的类型信息
	 * @param type
	 *            dao类型
	 * 
	 * @return 是否被注解
	 */
	public static boolean isMatchedAnnoted(final Class<?> classInfo,
			final DaoTypeEnum type) {
		final Set<DaoType> daoTypeSets = extractAllDaoTypes(classInfo);

		if (type == null) {
			for (final DaoType daoType : daoTypeSets) {
				if (daoType != null) {
					return false;
				}
			}
			return true;
		}

		for (final DaoType daoType : daoTypeSets) {
			if (daoType == null) {
				continue;
			}
			if (daoType.type() == type) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @param classInfo
	 * @return
	 */
	private static Set<DaoType> extractAllDaoTypes(final Class<?> classInfo) {
		final Set<DaoType> daoTypeSets = new HashSet<DaoType>();
		daoTypeSets.add(classInfo.getAnnotation(DaoType.class));
		for (final Class<?> interFace : classInfo.getInterfaces()) {
			daoTypeSets.add(interFace.getAnnotation(DaoType.class));
		}
		return daoTypeSets;
	}

	/**
	 * @param classInfo
	 *            数据类型
	 * @param daoTypeEnum
	 *            dao类型
	 * @return
	 */
	private static boolean isCopyAnnoted(final Class<?> classInfo,
			final DaoTypeEnum daoTypeEnum) {
		final DaoType daoType = classInfo.getAnnotation(DaoType.class);
		if (daoType == null) {
			if (daoTypeEnum == null) {
				return true;
			}
			return false;
		}
		return (daoType.type() == daoTypeEnum);
	}

	/**  */
	private DaoTypeProcessor() {
		super();
	}
}
