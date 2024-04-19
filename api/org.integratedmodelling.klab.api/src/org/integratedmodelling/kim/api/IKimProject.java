package org.integratedmodelling.kim.api;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.integratedmodelling.kactors.api.IKActorsBehavior;

public interface IKimProject {

    static final String KLAB_CONFIGURATION_DEFINED_WORLDVIEW_ID = "klab.defined.worldview";
    static final String KLAB_CONFIGURATION_WORLDVIEW_ID = "klab.worldview";
    static final String KLAB_CONFIGURATION_CLOSED_PROJECT = "klab.closed";
    static final String KLAB_CONFIGURATION_PREREQUISITES = "klab.prerequisites";
    static final String KLAB_CONFIGURATION_PERMISSIONS = "klab.permissions";

    static public final String SOURCE_FOLDER = "src";
    static public final String DOCUMENTATION_FOLDER = "docs";
    static public final String SCRIPT_FOLDER = "apps";
    static public final String TESTS_FOLDER = "tests";
    static public final String RESOURCE_FOLDER = "resources";

    /**
     * 
     * @return
     */
    String getDefinedWorldview();

    /**
     * 
     * @return
     */
    List<File> getSourceFiles();

    /**
     * 
     * @param id
     * @return
     */
    IKimNamespace getNamespace(String id);

    /**
     * 
     * @return
     */
    IKimWorkspace getWorkspace();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @return
     */
    String getWorldview();

    /**
     * 
     * @return
     */
    Collection<String> getRequiredProjectNames();

    /**
     * 
     * @return
     */
    File getRoot();

    /**
     * 
     * @return
     */
    Properties getProperties();

    /**
     * 
     * @return
     */
    List<IKimNamespace> getNamespaces();

    /**
     * All the legitimate behaviors (in the source files)
     * 
     * @return
     */
    List<IKActorsBehavior> getBehaviors();

    /**
     * All the behaviors in the apps directory (which may also contain k.IM
     * scripts). Includes apps and components but not other types of declared
     * behavior.
     * 
     * @return
     */
    List<IKActorsBehavior> getApps();

    /**
     * All the behaviors in the tests directory (which may also contain k.IM
     * scripts).
     * 
     * @return
     */
    List<IKActorsBehavior> getTests();

    /**
     * 
     * @return
     */
    boolean isErrors();

    boolean isWarnings();

    boolean isOpen();

}
