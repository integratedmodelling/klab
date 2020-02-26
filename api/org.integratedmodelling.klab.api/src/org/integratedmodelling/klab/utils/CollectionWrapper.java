package org.integratedmodelling.klab.utils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper for an individual POD, array or collection that allows extracting and
 * iterating without too much hassle.
 * 
 * @author Ferd
 *
 */
public class CollectionWrapper implements Iterable<Object> {

	Object pod;
	int size = 1;
	boolean isArray = false;
	boolean isCollection = false;

	class PodIterator implements Iterator<Object> {

		int current = 0;
		Iterator<?> delegate = null;

		PodIterator() {
			if (pod instanceof Collection) {
				this.delegate = ((Collection<?>) pod).iterator();
			}
		}

		@Override
		public boolean hasNext() {
			return delegate == null ? current < size : delegate.hasNext();
		}

		@Override
		public Object next() {

			if (isArray) {
				if (pod.getClass().equals(int[].class)) {
					return ((int[]) pod)[current++];
				} else if (pod.getClass().equals(long[].class)) {
					return ((long[]) pod)[current++];
				} else if (pod.getClass().equals(float[].class)) {
					return ((float[]) pod)[current++];
				} else if (pod.getClass().equals(double[].class)) {
					return ((double[]) pod)[current++];
				} else if (pod.getClass().equals(boolean[].class)) {
					return ((boolean[]) pod)[current++];
				} else {
					return ((Object[]) pod)[current++];
				}
			} else if (delegate != null) {
				return delegate.next();
			}

			return pod;
		}

	}

	public CollectionWrapper(Object pod) {

		this.pod = pod;
		if (pod == null) {
			return;
		}
		if (pod.getClass().isArray()) {
			isArray = true;
			this.size = Array.getLength(pod);
		}
		if (pod instanceof Collection) {
			isCollection = true;
			this.size = -1; // compute on demand
			if (pod instanceof List) {
				this.size = ((List<?>) pod).size();
			}
		}
	}
	
	public Object get(long offset) {
		if (isArray) {
			if (pod.getClass().equals(int[].class)) {
				return ((int[]) pod)[(int)offset];
			} else if (pod.getClass().equals(long[].class)) {
				return ((long[]) pod)[(int)offset];
			} else if (pod.getClass().equals(float[].class)) {
				return ((float[]) pod)[(int)offset];
			} else if (pod.getClass().equals(double[].class)) {
				return ((double[]) pod)[(int)offset];
			} else if (pod.getClass().equals(boolean[].class)) {
				return ((boolean[]) pod)[(int)offset];
			} else {
				return ((Object[]) pod)[(int)offset];
			}
		} else if (pod instanceof List) {
			return ((List<?>)pod).get((int)offset);
		} else if (pod instanceof Collection) {
			// gaaah
			Iterator<?> it = ((Collection<?>)pod).iterator();
			for (int i = 0; i < (offset-1); i++) {
				it.next();
			}
			return it.next();
		}

		return pod;
	}

	public int size() {
		if (size < 0) {
			// must iterate to know
			this.size = 0;
			for (Iterator<?> it = ((Collection<?>) pod).iterator(); it.hasNext(); it.next()) {
				this.size++;
			}
		}
		return size;
	}

	public Object asObject() {
		return this.pod;
	}

	@Override
	public Iterator<Object> iterator() {
		return new PodIterator();
	}

}
