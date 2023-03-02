/*******************************************************************************
 *  Copyright (C) 2007, 2014:
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

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLNaryBooleanClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChangeException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;
import org.semanticweb.owlapi.model.UnknownOWLOntologyException;
import org.semanticweb.owlapi.reasoner.InconsistentOntologyException;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.ReasonerInterruptedException;
import org.semanticweb.owlapi.reasoner.TimeOutException;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

public class Ontologies {

    private File file = new File("C:/MechanicalEngineering/MechanicalEngineeringOntology.owl");

    private OWLOntologyManager manager;
    private OWLOntology        ontology;
    final String               tabString = "    ";
//    OWLReasonerFactory         reasonerFactory;
    OWLReasoner                reasoner;
    private OWLDataFactory     factory;

    public Ontologies() {

    }

    public void openOntology(boolean invokeReasoner) {

        try {
            // Create our ontology manager in the usual way.
            manager = OWLManager.createOWLOntologyManager();
            ontology = manager.loadOntologyFromOntologyDocument(file);
            factory = manager.getOWLDataFactory();

            if (invokeReasoner) {
                // reasonerFactory = new PelletReasonerFactory();
                //
                // try {
                // reasoner = reasonerFactory.createNonBufferingReasoner(ontology);
                // reasoner.precomputeInferences(InferenceType.CLASS_HIERARCHY);
                // boolean consistent = reasoner.isConsistent();
                // System.out.println("Consistent: " + consistent);
                // System.out.println("\n");
                // } catch (Exception e) {
                // // TODO Auto-generated catch block
                // e.printStackTrace();
                // }
            }
        } catch (TimeOutException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InconsistentOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ReasonerInterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyCreationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public void closeOntology() {
        manager.removeOntology(ontology);
    }

    public String[] getTrimmedList(String[] toBeTrimmedList) {
        String[] trimmedList = new String[toBeTrimmedList.length];
        for (int i = 0; i < toBeTrimmedList.length; i++) {
            trimmedList[i] = toBeTrimmedList[i].trim();
        }
        return trimmedList;

    }

    public ArrayList<String> getTrimmedList(ArrayList<String> toBeTrimmedList) {
        ArrayList<String> trimmedList = new ArrayList<String>();
        for (String str : toBeTrimmedList) {
            trimmedList.add(str.trim());
        }
        return trimmedList;

    }

    public ArrayList<String> getAssertedSubclassNamesAll(String rootClassName, boolean includeRootClassName) {
        openOntology(false);

        ArrayList<String> subClassNames = new ArrayList<String>();
        if (includeRootClassName) {
            subClassNames.add(rootClassName);
            subClassNames.addAll(getTabbedAssertedClassList(rootClassName, 1));
        } else {
            subClassNames.addAll(getTabbedAssertedClassList(rootClassName, 0));
        }

        closeOntology();

        return subClassNames;

    }

    /*
     * public ArrayList<String> getDataPropertyList(String individualName) {
     * ArrayList<String> dataPropertyList = new ArrayList<String>();
     * 
     * openOntology(false);
     * 
     * URI individualURI = URI.create(ontology.getOntologyID() + "#" + individualName);
     * OWLNamedIndividual individual = factory.getOWLNamedIndividual (individualURI);
     * Set<OWLClassExpression> individualTypes = individual.getTypes(ontology);
     * 
     * for(OWLClassExpression desc: individualTypes) { if(desc.isOWLThing()) { continue; }
     * 
     * Set<OWLDataProperty> dataProperties = desc.getDataPropertiesInSignature();
     * 
     * if (!dataProperties.isEmpty()) { for(OWLDataProperty dp: dataProperties) {
     * dataPropertyList.add(dp.toString()); } } }
     * 
     * closeOntology();
     * 
     * return dataPropertyList;
     * 
     * }
     */
    public boolean processDataProperties(String individualName, Set<String[]> dataRestrictionCouplesForSelectedIndividual, Set<String[]> objectRestrictionCouplesForSelectedIndividual) {
        boolean bResult = false;

        openOntology(false);

        IRI individualIRI = IRI.create(ontology.getOntologyID() + "#" + individualName);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(individualIRI);
        Set<OWLClassExpression> individualTypes = individual.getTypes(ontology);

        for (OWLClassExpression des : individualTypes) {
            // if the type is THING, skip it as any individual is a thing.
            if (des.isOWLThing()) {
                continue;
            }

            Set<OWLSubClassOfAxiom> subClassAxiom = ontology.getSubClassAxiomsForSubClass(des.asOWLClass());

            RestrictionVisitor restrictionVisitor = new RestrictionVisitor(Collections.singleton(ontology));
            for (OWLSubClassOfAxiom ax : subClassAxiom) {
                OWLClassExpression superCls = ax.getSuperClass();
                if (superCls.isAnonymous()) {
                    try {
                        Set<OWLClassExpression> dess = ((OWLNaryBooleanClassExpression) superCls)
                                .getOperands();
                        for (OWLClassExpression annonymousDes : dess) {
                            // OWLClassExpression filler =
                            // ((OWLQuantifiedRestriction<OWLObjectPropertyExpression,
                            // OWLClassExpression>) annonymousDes).getFiller();
                            // filler.isAnonymous();
                            annonymousDes.accept(restrictionVisitor);
                        }
                    } catch (Exception ex) {
                        superCls.accept(restrictionVisitor);
                    }

                } else {
                    superCls.accept(restrictionVisitor);
                }
            }

            dataRestrictionCouplesForSelectedIndividual
                    .addAll(restrictionVisitor.getDataRestrictionCouples());
            objectRestrictionCouplesForSelectedIndividual.addAll(restrictionVisitor
                    .getObjectRestrictionCouples());
            // dataRestrictionsForSelectedIndividual.addAll(restrictionVisitor.getDataRestrictions());
        }

        if (dataRestrictionCouplesForSelectedIndividual.isEmpty()) {
            bResult = false;
        } else {
            bResult = true;
        }
        closeOntology();

        return bResult;
    }

    public boolean createIndividual(String individualTypeName, String individualName) {
        boolean created = false;

        openOntology(false);

        IRI classURI = IRI.create(ontology.getOntologyID() + "#" + individualTypeName);
        OWLClass individualType = factory.getOWLClass(classURI);

        IRI individualURI = IRI.create(ontology.getOntologyID() + "#" + individualName);
        OWLNamedIndividual individual = factory.getOWLNamedIndividual(individualURI);

        OWLAxiom axiom = factory.getOWLClassAssertionAxiom(individualType, individual);
        AddAxiom addAxiom = new AddAxiom(ontology, axiom);
        // We now use the manager to apply the change
        try {
            manager.applyChange(addAxiom);
            created = true;
            // org.semanticweb.owl.model.OWLOntologyStorer ?
            manager.saveOntology(ontology);

        } catch (OWLOntologyChangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownOWLOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return created;

    }

    public boolean createObjectPropertyTriple(String subjectName, String predicateName, String objectName) {
        boolean created = false;

        openOntology(false);

        // get subject
        IRI subjectURI = IRI.create(ontology.getOntologyID() + "#" + subjectName);
        OWLNamedIndividual subject = factory.getOWLNamedIndividual(subjectURI);

        // get object
        IRI objectURI = IRI.create(ontology.getOntologyID() + "#" + objectName);
        OWLNamedIndividual object = factory.getOWLNamedIndividual(objectURI);

        // get predicate
        IRI predicateURI = IRI.create(ontology.getOntologyID() + "#" + predicateName);
        OWLObjectProperty predicate = factory.getOWLObjectProperty(predicateURI);

        // Now create the actual assertion (triple), as an object property
        // assertion axiom
        // matthew --> hasFather --> peter
        OWLObjectPropertyAssertionAxiom assertion = factory
                .getOWLObjectPropertyAssertionAxiom(predicate, subject, object);

        AddAxiom addAxiom = new AddAxiom(ontology, assertion);
        // We now use the manager to apply the change
        try {
            manager.applyChange(addAxiom);
            created = true;
            // org.semanticweb.owl.model.OWLOntologyStorer ?
            manager.saveOntology(ontology);

        } catch (OWLOntologyChangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownOWLOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return created;

    }

    public boolean createDataPropertyTriple(String subjectName, String predicateName, String strValue) {
        boolean created = false;

        openOntology(false);

        // get subject
        IRI subjectURI = IRI.create(ontology.getOntologyID() + "#" + subjectName);
        OWLNamedIndividual subject = factory.getOWLNamedIndividual(subjectURI);

        // get data
        // Create the value "this is a string", which is a type of string.
        OWLLiteral stringConstant = factory.getOWLLiteral(strValue);

        // get predicate
        IRI predicateURI = IRI.create(ontology.getOntologyID() + "#" + predicateName);
        OWLDataProperty predicate = factory.getOWLDataProperty(predicateURI);

        // Now create the actual assertion (triple), as an object property
        // assertion axiom
        // weight --> hasWeightValue --> 0.10
        OWLDataPropertyAssertionAxiom assertion = factory
                .getOWLDataPropertyAssertionAxiom(predicate, subject, stringConstant);

        AddAxiom addAxiom = new AddAxiom(ontology, assertion);
        // We now use the manager to apply the change
        try {
            manager.applyChange(addAxiom);
            created = true;
            // org.semanticweb.owl.model.OWLOntologyStorer ?
            manager.saveOntology(ontology);

        } catch (OWLOntologyChangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownOWLOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return created;

    }

    public boolean createDataPropertyTriple(String subjectName, String predicateName, int intValue) {
        boolean created = false;

        openOntology(false);

        // get subject
        IRI subjectURI = IRI.create(ontology.getOntologyID() + "#" + subjectName);
        OWLNamedIndividual subject = factory.getOWLNamedIndividual(subjectURI);

        // get data
        // Create the value "100", which is a type of int.
        OWLLiteral intConstant = factory.getOWLLiteral(intValue);

        // get predicate
        IRI predicateURI = IRI.create(ontology.getOntologyID() + "#" + predicateName);
        OWLDataProperty predicate = factory.getOWLDataProperty(predicateURI);

        // Now create the actual assertion (triple), as an object property
        // assertion axiom
        // weight --> hasWeightValue --> 0.10
        OWLDataPropertyAssertionAxiom assertion = factory
                .getOWLDataPropertyAssertionAxiom(predicate, subject, intConstant);

        AddAxiom addAxiom = new AddAxiom(ontology, assertion);
        // We now use the manager to apply the change
        try {
            manager.applyChange(addAxiom);
            created = true;
            // org.semanticweb.owl.model.OWLOntologyStorer ?
            manager.saveOntology(ontology);

        } catch (OWLOntologyChangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownOWLOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return created;

    }

    public boolean createDataPropertyTriple(String subjectName, String predicateName, double doubleValue) {
        boolean created = false;

        openOntology(false);

        // get subject
        IRI subjectURI = IRI.create(ontology.getOntologyID() + "#" + subjectName);
        OWLNamedIndividual subject = factory.getOWLNamedIndividual(subjectURI);

        // get data
        // Create the value "3.141592657989", which is a type of double.
        OWLLiteral doubleConstant = factory.getOWLLiteral(doubleValue);

        // get predicate
        IRI predicateURI = IRI.create(ontology.getOntologyID() + "#" + predicateName);
        OWLDataProperty predicate = factory.getOWLDataProperty(predicateURI);

        // Now create the actual assertion (triple), as an object property
        // assertion axiom
        // weight --> hasWeightValue --> 0.10
        OWLDataPropertyAssertionAxiom assertion = factory
                .getOWLDataPropertyAssertionAxiom(predicate, subject, doubleConstant);

        AddAxiom addAxiom = new AddAxiom(ontology, assertion);
        // We now use the manager to apply the change
        try {
            manager.applyChange(addAxiom);
            created = true;
            // org.semanticweb.owl.model.OWLOntologyStorer ?
            manager.saveOntology(ontology);

        } catch (OWLOntologyChangeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (OWLOntologyStorageException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (UnknownOWLOntologyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return created;

    }

    public ArrayList<String> getAssertedIndividualAll(String belongingClassName) {

        openOntology(false);

        ArrayList<String> individualNames = new ArrayList<String>();

        // get all individuals from all subclasses of the selected class
        ArrayList<String> allSubclassNames = getAssertedSubclassNamesAll(belongingClassName, true);
        allSubclassNames = getTrimmedList(allSubclassNames);

        for (String currentSubclassName : allSubclassNames) {
            IRI currentSubclassURI = IRI.create(ontology.getOntologyID() + "#" + currentSubclassName);
            OWLClass currentSubclass = factory.getOWLClass(currentSubclassURI);

            if (currentSubclass != null) {
                // debug and check what is the difference between individuals in signature
                // and individuals.
                Set<OWLNamedIndividual> namedIndividualSet = currentSubclass.getIndividualsInSignature();
                Set<OWLIndividual> individualSet = currentSubclass.getIndividuals(ontology);
                for (OWLIndividual individual : individualSet) {
                    individualNames.add(individual.toString());
                }
            }
        }

        closeOntology();

        return individualNames;

    }

    /*
     * public ArrayList<String> getRefinedEVList (String targetIndividualName, String[]
     * targetEVNames) { openOntology(true);
     * 
     * ArrayList<String> inferredSubClassNames = new ArrayList<String>();
     * ArrayList<String> refinedEVlist = new ArrayList<String>(); try { URI
     * targetIndividualURI = URI.create(ontology.getOntologyID() + "#" +
     * targetIndividualName); OWLNamedIndividual targetIndividual =
     * manager.getOWLDataFactory().getOWLNamedIndividual (targetIndividualURI);
     * Set<OWLClassExpression> targetTypes = targetIndividual.getTypes(ontology);
     * 
     * // TODO hard coded. need to satisfy the whole list later on. URI targetEVClassURI =
     * URI.create(ontology.getOntologyID() + "#" + targetEVNames[0]); OWLClass
     * targetEVClass = manager.getOWLDataFactory().getOWLClass(targetEVClassURI);
     * 
     * OWLClass targetClassOfIndividual; Set<OWLSubClassOfAxiom > subClassAxiom;
     * 
     * // apply filter to get refined list against the target class descriptions
     * for(OWLClassExpression des: targetTypes) { // if the type is THING, skip it as any
     * individual is a thing. if(des.isOWLThing()) { continue; }
     * 
     * RestrictionVisitor restrictionVisitor = new
     * RestrictionVisitor(Collections.singleton(ontology));
     * 
     * targetClassOfIndividual = des.asOWLClass(); subClassAxiom =
     * ontology.getSubClassAxiomsForSubClass(targetClassOfIndividual);
     * for(OWLSubClassOfAxiom ax : subClassAxiom) { OWLClassExpression superCls =
     * ax.getSuperClass(); if(superCls.isAnonymous()) { Set<OWLClassExpression> dess =
     * ((OWLNaryBooleanClassExpression) superCls).getOperands(); for(OWLClassExpression
     * annonymousDes: dess) { annonymousDes.accept(restrictionVisitor); } } else {
     * superCls.accept(restrictionVisitor); } }
     * 
     * // get full inferred subclass name list
     * inferredSubClassNames.addAll(getTabbedInferredClassList(targetEVClass, 0));
     * ArrayList<String> trimedInferredSubClassNames =
     * getTrimedList(inferredSubClassNames); // Our RestrictionVisitor has now collected
     * all of the properties that have been restricted in existential // mask fillers
     * against inferred subclass names, so that only relevant list populated in //
     * annotation type list for the user to choose from for(OWLPropertyRange filler :
     * restrictionVisitor.getPropertyFillers()) {
     * if(trimedInferredSubClassNames.contains(filler.toString())) {
     * refinedEVlist.add(filler.toString()); } } }
     * 
     * } catch (OWLException e) { // TODO Auto-generated catch block e.printStackTrace();
     * }
     * 
     * closeOntology();
     * 
     * return refinedEVlist;
     * 
     * }
     */

    public ArrayList<String> getRefinedEVList(String targetIndividualName, String targetEVName, Set<String[]> objectCardinalityRestrictionCouples) {
        ArrayList<String> inferredSubClassNames = new ArrayList<String>();
        ArrayList<String> refinedEVlist = new ArrayList<String>();

        openOntology(true);

        try {
            IRI targetIndividualURI = IRI.create(ontology.getOntologyID() + "#" + targetIndividualName);
            OWLNamedIndividual targetIndividual = manager.getOWLDataFactory()
                    .getOWLNamedIndividual(targetIndividualURI);
            Set<OWLClassExpression> targetTypes = targetIndividual.getTypes(ontology);

            // TODO hard coded. need to satisfy the whole list later on.
            IRI targetEVClassURI = IRI.create(ontology.getOntologyID() + "#" + targetEVName);
            OWLClass targetEVClass = factory.getOWLClass(targetEVClassURI);

            OWLClass targetClassOfIndividual;
            Set<OWLSubClassOfAxiom> subClassAxiom;

            for (OWLClassExpression des : targetTypes) {
                // if the type is THING, skip it as any individual is a thing.
                if (des.isOWLThing()) {
                    continue;
                }

                targetClassOfIndividual = des.asOWLClass();

                // apply filter to get refined list against the target class
                // descriptions
                subClassAxiom = ontology.getSubClassAxiomsForSubClass(targetClassOfIndividual);
                // subClassAxiom =
                // ontology.getSubClassAxiomsForRHS(targetClassOfIndividual);
                // Set<OWLOntology> onts = new HashSet<OWLOntology>();
                // onts.add(ontology);
                RestrictionVisitor restrictionVisitor = new RestrictionVisitor(Collections
                        .singleton(ontology));
                for (OWLSubClassOfAxiom ax : subClassAxiom) {
                    OWLClassExpression superCls = ax.getSuperClass();
                    if (superCls.isAnonymous()) {
                        Set<OWLClassExpression> dess = ((OWLNaryBooleanClassExpression) superCls)
                                .getOperands();
                        for (OWLClassExpression anonymousDes : dess) {
                            // OWLClassExpression filler =
                            // ((OWLQuantifiedRestriction<OWLObjectPropertyExpression,
                            // OWLClassExpression>) annonymousDes).getFiller();
                            // filler.isAnonymous();
                            anonymousDes.accept(restrictionVisitor);
                        }
                        // Set<OWLObjectProperty> oProps =
                        // superCls.getObjectPropertiesInSignature();
                        // for(OWLObjectProperty prop: oProps)
                        // {
                        // Set<OWLObjectPropertyExpression> OPEs =
                        // prop.getSubProperties(ontology);
                        // }

                    } else {
                        superCls.accept(restrictionVisitor);
                    }
                }

                inferredSubClassNames.addAll(getTabbedInferredClassList(targetEVClass, 0));
                ArrayList<String> trimedInferredSubClassNames = getTrimmedList(inferredSubClassNames);
                for (OWLPropertyRange filler : restrictionVisitor.getObjectPropertyFillers()) {
                    if (trimedInferredSubClassNames.contains(filler.toString())) {
                        refinedEVlist.add(filler.toString());
                    }
                }

                objectCardinalityRestrictionCouples.addAll(restrictionVisitor.getObjectRestrictionCouples());

            }

        } catch (OWLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return refinedEVlist;

    }

    public ArrayList<String> getInferredSubclassNamesAll(String rootClassName) {
        openOntology(true);

        ArrayList<String> inferredSubClassNames = new ArrayList<String>();
        try {
            IRI rootClassURI = IRI.create(ontology.getOntologyID() + "#" + rootClassName);
            OWLClass rootClass = manager.getOWLDataFactory().getOWLClass(rootClassURI);

            inferredSubClassNames.addAll(getTabbedInferredClassList(rootClass, 0));
        } catch (OWLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        closeOntology();

        return inferredSubClassNames;

    }

    /**
     * Print the class hierarchy from this class down, assuming this class is at the given
     * level. Makes no attempt to deal sensibly with multiple inheritance. Modified from
     * OWL-API examples.
     * 
     * @param rootClass Top of hierarchy subtree to print
     * @param indent Indentation level to use in printing
     */

    public ArrayList<String> getTabbedInferredClassList(OWLClass rootClass, int indent) throws OWLException {

        ArrayList<String> subClassNames = new ArrayList<String>();
        /*
         * Only print satisfiable classes -- otherwise we end up with bottom everywhere
         */
        if (reasoner.isSatisfiable(rootClass)) {
            String currentIndent = "";
            for (int i = 0; i < indent; i++) {
                currentIndent = currentIndent + tabString;
            }
            subClassNames.add(currentIndent + rootClass.toString());

            // Find the children and recurse
            // Set<Set<OWLClass>> children = reasoner.getSubClasses(rootClass);
            NodeSet<OWLClass> subClses = reasoner.getSubClasses(rootClass, true);
            for (Node<OWLClass> setOfClasses : subClses) {
                for (OWLClass child : setOfClasses) {
                    if (!child.equals(rootClass)) {
                        subClassNames.addAll(getTabbedInferredClassList(child, indent + 1));
                    }
                }
            }
        }
        return subClassNames;
    }

    private ArrayList<String> getTabbedAssertedClassList(String rootClassName, int indent) {
        ArrayList<String> subClassNames = new ArrayList<String>();

        IRI rootClassURI = IRI.create(ontology.getOntologyID() + "#" + rootClassName);
        OWLClass rootClass = manager.getOWLDataFactory().getOWLClass(rootClassURI);

        if (rootClass != null) {
            Set<OWLClassExpression> subClassSet;
            subClassSet = rootClass.getSubClasses(ontology);

            final String tabString = "    ";

            for (OWLClassExpression des : subClassSet) {
                String currentIndent = "";
                for (int i = 0; i < indent; i++) {
                    currentIndent = currentIndent + tabString;
                }
                subClassNames.add(currentIndent + des.toString());

                Set<OWLClassExpression> subClassSubset = des.asOWLClass().getSubClasses(ontology);
                if (subClassSubset.size() != 0) {
                    ArrayList<String> subClassChildrenNames = new ArrayList<String>();
                    subClassChildrenNames = getTabbedAssertedClassList(des.toString(), indent + 1);
                    subClassNames.addAll(subClassChildrenNames);
                }
            }
        }

        return subClassNames;
    }

//    public ArrayList<String[]> getWDIndividuals() {
//
//        openOntology(true);
//
//        ArrayList<String> watchdogNames = new ArrayList<String>();
//        ArrayList<String[]> invokedWatchdogPairs = new ArrayList<String[]>();
//
//        try {
//            IRI rootClassIRI = IRI.create(ontology.getOntologyID() + "#ApplicationWatchdog");
//            OWLClass rootClass = factory.getOWLClass(rootClassIRI);
//
//            watchdogNames.addAll(getTrimmedList(getTabbedInferredClassList(rootClass, 0)));
//            // check if any watchdog has inferred individuals.
//            // if yes, then its corresponding application is functional,
//            // otherwise ignore.
//            for (String watchdogName : watchdogNames) {
//                IRI currentWDIRI = IRI.create(ontology.getOntologyID() + "#" + watchdogName);
//                OWLClass currentWD = manager.getOWLDataFactory().getOWLClass(currentWDIRI);
//                // TODO think about this Set in future, may need for further
//                // use.
//                NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(currentWD, true);
//                // The reasoner returns a NodeSet again. This time the NodeSet contains
//                // individuals.
//                // Again, we just want the individuals, so get a flattened set.
//                Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
//                if (individuals.size() != 0) {
//                    for (OWLNamedIndividual WDindividual : individuals) {
//                        String[] invokedWatchdogName = new String[2];
//                        invokedWatchdogName[0] = watchdogName;
//                        invokedWatchdogName[1] = WDindividual.toString();
//                        invokedWatchdogPairs.add(invokedWatchdogName);
//                    }
//                }
//            }
//        } catch (OWLException e) {
//
//            e.printStackTrace();
//        }
//
//        closeOntology();
//
//        return invokedWatchdogPairs;
//
//    }

    /**
     * this method convert a string to an non-whitespace string, as all entity names used
     * in owl ontology does not allow white spaces. all white space will be replaced with
     * underscore.
     * 
     * @param sourceString
     * @return converted string
     */
    public String convertQueryString(String sourceString) {
        String convertedString = "";
        convertedString = sourceString;
        convertedString = convertedString.replaceAll("\\s", "_");
        return convertedString;
    }



    /**
     * Visits existential restrictions and collects the properties that are restricted
     */
    public static class RestrictionVisitor extends OWLClassExpressionVisitorAdapter {

        private boolean                                processInherited = true;
        private Set<OWLClass>                          processedClasses;
        private Set<OWLObjectPropertyExpression>       restrictedObjectProperties;
        private Set<OWLDataPropertyExpression>         restrictedDataProperties;
        private Set<OWLPropertyRange>                  objectPropertyFillers;
        private Set<OWLPropertyRange>                  dataPropertyFillers;
        private Set<OWLQuantifiedRestriction<?, ?, ?>> objectRestrictions;
        private Set<OWLQuantifiedRestriction<?, ?, ?>> dataRestrictions;
        private Set<String[]>                          objectRestrictionCouples;
        private Set<String[]>                          dataRestrictionCouples;

        private Set<OWLOntology> onts;

        public RestrictionVisitor(Set<OWLOntology> onts) {
            restrictedObjectProperties = new HashSet<OWLObjectPropertyExpression>();
            restrictedDataProperties = new HashSet<OWLDataPropertyExpression>();
            objectPropertyFillers = new HashSet<OWLPropertyRange>();
            dataPropertyFillers = new HashSet<OWLPropertyRange>();
            processedClasses = new HashSet<OWLClass>();
            objectRestrictions = new HashSet<OWLQuantifiedRestriction<?, ?, ?>>();
            dataRestrictions = new HashSet<OWLQuantifiedRestriction<?, ?, ?>>();
            objectRestrictionCouples = new HashSet<String[]>();
            dataRestrictionCouples = new HashSet<String[]>();
            this.onts = onts;
        }

        public void setProcessInherited(boolean processInherited) {
            this.processInherited = processInherited;
        }

        public Set<OWLQuantifiedRestriction<?, ?, ?>> getObjectRestrictions() {
            return objectRestrictions;
        }

        public Set<OWLQuantifiedRestriction<?, ?, ?>> getDataRestrictions() {
            return dataRestrictions;
        }

        public Set<OWLObjectPropertyExpression> getRestrictedObjectProperties() {
            return restrictedObjectProperties;
        }

        public Set<OWLDataPropertyExpression> getRestrictedDataProperties() {
            return restrictedDataProperties;
        }

        public Set<String[]> getObjectRestrictionCouples() {
            return objectRestrictionCouples;
        }

        public Set<String[]> getDataRestrictionCouples() {
            return dataRestrictionCouples;
        }

        public Set<OWLPropertyRange> getObjectPropertyFillers() {
            return objectPropertyFillers;
        }

        public Set<OWLPropertyRange> getDataPropertyFillers() {
            return dataPropertyFillers;
        }

        @Override
        public void visit(OWLClass desc) {

            if (processInherited && !processedClasses.contains(desc)) {
                // If we are processing inherited restrictions then
                // we recursively visit named supers. Note that we
                // need to keep track of the classes that we have processed
                // so that we don't get caught out by cycles in the taxonomy
                processedClasses.add(desc);
                for (OWLOntology ont : onts) {
                    for (OWLSubClassOfAxiom ax : ont.getSubClassAxiomsForSubClass(desc)) {
                        ax.getSuperClass().accept(this);
                    }
                }
            }
        }

        public void reset() {
            processedClasses.clear();
            restrictedObjectProperties.clear();
            restrictedDataProperties.clear();
            objectPropertyFillers.clear();
            dataPropertyFillers.clear();
            objectRestrictions.clear();
            dataRestrictions.clear();
            objectRestrictionCouples.clear();
            dataRestrictionCouples.clear();
        }

        /*
         * all types of object restrictions need to process OWLObjectAllValuesFrom ,
         * OWLObjectCardinalityRestriction, OWLObjectExactCardinality,
         * OWLObjectMaxCardinality, OWLObjectMinCardinality, OWLObjectSomeValuesFrom
         */

        @Override
        public void visit(OWLObjectAllValuesFrom desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        @Override
        public void visit(OWLObjectExactCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        @Override
        public void visit(OWLObjectMaxCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        @Override
        public void visit(OWLObjectMinCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        public void visit(OWLObjectCardinalityRestriction desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        @Override
        public void visit(OWLObjectSomeValuesFrom desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedObjectProperties.add(desc.getProperty());
            objectPropertyFillers.add(desc.getFiller());
            objectRestrictions.add(desc);

            String[] objectAllRestrictionCouple = new String[2];
            objectAllRestrictionCouple[0] = desc.getProperty().toString();
            objectAllRestrictionCouple[1] = desc.getFiller().toString();
            objectRestrictionCouples.add(objectAllRestrictionCouple);
        }

        /*
         * all types of data restrictions need to process
         * 
         * OWLDataAllValuesFrom, OWLDataCardinalityRestriction, OWLDataExactCardinality,
         * OWLDataMaxCardinality, OWLDataMinCardinality, OWLDataSomeValuesFrom,
         */
        @Override
        public void visit(OWLDataAllValuesFrom desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }

        @Override
        public void visit(OWLDataExactCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }

        @Override
        public void visit(OWLDataMaxCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }

        @Override
        public void visit(OWLDataMinCardinality desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }

        public void visit(OWLDataCardinalityRestriction desc) {
            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }

        @Override
        public void visit(OWLDataSomeValuesFrom desc) {

            // This method gets called when a description (OWLClassExpression) is an
            // existential (someValuesFrom) restriction and it asks us to visit
            // it
            restrictedDataProperties.add(desc.getProperty());
            dataPropertyFillers.add(desc.getFiller());
            dataRestrictions.add(desc);

            String[] dataAllRestrictionCouple = new String[2];
            dataAllRestrictionCouple[0] = desc.getProperty().toString();
            dataAllRestrictionCouple[1] = desc.getFiller().toString();
            dataRestrictionCouples.add(dataAllRestrictionCouple);
        }
    }

}
