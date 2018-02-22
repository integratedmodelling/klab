package org.integratedmodelling.klab.api.engine;

import java.io.File;

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
