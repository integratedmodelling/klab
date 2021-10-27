package org.integratedmodelling.klab.engine.runtime.code.groovy;

import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IRelationship;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.extensions.groovy.model.DirectObservation;
import org.integratedmodelling.klab.extensions.groovy.model.Relationship;
import org.integratedmodelling.klab.extensions.groovy.model.Scale;
import org.integratedmodelling.klab.extensions.groovy.model.Space;
import org.integratedmodelling.klab.extensions.groovy.model.State;
import org.integratedmodelling.klab.extensions.groovy.model.Time;

import groovy.lang.Binding;

public class Wrapper<T> {

    protected Binding binding;
  
    // we either have this, which means our actual object is in the bindings with this ID...
    String  jID;
    // or this, which means we're a direct wrapper for one-off usage.
    T unwrapped;

    protected Wrapper(String id, Binding binding) {
        this.binding = binding;
        this.jID = id;
    }

    protected Wrapper(T object, Binding binding) {
        this.unwrapped = object;
        this.binding = binding;
    }

    @SuppressWarnings("unchecked")
    public T unwrap() {
        return unwrapped == null ? (T) this.binding.getVariables().get(jID) : unwrapped;
    }
    
    public IRuntimeScope getScope() {
    	return (IRuntimeScope) this.binding.getVariable("_c");
    }
    
    /**
     * Produce the Groovy wrapper for the passed object and install it in the
     * bindings with the passed name. After this we can just point the wrapped
     * ID to a different object to use the same wrapper without recreating it.
     * 
     * @param object
     * @return the wrapped object (already set in binding)
     */
    public static Object wrap(Object object, String eID, Binding binding) {

        Object ret = object;
        String jID = GroovyExpression.eid2j(eID);
        binding.setVariable(jID, object);

        if (object instanceof IRelationship) {
            ret = new Relationship(jID, binding);
        } else if (object instanceof IDirectObservation) {
            ret = new DirectObservation<IDirectObservation>(jID, binding);
        } else if (object instanceof IState) {
            ret = new State(jID, binding);
        } else if (object instanceof IScale) {
            ret = new Scale(jID, binding);
        } else if (object instanceof ISpace) {
            ret = new Space(jID, binding);
        } else if (object instanceof ITime) {
            ret = new Time(jID, binding);
        } /* else if (object instanceof IGrid) {
            ret = new State(jID, binding);
        } */

        binding.setVariable(eID, ret);

        return ret;
    }
}
