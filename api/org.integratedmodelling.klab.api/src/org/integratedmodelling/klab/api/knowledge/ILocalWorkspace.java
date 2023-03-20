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

import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The Interface IWorkspace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ILocalWorkspace extends IWorkspace {

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
     * @param monitor a {@link org.integratedmodelling.klab.api.runtime.monitoring.IMonitor} object.
     * @return the loader containing the details of resources and the corresponding namespaces
     * @throws org.integratedmodelling.klab.exceptions.KlabException in case of I/O or other errors. Syntax errors won't cause
     *         exceptions.
     */
    IKimLoader load(IMonitor monitor) throws KlabException;
    
    /**
     * Like {@link #load(IMonitor)} but adding the knowledge of another loader.
     * 
     * @param loader. May be null.
     * @param monitor
     * @return
     * @throws KlabException
     */
    IKimLoader load(IKimLoader loader, IMonitor monitor) throws KlabException;

    /**
     * Create an empty project in this workspace.
     * 
     * @param projectId
     * @param a monitor 
     * @return the new project
     * @throws IllegalStateException if the project already exists in the workspace.
     */
	IProject createProject(String projectId, IMonitor monitor);

	/**
	 * Load a project on demand. Return null without error if the project is not present.
	 * 
	 * @param projectId
	 * @param monitor
	 * @return
	 */
	IProject loadProject(String projectId, IMonitor monitor);
	
	/**
	 * Workspaces have control over a project's deletion.
	 * 
	 * @param project
	 */
	void deleteProject(IProject project);
    
}
