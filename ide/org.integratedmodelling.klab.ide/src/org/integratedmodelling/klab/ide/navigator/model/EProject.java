package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.ide.Activator;
import org.integratedmodelling.klab.ide.navigator.model.documentation.EDocumentationFolder;

public class EProject extends ENavigatorItem {

	IKimProject delegate;

	public EProject(IKimProject project, ENavigatorItem parent) {
		super(project.getName(), parent);
		this.delegate = project;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		if (IResource.class.isAssignableFrom(adapter)) {
			return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(delegate.getName());
		}
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {

		List<ENavigatorItem> ret = new ArrayList<>(delegate.getNamespaces().size());

		for (IKimNamespace child : delegate.getNamespaces()) {
			if (!child.isWorldviewBound()) {
				ret.add(new ENamespace(child, this));
			}
		}
		for (IKActorsBehavior child : delegate.getBehaviors()) {
			ret.add(new EActorBehavior(child, this));
		}

		/*
		 * we don't let worldviews have resources or scripts
		 */
		if (delegate.getDefinedWorldview() == null) {
			ret.add(new EDocumentationFolder(this,
					new File(delegate.getRoot() + File.separator + IKimProject.DOCUMENTATION_FOLDER), "Documentation"));
			ret.add(new EResourceFolder(this));
			ret.add(new EScriptFolder(this, this,
					new File(delegate.getRoot() + File.separator + IKimProject.SCRIPT_FOLDER)));
			ret.add(new ETestFolder(this, this,
					new File(delegate.getRoot() + File.separator + IKimProject.TESTS_FOLDER)));
		}

		return ret.toArray(new ENavigatorItem[ret.size()]);
	}

	@Override
	public boolean hasEChildren() {
		return true;
	}

	public String getName() {
		return delegate.getName();
	}

	public File getRoot() {
		return delegate.getRoot();
	}

//	public boolean isErrors() {
//		for (IKimNamespace child : delegate.getNamespaces()) {
//			if (Activator.klab().getErrors(child.getName()).size() > 0) {
//				return true;
//			}
//		}
//		return false;
//	}
//
//	public boolean isWarnings() {
//		for (IKimNamespace child : delegate.getNamespaces()) {
//			if (Activator.klab().getWarnings(child.getName()).size() > 0) {
//				return true;
//			}
//		}
//		return false;
//	}

	public IKimProject getProject() {
		return delegate;
	}

	public boolean isWorldview() {
		return delegate.getDefinedWorldview() != null;
	}

	public EResourceFolder getResourceFolder() {
		return new EResourceFolder(this);
	}

}
