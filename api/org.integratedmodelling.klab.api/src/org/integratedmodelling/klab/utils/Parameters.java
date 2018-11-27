package org.integratedmodelling.klab.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IParameters;

/**
 * An order-preserving map with two improved get() methods to enable simpler and
 * more flexible use idioms.
 * 
 * @author ferdinando.villa
 *
 */
public class Parameters<T> implements IParameters<T> {

	private Map<T, Object> delegate;

	public Parameters(Map<T, Object> delegate) {
		this.delegate = delegate;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Parameters<T> create(Object... o) {
		Map<T, Object> inp = new HashMap<T, Object>();
		if (o != null) {
			for (int i = 0; i < o.length; i++) {
				inp.put((T)o[i], o[++i]);
			}	
		}
		return new Parameters(inp);
	}

	/**
	 * Only used when the object must be serialized through reflection.
	 * 
	 * @return a map with all data
	 */
	public Map<T, Object> getData() {
		return delegate;
	}

	@Override
	public <K> K get(T name, K defaultValue) {
		Object ret = get(name);
		if (ret == null) {
			return defaultValue;
		}
		return defaultValue == null ? null : Utils.asType(ret, defaultValue.getClass());
	}

	@Override
	public <K> K get(T name, Class<? extends K> cls) {
		Object ret = get(name);
		if (ret == null) {
			return null;
		}
		return Utils.asType(ret, cls);
	}

	public Parameters() {
		this.delegate = new LinkedHashMap<>();
	}

	public int size() {
		return delegate.size();
	}

	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	public boolean containsKey(Object key) {
		return delegate.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return delegate.containsValue(value);
	}

	public Object get(Object key) {
		return delegate.get(key);
	}

	public Object put(T key, Object value) {
		return delegate.put(key, value);
	}

	public Object remove(Object key) {
		return delegate.remove(key);
	}

	public void putAll(Map<? extends T, ? extends Object> m) {
		delegate.putAll(m);
	}

	public void clear() {
		delegate.clear();
	}

	public Set<T> keySet() {
		return delegate.keySet();
	}

	public Collection<Object> values() {
		return delegate.values();
	}

	public Set<Entry<T, Object>> entrySet() {
		return delegate.entrySet();
	}

	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public Object getOrDefault(Object key, Object defaultValue) {
		return delegate.getOrDefault(key, defaultValue);
	}

	public void forEach(BiConsumer<? super T, ? super Object> action) {
		delegate.forEach(action);
	}

	public void replaceAll(BiFunction<? super T, ? super Object, ? extends Object> function) {
		delegate.replaceAll(function);
	}

	public Object putIfAbsent(T key, Object value) {
		return delegate.putIfAbsent(key, value);
	}

	public boolean remove(Object key, Object value) {
		return delegate.remove(key, value);
	}

	public boolean replace(T key, Object oldValue, Object newValue) {
		return delegate.replace(key, oldValue, newValue);
	}

	public Object replace(T key, Object value) {
		return delegate.replace(key, value);
	}

	public Object computeIfAbsent(T key, Function<? super T, ? extends Object> mappingFunction) {
		return delegate.computeIfAbsent(key, mappingFunction);
	}

	public Object computeIfPresent(T key, BiFunction<? super T, ? super Object, ? extends Object> remappingFunction) {
		return delegate.computeIfPresent(key, remappingFunction);
	}

	public Object compute(T key, BiFunction<? super T, ? super Object, ? extends Object> remappingFunction) {
		return delegate.compute(key, remappingFunction);
	}

	public Object merge(T key, Object value,
			BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
		return delegate.merge(key, value, remappingFunction);
	}

	@Override
	public boolean contains(T key) {
		return delegate.get(key) != null;
	}

	@Override
	public boolean contains(T key, Class<?> cls) {
		Object obj = delegate.get(key);
		return obj != null && cls.isAssignableFrom(obj.getClass());
	}

}
