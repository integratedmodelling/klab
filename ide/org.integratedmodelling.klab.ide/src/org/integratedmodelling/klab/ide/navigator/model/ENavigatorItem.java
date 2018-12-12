package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.runtime.IAdaptable;

/**
 * Root element class for the tree elements. We create these simple wrappers as
 * needed, their string id/URN is the basis for equality.
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
		return parent == null ? null : parent.getEParent(cls);
	}

	public ENavigatorItem getEParent() {
		return parent;
	}

	public abstract ENavigatorItem[] getEChildren();

	public abstract boolean hasEChildren();

	@Override
	public int hashCode() {
		return id == null ? 0 : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (id == null || obj == null) {
			return false;
		}
		return this == obj || (this.getClass() == obj.getClass() && ((ENavigatorItem) obj).id != null
				&& ((ENavigatorItem) obj).id.equals(id));
	}

	public boolean isDocumented() {
		return false;
	}
	
	public void setParent(ENavigatorItem parent) {
		this.parent = parent;
	}

}
