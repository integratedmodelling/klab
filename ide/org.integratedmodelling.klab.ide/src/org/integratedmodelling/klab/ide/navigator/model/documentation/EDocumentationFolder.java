package org.integratedmodelling.klab.ide.navigator.model.documentation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.client.documentation.ProjectReferences;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.utils.MiscUtilities;

public class EDocumentationFolder extends ENavigatorItem {

    ENavigatorItem project;
    // if directory, children are the subdirectories and subfiles; if file, children
    // are the pages specified in the included JSON.
    File           file;
    File           page;
    File           refs;
    String         name;

    public EDocumentationFolder(ENavigatorItem parent, File file, String name) {
        super(parent.getId() + "." + name, parent);
        this.project = parent;
        this.file = file;
        File pfile = new File(file + File.separator + "documentation.json");
        if (pfile.exists()) {
            this.page = pfile;
        }
        pfile = new File(file + File.separator + "references.json");
        if (pfile.exists()) {
            this.refs = pfile;
        }
        this.name = name;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
    	if (IContainer.class.isAssignableFrom(adapter) && adapter != IProject.class) {
           	return (T) Eclipse.INSTANCE.getIFolder(this.file);
    	} else if (IResource.class.isAssignableFrom(adapter) && adapter != IProject.class) {
            if (this.page != null) {
                return (T) Eclipse.INSTANCE.getIFile(this.page);
            } else if (this.refs != null) {
                return (T) Eclipse.INSTANCE.getIFile(this.refs);
            } else {
            	return (T) Eclipse.INSTANCE.getIFolder(this.file);
            }
        }
        return null;
    }

    @Override
    public ENavigatorItem[] getEChildren() {

        List<ENavigatorItem> ret = new ArrayList<>();
        Set<Trigger> sections = new HashSet<>();

        if (this.page != null) {
            ProjectDocumentation documentation = new ProjectDocumentation(this.page);
            for (String key : documentation.getItems()) {
                for (Trigger trigger : documentation.getTriggers(key)) {
                    if (trigger == Trigger.DEFINITION) {
                        ret.add(new EDocumentationPage(this, this.page, key, Trigger.DEFINITION, documentation));
                    } else if (!sections.contains(trigger)) {
                        ret.add(new EDocumentationScope(this, documentation, trigger, file));
                        sections.add(trigger);
                    }
                }
            }
        }

        for (File file : this.file.listFiles()) {
            if (file.isDirectory()) {
                String name = MiscUtilities.getFileBaseName(file);
                // if (!this.name.equals("Documentation")) {
                // name = this.name + "." + name;
                // }
                ret.add(new EDocumentationFolder(this, file, name));
            }
        }

        if (this.refs != null) {
            ret.add(new EReferencesPage(this, file, "References", new ProjectReferences(this.refs)));
        }

        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean hasEChildren() {
        int nfiles = file.listFiles().length;
        return nfiles > 0
                && (this.page == null || (this.page != null && this.page.length() > 8) || nfiles > 1);
    }
    
    public String getPath() {
    	if (this.name.equals("Documentation")) {
    		return "";
    	}
    	String ret = this.name;
    	ENavigatorItem p = getEParent();
    	while (p instanceof EDocumentationFolder && p.getEParent() instanceof EDocumentationFolder) {
    		ret = ((EDocumentationFolder)p).name + "." + ret;
    		p = p.getEParent();
    	}
    	return ret;
    }

}
