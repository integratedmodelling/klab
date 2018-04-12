/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
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
