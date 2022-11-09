package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.klab.ide.Activator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ETestFolder extends ENavigatorItem {

    EProject project;
    File folder;
    
    private static Logger logger = LoggerFactory.getLogger(ETestFolder.class);

    public ETestFolder(EProject project, ENavigatorItem parent, File folder) {
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
		for (IKActorsBehavior child : project.delegate.getTests()) {
			ret.add(new EActorBehavior(child, this));
		}
        if (folder.isDirectory()) {
            for (File script : folder.listFiles()) {
                if (script.isDirectory()) {
                    ret.add(new EScriptFolder(project, this, script));
                } else if (script.toString().endsWith(".kim")) {
                    IKimNamespace ns = Activator.loader().getNamespace(script);
                    if (ns != null) {
                        ret.add(new ETestCase(ns, this));
                    } else {
                        logger.warn("ACHTUNG: SCREWED-UP NS: " + script);
                    }
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
