package org.integratedmodelling.klab.data.storage;

import java.util.Iterator;
import java.util.function.Function;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.classification.IDataKey;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.utils.NumberUtils;

/**
 * Iterate a state. Never return NaN - always nulls for nodata.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class DataIterator<T> implements Iterator<T> {

	private Iterator<ILocator> iterator;
	private IDataArtifact data;
	private Function<Object, T> converter;

	public DataIterator(IDataArtifact data, Iterator<ILocator> iterator, Function<Object, T> translator) {
		this.iterator = iterator;
		this.data = data;
		this.converter = translator;
	}

	@SuppressWarnings("unchecked")
	public static <T> DataIterator<T> create(IState data, IScale scale, Class<? extends T> cls) {
		
		IDataKey classification = data.getDataKey();
		
		if (Number.class.isAssignableFrom(cls)) {	
			return (DataIterator<T>) new DataIterator<Number>(data, scale.iterator(), getConverterToNumber(data, cls, classification));
		} else if (Boolean.class.isAssignableFrom(cls)) {
			return (DataIterator<T>) new DataIterator<Boolean>(data, scale.iterator(), getConverterToBoolean(data));
		} else if (IConcept.class.isAssignableFrom(cls)) {
			if (data.getType() != IArtifact.Type.CONCEPT) {
				throw new IllegalArgumentException("cannot iterate categorical state " + data + " as " + cls.getCanonicalName());
			}
			return (DataIterator<T>) new DataIterator<IConcept>(data, scale.iterator(), null);
		}
		throw new IllegalArgumentException("cannot iterate state " + data + " as " + cls.getCanonicalName());
	}

	private static Function<Object, Boolean> getConverterToBoolean(IDataArtifact data) {

		return (object) -> {
			if (object instanceof Boolean) {
				return (Boolean) object;
			} else if (object instanceof Number) {
				return NumberUtils.equal(((Number) object).doubleValue(), 0);
			}
			return false;
		};
	}

	private static Function<Object, Number> getConverterToNumber(IDataArtifact data, Class<?> cls,
			IDataKey classification) {

		return (object) -> {
			if (object instanceof Boolean) {
				return NumberUtils.convertNumber(((Boolean) object) ? 1 : 0, cls);
			} else if (object instanceof Number) {
				return object.getClass().equals(cls) ? (Number) object
						: NumberUtils.convertNumber((Number) object, cls);
			} else if (object instanceof IConcept && classification != null) {
				return NumberUtils.convertNumber(classification.reverseLookup(object), cls);
			}
			return 0;
		};
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T next() {
		Object value = data.get(iterator.next());
		if (value != null) {
			return converter == null ? (T) value : converter.apply(value);
		}
		return null;
	}

}
