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
package org.integratedmodelling.klab.api.auth;

import java.util.Collection;

import org.integratedmodelling.klab.rest.NodeReference.Permission;

/**
 * The Interface INetworkSessionIdentity. Represents the view of the k.LAB
 * network available to the current identity. If there is no
 * {@code INetworkIdentity} in the lineage of the current identity, it is not
 * connected to the k.LAB network.
 * <p>
 * The network service will make sure that the node list are periodically
 * refreshed and their resources are checked.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface INetworkSessionIdentity extends IIdentity {

	/** Constant <code>type</code> */
	Type type = Type.NETWORK_SESSION;

	/**
	 * 
	 * @return all known nodes that were online at the last check.
	 */
	Collection<INodeIdentity> getNodes();

	/**
	 * 
	 * @return all known nodes that were online at the last check and have the
	 *         specified permission for the current engine user.
	 */
	Collection<INodeIdentity> getNodes(Permission permission);
}
