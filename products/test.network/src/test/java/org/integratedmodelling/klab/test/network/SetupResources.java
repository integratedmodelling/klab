package org.integratedmodelling.klab.test.network;

import java.net.URL;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.engine.Engine;

public class SetupResources {

	public static void setupWCSResources(IProject project) throws Exception {
		Resources.INSTANCE.importResources(new URL("http://www.integratedmodelling.org/geodata/ows?service=WCS&version=2.0.1&request=GetCapabilities"), project, "wcs");
	}
	
	/**
	 * Call to create resources
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
	
		Engine engine = Engine.start();
		IProject testProject = Resources.INSTANCE.getLocalWorkspace().getProject("test.network");
		if (testProject == null) {
			testProject = Resources.INSTANCE.getLocalWorkspace().createProject("test.network");
		}
		setupWCSResources(testProject);
		engine.stop();
	}
	
}
