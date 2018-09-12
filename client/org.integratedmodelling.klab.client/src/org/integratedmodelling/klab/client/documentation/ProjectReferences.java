package org.integratedmodelling.klab.client.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.documentation.Reference;

public class ProjectReferences extends FileCatalog<Reference> {

	private static final long serialVersionUID = -3376602822601253693L;

	public static final String KEY_INITIALIZATION = "Initialization";
	public static final String KEY_DEFINITION = "Definition";
	public static final String KEY_INSTANTIATION = "Instantiation";
	public static final String KEY_TRANSITION = "Transition";
	public static final String KEY_TERMINATION = "Termination";

	public ProjectReferences(File file) {
		super(file, Reference.class, Reference.class);
	}

	public ProjectReferences(IKimProject project) {
		super(new File(project.getRoot() + File.separator + IKimProject.DOCUMENTATION_FOLDER + File.separator
				+ "references.json"), Reference.class, Reference.class);
	}

	public Object[] sortedValues() {
		List<Reference> ret = new ArrayList<>();
		List<String> keys = new ArrayList<>(keySet());
		Collections.sort(keys);
		for (String key : keys) {
			ret.add(get(key));
		}
		return ret.toArray();
	}

}
