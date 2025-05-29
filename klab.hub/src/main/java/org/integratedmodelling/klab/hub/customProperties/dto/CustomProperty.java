package org.integratedmodelling.klab.hub.customProperties.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.integratedmodelling.klab.hub.api.GenericModel;
import org.integratedmodelling.klab.hub.customProperties.enums.CustomPropertyType;
import org.integratedmodelling.klab.hub.users.JsonUtils;
import org.integratedmodelling.klab.rest.CustomPropertyRest;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is used to catalog CustomProperties that are known and have been used for Users or Groups.
 *
 */
@Document(collection = "CustomProperties")
@TypeAlias("CustomProperties")
public class CustomProperty extends GenericModel {

    private boolean isForUser = false;
    private boolean isForGroup = false;

    private String key;
    private String value;
    private boolean onlyAdmin;
    
    private CustomPropertyType type;
    
    private static final ObjectMapper mapper = new ObjectMapper();    

    public CustomProperty() {
        super();
    }

    public CustomProperty(String key, Object valueObject) {
        super();
        this.key = key;
        setValue(valueObject);
    }
    
    
    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public CustomProperty(String name) {
        setName(name);
    }

    public boolean isForUser() {
        return isForUser;
    }
    public void setForUser(boolean isForUser) {
        this.isForUser = isForUser;
    }
    public boolean isForGroup() {
        return isForGroup;
    }
    public void setForGroup(boolean isForGroup) {
        this.isForGroup = isForGroup;
    }
    
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValueAsString() {
        return value;
    }
    
    public JsonNode getValueAsJsonNode() {
        try {
            return mapper.readTree(value);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing value to JsonNode", e);
        }
    }

    public <T> T getValueAs(Class<T> clazz) {
        try {
            return mapper.readValue(value, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing value", e);
        }
    }

    public <T> T getValueAs(TypeReference<T> typeRef) {
        try {
            return mapper.readValue(value, typeRef);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing value", e);
        }
    }

    public void setValue(Object valueObject) {
        try {
            this.value = (String) valueObject;
            //this.value = mapper.writeValueAsString(valueObject);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing value", e);
        }
    }
    
    public CustomPropertyRest getCustomPropertyRest() {
        CustomPropertyRest customPropertyRest =  new CustomPropertyRest();
        customPropertyRest.setKey(key);
        customPropertyRest.setOnlyAdmin(onlyAdmin);
        
        /* Set value as String */
        customPropertyRest.setValue(value);
        
        /* If it's JSON or defined in CustomPropertyKey, convert it to its specific type. */
        CustomPropertyKey propertyKey = CustomPropertyKey.fromKey(key);
        if (propertyKey != null) {
            try {
                Object deserializedValue = mapper.readValue(value, propertyKey.getTypeReference());
                customPropertyRest.setValueObject(deserializedValue);
            } catch (Exception e) {
                throw new RuntimeException("Error deserializing value for key: " + key, e);
            }
        } else if (JsonUtils.isValidJson(value)) {
            customPropertyRest.setValueObject(getValueAsJsonNode());
        }
        return customPropertyRest;
    }


    public boolean isOnlyAdmin() {
        return onlyAdmin;
    }

    public void setOnlyAdmin(boolean onlyAdmin) {
        this.onlyAdmin = onlyAdmin;
    }

    public CustomPropertyType getType() {
        return type;
    }

    public void setType(CustomPropertyType type) {
        this.type = type;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isForGroup, isForUser, getName());
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
        return isForGroup == other.isForGroup && isForUser == other.isForUser && Objects.equals(getName(), other.getName());
    }

}
