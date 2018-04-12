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
package org.integratedmodelling.klab.api.model;

/**
 * Any object that is part of a namespace implements this interface, which
 * simply provides access to the namespace the object is part of.
 *
 * @author fvilla
 * @version $Id: $Id
 */
public interface INamespaceQualified {

    /**
     * Return the namespace this is part of.
     *
     * @return namespace this belongs to.
     */
    INamespace getNamespace();
    
}
