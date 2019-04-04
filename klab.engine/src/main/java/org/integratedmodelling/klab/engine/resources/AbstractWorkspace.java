package org.integratedmodelling.klab.engine.resources;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

public abstract class AbstractWorkspace implements IWorkspace {

	protected KimWorkspace delegate;
	
	AbstractWorkspace() {
	}

	public AbstractWorkspace(String name, File root) {
		delegate = new KimWorkspace(root, name);
	}

	@Override
	public String getName() {
		return delegate.getName();
	}

	public Collection<File> getProjectLocations() {
		return delegate.getProjectLocations();
	}

	public Collection<String> getProjectNames() {
		return delegate.getProjectNames();
	}

	protected void readProjects() throws IOException {
		delegate.readProjects();
	}

	/**
	 * Add a project from a local directory.
	 * 
	 * @param root
	 * @return
	 */
	public IProject addProject(File root) {
		IKimProject ret = delegate.loadProject(root);
		return ret == null ? null : Resources.INSTANCE.retrieveOrCreate(ret);
	}

	@Override
	public IKimLoader load(IMonitor monitor) throws KlabException {
		return delegate.load();
	}

	@Override
	public IKimLoader load(IKimLoader loader, IMonitor monitor) throws KlabException {
		return delegate.load(loader);
	}

	@Override
	public File getRoot() {
		return delegate.getRoot();
	}

	public Collection<IProject> getProjects() {
		List<IProject> ret = new ArrayList<>();
		for (String projectId : delegate.getProjectNames()) {
			ret.add(Resources.INSTANCE.retrieveOrCreate(delegate.getProject(projectId)));
		}
		return ret;
	}

	@Override
	public IProject getProject(String projectId) {
		IKimProject ret = delegate.getProject(projectId);
		return ret == null ? null : Resources.INSTANCE.retrieveOrCreate(ret);
	}

}
