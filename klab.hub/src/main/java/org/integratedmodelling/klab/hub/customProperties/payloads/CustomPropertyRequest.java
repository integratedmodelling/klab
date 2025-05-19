package org.integratedmodelling.klab.hub.customProperties.payloads;

import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;

public class CustomPropertyRequest {

    private CustomPropertyType type;
    private String name;

    public CustomPropertyRequest() {
        super();
    }

    public CustomPropertyRequest(CustomPropertyType type, String name) {
        super();
        this.type = type;
        this.name = name;
    }

    public CustomPropertyType getCustomPropertiesType() {
        return type;
    }
    public void setCustomPropertyType(CustomPropertyType type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

}
