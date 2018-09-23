package org.integratedmodelling.klab.documentation;

import java.io.File;

import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.utils.FileCatalog;

public class ProjectReferences extends FileCatalog<Reference> {

    private static final long serialVersionUID = -3376602822601253693L;

    public ProjectReferences(File file) {
        super(file, Reference.class, Reference.class);
    }

    public ProjectReferences(IProject project) {
        super(IDocumentation.getReferencesFile(project
                .getRoot()), Reference.class, Reference.class);
    }
}
