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
package org.integratedmodelling.klab.api.exceptions;

// TODO: Auto-generated Javadoc
/**
 * The Class KlabIOException.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class KIOException extends KException {

    private static final long serialVersionUID = 6219417233563472839L;

    /**
     * Instantiates a new klab IO exception.
     *
     * @param s the s
     */
    public KIOException(String s) {
        super("Input/output error: " + s);
    }

    /**
     * Instantiates a new klab IO exception.
     *
     * @param e the e
     */
    public KIOException(Throwable e) {
        super("Input/output error: " + e.getMessage());
    }
}
