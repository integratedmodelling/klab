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

    File getDataPath();

    /**
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the
     * ontology holding the main concept in the declaration (false). Default is true.
     * 
     * @return the value of this setting (default true, should be false only for special purposes,
     *  such as using k.LAB only as an OWL processor)
     */
    boolean useCommonOntology();

}
