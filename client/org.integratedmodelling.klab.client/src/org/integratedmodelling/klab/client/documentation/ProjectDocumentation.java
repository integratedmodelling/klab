package org.integratedmodelling.klab.client.documentation;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.klab.api.documentation.IDocumentation;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.client.utils.FileCatalog;
import org.integratedmodelling.klab.documentation.ModelDocumentation;

public class ProjectDocumentation extends FileCatalog<ModelDocumentation> {

    private static final long serialVersionUID = 8403756131577259223L;

    public ProjectDocumentation(File file) {
        super(file, ModelDocumentation.class, ModelDocumentation.class);
    }

    public ProjectDocumentation(String docId, IKimProject project) {
        super(IDocumentation.getDocumentationFile(docId, project
                .getRoot()), ModelDocumentation.class, ModelDocumentation.class);
    }

    /**
     * Return all defined triggers for the passed documentation item ID.
     * 
     * @param item
     * @return
     */
    public Set<Trigger> getTriggers(String item) {
        Set<Trigger> ret = new LinkedHashSet<>();
        for (String key : keySet()) {
            if (key.startsWith(item)) {
                String[] kk = key.split("#");
                ret.add(Trigger.valueOf(kk[1].toUpperCase()));
            }
        }
        return ret;
    }

    /**
     * Get all the documentation items (those with unique ID) in this documentation. Each will have sections and triggers.
     * 
     * @return
     */
    public Set<String> getItems() {
        Set<String> ret = new LinkedHashSet<>();
        for (String key : keySet()) {
            String[] kk = key.split("#");
            ret.add(kk[0]);
        }
        return ret;
    }

    /**
     * Return all the available sections for the passed item and trigger.
     * 
     * @param item
     * @param trigger
     * @return
     */
    public Set<String> getSections(String item, Trigger trigger) {
        Set<String> ret = new LinkedHashSet<>();
        for (String key : keySet()) {
            if (key.startsWith(item)) {
                String[] kk = key.split("#");
                Trigger tr = Trigger.valueOf(kk[1].toUpperCase());
                if (tr == trigger) {
                    ret.add(kk[2].toUpperCase());
                }
            }
        }
        return ret;
    }
}
