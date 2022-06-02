package org.integratedmodelling.klab.utils;

/**
 * Any object that delegates functionality to another.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public interface Delegating<T> {

	T getDelegate();
	
}
