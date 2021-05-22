package org.integratedmodelling.klab.extensions.groovy.model

import org.integratedmodelling.kim.api.IKimConcept.Type
import org.integratedmodelling.klab.Observations
import org.integratedmodelling.klab.Roles
import org.integratedmodelling.klab.Traits
import org.integratedmodelling.klab.api.knowledge.IConcept
import org.integratedmodelling.klab.api.knowledge.ISemantic
import org.integratedmodelling.klab.api.model.IModel
import org.integratedmodelling.klab.api.observations.IObservation
import org.integratedmodelling.klab.api.observations.IState
import org.integratedmodelling.klab.api.observations.scale.IScale
import org.integratedmodelling.klab.api.observations.scale.time.ITime
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor
import org.integratedmodelling.klab.engine.runtime.code.groovy.Wrapper

abstract class Observation<T extends IObservation> extends Wrapper<T> {

	String name;
	IModel model;

	def Observation(IObservation observation, Binding binding) {
		super(observation, binding);
		if (binding.hasVariable("_model")) {
			this.model = binding.getVariable("_model");
		}
	}

	def Observation(String id, Binding binding) {
		super(id, binding);
		if (binding.hasVariable("_model")) {
			this.model = binding.getVariable("_model");
		}
	}

	protected IScale getTransitionScale() {
		ITime scopeTime = getScope().getScale().getTime();
		org.integratedmodelling.klab.scale.Scale ret = (org.integratedmodelling.klab.scale.Scale)unwrap().getScale();
		if (scopeTime != null && ret.getTime() != null && ret.getTime().size() > 1) {
			return ret.at(scopeTime);
		}
		return ret;
	}

	protected long getTimeIndex() {
		long time = -1;
		if (getScope() != null && getScope().getScale() instanceof IScale && getScope().getScale().getTime() != null) {
			time = getScope().getScale().getTime().getStart().getMilliseconds();
		}
		return time;
	}

	def isa(Object o) {

		if (o instanceof Concept) {
			o = ((Concept)o).concept;
		}
		if (!(o instanceof ISemantic)) {
			return false;
		}
		IConcept c = ((ISemantic)o).getType();
		if (c.is(Type.TRAIT)) {
			return Traits.INSTANCE.hasTrait(unwrap().getObservable().getType(), c);
		}
		if (c.is(Type.ROLE)) {
			return Roles.INSTANCE.hasRole(unwrap().getObservable().getType(), c);
		}
		return unwrap().getObservable().getType().is(c);
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
		return new Concept(unwrap().observable.type, binding);
	}

	def getObserver() {
		return new DirectObservation(unwrap().observable.observer, binding);
	}

	def getId() {
		return unwrap().id;
	}

	def getSpace() {
		return unwrap().scale.space == null ? null : new Space(unwrap().scale.space, binding);
	}

	def getTime() {
		return unwrap().scale.time == null ? null : new Time(unwrap().scale.time, binding);
	}

	def getScale() {
		return new Scale(unwrap().getScale(), binding);
	}

	def isSibling(Observation o) {
		unwrap().context != null &&
				unwrap().context != null &&
				o.unwrap().context == unwrap().context;
	}

	IMonitor getMonitor() {
		Object o = binding.getVariable("_monitor");
		if (o instanceof IMonitor) {
			return ((IMonitor)o);
		}
		return null;
	}

	def getMetadata() {
		return unwrap().metadata;
	}

	def getContext() {
		return new DirectObservation(unwrap().context, binding);
	}

    def getDisplayLabel() {
        return Observations.INSTANCE.getDisplayLabel(unwrap());
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
