package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.klab.api.data.ILocator
import org.integratedmodelling.klab.api.model.IModel
import org.integratedmodelling.klab.api.observations.IDirectObservation
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.ISubject
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor

abstract class Observation {

    IObservation obs;
    String name;
    Binding binding;
    IModel model;

    def Observation(IObservation observation, Binding binding) {
        this.obs = observation;
        this.binding = binding;
        if (binding.hasVariable("_model")) {
            this.model = binding.getVariable("_model");
        }
    }
    
    def size() {
        // gimmick to be able to call size() on a selection even if it results in one observation.
        return 1;
    }
    
    def named(String name) {
        this.name = name;
    }

    def getName() {
        return name;
    }
    
    def getObservable() {
        return new Concept(obs.getObservable().getSemantics().getType(), binding);
    }
    
    def getObservingSubject() {
        ISubject subj = obs.getObservingSubject();
        return subj == null ? null : new DirectObservation(subj, binding);
    }

    def unwrap() {
        return obs;
    }

    def getId() {
        return ((Observation)obs).getInternalId();
    }

    def getSpace() {
        return obs.getScale().getSpace() == null ? null : new Space(obs.getScale().getSpace(), binding);
    }

    def getTime() {
        return obs.getScale().getTime() == null ? null : new Time(obs.getScale().getTime(), binding);
    }

    def getScale() {
        return new Scale(obs.getScale(), binding);
    }

    def isSibling(Observation o) {
        return
             o.obs.getContextObservation() != null &&
                obs.getContextObservation() != null &&
                o.obs.getContextObservation() == obs.getContextObservation();
    }

    /*
     * internal use
     */
    protected ILocator getTransition() {
        if (binding.hasVariable('_transition')) {
            return binding.getVariable('_transition');
        }
        return null;
    }

    IMonitor getMonitor() {
        Object o = binding.getVariable("_monitor");
        if (o instanceof IMonitor) {
            return ((IMonitor)o);
        }
        return null;
    }

    def getMetadata() {
        return obs.getMetadata();
    }

    def getContext() {
        IDirectObservation ret = obs.getContextObservation();
        return ret == null ? null : DefaultAction._wrapIfNecessary(ret, binding);
    }
    
    /**
     * Used to force non-scalar usage when we need the object as is and
     * we have scalar usage in the same expression.
     * 
     * @return
     */
    def getSelf() {
        return this;
    }
}
