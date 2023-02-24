/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.services.reasoner.api;

// TODO: Auto-generated Javadoc
/**
 * Used to programmatically create knowledge when it's more convenient to accumulate
 * axioms than it is to explicity create and restrict concepts and properties. Axioms must
 * be comparable and hashable (so they can be put in sets with no duplications) and
 * ontologies must be able to define themselves completely from and as a set of axioms.
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface IAxiom extends Iterable<Object> {

    /** The Constant EQUIVALENT_CLASSES. */
    /*
     * class axioms
     */
    public static final String EQUIVALENT_CLASSES                 = "EquivalentClasses";
    
    /** The Constant SUBCLASS_OF. */
    public static final String SUBCLASS_OF                        = "SubClassOf";
    
    /** The Constant DISJOINT_CLASSES. */
    public static final String DISJOINT_CLASSES                   = "DisjointClasses";
    
    /** The Constant DISJOINT_UNION. */
    public static final String DISJOINT_UNION                     = "DisjointUnion";

    /** The Constant CLASS_ASSERTION. */
    /*
     * individual axioms
     */
    public static final String CLASS_ASSERTION                    = "ClassAssertion";
    
    /** The Constant SAME_INDIVIDUAL. */
    public static final String SAME_INDIVIDUAL                    = "SameIndividual";
    
    /** The Constant DIFFERENT_INDIVIDUALS. */
    public static final String DIFFERENT_INDIVIDUALS              = "DifferentIndividuals";
    
    /** The Constant OBJECT_PROPERTY_ASSERTION. */
    public static final String OBJECT_PROPERTY_ASSERTION          = "ObjectPropertyAssertion";
    
    /** The Constant NEGATIVE_OBJECT_PROPERTY_ASSERTION. */
    public static final String NEGATIVE_OBJECT_PROPERTY_ASSERTION = "NegativeObjectPropertyAssertion";
    
    /** The Constant DATA_PROPERTY_ASSERTION. */
    public static final String DATA_PROPERTY_ASSERTION            = "DataPropertyAssertion";
    
    /** The Constant NEGATIVE_DATA_PROPERTY_ASSERTION. */
    public static final String NEGATIVE_DATA_PROPERTY_ASSERTION   = "NegativeDataPropertyAssertion";

    /** The Constant EQUIVALENT_OBJECT_PROPERTIES. */
    /*
     * object property axioms
     */
    public static final String EQUIVALENT_OBJECT_PROPERTIES       = "EquivalentObjectProperties";
    
    /** The Constant SUB_OBJECT_PROPERTY. */
    public static final String SUB_OBJECT_PROPERTY                = "SubObjectPropertyOf";
    
    /** The Constant INVERSE_OBJECT_PROPERTIES. */
    public static final String INVERSE_OBJECT_PROPERTIES          = "InverseObjectProperties";
    
    /** The Constant FUNCTIONAL_OBJECT_PROPERTY. */
    public static final String FUNCTIONAL_OBJECT_PROPERTY         = "FunctionalObjectProperty";
    
    /** The Constant INVERSE_FUNCTIONAL_OBJECT_PROPERTY. */
    public static final String INVERSE_FUNCTIONAL_OBJECT_PROPERTY = "InverseFunctionalObjectProperty";
    
    /** The Constant SYMMETRIC_OBJECT_PROPERTY. */
    public static final String SYMMETRIC_OBJECT_PROPERTY          = "SymmetricObjectProperty";
    
    /** The Constant ASYMMETRIC_OBJECT_PROPERTY. */
    public static final String ASYMMETRIC_OBJECT_PROPERTY         = "AsymmetricObjectProperty";
    
    /** The Constant TRANSITIVE_OBJECT_PROPERTY. */
    public static final String TRANSITIVE_OBJECT_PROPERTY         = "TransitiveObjectProperty";
    
    /** The Constant REFLEXIVE_OBJECT_PROPERTY. */
    public static final String REFLEXIVE_OBJECT_PROPERTY          = "ReflexiveObjectProperty";
    
    /** The Constant IRREFLEXIVE_OBJECT_PROPERTY. */
    public static final String IRREFLEXIVE_OBJECT_PROPERTY        = "IrrefexiveObjectProperty";
    
    /** The Constant OBJECT_PROPERTY_DOMAIN. */
    public static final String OBJECT_PROPERTY_DOMAIN             = "ObjectPropertyDomain";
    
    /** The Constant OBJECT_PROPERTY_RANGE. */
    public static final String OBJECT_PROPERTY_RANGE              = "ObjectPropertyRange";
    
    /** The Constant DISJOINT_OBJECT_PROPERTIES. */
    public static final String DISJOINT_OBJECT_PROPERTIES         = "DisjointObjectProperties";
    
    /** The Constant SUB_PROPERTY_CHAIN_OF. */
    public static final String SUB_PROPERTY_CHAIN_OF              = "SubPropertyChainOf";

    /** The Constant EQUIVALENT_DATA_PROPERTIES. */
    /*
     * data property axioms
     */
    public static final String EQUIVALENT_DATA_PROPERTIES         = "EquivalentDataProperties";
    
    /** The Constant SUB_DATA_PROPERTY. */
    public static final String SUB_DATA_PROPERTY                  = "SubDataPropertyOf";
    
    /** The Constant SUB_ANNOTATION_PROPERTY. */
    public static final String SUB_ANNOTATION_PROPERTY            = "SubAnnotationPropertyOf";
    
    /** The Constant FUNCTIONAL_DATA_PROPERTY. */
    public static final String FUNCTIONAL_DATA_PROPERTY           = "FunctionalDataProperty";
    
    /** The Constant DATA_PROPERTY_DOMAIN. */
    public static final String DATA_PROPERTY_DOMAIN               = "DataPropertyDomain";
    
    /** The Constant DATA_PROPERTY_RANGE. */
    public static final String DATA_PROPERTY_RANGE                = "DataPropertyRange";
    
    /** The Constant DISJOINT_DATA_PROPERTIES. */
    public static final String DISJOINT_DATA_PROPERTIES           = "DisjointDataProperties";
    
    /** The Constant HAS_KEY. */
    public static final String HAS_KEY                            = "HasKey";
    
    /** The Constant SWRL_RULE. */
    public static final String SWRL_RULE                          = "Rule";

    /** The Constant NO_VALUES_FROM_RESTRICTION. */
    /*
     * restriction "axioms". These are not atomic in DL and actually are more like
     * ontology "actions", but we represent them as single axioms for ease of use - the
     * ontology implementation is able to translate them into the non-atomic actual OWL
     * axioms.
     */
    public static final String NO_VALUES_FROM_RESTRICTION         = "NoValuesFrom";
    
    /** The Constant ALL_VALUES_FROM_RESTRICTION. */
    public static final String ALL_VALUES_FROM_RESTRICTION        = "AllValuesFrom";
    
    /** The Constant SOME_VALUES_FROM_RESTRICTION. */
    public static final String SOME_VALUES_FROM_RESTRICTION       = "SomeValuesFrom";
    
    /** The Constant EXACTLY_N_VALUES_FROM_RESTRICTION. */
    public static final String EXACTLY_N_VALUES_FROM_RESTRICTION  = "ExactlyNValuesFrom";
    
    /** The Constant AT_LEAST_N_VALUES_FROM_RESTRICTION. */
    public static final String AT_LEAST_N_VALUES_FROM_RESTRICTION = "AtLeastNaluesFrom";
    
    /** The Constant AT_MOST_N_VALUES_FROM_RESTRICTION. */
    public static final String AT_MOST_N_VALUES_FROM_RESTRICTION  = "AtMostNValuesFrom";
    
    /** The Constant HAS_VALUE_RESTRICTION. */
    public static final String HAS_VALUE_RESTRICTION              = "HasValue";
    
    /** The Constant HAS_MAX_CARDINALITY_RESTRICTION. */
    public static final String HAS_MAX_CARDINALITY_RESTRICTION    = "HasMaxCardinality";
    
    /** The Constant HAS_MIN_CARDINALITY_RESTRICTION. */
    public static final String HAS_MIN_CARDINALITY_RESTRICTION    = "HasMinCardinality";
    
    /** The Constant HAS_EXACT_CARDINALITY_RESTRICTION. */
    public static final String HAS_EXACT_CARDINALITY_RESTRICTION  = "HasExactCardinality";

    /** The Constant ANNOTATION_ASSERTION. */
    /*
     * annotation property axioms
     */
    public static final String ANNOTATION_ASSERTION               = "AnnotationAssertion";
    
    /** The Constant SUB_ANNOTATION_PROPERTY_OF. */
    public static final String SUB_ANNOTATION_PROPERTY_OF         = "SubAnnotationPropertyOf";
    
    /** The Constant ANNOTATION_PROPERTY_ASSERTION. */
    public static final String ANNOTATION_PROPERTY_ASSERTION      = "AnnotationPropertyAssertion";
    
    /** The Constant ANNOTATION_PROPERTY_RANGE. */
    public static final String ANNOTATION_PROPERTY_RANGE          = "AnnotationPropertyRangeOf";
    
    /** The Constant ANNOTATION_PROPERTY_DOMAIN. */
    public static final String ANNOTATION_PROPERTY_DOMAIN         = "AnnotationPropertyDomain";
    
    /** The Constant DATATYPE_DEFINITION. */
    public static final String DATATYPE_DEFINITION                = "DatatypeDefinition";

    /**
     * Just check if we're this type
     *
     * @param classAssertion the class assertion
     * @return true if the assertion is this type of axiom
     */
    public abstract boolean is(String classAssertion);

    /**
     * Get i-th argument.
     *
     * @param index the index
     * @return i-th argument
     */
    public abstract Object getArgument(int index);

}
