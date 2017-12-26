/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other authors listed
 * in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular, collaborative,
 * integrated development of interoperable data and model components. For details, see
 * http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the Affero
 * General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without even the
 * implied warranty of merchantability or fitness for a particular purpose. See the Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. The license
 * is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.owl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.model.SemanticType;
import org.integratedmodelling.kim.utils.CamelCase;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.URLUtils;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLProperty;
import org.semanticweb.owlapi.util.AutoIRIMapper;

/**
 * Import concepts and properties from OWL ontologies.
 *
 * @author Ferd
 */
public enum OWL {

    INSTANCE;

    public static final String                INTERNAL_ONTOLOGY_PREFIX = "http://integratedmodelling.org/ks/internal";

    private final HashMap<String, INamespace> namespaces               = new HashMap<>();
    private final HashMap<String, INamespace> resourceIndex            = new HashMap<>();
    HashMap<String, IOntology>                ontologies               = new HashMap<>();
    HashMap<String, String>                   iri2ns                   = new HashMap<>();
    HashMap<String, String>                   c2ont                    = new HashMap<>();
    HashMap<SemanticType, OWLClass>           systemConcepts           = new HashMap<>();
    HashMap<String, IConcept>                 xsdMappings              = new HashMap<>();

    static EnumSet<Type>                      emptyType                = EnumSet.noneOf(Type.class);

    /*
     * package-visible, never null.
     */
    OWLOntologyManager                        manager                  = null;
    private File                              loadPath;

    IConcept                                  thing;
    IConcept                                  nothing;

    public IOntology requireOntology(String id, String prefix) {

        if (ontologies.get(id) != null) {
            return ontologies.get(id);
        }

        IOntology ret = null;
        try {
            OWLOntology o = manager.createOntology(IRI
                    .create(prefix + "/" + id));
            ret = new Ontology(o, id);
            ontologies.put(id, ret);
            iri2ns.put(((Ontology) ret).getPrefix(), id);
        } catch (OWLOntologyCreationException e) {
            throw new KlabRuntimeException(e);
        }

        return ret;
    }

    /**
     * Get the IConcept corresponding to the OWL class passed. Throws an unchecked exception if not found.
     * 
     * @param owl
     * @return
     */
    public IConcept getConceptFor(OWLClass owl, boolean complainIfNotFound) {
        IConcept ret = null;
        String sch = owl.getIRI().getNamespace();
        if (sch.endsWith("#")) {
            sch = sch.substring(0, sch.length() - 1);
        }
        IOntology ontology = getOntology(iri2ns.get(sch));
        if (ontology != null) {
            ret = ontology.getConcept(owl.getIRI().getFragment());
        }

        if (ret == null && complainIfNotFound) {
            throw new KlabRuntimeException("internal: OWL entity " + owl
                    + " not corresponding to a known ontology");
        }

        return ret;
    }

    public IConcept getConceptFor(IRI iri) {
        IConcept ret = null;
        String sch = iri.getNamespace();
        if (sch.endsWith("#")) {
            sch = sch.substring(0, sch.length() - 1);
        }
        IOntology ontology = getOntology(iri2ns.get(sch));
        if (ontology != null) {
            ret = ontology.getConcept(iri.getFragment());
        }

        if (ret == null) {
            throw new KlabRuntimeException("internal: OWL IRI " + iri
                    + " not corresponding to a known ontology");
        }

        return ret;
    }

    /**
     * Get the IProperty corresponding to the OWL class passed. Throws an unchecked exception if not found.
     * 
     * @param owl
     * @return
     */
    public IProperty getPropertyFor(OWLProperty<?, ?> owl) {
        IProperty ret = null;
        String sch = owl.getIRI().getNamespace();
        if (sch.endsWith("#")) {
            sch = sch.substring(0, sch.length() - 1);
        }
        IOntology ontology = getOntology(iri2ns.get(sch));
        if (ontology != null) {
            ret = ontology.getProperty(owl.getIRI().getFragment());
        }

        if (ret == null) {
            throw new KlabRuntimeException("internal: OWL entity " + owl
                    + " not corresponding to a known ontology");
        }

        return ret;
    }

    public IProperty getPropertyFor(IRI iri) {
        IProperty ret = null;
        String sch = iri.getNamespace();
        if (sch.endsWith("#")) {
            sch = sch.substring(0, sch.length() - 1);
        }
        IOntology ontology = getOntology(iri2ns.get(sch));
        if (ontology != null) {
            ret = ontology.getProperty(iri.getFragment());
        }

        if (ret == null) {
            throw new KlabRuntimeException("internal: OWL IRI " + iri
                    + " not corresponding to a known ontology");
        }

        return ret;
    }

    public static String getFileName(String s) {

        String ret = s;

        int sl = ret.lastIndexOf(File.separator);
        if (sl < 0) {
            sl = ret.lastIndexOf('/');
        }
        if (sl > 0) {
            ret = ret.substring(sl + 1);
        }

        return ret;
    }

    /**
     * Create a manager and load every OWL file under the load path.
     * 
     * @throws KlabException
     */
    public void initialize(File loadPath) throws KlabException {
        manager = OWLManager.createOWLOntologyManager();
        this.loadPath = loadPath;
        initialize();
    }

    public void createReasoner() {

        /*
         * Create the reasoner.
         */
        Reasoner.INSTANCE.setReasoner(new KlabReasoner(this));

        /*
         * all namespaces so far are internal, and just these.
         */
        for (INamespace ns : this.namespaces.values()) {
            Reasoner.INSTANCE.addOntology(ns.getOntology());
        }
    }

    private void initialize() throws KlabException {

        /*
         * FIXME manual mapping until I understand what's going on with BFO, whose
         * concepts have a IRI that does not contain the namespace.
         */
        iri2ns.put("http://purl.obolibrary.org/obo", "bfo");

        /*
         * TODO insert basic datatypes as well
         */
        this.systemConcepts.put(new SemanticType("owl:Thing"), manager
                .getOWLDataFactory().getOWLThing());
        this.systemConcepts.put(new SemanticType("owl:Nothing"), manager
                .getOWLDataFactory().getOWLNothing());

        if (this.loadPath == null) {
            throw new KlabIOException("owl resources cannot be found: knowledge load directory does not exist");
        }

        load(this.loadPath);

        /*
         * all namespaces so far are internal, and just these.
         */
        for (INamespace ns : this.namespaces.values()) {
            ((Namespace) ns).setInternal(true);
            ((Ontology) (ns.getOntology())).setInternal(true);
        }

        createReasoner();
    }

    String importOntology(OWLOntology ontology, String resource, String namespace, boolean imported, IMonitor monitor)
            throws KlabException {

        if (!ontologies.containsKey(namespace)) {
            ontologies.put(namespace, new Ontology(ontology, namespace));
        }

        /*
         * seen already?
         */
        if (this.namespaces.containsKey(namespace)) {
            return namespace;
        }

        Namespace ns = new Namespace(namespace, new File(resource), ontologies
                .get(namespace));

        this.namespaces.put(namespace, ns);

        return namespace;
    }

    /*
     * the three knowledge manager methods we implement, so we can serve as delegate to a
     * KM for these.
     */
    public IConcept getConcept(String concept) {

        IConcept result = null;

        if (SemanticType.validate(concept)) {
            SemanticType st = new SemanticType(concept);
            IOntology o = ontologies.get(st.getNamespace());
            if (o == null) {
                OWLClass systemConcept = this.systemConcepts.get(st);
                if (systemConcept != null) {
                    result = new Concept(systemConcept, st.getNamespace(), emptyType);
                }
            } else {
                result = o.getConcept(st.getName());
            }
        }

        return result;
    }

    public IProperty getProperty(String concept) {

        IProperty result = null;

        if (SemanticType.validate(concept)) {
            String[] conceptSpaceAndLocalName = SemanticType
                    .splitIdentifier(concept);
            IOntology o = ontologies.get(conceptSpaceAndLocalName[0]);
            if (o != null) {
                result = o.getProperty(conceptSpaceAndLocalName[1]);
                if (result == null && !conceptSpaceAndLocalName[1].startsWith("p")) {
                    result = o.getProperty("p" + conceptSpaceAndLocalName[1]);
                }
            }
        }
        return result;
    }

    public IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc) {
        IConcept ret = null;
        IConcept tmpConcept;

        for (IConcept concept : cc) {
            if (ret == null) {
                ret = concept;
            } else {
                tmpConcept = ret.getLeastGeneralCommonConcept(concept);
                if (tmpConcept != null) {
                    ret = tmpConcept;
                }
            }
        }

        return ret;
    }

    /**
     * Return the ontology for the given namespace ID (short name).
     * 
     * @param ns
     * @return the ontology
     */
    public Ontology getOntology(String ns) {
        return (Ontology) ontologies.get(ns);
    }

    public IConcept getRootConcept() {
        if (this.thing == null) {
            this.thing = new Concept(manager.getOWLDataFactory()
                    .getOWLThing(), "owl", emptyType);
        }
        return this.thing;
    }

    public String getConceptSpace(IRI iri) {

        if (iri2ns.containsKey(iri.toString())) {
            return iri2ns.get(iri.toString());
        }

        String oIri = removeEntity(iri);
        String ret = iri2ns.get(oIri);

        if (ret == null) {
            /*
             * happens, whenever we depend on a concept from a server ontology not loaded
             * yet. Must find a way to deal with this.
             */
            ret = MiscUtilities.getNameFromURL(oIri);
        }

        return ret;
    }

    private String removeEntity(IRI iri) {
        String eiri = iri.toString();
        if (eiri.contains("#")) {
            eiri = eiri.substring(0, eiri.lastIndexOf('#'));
        } else {
            eiri = eiri.substring(0, eiri.lastIndexOf('/'));
        }
        return eiri;
    }

    /**
     * Load OWL files from given directory and in its subdirectories, using a prefix mapper to resolve URLs
     * internally and deriving ontology names from the relative paths. This uses the resolver passed at
     * initialization only to create the namespace. It's only meant for core knowledge not seen by users.
     *
     * @param kdir
     * @throws KlabIOException
     */
    public void load(File kdir) throws KlabException {

        AutoIRIMapper imap = new AutoIRIMapper(kdir, true);
        manager.addIRIMapper(imap);

        File[] files = kdir.listFiles();
        // null in error
        if (files != null) {
            for (File fl : files) {
                loadInternal(fl, "", null);
            }
        } else {
            throw new KlabIOException("Errors reading core ontologies: system will be nonfunctional. Check server distribution.");
        }
    }

    private void loadInternal(File f, String path, IMonitor monitor)
            throws KlabException {

        String pth = path == null ? ""
                : (path + (path.isEmpty() ? "" : ".")
                        + CamelCase.toLowerCase(MiscUtilities
                                .getFileBaseName(f.toString()), '-'));

        if (f.isDirectory()) {

            for (File fl : f.listFiles()) {
                loadInternal(fl, pth, monitor);
            }

        } else if (MiscUtilities.getFileExtension(f.toString()).equals("owl")) {

            InputStream input;

            try {
                input = new FileInputStream(f);
                OWLOntology ontology = manager
                        .loadOntologyFromOntologyDocument(input);
                input.close();
                Ontology ont = new Ontology(ontology, pth);
                ont.setResourceUrl(f.toURI().toURL().toString());
                ontologies.put(pth, ont);
                iri2ns.put(ont.getPrefix(), pth);

            } catch (OWLOntologyAlreadyExistsException e) {

                /*
                 * already imported- wrap it and use it as is.
                 */
                OWLOntology ont = manager.getOntology(e.getOntologyID()
                        .getOntologyIRI());
                if (ont != null && ontologies.get(pth) == null) {
                    Ontology o = new Ontology(ont, pth);
                    try {
                        o.setResourceUrl(f.toURI().toURL().toString());
                    } catch (MalformedURLException e1) {
                    }
                    ontologies.put(pth, o);
                    iri2ns.put(ont.getOntologyID().getOntologyIRI().toString(), pth);
                }

            } catch (Exception e) {

                /*
                 * everything else is probably an error
                 */
                throw new KlabIOException("reading " + f + ": "
                        + ExceptionUtils.getRootCauseMessage(e));
            }

            IOntology o = ontologies.get(pth);
            if (o != null) {
                /*
                 * create namespace
                 */
                Namespace ns = new Namespace(pth, f, o);
                // ns.setId(pth);
                // ns.setResourceUrl(f.toString());
                // ns.setOntology(o);
                this.namespaces.put(pth, ns);
            }
        }
    }

    public IOntology refreshOntology(URL url, String id)
            throws KlabException {

        InputStream input;
        Ontology ret = null;

        try {
            input = url.openStream();
            OWLOntology ontology = manager
                    .loadOntologyFromOntologyDocument(input);
            input.close();
            ret = new Ontology(ontology, id);
            ret.setResourceUrl(url.toString());
            ontologies.put(id, ret);
            iri2ns.put(ret.getPrefix(), id);

        } catch (OWLOntologyAlreadyExistsException e) {

            /*
             * already imported- wrap it and use it as is.
             */
            OWLOntology ont = manager.getOntology(e.getOntologyID()
                    .getOntologyIRI());
            if (ont != null && ontologies.get(id) == null) {
                Ontology ontology = new Ontology(ont, id);
                ontologies.put(id, ontology);
                iri2ns.put(ontology.getPrefix(), id);
            }

        } catch (Exception e) {

            /*
             * everything else is probably an error
             */
            throw new KlabIOException(e);
        }

        IOntology o = ontologies.get(id);
        if (o != null) {
            /*
             * create namespace
             */
            Namespace ns = new Namespace(id, new File(url.getFile()), o);
            // INamespaceDefinition ns = (INamespaceDefinition) _resolver
            // .newLanguageObject(INamespace.class, null);
            // ns.setId(id);
            // ns.setResourceUrl(url.toString());
            // ns.setOntology(o);
            this.namespaces.put(id, ns);
        }

        return ret;
    }

    public IConcept getDatatypeMapping(String string) {
        return this.xsdMappings.get(string);
    }

    public IConcept registerDatatypeMapping(String xsd, IConcept concept) {
        return this.xsdMappings.put(xsd, concept);
    }

    public void releaseOntology(IOntology ontology) {

        // TODO remove from _csIndex - should be harmless to leave for now
        INamespace ns = this.namespaces.get(ontology.getName());
        if (ns != null) {
            this.resourceIndex.remove(ns.getLocalFile().toString());
        }
        this.namespaces.remove(ontology.getName());
        ontologies.remove(ontology.getName());
        iri2ns.remove(((Ontology) ontology).getPrefix());
        manager.removeOntology(((Ontology) ontology).ontology);
    }

    public void clear() {
        Collection<String> keys = new HashSet<>(ontologies.keySet());
        for (String o : keys) {
            releaseOntology(getOntology(o));
        }
    }

    public Collection<IOntology> getOntologies(boolean includeInternal) {
        ArrayList<IOntology> ret = new ArrayList<>();
        for (IOntology o : ontologies.values()) {
            if (((Ontology) o).isInternal() && !includeInternal)
                continue;
            ret.add(o);
        }
        return ret;
    }

    public INamespace getNamespace(String ns) {
        return this.namespaces.get(ns);
    }

    public Collection<INamespace> getNamespaces() {
        return this.namespaces.values();
    }

    public Collection<IConcept> listConcepts(boolean includeInternal) {

        ArrayList<IConcept> ret = new ArrayList<>();

        for (IOntology o : ontologies.values()) {
            if (((Ontology) o).isInternal() && !includeInternal)
                continue;
            ret.addAll(o.getConcepts());
        }
        return ret;
    }

    public IConcept getNothing() {
        if (this.nothing == null) {
            this.nothing = new Concept(manager.getOWLDataFactory()
                    .getOWLNothing(), "owl", emptyType);
        }
        return this.nothing;
    }

    public Collection<IConcept> unwrap(OWLClassExpression cls) {

        Set<IConcept> ret = new HashSet<>();
        if (cls instanceof OWLObjectIntersectionOf) {
            for (OWLClassExpression o : ((OWLObjectIntersectionOf) cls).getOperands()) {
                ret.addAll(unwrap(o));
            }
        } else if (cls instanceof OWLObjectUnionOf) {
            for (OWLClassExpression o : ((OWLObjectUnionOf) cls).getOperands()) {
                ret.addAll(unwrap(o));
            }
        } else if (cls instanceof OWLClass) {
            ret.add(getExistingOrCreate(cls.asOWLClass()));
        }
        return ret;
    }

    /**
     * Return the concept or concepts (if a union) restricted by the passed object property in the restriction
     * closest to the passed concept in its asserted parent hierarchy.
     * 
     * @param target
     * @param restricted
     * @return the concepts restricted in the target by the property
     */
    public Collection<IConcept> getRestrictedClasses(IConcept target, IProperty restricted) {
        return new SpecializingRestrictionVisitor(target, restricted, true).getResult();
    }

    public Collection<IConcept> getRestrictedClasses(IConcept target, IProperty restricted, boolean useSuperproperties) {
        return new SpecializingRestrictionVisitor(target, restricted, useSuperproperties)
                .getResult();
    }

    public void restrictSome(IConcept target, IProperty property, IConcept filler) {
        target.getOntology().define(Collections.singleton(Axiom
                .SomeValuesFrom(target.getLocalName(), property.toString(), filler
                        .toString())));
    }

    public void restrictAll(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers) {

        if (fillers.size() == 1) {
            restrictAll(target, property, fillers.iterator().next());
            return;
        }

        if (!(how.equals(LogicalConnector.INTERSECTION)
                || how.equals(LogicalConnector.UNION))) {
            throw new KlabRuntimeException("connectors can only be union or intersection");
        }

        Set<OWLClassExpression> classes = new HashSet<>();
        for (IConcept c : fillers) {
            classes.add(((Concept) c)._owl);
        }
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClassExpression union = how.equals(LogicalConnector.UNION)
                ? factory.getOWLObjectUnionOf(classes)
                : factory.getOWLObjectIntersectionOf(classes);
        OWLClassExpression restriction = factory
                .getOWLObjectAllValuesFrom(((Property) property)._owl
                        .asOWLObjectProperty(), union);
        manager.addAxiom(((Ontology) target.getOntology()).ontology, factory
                        .getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
    }

    public void restrictSome(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers) {

        if (fillers.size() == 1) {
            restrictSome(target, property, fillers.iterator().next());
            return;
        }

        if (!(how.equals(LogicalConnector.INTERSECTION)
                || how.equals(LogicalConnector.UNION))) {
            throw new KlabRuntimeException("connectors can only be union or intersection");
        }

        Set<OWLClassExpression> classes = new HashSet<>();
        for (IConcept c : fillers) {
            classes.add(((Concept) c)._owl);
        }
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClassExpression union = how.equals(LogicalConnector.UNION)
                ? factory.getOWLObjectUnionOf(classes)
                : factory.getOWLObjectIntersectionOf(classes);
        OWLClassExpression restriction = factory
                .getOWLObjectSomeValuesFrom(((Property) property)._owl
                        .asOWLObjectProperty(), union);
        manager.addAxiom(((Ontology) target.getOntology()).ontology, factory
                        .getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
    }

    public void restrictAll(IConcept target, IProperty property, IConcept filler) {
        target.getOntology().define(Collections.singleton(Axiom
                .AllValuesFrom(target.getLocalName(), property.toString(), filler
                        .toString())));
    }

    public void restrictAtLeast(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers, int min) {

        if (fillers.size() == 1) {
            restrictAtLeast(target, property, fillers.iterator().next(), min);
            return;
        }

        if (!(how.equals(LogicalConnector.INTERSECTION)
                || how.equals(LogicalConnector.UNION))) {
            throw new KlabRuntimeException("connectors can only be union or intersection");
        }

        Set<OWLClassExpression> classes = new HashSet<>();
        for (IConcept c : fillers) {
            classes.add(((Concept) c)._owl);
        }
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClassExpression union = how.equals(LogicalConnector.UNION)
                ? factory.getOWLObjectUnionOf(classes)
                : factory.getOWLObjectIntersectionOf(classes);
        OWLClassExpression restriction = factory
                .getOWLObjectMinCardinality(min, ((Property) property)._owl
                        .asOWLObjectProperty(), union);
        manager.addAxiom(((Ontology) target.getOntology()).ontology, factory
                        .getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
    }

    public void restrictAtLeast(IConcept target, IProperty property, IConcept filler, int min) {
        target.getOntology().define(Collections.singleton(Axiom
                .AtLeastNValuesFrom(target.getLocalName(), property.toString(), filler
                        .toString(), min)));
    }

    public void restrictAtMost(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers, int max) {

        if (fillers.size() == 1) {
            restrictAtMost(target, property, fillers.iterator().next(), max);
            return;
        }
        if (!(how.equals(LogicalConnector.INTERSECTION)
                || how.equals(LogicalConnector.UNION))) {
            throw new KlabRuntimeException("connectors can only be union or intersection");
        }
        Set<OWLClassExpression> classes = new HashSet<>();
        for (IConcept c : fillers) {
            classes.add(((Concept) c)._owl);
        }
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClassExpression union = how.equals(LogicalConnector.UNION)
                ? factory.getOWLObjectUnionOf(classes)
                : factory.getOWLObjectIntersectionOf(classes);
        OWLClassExpression restriction = factory
                .getOWLObjectMaxCardinality(max, ((Property) property)._owl
                        .asOWLObjectProperty(), union);
        manager.addAxiom(((Ontology) target.getOntology()).ontology, factory
                        .getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
    }

    public void restrictAtMost(IConcept target, IProperty property, IConcept filler, int max) {
        target.getOntology().define(Collections.singleton(Axiom
                .AtMostNValuesFrom(target.getLocalName(), property.toString(), filler
                        .toString(), max)));
    }

    public void restrictExactly(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers, int howmany) {

        if (fillers.size() == 1) {
            restrictExactly(target, property, fillers.iterator().next(), howmany);
            return;
        }
        if (!(how.equals(LogicalConnector.INTERSECTION)
                || how.equals(LogicalConnector.UNION))) {
            throw new KlabRuntimeException("connectors can only be union or intersection");
        }
        Set<OWLClassExpression> classes = new HashSet<>();
        for (IConcept c : fillers) {
            classes.add(((Concept) c)._owl);
        }
        OWLDataFactory factory = manager.getOWLDataFactory();
        OWLClassExpression union = how.equals(LogicalConnector.UNION)
                ? factory.getOWLObjectUnionOf(classes)
                : factory.getOWLObjectIntersectionOf(classes);
        OWLClassExpression restriction = factory
                .getOWLObjectExactCardinality(howmany, ((Property) property)._owl
                        .asOWLObjectProperty(), union);
        manager.addAxiom(((Ontology) target.getOntology()).ontology, factory
                        .getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
    }

    public void restrictExactly(IConcept target, IProperty property, IConcept filler, int howMany) {
        target.getOntology().define(Collections.singleton(Axiom
                .ExactlyNValuesFrom(target.getLocalName(), property.toString(), filler
                        .toString(), howMany)));
    }

    /**
     * Return whether the restriction on type involving concept is optional. If there is no such restriction,
     * return false.
     * 
     * @param type
     * @param observableRole
     * @return
     */
    public boolean isRestrictionOptional(IConcept type, IConcept concept) {
        return new ConceptRestrictionVisitor(type, concept).isOptional();
    }

    /**
     * Return whether the restriction on type involving concept is optional. If there is no such restriction,
     * return false.
     * 
     * @param type
     * @param observableRole
     * @return
     */
    public boolean isRestrictionDenied(IConcept type, IConcept concept) {
        return new ConceptRestrictionVisitor(type, concept).isDenied();
    }

    public IProperty getRestrictingProperty(IConcept type, IConcept concept) {
        ConceptRestrictionVisitor visitor = new ConceptRestrictionVisitor(type, concept);
        if (visitor.getRestriction() != null) {
            return getPropertyFor((OWLProperty<?, ?>) visitor.getRestriction()
                    .getProperty());
        }
        return null;
    }

    public IConcept getExistingOrCreate(OWLClass owl) {

        String conceptId = owl.getIRI().getFragment();
        String namespace = getConceptSpace(owl.getIRI());

        IConcept ret = null;
        IOntology ontology = ontologies.get(namespace);
        if (ontology == null) {
            throw new KlabRuntimeException("getExistingOrCreate: ontology not found: " + namespace);
        }

        ret = ontology.getConcept(conceptId);
        if (ret == null) {
            ret = ((Ontology) ontology).createConcept(conceptId, emptyType);
        }

        return ret;
    }

    public IConcept getIntersection(Collection<IConcept> concepts, IOntology destination) {
        // TODO
        return null;
    }

    public IConcept getUnion(Collection<IConcept> concepts, IOntology destination) {
        // TODO
        return null;
    }
    
    public IConcept getConsequentialityEvent(Collection<IConcept> concepts, IOntology destination) {
        // TODO
        return null;
    }


	public String importExternal(String url, String prefix, IMonitor monitor) throws KlabException {

	    // TODO must handle the situation when the prefix is already there better than this.
	    
        if (iri2ns.containsKey(url)) {
            return iri2ns.get(url);
        }

        File out = new File(Configuration.INSTANCE.getDataPath("knowledge/imports") + File.separator + prefix + ".owl");
        try {
            URLUtils.copyChanneled(new URL(url), out);
            loadInternal(out, prefix, monitor);
        } catch (MalformedURLException e) {
            monitor.error(e);
            return null;
        }
                
		return prefix;
	}

}
