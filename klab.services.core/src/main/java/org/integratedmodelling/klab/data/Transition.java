package org.integratedmodelling.klab.data;

/**
 * Nothing but a pair, but with the implied semantics of an event causing the
 * source value to have turned into the destination value.
 * 
 * @author Ferd
 *
 * @param <T>
 */
public class Transition<T> {

	private T source;
	private T destination;

	public Transition(T from, T to) {
		this.source = from;
		this.destination = to;
	}

	public T getDestination() {
		return destination;
	}

	public void setDestination(T destination) {
		this.destination = destination;
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((source == null) ? 0 : source.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transition<?> other = (Transition<?>) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[" + source + "->" + destination + "]";
	}

}
