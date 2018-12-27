package org.integratedmodelling.klab.ide.navigator.model;

public class EDocumentationPage extends ENavigatorItem {

	private String id;
	
	protected EDocumentationPage(String id, ENavigatorItem parent) {
		super(id, parent);
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
	
	public String getPagePath() {
		return getEParent(EDocumentationFolder.class).getName() + "." + id;
	}
}
