package org.integratedmodelling.klab.hub.api;

import java.time.Duration;
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

@Document(collection = "AgreementTemplates")
@TypeAlias("MongoAgreementTemplate")
public class AgreementTemplate {
    @Id
    private String id;

    @Enumerated(EnumType.STRING)
    private AgreementLevel agreementLevel;

    @Enumerated(EnumType.STRING)
    private AgreementType agreementType;

    private String text;

    private Boolean defaultTemplate;

    private Long defaultDuration;

    @Reference
    private Set<GroupEntry> defaultGroups = new HashSet<GroupEntry>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Boolean getDefaultTemplate() {
        return defaultTemplate;
    }

    public void setDefaultTemplate(Boolean defaultTemplate) {
        this.defaultTemplate = defaultTemplate;
    }


    public Long getDefaultDuration() {
        return defaultDuration;
    }

    public void setDefaultDuration(Long defaultDuration) {
        this.defaultDuration = defaultDuration;
    }

    public Set<GroupEntry> getDefaultGroups() {
        return defaultGroups;
    }

    public void setDefaultGroups(Set<GroupEntry> defaultGroups) {
        this.defaultGroups = defaultGroups;
    }

}
