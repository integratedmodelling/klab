package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConcept.Visitor;
import org.integratedmodelling.kim.utils.Pair;

public interface IKimConceptStatement extends IKimStatement {

    /**
     * Types of descriptional relationships to other concepts
     * 
     * @author ferdinando.villa
     */
    public static enum DescriptionType {
        DESCRIBES,
        INCREASES_WITH,
        DECREASES_WITH,
        MARKS,
        CLASSIFIES,
        DISCRETIZES
    }

    EnumSet<Type> getType();

    String getUpperConceptDefined();

    /**
     * Authority, not null when this concept adopts a term from it as a trait. When this is not null,
     * {@link #getAuthorityTerm} also returns a non-null authority.
     * 
     * @return the authority ID
     */
    String getAuthority();

    /**
     * Authority term adopted as a trait. When this is not null, {@link #getAuthority} also returns a non-null
     * authority.
     * 
     * @return the authority term
     */
    String getAuthorityTerm();

    String getAuthorityDefined();

    String getAuthorityRequired();

    List<IKimConcept> getQualitiesAffected();

//    List<ApplicableConcept> getSubjectsLinked();

    List<IKimConcept> getCountablesCreated();

    List<IKimConcept> getConstituentParticipants();

    List<IKimConcept> getPartParticipants();

    List<IKimConcept> getTraitsConferred();

    List<IKimConcept> getTraitsInherited();

    List<IKimConcept> getRequiredExtents();

    List<IKimConcept> getRequiredRealms();

    List<IKimConcept> getRequiredAttributes();

    List<IKimConcept> getRequiredIdentities();

    List<IKimConcept> getExposedTraits();

    List<IKimRestriction> getRestrictions();

//    List<ParentConcept> getParents();

    boolean isAlias();

    boolean isAbstract();

    String getNamespace();

    String getName();

    boolean isMacro();

    List<Pair<IKimConcept, DescriptionType>> getObservablesDescribed();

//    List<ApplicableConcept> getAppliesTo();

    /**
     * Visit every declaration in the concept with the passed visitor.
     * 
     * @param visitor
     */
    void visitDeclarations(Visitor visitor);

    List<IKimObservable> getTraitsExposed();

    boolean isDefiningExposedTraits();

    /**
     * Whatever concept this configuration 'consists of'.
     * 
     * @return consists of
     */
    List<IKimConcept> getConfigurationParticipants();

}
