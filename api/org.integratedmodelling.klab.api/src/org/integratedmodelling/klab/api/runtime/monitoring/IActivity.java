package org.integratedmodelling.klab.api.runtime.monitoring;

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


}
