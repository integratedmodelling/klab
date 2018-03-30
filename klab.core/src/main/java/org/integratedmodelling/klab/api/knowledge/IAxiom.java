/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.knowledge;

/**
 * Used to programmatically create knowledge when it's more convenient to accumulate
 * axioms than it is to explicity create and restrict concepts and properties. Axioms must
 * be comparable and hashable (so they can be put in sets with no duplications) and
 * ontologies must be able to define themselves completely from and as a set of axioms.
 * 
 * @author Ferd
 *
 */
public interface IAxiom extends Iterable<Object> {

    /*
     * class axioms
     */
    public static final String EQUIVALENT_CLASSES                 = "EquivalentClasses";
    public static final String SUBCLASS_OF                        = "SubClassOf";
    public static final String DISJOINT_CLASSES                   = "DisjointClasses";
    public static final String DISJOINT_UNION                     = "DisjointUnion";

    /*
     * individual axioms
     */
    public static final String CLASS_ASSERTION                    = "ClassAssertion";
    public static final String SAME_INDIVIDUAL                    = "SameIndividual";
    public static final String DIFFERENT_INDIVIDUALS              = "DifferentIndividuals";
    public static final String OBJECT_PROPERTY_ASSERTION          = "ObjectPropertyAssertion";
    public static final String NEGATIVE_OBJECT_PROPERTY_ASSERTION = "NegativeObjectPropertyAssertion";
    public static final String DATA_PROPERTY_ASSERTION            = "DataPropertyAssertion";
    public static final String NEGATIVE_DATA_PROPERTY_ASSERTION   = "NegativeDataPropertyAssertion";

    /*
     * object property axioms
     */
    public static final String EQUIVALENT_OBJECT_PROPERTIES       = "EquivalentObjectProperties";
    public static final String SUB_OBJECT_PROPERTY                = "SubObjectPropertyOf";
    public static final String INVERSE_OBJECT_PROPERTIES          = "InverseObjectProperties";
    public static final String FUNCTIONAL_OBJECT_PROPERTY         = "FunctionalObjectProperty";
    public static final String INVERSE_FUNCTIONAL_OBJECT_PROPERTY = "InverseFunctionalObjectProperty";
    public static final String SYMMETRIC_OBJECT_PROPERTY          = "SymmetricObjectProperty";
    public static final String ASYMMETRIC_OBJECT_PROPERTY         = "AsymmetricObjectProperty";
    public static final String TRANSITIVE_OBJECT_PROPERTY         = "TransitiveObjectProperty";
    public static final String REFLEXIVE_OBJECT_PROPERTY          = "ReflexiveObjectProperty";
    public static final String IRREFLEXIVE_OBJECT_PROPERTY        = "IrrefexiveObjectProperty";
    public static final String OBJECT_PROPERTY_DOMAIN             = "ObjectPropertyDomain";
    public static final String OBJECT_PROPERTY_RANGE              = "ObjectPropertyRange";
    public static final String DISJOINT_OBJECT_PROPERTIES         = "DisjointObjectProperties";
    public static final String SUB_PROPERTY_CHAIN_OF              = "SubPropertyChainOf";

    /*
     * data property axioms
     */
    public static final String EQUIVALENT_DATA_PROPERTIES         = "EquivalentDataProperties";
    public static final String SUB_DATA_PROPERTY                  = "SubDataPropertyOf";
    public static final String SUB_ANNOTATION_PROPERTY            = "SubAnnotationPropertyOf";
    public static final String FUNCTIONAL_DATA_PROPERTY           = "FunctionalDataProperty";
    public static final String DATA_PROPERTY_DOMAIN               = "DataPropertyDomain";
    public static final String DATA_PROPERTY_RANGE                = "DataPropertyRange";
    public static final String DISJOINT_DATA_PROPERTIES           = "DisjointDataProperties";
    public static final String HAS_KEY                            = "HasKey";
    public static final String SWRL_RULE                          = "Rule";

    /*
     * restriction "axioms". These are not atomic in DL and actually are more like
     * ontology "actions", but we represent them as single axioms for ease of use - the
     * ontology implementation is able to translate them into the non-atomic actual OWL
     * axioms.
     */
    public static final String NO_VALUES_FROM_RESTRICTION         = "NoValuesFrom";
    public static final String ALL_VALUES_FROM_RESTRICTION        = "AllValuesFrom";
    public static final String SOME_VALUES_FROM_RESTRICTION       = "SomeValuesFrom";
    public static final String EXACTLY_N_VALUES_FROM_RESTRICTION  = "ExactlyNValuesFrom";
    public static final String AT_LEAST_N_VALUES_FROM_RESTRICTION = "AtLeastNaluesFrom";
    public static final String AT_MOST_N_VALUES_FROM_RESTRICTION  = "AtMostNValuesFrom";
    public static final String HAS_VALUE_RESTRICTION              = "HasValue";
    public static final String HAS_MAX_CARDINALITY_RESTRICTION    = "HasMaxCardinality";
    public static final String HAS_MIN_CARDINALITY_RESTRICTION    = "HasMinCardinality";
    public static final String HAS_EXACT_CARDINALITY_RESTRICTION  = "HasExactCardinality";

    /*
     * annotation property axioms
     */
    public static final String ANNOTATION_ASSERTION               = "AnnotationAssertion";
    public static final String SUB_ANNOTATION_PROPERTY_OF         = "SubAnnotationPropertyOf";
    public static final String ANNOTATION_PROPERTY_ASSERTION      = "AnnotationPropertyAssertion";
    public static final String ANNOTATION_PROPERTY_RANGE          = "AnnotationPropertyRangeOf";
    public static final String ANNOTATION_PROPERTY_DOMAIN         = "AnnotationPropertyDomain";
    public static final String DATATYPE_DEFINITION                = "DatatypeDefinition";

    /*
     * just check if we're this type
     */
    /**
     * @param classAssertion
     * @return true if the assertion is this type of axiom
     */
    public abstract boolean is(String classAssertion);

    /**
     * Get i-th argument
     * @param index
     * @return i-th argument
     */
    public abstract Object getArgument(int index);

}
