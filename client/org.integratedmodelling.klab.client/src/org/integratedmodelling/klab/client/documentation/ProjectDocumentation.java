package org.integratedmodelling.klab.client.documentation;

import java.io.File;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.documentation.ModelDocumentation;

public class ProjectDocumentation extends FileCatalog<ModelDocumentation> {

    private static final long serialVersionUID = 8403756131577259223L;

    public ProjectDocumentation(File file) {
        super(file, ModelDocumentation.class, ModelDocumentation.class);
    }

    public ProjectDocumentation(String docId, IKimProject project) {
        super(IDocumentation.getDocumentationFile(docId, project
                .getRoot()), ModelDocumentation.class, ModelDocumentation.class);
    }
}
