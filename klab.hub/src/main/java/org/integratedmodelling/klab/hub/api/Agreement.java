package org.integratedmodelling.klab.hub.api;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.hub.enums.AgreementLevel;
import org.integratedmodelling.klab.hub.enums.AgreementType;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;


@Document(collection="Agreements")
@TypeAlias("MongoAgreement")
public class Agreement {
    
    @Id
    private String id;    
    
    @Enumerated(EnumType.STRING)
    private AgreementLevel agreementLevel;
    
    @Enumerated(EnumType.STRING)
    private AgreementType agreementType;
    
    private Date transactionDate;
    
    private Date validDate;
    
    private Date expirationDate;
    
    private Date revokedDate;   
    
    private String ownAgreement;
    

    @Reference
    private Set<GroupEntry> groupEntries =  new HashSet<>(); // research groups, etc. in web tool


    public AgreementLevel getAgreementLevel() {
        return agreementLevel;
    }


    public void setAgreementLevel(AgreementLevel agreementLevel) {
        this.agreementLevel = agreementLevel;
    }


    public AgreementType getAgreementType() {
        return agreementType;
    }


    public void setAgreementType(AgreementType agreementType) {
        this.agreementType = agreementType;
    }


    public Date getTransactionDate() {
        return transactionDate;
    }


    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }


    public Date getValidDate() {
        return validDate;
    }


    public void setValidDate(Date validDate) {
        this.validDate = validDate;
    }


    public Date getExpirationDate() {
        return expirationDate;
    }


    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }


    public Date getRevokedDate() {
        return revokedDate;
    }


    public void setRevokedDate(Date revokedDate) {
        this.revokedDate = revokedDate;
    }


    public void addGroupEntries(GroupEntry... groups) {
        this.groupEntries.addAll(Arrays.asList(groups));
    }

    public void addGroupEntries(Set<GroupEntry> groups) {
        this.groupEntries.addAll(groups);
    }

    public void setGroupEntries(Set<GroupEntry> groups) {
        this.groupEntries = groups;
    }
    
    public void removeGroupEntries(Set<GroupEntry> groupEntries) {
        
        Set<String> names = new HashSet<>();
        groupEntries
          .forEach(e -> {
              String name = e.getGroupName();
              names.add(name);
          });
        
        if(groupEntries.isEmpty()) {
            return;
        }
        
        Set<GroupEntry> entries = getGroupEntries();
        entries.removeIf(e -> names.contains(e.getGroupName()));        
        setGroupEntries(entries);
        
    }

    public Set<GroupEntry> getGroupEntries() {
        return groupEntries;
    }
    
    public boolean userGroupsOverlapWith(HashSet<GroupEntry> groups) {
        if (groups == null) {
            // force this to be checked by set intersection, rather than instantly failing (preserves logic)
            groups = new HashSet<>();
        }

        if (groups.contains(User.GLOBAL_GROUP)) {
            return true;
        }

        Set<GroupEntry> list = getGroupEntries(); // returns a copy
        list.retainAll(groups);
        if (list.size() > 0) {
            return true;
        }

        return false;
    }


    public String getOwnAgreement() {
        return ownAgreement;
    }


    public void setOwnAgreement(String ownAgreement) {
        this.ownAgreement = ownAgreement;
    }

    public boolean isRevoked() {
        return revokedDate != null;
    }

    public boolean isExpirable() {
        // Agreements with null expiration dates are the ones with no expiration date
        return expirationDate != null;
    }

    public boolean isExpired() {
        if(!isExpirable()) {
            return false;
        }
        return expirationDate.toInstant().isBefore(Instant.now());
    }

    public boolean isValid() {
        return !isRevoked() && !isExpired();
    }

}
