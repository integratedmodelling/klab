package org.integratedmodelling.klab.rest;

import java.util.Objects;


/**
 * Custom properties with visibility field
 * @author Enrico Girotto
 *
 */

public class CustomProperty {
    
    private String key;
    private String value;
    private boolean onlyAdmin;
    
    public CustomProperty(String key, String value, boolean onlyAdmin) {
        this.key = key;
        this.value = value;
        this.onlyAdmin = onlyAdmin;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
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
        CustomProperty other = (CustomProperty) obj;
        return Objects.equals(key, other.key) && onlyAdmin == other.onlyAdmin && Objects.equals(value, other.value);
    }
    @Override
    public String toString() {
        return "CustomProperty [key=" + key + ", value=" + value + ", onlyAdmin=" + onlyAdmin + "]";
    }

}
