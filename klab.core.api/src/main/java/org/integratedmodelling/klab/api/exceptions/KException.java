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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.integratedmodelling.klab.api.knowledge.KArtifact;

/**
 * The Class KlabException.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public class KException extends RuntimeException {

    private static final long serialVersionUID = 5999457326224959271L;
    private KArtifact scope;

    /**
     * Instantiates a new klab exception.
     */
    public KException() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new klab exception.
     *
     * @param arg0 the arg 0
     * @param arg1 the arg 1
     */
    public KException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new klab exception.
     *
     * @param arg0 the arg 0
     */
    public KException(String arg0) {
        super(arg0);
    }

    /**
     * Instantiates a new klab exception.
     *
     * @param arg0 the arg 0
     */
    public KException(Throwable arg0) {
        super(arg0);
    }
    
    /**
     * Instantiates a new klab exception.
     *
     * @param arg0 the arg 0
     * @param scope the scope
     */
    public KException(String arg0, KArtifact scope) {
        super(arg0);
        this.scope = scope;
    }

    /**
     * Instantiates a new klab exception.
     *
     * @param arg0 the arg 0
     * @param scope the scope
     */
    public KException(Throwable arg0, KArtifact scope) {
        super(arg0);
        this.scope = scope;
    }

    /**
     * Stack trace.
     *
     * @return the string
     */
    public String stackTrace() {
        StringWriter sw = new StringWriter();
        printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */
    public KArtifact getScope() {
        return scope;
    }

}
