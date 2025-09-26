package org.integratedmodelling.klab.rest;

import java.util.Objects;

/**
 * Custom properties with visibility field
 * @author Enrico Girotto
 *
 */

public class CustomPropertyRest implements ICustomProperty{
    
    private String key;
    private String value;
    private Object valueObject;
    private boolean onlyAdmin;    
    
    public CustomPropertyRest() {}
    
    public CustomPropertyRest(String key, String value, boolean onlyAdmin) {
        this.key = key;
        this.value = value;
        this.onlyAdmin = onlyAdmin;
    }
    
    public CustomPropertyRest(String key, String value, boolean onlyAdmin, CustomPropertyTypeRest valueObject) {
        this.key = key;
        this.value = value;
        this.valueObject = valueObject;
        this.onlyAdmin = onlyAdmin;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public String getValue() {
        return this.value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    
    public Object getValueObject() {
        return valueObject;
    }

    public void setValueObject(Object valueObject2) {
        this.valueObject = valueObject2;
    }

    public boolean isOnlyAdmin() {
        return onlyAdmin;
    }
    public void setOnlyAdmin(boolean onlyAdmin) {
        this.onlyAdmin = onlyAdmin;
    }
    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        CustomPropertyRest other = (CustomPropertyRest) obj;
        return Objects.equals(key, other.key) && onlyAdmin == other.onlyAdmin && Objects.equals(value, other.value);
    }
    @Override
    public String toString() {
        return "CustomProperty [key=" + key + ", value=" + value + ", onlyAdmin=" + onlyAdmin + "]";
    }

}
