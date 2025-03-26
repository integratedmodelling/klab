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

import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.api.knowledge.IAuthority;
import org.integratedmodelling.klab.rest.NodeReference.Permission;

/**
 * The "view" of each k.LAB network node that gets to the engine after network
 * connection. Contains permissions for the connecting engine.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface INodeIdentity extends IServerIdentity {

    /** {@inheritDoc} */
    @Override
    IPartnerIdentity getParentIdentity();

    /**
     * All the permissions available.
     * 
     * @return all permissions for the current identity
     */
    Set<Permission> getPermissions();

    /**
     * IDs for all adapters usable by the asking engine
     * 
     * @return
     */
    Set<String> getAdapters();
    
    /**
     * All resource IDs handled by the node visible by the asking engine
     * 
     * @return
     */
    Set<String> getResources();

    /**
     * All the namespace IDs in node resources, including its default namespace.
     * 
     * @return
     */
	Set<String> getNamespaceIds();
    
    /**
     * All the catalog IDs in node resources, including its default catalog.
     * 
     * @return
     */
	Set<String> getCatalogIds();
	
	/**
	 * Authorities and their capabilities.
	 * 
	 * @return
	 */
	Map<String, IAuthority.Capabilities> getAuthorities();	
	
	/**
	 * Get the node uptime in milliseconds
	 * 
	 * @return
	 */
	long getUptime();

	/**
	 * k.LAB version and build
	 * 
	 * @return
	 */
	String getVersion();
}
