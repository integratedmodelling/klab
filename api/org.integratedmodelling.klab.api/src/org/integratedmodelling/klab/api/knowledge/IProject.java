/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.INamespace;

/**
 * The Interface IProject.
 * 
 * TODO the project should be a resource and have a resource.json in the META_INF along with the
 * klab.properties
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IProject {

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
     * getRoot.
     * </p>
     *
     * @return a {@link java.io.File} object.
     */
    File getRoot();

    /**
     * <p>
     * getNamespaces.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    List<INamespace> getNamespaces();

    /**
     * The behaviors defined in the projects (within namespaces).
     * 
     * @return
     */
    List<IBehavior> getBehaviors();

    /**
     * The behaviors defined in the Apps folder. These are for session actors only.
     * 
     * @return
     */
    List<IBehavior> getApps();

    /**
     * <p>
     * getPrerequisites.
     * </p>
     *
     * @return a {@link java.util.List} object.
     */
    List<IProject> getPrerequisites();

    /**
     * The URNs of all local resources defined in the project.
     * 
     * @return
     */
    Collection<String> getLocalResourceUrns();

    /**
     * <p>
     * getVersion.
     * </p>
     *
     * @return a {@link org.integratedmodelling.klab.Version} object.
     */
    Version getVersion();

    /**
     * This will return a non-null namespace unless the project is a worldview (which will return
     * null). The "user knowledge" namespace is permitted to have only aliases (defined using "is"
     * which is interpreted differently) and resides in META_INF/knowledge.kim. Non-worldview
     * projects should only define concepts this way; having a namespace define concepts makes the
     * namespace "dirty" and the project non-shareable.
     *
     * @return the user knowledge namespace. Only null in worldviews.
     */
    INamespace getUserKnowledge();

    /**
     * True if the project has no namespaces that define non-worldview knowledge (except for
     * aliases) and is not using any local resources (see
     * {@link org.integratedmodelling.klab.api.model.INamespace#isCanonical()}). A canonical project
     * can be published on a node.
     *
     * @return true if project is canonical
     */
    public boolean isCanonical();

    /**
     * True if this project originates from a remote node and not from a local workspace.
     *
     * @return a boolean.
     */
    boolean isRemote();

    /**
     * Find a local resource by local name and return it.
     * 
     * @param urn the local resource name
     * @return local resource or null
     */
    IResource getLocalResource(String urn);

    /**
     * Name of originating node. Not null implies {@link #isRemote()} == true.
     *
     * @return a {@link java.lang.String} object.
     */
    String getOriginatingNodeId();

    /**
     * Get the workspace where this project resides.
     * 
     * @return
     */
    IWorkspace getWorkspace();

    /**
     * 
     * @return the originating k.IM project
     */
    IKimProject getStatement();

    /**
     * Get all the unit tests in k.Actors declared in the project
     * 
     * @return
     */
    List<IBehavior> getUnitTests();
}
