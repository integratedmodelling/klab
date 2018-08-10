package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.kim.api.IKimSymbolDefinition;

public class EDefinition extends EKimObject implements IKimSymbolDefinition {

	private static final long serialVersionUID = -8065004326025509869L;

	private String name;
	private Object value;
	
	public EDefinition(IKimSymbolDefinition definition, ENamespace parent) {
        super(definition.getName(), definition, parent);
        this.name = definition.getName();
        this.value = definition.getValue();
    }

	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return null;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Object getValue() {
		return value;
	}

	@Override
	public ENavigatorItem[] getEChildren() {
		return null;
	}

	@Override
	public boolean hasEChildren() {
		return false;
	}

}
