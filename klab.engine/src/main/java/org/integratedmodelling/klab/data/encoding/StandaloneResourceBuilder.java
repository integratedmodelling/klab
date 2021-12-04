package org.integratedmodelling.klab.data.encoding;

import java.io.File;
import java.io.IOException;

import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.data.resources.ResourceBuilder;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;

/**
 * A resource builder that will use the IResourceService to build a resource
 * without intermediation of an adapter, importer or other. Will need a session
 * to build and notify the actual resource.
 * 
 * @author ferdinando.villa
 *
 */
public class StandaloneResourceBuilder extends ResourceBuilder {

	private IProject project;

	public StandaloneResourceBuilder(IProject project, String resourceId) {
		super(Resources.INSTANCE.createLocalResourceUrn(resourceId, project));
		setResourceId(resourceId);
		this.project = project;
		withProjectName(project.getName());
	}

	/**
	 * When building a resource directly (not through an importer), add file
	 * resources here. This should ensure that the files are available and readable,
	 * store the filenames and copy them when build() is called, also updating any
	 * local paths appropriately.
	 * 
	 * @param dataset
	 */
	public StandaloneResourceBuilder addFile(File dataset) {
		getImportedFiles().add(dataset);
		return this;
	}

	public IResource build(ISession session) {

		// create the directory
		File resourceDir = new File(
				project.getRoot() + File.separator + "resources" + File.separator + getResourceId());
		if (resourceDir.exists()) {
			// mercilessly throw away any previous resource
			FileUtils.deleteQuietly(resourceDir);
		}
		resourceDir.mkdirs();
		this.withLocalPath(project.getName() + "/resources/" + getResourceId());

		// copy any files
		for (File file : getImportedFiles()) {
			try {
				FileUtils.copyFileToDirectory(file, resourceDir);
			} catch (IOException e) {
				throw new KlabIOException(e);
			}
			this.addLocalResourcePath(
					project.getName() + "/resources/" + getResourceId() + "/" + MiscUtilities.getFileName(file));
		}
		
		return ((Session) session).registerResource(super.build());

	}

}