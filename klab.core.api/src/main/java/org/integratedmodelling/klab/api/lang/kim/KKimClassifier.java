package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;

import org.integratedmodelling.klab.api.collections.impl.Range;
import org.integratedmodelling.klab.api.knowledge.KArtifact;

/**
 * Syntactic bean for a k.IM classifier, used in both classifications and lookup tables.
 * 
 * @author ferdinando.villa
 *
 */
public interface KKimClassifier extends KKimStatement {

    boolean isCatchAll();

    boolean isCatchAnything();

    boolean isNegated();

    KKimConcept getConceptMatch();

    Double getNumberMatch();

    Boolean getBooleanMatch();

    List<KKimClassifier> getClassifierMatches();

    Range getIntervalMatch();

    boolean isNullMatch();

    KKimExpression getExpressionMatch();

    String getStringMatch();

    List<KKimConcept> getConceptMatches();

    KKimQuantity getQuantityMatch();

    KKimDate getDateMatch();

    /**
     * The type of the object incarnated by this classifier
     * 
     * @return
     */
    KArtifact.Type getType();

}
