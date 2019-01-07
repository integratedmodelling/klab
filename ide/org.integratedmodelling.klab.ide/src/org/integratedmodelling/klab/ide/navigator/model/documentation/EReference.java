package org.integratedmodelling.klab.ide.navigator.model.documentation;

import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;

public class EReference extends ENavigatorItem {

    private String name;

    protected EReference(EReferencesPage eReferencesPage, ProjectReferences references, String name) {
        super(name + "_REF_", eReferencesPage);
        this.name = name;
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        return null;
    }

    @Override
    public boolean hasEChildren() {
        return false;
    }

    public String getName() {
        return name;
    }
    
}
