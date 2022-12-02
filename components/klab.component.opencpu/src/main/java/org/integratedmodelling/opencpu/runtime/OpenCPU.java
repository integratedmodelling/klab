package org.integratedmodelling.opencpu.runtime;

import java.io.File;
import java.util.Properties;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.opencpu.temp.OCPURuntimeEnvironment;

/**
 * The Java peer for the OpenCPU runtime installed in the machine running the adapter.
 * 
 * @author Ferd
 *
 */
public class OpenCPU {

    private boolean isOnline = false;
    OCPURuntimeEnvironment openCpu;
    
    public OpenCPU(String url) {

        /*
         * read configuration. If no config, assume http://127.0.0.1/ocpu
         */
        String service = "http://127.0.0.1/ocpu";
        File config = new File(Configuration.INSTANCE.getDataPath() + File.separator + "opencpu.config");
        if (config.exists()) {
            Properties properties = MiscUtilities.readProperties(config);
            if (properties.containsKey("service")) {
                service = properties.getProperty("service");
            }
        }

        openCpu = new OCPURuntimeEnvironment(service);
        
        /*
         * Ping the server and ensure it's online
         */

    }

    public boolean isOnline() {
        return isOnline;
    }

}
