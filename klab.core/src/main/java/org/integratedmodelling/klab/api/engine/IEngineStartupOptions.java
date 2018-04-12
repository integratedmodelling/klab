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

/**
 * The Interface IEngineStartupOptions.
 */
public interface IEngineStartupOptions extends IStartupOptions {

    /**
     * Tied to -workspace option, locates the local workspace which is monitored for changes and automatically
     * reloaded as needed. If there is no option, the workspace is in ~/.klab/workspace and is not monitored.
     * 
     * @return
     */
    File getWorkspaceLocation();

    /**
     * Tied to <pre>-mcast</pre> option, opens a multicast channel with the specified ID so that a client can communicate
     * quickly with the engine.
     * 
     * @return
     */
    String getMulticastChannel();

    /**
     * Tied to <pre>-exit</pre> option, exits the engine after initializing and running any scripts passed on the
     * command line. REST services are not started.
     * 
     * @return
     */
    boolean isExitAfterStartup();

    /**
     * Return a string description of the options.
     * 
     * @return usage string
     */
    String usage();
}
