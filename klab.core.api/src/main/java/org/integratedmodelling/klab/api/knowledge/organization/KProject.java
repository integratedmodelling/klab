package org.integratedmodelling.klab.api.knowledge.organization;

import java.io.File;
import java.io.Serializable;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.integratedmodelling.klab.api.lang.kactors.KKActorsBehavior;
import org.integratedmodelling.klab.api.lang.kim.KKimNamespace;

public interface KProject extends Serializable {

    /**
     * 
     * @return
     */
    String getDefinedWorldview();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * The URL for the project. With content type JSON and proper authorization it should return the
     * parsed projects.
     * 
     * @return the workspace URL.
     */
    URL getURL();

    /**
     * 
     * @return
     */
    String getWorldview();

    /**
     * 
     * @param id
     * @return
     */
    KKimNamespace getNamespace(String id);

    /**
     * 
     * @return
     */
    Collection<String> getRequiredProjectNames();

    /**
     * 
     * @return
     */
    Properties getProperties();

    /**
     * 
     * @return
     */
    List<KKimNamespace> getNamespaces();

    /**
     * All the legitimate behaviors (in the source files)
     * 
     * @return
     */
    List<KKActorsBehavior> getBehaviors();

    /**
     * All the behaviors in the apps directory (which may also contain k.IM scripts). Includes apps
     * and components but not other types of declared behavior.
     * 
     * @return
     */
    List<KKActorsBehavior> getApps();

    /**
     * All the behaviors in the tests directory (which may also contain k.IM scripts).
     * 
     * @return
     */
    List<KKActorsBehavior> getTests();

    /**
     * 
     * @return
     */
    boolean isErrors();

    boolean isWarnings();

    boolean isOpen();
}
