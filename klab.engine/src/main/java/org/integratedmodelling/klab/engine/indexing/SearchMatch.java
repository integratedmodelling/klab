package org.integratedmodelling.klab.engine.indexing;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;

public class SearchMatch implements IIndexingService.Match {

    String id;
    String name;
    String description;
    int rank;
    Type matchType;

    Set<IKimConcept.Type> conceptType = EnumSet.noneOf(IKimConcept.Type.class);

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
    public int getRank() {
        return rank;
    }

    @Override
    public Type getMatchType() {
        return matchType;
    }

    @Override
    public Set<org.integratedmodelling.kim.api.IKimConcept.Type> getConceptType() {
        return conceptType;
    }

}
