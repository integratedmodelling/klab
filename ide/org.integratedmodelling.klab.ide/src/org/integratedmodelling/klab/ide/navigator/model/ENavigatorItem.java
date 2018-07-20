package org.integratedmodelling.klab.ide.navigator.model;

import org.eclipse.core.runtime.IAdaptable;

/**
 * Root element class for the tree elements.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class ENavigatorItem implements IAdaptable {

	ENavigatorItem parent;

	protected ENavigatorItem() {
	}

	protected ENavigatorItem(ENavigatorItem parent) {
		this.parent = parent;
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
}
