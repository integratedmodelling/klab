package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Query;
import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.Modifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.model.Kim;
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
		ret.constraints.add(Constraint.allObservables(false));
		ret.constraints.add(Constraint.allTraits(false));
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

		boolean filter(Match document) {
			// TODO
			return true;
		}
	}

	/**
	 * Constraints can be matchers (which produce matches directly) or queries
	 * (which produce queries for documents). Optionally, they may also be filters
	 * (which scan the query results and accept/reject them selectively). Conditions
	 * of various types can be added to build intelligent filters.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	static class Constraint {

		// if not empty, these are in AND and filter is true
		private List<Condition> conditions = new ArrayList<>();

		boolean filter;
		boolean query;
		boolean matcher;

		// if set, all matches must have at least minMatches of the types in here
		Set<IKimConcept.Type> semantics;
		int minMatches = 1;

		// the type selects some pre-defined matches
		Type type;
		
		// only effective if filtering
		private boolean allowAbstract;

		/**
		 * true if it wants to filter matches once produced by a query (usually when
		 * reasoning is required). Adding conditions will set this to true.
		 * 
		 * @return
		 */
		boolean isFilter() {
			return filter;
		}

		/**
		 * true if it produces matches directly
		 * 
		 * @return
		 */
		boolean isMatcher() {
			return matcher;
		}

		/**
		 * true if it produces a query for the index
		 * 
		 * @return
		 */
		boolean isQuery() {
			return query;
		}

		/**
		 * Add a condition
		 * 
		 * @param condition
		 */
		public void addCondition(Condition condition) {
			this.conditions.add(condition);
			this.filter = true;
		}

		/**
		 * If {@link #isMatcher()}, this will be called to produce any matches directly.
		 * The matches will also be filtered if {@link #isFilter()}.
		 * 
		 * @param queryTerm
		 * @return
		 */
		List<Match> getMatches(String queryTerm) {
			List<Match> ret = new ArrayList<>();
			if (this.type != null) {
				switch (this.type) {
				case INFIX_OPERATOR:
					for (BinarySemanticOperator op : BinarySemanticOperator.values()) {
						if (op.name().toLowerCase().startsWith(queryTerm)) {
							ret.add(new SearchMatch(op));
						}
					}
					break;
				case MODIFIER:
					for (Modifier op : Modifier.values()) {
						if (op.declaration[0].startsWith(queryTerm)) {
							ret.add(new SearchMatch(op));
						}
					}
					break;
				case PREFIX_OPERATOR:
					for (UnarySemanticOperator op : UnarySemanticOperator.values()) {
						if (op.declaration[0].startsWith(queryTerm)) {
							ret.add(new SearchMatch(op));
						}
					}
					break;
				default:
					break;

				}
			}
			return ret;
		}

		public boolean filter(Match match) {
			
			if (!allowAbstract && ((SearchMatch) match).getSemantics() != null) {
				if (((SearchMatch) match).getSemantics().contains(IKimConcept.Type.ABSTRACT)) {
					return false;
				}
			}
			
			if (this.semantics != null) {
			
				if (((SearchMatch) match).getSemantics() == null) {
					return false;
				}
				
				EnumSet<IKimConcept.Type> intersection = Kim.intersection(((SearchMatch) match).getSemantics(),
						this.semantics);
				
				if (minMatches < 0) {
					if (intersection.size() < this.semantics.size()) {
						return false;
					}
				} else if (intersection.size() < minMatches) {
					return false;
				}
			}
			
			for (Condition condition : conditions) {
				if (!condition.filter(match)) {
					return false;
				}
			}
			return true;
		}

		public static Constraint allObservables(boolean allowAbstract) {
			Constraint ret = new Constraint();
			ret.semantics = EnumSet.of(IKimConcept.Type.OBSERVABLE);
			ret.query = true;
			ret.filter = true;
			ret.allowAbstract = allowAbstract;
			return ret;
		}

		public static Constraint allTraits(boolean allowAbstract) {
			Constraint ret = new Constraint();
			ret.semantics = EnumSet.of(IKimConcept.Type.TRAIT);
			ret.query = true;
			ret.filter = true;
			ret.allowAbstract = allowAbstract;
			return ret;
		}

		/**
		 * Match the types and ensure one or more is in the matched object.
		 * 
		 * @param types
		 * @return
		 */
		public static Constraint with(Set<IKimConcept.Type> types) {
			Constraint ret = new Constraint();
			ret.semantics = types;
			ret.query = true;
			ret.filter = true;
			return ret;
		}

		/**
		 * Match the types and ensure the match have at least matchCount of them. Pass
		 * -1 for all of them.
		 * 
		 * @param types
		 * @param matchCount
		 * @return
		 */
		public static Constraint with(Set<IKimConcept.Type> types, int matchCount) {
			Constraint ret = new Constraint();
			ret.semantics = types;
			ret.query = true;
			ret.filter = true;
			ret.minMatches = matchCount;
			return ret;
		}

		public static Constraint allPrefixOperators() {
			Constraint ret = new Constraint();
			ret.type = Type.PREFIX_OPERATOR;
			ret.matcher = true;
			return ret;
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

	}

	@Override
	public SearchContext accept(Match m) {
		SearchContext ret = new SearchContext(this);
		SearchMatch match = (SearchMatch) m;

		if (match.getUnaryOperator() != null) {
			ret.constraints.add(Constraint.with(match.getUnaryOperator().getAllowedOperandTypes()));
		} else if (match.getBinaryOperator() != null) {

		} else if (match.getModifier() != null) {

		} else {

		}

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

	public static Context createNew(Set<Type> matchTypes,
			Set<org.integratedmodelling.kim.api.IKimConcept.Type> semanticTypes) {
		SearchContext ret = new SearchContext();
		if (matchTypes.isEmpty() && semanticTypes.isEmpty()) {
			// first context can select operators, non-abstract traits or non-abstract
			// observables
			ret.constraints.add(Constraint.allPrefixOperators());
			ret.constraints.add(Constraint.allObservables(false));
			ret.constraints.add(Constraint.allTraits(false));
		} else {
			// TODO
		}
		return ret;
	}

	public List<Constraint> getConstraints() {
		return constraints;
	}

}
