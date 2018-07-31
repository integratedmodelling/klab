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
package org.integratedmodelling.klab.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class KlabResourceNotFoundException.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class KlabResourceNotFoundException extends KlabException {

    private static final long serialVersionUID = 8333122286382736773L;

    /**
     * Instantiates a new klab resource not found exception.
     *
     * @param resourceName the resource name
     */
    public KlabResourceNotFoundException(String resourceName) {
        super("resource not found: " + resourceName);
    }

    /**
     * Instantiates a new klab resource not found exception.
     */
    public KlabResourceNotFoundException() {
        super();
    }

    /**
     * Instantiates a new klab resource not found exception.
     *
     * @param arg0 the arg 0
     * @param arg1 the arg 1
     */
    public KlabResourceNotFoundException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Instantiates a new klab resource not found exception.
     *
     * @param arg0 the arg 0
     */
    public KlabResourceNotFoundException(Throwable arg0) {
        super(arg0);
    }

}
