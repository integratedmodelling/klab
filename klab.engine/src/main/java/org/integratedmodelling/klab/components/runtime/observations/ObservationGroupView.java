package org.integratedmodelling.klab.components.runtime.observations;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.engine.runtime.api.IModificationListener;
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
public class ObservationGroupView extends CountableObservation implements IObservationGroup {
	
	private IArtifact.Type atype;
	private ObservationGroup original;
	private List<Function<IArtifact, Boolean>> filters = new ArrayList<>();
	private List<IArtifact> filtered = null;
	
	/*
	 * like everything, a group is born new, then the actuator ensures the next time
	 * objects are added we should notify a change and not the whole group. Because
	 * the group changes when objects are added or removed, we use the notified size
	 * to assess if it's new.
	 */
	int notifiedSize = -1;

	public ObservationGroupView(Observable observable, IObservation original, Function<IArtifact, Boolean> filter) {
		
		super(observable.getName(), observable, (Scale)original.getScale(), ((Observation)original).getScope());
		this.atype = original.getType();
//		IIdentity identity = getScope().getMonitor().getIdentity();
//		if (identity instanceof AbstractTask) {
//			setGenerator(((AbstractTask<?>) identity).getActivity());
//		}
		if (original instanceof ObservationGroup) {
			this.original = (ObservationGroup)original;
		} else if (original instanceof ObservationGroupView) {
			this.original = ((ObservationGroupView)original).original;
			this.filters.addAll(((ObservationGroupView)original).filters);
		} else {
			throw new IllegalArgumentException("cannot initialize a view with a non-group observation");
		}
		
		this.original.addModificationListener(new IModificationListener() {
			
			@Override
			public void onModification(IObservation modified) {
				// reset the filtered list on modification
				filtered = null;
			}

            @Override
            public void onFirstNontrivialState(Object state, ITime currentTime) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void onTemporalExtension(ITime time) {
                // TODO Auto-generated method stub
                
            }
		});
		
		filters.add(filter);
	}

	public ObservationGroup getOriginalGroup() {
		return original;
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
		return filter().isEmpty();
	}

	public boolean isNew() {
		return groupSize() != notifiedSize;
	}

	public void setNew(boolean b) {
		this.notifiedSize = groupSize();
	}

	@Override
	public Iterator<IArtifact> iterator() {
		return filter().iterator();
	}

	@Override
	public int groupSize() {
		return filter().size();
	}
	
	public List<IArtifact> filter() {
		if (this.filtered == null) {
			this.filtered = new ArrayList<>();
			for (IArtifact artifact : original) {
				boolean ok = true;
				for (Function<IArtifact, Boolean> filter : filters) {
					if (!filter.apply(artifact)) {
						ok = false;
						break;
					}
				}
				if (ok) {
					this.filtered.add(artifact);
				}
			}
		}
		return this.filtered;
	}

	@Override
	public void chain(IArtifact data) {
		original.chain(data);
		filtered = null;
	}

//	@Override
//	public void setObserver(IDirectObservation observer) {
//		original.setObserver(observer);
//		filtered = null;
//	}

//	@Override
//	public ISubjectiveObservation reinterpret(IDirectObservation observer) {
//		filtered = null;
//		original = (ObservationGroup) original.reinterpret(observer);
//		return this;
//	}

//	@Override
//	public IDirectObservation getObserver() {
//		return original.getObserver();
//	}

}
