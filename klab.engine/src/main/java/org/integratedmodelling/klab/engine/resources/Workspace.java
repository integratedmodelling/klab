package org.integratedmodelling.klab.engine.resources;

import java.io.File;

import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.utils.MiscUtilities;

/**
 * Gener tests.
 * 
 * @author Ferd
 *
 */
public class Workspace extends MonitorableFileWorkspace {

	private String name;

	public Workspace(File root, String worldview) {
		this.name = MiscUtilities.getFileBaseName(root.toString());
		delegate = new KimWorkspace(root, worldview, name);
	}

}
