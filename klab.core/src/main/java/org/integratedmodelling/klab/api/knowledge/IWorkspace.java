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
import java.util.Collection;
import java.util.List;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IWorkspace.
 *
 * @author ferdinando.villa
 */
public interface IWorkspace {

    /**
     * All workspaces in k.LAB have a unique name.
     *
     * @return the unique workspace name
     */
    String getName();

    /**
     * All workspaces in k.LAB are filesystem based for now.
     * 
     * @return the file location of the root of the workspace on the filesystem.
     */
    File getRoot();
    
    /**
     * Load all the knowledge in the namespace, optionally limited to anything that has
     * changed since last read.
     * 
     * @param incremental if true, only read resources that have changed.
     * @return the list of namespaces read, excluding those that did not need update.
     *         Workspaces that keep track of dependencies should return the namespaces
     *         in dependency order.
     * 
     * @throws KlabException in case of I/O or other errors. Syntax errors won't cause
     *         exceptions.
     */
    List<INamespace> load(boolean incremental, IMonitor monitor) throws KlabException;
    
    /**
     * 
     * @return
     */
    Collection<IProject> getProjects();
    
}
