package org.integratedmodelling.klab.hub.api;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
    
    @DBRef
    private MongoGroup group;
    private LocalDateTime start;
    private LocalDateTime expiration;
    
    
    public GroupEntry(MongoGroup group, LocalDateTime expiration) {
        this.group = group;
        if(expiration != null) {
            this.expiration = expiration;
        } else {
            this.expiration = LocalDateTime.now().plusDays(365);
        }
        setStart();
    }
    
    public GroupEntry(MongoGroup group) {
        this.group = group;
        this.expiration = LocalDateTime.now().plusDays(365);
        setStart();
    }
    /*
    private static LocalDateTime getDefaultExpirationPeriod(MongoGroup group) {
        return group.getDefaultExpirationPeriod() == null
                || group.getDefaultExpirationPeriod().equals(Period.ZERO) ? null : LocalDateTime.now().plus(group.getDefaultExpirationPeriod());
    }
    */
    public GroupEntry() {
    }
    
    public void setStart(LocalDateTime start) {
        this.start = start;
    }
    
    public void setStart() {
        this.start = LocalDateTime.now();
    }

    public String getGroupName() {
        return group.getName();
    }

    public LocalDateTime getExpiration() {
        return expiration;

    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public LocalDateTime getInception() {
        return start;
    }
    
    public boolean isExpired() {
        return this.expiration != null && this.expiration.isAfter(LocalDateTime.now());
    }

    public MongoGroup getGroup() {
        return group;
    }
    
    public void setGroup(MongoGroup group) {
        this.group = group;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(group);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        GroupEntry other = (GroupEntry) obj;
        return Objects.equals(group, other.group);
    }
}
