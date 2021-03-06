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
package org.integratedmodelling.klab.api.errormanagement;

import java.util.logging.Level;

import org.integratedmodelling.klab.api.IStatement;

/**
 * Base class for anything that needs to be notified linked to a line of k.IM code - info, error, warning.
 * The compiler produces a set of these in each namespace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface ICompileNotification {

    /**
     * Level of the notification. 
     * 
     * @return
     */
    Level getLevel();
    
    /**
     * May be null (e.g. for code snippets read from the console). Usually isn't.
     *
     * @return the namespace ID for the notification
     */
    String getNamespaceId();

    /**
     * Get the most specific statement where the notification originated. May be an incomplete proxy in
     * notifications sent over the wire, only mentioning coordinates in the originating document.
     *
     * @return a {@link org.integratedmodelling.api.IStatement} object.
     */
    IStatement getStatement();

    /**
     * <p>getMessage.</p>
     *
     * @return the message notified
     */
    String getMessage();

}
