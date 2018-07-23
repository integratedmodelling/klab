package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;

public class ETestFolder extends ENavigatorItem {

    IKimProject project;
    
	public ETestFolder(EProject parent) {
        super(parent.id + "#__TESTCASES__", parent);
        this.project = parent.delegate;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
        if (IResource.class.isAssignableFrom(adapter)) {
            return (T) ResourcesPlugin.getWorkspace().getRoot().getProject(project.getName()).getFolder(IKimProject.TESTS_FOLDER);
        }
        return null;
	}

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
        for (IKimNamespace child : project.getNamespaces()) {
            if (child.isWorldviewBound() && child.getTestCaseId() != null) {
                ret.add(new ETestCase(child, this));
            }
        }
        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return getEChildren().length > 0;
    }


}
