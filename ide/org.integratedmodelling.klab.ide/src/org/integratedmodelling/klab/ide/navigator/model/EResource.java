package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.klab.rest.ResourceReference;

public class EResource extends ENavigatorItem {

	ResourceReference resource;
	
	protected EResource(ResourceReference resource, ENavigatorItem parent) {
		super(resource.getUrn(), parent);
		this.resource = resource;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
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

	public boolean isOnline() {
		return true;
	}
}
