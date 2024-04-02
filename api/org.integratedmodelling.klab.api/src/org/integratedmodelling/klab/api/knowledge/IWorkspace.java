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

import java.util.Collection;

/**
 * The Interface IWorkspace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IWorkspace {

    /**
     * All workspaces in k.LAB have a unique name.
     *
     * @return the unique workspace name
     */
    String getName();
    
    /**
     * Get all the projects in the workspace.
     *
     * @return a {@link java.util.Collection} of all projects.
     */
    Collection<IProject> getProjects();
	
	/**
	 * Retrieve the named project.
	 * 
	 * @param projectId
	 * @return the project or null if project is not in the workspace
	 */
	IProject getProject(String projectId);
    
}
