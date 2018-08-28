package org.integratedmodelling.klab.documentation;

import java.io.File;

import org.integratedmodelling.klab.utils.FileCatalog;

public class ProjectDocumentation extends FileCatalog<Reference> {

	private static final long serialVersionUID = 8403756131577259223L;

	public ProjectDocumentation(File file) {
		super(file, Reference.class, Reference.class);
	}
}
