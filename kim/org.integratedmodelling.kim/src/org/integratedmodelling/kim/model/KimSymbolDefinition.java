package org.integratedmodelling.kim.model;

import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.kim.DefineStatement;

public class KimSymbolDefinition extends KimStatement implements IKimSymbolDefinition {

	private static final long serialVersionUID = -8891996252364410583L;

	private String name;
	private String defineClass;
	private Object value;
	
	public KimSymbolDefinition(DefineStatement statement, IKimStatement parent) {
		
		super(statement, parent);
		
    	IKimNamespace namespace = Kim.INSTANCE.getNamespace(statement);
		this.name = statement.getDefineBody().getName();
		this.defineClass = statement.getDefineBody().getDefineClass();
		this.value = Kim.INSTANCE.parseValue(statement.getDefineBody().getValue(), namespace);
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Object getValue() {
		return this.value;
	}

	@Override
	public String getDefineClass() {
		return this.defineClass;
	}

}
