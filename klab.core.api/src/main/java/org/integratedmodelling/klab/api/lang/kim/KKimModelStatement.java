package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;
import java.util.Optional;

import org.integratedmodelling.klab.api.knowledge.KArtifact;
import org.integratedmodelling.klab.api.lang.KContextualizable;

public interface KKimModelStatement extends KKimActiveStatement {

    Optional<KKimConcept> getReinterpretingRole();

    List<KKimObservable> getDependencies();

    List<KKimObservable> getObservables();

    /**
     * Data type of primary observable meant to discriminate void and non-semantic models from the
     * semantic ones. Can be either of VOID, CONCEPT, NUMBER, TEXT or BOOLEAN.
     * 
     * @return
     */
    KArtifact.Type getType();

    List<String> getResourceUrns();

    boolean isLearningModel();

    boolean isInterpreter();

    boolean isInstantiator();

    String getName();

    Optional<Object> getInlineValue();

    /**
     * Contextualizer or processor(s) given after 'using'
     * 
     * @return computables or an empty list
     */
    List<KContextualizable> getContextualization();

    String getDocstring();

    /**
     * Normally true, it will return false in models that were expressed as non-semantic operations,
     * using the 'number', 'text', etc. keywords. These are also, by default, private and are used
     * only directly by name.
     * 
     * @return
     */
    boolean isSemantic();
}
