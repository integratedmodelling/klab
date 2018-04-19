package org.integratedmodelling.klab.data.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.integratedmodelling.kim.api.data.ILocator;
import org.integratedmodelling.kim.utils.Utils;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;

/**
 * The state we wrap has the desired semantics but a different scale. We use {@link IScaleMediator mediators}
 * to remap value set and get. Mediators are built as needed.
 * 
 * @author Ferd
 *
 */
public class RescalingState extends Observation implements IState {

    IState delegate;
    Scale newScale;

    List<IScaleMediator> mediatorsFrom = null;
    List<IScaleMediator> mediatorsTo = null;

    public RescalingState(IState state, Scale newScale, IRuntimeContext context) {
        super(new Observable((Observable)state.getObservable()), newScale, context);
        this.delegate = state;
        this.newScale = newScale;
    }

    public Object get(ILocator index) {
        Object val = delegate.get(index);
        if (mediatorsFrom == null) {
            mediatorsFrom = getMediators((Scale)this.delegate.getScale(), this.newScale);
        }
        // TODO apply mediators
        return val;
    }
    
    List<IScaleMediator> getMediators(Scale original, Scale target) {
        List<IScaleMediator> mediators = new ArrayList<>();
        for (IExtent originalExtent : original.getExtents()) {
            IExtent targetExtent = target.getDimension(originalExtent.getType());
            if (targetExtent != null) {
                IScaleMediator mediator = originalExtent.getMediator(targetExtent);
                if (mediator == null) {
                    throw new KlabInternalErrorException("internal: extent.getMediator() returned null");
                }
                mediators.add(mediator);
            }
        }
        return mediators;
    }
    

    public <T> T get(ILocator index, Class<T> cls) {
        return Utils.asType(get(index), cls);
    }

    public long set(ILocator index, Object value) {
        if (mediatorsTo == null) {
            mediatorsTo = getMediators(this.newScale, (Scale)this.delegate.getScale());
        }
        // TODO apply mediators
        //        Object val = value instanceof Number ? from.convert(((Number) value).doubleValue(), to) : value;
        return 0; // delegate.set(index, val);
    }

    public boolean isConstant() {
        return false;
    }
    
//    @Override
//    public Object getValue(int index) {
//
//        /*
//         * mediated state may be covered while this isn't, so
//         * check.
//         */
//        if (scale.isCovered(index)) {
//            Queue<IState.Mediator> meds = new LinkedList<>(mediators);
//            List<IScale.Locator> locs = new LinkedList<>();
//            return reduce(index, meds, locs);
//        }
//        return null;
//    }
//
//    private Object reduce(int index, Queue<Mediator> meds, List<Locator> locs) {
//
//        IState.Mediator m = meds.remove();
//        ArrayList<Pair<Object, Double>> toReduce = new ArrayList<>();
//        
//        /**
//         * FIXME the index passed to each mediator should probably be relative to its own
//         * dimension.
//         */
//        for (IScale.Locator locator : m.getLocators(index)) {
//
//            ArrayList<IScale.Locator> floc = new ArrayList<>(locs);
//            floc.add(locator);
//
//            if (meds.isEmpty()) {
//                Object val = originalState.getValue((int) originalState.getScale()
//                        .locate(floc.toArray(new Locator[floc.size()])));
//                toReduce.add(new Pair<>(val, locator.getWeight()));
//            } else {
//                toReduce.add(new Pair<>(reduce(index, meds, floc), locator.getWeight()));
//            }
//        }
//
//        return m.reduce(toReduce, getMetadata());
//    }


    // Remaining functionality is delegated to original state

    public boolean isDynamic() {
        return delegate.isDynamic();
    }

    @Override
    public long size() {
        return delegate.size();
    }

    @Override
    public IState as(IObservable observable) {
        return delegate.as(observable);
    }
    
    

}
