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
package org.integratedmodelling.klab.services.reasoner.owl;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.klab.api.knowledge.IAxiom;

/**
 * Just a holder for axiom information. Because axioms are basically just syntax, we provide an implementation
 * here so you don't need to.
 * 
 * @author Ferd
 * 
 */
public class Axiom implements IAxiom {

    private String   _type;
    private Object[] _args;
    Set<Type> conceptType;
    
    /**
     * Create a class assertion. The option integer can be used to store additional flags for the
     * concept with the ontology, which will be quicker to retrieve than annotation properties, for
     * internal use (e.g. distinguish "inferred" observation concepts from asserted ones).
     * 
     * @param conceptId
     * @param type 
     * @return class assertion
     */
    static public IAxiom ClassAssertion(String conceptId, Set<Type> type) {
        Axiom ret = new Axiom(CLASS_ASSERTION, conceptId);
        ret.conceptType = type;
        return ret;
    }

    static public IAxiom AnnotationAssertion(String targetConcept, String annotationProperty, Object value) {
        return new Axiom(ANNOTATION_ASSERTION, targetConcept, annotationProperty, value);
    }

    static public IAxiom SubClass(String parentClass, String subclass) {
        return new Axiom(SUBCLASS_OF, parentClass, subclass);
    }

    static public IAxiom SubObjectProperty(String parentProperty, String subProperty) {
        return new Axiom(SUB_OBJECT_PROPERTY, parentProperty, subProperty);
    }

    static public IAxiom SubAnnotationProperty(String parentProperty, String subProperty) {
        return new Axiom(SUB_ANNOTATION_PROPERTY, parentProperty, subProperty);
    }
    
    static public IAxiom SubDataProperty(String parentProperty, String subProperty) {
        return new Axiom(SUB_DATA_PROPERTY, parentProperty, subProperty);
    }

    public static IAxiom ObjectPropertyAssertion(String string) {
        return new Axiom(OBJECT_PROPERTY_ASSERTION, string);
    }

    public static IAxiom DataPropertyAssertion(String string) {
        return new Axiom(DATA_PROPERTY_ASSERTION, string);
    }

    public static IAxiom ObjectPropertyRange(String property, String concept) {
        return new Axiom(OBJECT_PROPERTY_RANGE, property, concept);
    }

    public static IAxiom DataPropertyRange(String property, String concept) {
        return new Axiom(DATA_PROPERTY_RANGE, property, concept);
    }

    public static IAxiom ObjectPropertyDomain(String property, String concept) {
        return new Axiom(OBJECT_PROPERTY_DOMAIN, property, concept);
    }

    public static IAxiom DataPropertyDomain(String property, String concept) {
        return new Axiom(DATA_PROPERTY_DOMAIN, property, concept);
    }

    public static IAxiom FunctionalDataProperty(String id) {
        return new Axiom(FUNCTIONAL_DATA_PROPERTY, id);
    }

    public static IAxiom FunctionalObjectProperty(String id) {
        return new Axiom(FUNCTIONAL_OBJECT_PROPERTY, id);
    }

    public static IAxiom AnnotationPropertyAssertion(String id) {
        return new Axiom(ANNOTATION_PROPERTY_ASSERTION, id);
    }

    public static IAxiom DisjointClasses(String[] concepts) {
        return new Axiom(DISJOINT_CLASSES, (Object[]) concepts);
    }

    public static IAxiom SomeValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller) {
        return new Axiom(SOME_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller);
    }

    public static IAxiom AllValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller) {
        return new Axiom(ALL_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller);
    }

    public static IAxiom NoValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller) {
        return new Axiom(NO_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller);
    }

    public static IAxiom AtLeastNValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller, int n) {
        return new Axiom(AT_LEAST_N_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller, n);
    }

    public static IAxiom AtMostNValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller, int n) {
        return new Axiom(AT_MOST_N_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller, n);
    }

    public static IAxiom ExactlyNValuesFrom(String restrictedConcept, String restrictedProperty, String restrictionFiller, int n) {
        return new Axiom(EXACTLY_N_VALUES_FROM_RESTRICTION, restrictedConcept, restrictedProperty, restrictionFiller, n);
    }

    public static IAxiom HasValue(String concept, String dataProperty, Object value) {
        return new Axiom(HAS_VALUE_RESTRICTION, concept, dataProperty, value);
    }

    public static IAxiom EquivalentClasses(String class1, String class2) {
        return new Axiom(EQUIVALENT_CLASSES, class1, class2);
    }

    public Axiom(String type, Object... args) {
        _type = type;
        _args = args;
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof Axiom) {
            return toString().equals(arg0.toString());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public String toString() {
        String ret = "<" + _type;
        for (Object o : _args) {
            ret += "," + o.toString();
        }
        return ret + ">";
    }

    @Override
    public boolean is(String classAssertion) {
        return _type.equals(classAssertion);
    }

    @Override
    public Object getArgument(int index) {
        return _args[index];
    }

    @Override
    public Iterator<Object> iterator() {
        return Arrays.asList(_args).iterator();
    }

    public int size() {
        return _args == null ? 0 : _args.length;
    }

}
