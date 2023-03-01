package org.integratedmodelling.klab.api.lang.kim;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.lang.KStatement;

/**
 * 
 * @author Ferd
 *
 */
public interface KKimStatement extends KStatement, KKimScope {

    enum Scope {
        PUBLIC, PRIVATE, PROJECT_PRIVATE
    }

    /**
     * 
     * @return the documentation
     */
    KParameters<String> getDocumentationMetadata();

    /**
     * The namespace ID for this object. Coincides with getName() if this is a IKimNamespace.
     * 
     * @return
     */
    String getNamespace();

    /**
     * Scope can be declared for namespaces and models. Default is public or whatever the containing
     * namespace scope is. Concepts unfortunately cannot be scoped with current infrastructure.
     * 
     * @return
     */
    Scope getScope();

}
