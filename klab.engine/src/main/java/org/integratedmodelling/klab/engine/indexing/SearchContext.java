package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.api.services.IIndexingService.Match.Type;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class SearchContext implements IIndexingService.Context {

	// these are in OR - anything matching any of these is acceptable. No
	// constraints means everything is acceptable.
	private List<Constraint> constraints = new ArrayList<>();
	private Set<Type> constraintTypes = EnumSet.noneOf(Type.class);
	private SearchContext parent = null;

	public static Context createNew() {
		SearchContext ret = new SearchContext();
		// first context can select operators, non-abstract traits or non-abstract
		// observables
		ret.constraints.add(Constraint.allPrefixOperators());
		ret.constraints.add(Constraint.allTraits(false));
		ret.constraints.add(Constraint.allObservables(false));
		return ret;
	}

	private SearchContext() {
	}

	private SearchContext(SearchContext parent) {
		this.parent = parent;
	}

	static class Condition {

		Type type;
		Set<IKimConcept.Type> semantics;
		IConcept c1;
		IConcept c2;

		public Condition(Type type) {
			this.type = type;
		}

		public Condition(IKimConcept.Type... semantics) {
			this.semantics = EnumSet.noneOf(IKimConcept.Type.class);
			for (IKimConcept.Type type : semantics) {
				this.semantics.add(type);
			}
		}

		boolean match(Document document) {
			// TODO
			return true;
		}
	}

	static class Constraint {

		// these are in AND
		List<Condition> conditions = new ArrayList<>();

		boolean match(Document document) {
			for (Condition condition : conditions) {
				if (!condition.match(document)) {
					return false;
				}
			}
			return true;
		}

		public static Constraint allObservables(boolean allowAbstract) {
			Constraint ret = new Constraint();
			ret.conditions.add(new Condition(IKimConcept.Type.OBSERVABLE));
			return ret;
		}

		public static Constraint allTraits(boolean allowAbstract) {
			Constraint ret = new Constraint();
			ret.conditions.add(new Condition(IKimConcept.Type.TRAIT));
			return ret;
		}

		public static Constraint allPrefixOperators() {
			Constraint ret = new Constraint();
			ret.conditions.add(new Condition(Type.PREFIX_OPERATOR));
			return ret;
		}

	}

	@Override
	public SearchContext accept(Match match) {
		SearchContext ret = new SearchContext(this);
		// TODO define constraints for the next match
		return ret;
	}

	public boolean isAllowed(Type type) {
		return constraintTypes.isEmpty() || constraintTypes.contains(type);
	}

	@Override
	public boolean isEnd() {
		return false;
	}

	@Override
	public boolean isConsistent() {
		return false;
	}

	@Override
	public String getUrn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEmpty() {
		// root context is empty
		return parent == null;
	}

	@Override
	public Context previous() {
		return parent;
	}

	public Query buildQuery(String currentTerm, Analyzer analyzer) {
		QueryParser parser = new QueryParser("name", analyzer);
		// parser.setAllowLeadingWildcard(true);
		try {
			// hai voglia
			return parser.parse("name:" + currentTerm + "*");
		} catch (ParseException e) {
			throw new KlabValidationException(e);
		}
	}

	public static Context createNew(Set<Type> matchTypes,
			Set<org.integratedmodelling.kim.api.IKimConcept.Type> semanticTypes) {
		SearchContext ret = new SearchContext();
		if (matchTypes.isEmpty() && semanticTypes.isEmpty()) {
			// first context can select operators, non-abstract traits or non-abstract
			// observables
			ret.constraints.add(Constraint.allPrefixOperators());
			ret.constraints.add(Constraint.allTraits(false));
			ret.constraints.add(Constraint.allObservables(false));
		} else {
			// TODO
		}
		return ret;

	}

}
