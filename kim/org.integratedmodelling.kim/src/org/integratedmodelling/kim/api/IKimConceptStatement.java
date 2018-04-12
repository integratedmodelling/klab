package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.List;
import org.eclipse.xtext.util.Pair;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConcept.Visitor;
import org.integratedmodelling.kim.model.KimConcept;
import org.integratedmodelling.kim.model.KimConceptStatement.ApplicableConcept;
import org.integratedmodelling.kim.model.KimConceptStatement.ParentConcept;
import org.integratedmodelling.kim.model.KimRestriction;

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

    List<KimConcept> getQualitiesAffected();

    List<ApplicableConcept> getSubjectsLinked();

    List<KimConcept> getCountablesCreated();

    List<KimConcept> getConstituentParticipants();

    List<KimConcept> getPartParticipants();

    List<KimConcept> getTraitsConferred();

    List<KimConcept> getTraitsInherited();

    List<KimConcept> getRequiredExtents();

    List<KimConcept> getRequiredRealms();

    List<KimConcept> getRequiredAttributes();

    List<KimConcept> getRequiredIdentities();

    List<KimConcept> getExposedTraits();

    List<KimRestriction> getRestrictions();

    List<ParentConcept> getParents();

    boolean isAlias();

    boolean isAbstract();

    String getNamespace();

    String getName();

    boolean isMacro();

    List<Pair<KimConcept, DescriptionType>> getObservablesDescribed();

    List<ApplicableConcept> getAppliesTo();

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
    List<KimConcept> getConfigurationParticipants();

}
