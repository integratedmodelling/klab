package org.integratedmodelling.klab.test.network;

import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.EngineStartupOptions;

public class SetupResources {

	static Set<String> resourceUrns = new HashSet<>();

	public static Set<String> getResourceUrns() {
		return resourceUrns;
	}

	public static void setupWCSResources(IProject project) throws Exception {
		for (IResource resource : Resources.INSTANCE.importResources(new URL(
				"http://www.integratedmodelling.org/geodata/ows?service=WCS&version=2.0.1&request=GetCapabilities"),
				project, "wcs")) {
			resourceUrns.add(resource.getUrn());
		}
	}

	/**
	 * Call to obtain an engine with preloaded resources, loading if necessary or
	 * when requested. Engne is started in anonymous mode so that the URNs are
	 * stable.
	 * 
	 * @param clearResources
	 *            clear the resource catalog, forcing reload.
	 * 
	 * @throws Exception
	 */
	public static Engine startAndLoad(boolean clearResources) throws Exception {

		Engine engine = Engine.start(new EngineStartupOptions("-anonymous"));
		if (clearResources) {
			Resources.INSTANCE.getLocalResourceCatalog().clear();
		}
		boolean loaded = false;
		for (String urn : Resources.INSTANCE.getLocalResourceCatalog().keySet()) {
			if (urn.contains(":test.network:")) {
				loaded = true;
				resourceUrns.addAll(Resources.INSTANCE.getLocalResourceCatalog().keySet());
				break;
			}
		}

		if (!loaded) {
			IProject testProject = Resources.INSTANCE.getLocalWorkspace().getProject("test.network");
			if (testProject == null) {
				testProject = Resources.INSTANCE.getLocalWorkspace().createProject("test.network");
			}
			setupWCSResources(testProject);
		}

		return engine;
	}

}
