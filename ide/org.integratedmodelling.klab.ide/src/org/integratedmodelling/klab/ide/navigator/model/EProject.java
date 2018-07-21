package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;

public class EProject extends ENavigatorItem {

    IKimProject delegate;

    protected EProject(IKimProject project, ENavigatorItem parent) {
        super(project.getName(), parent);
        this.delegate = project;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        if (IProject.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(delegate.getName());
        }
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {

        List<ENavigatorItem> ret = new ArrayList<>(delegate.getNamespaces().size());

        int nscripts = 0;
        for (IKimNamespace child : delegate.getNamespaces()) {
            if (!child.isWorldviewBound()) {
                ret.add(new ENamespace(child, this));
            } else {
                nscripts++;
            }
        }
        if (nscripts > 0) {
            ret.add(new EScriptFolder(this));
            ret.add(new ETestFolder(this));
        }
        ret.add(new EResourceFolder(this));

        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return true;
    }

    public String getName() {
        return delegate.getName();
    }

}
