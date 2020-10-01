package org.integratedmodelling.klab;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.services.IDocumentationService;
import org.integratedmodelling.klab.documentation.ProjectDocumentation;

public enum Documentation implements IDocumentationService {

	INSTANCE;

	private Documentation() {
		Services.INSTANCE.registerService(this, IDocumentationService.class);
	}

	private Map<String, org.integratedmodelling.klab.documentation.Documentation> catalog = Collections
			.synchronizedMap(new HashMap<>());

	@Override
	public IDocumentation getDocumentation(String docId, IParameters<?> parameters, IProject project) {

		org.integratedmodelling.klab.documentation.Documentation ret = catalog.get(docId);
		if (ret != null) {
			return ret.configure(parameters);
		}
		File docFile = IDocumentation.getDocumentationFile(docId, project.getRoot());
		if (!docFile.exists()) {
			return org.integratedmodelling.klab.documentation.Documentation.empty();
		}
		// read on the spot and forget
		ProjectDocumentation documentation = new ProjectDocumentation(docFile);
		ret = new org.integratedmodelling.klab.documentation.Documentation(documentation, docId, project);
		((org.integratedmodelling.klab.documentation.Documentation) ret).setDocfile(docFile);
		catalog.put(docId, ret);
		return ret.configure(parameters);
	}

	public synchronized void resetDocumentation(File docFile) {
		Set<String> keys = new HashSet<>(catalog.keySet());
		for (String key : keys) {
			if (catalog.get(key).getDocfile().equals(docFile)) {
				catalog.remove(key);
			}
		}
	}
}
