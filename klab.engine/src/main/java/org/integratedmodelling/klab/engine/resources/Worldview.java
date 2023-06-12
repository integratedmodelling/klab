package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IWorldview;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.scale.Scale;

public class Worldview extends MonitorableGitWorkspace implements IWorldview {

	public Worldview(String name, File root, Map<String, Set<String>> gitUrls) {
		super(root, name, name, gitUrls);
		this.setSkipSync(System.getProperty("skipWorldviewSync") != null);
	}

	@Override
	public IScale getScale(IGeometry geometry) {
		return Scale.create(geometry);
	}

	@Override
	public IConcept getCoreConcept(IConcept coreConcept) {

		for (IConcept c : coreConcept.getChildren()) {
			if (c.getOntology().getName().equals(this.getName()) && c.getDefinition().equals(c.toString())) {
				return c;
			}
		}

		return coreConcept;
	}

	public static IConcept getGeoregionConcept() {
		/**
		 * TODO! Tie to annotation in worldview AND configured parameter to override
		 */
		return Concepts.c("earth:Region");
	}

	@Override
	public INamespace getRootNamespace() {
		return Namespaces.INSTANCE.getNamespace(getName());
	}

}
