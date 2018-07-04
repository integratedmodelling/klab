package org.integratedmodelling.klab.ogc.test;

import java.net.URL;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.services.IResourceService.Importer;

public enum TestSetup {

	INSTANCE;
	
	public void importResource(String resource) {
		URL url = getClass().getClassLoader().getResource(resource);
//		Importer importer = Resources.INSTANCE.createImporter(url);
	}
	
}
