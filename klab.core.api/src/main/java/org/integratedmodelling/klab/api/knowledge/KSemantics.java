package org.integratedmodelling.klab.api.knowledge;

public interface KSemantics extends KKnowledge {

    /**
     * The name is never null and is meant for human and code consumption. It is is always a simple
     * lowercase identifier. In a {@link KObservable} it can be set using the 'named' clause. If not
     * a KObservable or 'named' clause is present, a name is computed similarly to
     * {@link #getReferenceName()} but without using disambiguating namespaces, therefore not
     * guaranteeing a 1:1 correspondence to the semantics but with enough predictability to not have
     * to use 'named' in simple situations to refer to the observable. It's always a lowercase valid
     * identifier in k.IM, k.DL, and most languages. Even if 'named' is given, the name may be
     * different from the stated because of disambiguation when the observable is used in a
     * dataflow.
     *
     * @return the name of this observable
     */
    String getName();

    /**
     * The reference name is the default name and only depends on the contents of the observable. It
     * is uniquely related to the semantics. It may be modified for disambiguation in the
     * observables used when creating dataflows. It does not correspond to {@link #getName()}, which
     * is meant for human consumption.
     * 
     * @return the reference name of this observable
     */
    String getReferenceName();

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
