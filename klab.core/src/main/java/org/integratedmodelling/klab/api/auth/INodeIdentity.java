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

import java.util.Set;

/**
 * The "view" of each k.LAB network node that gets to the engine after network
 * connection. Contains permissions for the connecting engine.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface INodeIdentity extends IServerIdentity {

	/**
	 * Permissions available to the current identity.
	 * 
	 * @author Ferd
	 *
	 */
	public static enum Permission {
		PUBLISH,
		QUERY
	}

	/** Constant <code>type</code> */
	Type type = Type.NODE;

	/** {@inheritDoc} */
	@Override
	IPartnerIdentity getParentIdentity();

	/**
	 * Nodes should be periodically checked for online status.
	 * 
	 * @return true if online at the time of calling (or the most recent check).
	 */
	boolean isOnline();
	
	/**
	 * All the permissions available.
	 * 
	 * @return all permissions for the current identity
	 */
	Set<Permission> getPermissions();
}
