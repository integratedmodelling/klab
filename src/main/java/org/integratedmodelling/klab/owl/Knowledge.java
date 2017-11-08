/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
 * other authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable
 * modular, collaborative, integrated development of interoperable data and model
 * components. For details, see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms
 * of the Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for a
 * particular purpose. See the Affero General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this
 * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
 * 330, Boston, MA 02111-1307, USA. The license is also available at:
 * https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.utils.Pair;

/**
 * The only functions this provides are the parsing ones, which separate out traits and
 * concepts/properties so that we avoid problems from concepts being legitimate but not
 * having been seen at one side of two communicating endpoints that adopt the same
 * ontologies. When this situation can happen, send ((IParseable)k).asText() and receive k
 * = Knowledge.parse(string).
 * 
 * This will allow to send, say, im.geography:AverageMinimumTemperature as
 * 
 * im.geography:AverageMinimumTemperature=im.geography:Temperature+im.Average+im:Minimum
 * 
 * and have it reassembled from the traits as im.geography:AverageMinimumTemperature if
 * the latter has not been seen and created already.
 * 
 * TODO add methods to browse the asserted hierarchy and specialize them in derived
 * classes.
 * 
 * @author ferdinando.villa
 *
 */
public abstract class Knowledge implements IKnowledge {

    // used by NS for quick check for common inferences to speed up lots of operations.
    // we just expose these, leaving them for external code to use as necessary.
    public int flags         = 0;
    public int flagscomputed = 0;

    /**
     * Create a concept with parents, restrictions and annotations as specified by the
     * passed string. Used by remote engines to build up ontologies on the receiving end.
     * 
     * String format: Existing concept with namespace is parent - only one allowed ID
     * without namespace is concept ID - if existing, everything is abandoned and concept
     * is returned Token with spaces is restriction, in format TYPE property concept.TYPE
     * is restriction type: A (all values from) S (some values from) ... rest to come.
     * 
     * Basic and incomplete; no error checking is done for now; use carefully.
     * 
     * @param specs
     * @return
     */
    public static IConcept createConcept(String specs, IOntology ontology) {
        return (IConcept) create(specs, ontology, true);
    }

    /**
     * Create a concept with parents, restrictions and annotations as specified by the
     * passed string. Used by remote engines to build up ontologies on the receiving end.
     * String can contain parent (fully specified) and property ID. Only object properties
     * are created for now.
     *
     * Basic and incomplete; no error checking is done for now; use carefully.
     * 
     * @param specs
     * @return
     */
    public static IProperty createProperty(String specs, IOntology ontology) {
        return (IProperty) create(specs, ontology, false);
    }

    /**
     * String made up by comma-separated tokens Existing concept with namespace is parent
     * - only one allowed ID without namespace is concept ID - if existing, everything is
     * abandoned and concept is returned Token with spaces is restriction, in format TYPE
     * property concept.TYPE is restriction type: A (all values from) S (some values
     * from). Annotation property values can be specified by a <property>=<value> token.
     * 
     * @param specs
     * @param ontology
     * @param isConcept
     * @return
     */
    private static IKnowledge create(String specs, IOntology ontology, boolean isConcept) {

        IKnowledge ret = null;

        String conceptId = null;
        String parentId = null;
        List<Pair<String, String>> restrictions = new ArrayList<>();

        for (String token : specs.split(",")) {

            if (token.contains("=")) {
                // ACHTUNG! Order important! Property statement may contain : and match
                // the next if.
                String[] ps = token.split("=");
                if (ps.length == 2 && !ps[1].equals("null")) {
                    restrictions.add(new Pair<>(ps[0], ps[1]));
                }
            } else if (token.contains(":")) {
                parentId = token;
            } else {
                /*
                 * attributed to concept, but if two are present, the parent is set from
                 * the first.
                 */
                if (conceptId != null) {
                    parentId = conceptId;
                }
                conceptId = token;
            }
        }

        if (conceptId != null) {

            List<IAxiom> axioms = new ArrayList<>();

            if (isConcept) {

                ret = ontology.getConcept(conceptId);
                if (ret == null) {

                    axioms.add(Axiom.ClassAssertion(conceptId));
                    if (parentId != null && !parentId.contains(":")) {
                        if (ontology.getConcept(parentId) == null) {
                            axioms.add(Axiom.ClassAssertion(parentId));
                        }
                    }
                    if (parentId != null) {
                        axioms.add(Axiom.SubClass(parentId, conceptId));
                    }

                    for (Pair<String, String> a : restrictions) {

                        IProperty property = null;
                        String propId = a.getFirst();
                        if (!propId.contains(":")) {
                            property = ontology.getProperty(propId);
                        } else {
                            property = Concepts.INSTANCE.getProperty(propId);
                        }

                        if (property != null && property.isObjectProperty()) {
                            axioms.add(Axiom.SomeValuesFrom(conceptId, a.getFirst(), a.getSecond()));
                        } else {
                            axioms.add(Axiom.AnnotationAssertion(conceptId, a.getFirst(), a.getSecond()));
                        }
                    }
                }

            } else {

                boolean isAnnotation = false;
                if (conceptId.startsWith("@")) {
                    isAnnotation = true;
                    conceptId = conceptId.substring(1);
                }
                if (parentId != null && parentId.startsWith("@")) {
                    isAnnotation = true;
                    parentId = parentId.substring(1);
                }

                ret = ontology.getProperty(conceptId);
                if (ret == null) {
                    axioms.add(isAnnotation ? Axiom.AnnotationPropertyAssertion(conceptId)
                            : Axiom.ObjectPropertyAssertion(conceptId));
                    if (parentId != null && !parentId.contains(":")) {
                        if (ontology.getProperty(parentId) == null) {
                            axioms.add(isAnnotation ? Axiom.AnnotationPropertyAssertion(conceptId)
                                    : Axiom.ObjectPropertyAssertion(conceptId));
                        }
                    }
                    if (parentId != null) {
                        axioms.add(isAnnotation ? Axiom.SubAnnotationProperty(parentId, conceptId)
                                : Axiom.SubObjectProperty(parentId, conceptId));
                    }
                }
            }

            if (axioms.size() > 0) {
                ontology.define(axioms);
                ret = isConcept ? ontology.getConcept(conceptId) : ontology.getProperty(conceptId);
            }

        }

        return ret;

    }

//    @Override
    public String asText() {
        return this instanceof IConcept ? ((IConcept) this).getDefinition() : this.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Knowledge ? toString().equals(obj.toString()) : false;
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public IConcept getDomain() {
        INamespace ns = Namespaces.INSTANCE.getNamespace(getConceptSpace());
        return ns == null ? null : ns.getDomain();
    }

}
