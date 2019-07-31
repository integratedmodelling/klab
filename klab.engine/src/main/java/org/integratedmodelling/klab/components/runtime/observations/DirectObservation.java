package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;

public abstract class DirectObservation extends Observation implements IDirectObservation {

	String name;

	// contains the IDs of any subjective observations that we have made.
	private Set<String> subjectivelyObserved = new HashSet<>();

	/*
	 * Predicates added to the observable by a classification which we still need to
	 * resolve. These are added after classification and removed after resolution.
	 * Use a linked hash set to return them for resolution in order of addition -
	 * probably not useful but who knows.
	 */
	private Set<IConcept> predicatesToResolve = new LinkedHashSet<>();

	/*
	 * Any modification that needs to be reported to clients is recorded here
	 */
	private List<ObservationChange> modificationsToReport = new ArrayList<>();

	protected DirectObservation(String name, Observable observable, Scale scale, IRuntimeContext context) {
		super(observable, scale, context);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<IState> getStates() {
		return getRuntimeContext().getChildren(this, IState.class);
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
		return getRuntimeContext().getChildren(this, cls);
	}

	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.OBJECT;
	}

	public void setName(String name) {
		this.name = name;
	}

	public IDirectObservation at(ILocator locator) {
//    	Logging.INSTANCE.warn("RELOCATION OF DIRECT OBSERVATION IS UNIMPLEMENTED!");
		return this;
	}

	public void addSubjectiveObservation(ISubjectiveObservation subjectiveObservation) {
		subjectivelyObserved.add(subjectiveObservation.getId());
	}

	/**
	 * Add a predicate, adjust the observable as needed and enqueue a modification
	 * message for the clients.
	 * 
	 * @param c
	 */
	public void addPredicate(IConcept c) {
		if (this.predicatesToResolve.add(c)) {

			// the observable gets the trait or role
			
			// record the modification
			
		}
	}

	/**
	 * Get any modification that still needs to be reported and reset our list to
	 * empty.
	 * 
	 * @return
	 */
	public List<ObservationChange> getChangesAndReset() {
		List<ObservationChange> ret = this.modificationsToReport;
		this.modificationsToReport = new ArrayList<>();
		return ret;
	}

	public IConcept nextPredicateToResolve() {
		IConcept ret = null;
		if (!predicatesToResolve.isEmpty()) {
			ret = predicatesToResolve.iterator().next();
			predicatesToResolve.remove(ret);
		}
		return ret;
	}

	/**
	 * The set of IDs of any subjective observation that contain this as an
	 * observer.
	 * 
	 * @return
	 */
	public Collection<String> getSubjectiveObservationIds() {
		return subjectivelyObserved;
	}

	@Override
	public Collection<IArtifact> getChildArtifacts() {
		return getRuntimeContext().getChildArtifactsOf(this);
	}

}
