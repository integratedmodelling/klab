package org.integratedmodelling.klab.api.data;

public enum DataType {

	/*
	 * POD data types
	 */
	BYTE(Byte.SIZE / 8), SHORT(Short.SIZE / 8), INT(Integer.SIZE / 8), LONG(Long.SIZE / 8), FLOAT(Float.SIZE / 8),
	DOUBLE(Double.SIZE / 8),

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
}
