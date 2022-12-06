package org.integratedmodelling.klab.api.runtime.monitoring;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IObservable;

/**
 * Anonymized observation activity class with builder, made for monitoring. The monitor gets one
 * when appropriate and decides what to do with it: nothing, on-the-spot stat collection, or sending
 * to a statistics service.
 * 
 * @author Ferd
 *
 */
public interface IActivity {

    enum Source {
        API,
        EXPLORER,
        APPLICATION,
        CODE
    }

    enum Platform {
        LOCAL_ENGINE,
        PUBLIC_ENGINE
    }

    /**
     * If null, this activity built a context. Otherwise it's an observation made in it.
     * 
     * @return
     */
    String getContextId();

    /**
     * Build an activity incrementally. The result of build() should be passed to the monitor.
     * 
     * @author Ferd
     *
     */
    interface Builder {

        /**
         * If this isn't called, start should be set to the time the builder is created.
         * 
         * @return
         */
        Builder start();

        /**
         * Successful end. After this no more activity should be possible.
         * 
         * @return
         */
        Builder success();

        /**
         * Intentional interruption. After this no more activity should be possible.
         * 
         * @return
         */
        Builder interrupt();

        /**
         * Unintentional interruption for controlled causes (no exception). After this no more
         * activity should be possible.
         * 
         * @return
         */
        Builder error();

        /**
         * Intentional interruption. After this no more activity should be possible.
         * 
         * @return
         */
        Builder exception(Throwable e);

        Builder withObservable(IObservable observable);

        Builder withGeometry(IGeometry geometry);

        /**
         * Should throw an exception if none of the finish methods was called.
         * 
         * @return
         */
        IActivity build();

    }

}
