package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IEvent;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Range;

/**
 * The initial artifact for an instantiated group of objects. Starts empty but
 * with an observable. Can be sorted according to comparators installed by
 * contextualizers. The sorting can be made subjective.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservationGroup extends CountableObservation implements ISubjectiveObservation {

	private IArtifact.Type atype;
	private List<IArtifact> artifacts = new ArrayList<>();
	boolean sorted = false;
	private Comparator<IArtifact> comparator = null;

	/**
	 * Only set in temporal views of event groups.
	 */
	Range temporalHorizon = null;
	private List<IArtifact> currentArtifacts = null;

	protected ObservationGroup(ObservationGroup other) {
		super(other);
		this.atype = other.atype;
		this.artifacts = other.artifacts;
		this.sorted = other.sorted;
		this.comparator = other.comparator;
	}

	public ObservationGroup(Observable observable, Scale scale, IRuntimeScope context, IArtifact.Type type) {
		super(observable.getName(), observable, scale, context);
		this.atype = type;
		IIdentity identity = context.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			setGenerator(((AbstractTask<?>) identity).getActivity());
		}
	}

	@Override
	public DirectObservation at(ILocator locator) {

		if (locator instanceof ITime && getObservable().is(IKimConcept.Type.EVENT)) {
			ObservationGroup ret = new ObservationGroup(this);
			ret.temporalHorizon = Range.create(((ITime) locator).getStart().getMilliseconds(),
					((ITime) locator).getEnd().getMilliseconds());
			ret.subset();
			return ret;
		}

		/*
		 * every other situation should be continuants, so no rescaling (although if we
		 * eventually respond to at() in continuants, we may need to lazily apply it to
		 * the entire group).
		 */

		return this;
	}

	private void subset() {
		currentArtifacts = new ArrayList<>();
		for (IArtifact artifact : artifacts) {
			// TODO
			Range erange = Range.create(((IEvent) artifact).getScale().getTime().getStart().getMilliseconds(),
					((IEvent) artifact).getScale().getTime().getEnd().getMilliseconds());
			if (erange.overlaps(temporalHorizon)) {
				currentArtifacts.add(((Event)artifact).locate(erange.intersection(temporalHorizon)));
			}
		}
	}

	@Override
	public IArtifact.Type getType() {
		return atype;
	}

	@Override
	public boolean isEmpty() {
		return artifacts.isEmpty();
	}

	@Override
	public Iterator<IArtifact> iterator() {
		if (currentArtifacts != null) {
			return currentArtifacts.iterator();
		}
		sort();
		return artifacts.iterator();
	}

	private void sort() {
		if (comparator != null && !sorted) {
			artifacts.sort(comparator);
			sorted = true;
		}
	}

	@Override
	public int groupSize() {
		return artifacts.size();
	}

	@Override
	public void chain(IArtifact data) {
		chain(data, false);
	}

	public void chain(IArtifact data, boolean notify) {
		artifacts.add(data);
		((Observation) data).setGroup(this);
		sorted = false;
		if (notify) {
			ObservationChange change = requireStructureChangeEvent();
			change.setTimestamp(-1);
			change.setNewSize(this.groupSize());
		}
	}

	public void setComparator(Comparator<IArtifact> comparator) {
		this.comparator = comparator;
	}

	@Override
	public void setObserver(IDirectObservation observer) {
		// TODO Auto-generated method stub

	}

	@Override
	public ISubjectiveObservation reinterpret(IDirectObservation observer) {
		throw new IllegalStateException("reinterpret() was called on an illegal or unsupported type");
	}

	@Override
	public IDirectObservation getObserver() {
		// TODO Auto-generated method stub
		return null;
	}

}
