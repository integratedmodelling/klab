package org.integratedmodelling.klab.ide.navigator.model.documentation;

import org.integratedmodelling.klab.api.documentation.IDocumentation.Trigger;
import org.integratedmodelling.klab.client.documentation.ProjectDocumentation;
import org.integratedmodelling.klab.ide.navigator.model.ENavigatorItem;
import org.integratedmodelling.klab.utils.StringUtil;

public class EDocumentationItem extends ENavigatorItem {

    private String item;
    private String section;
    private Trigger trigger;
    private ProjectDocumentation documentation;
    
    protected EDocumentationItem(String key, String section, Trigger trigger, ProjectDocumentation documentation, ENavigatorItem parent) {
        super(key + "_" + section + "_" + trigger, parent);
        this.setItem(key);
        this.setSection(section);
        this.setDocumentation(documentation);
        this.setTrigger(trigger);
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public Trigger getTrigger() {
        return trigger;
    }

    public void setTrigger(Trigger trigger) {
        this.trigger = trigger;
    }

    public ProjectDocumentation getDocumentation() {
        return documentation;
    }

    public void setDocumentation(ProjectDocumentation documentation) {
        this.documentation = documentation;
    }
    
    public String getName() {
        return StringUtil.capitalize(section.toLowerCase());
    }

}
