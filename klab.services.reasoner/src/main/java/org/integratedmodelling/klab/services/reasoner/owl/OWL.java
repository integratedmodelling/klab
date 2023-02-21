///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
// * authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
// * collaborative, integrated development of interoperable data and model components. For details,
// * see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms of the
// * Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any warranty; without
// * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
// * General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this program; if
// * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
// * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.klab.services.reasoner.owl;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.InputStream;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.EnumSet;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.integratedmodelling.kim.api.IKimConcept.Type;
//import org.integratedmodelling.klab.Authorities;
//import org.integratedmodelling.klab.Concepts;
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.Ontologies;
//import org.integratedmodelling.klab.Reasoner;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IOntology;
//import org.integratedmodelling.klab.api.knowledge.IProperty;
//import org.integratedmodelling.klab.api.knowledge.ISemantic;
//import org.integratedmodelling.klab.api.model.INamespace;
//import org.integratedmodelling.klab.api.provenance.IArtifact;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.common.LogicalConnector;
//import org.integratedmodelling.klab.common.SemanticType;
//import org.integratedmodelling.klab.exceptions.KlabException;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
//import org.integratedmodelling.klab.model.Namespace;
//import org.integratedmodelling.klab.utils.CamelCase;
//import org.integratedmodelling.klab.utils.MiscUtilities;
//import org.integratedmodelling.klab.utils.StringUtil;
//import org.integratedmodelling.klab.utils.URLUtils;
//import org.semanticweb.owlapi.apibinding.OWLManager;
//import org.semanticweb.owlapi.model.IRI;
//import org.semanticweb.owlapi.model.OWLClass;
//import org.semanticweb.owlapi.model.OWLClassExpression;
//import org.semanticweb.owlapi.model.OWLDataFactory;
//import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
//import org.semanticweb.owlapi.model.OWLObjectUnionOf;
//import org.semanticweb.owlapi.model.OWLOntology;
//import org.semanticweb.owlapi.model.OWLOntologyAlreadyExistsException;
//import org.semanticweb.owlapi.model.OWLOntologyCreationException;
//import org.semanticweb.owlapi.model.OWLOntologyManager;
//import org.semanticweb.owlapi.model.OWLProperty;
//import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
//import org.semanticweb.owlapi.util.AutoIRIMapper;
//
///**
// * Import concepts and properties from OWL ontologies.
// *
// * @author Ferd
// */
//public enum OWL {
//
//    INSTANCE;
//
//    public static final String INTERNAL_ONTOLOGY_PREFIX = "http://integratedmodelling.org/ks/internal";
//
//    private final HashMap<String, INamespace> namespaces = new HashMap<>();
//    private final HashMap<String, INamespace> resourceIndex = new HashMap<>();
//    HashMap<String, Ontology> ontologies = new HashMap<>();
//    HashMap<String, String> iri2ns = new HashMap<>();
//    HashMap<String, String> c2ont = new HashMap<>();
//    HashMap<SemanticType, OWLClass> systemConcepts = new HashMap<>();
//    HashMap<String, Concept> xsdMappings = new HashMap<>();
//
//    static EnumSet<Type> emptyType = EnumSet.noneOf(Type.class);
//
//    Ontology nonSemanticConcepts;
//
//    /*
//     * package-visible, never null.
//     */
//    OWLOntologyManager manager = null;
//    private File loadPath;
//
//    Concept thing;
//    Concept nothing;
//
//    public Ontology requireOntology(String id, String prefix) {
//
//        if (ontologies.get(id) != null) {
//            return ontologies.get(id);
//        }
//
//        Ontology ret = null;
//        try {
//            OWLOntology o = manager.createOntology(IRI.create(prefix + "/" + id));
//            ret = new Ontology(o, id);
//            ontologies.put(id, ret);
//            iri2ns.put(((Ontology) ret).getPrefix(), id);
//        } catch (OWLOntologyCreationException e) {
//            throw new KlabInternalErrorException(e);
//        }
//
//        return ret;
//    }
//
//    /**
//     * Get the IConcept corresponding to the OWL class passed. Throws an unchecked exception if not
//     * found.
//     * 
//     * @param owl
//     * @param complainIfNotFound
//     * @return the concept for the class
//     */
//    public Concept getConceptFor(OWLClass owl, boolean complainIfNotFound) {
//        Concept ret = null;
//        String sch = owl.getIRI().getNamespace();
//        if (sch.endsWith("#")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        if (sch.endsWith("/")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        Ontology ontology = getOntology(iri2ns.get(sch));
//        if (ontology != null) {
//            ret = ontology.getConcept(owl.getIRI().getFragment());
//        }
//
//        if (ret == null && complainIfNotFound) {
//            throw new KlabInternalErrorException("internal: OWL entity " + owl + " not corresponding to a known ontology");
//        }
//
//        return ret;
//    }
//
//    public IConcept getConceptFor(IRI iri) {
//        IConcept ret = null;
//        String sch = iri.getNamespace();
//        if (sch.endsWith("#")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        IOntology ontology = getOntology(iri2ns.get(sch));
//        if (ontology != null) {
//            ret = ontology.getConcept(iri.getFragment());
//        }
//
//        if (ret == null) {
//            throw new KlabInternalErrorException("internal: OWL IRI " + iri + " not corresponding to a known ontology");
//        }
//
//        return ret;
//    }
//
//    /**
//     * Get the IProperty corresponding to the OWL class passed. Throws an unchecked exception if not
//     * found.
//     * 
//     * @param owl
//     * @return the property for the class
//     */
//    public IProperty getPropertyFor(OWLProperty<?, ?> owl) {
//
//        IProperty ret = null;
//        String sch = owl.getIRI().getNamespace();
//        if (sch.endsWith("#")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        if (sch.endsWith("/")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        IOntology ontology = getOntology(iri2ns.get(sch));
//        if (ontology != null) {
//            ret = ontology.getProperty(owl.getIRI().getFragment());
//        }
//
//        if (ret == null) {
//            throw new KlabInternalErrorException("internal: OWL entity " + owl + " does not correspond to a known ontology");
//        }
//
//        return ret;
//    }
//
//    public IProperty getPropertyFor(IRI iri) {
//        IProperty ret = null;
//        String sch = iri.getNamespace();
//        if (sch.endsWith("#")) {
//            sch = sch.substring(0, sch.length() - 1);
//        }
//        IOntology ontology = getOntology(iri2ns.get(sch));
//        if (ontology != null) {
//            ret = ontology.getProperty(iri.getFragment());
//        }
//
//        if (ret == null) {
//            throw new KlabInternalErrorException("internal: OWL IRI " + iri + " not corresponding to a known ontology");
//        }
//
//        return ret;
//    }
//
//    public static String getFileName(String s) {
//
//        String ret = s;
//
//        int sl = ret.lastIndexOf(File.separator);
//        if (sl < 0) {
//            sl = ret.lastIndexOf('/');
//        }
//        if (sl > 0) {
//            ret = ret.substring(sl + 1);
//        }
//
//        return ret;
//    }
//
//    /**
//     * Create a manager and load every OWL file under the load path.
//     * 
//     * @param loadPath
//     * @param monitor
//     * 
//     * @throws KlabException
//     */
//    public void initialize(File loadPath, IMonitor monitor) throws KlabException {
//        manager = OWLManager.createOWLOntologyManager();
//        this.loadPath = loadPath;
//        initialize(monitor);
//    }
//
//    public void createReasoner(IMonitor monitor) {
//
//        /*
//         * Create the reasoner.
//         */
//        Reasoner.INSTANCE.setReasoner(new KlabReasoner(this, monitor));
//
//        /*
//         * all namespaces so far are internal, and just these.
//         */
//        for (INamespace ns : this.namespaces.values()) {
//            Reasoner.INSTANCE.addOntology((Ontology) ns.getOntology());
//        }
//    }
//
//    private void initialize(IMonitor monitor) throws KlabException {
//
//        /*
//         * FIXME manual mapping until I understand what's going on with BFO, whose concepts have a
//         * IRI that does not contain the namespace.
//         */
//        iri2ns.put("http://purl.obolibrary.org/obo", "bfo");
//
//        /*
//         * TODO insert basic datatypes as well
//         */
//        this.systemConcepts.put(new SemanticType("owl:Thing"), manager.getOWLDataFactory().getOWLThing());
//        this.systemConcepts.put(new SemanticType("owl:Nothing"), manager.getOWLDataFactory().getOWLNothing());
//
//        if (this.loadPath == null) {
//            throw new KlabIOException("owl resources cannot be found: knowledge load directory does not exist");
//        }
//
//        load(this.loadPath);
//
//        /*
//         * all namespaces so far are internal, and just these.
//         */
//        for (INamespace ns : this.namespaces.values()) {
//            ((Namespace) ns).setInternal(true);
//            ((Ontology) (ns.getOntology())).setInternal(true);
//        }
//
//        createReasoner(monitor);
//
//        /*
//         * create an independent ontology for the non-semantic types we encounter.
//         */
//        this.nonSemanticConcepts = requireOntology("nonsemantic", INTERNAL_ONTOLOGY_PREFIX);
//    }
//
//    String importOntology(OWLOntology ontology, String resource, String namespace, boolean imported, IMonitor monitor)
//            throws KlabException {
//
//        if (!ontologies.containsKey(namespace)) {
//            ontologies.put(namespace, new Ontology(ontology, namespace));
//        }
//
//        /*
//         * seen already?
//         */
//        if (this.namespaces.containsKey(namespace)) {
//            return namespace;
//        }
//
//        Namespace ns = new Namespace(namespace, new File(resource), ontologies.get(namespace));
//
//        this.namespaces.put(namespace, ns);
//
//        return namespace;
//    }
//
//    /*
//     * the three knowledge manager methods we implement, so we can serve as delegate to a KM for
//     * these.
//     */
//    public Concept getConcept(String concept) {
//
//        Concept result = null;
//
//        if (SemanticType.validate(concept)) {
//
//            SemanticType st = new SemanticType(concept);
//
//            if (Character.isUpperCase(st.getNamespace().charAt(0))) {
//            
//                /*
//                 * authority concept
//                 */
//                result = Concepts.INSTANCE
//                        .getAuthorityConcept(Authorities.INSTANCE.getIdentity(st.getNamespace(), removeTicks(st.getName())));
//            
//            } else {
//
//                Ontology o = ontologies.get(st.getNamespace());
//                if (o == null) {
//                    OWLClass systemConcept = this.systemConcepts.get(st);
//                    if (systemConcept != null) {
//                        result = new Concept(systemConcept, st.getNamespace(), emptyType);
//                    }
//                } else {
//                    result = o.getConcept(st.getName());
//                }
//            }
//        }
//
//        return result;
//    }
//
//    private String removeTicks(String id) {
//        if (id.startsWith("'") || id.startsWith("\"")) {
//            id = id.substring(1);
//        }
//        if (id.endsWith("'") || id.endsWith("\"")) {
//            id = id.substring(0, id.length() - 1);
//        }
//        return id;
//    }
//
//    public Property getProperty(String concept) {
//
//        Property result = null;
//
//        if (SemanticType.validate(concept)) {
//            String[] conceptSpaceAndLocalName = SemanticType.splitIdentifier(concept);
//            Ontology o = ontologies.get(conceptSpaceAndLocalName[0]);
//            if (o != null) {
//                result = o.getProperty(conceptSpaceAndLocalName[1]);
//                if (result == null && !conceptSpaceAndLocalName[1].startsWith("p")) {
//                    result = o.getProperty("p" + conceptSpaceAndLocalName[1]);
//                }
//            }
//        }
//        return result;
//    }
//
//    public IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc) {
//        IConcept ret = null;
//        IConcept tmpConcept;
//
//        for (IConcept concept : cc) {
//            if (ret == null) {
//                ret = concept;
//            } else {
//                tmpConcept = ret.getLeastGeneralCommonConcept(concept);
//                if (tmpConcept != null) {
//                    ret = tmpConcept;
//                }
//            }
//        }
//
//        return ret;
//    }
//
//    /**
//     * Return the ontology for the given namespace ID (short name).
//     * 
//     * @param ns
//     * @return the ontology
//     */
//    public Ontology getOntology(String ns) {
//        return (Ontology) ontologies.get(ns);
//    }
//
//    public Concept getRootConcept() {
//        if (this.thing == null) {
//            this.thing = new Concept(manager.getOWLDataFactory().getOWLThing(), "owl", emptyType);
//        }
//        return this.thing;
//    }
//
//    public String getConceptSpace(IRI iri) {
//
//        if (iri2ns.containsKey(iri.toString())) {
//            return iri2ns.get(iri.toString());
//        }
//
//        String oIri = removeEntity(iri);
//        String ret = iri2ns.get(oIri);
//
//        if (ret == null) {
//            /*
//             * happens, whenever we depend on a concept from a server ontology not loaded yet. Must
//             * find a way to deal with this.
//             */
//            ret = MiscUtilities.getNameFromURL(oIri);
//        }
//
//        return ret;
//    }
//
//    private String removeEntity(IRI iri) {
//        String eiri = iri.toString();
//        if (eiri.contains("#")) {
//            eiri = eiri.substring(0, eiri.lastIndexOf('#'));
//        } else {
//            eiri = eiri.substring(0, eiri.lastIndexOf('/'));
//        }
//        return eiri;
//    }
//
//    /**
//     * Load OWL files from given directory and in its subdirectories, using a prefix mapper to
//     * resolve URLs internally and deriving ontology names from the relative paths. This uses the
//     * resolver passed at initialization only to create the namespace. It's only meant for core
//     * knowledge not seen by users.
//     *
//     * @param kdir
//     * @throws KlabException
//     * @throws KlabIOException
//     */
//    public void load(File kdir) throws KlabException {
//
//        AutoIRIMapper imap = new AutoIRIMapper(kdir, true);
//        manager.addIRIMapper(imap);
//
//        File[] files = kdir.listFiles();
//        // null in error
//        if (files != null) {
//            for (File fl : files) {
//                loadInternal(fl, "", false, null);
//            }
//        } else {
//            throw new KlabIOException("Errors reading core ontologies: system will be nonfunctional.");
//        }
//    }
//
//    /**
//     * 
//     * @param f
//     * @param path
//     * @param forcePath disregard directory structure and use passed path as prefix for ontology
//     * @param monitor
//     * @throws KlabException
//     */
//    private void loadInternal(File f, String path, boolean forcePath, IMonitor monitor) throws KlabException {
//
//        String pth = path == null
//                ? ""
//                : (path + (path.isEmpty() ? "" : ".") + CamelCase.toLowerCase(MiscUtilities.getFileBaseName(f.toString()), '-'));
//
//        if (forcePath) {
//            pth = path;
//        }
//
//        if (f.isDirectory()) {
//            if (!MiscUtilities.getFileBaseName(f.toString()).startsWith(".")) {
//                for (File fl : f.listFiles()) {
//                    loadInternal(fl, pth, false, monitor);
//                }
//            }
//        } else if (MiscUtilities.getFileExtension(f.toString()).equals("owl")) {
//
//            InputStream input;
//
//            try {
//                input = new FileInputStream(f);
//                OWLOntology ontology = manager.loadOntologyFromOntologyDocument(input);
//                input.close();
//                Ontology ont = new Ontology(ontology, pth);
//                ont.setResourceUrl(f.toURI().toURL().toString());
//                ontologies.put(pth, ont);
//                iri2ns.put(ont.getPrefix(), pth);
//
//            } catch (OWLOntologyAlreadyExistsException e) {
//
//                /*
//                 * already imported- wrap it and use it as is.
//                 */
//                OWLOntology ont = manager.getOntology(e.getOntologyID().getOntologyIRI());
//                if (ont != null && ontologies.get(pth) == null) {
//                    Ontology o = new Ontology(ont, pth);
//                    try {
//                        o.setResourceUrl(f.toURI().toURL().toString());
//                    } catch (MalformedURLException e1) {
//                    }
//                    ontologies.put(pth, o);
//                    iri2ns.put(ont.getOntologyID().getOntologyIRI().toString(), pth);
//                }
//
//            } catch (Exception e) {
//
//                /*
//                 * everything else is probably an error
//                 */
//                throw new KlabIOException("reading " + f + ": " + ExceptionUtils.getRootCauseMessage(e));
//            }
//
//            Ontology o = ontologies.get(pth);
//            if (o != null) {
//                /*
//                 * create namespace
//                 */
//                Namespace ns = new Namespace(pth, f, o);
//                // ns.setId(pth);
//                // ns.setResourceUrl(f.toString());
//                // ns.setOntology(o);
//                this.namespaces.put(pth, ns);
//            }
//        }
//    }
//
//    public IOntology refreshOntology(URL url, String id) throws KlabException {
//
//        InputStream input;
//        Ontology ret = null;
//
//        try {
//            input = url.openStream();
//            OWLOntology ontology = manager.loadOntologyFromOntologyDocument(input);
//            input.close();
//            ret = new Ontology(ontology, id);
//            ret.setResourceUrl(url.toString());
//            ontologies.put(id, ret);
//            iri2ns.put(ret.getPrefix(), id);
//
//        } catch (OWLOntologyAlreadyExistsException e) {
//
//            /*
//             * already imported- wrap it and use it as is.
//             */
//            OWLOntology ont = manager.getOntology(e.getOntologyID().getOntologyIRI());
//            if (ont != null && ontologies.get(id) == null) {
//                Ontology ontology = new Ontology(ont, id);
//                ontologies.put(id, ontology);
//                iri2ns.put(ontology.getPrefix(), id);
//            }
//
//        } catch (Exception e) {
//
//            /*
//             * everything else is probably an error
//             */
//            throw new KlabIOException(e);
//        }
//
//        Ontology o = ontologies.get(id);
//        if (o != null) {
//            /*
//             * create namespace
//             */
//            Namespace ns = new Namespace(id, new File(url.getFile()), o);
//            // INamespaceDefinition ns = (INamespaceDefinition) _resolver
//            // .newLanguageObject(INamespace.class, null);
//            // ns.setId(id);
//            // ns.setResourceUrl(url.toString());
//            // ns.setOntology(o);
//            this.namespaces.put(id, ns);
//        }
//
//        return ret;
//    }
//
//    public Concept getDatatypeMapping(String string) {
//        return this.xsdMappings.get(string);
//    }
//
//    public Concept registerDatatypeMapping(String xsd, Concept concept) {
//        return this.xsdMappings.put(xsd, concept);
//    }
//
//    public void releaseOntology(IOntology ontology) {
//
//        // TODO remove from _csIndex - should be harmless to leave for now
//        INamespace ns = this.namespaces.get(ontology.getName());
//        if (ns != null) {
//            this.resourceIndex.remove(ns.getLocalFile().toString());
//        }
//        this.namespaces.remove(ontology.getName());
//        ontologies.remove(ontology.getName());
//        iri2ns.remove(((Ontology) ontology).getPrefix());
//        manager.removeOntology(((Ontology) ontology).ontology);
//    }
//
//    public void clear() {
//        Collection<String> keys = new HashSet<>(ontologies.keySet());
//        for (String o : keys) {
//            releaseOntology(getOntology(o));
//        }
//    }
//
//    public Collection<IOntology> getOntologies(boolean includeInternal) {
//        ArrayList<IOntology> ret = new ArrayList<>();
//        for (IOntology o : ontologies.values()) {
//            if (((Ontology) o).isInternal() && !includeInternal)
//                continue;
//            ret.add(o);
//        }
//        return ret;
//    }
//
//    public INamespace getNamespace(String ns) {
//        return this.namespaces.get(ns);
//    }
//
//    public Collection<INamespace> getNamespaces() {
//        return this.namespaces.values();
//    }
//
//    public Collection<IConcept> listConcepts(boolean includeInternal) {
//
//        ArrayList<IConcept> ret = new ArrayList<>();
//
//        for (IOntology o : ontologies.values()) {
//            if (((Ontology) o).isInternal() && !includeInternal)
//                continue;
//            ret.addAll(o.getConcepts());
//        }
//        return ret;
//    }
//
//    public Concept getNothing() {
//        if (this.nothing == null) {
//            this.nothing = new Concept(manager.getOWLDataFactory().getOWLNothing(), "owl", EnumSet.of(Type.NOTHING));
//        }
//        return this.nothing;
//    }
//
//    public Collection<IConcept> unwrap(OWLClassExpression cls) {
//
//        Set<IConcept> ret = new HashSet<>();
//        if (cls instanceof OWLObjectIntersectionOf) {
//            for (OWLClassExpression o : ((OWLObjectIntersectionOf) cls).getOperands()) {
//                ret.addAll(unwrap(o));
//            }
//        } else if (cls instanceof OWLObjectUnionOf) {
//            for (OWLClassExpression o : ((OWLObjectUnionOf) cls).getOperands()) {
//                ret.addAll(unwrap(o));
//            }
//        } else if (cls instanceof OWLClass) {
//            ret.add(getExistingOrCreate(cls.asOWLClass()));
//        }
//        return ret;
//    }
//
//    /**
//     * Get the restricted classes only if the target concept of the restriction is the one passed.
//     * This simply returns one class - TODO improve API.
//     * 
//     * @param target
//     * @param restricted
//     * @return
//     */
//    public IConcept getDirectRestrictedClass(IConcept target, IProperty restricted) {
//        OWLClass owl = ((Concept) target)._owl;
//        synchronized (owl) {
//            for (OWLClassExpression s : owl.getSuperClasses(OWL.INSTANCE.manager.getOntologies())) {
//                if (s instanceof OWLQuantifiedRestriction) {
//                    if (getPropertyFor((OWLProperty<?, ?>) ((OWLQuantifiedRestriction<?, ?, ?>) s).getProperty()).is(restricted)
//                            && ((OWLQuantifiedRestriction<?, ?, ?>) s).getFiller() instanceof OWLClassExpression) {
//                        Collection<IConcept> concepts = unwrap(
//                                (OWLClassExpression) ((OWLQuantifiedRestriction<?, ?, ?>) s).getFiller());
//                        if (concepts != null) {
//                            return concepts.iterator().next();
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * Return all the concepts directly restricted by this property.
//     * 
//     * @param target
//     * @param restricted
//     * @return
//     */
//    public Collection<IConcept> getDirectRestrictedClasses(IConcept target, IProperty restricted) {
//        Set<IConcept> ret = new HashSet<>();
//        OWLClass owl = ((Concept) target)._owl;
//        synchronized (owl) {
//            for (OWLClassExpression s : owl.getSuperClasses(OWL.INSTANCE.manager.getOntologies())) {
//                if (s instanceof OWLQuantifiedRestriction) {
//                    if (getPropertyFor((OWLProperty<?, ?>) ((OWLQuantifiedRestriction<?, ?, ?>) s).getProperty()).is(restricted)
//                            && ((OWLQuantifiedRestriction<?, ?, ?>) s).getFiller() instanceof OWLClassExpression) {
//                        ret.addAll(unwrap((OWLClassExpression) ((OWLQuantifiedRestriction<?, ?, ?>) s).getFiller()));
//                    }
//                }
//            }
//        }
//        return ret;
//    }
//
//    /**
//     * Return the concept or concepts (if a union) restricted by the passed object property in the
//     * restriction closest to the passed concept in its asserted parent hierarchy.
//     * 
//     * @param target
//     * @param restricted
//     * @return the concepts restricted in the target by the property
//     */
//    public Collection<IConcept> getRestrictedClasses(IConcept target, IProperty restricted) {
//        return new SpecializingRestrictionVisitor(target, restricted, true).getResult();
//    }
//
//    public Collection<IConcept> getRestrictedClasses(IConcept target, IProperty restricted, boolean useSuperproperties) {
//        return new SpecializingRestrictionVisitor(target, restricted, useSuperproperties).getResult();
//    }
//
//    public void restrictSome(IConcept target, IProperty property, IConcept filler, Ontology ontology) {
//        Ontologies.INSTANCE.getTargetOntology(ontology, target, property, filler)
//                .define(Collections.singleton(Axiom.SomeValuesFrom(target.toString(), property.toString(), filler.toString())));
//    }
//
//    public void restrictAll(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers,
//            Ontology ontology) {
//
//        if (fillers.size() == 1) {
//            restrictAll(target, property, fillers.iterator().next(), ontology);
//            return;
//        }
//
//        if (!(how.equals(LogicalConnector.INTERSECTION) || how.equals(LogicalConnector.UNION))) {
//            throw new IllegalArgumentException("connectors can only be union or intersection");
//        }
//
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : fillers) {
//            classes.add(((Concept) c)._owl);
//        }
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = how.equals(LogicalConnector.UNION)
//                ? factory.getOWLObjectUnionOf(classes)
//                : factory.getOWLObjectIntersectionOf(classes);
//        OWLClassExpression restriction = factory.getOWLObjectAllValuesFrom(((Property) property)._owl.asOWLObjectProperty(),
//                union);
//        manager.addAxiom((Ontologies.INSTANCE.getTargetOntology(ontology, target, property, fillers)).ontology,
//                factory.getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
//    }
//
//    public void restrictSome(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers,
//            Ontology ontology) {
//
//        if (fillers.size() == 1) {
//            restrictSome(target, property, fillers.iterator().next(), ontology);
//            return;
//        }
//
//        if (!(how.equals(LogicalConnector.INTERSECTION) || how.equals(LogicalConnector.UNION))) {
//            throw new IllegalArgumentException("connectors can only be union or intersection");
//        }
//
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : fillers) {
//            classes.add(((Concept) c)._owl);
//        }
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = how.equals(LogicalConnector.UNION)
//                ? factory.getOWLObjectUnionOf(classes)
//                : factory.getOWLObjectIntersectionOf(classes);
//        OWLClassExpression restriction = factory.getOWLObjectSomeValuesFrom(((Property) property)._owl.asOWLObjectProperty(),
//                union);
//        manager.addAxiom(((Ontology) property.getOntology()).ontology,
//                factory.getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
//    }
//
//    public void restrictAll(IConcept target, IProperty property, IConcept filler, Ontology ontology) {
//        Ontologies.INSTANCE.getTargetOntology(ontology, target, property, filler)
//                .define(Collections.singleton(Axiom.AllValuesFrom(target.toString(), property.toString(), filler.toString())));
//    }
//
//    public void restrictAtLeast(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers, int min,
//            Ontology ontology) {
//
//        if (fillers.size() == 1) {
//            restrictAtLeast(target, property, fillers.iterator().next(), min, ontology);
//            return;
//        }
//
//        if (!(how.equals(LogicalConnector.INTERSECTION) || how.equals(LogicalConnector.UNION))) {
//            throw new IllegalArgumentException("connectors can only be union or intersection");
//        }
//
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : fillers) {
//            classes.add(((Concept) c)._owl);
//        }
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = how.equals(LogicalConnector.UNION)
//                ? factory.getOWLObjectUnionOf(classes)
//                : factory.getOWLObjectIntersectionOf(classes);
//        OWLClassExpression restriction = factory.getOWLObjectMinCardinality(min, ((Property) property)._owl.asOWLObjectProperty(),
//                union);
//        manager.addAxiom((Ontologies.INSTANCE.getTargetOntology(ontology, target, property, fillers)).ontology,
//                factory.getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
//    }
//
//    public void restrictAtLeast(IConcept target, IProperty property, IConcept filler, int min, Ontology ontology) {
//        Ontologies.INSTANCE.getTargetOntology(ontology, target, property, filler).define(
//                Collections.singleton(Axiom.AtLeastNValuesFrom(target.toString(), property.toString(), filler.toString(), min)));
//    }
//
//    public void restrictAtMost(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers, int max,
//            Ontology ontology) {
//
//        if (fillers.size() == 1) {
//            restrictAtMost(target, property, fillers.iterator().next(), max, ontology);
//            return;
//        }
//        if (!(how.equals(LogicalConnector.INTERSECTION) || how.equals(LogicalConnector.UNION))) {
//            throw new IllegalArgumentException("connectors can only be union or intersection");
//        }
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : fillers) {
//            classes.add(((Concept) c)._owl);
//        }
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = how.equals(LogicalConnector.UNION)
//                ? factory.getOWLObjectUnionOf(classes)
//                : factory.getOWLObjectIntersectionOf(classes);
//        OWLClassExpression restriction = factory.getOWLObjectMaxCardinality(max, ((Property) property)._owl.asOWLObjectProperty(),
//                union);
//        manager.addAxiom((Ontologies.INSTANCE.getTargetOntology(ontology, target, property, fillers)).ontology,
//                factory.getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
//    }
//
//    public void restrictAtMost(IConcept target, IProperty property, IConcept filler, int max, Ontology ontology) {
//        Ontologies.INSTANCE.getTargetOntology(ontology, target, property, filler).define(
//                Collections.singleton(Axiom.AtMostNValuesFrom(target.toString(), property.toString(), filler.toString(), max)));
//    }
//
//    public void restrictExactly(IConcept target, IProperty property, LogicalConnector how, Collection<IConcept> fillers,
//            int howmany, Ontology ontology) {
//
//        if (fillers.size() == 1) {
//            restrictExactly(target, property, fillers.iterator().next(), howmany, ontology);
//            return;
//        }
//        if (!(how.equals(LogicalConnector.INTERSECTION) || how.equals(LogicalConnector.UNION))) {
//            throw new IllegalArgumentException("connectors can only be union or intersection");
//        }
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : fillers) {
//            classes.add(((Concept) c)._owl);
//        }
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = how.equals(LogicalConnector.UNION)
//                ? factory.getOWLObjectUnionOf(classes)
//                : factory.getOWLObjectIntersectionOf(classes);
//        OWLClassExpression restriction = factory.getOWLObjectExactCardinality(howmany,
//                ((Property) property)._owl.asOWLObjectProperty(), union);
//        manager.addAxiom((Ontologies.INSTANCE.getTargetOntology(ontology, target, property, fillers)).ontology,
//                factory.getOWLSubClassOfAxiom(((Concept) target)._owl, restriction));
//    }
//
//    public void restrictExactly(IConcept target, IProperty property, IConcept filler, int howMany, Ontology ontology) {
//        Ontologies.INSTANCE.getTargetOntology(ontology, target, property, filler).define(Collections
//                .singleton(Axiom.ExactlyNValuesFrom(target.toString(), property.toString(), filler.toString(), howMany)));
//    }
//
//    /**
//     * Return whether the restriction on type involving concept is optional. If there is no such
//     * restriction, return false.
//     * 
//     * @param type
//     * @param concept
//     * @return true if restriction exists and is optional
//     */
//    public boolean isRestrictionOptional(IConcept type, IConcept concept) {
//        return new ConceptRestrictionVisitor(type, concept).isOptional();
//    }
//
//    /**
//     * Return whether the restriction on type involving concept is a negation. If there is no such
//     * restriction, return false.
//     * 
//     * @param type
//     * @param concept
//     * @return true if restriction exists and is a negation
//     */
//    public boolean isRestrictionDenied(IConcept type, IConcept concept) {
//        return new ConceptRestrictionVisitor(type, concept).isDenied();
//    }
//
//    public IProperty getRestrictingProperty(IConcept type, IConcept concept) {
//        ConceptRestrictionVisitor visitor = new ConceptRestrictionVisitor(type, concept);
//        if (visitor.getRestriction() != null) {
//            return getPropertyFor((OWLProperty<?, ?>) visitor.getRestriction().getProperty());
//        }
//        return null;
//    }
//
//    public IConcept getExistingOrCreate(OWLClass owl) {
//
//        String conceptId = owl.getIRI().getFragment();
//        String namespace = getConceptSpace(owl.getIRI());
//
//        IConcept ret = null;
//        IOntology ontology = ontologies.get(namespace);
//        if (ontology == null) {
//            throw new IllegalArgumentException("getExistingOrCreate: ontology not found: " + namespace);
//        }
//
//        ret = ontology.getConcept(conceptId);
//        if (ret == null) {
//            ret = ((Ontology) ontology).createConcept(conceptId, emptyType);
//        }
//
//        return ret;
//    }
//
//    public Concept getIntersection(Collection<IConcept> concepts, IOntology destination, Set<Type> stype) {
//
//        EnumSet<Type> type = EnumSet.copyOf(stype);
//        type.add(Type.INTERSECTION);
//
//        List<String> ids = new ArrayList<>();
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : concepts) {
//            classes.add(((Concept) c)._owl);
//            ids.add(c.toString().replace(':', '_'));
//        }
//
//        Collections.sort(ids);
//        String id = "";
//        for (String iid : ids) {
//            id += (id.isEmpty() ? "" : "__and__") + iid;
//        }
//
//        Concept ret = ((Ontology) destination).getConcept(id);
//        if (ret != null) {
//            return ret;
//        }
//
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = factory.getOWLObjectIntersectionOf(classes);
//        ret = (Concept) ((Ontology) destination).createConcept(id, type);
//        manager.addAxiom(((Ontology) destination).ontology, factory.getOWLSubClassOfAxiom(((Concept) ret)._owl, union));
//
//        return ret;
//    }
//
//    public Concept getUnion(Collection<IConcept> concepts, IOntology destination, Set<Type> stype) {
//
//        EnumSet<Type> type = EnumSet.copyOf(stype);
//        type.add(Type.UNION);
//
//        List<String> ids = new ArrayList<>();
//        Set<OWLClassExpression> classes = new HashSet<>();
//        for (IConcept c : concepts) {
//            classes.add(((Concept) c)._owl);
//            ids.add(c.toString().replace(':', '_'));
//        }
//
//        Collections.sort(ids);
//        String id = "";
//        for (String iid : ids) {
//            id += (id.isEmpty() ? "" : "__or__") + iid;
//        }
//
//        Concept ret = ((Ontology) destination).getConcept(id);
//        if (ret != null) {
//            return ret;
//        }
//
//        OWLDataFactory factory = manager.getOWLDataFactory();
//        OWLClassExpression union = factory.getOWLObjectUnionOf(classes);
//        ret = (Concept) ((Ontology) destination).createConcept(id, type);
//        manager.addAxiom(((Ontology) destination).ontology, factory.getOWLSubClassOfAxiom(((Concept) ret)._owl, union));
//
//        return ret;
//    }
//
//    public Concept getConsequentialityEvent(Collection<IConcept> concepts, IOntology destination) {
//        // TODO
//        return null;
//    }
//
//    public String importExternal(String url, String prefix, IMonitor monitor) throws KlabException {
//
//        // TODO must handle the situation when the prefix is already there better than
//        // this.
//
//        if (iri2ns.containsKey(url)) {
//            return iri2ns.get(url);
//        }
//
//        File out = new File(Configuration.INSTANCE.getDataPath("knowledge/.imports") + File.separator + prefix + ".owl");
//        try {
//            URLUtils.copyChanneled(new URL(url), out);
//            loadInternal(out, prefix, true, monitor);
//        } catch (MalformedURLException e) {
//            monitor.error(e);
//            return null;
//        }
//
//        return prefix;
//    }
//
//    public Concept getNonsemanticPeer(String name, IArtifact.Type type) {
//
//        String conceptId = StringUtil.capitalize(type.name().toLowerCase()) + CamelCase.toUpperCamelCase(name, '.');
//        Type qualityType = null;
//        switch(type) {
//        case TEXT:
//            qualityType = Type.CATEGORY;
//            break;
//        case NUMBER:
//            qualityType = Type.QUANTITY;
//            break;
//        case CONCEPT:
//            qualityType = Type.CLASS;
//            break;
//        case BOOLEAN:
//            qualityType = Type.PRESENCE;
//            break;
//        case OBJECT:
//            qualityType = Type.SUBJECT;
//            break;
//        case EVENT:
//            qualityType = Type.EVENT;
//            break;
//        default:
//            throw new IllegalArgumentException("wrong type passed for non-semantic peer generation: " + type);
//        }
//        EnumSet<Type> identity = type.isCountable()
//                ? EnumSet.of(Type.SUBJECT, Type.OBSERVABLE, Type.DIRECT_OBSERVABLE, Type.COUNTABLE)
//                : EnumSet.of(Type.QUALITY, Type.OBSERVABLE, qualityType);
//
//        Concept ret = nonSemanticConcepts.getConcept(conceptId);
//        if (ret != null) {
//            if (!ret.is(qualityType)) {
//                throw new KlabInternalErrorException(
//                        "non-semantic peer concept for " + name + " was declared previously with a different type");
//            }
//            return ret;
//        }
//
//        nonSemanticConcepts.define(Collections.singletonList(Axiom.ClassAssertion(conceptId, identity)));
//
//        return nonSemanticConcepts.getConcept(conceptId);
//    }
//
//    /**
//     * True if the passed object has true semantics, i.e. is not a non-semantic object.
//     * 
//     * @param observable
//     * @return
//     */
//    public boolean isSemantic(ISemantic observable) {
//        return !observable.getType().getNamespace().equals(nonSemanticConcepts.getName());
//    }
//
//    public IOntology readOntology(String string) {
//        try {
//            return new Ontology(manager.loadOntology(IRI.create(string)), MiscUtilities.getURLBaseName(string));
//        } catch (OWLOntologyCreationException e) {
//            throw new KlabIOException(e);
//        }
//    }
//
//}
