package org.integratedmodelling.klab.ide.navigator.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.klab.ide.utils.Eclipse;

public class EActorBehavior extends EKimObject implements IKActorsBehavior {

    private IKActorsBehavior behavior;

    EActorBehavior(IKActorsBehavior behavior, ENavigatorItem parent) {
        super(behavior.getName(), behavior, parent);
        this.behavior = behavior;
    }

    private static final long serialVersionUID = 6120904235254835394L;

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getAdapter(Class<T> adapter) {
        /*
         * The hierarchy is either IContainer or IFile, but if I put IFile in the last condition and
         * remove the others, the namespace isn't seen as a file. Leave like this although it looks
         * weird.
         */
        if (IContainer.class == adapter) {
            // ehm.
        } else if (IProject.class.isAssignableFrom(adapter)) {
            // boh
        } else if (IResource.class.isAssignableFrom(adapter)) {
            return (T) Eclipse.INSTANCE.getIFile(this.getFile());
        }
        return null;
    }

    @Override
    public IFile getIFile() {
        return Eclipse.INSTANCE.getIFile(this.getFile());
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>();
        for (IKActorsAction action : behavior.getActions()) {
            ret.add(new EActorAction(action, this));
        }
        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return behavior.getActions().size() > 0;
    }

    @Override
    public String getNamespace() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getName() {
        return behavior.getName();
    }

    @Override
    public Type getType() {
        return behavior.getType();
    }

    @Override
    public File getFile() {
        return behavior.getFile();
    }

    @Override
    public List<String> getImports() {
        return behavior.getImports();
    }

    @Override
    public List<IKActorsAction> getActions() {
        return behavior.getActions();
    }

    @Override
    public String getStyle() {
        return behavior.getStyle();
    }

    @Override
    public String getTag() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Platform getPlatform() {
        return behavior.getPlatform();
    }

    @Override
    public String getLabel() {
        return behavior.getLabel();
    }

    @Override
    public String getDescription() {
        return behavior.getDescription();
    }

    @Override
    public String getLogo() {
        return behavior.getLogo();
    }

    @Override
    public String getProjectId() {
        return behavior.getProjectId();
    }

    @Override
    public Map<String, String> getStyleSpecs() {
        return behavior.getStyleSpecs();
    }

    @Override
    public boolean isPublic() {
        return behavior.isPublic();
    }

    @Override
    public String getVersionString() {
        return behavior.getVersionString();
    }

    @Override
    public void visit(IKActorsBehavior.Visitor visitor) {
        behavior.visit(visitor);
    }

    @Override
    public String getOutput() {
        return behavior.getOutput();
    }

}
