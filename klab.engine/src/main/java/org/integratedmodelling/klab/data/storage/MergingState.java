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
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.components.geospace.extents.Envelope;
import org.integratedmodelling.klab.components.runtime.observations.ObservationGroup;
import org.integratedmodelling.klab.components.runtime.observations.State;
import org.integratedmodelling.klab.data.Aggregator;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

import com.vividsolutions.jts.index.strtree.STRtree;

/**
 * A state that merges others and remaps space and time locators to retrieve data from them,
 * aggregating or disaggregating if needed.
 * 
 * @author Ferd
 *
 */
public class MergingState extends State {

    IState delegate;
    STRtree spatialIndex;
    List<IState> states = new ArrayList<>();
    private boolean indexBuilt;

    class StateLocator {
        public IShape shape;
        public IState state;
    }

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

    public static MergingState promote(IState state, Collection<?> distributingArtifacts) {
        MergingState ret = new MergingState(state);
        ret.getAnnotations().addAll(Annotations.INSTANCE.collectAnnotations(state, ((IState) state).getObservable()));
        for (Object dis : distributingArtifacts) {
            if (dis instanceof ObservationGroup) {
                IArtifact distributingArtifact = (IArtifact) dis;
                for (IArtifact object : distributingArtifact) {
                    if (object instanceof IDirectObservation) {
                        for (IState ostate : ((IDirectObservation) object).getStates()) {
                            if (state.getObservable().getType().is(ostate.getObservable())) {
                                ret.add(ostate);
                            }
                        }
                    }
                }
            } else if (dis instanceof IDirectObservation) {
                for (IState ostate : ((IDirectObservation) dis).getStates()) {
                    if (state.getObservable().getType().is(ostate.getObservable())) {
                        ret.add(ostate);
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
        states.add(state);
        if (state.getScale().getSpace() != null) {
            if (spatialIndex == null) {
                if (this.indexBuilt) {
                    throw new KlabIllegalStateException("internal: spatial index for merging state is already built");
                }
                spatialIndex = new STRtree();
            }
            StateLocator data = new StateLocator();
            data.state = state;
            data.shape = state.getScale().getSpace().getShape();
            spatialIndex.insert(((Envelope) state.getScale().getSpace().getEnvelope()).getJTSEnvelope(), data);
        }
    }

    private List<IState> applicable(ILocator locator) {
        if (spatialIndex != null) {
            if (!indexBuilt) {
                synchronized (this) {
                    spatialIndex.build();
                    this.indexBuilt = true;
                }
            }
            ISpace location = locator.as(ISpace.class);
            if (location != null) {
                List<IState> ret = new ArrayList<>();
                for (Object o : spatialIndex.query(((Envelope) location.getEnvelope()).getJTSEnvelope())) {
                    ret.add(((StateLocator) o).state);
                }
                return ret;
            }
        }
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
            throw new KlabIllegalArgumentException("MergingState: cannot merge states unless the locator is a scale");
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
                while(iterator.hasNext()) {
                    Offset offset = iterator.next();
                    aggregator.add(state.get(offset), state.getObservable(), index);
                }
            }
        }

        return aggregator.aggregate();
    }

    public long set(ILocator index, Object value) {
        throw new KlabIllegalStateException("Merging states are read-only");
    }

}
