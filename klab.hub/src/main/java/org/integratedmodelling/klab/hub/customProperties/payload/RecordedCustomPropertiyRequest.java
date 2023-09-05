package org.integratedmodelling.klab.hub.customProperties.payload;

import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;

public class RecordedCustomPropertiyRequest {

    private CustomPropertyType type;
    private String name;

    public RecordedCustomPropertiyRequest() {
        super();
    }

    public RecordedCustomPropertiyRequest(CustomPropertyType type, String name) {
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
