package org.integratedmodelling.klab.owl;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;

public class ConfigurationDetector {

    /**
     * These describe the configuration from the worldview, and get notified of all the triggers and
     * enablements connected to it by models.
     * 
     * @author Ferd
     *
     */
    class ConfigurationDescriptor {

    }

    /**
     * These get recorded in context observations and updated by runtime scopes, which should call
     * update() on each of the notified configurations at each new observation in each context.
     * 
     * @author Ferd
     *
     */
    public class Configuration {

    }

    public void registerConfiguration(IKimConceptStatement configuration) {
        
    }
    
    /**
     * Call at each new observation (groups for instantiators). If a DirectObservation is passed,
     * this will use the cache in it to store partial matches and resolve them. If non-null is
     * returned, there is a new configuration and nothing is done in the observation except removing
     * any related cache entries.
     * 
     * @param context
     * @param newObservation
     * @param scope
     * @return
     */
    public Configuration detectConfiguration(IDirectObservation context, IObservation newObservation, IRuntimeScope scope) {
        return null;
    }

}
