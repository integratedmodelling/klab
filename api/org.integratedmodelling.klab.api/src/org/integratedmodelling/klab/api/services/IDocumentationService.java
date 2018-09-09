package org.integratedmodelling.klab.api.services;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IProject;

public interface IDocumentationService {

    /**
     * The name of the k.IM annotation used to link documentation to objects.
     */
    final static String DOCUMENTED_ANNOTATION_ID = "documented";

    IDocumentation getDocumentation(String docId, IParameters<?> parameters, IProject project);

}
