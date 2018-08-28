package org.integratedmodelling.klab.documentation;

import java.io.File;

import org.integratedmodelling.klab.utils.FileCatalog;

public class ProjectReferences extends FileCatalog<ModelDocumentation> {

	private static final long serialVersionUID = -3376602822601253693L;

	public static final String KEY_INITIALIZATION = "Initialization";
	public static final String KEY_DEFINITION = "Definition";
	public static final String KEY_INSTANTIATION = "Instantiation";
	public static final String KEY_TRANSITION = "Transition";
	public static final String KEY_TERMINATION = "Termination";

	public ProjectReferences(File file) {
		super(file, ModelDocumentation.class, ModelDocumentation.class);
	}

}
