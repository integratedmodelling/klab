package org.integratedmodelling.klab.api.services;

import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.KConcept;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.KSemantics;
import org.integratedmodelling.klab.api.knowledge.SemanticType;
import org.integratedmodelling.klab.api.lang.kim.KKimConceptStatement;

public interface KReasoner {

    /**
     * All services publish capabilities and have a call to obtain them.
     * 
     * @author Ferd
     *
     */
    interface Capabilities {

    }

    Capabilities getCapabilities();

    /**
     * 
     * @param definition
     * @return
     */
    KConcept resolveConcept(String definition);

    /**
     * 
     * @param definition
     * @return
     */
    KObservable resolveObservable(String definition);

    Collection<KConcept> operands(KSemantics target);

    Collection<KConcept> children(KSemantics target);

    Collection<KConcept> parents(KSemantics target);

    Collection<KConcept> allChildren(KSemantics target);

    Collection<KConcept> allParents(KSemantics target);

    Collection<KConcept> closure(KSemantics target);

    int semanticDistance(KSemantics target);

    int semanticDistance(KSemantics target, KSemantics context);

    KConcept coreObservable(KConcept first);

    Pair<KConcept, List<SemanticType>> splitOperators(KConcept concept);

    Collection<KConcept> traits(KConcept concept);

    int assertedDistance(KConcept kConcept, KConcept t);

    boolean hasTrait(KConcept concept, KConcept t);

    Collection<KConcept> roles(KConcept concept);

    boolean hasRole(KConcept concept, KConcept t);

    KConcept directContext(KConcept concept);

    KConcept context(KConcept concept);

    KConcept directInherent(KConcept concept);

    KConcept inherent(KConcept concept);

    KConcept directGoal(KConcept concept);

    KConcept goal(KConcept concept);

    KConcept directCooccurrent(KConcept concept);

    KConcept directCausant(KConcept concept);

    KConcept directCaused(KConcept concept);

    KConcept directAdjacent(KConcept concept);

    KConcept directCompresent(KConcept concept);

    KConcept directRelativeTo(KConcept concept);

    KConcept cooccurrent(KConcept concept);

    KConcept causant(KConcept concept);

    KConcept caused(KConcept concept);

    KConcept adjacent(KConcept concept);

    KConcept compresent(KConcept concept);

    KConcept relativeTo(KConcept concept);

    Object displayLabel(KSemantics concept);

    Object codeName(KSemantics concept);

    String style(KConcept concept);

    interface Admin {

        /**
         * The "port" to ingest a wordview, available only to admins. Also makes it possible for the
         * resolver to declare local concepts as long as it owns the semantic service. Definition
         * must be made only in terms of known concepts (no forward declaration is allowed), so
         * order of ingestion is critical.
         * 
         * @param statement
         * @return
         */
        KConcept addConcept(KKimConceptStatement statement);

    }

}