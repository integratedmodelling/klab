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
package org.integratedmodelling.klab.api.engine;

import java.io.File;
import java.util.Collection;

/**
 * The Interface IEngineStartupOptions.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IEngineStartupOptions extends IStartupOptions {

    /**
     * Tied to -workspace option, locates the local workspace which is monitored for changes and automatically
     * reloaded as needed. If there is no option, the workspace is in ~/.klab/workspace and is not monitored.
     *
     * @return a {@link java.io.File} object.
     */
    File getWorkspaceLocation();

    /**
     * Tied to -certResource, allows the certificate to be read from the classpath rather than from a file. This should be checked before 
     * {@link #getCertificateFile()} is called. For self-contained embedded servers.
     * 
     * @return the classpath resource with a certificate or null
     */
    String getCertificateResource();

    /**
     * Tied to <pre>-exit</pre> option, exits the engine after initializing and running any scripts passed on the
     * command line. REST services are not started.
     *
     * @return a boolean.
     */
    boolean isExitAfterStartup();

    /**
     * Return a string description of the options.
     *
     * @return usage string
     */
    String usage();

    /**
     * Return the paths to the components, one by one (not a workspace). The default here is 
     * normally ok, used only by custom or dev applications to control component loading.
     * 
     * @return the paths to all components we want to load
     */
    Collection<File> getComponentPaths();
}
