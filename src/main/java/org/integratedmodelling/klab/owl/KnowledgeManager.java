///*******************************************************************************
// * Copyright (C) 2007, 2015:
// * 
// * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any
// * other authors listed in @author annotations
// *
// * All rights reserved. This file is part of the k.LAB software suite, meant to enable
// * modular, collaborative, integrated development of interoperable data and model
// * components. For details, see http://integratedmodelling.org.
// * 
// * This program is free software; you can redistribute it and/or modify it under the terms
// * of the Affero General Public License Version 3 or any later version.
// *
// * This program is distributed in the hope that it will be useful, but without any
// * warranty; without even the implied warranty of merchantability or fitness for a
// * particular purpose. See the Affero General Public License for more details.
// * 
// * You should have received a copy of the Affero General Public License along with this
// * program; if not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite
// * 330, Boston, MA 02111-1307, USA. The license is also available at:
// * https://www.gnu.org/licenses/agpl.html
// *******************************************************************************/
//package org.integratedmodelling.klab.owl;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.MalformedURLException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import org.integratedmodelling.api.factories.IKnowledgeFactory;
//import org.integratedmodelling.api.knowledge.IAuthority;
//import org.integratedmodelling.api.knowledge.IConcept;
//import org.integratedmodelling.api.knowledge.IKnowledge;
//import org.integratedmodelling.api.knowledge.IKnowledgeIndex;
//import org.integratedmodelling.api.knowledge.IOntology;
//import org.integratedmodelling.api.knowledge.IProperty;
//import org.integratedmodelling.api.modelling.INamespace;
//import org.integratedmodelling.common.client.referencing.BookmarkManager;
//import org.integratedmodelling.common.configuration.KLAB;
//import org.integratedmodelling.common.indexing.KnowledgeIndex;
//import org.integratedmodelling.common.interfaces.OWLManager;
//import org.integratedmodelling.common.vocabulary.NS;
//import org.integratedmodelling.common.vocabulary.authority.AuthorityFactory;
//import org.integratedmodelling.exceptions.KlabException;
//import org.integratedmodelling.exceptions.KlabIOException;
//
///**
// * Knowledge manager for client library, which will not allow any operation but will
// * create blindly any concept and ontology that it's asked to produce. Used to parse
// * models where all concepts defined are expected to be created. Don't use improperly.
// * 
// * TODO implement all the concept, property, ontology and reasoning functions using OWLAPI
// * 2.0 and use as delegate for the KM in thinklab. We need reasoning in the client if we
// * want any meaningful way to organize concepts.
// * 
// * @author Ferd
// *
// */
//public class KnowledgeManager implements IKnowledgeFactory,
//        OWLManager {
//
//    private OWL             manager;
//    private KnowledgeIndex  index;
//    private BookmarkManager bookmarkManager;
//
//    public KnowledgeManager() throws KlabException {
//
//        KLAB.KM = this;
//
//        try {
//            manager = new OWL(KLAB.CONFIG.getDataPath("knowledge"));
//        } catch (Exception e) {
//            throw new KlabIOException(e);
//        }
//        manager.createReasoner();
//    }
//
//    /**
//     * Prepare for use, by creating the knowledge index and the bookmark manager. May be
//     * called after engine clean, so reentrant.
//     * 
//     * @throws KlabException
//     */
//    public void initialize() throws KlabException {
//        if (index == null) {
//            index = new KnowledgeIndex("index.client");
//        }
//        if (bookmarkManager == null) {
//            bookmarkManager = new BookmarkManager();
//            bookmarkManager.restoreBookmarks();
//        }
//    }
//
//    @Override
//    public BookmarkManager getBookmarkManager() {
//        return bookmarkManager;
//    }
//
//    @Override
//    public IKimConcept getConcept(String concept) {
//        return manager.getConcept(concept);
//    }
//
//    @Override
//    public IProperty getProperty(String prop) {
//        return manager.getProperty(prop);
//    }
//
//    @Override
//    public IKnowledge getKnowledge(String k) {
//        IKnowledge ret = getConcept(k);
//        if (ret == null) {
//            ret = getProperty(k);
//        }
//        return ret;
//    }
//
//    public void loadKnowledge(File directory) throws KlabException {
//        manager.load(directory);
//    }
//
//    public IOntology requireOntology(String id, String ontologyNamespacePrefix) {
//        return manager.requireOntology(id, ontologyNamespacePrefix);
//    }
//
//    public void releaseOntology(IOntology ontology) {
//        manager.releaseOntology(ontology);
//    }
//
//    @Override
//    public IKimConcept getRootConcept() {
//        return manager.getRootConcept();
//    }
//
//    @Override
//    public IKimConcept getNothing() {
//        return manager.getNothing();
//    }
//
//    @Override
//    public OWL getOWLManager() {
//        return manager;
//    }
//
//    @Override
//    public IKimNamespace getCoreNamespace(String ns) {
//        return manager.getNamespace(ns);
//    }
//
//    // @Override
//    // public IAuthority getAuthority(String id) {
//    // return AuthorityFactory.get().getAuthority(id);
//    // }
//
//    @Override
//    public IOntology requireOntology(String id) {
//        return requireOntology(id, "http://integratedmodelling.org/ks");
//    }
//
//    @Override
//    public IOntology refreshOntology(URL url, String name)
//            throws KlabException {
//        return manager.refreshOntology(url, name);
//    }
//
//    @Override
//    public boolean releaseOntology(String s) {
//        if (OWL.getOntology(s) != null) {
//            manager.releaseOntology(getOntology(s));
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public void releaseAllOntologies() {
//        manager.clear();
//    }
//
//    @Override
//    public IOntology getOntology(String ontName) {
//        return OWL.getOntology(ontName);
//    }
//
//    @Override
//    public Collection<IOntology> getOntologies(boolean includeInternal) {
//        return manager.getOntologies(includeInternal);
//    }
//
//    @Override
//    public IOntology createOntology(String id, String ontologyPrefix)
//            throws KlabException {
//        return manager.requireOntology(id, ontologyPrefix);
//    }
//
//    @Override
//    public Collection<IKimConcept> getRootConcepts() {
//
//        ArrayList<IKimConcept> ret = new ArrayList<>();
//        for (IOntology onto : getOntologies(true)) {
//            for (IKimConcept c : onto.getConcepts()) {
//                Collection<IKimConcept> pp = c.getParents();
//                if (pp.size() == 0
//                        || (pp.size() == 1 && pp.iterator().next()
//                                .is(getRootConcept())))
//                    ret.add(c);
//            }
//        }
//        return ret;
//
//    }
//
//    @Override
//    public Collection<IKimConcept> getConcepts() {
//        ArrayList<IKimConcept> ret = new ArrayList<>();
//        for (IOntology onto : getOntologies(true)) {
//            for (IKimConcept c : onto.getConcepts()) {
//                ret.add(c);
//            }
//        }
//        return ret;
//    }
//
//    @Override
//    public File exportOntology(String ontologyId) throws KlabException {
//
//        IOntology ontology = getOntology(ontologyId);
//
//        if (ontology == null)
//            return null;
//
//        if (((Ontology) ontology).getResourceUrl() != null) {
//            try {
//                URL url = new URL(((Ontology) ontology).getResourceUrl());
//                if (url.getProtocol().startsWith("file")) {
//                    return new File(url.getFile());
//                }
//            } catch (MalformedURLException e) {
//                // just move on
//            }
//        }
//
//        File ret;
//        try {
//            ret = File.createTempFile("ont", "owl");
//        } catch (IOException e) {
//            throw new KlabIOException(e);
//        }
//
//        if (!ontology.write(ret, true))
//            return null;
//
//        return ret;
//    }
//
//    @Override
//    public IKnowledgeIndex getIndex() {
//        return index;
//    }
//
//    @Override
//    public IAuthority<?> getAuthorityFor(IKimConcept concept) {
//        String s = concept.getMetadata().getString(NS.AUTHORITY_ID_PROPERTY);
//        if (s != null) {
//            return AuthorityFactory.get().getAuthorityNamed(s);
//        }
//        return null;
//    }
//
//    @Override
//    public IProperty getProperty(IKimConcept knowledge) {
//        String s = "p" + knowledge.getLocalName();
//        return knowledge.getOntology().getProperty(s);
//    }
//}
