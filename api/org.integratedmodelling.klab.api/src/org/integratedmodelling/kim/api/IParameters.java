package org.integratedmodelling.kim.api;

import java.util.Map;

import org.integratedmodelling.klab.utils.Parameters;

/**
 * API for a read-only, nicer to use Map<String, Object> that collects named parameters of a
 * function. Implemented in {@link Parameters} which can be used as a drop-in replacement for a
 * parameter map.
 * 
 * @author ferdinando.villa
 *
 */
public interface IParameters<T> extends Map<T, Object>    {

  /**
   * Get the value as the passed type, if necessary converting between numeric types or casting to
   * strings.
   * 
   * @param name
   * @param cls the expected class of the result
   * @return a plain Java object
   * @throws IllegalArgumentException if the requested class is incompatible with the type.
   */
  <K> K get(T name, Class<? extends K> cls);

  /**
   * Get the value as the passed type, returning a set default if the value is not there, otherwise
   * converting if necessary between numeric types or casting to strings.
   * 
   * @param name
   * @param defaultValue the default value returned if the map does not contain the value; also
   *        specifies the expected class of the result and a potential conversion if found.
   * @return a plain Java object
   * @throws IllegalArgumentException if the requested class is incompatible with the type.
   */
  <K> K get(T name, K defaultValue);
  
  /**
   * Like {@link #containsKey(Object)}, except it returns false also if the key is there but the
   * corresponding object is null.
   * 
   * @param key
   * @return false if key is not there or points to a null object
   */
  boolean contains(T key);

  /**
   * Check if an object is present for the key and it is of the passed class.
   * 
   * @param key
   * @param cls
   * @return true if object is there and belongs to cls
   */
  boolean contains(T key, Class<?> cls);
}
