package org.integratedmodelling.klab.engine.indexing;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;

public class SearchMatch implements IIndexingService.Match {

    String id;
    String name;
    String description = "";
    float score;
    Type matchType;
    Map<String, String> indexableFields = new HashMap<>();
    Set<IKimConcept.Type> conceptType = EnumSet.noneOf(IKimConcept.Type.class);
    boolean isAbstract = false;

    public SearchMatch() {
    }

    public SearchMatch(Type matchType, Set<org.integratedmodelling.kim.api.IKimConcept.Type> conceptType) {
        this.matchType = matchType;
        this.conceptType.addAll(conceptType);
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public float getScore() {
        return score;
    }

    @Override
    public Type getMatchType() {
        return matchType;
    }

    @Override
    public Set<org.integratedmodelling.kim.api.IKimConcept.Type> getConceptType() {
        return conceptType;
    }

    @Override
    public Map<String, String> getIndexableFields() {
        return indexableFields;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description == null ? "" : description;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setMatchType(Type matchType) {
        this.matchType = matchType;
    }

    public void setIndexableFields(Map<String, String> indexableFields) {
        this.indexableFields = indexableFields;
    }

    public void setConceptType(Set<IKimConcept.Type> conceptType) {
        this.conceptType = conceptType;
    }

    public boolean isAbstract() {
        return isAbstract;
    }

    public void setAbstract(boolean isAbstract) {
        this.isAbstract = isAbstract;
    }

}
