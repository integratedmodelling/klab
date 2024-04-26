package org.integratedmodelling.klab.hub.nodes.dtos;

import java.time.LocalDateTime;
import java.util.Set;

import org.integratedmodelling.klab.hub.api.IdentityModel;
import org.integratedmodelling.klab.hub.groups.dto.MongoGroup;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Nodes")
@TypeAlias("MongoNode")
public class MongoNode extends IdentityModel {

    private String url;

    @Indexed(unique = false)
    private String email;

    @DBRef(lazy = true)
    private Set<MongoGroup> groups;

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<MongoGroup> getGroups() {
        return this.groups;
    }

    public void setGroups(Set<MongoGroup> groups) {
        this.groups = groups;
    }

    public void removeGroupByName(String groupName) {
        groups.removeIf(grp -> grp.getName().equalsIgnoreCase(groupName));
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
