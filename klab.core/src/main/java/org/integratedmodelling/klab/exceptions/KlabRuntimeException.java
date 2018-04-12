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

import org.integratedmodelling.klab.api.provenance.IArtifact;

// TODO: Auto-generated Javadoc
/**
 * The Class KlabRuntimeException.
 */
@SuppressWarnings("javadoc")
public class KlabRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4432900820629531550L;
    private IArtifact scope;
    
    /**
     * Instantiates a new klab runtime exception.
     */
    public KlabRuntimeException() {
    }

    /**
     * Instantiates a new klab runtime exception.
     *
     * @param message the message
     */
    public KlabRuntimeException(String message) {
        super(message);
    }
    
    /**
     * Instantiates a new klab runtime exception.
     *
     * @param message the message
     * @param scope the scope
     */
    public KlabRuntimeException(String message, IArtifact scope) {
        super(message);
        this.scope = scope;
    }

    /**
     * Instantiates a new klab runtime exception.
     *
     * @param cause the cause
     */
    public KlabRuntimeException(Throwable cause) {
        super(cause);
    }

    /**
     * Instantiates a new klab runtime exception.
     *
     * @param cause the cause
     * @param scope the scope
     */
    public KlabRuntimeException(Throwable cause, IArtifact scope) {
        super(cause);
        this.scope = scope;
    }
    
    /**
     * Instantiates a new klab runtime exception.
     *
     * @param message the message
     * @param cause the cause
     */
    public KlabRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Gets the scope.
     *
     * @return the scope
     */
    public IArtifact getScope() {
        return scope;
    }
}
