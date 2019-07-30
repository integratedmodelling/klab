package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubjectiveObservation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.AbstractTask;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

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

	/*
	 * like everything, a group is born new, then the actuator ensures the next time
	 * objects are added we should notify a change and not the whole group. Because
	 * the group changes when objects are added or removed, we use the notified size
	 * to assess if it's new.
	 */
	int notifiedSize = -1;

	public ObservationGroup(Observable observable, Scale scale, IRuntimeContext context, IArtifact.Type type) {
		super(observable.getName(), observable, scale, context);
		this.atype = type;
		IIdentity identity = context.getMonitor().getIdentity();
		if (identity instanceof AbstractTask) {
			setGenerator(((AbstractTask<?>) identity).getActivity());
		}
	}

	@Override
	public DirectObservation at(ILocator locator) {
		// TODO may need to at() all in the group? So far this only gets called if the
		// group is empty.
		return this;
	}

	@Override
	public IArtifact.Type getType() {
		return atype;
	}

	@Override
	public boolean isEmpty() {
		return artifacts.isEmpty();
	}

	public boolean isNew() {
		return groupSize() != notifiedSize;
	}

	public void setNew(boolean b) {
		this.notifiedSize = groupSize();
	}

	@Override
	public Iterator<IArtifact> iterator() {
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
		artifacts.add(data);
		((Observation) data).setGroup(this);
		sorted = false;
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
