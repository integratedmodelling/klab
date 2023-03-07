package org.integratedmodelling.klab.configuration;

import org.integratedmodelling.klab.api.services.KEngine;
import org.integratedmodelling.klab.api.services.KReasoner;
import org.integratedmodelling.klab.api.services.KResolver;
import org.integratedmodelling.klab.api.services.KResources;
import org.integratedmodelling.klab.api.services.KRuntime;

/**
 * Makes the k.LAB services available globally through self-notification on injection. Each service
 * implementation is required to manually register itself. Needed by small objects such as concepts
 * and observables, unless we want to implement them all as non-static embedded classes.
 * 
 * @author Ferd
 *
 */
public enum Services {

    INSTANCE;

    private KReasoner reasoner;
    private KResources resources;
    private KEngine engine;
    private KResolver resolver;
    private KRuntime runtime;
    public KReasoner getReasoner() {
        return reasoner;
    }
    public void setReasoner(KReasoner reasoner) {
        this.reasoner = reasoner;
    }
    public KResources getResources() {
        return resources;
    }
    public void setResources(KResources resources) {
        this.resources = resources;
    }
    public KEngine getEngine() {
        return engine;
    }
    public void setEngine(KEngine engine) {
        this.engine = engine;
    }
    public KResolver getResolver() {
        return resolver;
    }
    public void setResolver(KResolver resolver) {
        this.resolver = resolver;
    }
    public KRuntime getRuntime() {
        return runtime;
    }
    public void setRuntime(KRuntime runtime) {
        this.runtime = runtime;
    }

}
