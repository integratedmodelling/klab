/*******************************************************************************
 *  Copyright (C) 2007, 2015:
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
package org.integratedmodelling.klab.api.errormanagement;

import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.klab.api.model.INamespace;

/**
 * Base class for anything that needs to be notified linked to a line of k.IM code - info, error, warning.
 * The compiler produces a set of these in each namespace.
 * 
 * @author ferdinando.villa
 *
 */
public abstract interface ICompileNotification {

    /**
     * May be null (e.g. for code snippets read from the console). Usually isn't.
     * @return the namespace for the notification
     */
    INamespace getNamespace();

    /**
     * Get the most specific statement where the notification originated.
     * 
     * @return
     */
    IKimStatement getStatement();
    
    /**
     * 
     * @return the message notified
     */
    String getMessage();

}
