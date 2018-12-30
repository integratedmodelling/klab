package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.client.documentation.ProjectReferences;

public class EReferencesPage extends ENavigatorItem {

    private ProjectReferences references;
    private File file;
    
    protected EReferencesPage(ENavigatorItem parent, File file, String id, ProjectReferences documentation) {
        super(id, parent);
        this.id = id;
        this.file = file;
        this.references = documentation;
    }
    
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
        for (String key : references.keySet()) {
            ret.add(new EReference(this, references, key));
        }
        return ret.toArray(new ENavigatorItem[0]);
    }

    @Override
    public boolean hasEChildren() {
        return references.size() > 0;
    }

    public String getPagePath() {
        return id;
    }
}
