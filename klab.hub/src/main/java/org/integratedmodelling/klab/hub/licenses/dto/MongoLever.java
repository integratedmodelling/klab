package org.integratedmodelling.klab.hub.licenses.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.auth.Role;
import org.integratedmodelling.klab.hub.api.IdentityModel;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Transient;

@Document(collection = "Levers")
@TypeAlias("MongoLever")
public class MongoLever extends IdentityModel {

    @Indexed(unique = false)
    String email;

    String baseUrl;

    @Transient // There is only one role of a lever
    final Set<Role> roles = new HashSet<>();

    @CreatedBy
    private String createdBy;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedBy
    private String lastModifiedBy;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    public String getName() {
        return super.getName();
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Collection<Role> getAuthorities() {
        return new HashSet<>(getRoles());
    }

    private Set<Role> getRoles() {
        this.roles.clear();
        this.roles.add(Role.ROLE_LEVER);
        return this.roles;
    }

}