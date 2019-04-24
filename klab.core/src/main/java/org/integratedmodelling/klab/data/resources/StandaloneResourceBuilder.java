package org.integratedmodelling.klab.data.resources;

import java.io.File;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;

/**
 * A resource builder that will use the IResourceService to build a resource
 * without intermediation of an adapter, importer or other.
 * 
 * @author ferdinando.villa
 *
 */
public class StandaloneResourceBuilder extends ResourceBuilder {

	public StandaloneResourceBuilder(IProject project, String resourceId) {

	}

	/**
	 * When building a resource directly (not through an importer), add file
	 * resources here. This should ensure that the files are available and readable,
	 * store the filenames and copy them when build() is called, also updating any
	 * local paths appropriately.
	 * 
	 * @param dataset
	 */
	public void addFile(File dataset) {
		getImportedFiles().add(dataset);
	}

	public IResource build() {
		
		// copy any files
		
		// invoke the service
		
		
		
		return null;
	}

	
	
}