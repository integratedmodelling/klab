package org.integratedmodelling.klab.ide.navigator.model;

public class ETestFolder extends ENavigatorItem {

	public ETestFolder(EProject parent) {
        super(parent.id + "#__RESOURCES__", parent);
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		
		return null;
	}

    @Override
    public ENavigatorItem[] getEChildren() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean hasEChildren() {
        // TODO Auto-generated method stub
        return false;
    }

}
