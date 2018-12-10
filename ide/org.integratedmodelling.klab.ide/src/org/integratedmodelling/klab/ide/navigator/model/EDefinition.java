package org.integratedmodelling.klab.ide.navigator.model;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;

public class EDefinition extends EKimObject implements IKimSymbolDefinition, EDocumentable {

	private static final long serialVersionUID = -8065004326025509869L;

	private String name;
	private String namespace;
	private Object value;

	public EDefinition(IKimSymbolDefinition definition, ENamespace parent) {
		super(definition.getName(), definition, parent);
		this.name = definition.getName();
		this.namespace = definition.getNamespace();
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

	@Override
	public boolean isDocumented() {

		for (IKimAnnotation annotation : delegate_.getAnnotations()) {
			// TODO parameterize the annotations recognized
			if (annotation.getName().equals("documented")) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getDocId() {
		for (IKimAnnotation annotation : delegate_.getAnnotations()) {
			// TODO parameterize the annotations recognized
			if (annotation.getName().equals("documented")) {
				return annotation.getParameters().get(IKimAnnotation.DEFAULT_PARAMETER_NAME, String.class);
			}
		}
		return null;
	}

	@Override
	public String getNamespace() {
		return namespace;
	}

}
