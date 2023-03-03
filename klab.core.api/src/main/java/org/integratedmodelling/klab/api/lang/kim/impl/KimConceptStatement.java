package org.integratedmodelling.klab.api.lang.kim.impl;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimConcept;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;
import org.integratedmodelling.klab.api.lang.kim.KKimRestriction;

public class KimConceptStatement extends KimStatement implements KKimConceptStatement {

    private static final long serialVersionUID = 2640057106561346868L;
    
    private Set<SemanticType> type = EnumSet.noneOf(SemanticType.class);
    private String authorityRequired;
    private List<KKimConcept> qualitiesAffected = new ArrayList<>();
    private List<KKimConcept> observablesCreated = new ArrayList<>();
    private List<KKimConcept> traitsConferred = new ArrayList<>();
    private List<KKimConcept> traitsInherited = new ArrayList<>();
    private List<KKimConcept> requiredExtents = new ArrayList<>();
    private List<KKimConcept> requiredRealms = new ArrayList<>();
    private List<KKimConcept> requiredAttributes = new ArrayList<>();
    private List<KKimConcept> requiredIdentities = new ArrayList<>();
    private List<KKimConcept> emergenceTriggers = new ArrayList<>();
    private List<KKimRestriction> restrictions = new ArrayList<>();
    private boolean alias;
    private boolean isAbstract;
    private String name;
    private boolean macro;
    private List<Pair<KKimConcept, DescriptionType>> observablesDescribed = new ArrayList<>();
    private List<ApplicableConcept> subjectsLinked = new ArrayList<>();
    private List<ApplicableConcept> appliesTo = new ArrayList<>();
    private String docstring;
    private String upperConceptDefined;
    private String authorityDefined;

    @Override
    public Set<SemanticType> getType() {
        return this.type;
    }

    @Override
    public String getAuthorityRequired() {
        return this.authorityRequired;
    }

    @Override
    public List<KKimConcept> getQualitiesAffected() {
        return this.qualitiesAffected;
    }

    @Override
    public List<KKimConcept> getObservablesCreated() {
        return this.observablesCreated;
    }

    @Override
    public List<KKimConcept> getTraitsConferred() {
        return this.traitsConferred;
    }

    @Override
    public List<KKimConcept> getTraitsInherited() {
        return this.traitsInherited;
    }

    @Override
    public List<KKimConcept> getRequiredExtents() {
        return this.requiredExtents;
    }

    @Override
    public List<KKimConcept> getRequiredRealms() {
        return this.requiredRealms;
    }

    @Override
    public List<KKimConcept> getRequiredAttributes() {
        return this.requiredAttributes;
    }

    @Override
    public List<KKimConcept> getRequiredIdentities() {
        return this.requiredIdentities;
    }

    @Override
    public List<KKimConcept> getEmergenceTriggers() {
        return this.emergenceTriggers;
    }

    @Override
    public List<KKimRestriction> getRestrictions() {
        return this.restrictions;
    }

    @Override
    public boolean isAlias() {
        return this.alias;
    }

    @Override
    public boolean isAbstract() {
        return this.isAbstract;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean isMacro() {
        return this.macro;
    }

    @Override
    public List<Pair<KKimConcept, DescriptionType>> getObservablesDescribed() {
        return this.observablesDescribed;
    }

    @Override
    public List<ApplicableConcept> getSubjectsLinked() {
        return this.subjectsLinked;
    }

    @Override
    public List<ApplicableConcept> getAppliesTo() {
        return this.appliesTo;
    }

    @Override
    public String getDocstring() {
        return this.docstring;
    }

    public void setType(Set<SemanticType> type) {
        this.type = type;
    }

    public void setAuthorityRequired(String authorityRequired) {
        this.authorityRequired = authorityRequired;
    }

    public void setQualitiesAffected(List<KKimConcept> qualitiesAffected) {
        this.qualitiesAffected = qualitiesAffected;
    }

    public void setObservablesCreated(List<KKimConcept> observablesCreated) {
        this.observablesCreated = observablesCreated;
    }

    public void setTraitsConferred(List<KKimConcept> traitsConferred) {
        this.traitsConferred = traitsConferred;
    }

    public void setTraitsInherited(List<KKimConcept> traitsInherited) {
        this.traitsInherited = traitsInherited;
    }

    public void setRequiredExtents(List<KKimConcept> requiredExtents) {
        this.requiredExtents = requiredExtents;
    }

    public void setRequiredRealms(List<KKimConcept> requiredRealms) {
        this.requiredRealms = requiredRealms;
    }

    public void setRequiredAttributes(List<KKimConcept> requiredAttributes) {
        this.requiredAttributes = requiredAttributes;
    }

    public void setRequiredIdentities(List<KKimConcept> requiredIdentities) {
        this.requiredIdentities = requiredIdentities;
    }

    public void setEmergenceTriggers(List<KKimConcept> emergenceTriggers) {
        this.emergenceTriggers = emergenceTriggers;
    }

    public void setRestrictions(List<KKimRestriction> restrictions) {
        this.restrictions = restrictions;
    }

    public void setAlias(boolean alias) {
        this.alias = alias;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMacro(boolean macro) {
        this.macro = macro;
    }

    public void setObservablesDescribed(List<Pair<KKimConcept, DescriptionType>> observablesDescribed) {
        this.observablesDescribed = observablesDescribed;
    }

    public void setSubjectsLinked(List<ApplicableConcept> subjectsLinked) {
        this.subjectsLinked = subjectsLinked;
    }

    public void setAppliesTo(List<ApplicableConcept> appliesTo) {
        this.appliesTo = appliesTo;
    }

    public void setDocstring(String docstring) {
        this.docstring = docstring;
    }

    @Override
    public String getUpperConceptDefined() {
        return this.upperConceptDefined;
    }

    @Override
    public String getAuthorityDefined() {
        return this.authorityDefined;
    }

    public void setUpperConceptDefined(String upperConceptDefined) {
        this.upperConceptDefined = upperConceptDefined;
    }

    public void setAuthorityDefined(String authorityDefined) {
        this.authorityDefined = authorityDefined;
    }

}
