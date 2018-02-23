package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

/**
 * This service holds the catalog of the k.IM annotations recognized by the system and acts as a gateway for
 * their processing. It also provides the point of extension for supporting annotations other than the system ones.
 * 
 * @author ferdinando.villa
 *
 */
public interface IAnnotationService {

    /**
     * If an annotation is recognized, return the corresponding
     * prototype. Otherwise return null.
     * 
     * @param annotation
     * @return 
     */
    IPrototype getPrototype(String annotation);
    
    
    /**
     * Process the passed annotation on the passed object.
     * 
     * @param annotation
     * @param object
     * @param monitor 
     * @return
     */
    Object process(IKimAnnotation annotation, IKimObject object, IMonitor monitor);
    
}
