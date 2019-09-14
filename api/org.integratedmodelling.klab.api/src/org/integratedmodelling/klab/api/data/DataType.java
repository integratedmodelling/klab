package org.integratedmodelling.klab.api.data;

public enum DataType {

	/*
	 * POD data types
	 */
	BYTE(Byte.SIZE / 8), SHORT(Short.SIZE / 8), INT(Integer.SIZE / 8), LONG(Long.SIZE / 8), FLOAT(Float.SIZE / 8),
	DOUBLE(Double.SIZE / 8), TEXT(false),

	/*
	 * non-POD
	 */
	DISTRIBUTION(false), SHAPE(false);

	public int size;
	public boolean isPOD = true;

	private DataType(int size) {
		this.size = size;
	}

	private DataType(boolean isPod) {
		this.size = -1;
		this.isPOD = isPod;
	}

	/**
	 * Return the type corresponding to a Java class. Only works for
	 * POD types.
	 * 
	 * @param cls
	 * @return
	 */
	public static DataType forClass(Class<?> cls) {
		if (cls.equals(Byte.class)) {
			return BYTE;
		} else if (cls.equals(Short.class)) {
			return SHORT;
		} else if (cls.equals(Integer.class)) {
			return INT;
		} else if (cls.equals(Long.class)) {
			return LONG;
		} else if (cls.equals(Float.class)) {
			return FLOAT;
		} else if (cls.equals(Double.class)) {
			return DOUBLE;
		} else if (cls.equals(String.class)) {
			return TEXT;
		}
		return null;
	}
}
