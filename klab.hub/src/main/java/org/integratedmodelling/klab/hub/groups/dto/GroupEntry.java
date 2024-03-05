package org.integratedmodelling.klab.hub.groups.dto;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class GroupEntry {
    
    @DBRef
    private MongoGroup group;
    private LocalDateTime start;
    private LocalDateTime expiration;
    
    
    public GroupEntry(MongoGroup group, LocalDateTime expiration) {
        this.group = group;
        setStart();
        setExpiration(expiration);
    }
    
    public GroupEntry(MongoGroup group) {
        this(group, null);
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
        if(expiration != null) {
            this.expiration = expiration;
        } else if (this.group != null && this.group.getDefaultExpirationTime() != 0) {
            if (this.start != null) {
                this.expiration = this.start.plus(group.getDefaultExpirationTime(), ChronoUnit.MILLIS);
            } else {
                this.expiration = LocalDateTime.now().plus(group.getDefaultExpirationTime(), ChronoUnit.MILLIS);
            }
        }
    }

    public LocalDateTime getInception() {
        return start;
    }

    public boolean isValid() {
        if (!isExpirable()) {
            return true;
        }
        return !isExpired();
    }

    public boolean isExpired() {
        return isExpirable() && this.expiration.isBefore(LocalDateTime.now());
    }

    public boolean isExpirable() {
        // Groups with null expiration dates are the ones with no expiration date
        return this.expiration != null;
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
