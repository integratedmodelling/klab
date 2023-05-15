package org.integratedmodelling.klab.hub.customProperties.payload;

import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertiesType;

public class CustomPropertiesRequest {
    
    private CustomPropertiesType type;
    private String name;
    
    
    
    public CustomPropertiesRequest() {
        super();
    }

    public CustomPropertiesRequest(CustomPropertiesType customPropertiesType, String name) {
        super();
        this.type = customPropertiesType;
        this.name = name;
    }
    
    public CustomPropertiesType getCustomPropertiesType() {
        return type;
    }
    public void setCustomPropertiesType(CustomPropertiesType customPropertiesType) {
        this.type = customPropertiesType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    

}
