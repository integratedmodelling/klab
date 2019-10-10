package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Triple;

public abstract class DirectObservation extends Observation implements IDirectObservation {

	String name;
	private boolean active = true;

	// contains the IDs of any subjective observations that we have made.
	private Set<String> subjectivelyObserved = new HashSet<>();

	/*
	 * Predicates added to the observable by a classification which we still need to
	 * resolve. These are added after classification and removed after resolution.
	 * Use a linked hash set to return them for resolution in order of addition -
	 * probably not useful but who knows.
	 */
	private Set<IConcept> predicates = new LinkedHashSet<>();

	protected DirectObservation(String name, Observable observable, Scale scale, IRuntimeScope context) {
		super(observable, scale, context);
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Collection<IState> getStates() {
		return getRuntimeScope().getChildren(this, IState.class);
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
		return getRuntimeScope().getChildren(this, cls);
	}

	public IObservation getChildArtifact(String name) {
		for (IArtifact artifact : this.getRuntimeScope().getChildArtifactsOf(this)) {
			if ((artifact instanceof IDirectObservation && ((IDirectObservation) artifact).getName().equals(name))
					|| (artifact instanceof IState && ((IState) artifact).getObservable().getName().equals(name))) {
				return (IObservation) artifact;
			}
		}
		return null;
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
	 * @param predicate
	 */
	public void addPredicate(IConcept predicate) {

		if (this.predicates.add(predicate)) {

			IObservable.Builder builder = getObservable().getBuilder(getRuntimeScope().getMonitor());

			if (predicate.is(IKimConcept.Type.ROLE)) {
				builder.withRole(predicate);
			} else if (predicate.is(IKimConcept.Type.TRAIT)) {
				builder.withTrait(predicate);
			}

			this.setObservable((Observable) builder.buildObservable());

			// record the modification
			ObservationChange change = new ObservationChange();
			change.setNewAttributes(new ArrayList<>());
			change.getNewAttributes()
					.add(new Triple<>(predicate.toString(), Concepts.INSTANCE.getDisplayLabel(predicate),
							predicate.getMetadata().get(IMetadata.DC_COMMENT, String.class)));
			change.setNewSemantics(this.getObservable().getDefinition());
			this.reportChange(change);
		}
	}

	public Set<IConcept> getPredicates() {
		return this.predicates;
	}
	
	public void removePredicate(IConcept predicate) {

		if (this.predicates.remove(predicate)) {

			IObservable.Builder builder = getObservable().getBuilder(getRuntimeScope().getMonitor())
					.without(predicate);

			this.setObservable((Observable) builder.buildObservable());

			/*
			 * this can, so far, only be called when the previous addition is rejected due
			 * to a characterizer returning false, so it should just remove the
			 * notification, after ensuring that there is a notification for the
			 * attribution. Otherwise add another with the changed semantics.
			 */
			if (!findAndRemoveAttribution(predicate)) {
				ObservationChange change = new ObservationChange();
				change.setNewSemantics(this.getObservable().getDefinition());
				this.reportChange(change);
			}
		}

	}

	private boolean findAndRemoveAttribution(IConcept predicate) {
		// TODO Auto-generated method stub
		return false;
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
		return getRuntimeScope().getChildArtifactsOf(this);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

}
