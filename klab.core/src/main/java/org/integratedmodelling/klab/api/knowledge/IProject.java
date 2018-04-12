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
package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.List;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.model.INamespace;

// TODO: Auto-generated Javadoc
/**
 * The Interface IProject.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IProject {

    /**
     * <p>getName.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    String getName();

    /**
     * <p>getRoot.</p>
     *
     * @return a {@link java.io.File} object.
     */
    File getRoot();

    /**
     * <p>getNamespaces.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<INamespace> getNamespaces();

    /**
     * <p>getPrerequisites.</p>
     *
     * @return a {@link java.util.List} object.
     */
    List<IProject> getPrerequisites();

    /**
     * <p>getVersion.</p>
     *
     * @return a {@link org.integratedmodelling.klab.Version} object.
     */
    Version getVersion();

    /**
     * This will return a non-null namespace unless the project is a worldview (which will return null). The
     * "user knowledge" namespace is permitted to have only aliases (defined using "is" which is interpreted
     * differently) and resides in META_INF/knowledge.kim. Non-worldview projects should only define concepts
     * this way; having a namespace define concepts makes the namespace "dirty" and the project non-shareable.
     *
     * @return the user knowledge namespace. Only null in worldviews.
     */
    INamespace getUserKnowledge();

    /**
     * True if the project has no namespaces that define non-worldview knowledge (see
     * {@link org.integratedmodelling.klab.api.model.INamespace#isCanonical()}).
     *
     * @return true if project is canonital
     */
    public boolean isCanonical();

    /**
     * True if this project originates from a remote node and not from a local workspace.
     *
     * @return a boolean.
     */
    boolean isRemote();

    /**
     * Name of originating node. Not null implies {@link #isRemote()} == true.
     *
     * @return a {@link java.lang.String} object.
     */
    String getOriginatingNodeId();
}
