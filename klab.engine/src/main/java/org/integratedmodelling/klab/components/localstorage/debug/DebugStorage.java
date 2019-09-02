package org.integratedmodelling.klab.components.localstorage.debug;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.data.DataType;
import org.integratedmodelling.klab.engine.runtime.api.IDataStorage;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Utils;

public class DebugStorage<T> implements IDataStorage<T> {

	private IScale geometry;
	List<T> data = null;
	private Class<?> valueClass;
	private DataType type;

	
	@SuppressWarnings("unchecked")
	public DebugStorage(IScale geometry, Class<?> cls) {
		this.geometry = geometry;
		this.valueClass = cls;
		this.data = Collections.synchronizedList(new ArrayList<>((int)geometry.size()));
		for (int i = 0; i < geometry.size(); i ++) {
			this.data.add(i, (T)getNodataValue());
		}
	}
	
	@Override
	public void close() throws IOException {
	}

	private DataType getDatatype() {

		if (type == null) {

			if (valueClass == Double.class) {
				// Double.NaN for nodata
				type = DataType.DOUBLE;
			} else if (valueClass == Short.class) {
				// Short.MIN_VALUE for nodata
				type = DataType.SHORT;
			} else if (valueClass == Integer.class) {
				// Integer.MIN_VALUE for nodata
				type = DataType.INT;
			} else if (valueClass == Long.class) {
				// Long.MIN_VALUE for nodata
				type = DataType.LONG;
			} else if (valueClass == Float.class) {
				// Float.NaN for nodata
				type = DataType.FLOAT;
			} else if (valueClass == Boolean.class) {
				// 0 for false, 1 for true, Byte.MIN_VALUE for nodata
				type = DataType.BYTE;
			} else {
				throw new IllegalStateException(
						"NetCDF-backed storage cannot use type " + valueClass.getCanonicalName());
			}
		}

		return type;
	}
	
	private boolean isNodata(Object value) {
		switch (type) {
		case BYTE:
			return value instanceof Number && ((Number) value).byteValue() == Byte.MIN_VALUE;
		case DOUBLE:
			return value instanceof Number && Double.isNaN(((Number) value).doubleValue());
		case FLOAT:
			return value instanceof Number && Double.isNaN(((Number) value).floatValue());
		case INT:
			return value instanceof Number && ((Number) value).intValue() == Integer.MIN_VALUE;
		case LONG:
			return value instanceof Number && ((Number) value).longValue() == Long.MIN_VALUE;
		case SHORT:
			return value instanceof Number && ((Number) value).shortValue() == Short.MIN_VALUE;
		default:
			break;
		}
		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public long put(T val, ILocator locator) {
		
		if (val == null) {
			val = (T)getNodataValue();
		}

		Offset offsets = locator.as(Offset.class);
		long offset = offsets.linear; // ((Scale)this.geometry).getOffset(locator);
		this.data.set((int)offset, val);

		return offset;
	}
	
	private Object getNodataValue() {
		switch (getDatatype()) {
		case BYTE:
			return Byte.MIN_VALUE;
		case DOUBLE:
			return Double.NaN;
		case FLOAT:
			return Float.NaN;
		case INT:
			return Integer.MIN_VALUE;
		case LONG:
			return Long.MIN_VALUE;
		case SHORT:
			return Short.MIN_VALUE;
		default:
			break;
		}
		throw new KlabInternalErrorException("NetCDF-backed storage: unexpected type");
	}

	@SuppressWarnings("unchecked")
	@Override
	public T get(ILocator locator) {
		Offset offsets = locator.as(Offset.class);
		long offset = offsets.linear; // ((Scale)this.geometry).getOffset(locator);
		Object value = this.data.get((int)offset);
		return isNodata(value) ? null : (T)value;
	}

	@Override
	public Type getType() {
		return Utils.getArtifactType(valueClass);
	}

	@Override
	public Object getObject(ILocator locator) {
		return get(locator);
	}

	@SuppressWarnings("unchecked")
	@Override
	public long putObject(Object value, ILocator locator) {
		return put((T) value, locator);
	}

}
