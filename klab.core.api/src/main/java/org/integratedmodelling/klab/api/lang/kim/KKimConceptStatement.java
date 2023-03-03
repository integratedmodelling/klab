package org.integratedmodelling.klab.api.lang.kim;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.knowledge.SemanticType;

public interface KKimConceptStatement extends KKimStatement {

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
    	KKimConcept getOriginalObservable();
    	
    	/**
    	 * Only filled in when the target concept is a relationship.
    	 * 
    	 * @return a concept declaration or null
    	 */
    	KKimConcept getSource();
    	
    	/**
    	 * The concept that constitutes the target of the application. In relationships, the
    	 * target of the relationship. 
    	 *  
    	 * @return a concept declaration or null
    	 */
    	KKimConcept getTarget();
    }
    
    Set<SemanticType> getType();

    String getUpperConceptDefined();

    String getAuthorityDefined();

    String getAuthorityRequired();

    List<KKimConcept> getQualitiesAffected();

    List<KKimConcept> getObservablesCreated();
    
    List<KKimConcept> getTraitsConferred();

    List<KKimConcept> getTraitsInherited();

    List<KKimConcept> getRequiredExtents();

    List<KKimConcept> getRequiredRealms();

    List<KKimConcept> getRequiredAttributes();

    List<KKimConcept> getRequiredIdentities();

    List<KKimConcept> getEmergenceTriggers();

//    List<IKimConcept> getExposedTraits();

    List<KKimRestriction> getRestrictions();

    boolean isAlias();

    boolean isAbstract();

    String getNamespace();

    String getName();

    boolean isMacro();

    List<Pair<KKimConcept, DescriptionType>> getObservablesDescribed();

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
