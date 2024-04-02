package org.integratedmodelling.klab.hub.recordedCustomProperty.dto;

import java.time.LocalDateTime;
import java.util.Objects;

import org.integratedmodelling.klab.hub.api.GenericModel;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class is used to catalog CustomProperties that are known and have been used for Users or Groups.
 *
 */
@Document(collection = "CustomProperties")
@TypeAlias("CustomProperties")
public class RecordedCustomProperty extends GenericModel {

    private boolean isForUser = false;
    private boolean isForGroup = false;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public RecordedCustomProperty(String name) {
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
        RecordedCustomProperty other = (RecordedCustomProperty) obj;
        return isForGroup == other.isForGroup && isForUser == other.isForUser && Objects.equals(getName(), other.getName());
    }

}
