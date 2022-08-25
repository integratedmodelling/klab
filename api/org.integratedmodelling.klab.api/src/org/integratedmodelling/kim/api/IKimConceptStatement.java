package org.integratedmodelling.kim.api;

import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.utils.Pair;

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

    /**
     * Anything that "applies to" (including subject linked by relationships) gets
     * this descriptor. If the application is defined for a role, the original
     * observable is also indicated.
     * 
     * @author ferdinando.villa
     *
     */
    public interface ApplicableConcept {
    	
    	/**
    	 * If the application is through a role, the original observable that
    	 * is expected to incarnate it. Otherwise null.
    	 *
    	 * @return a concept declaration or null
    	 */
    	IKimConcept getOriginalObservable();
    	
    	/**
    	 * Only filled in when the target concept is a relationship.
    	 * 
    	 * @return a concept declaration or null
    	 */
    	IKimConcept getSource();
    	
    	/**
    	 * The concept that constitutes the target of the application. In relationships, the
    	 * target of the relationship. 
    	 *  
    	 * @return a concept declaration or null
    	 */
    	IKimConcept getTarget();
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

    List<IKimConcept> getObservablesCreated();

//    List<IKimConcept> getConstituentParticipants();
//
//    List<IKimConcept> getPartParticipants();

    List<IKimConcept> getTraitsConferred();

    List<IKimConcept> getTraitsInherited();

    List<IKimConcept> getRequiredExtents();

    List<IKimConcept> getRequiredRealms();

    List<IKimConcept> getRequiredAttributes();

    List<IKimConcept> getRequiredIdentities();

    List<IKimConcept> getEmergenceTriggers();

//    List<IKimConcept> getExposedTraits();

    List<IKimRestriction> getRestrictions();

    boolean isAlias();

    boolean isAbstract();

    String getNamespace();

    String getName();

    boolean isMacro();

    List<Pair<IKimConcept, DescriptionType>> getObservablesDescribed();

//    List<IKimObservable> getTraitsExposed();
//
//    boolean isDefiningExposedTraits();

//    /**
//     * Whatever concept this configuration 'consists of'.
//     * 
//     * @return consists of
//     */
//    List<IKimConcept> getConfigurationParticipants();

	List<ApplicableConcept> getSubjectsLinked();

	List<ApplicableConcept> getAppliesTo();

	String getDocstring();

}
