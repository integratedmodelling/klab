package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * The initial artifact for an instantiated group of objects. Starts empty but
 * with an observable. Can be sorted according to comparators installed by
 * contextualizers.
 * 
 * @author ferdinando.villa
 *
 */
public class ObservationGroup extends CountableObservation {

	private IArtifact.Type atype;
	private List<IArtifact> artifacts = new ArrayList<>();
	boolean sorted = false;
	private Comparator<IArtifact> comparator = null;

	public ObservationGroup(Observable observable, Scale scale, IRuntimeContext context, IArtifact.Type type) {
		super(observable.getName(), observable, scale, context);
		this.atype = type;
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

}
