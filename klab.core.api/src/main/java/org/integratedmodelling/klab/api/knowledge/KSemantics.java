package org.integratedmodelling.klab.api.knowledge;

public interface KSemantics extends KKnowledge {

    /**
     * Semantics always resides in a namespace.
     * 
     * @return
     */
    String getNamespace();

    /**
     * Return the raw semantics for this object, normally a KConcept. If this is a KConcept, return
     * self.
     * 
     * @return
     */
    KSemantics semantics();

    /**
     * All semantic knowledge in a worldview should exist in a conceptual domain.
     * 
     * @return
     */
    KSemantics domain();

    /**
     * 
     * @param other
     * @return
     */
    boolean is(KSemantics other);

    /**
     * 
     * @param type
     * @return
     */
    boolean is(SemanticType type);

    /**
     * 
     * @return
     */
    boolean isAbstract();

}
