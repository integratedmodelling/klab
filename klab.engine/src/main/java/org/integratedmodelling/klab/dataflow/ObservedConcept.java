package org.integratedmodelling.klab.dataflow;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;

/**
 * A container for an observable concept and a resolution mode with appropriate
 * equality assessment that matches interoperability rules (only consider the
 * concept definition and any value operators, but no units, range, currency or
 * name). Used to create the dependency tree in the contextualization scope. Can
 * also carry arbitrary data through a generic map.
 * 
 * @author Ferd
 *
 */
public class ObservedConcept {

	private IObservable observable = null;
	private IResolutionScope.Mode mode;
	private Map<String, Object> data = new HashMap<>();
	String conceptDeclaration = null;

	public ObservedConcept(IObservable observable, IResolutionScope.Mode mode) {
		this.observable = observable;
		this.mode = mode;
		this.conceptDeclaration = observable.getType().getDefinition();
	}

	public IObservable getObservable() {
		return observable;
	}

	public IConcept getConcept() {
		return observable.getType();
	}

	public IResolutionScope.Mode getMode() {
		return mode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((conceptDeclaration == null) ? 0 : conceptDeclaration.hashCode());
		result = prime * result + ((mode == null) ? 0 : mode.hashCode());
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
		ObservedConcept other = (ObservedConcept) obj;
		if (conceptDeclaration == null) {
			if (other.conceptDeclaration != null)
				return false;
		} else if (!conceptDeclaration.equals(other.conceptDeclaration))
			return false;
		if (mode != other.mode)
			return false;
		return true;
	}

	/**
	 * Arbitrary data, such as an actuator to repeat the observation when
	 * dependencies change.
	 * 
	 * @return
	 */
	public Map<String, Object> getData() {
		return data;
	}
	
	@Override
	public String toString() {
		return "<" + observable + " " + mode.name().toLowerCase() + ">";
	}

}
