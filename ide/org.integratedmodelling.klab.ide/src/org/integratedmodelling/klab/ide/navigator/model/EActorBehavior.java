package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class EActorBehavior extends EKimObject implements IKActorsBehavior {

	private IKActorsBehavior behavior;

	EActorBehavior(IKActorsBehavior behavior, ENavigatorItem parent) {
		super(behavior.getName(), behavior, parent);
		this.behavior = behavior;
	}

	private static final long serialVersionUID = 6120904235254835394L;

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		/*
		 * The hierarchy is either IContainer or IFile, but if I put IFile in the last
		 * condition and remove the others, the namespace isn't seen as a file. Leave
		 * like this although it looks weird.
		 */
		if (IContainer.class == adapter) {
			// ehm.
		} else if (IProject.class.isAssignableFrom(adapter)) {
			// boh
		} else if (IResource.class.isAssignableFrom(adapter)) {
			return (T) Eclipse.INSTANCE.getIFile(this.getFile());
		}
		return null;
	}

	@Override
	public IFile getIFile() {
		return Eclipse.INSTANCE.getIFile(this.getFile());
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasEChildren() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		return behavior.getName();
	}

	@Override
	public Type getType() {
		return behavior.getType();
	}

	@Override
	public File getFile() {
		return behavior.getFile();
	}

}
