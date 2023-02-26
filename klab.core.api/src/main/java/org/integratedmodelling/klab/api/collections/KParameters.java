package org.integratedmodelling.klab.api.collections;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * API for a read-only, nicer to use Map<String, Object> that collects named parameters of a
 * function. Implemented in {@link Parameters} which can be used as a drop-in replacement for a
 * parameter map.
 * 
 * @author ferdinando.villa
 *
 */
public interface KParameters<T> extends Map<T, Object>, Serializable {

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
     * Get the value as the passed type, if necessary converting between numeric types or casting to
     * strings. If the result is null, do your best to convert to a suitable primitive POD so that
     * it can be assigned to one without NPEs, but with possible inaccuracies (e.g. ints and longs
     * will be 0).
     * 
     * @param name
     * @param cls the expected class of the result
     * @return a plain Java object
     * @throws IllegalArgumentException if the requested class is incompatible with the type.
     */
    <K> K getNotNull(T name, Class<? extends K> cls);

    /**
     * Get the value as the passed type, returning a set default if the value is not there,
     * otherwise converting if necessary between numeric types or casting to strings.
     * 
     * @param name
     * @param defaultValue the default value returned if the map does not contain the value; also
     *        specifies the expected class of the result and a potential conversion if found.
     * @return a plain Java object
     * @throws IllegalArgumentException if the requested class is incompatible with the type.
     */
    <K> K get(T name, K defaultValue);

    /**
     * Return the value that matches any of the passed keys, or null.
     * 
     * @param <K>
     * @param keys
     * @return
     */
    <K> K getAny(T... keys);

    /**
     * When used as a parameter list parsed from a function call, this may contain arguments that
     * are unnamed. These are given default names and if any is present, their names are returned
     * here. Usage of this functionality is restricted to T == String.class and any usage outside of
     * that will generate runtime errors.
     * 
     * @return a list of unnamed argument keys, possibly empty.
     */
    List<T> getUnnamedKeys();

    /**
     * Return all the unnamed arguments in order of declaration.
     * 
     * @return
     */
    List<Object> getUnnamedArguments();

    /**
     * Return all the keys that correspond to named parameters.
     * 
     * @return a list of unnamed argument keys, possibly empty.
     */
    List<T> getNamedKeys();

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

    /**
     * True if this contains any of the passed keys
     * 
     * @param keys
     * @return
     */
    boolean containsAnyKey(T... keys);

    /**
     * True if this contains any of the passed values
     * 
     * @param keys
     * @return
     */
    boolean containsAny(Object... objects);

    /**
     * Return the subset of the map whose keys start with the passed string.
     * 
     * @param string
     * @return
     */
    Map<T, Object> getLike(String string);

    /**
     * Return a new parameter object with the same content that automatically resolves templated
     * values using the passed map.
     * 
     * @param templateVariables
     * @return
     */
    KParameters<T> with(KParameters<String> templateVariables);

    /**
     * If {@link #with(KParameters)} has been called, return the variables, otherwise return null.
     * 
     * @return
     */
    KParameters<String> getTemplateVariables();

}
