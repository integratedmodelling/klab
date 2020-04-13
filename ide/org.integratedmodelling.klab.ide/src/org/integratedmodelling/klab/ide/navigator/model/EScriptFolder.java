package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.klab.ide.Activator;

public class EScriptFolder extends ENavigatorItem {

    EProject project;
    File folder;

    public EScriptFolder(EProject project, ENavigatorItem parent, File folder) {
        super(parent.id + folder, parent);
        this.project = project;
        this.folder = folder;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().findMember(folder.toString());
        }
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
		for (IKActorsBehavior child : project.delegate.getApps()) {
			ret.add(new EActorBehavior(child, this));
		}
        if (folder.isDirectory()) {
            for (File script : folder.listFiles()) {
                if (script.isDirectory()) {
                    ret.add(new EScriptFolder(project, this, script));
                } else if (script.toString().endsWith(".kim")) {
                    ret.add(new EScript(Activator.loader().getNamespace(script), this));
                }
            }
        }
        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return getEChildren().length > 0;
    }

}
