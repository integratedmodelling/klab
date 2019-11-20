package org.integratedmodelling.klab.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.kim.api.IKimAction.Trigger;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAction;
import org.integratedmodelling.klab.api.model.IActiveKimObject;
import org.integratedmodelling.klab.api.model.IBehavior;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class Behavior implements IBehavior {

	List<IAction> actions = new ArrayList<>();
	List<IExtent> extents;
	IKimBehavior statement;
	boolean spatial;
	boolean temporal;

	public Behavior(IKimBehavior behavior, IActiveKimObject model) {
		this.statement = behavior;
		if (behavior != null) {
			for (IKimAction action : behavior) {
				actions.add(new Action(action));
				if (model instanceof Model) {
					/*
					 * Fix the trigger according to semantics and role
					 */
					List<IObservable> observables = ((Model) model).getObservables();
					if (observables.size() > 0 && observables.get(0).getArtifactType().isOccurrent()) {
						// resolution becomes transition
					}
				}
			}
		}
	}

	@Override
	public Iterator<IAction> iterator() {
		return actions.iterator();
	}

	/**
	 * True if the behavior defines a scale for the containing object. Used during
	 * resolution.
	 * 
	 * @return true if scale is defined
	 */
	public boolean hasScale() {
		return this.extents != null && !this.extents.isEmpty();
	}

	@Override
	public boolean isSpatial() {
		return spatial;
	}

	@Override
	public boolean isTemporal() {
		return temporal;
	}

	@Override
	public Collection<IExtent> getExtents(IMonitor monitor) throws KlabException {
		if (this.extents == null) {
			this.extents = new ArrayList<>();
			if (statement != null) {
				for (IServiceCall extentFunction : statement.getExtentFunctions()) {
					/*
					 * legacy behavior: for now only call the function if there are arguments,
					 * otherwise it's just a constraint that should be created from the outside
					 */
					IPrototype prototype = extentFunction.getPrototype();
					if (extentFunction.getParameterCount() == 0) {
						if (extentFunction.getName().equals("space")
								|| (prototype != null && prototype.getType() == Type.SPATIALEXTENT)) {
							spatial = true;
						}
						if (extentFunction.getName().equals("time")
								|| (prototype != null && prototype.getType() == Type.TEMPORALEXTENT)) {
							temporal = true;
						}
					} else {
						Object extent = Extensions.INSTANCE.callFunction(extentFunction, monitor);
						if (!(extent instanceof IExtent)) {
							throw new KlabValidationException(
									"function " + extentFunction + " does not produce a valid extent");
						}
						this.extents.add((IExtent) extent);
					}
				}
			}
		}
		return this.extents;
	}

	@Override
	public List<IAction> getActions(Trigger trigger) {
		List<IAction> ret = new ArrayList<>();
		for (IAction action : actions) {
			if (action.getTrigger() == trigger) {
				ret.add(action);
			}
		}
		return ret;
	}

	@Override
	public boolean respondsTo(IConcept eventType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		return actions.isEmpty();
	}

}
