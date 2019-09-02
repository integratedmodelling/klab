package org.integratedmodelling.klab.data;

public enum DataType {

	BYTE(Byte.SIZE / 8), SHORT(Short.SIZE / 8), INT(Integer.SIZE / 8), LONG(Long.SIZE / 8), FLOAT(Float.SIZE / 8),
	DOUBLE(Double.SIZE / 8);

	public int size;

	DataType(int size) {
		this.size = size;
	}
}
