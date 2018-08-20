package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;

public class AttributeReference implements IResource.Attribute {

    private String  name;
    private Type    type;
    private boolean key;
    private boolean optional;
    private String exampleValue;
    
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public boolean isKey() {
        return this.key;
    }

    @Override
    public boolean isOptional() {
        return this.optional;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setKey(boolean key) {
        this.key = key;
    }

    public void setOptional(boolean optional) {
        this.optional = optional;
    }

    @Override
    public String toString() {
        return "AttributeReference [name=" + name + ", type=" + type + ", key=" + key + ", optional="
                + optional + "]";
    }

	public String getExampleValue() {
		return exampleValue;
	}

	public void setExampleValue(String exampleValue) {
		this.exampleValue = exampleValue;
	}

}
