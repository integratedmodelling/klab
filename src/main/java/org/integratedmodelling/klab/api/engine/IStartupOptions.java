package org.integratedmodelling.klab.api.engine;

import java.io.File;

public interface IStartupOptions {

    /**
     * Tied to the <pre>-cert</pre> option, provides an alternate certificate file to use when initializing. The
     * default is <pre>{dataDir}/im.cert</pre>.
     * 
     * @return
     */
    File getCertificateFile();


    /**
     * Tied to the <pre>-dataDir</pre> option, provides an alternate work directory full path. The default is
     * <pre>~/.klab</pre>.
     * 
     * @return
     */
    File getDataDirectory();


    /**
     * Tied to the <pre>-port</pre> option, establishes the REST communication port if we are implementing
     * REST communication. It also transparently links to the Spring application properties so it can be 
     * specified either way.
     * 
     * @return
     */
    int getPort();


    /**
     * Tied to <pre>-help</pre> option. Print version and usage banner and exit.
     * @return
     */
    boolean isHelp();
    
}
