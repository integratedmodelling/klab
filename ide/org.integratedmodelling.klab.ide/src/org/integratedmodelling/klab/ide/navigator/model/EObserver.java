package org.integratedmodelling.klab.ide.navigator.model;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimBehavior;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;

public class EObserver extends EKimObject implements IKimObserver {

    private static final long serialVersionUID = -5755690442793814545L;

    IKimObserver delegate;
    ENamespace namespace;

    public EObserver(String id, IKimObserver statement, ENavigatorItem parent, ENamespace namespace) {
        super(id, statement, parent);
        this.delegate = statement;
        this.namespace = namespace;
    }

    public String getName() {
        return delegate.getName();
    }

    public IKimBehavior getBehavior() {
        return delegate.getBehavior();
    }

    public IKimObservable getObservable() {
        return delegate.getObservable();
    }

    public List<IKimAnnotation> getAnnotations() {
        return delegate.getAnnotations();
    }

    public List<IKimObservable> getStates() {
        return delegate.getStates();
    }

    @Override
    public String getDocstring() {
        return delegate.getDocstring();
    }

    @Override
    public ENavigatorItem[] getEChildren() {
        List<ENavigatorItem> ret = new ArrayList<>(delegate.getChildren().size());
        for (IKimScope child : delegate.getChildren()) {
            ret.add(new EObserver(namespace.getName() + "." + ((IKimObserver) child).getName(), (IKimObserver) child,
                    this, namespace));
        }
        return ret.toArray(new ENavigatorItem[ret.size()]);
    }

    @Override
    public boolean hasEChildren() {
        return delegate.getChildren().size() > 0;
    }

    @Override
    public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

}
