package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.runtime.IAdaptable;

/**
 * Root element class for the tree elements. We create these simple wrappers as needed, their
 * string id/URN is the basis for equality.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class ENavigatorItem implements IAdaptable {

    ENavigatorItem parent;
    String id;

    protected ENavigatorItem(String id, ENavigatorItem parent) {
        this.id = id;
        this.parent = parent;
    }

    public String getId() {
        return id;
    }
    
    @SuppressWarnings("unchecked")
    public <T extends ENavigatorItem> T getEParent(Class<T> cls) {
        if (cls.isAssignableFrom(this.getClass())) {
            return (T) this;
        }
        ENavigatorItem parent = getEParent();
        return parent == null ? null : parent.getEParent(cls);
    }

    public ENavigatorItem getEParent() {
        return parent;
    }

    public abstract ENavigatorItem[] getEChildren();

    public abstract boolean hasEChildren();

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ENavigatorItem other = (ENavigatorItem) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}
