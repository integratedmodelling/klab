package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.indexing.SpatialIndex;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * A state that merges others and remaps space and time locators to retrieve
 * data from them, aggregating or disaggregating if needed.
 * 
 * @author Ferd
 *
 */
public class MergingState extends State {

	IState delegate;
	SpatialIndex spatialIndex;

	List<IState> states = new ArrayList<>();

	public static MergingState promote(IState state, IArtifact distributingArtifact) {
		MergingState ret = new MergingState(state);
		ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(state, ((IState) state).getObservable()));
		for (IArtifact object : distributingArtifact) {
			if (object instanceof IDirectObservation) {
				for (IState ostate : ((IDirectObservation) object).getStates()) {
					if (ostate.getObservable().getType().is(state.getObservable())) {
						ret.add(ostate);
					}
				}
			}
		}
		return ret;
	}

	public static MergingState promote(IState state, Collection<IArtifact> distributingArtifacts) {
		MergingState ret = new MergingState(state);
		ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(state, ((IState) state).getObservable()));
		for (IArtifact distributingArtifact : distributingArtifacts) {
			for (IArtifact object : distributingArtifact) {
				if (object instanceof IDirectObservation) {
					for (IState ostate : ((IDirectObservation) object).getStates()) {
						if (ostate.getObservable().getType().is(state.getObservable())) {
							ret.add(ostate);
						}
					}
				}
			}
		}
		return ret;
	}

	public MergingState(IState delegate) {
		super((Observable) delegate.getObservable(), (Scale) delegate.getScale(), ((State) delegate).getScope(),
				((State) delegate).getStorage());
		this.delegate = delegate;
	}

	public void add(IState state) {
		// TODO index
		states.add(state);
	}

	private List<IState> applicable(ILocator locator) {
		// TODO filter QUICKLY, or don't.
		return states;
	}

	@Override
	public boolean isDynamic() {
		for (IState state : states) {
			if (state.isDynamic()) {
				return true;
			}
		}
		return false;
	}

	public Object get(ILocator index) {

		Aggregator aggregator = new Aggregator(delegate.getObservable(), delegate.getMonitor());

		if (!(index instanceof IScale)) {
			throw new IllegalArgumentException("MergingState: cannot merge states unless the locator is a scale");
		}
		IScale scale = (IScale) index;

		for (IState state : applicable(index)) {

			List<IExtent> exts = new ArrayList<>();
			for (IExtent ext : ((Scale) scale).getExtents()) {
				IExtent oex = ((Scale) state.getScale()).getExtent(ext.getType()).at(ext);
				if (oex == null) {
					break;
				}
				exts.add(oex);
			}

			if (exts.size() == scale.getExtentCount()) {

				OffsetIterator iterator = new OffsetIterator(state.getScale(), exts);
				while (iterator.hasNext()) {
					Offset offset = iterator.next();
					aggregator.add(state.get(offset), state.getObservable(), offset);
				}
			}
		}

		return aggregator.getAndReset(index);
	}

	public long set(ILocator index, Object value) {
		throw new IllegalStateException("Merging states are read-only");
	}

}
