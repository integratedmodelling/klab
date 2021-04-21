package org.integratedmodelling.klab.components.localstorage.impl;

import java.util.Date;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.localstorage.impl.AbstractAdaptiveStorage.Slice;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;

/**
 * A special-purpose locator that will point to one specific timeslice in this specific
 * implementation of State. Used to quickly download the different temporal states of a state.
 * 
 * @author Ferd
 *
 */
public class TimesliceLocator extends Time {

    int sliceIndex;
    String label;

    @SuppressWarnings("rawtypes")
    TimesliceLocator(AbstractAdaptiveStorage<?> storage, Slice slice, int sliceIndex) {
        super(new TimeInstant(slice.timestart), new TimeInstant(slice.timeend));
        this.sliceIndex = sliceIndex;
        ITime overall = storage.getState().getScale().getTime();
        this.label = slice.isInitialization()
                ? (overall == null ? "Initialization" : ("Before " + overall.getStart()))
                : (new Date(slice.timestart) + " to " + new Date(slice.timeend));
    }

    
    public String getLabel() {
        return label;
    }

}
