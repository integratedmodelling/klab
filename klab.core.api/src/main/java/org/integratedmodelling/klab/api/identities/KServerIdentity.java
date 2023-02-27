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
package org.integratedmodelling.klab.api.identities;

import java.util.Collection;
import java.util.Date;

/**
 * The Interface IServerIdentity.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public abstract interface KServerIdentity extends KRuntimeIdentity {

	/**
	 * <p>
	 * getName.
	 * </p>
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getName();

	/**
	 * <p>
	 * getBootTime.
	 * </p>
	 *
	 * @return a {@link java.util.Date} object.
	 */
	Date getBootTime();

	/**
	 * The node official URL. If the node represents a cluster of servers or has
	 * mirrors, multiple URLs may be returned.
	 * 
	 * @return the URL(s) where this node can be reached.
	 */
	Collection<String> getUrls();
	
    /**
     * Servers should be periodically checked for online status.
     * 
     * @return true if online at the time of calling (or the most recent check).
     */
    boolean isOnline();

//    /**
//     * Return a primed client with the URLs and the token already set.
//     * 
//     * @return
//     */
//	IClient getClient();
}
