package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.components.runtime.RuntimeScope;
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

	protected DirectObservation(DirectObservation other) {
		super(other);
		this.name = other.name;
		this.active = other.active;
		this.predicates.addAll(other.predicates);
	}

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
		return getScope().getChildren(this, IState.class);
	}

	@Override
	public IObservation getChildObservation(IObservable observable) {
		for (IArtifact child : getScope().getChildArtifactsOf(this)) {
			if (child instanceof IObservation && ((RuntimeScope) this.getScope())
					.cached_is(((IObservation) child).getObservable().getType(), observable.getType())) {
				return (IObservation) child;
			}
		}
		return null;
	}

	/**
	 * Return the first observation that resolves the passed observable in the
	 * context of this one, reassessing the context if the observable comes with an
	 * explicit 'within' clause.
	 * 
	 * @param observable
	 * @return
	 */
	public IObservation getObservationResolving(IObservable observable) {
		
		/*
		 * TODO contextualized dynamic states must resolve 'change in' X
		 */
		IConcept changing = null;
		if (observable.is(IKimConcept.Type.CHANGE)) {
			changing = Observables.INSTANCE.getDescribedType(observable.getType());
		}
		
		for (IArtifact child : getScope().getChildArtifactsOf(this)) {
			// TODO should we use resolvesStrictly, which doesn't admit a non-equal observable concept?
			if (child instanceof IObservation && ((IObservation) child).getObservable()
					.resolves(changing == null ? observable : Observable.promote(changing), getObservable().getType())) {
				if (changing != null) {
					return child instanceof Observation && ((Observation)child).isDynamic() ? (IObservation)child : null;
				}
				return (IObservation) child;
			}
		}
		return null;
	}

	@Override
	public <T extends IArtifact> Collection<T> getChildren(Class<T> cls) {
		return getScope().getChildren(this, cls);
	}

	public IObservation getChildArtifact(String name) {
		for (IArtifact artifact : this.getScope().getChildArtifactsOf(this)) {
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
		this.getObservable().setName(name);
	}

	public IDirectObservation at(ILocator locator) {
//    	Logging.INSTANCE.warn("RELOCATION OF DIRECT OBSERVATION IS UNIMPLEMENTED!");
		return this;
	}
//
//	public void addSubjectiveObservation(ISubjectiveObservation subjectiveObservation) {
//		subjectivelyObserved.add(subjectiveObservation.getId());
//	}

	/**
	 * Add a predicate, adjust the observable as needed and enqueue a modification
	 * message for the clients.
	 * 
	 * @param predicate
	 */
	public void addPredicate(IConcept predicate) {

		if (this.predicates.add(predicate)) {

			IObservable.Builder builder = getObservable().getBuilder(getScope().getMonitor());

			if (predicate.is(IKimConcept.Type.ROLE)) {
				builder.withRole(predicate);
			} else if (predicate.is(IKimConcept.Type.TRAIT)) {
				builder.withTrait(predicate);
			}

			this.setObservable((Observable) builder.buildObservable());

			// record the modification
			ObservationChange change = new ObservationChange();
			change.setContextId(getScope().getRootSubject().getId());
			change.setId(this.getId());
			change.setTimestamp(-1);
			change.setType(ObservationChange.Type.AttributeChange);
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

			IObservable.Builder builder = getObservable().getBuilder(getScope().getMonitor()).without(predicate);

			this.setObservable((Observable) builder.buildObservable());

			/*
			 * this can, so far, only be called when the previous addition is rejected due
			 * to a characterizer returning false, so it should just remove the
			 * notification, after ensuring that there is a notification for the
			 * attribution. Otherwise add another with the changed semantics.
			 */
			if (!findAndRemoveAttribution(predicate)) {
				ObservationChange change = new ObservationChange();
				change.setContextId(getScope().getRootSubject().getId());
				change.setId(this.getId());
				change.setTimestamp(-1);
				change.setType(ObservationChange.Type.SemanticsChange);
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
		return getScope().getChildArtifactsOf(this);
	}

	@Override
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Override
	public String dump() {
		return "";
	}

//	public Map<String, Configuration> getConfigurationCache() {
//		return configurationCache;
//	}

}
