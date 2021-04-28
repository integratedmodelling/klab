package org.integratedmodelling.klab.components.localstorage.impl;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
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
        boolean years = overall != null && overall.getResolution().getType() == Resolution.Type.YEAR;

        String start = "", end = "";
        if (!slice.isInitialization()) {
            ITimeInstant st = new TimeInstant(slice.timestart);
            ITimeInstant en = new TimeInstant(slice.timeend);
            start = years ? ("" + st.getYear()) : st.toString();
            end = years ? ("" + en.getYear()) : en.toString();
        }

        this.label = slice.isInitialization()
                ? (overall == null
                        ? "Initialization"
                        : ("Before " + (years ? ("" + overall.getStart().getYear()) : overall.getStart().toString())))
                : (start + " to " + end);
                
        if (sliceIndex == 0) {
            this.setTimeType(ITime.Type.INITIALIZATION);
        }
    }

    public String getLabel() {
        return label;
    }
    
    public String getLocatorCode() {
        return "tloc:" + sliceIndex;
    }

}
