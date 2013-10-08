package com.bankcomm.novem.biz.dozer.convert;

import org.apache.commons.lang3.StringUtils;
import org.dozer.ConfigurableCustomConverter;
import org.dozer.MappingException;

/**
 * @author 朱诗君 zhushijun@bankcomm.com 交通银行 2010-12-17
 * 
 */
public class CommonEnumConverter implements ConfigurableCustomConverter {
	private String parameter;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.dozer.CustomConverter#convert(java.lang.Object,
	 * java.lang.Object, java.lang.Class, java.lang.Class)
	 */
	@Override
	public Object convert(final Object existingDestinationFieldValue,
			final Object sourceFieldValue, final Class<?> destinationClass,
			final Class<?> sourceClass) {
		if (destinationClass.equals(String.class) && sourceClass.isEnum()) {
			return fromEnumToString(sourceFieldValue);
		} else if (sourceClass.equals(String.class)
				&& destinationClass.isEnum()) {
			return fromStringToEnum((String) sourceFieldValue, destinationClass);
		} else {
			throw new MappingException("Can't convert from(" + sourceFieldValue
					+ " to " + destinationClass.getName()
					+ ") with converter (" + this.getClass().getName() + ")!");
		}
	}

	/**
	 * Retrieves the static parameter configured for this particular converter
	 * instance. It is not advisable to call this method from converter
	 * constructor as the parameter is not yet there.
	 * 
	 * @return parameter value
	 * @throws IllegalStateException
	 *             if parameter has not been set yet.
	 */
	public String getParameter() {
		if (parameter == null) {
			throw new IllegalStateException(
					"Custom Converter Parameter has not yet been set!");
		}
		return parameter;
	}

	/**
	 * Sets the configured parameter value for this converter instance. Should
	 * be called by Dozer internaly before actual mapping.
	 * 
	 * @param parameter
	 *            configured parameter value
	 */
	@Override
	public void setParameter(final String parameter) {
		this.parameter = parameter;
	}

	/**
	 * @param sourceFieldValue
	 * @return
	 */
	private String fromEnumToString(final Object sourceFieldValue) {
		if (sourceFieldValue == null) {
			return null;
		}
		return ((Enum<?>) sourceFieldValue).name();
	}

	/**
	 * @param sourceFieldValue
	 * @param destinationClass
	 * @return
	 */
	private Enum<?> fromStringToEnum(final String sourceFieldValue,
			final Class<?> destinationClass) {
		if (StringUtils.isBlank(sourceFieldValue)) {
			return null;
		}

		for (final Enum<?> enumValue : (Enum<?>[]) destinationClass
				.getEnumConstants()) {
			if (StringUtils.equals(enumValue.name(), sourceFieldValue)) {
				return enumValue;
			}
		}

		throw new MappingException("Can't convert from(" + sourceFieldValue
				+ " to " + destinationClass.getName() + ") with converter ("
				+ this.getClass().getName() + ")!");
	}
}