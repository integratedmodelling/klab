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
 * The Class KlabInternalErrorException.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class KInternalErrorException extends KException {

    private static final long serialVersionUID = 461213337593957416L;

    /**
     * Instantiates a new klab internal error exception.
     */
    public KInternalErrorException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new klab internal error exception.
     *
     * @param arg0 the arg 0
     */
    public KInternalErrorException(String arg0) {
        super(arg0);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new klab internal error exception.
     *
     * @param e the e
     */
    public KInternalErrorException(Throwable e) {
        super(e);
    }

}
