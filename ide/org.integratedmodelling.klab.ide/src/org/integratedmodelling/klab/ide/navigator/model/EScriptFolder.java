package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.model.Kim;

public class EScriptFolder extends ENavigatorItem {

    IKimProject project;

    public EScriptFolder(EProject parent) {
        super(parent.id + "#__SCRIPTS__", parent);
        this.project = parent.delegate;
    }

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
        if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName()).getFolder(IKimProject.SCRIPT_FOLDER);
        }
        return null;
	}

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
        File folder = new File(project.getRoot() + File.separator + IKimProject.SCRIPT_FOLDER);
        for (File script : folder.listFiles()) {
            if (script.toString().endsWith(".kim")) {
                // TODO find a way to not reload if it was loaded before
                ret.add(new EScript(Kim.INSTANCE.load(script), this));
            }
        }

        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return getEChildren().length > 0;
    }

}
