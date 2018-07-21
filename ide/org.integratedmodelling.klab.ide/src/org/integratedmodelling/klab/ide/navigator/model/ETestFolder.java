package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimProject;

public class ETestFolder extends ENavigatorItem {

    IKimProject project;
    
	public ETestFolder(EProject parent) {
        super(parent.id + "#__TESTCASES__", parent);
        this.project = parent.delegate;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		
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
