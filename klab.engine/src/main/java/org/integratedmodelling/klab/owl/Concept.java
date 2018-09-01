/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IKnowledge;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.owl.OntologyUtilities.RestrictionVisitor;
import org.integratedmodelling.klab.utils.Pair;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNaryBooleanClassExpression;
import org.semanticweb.owlapi.model.OWLObjectAllValuesFrom;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLQuantifiedRestriction;
import org.semanticweb.owlapi.model.OWLRestriction;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

/**
 * Just a wrapper for an OWL concept. Metadata are redirected to annotation properties.
 * 
 * @author Ferd
 * 
 */
public class Concept extends Knowledge implements IConcept {

    String            _id;
    String            _cs;
    OWLClass          _owl;
    private IMetadata metadata;
    Set<Type>         type = Collections.synchronizedSet(EnumSet.noneOf(Type.class));

    Concept(OWLClass c, String cs, Set<Type> type) {
        _owl = c;
        _id = c.getIRI().getFragment();
        _cs = cs;
        this.type.addAll(type);
    }

    protected Concept(Concept concept) {
        _owl = concept._owl;
        _id = concept._owl.getIRI().getFragment();
        _cs = concept._cs;
        this.type.addAll(concept.type);
    }

    @Override
    public boolean is(Type type) {
        return this.type.contains(type);
    }

    @Override
    public String getNamespace() {
        if (_owl.isTopEntity())
            return "owl";
        return _cs;
    }

    @Override
    public String getName() {
        return _id;
    }

    @Override
    public boolean is(ISemantic s) {

        IKnowledge concept = s.getType();

        if (!(concept instanceof Concept)) {
            return false;
        }

        Concept cc = (Concept) concept;

        if (cc.equals(this)) {
            return true;
        }

        Collection<IConcept> collection = getAllParents();
        collection.add(this);

        boolean ret = collection.contains(concept);

        // /*
        // * If we're aliasing an authority base identity, try that before failing.
        // */
        // if (!ret && concept.getMetadata().contains(NS.AUTHORITY_BASE_CONCEPT)) {
        // IConcept authc = KLAB.c(concept.getMetadata().getString(NS.AUTHORITY_BASE_CONCEPT));
        // if (authc != null) {
        // ret = concept.is(authc);
        // }
        // }

        return ret;
    }

    @Override
    public String getURI() {
        return _owl.getIRI().toString();
    }

    @Override
    public Ontology getOntology() {
        return OWL.INSTANCE.getOntology(getNamespace());
    }

    @Override
    public synchronized Collection<IConcept> getParents() {

        Set<IConcept> concepts = new HashSet<>();
        synchronized (_owl) {
            Set<OWLClassExpression> set = _owl.getSuperClasses(OWL.INSTANCE.manager.getOntologies());
            for (OWLClassExpression s : set) {
                if (s.equals(_owl)) {
                    Logging.INSTANCE.error("self-referential inheritance for " + this);
                    break;
                } else if (!(s.isAnonymous() || s.asOWLClass().isBuiltIn()))
                    concepts.add(OWL.INSTANCE.getExistingOrCreate(s.asOWLClass()));
            }
        }

        return concepts;
    }

    @Override
    public synchronized Collection<IConcept> getAllParents() {
        return getAllParentsInternal(new HashSet<IConcept>());
    }

    private synchronized Collection<IConcept> getAllParentsInternal(Set<IConcept> seen) {

        Set<IConcept> concepts = new HashSet<>();

        if (seen.contains(this)) {
            // System.out.println("po'dio, cycle with " + this);
            return concepts;
        }

        seen.add(this);

        for (IConcept c : getParents()) {
            concepts.add(c);
            concepts.addAll(((Concept) c).getAllParentsInternal(seen));
        }

        return concepts;
    }

    @Override
    public synchronized Collection<IConcept> getChildren() {

        Set<IConcept> concepts = new HashSet<>();
        synchronized (_owl) {
            Set<OWLClassExpression> set = _owl.getSubClasses(OWL.INSTANCE.manager.getOntologies());

            for (OWLClassExpression s : set) {
                if (!(s.isAnonymous() || s.isOWLNothing() || s.isOWLThing()))
                    concepts.add(OWL.INSTANCE.getExistingOrCreate(s.asOWLClass()));
            }
            if (set.isEmpty() && _owl.isOWLThing()) {
                for (IOntology onto : OWL.INSTANCE.ontologies.values()) {
                    concepts.addAll(onto.getConcepts());
                }
            }
        }
        return concepts;
    }

    @Override
    public synchronized Collection<IProperty> getProperties() {

        Collection<IProperty> props = getDirectProperties();
        ArrayList<Collection<IProperty>> psets = new ArrayList<>();

        for (IProperty prop : props)
            synchronized (prop) {
                psets.add(prop.getChildren());
            }

        for (Collection<IProperty> pp : psets)
            props.addAll(pp);

        return props;
    }

    public synchronized Collection<IProperty> getDirectProperties() {

        Set<IProperty> properties = new HashSet<>();
        /*
         * builtin
         */
        if (getOntology() == null)
            return properties;

        OWLOntology ontology = (getOntology()).ontology;

        synchronized (ontology) {
            for (OWLObjectProperty op : ontology.getObjectPropertiesInSignature(true)) {
                Set<OWLClassExpression> rang = op.getDomains(OWL.INSTANCE.manager.getOntologies());
                if (rang.contains(_owl))
                    properties.add(new Property(op, OWL.INSTANCE.getConceptSpace(op.getIRI())));
            }
            for (OWLDataProperty op : ontology.getDataPropertiesInSignature(true)) {
                Set<OWLClassExpression> rang = op.getDomains(OWL.INSTANCE.manager.getOntologies());
                if (rang.contains(_owl))
                    properties.add(new Property(op, OWL.INSTANCE.getConceptSpace(op.getIRI())));
            }
        }
        return properties;
    }

    @Override
    public synchronized Collection<IProperty> getAllProperties() {
        Set<IProperty> props = (Set<IProperty>) getProperties();
        for (IConcept c : getAllParents()) {
            props.addAll(c.getProperties());
        }
        return props;
    }

    /**
     * Return fillers and properties for every restriction declared on this concept.
     * 
     * @return the restrictions on this
     */
    public Collection<Pair<IConcept, IProperty>> getObjectRestrictions() {

        List<Pair<IConcept, IProperty>> ret = new ArrayList<>();
        for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getObjectRestrictions()) {

            OWLClass filler = r.getFiller() instanceof OWLClass ? (OWLClass) r.getFiller() : null;

            if (filler == null)
                continue;
            @SuppressWarnings("unchecked")
            OWLObjectPropertyExpression zz = ((OWLRestriction<?, ? extends OWLObjectPropertyExpression, ?>) r)
                    .getProperty();
            ret.add(new Pair<IConcept, IProperty>(OWL.INSTANCE
                    .getExistingOrCreate(filler), new Property(zz.asOWLObjectProperty(), OWL.INSTANCE
                            .getConceptSpace(zz.asOWLObjectProperty().getIRI()))));
        }
        return ret;
    }

    @Override
    public synchronized Collection<IProperty> findRestrictingProperty(IConcept target) {
        HashSet<IProperty> ret = new HashSet<>();

        for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getObjectRestrictions()) {

            OWLClass filler = r.getFiller() instanceof OWLClass ? (OWLClass) r.getFiller() : null;

            if (filler == null || !filler.getIRI().equals(((Concept) target)._owl.getIRI()))
                continue;
            @SuppressWarnings("unchecked")
            OWLObjectPropertyExpression zz = ((OWLRestriction<?, ? extends OWLObjectPropertyExpression, ?>) r)
                    .getProperty();
            ret.add(new Property(zz.asOWLObjectProperty(), OWL.INSTANCE
                    .getConceptSpace(zz.asOWLObjectProperty().getIRI())));
        }

        return ret;
    }

    @Override
    public synchronized Collection<IConcept> getPropertyRange(IProperty property)
            throws KlabException {

        HashSet<IConcept> ret = new HashSet<>();
        // OWLReasoner reasoner = getOntology().getReasoner();

        /*
         * start with the stated range
         */
        if (property.isObjectProperty()) {

            // if (reasoner != null) {
            // NodeSet<OWLClass> nst = reasoner
            // .getObjectPropertyRanges(((Property) property)._owl
            // .asOWLObjectProperty(), false);
            // for (OWLClass cls : nst.getFlattened()) {
            // ret.add(new Concept(cls, _manager, _manager
            // .getConceptSpace(cls.getIRI())));
            // }
            // } else {

            for (OWLClassExpression zio : ((Property) property)._owl.asOWLObjectProperty()
                    .getRanges(OWL.INSTANCE.manager.getOntologies())) {
                ret.add(OWL.INSTANCE.getExistingOrCreate(zio.asOWLClass()));
            }
            // }
        } else if (property.isLiteralProperty()) {
            // for (OWLClassExpression zio :
            // ((Property)property)._owl.asOWLDataProperty().
            // getRanges(_manager.manager.getOntologies())) {
            // ret.add(new Concept(zio.asOWLClass(), _manager));
            // }
        }
        if (property.isObjectProperty()) {

            for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getObjectRestrictions()) {

                if (!r.getObjectPropertiesInSignature().contains(((Property) property)._owl))
                    continue;

                if (r instanceof OWLObjectAllValuesFrom) {
                    ret.clear();
                    OWLClassExpression ooo = ((OWLObjectAllValuesFrom) r).getFiller();
                    if (!ooo.isAnonymous()) {
                        OWLClass zz = ooo.asOWLClass();
                        ret.add(OWL.INSTANCE.getExistingOrCreate(zz));
                    }
                    break;
                } else if (r instanceof OWLObjectSomeValuesFrom) {
                    OWLClass zz = ((OWLObjectSomeValuesFrom) r).getFiller().asOWLClass();
                    ret.add(OWL.INSTANCE.getExistingOrCreate(zz));
                }
            }
        } else {
            for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getDataRestrictions()) {

                if (!r.getDataPropertiesInSignature().contains(((Property) property)._owl))
                    continue;

                if (r instanceof OWLDataAllValuesFrom) {
                    ret.clear();
                    // ret.add(new
                    // Concept(((OWLObjectAllValuesFrom)r).getFiller().asOWLClass(),
                    // _manager));
                    break;
                } else if (r instanceof OWLDataSomeValuesFrom) {
                    // ret.add(new
                    // Concept(((OWLDataAllValuesFrom)r).getFiller().asOWLClass(),
                    // _manager));
                }
            }
        }

        return ret;
    }

    @Override
    public boolean isAbstract() {
        return type.contains(Type.ABSTRACT);
        // Object p = getMetadata().get(NS.IS_ABSTRACT);
        // return p != null && p.toString().equals("true");
    }

    @Override
    public synchronized IConcept getParent() {
        Collection<IConcept> pp = getParents();
        if (pp.size() > 1) {
            throw new IllegalArgumentException("Concept " + this
                    + " has more than one parent: cannot call getParent() on it.");
        }
        return pp.iterator().next();
    }

    @Override
    public synchronized int getPropertiesCount(String property) {
        return getProperties().size();
    }

    @Override
    public synchronized int[] getCardinality(IProperty property) {

        if (property.isFunctional())
            return new int[] { 1, 1 };

        int min = -1, max = -1;

        if (property.isObjectProperty()) {
            for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getObjectRestrictions()) {
                if (r instanceof OWLObjectExactCardinality) {
                    min = max = ((OWLObjectExactCardinality) r).getCardinality();
                    break;
                } else if (r instanceof OWLObjectMaxCardinality) {
                    max = ((OWLObjectMaxCardinality) r).getCardinality();
                } else if (r instanceof OWLObjectMinCardinality) {
                    min = ((OWLObjectMinCardinality) r).getCardinality();
                }
            }
        } else {
            for (OWLQuantifiedRestriction<?, ?, ?> r : getRestrictions().getDataRestrictions()) {
                if (r instanceof OWLDataExactCardinality) {
                    min = max = ((OWLDataExactCardinality) r).getCardinality();
                    break;
                } else if (r instanceof OWLDataMaxCardinality) {
                    max = ((OWLDataMaxCardinality) r).getCardinality();
                } else if (r instanceof OWLDataMinCardinality) {
                    min = ((OWLDataMinCardinality) r).getCardinality();
                }
            }
        }

        return new int[] { min, max };
    }

    @Override
    public IConcept getLeastGeneralCommonConcept(IConcept otherConcept) {
        IConcept ret = null;
        if (otherConcept == null) {
            ret = this;
        }
        if (is(otherConcept)) {
            ret = otherConcept;
        } else if (otherConcept.is(this)) {
            ret = this;
        } else {
            for (IConcept pp : getParents()) {
                IConcept c1 = pp.getLeastGeneralCommonConcept(otherConcept);
                if (c1 != null) {
                    ret = c1;
                    break;
                }
            }
        }
        return ret;
    }

    @Override
    public synchronized Set<IConcept> getSemanticClosure() {

        // OWLReasoner reasoner = getOntology().getReasoner();

        // if (reasoner != null) {
        //
        // HashSet<IKnowledge> ret = new HashSet<>();
        // NodeSet<OWLClass> cset = reasoner.getSubClasses(_owl, false);
        // for (OWLClass cl : cset.getFlattened()) {
        // ret.add(new Concept(cl, _manager, _manager.getConceptSpace(cl
        // .getIRI())));
        // }
        // return ret;
        // }

        Set<IConcept> ret = collectChildren(new HashSet<IConcept>());
        ret.add(this);

        return ret;
    }

    private Set<IConcept> collectChildren(Set<IConcept> hashSet) {

        for (IConcept c : getChildren()) {
            if (!hashSet.contains(c))
                ((Concept) c).collectChildren(hashSet);
            hashSet.add(c);
        }
        return hashSet;
    }

    @Override
    public IMetadata getMetadata() {
        if (this.metadata == null) {
            Ontology ontology = getOntology();
            if (ontology == null) {
                this.metadata = new org.integratedmodelling.klab.data.Metadata();
            }
            this.metadata = new OWLMetadata(_owl, ontology.ontology);
            IKimObject object = Resources.INSTANCE.getModelObject(getUrn());
            if (object instanceof IConceptDefinition
                    && ((IConceptDefinition) object).getStatement().getMetadata() != null) {
                this.metadata.putAll(((IConceptDefinition) object).getStatement().getMetadata().getData());
            }
        }
        return this.metadata;
    }

    public synchronized RestrictionVisitor getRestrictions() {
        RestrictionVisitor visitor = new RestrictionVisitor(OWL.INSTANCE.manager.getOntologies());
        if (getOntology() == null) {
            return visitor;
        }
        for (OWLSubClassOfAxiom ax : getOntology().ontology.getSubClassAxiomsForSubClass(_owl)) {
            ax.getSuperClass().accept(visitor);
        }
        return visitor;
    }

    public synchronized Map<IProperty, String> getAnnotations() {
        HashMap<IProperty, String> ret = new HashMap<>();
        if (getOntology() == null)
            return ret;
        for (OWLAnnotation annotation : _owl.getAnnotations(getOntology().ontology)) {
            OWLLiteral l = (OWLLiteral) annotation.getValue();
            ret.put(new Property(annotation.getProperty(), OWL.INSTANCE
                    .getConceptSpace(annotation.getProperty().getIRI())), l.getLiteral());
        }
        return ret;
    }

    @Override
    public String toString() {
        return getNamespace() + ":" + _id;
    }

    @Override
    public Object getValueOf(IProperty property) throws KlabException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public synchronized Collection<IConcept> getDisjointConcreteChildren() {

        Set<IConcept> ret = new HashSet<>();
        Set<IConcept> bad = new HashSet<>();

        for (IKnowledge c : getSemanticClosure()) {
            if (!c.isAbstract()) {
                ret.add((IConcept) c);
            }
        }

        for (IConcept c1 : ret) {
            for (IConcept c2 : ret) {
                if (c1.is(c2) || c2.is(c1)) {
                    bad.add(c1);
                    bad.add(c2);
                }
            }
        }

        for (IConcept b : bad) {
            ret.remove(b);
        }

        return ret;
    }

    @Override
    public IConcept getType() {
        return this;
    }

    public Set<Type> getTypeSet() {
        return type;
    }

    public OWLClassExpression getOWLClass() {
        return _owl;
    }

    @Override
    public String getDefinition() {
        String def = getMetadata().get(NS.CONCEPT_DEFINITION_PROPERTY, String.class);
        return def == null ? (getNamespace() + ":" + _id) : def;
    }

    /**
     * Return the asserted definition in parentheses if the concept is composed, or the simple
     * definition otherwise. Used when composing definitions to avoid lots of branching.
     * 
     * @return the asserted definition
     */
    public String getAssertedDefinition() {
        String def = getMetadata().get(NS.CONCEPT_DEFINITION_PROPERTY, String.class);
        if (def == null) {
            def = toString();
        } else {
            def = "(" + def + ")";
        }
        return def;
    }

    @Override
    public String getUrn() {
        return getNamespace() + ":" + _id;
    }

    @Override
    public List<IConcept> getOperands() {
        
        List<IConcept> ret = new ArrayList<>();
        if (type.contains(Type.UNION) || type.contains(Type.INTERSECTION)) {
            Set<OWLClassExpression> set = _owl.getSuperClasses(OWL.INSTANCE.manager.getOntologies());
            for (OWLClassExpression s : set) {
                if (s instanceof OWLNaryBooleanClassExpression) {
                    for (OWLClassExpression cls : ((OWLNaryBooleanClassExpression)s).getOperandsAsList()) {
                        if (cls instanceof OWLClass) {
                          ret.add(OWL.INSTANCE.getExistingOrCreate(cls.asOWLClass()));
                        }
                    }
                }
            }
            
        } else {
            ret.add(this);
        }

        return ret;
    }

}
