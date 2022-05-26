package org.integratedmodelling.klab.api.runtime.monitoring;

import java.util.List;
import java.util.function.BiConsumer;

import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * The monitor may carry an inspector which, if present, is notified of a variety of possible
 * situations and events useful for debugging and profiling. The inspector may be
 * activated/deactivated and programmed from k.Actors or the CLI.
 * 
 * @author Ferd
 *
 */
public interface IInspector {

    /**
     * The trigger passed to the handler when a configured trigger matches.
     * 
     * @author Ferd
     *
     */
    interface Trigger {

        /**
         * Same as configured
         * 
         * @return
         */
        Asset getAsset();

        /**
         * Same as configured
         * 
         * @return
         */
        Metric getMetric();

        /**
         * Same as configured
         * 
         * @return
         */
        Event getEvent();

        /**
         * The actual asset that triggered this
         * 
         * @return
         */
        Object getSubject();

        /**
         * If applicable, the trigger value or object that matched any trigger condition (e.g. if
         * trigger is mean > 1000, the actual mean)
         * 
         * @return
         */
        Object getTriggerValue();

        /**
         * If any metric is requested, this will return it.
         * 
         * @return
         */
        Object getResult();

        /**
         * Any trigger from code may add data in addition to the subject. This will report all data
         * sent to the trigger function of the inspector, INCLUDING the main subject.
         * 
         * @return
         */
        List<Object> getData();
    }

    // the subject of the recording
    public enum Asset {
        MODEL, RESOURCE, OBSERVATION, STATE_SLICE, ACTUATOR, DATAFLOW, SCHEDULE
    }

    // What to record,if anything
    public enum Metric {
        
        /**
         * Statistics applies to states and is a map of data statistics indexed by time slice,
         * collected during contextualization.
         */
        STATISTICS
    }

    // when to record
    public enum Event {
        CREATION, SELECTION, START, FIRST_ACCESS, FIRST_READ, FIRST_WRITE, FINISH, ADD_DATA
    }

    // for future use: for now contextualization just continues
    public enum Action {
        /**
         * Trigger the action and move on, the only supported for now
         */
        CONTINUE,

        /**
         * Pause in inspector until user does something
         */
        PAUSE,

        /**
         * Stop contextualization and reset
         */
        FAIL
    }

    /**
     * User installs a trigger calling this one. Arguments are recognized based on type. Can use any
     * of the internal enums, along with {@link ValueOperator}; triggers will be built based on the
     * sequence of the arguments as well as the types.
     * 
     * @param triggerArguments
     */
    void setTrigger(BiConsumer<Trigger, IContextualizationScope> handler, Object... triggerArguments);

    /**
     * Code will call this at monitorable points; any installed triggers will be activated when
     * matching.
     * 
     * @param triggerArguments
     */
    void trigger(IContextualizationScope scope, Object... triggerArguments);

}
