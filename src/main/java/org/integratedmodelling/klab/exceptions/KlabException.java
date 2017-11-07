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

import java.io.PrintWriter;
import java.io.StringWriter;

import org.integratedmodelling.klab.api.provenance.IProvenance;
import org.integratedmodelling.klab.api.provenance.IProvenance.Artifact;

@SuppressWarnings("javadoc")
public class KlabException extends Exception {

    private static final long serialVersionUID = 5999457326224959271L;
    private Artifact scope;

    public KlabException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public KlabException(String arg0, Throwable arg1) {
        super(arg0, arg1);
        // TODO Auto-generated constructor stub
    }

    public KlabException(String arg0) {
        super(arg0);
    }

    public KlabException(Throwable arg0) {
        super(arg0);
    }
    
    public KlabException(String arg0, Artifact scope) {
        super(arg0);
        this.scope = scope;
    }

    public KlabException(Throwable arg0, Artifact scope) {
        super(arg0);
        this.scope = scope;
    }

    public String stackTrace() {
        StringWriter sw = new StringWriter();
        printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public IProvenance.Artifact getScope() {
        return scope;
    }

}
