/*******************************************************************************
 *  Copyright (C) 2007, 2014:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.exceptions;

import org.integratedmodelling.klab.api.provenance.IArtifact;

@SuppressWarnings("javadoc")
public class KlabRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 4432900820629531550L;
    private IArtifact scope;
    
    public KlabRuntimeException() {
    }

    public KlabRuntimeException(String message) {
        super(message);
    }
    
    public KlabRuntimeException(String message, IArtifact scope) {
        super(message);
        this.scope = scope;
    }

    public KlabRuntimeException(Throwable cause) {
        super(cause);
    }

    public KlabRuntimeException(Throwable cause, IArtifact scope) {
        super(cause);
        this.scope = scope;
    }
    
    public KlabRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IArtifact getScope() {
        return scope;
    }
}
