package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.util.Properties;

import org.integratedmodelling.klab.utils.OS;

public interface IConfigurationService {

    Properties getProperties();

    boolean useReasoner();

    OS getOS();

    File getDataPath(String subspace);

    boolean isOffline();
    
}
