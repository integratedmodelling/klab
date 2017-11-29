package org.integratedmodelling.klab.api.knowledge;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.model.INamespace;

public interface IProject extends IKimProject {

    String getName();

    File getRoot();

    List<INamespace> getNamespaces();

    List<IProject> getPrerequisites();

    Version getVersion();

    /**
     * This will return a non-null namespace unless the project is a worldview (which will return null). The
     * "user knowledge" namespace is permitted to have only aliases (defined using "is" which is interpreted
     * differently) and resides in META_INF/knowledge.kim. Non-worldview projects should only define concepts
     * this way; having a namespace define concepts makes the namespace "dirty" and the project non-shareable.
     * 
     * @return the user knowledge namespace. Only null in worldviews.
     */
    INamespace getUserKnowledge();

    /**
     * True if the project has no namespaces that define non-worldview knowledge (see
     * {@link INamespace#isCanonical()}).
     * 
     * @return
     */
    public boolean isCanonical();
}
