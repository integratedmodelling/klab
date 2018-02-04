package org.integratedmodelling.klab.api.services;

import java.io.File;
import java.util.Properties;

import org.integratedmodelling.klab.utils.OS;

/**
 * 
 * @author Ferd
 *
 */
public interface IConfigurationService {

    /**
     * The main properties, read and written by default to ${user.dir}/.klab/klab.properties.
     * 
     * @return the properties. Created if absent, never null.
     */
    Properties getProperties();

    /**
     * The operating system where we are running. Only recognizes the three main ones.
     * 
     * @return the OS identifier.
     */
    OS getOS();

    /**
     * Create (if necessary) and return a subdirectory within the k.LAB workspace. Slash-separated
     * subspace strings can be used to specify nested subdirectories.
     * 
     * @param subspace
     * @return the file directory created.
     */
    File getDataPath(String subspace);

    boolean isOffline();

    /**
     * Return the k.LAB workspace, by default ${user.dir}/.klab.
     * @return the k.LAB workspace directory.
     */
    File getDataPath();

    /**
     * Create derived concepts in the common ontology owned by the reasoner (true) or in the
     * ontology holding the main concept in the declaration (false). Default is true.
     * 
     * @return the value of this setting (default true, should be false only for special purposes,
     *  such as using k.LAB only as an OWL processor)
     */
    boolean useCommonOntology();

    /**
     * True if debugging mode has been enabled.
     * 
     * @return debugging mode
     */
    boolean isDebuggingEnabled();

}

