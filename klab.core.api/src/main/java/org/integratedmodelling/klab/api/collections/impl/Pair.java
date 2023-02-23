/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.collections.impl;

import java.util.Objects;

import org.integratedmodelling.klab.api.collections.IPair;

/**
 * Stupid generic pair class.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 * @param <T1> the generic type
 * @param <T2> the generic type
 */
public class Pair<T1, T2> implements IPair<T1, T2> {

//	static boolean cmpObj(Object o1, Object o2) {
//		return (o1 == null && o2 == null) || (o1 != null && o2 != null && o1.equals(o2));
//	}

	public static <A, B> Pair<A, B> create(A a, B b) {
		return new Pair<A, B>(a, b);
	}

	private static final long serialVersionUID = 1L;
	protected T1 first = null;
	protected T2 second = null;

	/**
	 * Pair constructor comment.
	 */
	public Pair() {
	}

	/**
	 * Instantiates a new pair.
	 *
	 * @param first  the first
	 * @param second the second
	 */
	public Pair(T1 first, T2 second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Instantiates a new pair.
	 *
	 * @param pc the pc
	 */
	public Pair(Pair<T1, T2> pc) {
		this.first = pc.first;
		this.second = pc.second;
	}

	/**
	 * Sets the first.
	 *
	 * @param newValue the new first
	 */
	public void setFirst(T1 newValue) {
		this.first = newValue;
	}

	/**
	 * Sets the second.
	 *
	 * @param newValue the new second
	 */
	public void setSecond(T2 newValue) {
		this.second = newValue;
	}

	/**
	 * <p>
	 * Getter for the field <code>first</code>.
	 * </p>
	 *
	 * @return a T1 object.
	 */
	public T1 getFirst() {
		return first;
	}

	/**
	 * <p>
	 * Getter for the field <code>second</code>.
	 * </p>
	 *
	 * @return a T2 object.
	 */
	public T2 getSecond() {
		return second;
	}

	@Override
	public String toString() {
		return "{" + getFirst() + "," + getSecond() + "}";
	}

	@Override
	public int hashCode() {
		return Objects.hash(first, second);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pair<?,?> other = (Pair) obj;
		return Objects.equals(first, other.first) && Objects.equals(second, other.second);
	}

}
