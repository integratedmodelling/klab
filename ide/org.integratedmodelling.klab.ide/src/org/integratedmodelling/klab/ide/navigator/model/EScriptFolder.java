package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;

public class EScriptFolder extends ENavigatorItem {

    IKimProject project;

    public EScriptFolder(EProject parent) {
        super(parent.id + "#__SCRIPTS__", parent);
        this.project = parent.delegate;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
//        if (IFolder.class.isAssignableFrom(adapter)) {
//            return (T) Eclipse.INSTANCE.getNamespaceIFile(this);
//        }
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
        for (IKimNamespace child : project.getNamespaces()) {
            if (child.isWorldviewBound() && child.getScriptId() != null) {
                ret.add(new EScript(child, this));
            }
        }
        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return getEChildren().length > 0;
    }

}
