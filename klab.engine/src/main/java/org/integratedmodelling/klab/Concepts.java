/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Pattern;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.kim.model.DefaultVisitor;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.KimConcept;
import org.integratedmodelling.klab.api.knowledge.IAuthority.Identity;
import org.integratedmodelling.klab.api.knowledge.IAxiom;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProperty;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.runtime.IScript;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IConceptService;
import org.integratedmodelling.klab.common.SemanticType;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.engine.resources.CoreOntology.NS;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.owl.Axiom;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.owl.KimKnowledgeProcessor;
import org.integratedmodelling.klab.owl.OWL;
import org.integratedmodelling.klab.owl.Ontology;
import org.integratedmodelling.klab.owl.Property;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.springframework.util.StringUtils;

import com.google.common.collect.Sets;

/**
 * The Enum Concepts.
 */
public enum Concepts implements IConceptService {

	INSTANCE;

	private Set<String> authorityOntologyIds = Collections.synchronizedSet(new HashSet<>());

	private Concepts() {
		Services.INSTANCE.registerService(this, IConceptService.class);
	}

	@Override
	public IProperty getProperty(String propertyId) {
		return OWL.INSTANCE.getProperty(propertyId);
	}

	@Override
	public Concept getConcept(String conceptId) {
		return OWL.INSTANCE.getConcept(conceptId);
	}

	@Override
	public KimConcept getDeclaration(IConcept concept) {
		Ontology ontology = OWL.INSTANCE.getOntology(concept.getNamespace());
		String definition = concept.getDefinition();
		if (ontology == null || definition == null) {
			IKimObservable obs = Kim.INSTANCE.declare(concept.getName());
			return obs == null ? null : (KimConcept) obs.getMain();
		}
		return declare(definition);
	}

	@Override
	public KimConcept declare(String declaration) {
		/*
		 * declarations that are pure semantic types for internal ontologies are
		 * possible with authority and core concepts. Check first if it's a semantic
		 * type with a namespace that matches either of these; if so, override the
		 * parser as the concepts are not k.IM-specified.
		 */
		if (SemanticType.validate(declaration)) {
			SemanticType st = SemanticType.create(declaration);
			if (CoreOntology.CORE_ONTOLOGY_NAME.equals(st.getNamespace())
					|| authorityOntologyIds.contains(st.getNamespace())) {
				return new KimConcept(declaration);
			}
		}
		IKimObservable ret = Observables.INSTANCE.parseDeclaration(declaration);
		if (ret != null) {
			return (KimConcept) ret.getMain();
		}
		return null;
	}

	@Override
	public synchronized IConcept declare(IKimConcept conceptDefinition) {
		return KimKnowledgeProcessor.INSTANCE.declare(conceptDefinition, Reasoner.INSTANCE.getOntology(),
				Klab.INSTANCE.getRootMonitor());
	}

	@Override
	public Object getMetadata(IConcept concept, String field) {
		Object ret = concept.getMetadata().get(field);
		if (ret != null) {
			return ret;
		}
		for (IConcept c : concept.getParents()) {
			ret = getMetadata(c, field);
			if (ret != null) {
				return ret;
			}
		}
		return null;
	}

	/**
	 * Quick static way to obtain a concept that is known to exist. Throws an
	 * unchecked exception if the concept isn't found.
	 *
	 * @param conceptId the concept id
	 * @return the concept. Never null.
	 */
	public static Concept c(String conceptId) {

		if (conceptId == null || conceptId.isEmpty()) {
			return null;
		}

		Concept ret = OWL.INSTANCE.getConcept(conceptId);
		if (ret == null) {
			throw new KlabResourceNotFoundException("cannot find concept " + conceptId);
		}
		return ret;

	}

	/**
	 * Quick static way to obtain a property that is known to exist. Throws an
	 * unchecked exception if the property isn't found.
	 *
	 * @param propertyId the property id
	 * @return the property. Never null.
	 */
	public static Property p(String propertyId) {

		if (propertyId == null || propertyId.isEmpty()) {
			return null;
		}

		Property ret = OWL.INSTANCE.getProperty(propertyId);
		if (ret == null) {
			throw new KlabResourceNotFoundException("cannot find property " + propertyId);
		}
		return ret;

	}

	@Override
	public String getDisplayName(ISemantic k) {
		if (k instanceof IObservable) {
			return getDisplayName((IObservable) k);
		}
		return getDisplayName(k.getType());
	}

	private String getDisplayName(IConcept t) {

		String ret = t.getMetadata().get(NS.DISPLAY_LABEL_PROPERTY, String.class);

		if (ret == null) {
			ret = t.getMetadata().get(IMetadata.DC_LABEL, String.class);
		}
		if (ret == null) {
			ret = t.getName();
		}
		if (ret.startsWith("i")) {
			ret = ret.substring(1);
		}
		return ret;
	}

	private String getDisplayName(IObservable o) {

		String ret = getDisplayName(o.getType());

		for (Pair<ValueOperator, Object> operator : o.getValueOperators()) {

			ret += StringUtils.capitalize(operator.getFirst().declaration.replace(' ', '_'));

			if (operator.getSecond() instanceof IConcept) {
				ret += getDisplayName((IConcept) operator.getSecond());
			} else if (operator.getSecond() instanceof IObservable) {
				ret += getDisplayName((IObservable) operator.getSecond());
			} else {
				ret += "_" + operator.getSecond().toString().replace(' ', '_');
			}
		}
		return ret;
	}

	@Override
	public String getDisplayLabel(ISemantic k) {
		if (k instanceof IObservable) {
			return getDisplayLabel((IObservable) k);
		}
		return getDisplayLabel(k.getType());
	}

	private String getDisplayLabel(IConcept t) {
		String ret = getDisplayName(t);
		if (!ret.contains(" ")) {
			ret = StringUtils.capitalize(CamelCase.toLowerCase(ret, ' '));
		}
		return ret;
	}

	private String getDisplayLabel(IObservable t) {
		String ret = getDisplayName(t);
		if (!ret.contains(" ")) {
			ret = StringUtils.capitalize(CamelCase.toLowerCase(ret, ' '));
		}
		return ret;
	}

	/**
	 * Arrange a set of concepts into the collection of the most specific members of
	 * each concept hierarchy therein. Return one concept or null.
	 * 
	 * @param cc
	 * @return least general
	 */
	@Override
	public IConcept getLeastGeneralConcept(Collection<IConcept> cc) {
		Collection<IConcept> z = getLeastGeneral(cc);
		return z.size() > 0 ? z.iterator().next() : null;
	}

	@Override
	public IConcept getLeastGeneralCommonConcept(IConcept concept1, IConcept c) {
		return concept1.getLeastGeneralCommonConcept(c);
	}

	@Override
	public IConcept getLeastGeneralCommonConcept(Collection<IConcept> cc) {

		IConcept ret = null;
		Iterator<IConcept> ii = cc.iterator();

		if (ii.hasNext()) {

			ret = ii.next();

			if (ret != null)
				while (ii.hasNext()) {
					ret = ret.getLeastGeneralCommonConcept(ii.next());
					if (ret == null)
						break;
				}
		}

		return ret;
	}

	/**
	 * Arrange a set of concepts into the collection of the most specific members of
	 * each concept hierarchy therein.
	 * 
	 * @param cc
	 * @return least general
	 */
	@Override
	public Collection<IConcept> getLeastGeneral(Collection<IConcept> cc) {

		Set<IConcept> ret = new HashSet<>();
		for (IConcept c : cc) {
			List<IConcept> ccs = new ArrayList<>(ret);
			boolean set = false;
			for (IConcept kn : ccs) {
				if (c.is(kn)) {
					ret.remove(kn);
					ret.add(c);
					set = true;
				} else if (kn.is(c)) {
					set = true;
				}
			}
			if (!set) {
				ret.add(c);
			}
		}
		return ret;
	}

	/**
	 * True if concept was declared in k.IM at root level. These serve as the
	 * "official" least general "family" of concepts for several purposes - e.g. for
	 * traits, only these are seen as "general" enough to be used in an "exposes"
	 * statement.
	 *
	 * @param tr the tr
	 * @return true if the concept was declared in a root-level k.IM statement.
	 */
	public boolean isBaseDeclaration(IConcept tr) {
		return tr.getMetadata().get(NS.BASE_DECLARATION) != null;
	}

	@Override
	public int getAssertedDistance(IConcept from, IConcept to) {
		if (from.equals(to)) {
			return 0;
		}
		int ret = 1;
		while (true) {
			Collection<IConcept> parents = from.getParents();
			if (parents.isEmpty()) {
				break;
			}
			if (parents.contains(to)) {
				return ret;
			}
			for (IConcept parent : parents) {
				int d = getAssertedDistance(from, parent);
				if (d >= 0) {
					return ret + d;
				}
			}
			ret++;
		}
		return -1;
	}

	/**
	 * Utility to check if a collection of concepts contains at least a concept of
	 * the passed type.
	 *
	 * @param concepts
	 * @param type
	 * @return
	 */
	public boolean is(Collection<IConcept> concepts, Type type) {
		for (IConcept concept : concepts) {
			if (concept.is(type)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Collect all the conceptual components of a concept that match the passed
	 * types, visiting all concepts referenced by the type. Can be used to obtain
	 * everything that is abstract, traits, roles etc., in terms of the original
	 * declaration.
	 * 
	 * @param observable
	 * @param types
	 * @return
	 */
	public Set<IConcept> collectComponents(IConcept observable, Set<Type> types) {
		Set<IConcept> ret = new HashSet<>();
		if (!OWL.INSTANCE.isSemantic(observable)) {
			return ret;
		}
		IKimConcept peer = declare(observable.getDefinition());
		peer.visit(new DefaultVisitor() {
			@Override
			public void visitReference(String conceptName, EnumSet<Type> type, IKimConcept validParent) {
				IConcept cn = c(conceptName);
				if (cn != null && Sets.intersection(type, types).size() == types.size()) {
					ret.add(cn);
				}
			}
		});
		return ret;
	}

	@Override
	public IConcept replaceComponent(IConcept original, Map<IConcept, IConcept> replacements) {

		if (replacements.isEmpty()) {
			return original;
		}

		String declaration = original.getDefinition();
		for (IConcept key : replacements.keySet()) {
			String rep = replacements.get(key).toString();
			if (rep.contains(" ")) {
				rep = "(" + rep + ")";
			}
			declaration = declaration.replace(key.toString(), rep);
		}

		return declare(declare(declaration));
	}

	@Override
	public int compareSpecificity(IConcept c1, IConcept c2, boolean useBaseTrait) {

		if (c1.equals(c2)) {
			return 0;
		}

		IConcept common = null;
		if (useBaseTrait) {
			IConcept common1 = Traits.INSTANCE.getBaseParentTrait(c1);
			IConcept common2 = Traits.INSTANCE.getBaseParentTrait(c2);
			if (common1 == null || common2 == null || !common1.equals(common2)) {
				return Integer.MIN_VALUE;
			}
			common = common1;
		} else {
			common = getLeastGeneralCommonConcept(c1, c2);
		}
		if (common == null) {
			return Integer.MIN_VALUE;
		}

		int d1 = getAssertedDistance(c1, common);
		int d2 = getAssertedDistance(c2, common);

		return (d1 < 0 || d2 < 0) ? Integer.MIN_VALUE : d1 - d2;
	}

	public void index(IKimConceptStatement object, String namespaceId, IMonitor monitor) {
		if (monitor.getIdentity().getParentIdentity(IScript.class) == null) {
			// check should be unnecessary as scripts can't declare concepts, but there may
			// be exceptions
			Indexer.INSTANCE.index(object, namespaceId);
		}
	}

	/**
	 * Utility to filter a concept list
	 * 
	 * @param concepts
	 * @param concept
	 * @return collection without the concepts and the concepts removed
	 */
	public Pair<Collection<IConcept>, Collection<IConcept>> copyWithout(Collection<IConcept> concepts,
			IConcept concept) {
		Set<IConcept> ret = new HashSet<>();
		Set<IConcept> rem = new HashSet<>();
		for (IConcept c : concepts) {
			if (!c.equals(concept)) {
				ret.add(c);
			} else {
				rem.add(c);
			}
		}
		return new Pair<>(ret, rem);
	}

	/**
	 * Utility to filter a concept list
	 * 
	 * @param concepts
	 * @param concept
	 * @return
	 */
	public Pair<Collection<IConcept>, Collection<IConcept>> copyWithoutAny(Collection<IConcept> concepts,
			IConcept concept) {
		Set<IConcept> ret = new HashSet<>();
		Set<IConcept> rem = new HashSet<>();
		for (IConcept c : concepts) {
			if (!c.is(concept)) {
				ret.add(c);
			} else {
				rem.add(c);
			}
		}
		return new Pair<>(ret, rem);
	}

	/**
	 * Utility to filter a concept list
	 * 
	 * @param concepts
	 * @param concept
	 * @return
	 */
	public Pair<Collection<IConcept>, Collection<IConcept>> copyWithoutAny(Collection<IConcept> concepts,
			IKimConcept.Type concept) {
		Set<IConcept> ret = new HashSet<>();
		Set<IConcept> rem = new HashSet<>();
		for (IConcept c : concepts) {
			if (!c.is(concept)) {
				ret.add(c);
			} else {
				rem.add(c);
			}
		}
		return new Pair<>(ret, rem);
	}

	public String getCssClass(IConcept concept) {

		switch (Kim.INSTANCE.getFundamentalType(((Concept) concept).getTypeSet())) {
		case QUALITY:
			return "text-sem-quality";
		case SUBJECT:
		case AGENT:
			return "text-sem-subject";
		case EVENT:
			return "text-sem-event";
		case CONFIGURATION:
			return "text-sem-configuration";
		case DOMAIN:
			return "text-sem-domain";
		case RELATIONSHIP:
			return "text-sem-relationship";
		case EXTENT:
			return "text-sem-extent";
		case PROCESS:
			return "text-sem-process";
		case ATTRIBUTE:
			return "text-sem-attribute";
		case REALM:
			return "text-sem-realm";
		case IDENTITY:
			return "text-sem-identity";
		case ROLE:
			return "text-sem-role";
		default:
			break;
		}
		return null;
	}

	public String getCodeName(IConcept main) {
		return CamelCase.toLowerCase(getDisplayName(main), '_');
	}

	static Pattern internalConceptPattern = Pattern.compile("[A-Z]+_[0-9]+");

	public boolean isDerived(IConcept c) {
		return internalConceptPattern.matcher(c.getName()).matches();
	}

	/**
	 * Brute-force check for transitive dependency within a set of concepts. Result
	 * is independent of whether the concepts are declared disjoint or not.
	 * 
	 * @param keySet
	 * @return
	 */
	public boolean isTransitivelyIndependent(Collection<IConcept> keySet) {

		Set<IConcept> keys2 = new HashSet<>(keySet);
		for (IConcept c1 : keySet) {
			for (IConcept c2 : keys2) {
				if (!c1.equals(c2) && (c1.is(c2) || c2.is(c1))) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean isInternal(IConcept c) {
		return CoreOntology.CORE_ONTOLOGY_NAME.equals(c.getNamespace());
	}

	public Concept getAuthorityConcept(Identity identity) {

		if (identity == null) {
			return null;
		}

		for (Notification notification : identity.getNotifications()) {
			if (Level.SEVERE.getName().equals(notification.getLevel())) {
				return null;
			}
		}

		String oid = Path.getFirst(identity.getAuthorityName(), ".").toLowerCase();
		boolean isNew = OWL.INSTANCE.getOntology(oid) == null;
		Ontology ontology = OWL.INSTANCE.requireOntology(oid, OWL.INTERNAL_ONTOLOGY_PREFIX);

		if (isNew) {
			ontology.setInternal(true);
			Reasoner.INSTANCE.addOntology(ontology);
			authorityOntologyIds.add(oid);
		}

		Concept ret = ontology.getConcept(identity.getConceptName());
		if (ret == null) {

			List<IAxiom> axioms = new ArrayList<>();
			EnumSet<Type> type = Kim.INSTANCE.getType("identity", null);
			type.add(Type.AUTHORITY_IDENTITY);

			// lookup parent if any; otherwise ensure we have a suitable parent
			String baseIdentity = identity.getBaseIdentity();
			if (baseIdentity == null) {
				baseIdentity = StringUtils.capitalize(oid.toLowerCase()) + "Identity";
			} else {
				// TODO recursively resolve the base identity
				throw new KlabUnimplementedException(
						"explicit base identities for authority concepts are still unimplemented");
			}

			String pName = "is" + baseIdentity;
			Concept base = ontology.getConcept(baseIdentity);
			if (base == null) {
				axioms.add(Axiom.ClassAssertion(baseIdentity, type));
				// TODO check - there should be a base identity per leaf vocabulary
				axioms.add(Axiom.AnnotationAssertion(baseIdentity, NS.BASE_DECLARATION, "true"));
				axioms.add(
						Axiom.AnnotationAssertion(baseIdentity, NS.AUTHORITY_ID_PROPERTY, identity.getAuthorityName()));
				axioms.add(Axiom.AnnotationAssertion(baseIdentity, NS.REFERENCE_NAME_PROPERTY,
						"auth_" + identity.getAuthorityName().toLowerCase() + "_" + baseIdentity.toLowerCase()));
				axioms.add(Axiom.ObjectPropertyAssertion(pName));
				axioms.add(Axiom.ObjectPropertyRange(pName, baseIdentity));
				axioms.add(Axiom.SubObjectProperty(NS.HAS_IDENTITY_PROPERTY, pName));
				axioms.add(Axiom.AnnotationAssertion(baseIdentity, NS.TRAIT_RESTRICTING_PROPERTY, oid + ":" + pName));
			}

			// if we created the parent, add the restricting property and prepare to
			// give it to the new identity

			axioms.add(Axiom.ClassAssertion(identity.getConceptName(), type));
			axioms.add(Axiom.SubClass(baseIdentity, identity.getConceptName()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), NS.REFERENCE_NAME_PROPERTY, "auth_"
					+ identity.getAuthorityName().toLowerCase() + "_" + identity.getConceptName().toLowerCase()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), IMetadata.DC_LABEL, identity.getLabel()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), NS.DISPLAY_LABEL_PROPERTY,
					identity.getLabel()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), NS.AUTHORITY_ID_PROPERTY,
					identity.getAuthorityName()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), IMetadata.DC_COMMENT,
					identity.getDescription()));
			axioms.add(Axiom.AnnotationAssertion(identity.getConceptName(), NS.CONCEPT_DEFINITION_PROPERTY,
					identity.getLocator()));

			ontology.define(axioms);

			ret = ontology.getConcept(identity.getConceptName());
		}

		return ret;
	}

	public IConcept or(Collection<IConcept> concepts) {
		/*
		 * TODO validate the types and choose the ontology more intelligently TODO add
		 * the definition
		 */
		return OWL.INSTANCE.getUnion(concepts, concepts.iterator().next().getOntology(),
				((Concept) concepts.iterator().next()).getTypeSet());
	}

	public IConcept and(Collection<IConcept> concepts) {
		/*
		 * TODO validate the types and choose the ontology more intelligently TODO add
		 * the definition
		 */
		return OWL.INSTANCE.getIntersection(concepts, concepts.iterator().next().getOntology(),
				((Concept) concepts.iterator().next()).getTypeSet());
	}

	/**
	 * Split all the unary operator chain from an observable and return the
	 * operators applied in sequence and the naked concept that the last operator
	 * was applied to.
	 * 
	 * @return
	 */
	public Pair<IConcept, List<IKimConcept.Type>> splitOperators(IConcept concept) {

		IConcept cret = concept;
		List<Type> types = new ArrayList<>();
		Set<Type> type = Sets.intersection(((Concept) concept).getTypeSet(), IKimConcept.OPERATOR_TYPES);

		while (type.size() > 0) {
			types.add(type.iterator().next());
			IConcept ccret = Observables.INSTANCE.getDescribedType(cret);
			if (ccret == null) {
				break;
			} else {
				cret = ccret;
			}
			type = Sets.intersection(((Concept) cret).getTypeSet(), IKimConcept.OPERATOR_TYPES);
		}

		return new Pair<>(cret, types);
	}

	/**
	 * Turn the passed object into a concept when it clearly represents one, or
	 * return null. For now accepting strings and groovy Concept peers as well as
	 * the trivial case.
	 * 
	 * @param abstractPredicate
	 * @return
	 */
	public IConcept asConcept(Object predicate) {

		if (predicate instanceof IConcept) {
			return (IConcept) predicate;
		} /*
			 * else if (predicate instanceof
			 * org.integratedmodelling.klab.extensions.groovy.model.Concept) { return
			 * (IConcept) ((org.integratedmodelling.klab.extensions.groovy.model.Concept)
			 * predicate).getConcept(); }
			 */else if (predicate instanceof String) {
			return declare(declare(predicate.toString()));
		}

		return null;
	}

	/**
	 * Return all atomic components of a concept, recursing any logical combination
	 * irrespective of the operator.
	 * 
	 * @param trigger
	 * @return
	 */
	public Collection<IConcept> flattenOperands(IConcept trigger) {
		Set<IConcept> ret = new HashSet<>();
		_flattenOperands(trigger, ret);
		return ret;
	}

	void _flattenOperands(IConcept trigger, Set<IConcept> ret) {
		if (trigger.is(Type.INTERSECTION) || trigger.is(Type.UNION)) {
			for (IConcept tr : trigger.getOperands()) {
				_flattenOperands(tr, ret);
			}
		} else {
			ret.add(trigger);
		}
	}

}
