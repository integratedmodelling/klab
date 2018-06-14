package org.integratedmodelling.klab.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.integratedmodelling.kim.api.IParameters;

/**
 * A map with two improved get() methods to enable simpler and more flexible use idioms.
 * 
 * @author ferdinando.villa
 *
 */
public class Parameters implements Map<String, Object>, IParameters {

  private Map<String, Object> delegate;

  public Parameters(Map<String, Object> delegate) {
    this.delegate = delegate;
  }
  
  /**
   * Only used when the object must be serialized through reflection.
   * 
   * @return a map with all data
   */
  public Map<String,Object> getData() {
    return delegate;
  }

  @Override
  public <T> T get(String name, T defaultValue) {
    Object ret = get(name);
    if (ret == null) {
      return defaultValue;
    }
    return Utils.asType(ret, defaultValue.getClass());
  }

  @Override
  public <T> T get(String name, Class<? extends T> cls) {
    Object ret = get(name);
    if (ret == null) {
      return null;
    }
    return Utils.asType(ret, cls);
  }


  public Parameters() {
    this.delegate = new HashMap<>();
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

  public Object put(String key, Object value) {
    return delegate.put(key, value);
  }

  public Object remove(Object key) {
    return delegate.remove(key);
  }

  public void putAll(Map<? extends String, ? extends Object> m) {
    delegate.putAll(m);
  }

  public void clear() {
    delegate.clear();
  }

  public Set<String> keySet() {
    return delegate.keySet();
  }

  public Collection<Object> values() {
    return delegate.values();
  }

  public Set<Entry<String, Object>> entrySet() {
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

  public void forEach(BiConsumer<? super String, ? super Object> action) {
    delegate.forEach(action);
  }

  public void replaceAll(BiFunction<? super String, ? super Object, ? extends Object> function) {
    delegate.replaceAll(function);
  }

  public Object putIfAbsent(String key, Object value) {
    return delegate.putIfAbsent(key, value);
  }

  public boolean remove(Object key, Object value) {
    return delegate.remove(key, value);
  }

  public boolean replace(String key, Object oldValue, Object newValue) {
    return delegate.replace(key, oldValue, newValue);
  }

  public Object replace(String key, Object value) {
    return delegate.replace(key, value);
  }

  public Object computeIfAbsent(String key,
      Function<? super String, ? extends Object> mappingFunction) {
    return delegate.computeIfAbsent(key, mappingFunction);
  }

  public Object computeIfPresent(String key,
      BiFunction<? super String, ? super Object, ? extends Object> remappingFunction) {
    return delegate.computeIfPresent(key, remappingFunction);
  }

  public Object compute(String key,
      BiFunction<? super String, ? super Object, ? extends Object> remappingFunction) {
    return delegate.compute(key, remappingFunction);
  }

  public Object merge(String key, Object value,
      BiFunction<? super Object, ? super Object, ? extends Object> remappingFunction) {
    return delegate.merge(key, value, remappingFunction);
  }

  @Override
  public boolean contains(String key) {
    return delegate.get(key) != null;
  }

  @Override
  public boolean contains(String key, Class<?> cls) {
    Object obj = delegate.get(key);
    return obj != null && cls.isAssignableFrom(obj.getClass());
  }
  
  
}
