package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class Project implements IProject {

	IKimProject delegate;
	IWorkspace workspace;
	Set<String> localResourceUrns = new HashSet<>();

	public Project(IKimProject project) {
		this.delegate = project;
		// if (project.getName().equals(Kim.COMMON_PROJECT_ID)) {
		// this.workspace = Resources.INSTANCE.getCommonWorkspace();
		// } else {
		this.workspace = Resources.INSTANCE.getWorkspace(project.getWorkspace().getName());
		// }
		synchronizeResources();
	}

	@Override
	public Collection<String> getLocalResourceUrns() {
		return localResourceUrns;
	}

	public void synchronizeResources() {
		File resourceDir = new File(getRoot() + File.separator + "resources");
		if (resourceDir.exists() && resourceDir.isDirectory()) {
			for (File rdir : resourceDir.listFiles()) {
				if (rdir.isDirectory()) {
					ResourceReference rref = Resources.INSTANCE.synchronize(rdir);
					if (rref != null) {
						localResourceUrns.add(rref.getUrn());
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	@Override
	public File getRoot() {
		return delegate.getRoot();
	}

	@Override
	public List<INamespace> getNamespaces() {
		return null;
	}

	@Override
	public List<IProject> getPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INamespace getUserKnowledge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCanonical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOriginatingNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Create a namespace file and return it (do not load it).
	 * 
	 * @param namespaceId
	 */
	public File createNamespace(String namespaceId, boolean createScenario) {

		File ret = new File(getRoot() + File.separator + IKimProject.SOURCE_FOLDER + File.separator
				+ namespaceId.replace('.', File.separatorChar) + ".kim");
		new File(MiscUtilities.getFilePath(ret.toString())).mkdirs();
		try (PrintWriter out = new PrintWriter(ret)) {
			out.print((createScenario ? "scenario " : "namespace ") + namespaceId + ";\n\n");
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
		
		return ret;

	}

}
