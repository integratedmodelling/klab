package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.kim.api.IKimObservable;

public class EDependency extends EKimObject {

	EDependency(String id, IKimObservable statement, ENavigatorItem parent) {
		super(id, statement, parent);
	}

	private static final long serialVersionUID = 193197111620427775L;

	@Override
	public String getNamespace() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {

		String name = id;
		IKimObservable observable = (IKimObservable) delegate_;
		if (observable.getMain() == null) {
			// remove first token
			int ns = name.indexOf(' ');
			if (ns > 0) {
				name = name.substring(ns + 1);
			}
		}
		return name;
	}
	
	@Override
	public boolean hasEChildren() {
		return false;
	}

}
