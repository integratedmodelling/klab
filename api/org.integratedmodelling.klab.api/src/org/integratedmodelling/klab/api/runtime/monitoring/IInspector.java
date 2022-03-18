package org.integratedmodelling.klab.api.runtime.monitoring;

import org.integratedmodelling.kim.api.ValueOperator;

/**
 * The monitor may carry an inspector which, if present, is notified of a variety of possible
 * situations and events useful for debugging and profiling. The inspector may be
 * activated/deactivated and programmed from k.Actors or the CLI.
 * 
 * @author Ferd
 *
 */
public interface IInspector {

    // the subject of the recording
    public enum Asset {
        MODEL, RESOURCE, OBSERVATION, STATE_SLICE, ACTUATOR, DATAFLOW, SCHEDULE
    }

    // what to record
    public enum Metric {
    }

    // when to record
    public enum Event {
        CREATION, START, FIRST_ACCESS, FIRST_READ, FIRST_WRITE, FINISH
    }

    /**
     * User installs a trigger calling this one. Arguments are recognized based on type. Can use any
     * of the internal enums, along with {@link ValueOperator}; triggers will be built based on the
     * sequence of the arguments as well as the types.
     * 
     * @param triggerArguments
     */
    void setTrigger(Object... triggerArguments);

    /**
     * Code will call this at monitorable points; any installed triggers will be activated when
     * matching.
     * 
     * @param triggerArguments
     */
    void trigger(Object... triggerArguments);

}
