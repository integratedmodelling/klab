package org.integratedmodelling.klab.engine.indexing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
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
import org.integratedmodelling.klab.utils.StringUtil;

public class SearchContext implements IIndexingService.Context {

	// these are in OR - anything matching any of these is acceptable. No
	// constraints means everything is acceptable.
	private List<Constraint> constraints = new ArrayList<>();
	private Set<Type> constrainttypes = EnumSet.noneOf(Type.class);
	private SearchContext parent = null;
	private SearchMatch acceptedMatch;

	// parenthesization level - must be 0 when accepting.
	private int depth = 0;

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

	public void addConstraint(Constraint constraint) {
		constraints.add(constraint);
		constrainttypes.add(constraint.getType());
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

		@Override
		public String toString() {
			return "[CONDITION " + type + " " + (semantics == null ? "" : semantics.toString())
					+ (c1 == null ? "" : (" C1: " + c1)) + (c2 == null ? "" : (" C2: " + c2)) + "]";
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
		private static Set<Modifier> allModifiers;

		static {
			allModifiers = new HashSet<>();
			for (Modifier modifier : Modifier.values()) {
				allModifiers.add(modifier);
			}
		}

		boolean filter;
		boolean query;
		boolean matcher;
		Set<Modifier> modifiers = null;

		// if set, all matches must have at least minMatches of the types in here
		Set<IKimConcept.Type> semantics;
		int minMatches = 1;

		// the type selects some pre-defined matches
		Type type;

		// only effective if filtering
		private boolean allowAbstract;

		private Constraint(Type type) {
			this.type = type;
		}

		public String toString() {
			String ret = "" + this.type;
			ret += filter ? " FILTER" : "";
			ret += query ? " QUERY" : "";
			ret += matcher ? " MATCH" : "";
			ret += semantics == null ? "" : (" " + semantics);
			ret += " " + conditions;
			return ret;
		}

		Type getType() {
			return type;
		}

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
					for (Modifier op : (modifiers == null ? allModifiers : modifiers)) {
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
				if (((SearchMatch) match).getSemantics().contains(IKimConcept.Type.ABSTRACT)
						// abstract qualities can be made concrete by adding inherency - we let them
						// through and refuse to
						// observe unless they have 'of' (should put the A in the icon shown in the UI
						// until inherency is
						// there).
						&& !((SearchMatch) match).getSemantics().contains(IKimConcept.Type.QUALITY)
						// abstract traits are the only ones that are observable in their own right
						&& !((SearchMatch) match).getSemantics().contains(IKimConcept.Type.TRAIT)) {
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
			Constraint ret = new Constraint(Type.CONCEPT);
			ret.semantics = EnumSet.of(IKimConcept.Type.OBSERVABLE);
			ret.query = true;
			ret.filter = true;
			ret.allowAbstract = allowAbstract;
			return ret;
		}

		public static Constraint allTraits(boolean allowAbstract) {
			Constraint ret = new Constraint(Type.CONCEPT);
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
			Constraint ret = new Constraint(Type.CONCEPT);
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
			Constraint ret = new Constraint(Type.CONCEPT);
			ret.semantics = types;
			ret.query = true;
			ret.filter = true;
			ret.minMatches = matchCount;
			return ret;
		}

		public static Constraint allPrefixOperators() {
			Constraint ret = new Constraint(Type.PREFIX_OPERATOR);
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

		public Constraint applyingTo(IKimConcept.Type type) {
			// TODO Auto-generated method stub
			return this;
		}

		/**
		 * We get here when we have an observable.
		 * 
		 * @param semantics
		 * @return
		 */
		public static Constraint modifiersFor(Set<IKimConcept.Type> semantics) {

			Constraint ret = new Constraint(Type.MODIFIER);
			ret.modifiers = new HashSet<>();
			if (Kim.INSTANCE.is(semantics, IKimConcept.Type.QUALITY)) {
				ret.modifiers.add(Modifier.ADJACENT_TO);
				ret.modifiers.add(Modifier.BY);
				ret.modifiers.add(Modifier.CAUSED_BY);
				ret.modifiers.add(Modifier.CAUSING);
				ret.modifiers.add(Modifier.CONTAINED_IN);
				ret.modifiers.add(Modifier.CONTAINING);
				ret.modifiers.add(Modifier.DOWN_TO);
				ret.modifiers.add(Modifier.DURING);
				ret.modifiers.add(Modifier.FOR);
				ret.modifiers.add(Modifier.IS);
				ret.modifiers.add(Modifier.OF);
				ret.modifiers.add(Modifier.OVER);
				ret.modifiers.add(Modifier.PER);
				ret.modifiers.add(Modifier.SAMEAS);
				ret.modifiers.add(Modifier.WHERE);
				ret.modifiers.add(Modifier.WITH);
				ret.modifiers.add(Modifier.WITHIN);
				ret.modifiers.add(Modifier.WITHOUT);

				if (Kim.INSTANCE.isNumeric(semantics)) {

					ret.modifiers.add(Modifier.PLUS);
					ret.modifiers.add(Modifier.TIMES);
					ret.modifiers.add(Modifier.GREATER);
					ret.modifiers.add(Modifier.GREATEREQUAL);
					ret.modifiers.add(Modifier.LESS);
					ret.modifiers.add(Modifier.LESSEQUAL);
					ret.modifiers.add(Modifier.MINUS);

					if (Kim.INSTANCE.is(semantics, IKimConcept.Type.EXTENSIVE_PROPERTY)
							|| Kim.INSTANCE.is(semantics, IKimConcept.Type.EXTENSIVE_PROPERTY)) {
						ret.modifiers.add(Modifier.IN);
					}
				}

			} else if (Kim.INSTANCE.is(semantics, IKimConcept.Type.EVENT)) {

			}

			return null;
		}

		public Constraint applicableTo(Collection<IConcept> collectTraits) {
			// TODO Auto-generated method stub
			return this;
		}

		public static Constraint with(IKimConcept.Type type) {
			return with(EnumSet.of(type));
		}

		public Constraint applyingTo(Set<IKimConcept.Type> semantics) {
			// TODO Auto-generated method stub
			return this;
		}

	}

	@Override
	public SearchContext accept(Match m) {

		SearchMatch match = (SearchMatch) m;
		this.acceptedMatch = match;

		SearchContext ret = new SearchContext(this);

		if (match.getUnaryOperator() != null) {
			ret.constraints.add(Constraint.with(match.getUnaryOperator().getAllowedOperandTypes()));
			switch (match.getUnaryOperator()) {
			case COUNT:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case DISTANCE:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.DISTANCE));
				break;
			case MAGNITUDE:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.QUALITY));
				break;
			case NOT:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.ATTRIBUTE)
						.applyingTo(IKimConcept.Type.DENIABLE));
				break;
			case OBSERVABILITY:
				break;
			case OCCURRENCE:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case PRESENCE:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case PROBABILITY:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.EVENT));
				break;
			case PROPORTION:
				break;
			case RATIO:
				break;
			case TYPE:
				break;
			case UNCERTAINTY:
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.QUALITY));
				break;
			case VALUE:
				break;
			default:
				break;

			}
		} else if (match.getBinaryOperator() != null) {

			switch (match.getBinaryOperator()) {
			case FOLLOWS:
				ret.constraints.add(Constraint.with(IKimConcept.Type.EVENT));
				ret.constraints.add(Constraint.allTraits(false).applyingTo(IKimConcept.Type.EVENT));
				break;
			case INTERSECTION:
			case UNION:
				ret.constraints.add(Constraint.with(getSemantics()));
				ret.constraints.add(Constraint.allTraits(false).applyingTo(getSemantics()));
				break;
			case BY:
				break;
			default:
				break;
			}

		} else if (match.getModifier() != null) {

			switch (match.getModifier()) {
			case ADJACENT_TO:
				break;
			case BY:
				break;
			case CAUSED_BY:
				break;
			case CAUSING:
				break;
			case CONTAINED_IN:
				break;
			case CONTAINING:
				break;
			case DOWN_TO:
				break;
			case DURING:
				break;
			case FOR:
				break;
			case IN:
				break;
			case OF:
				break;
			case PER:
				break;
			case WITH:
				break;
			case WITHIN:
				break;
			case GREATER:
				break;
			case GREATEREQUAL:
				break;
			case IS:
				break;
			case LESS:
				break;
			case LESSEQUAL:
				break;
			case MINUS:
				break;
			case OVER:
				break;
			case PLUS:
				break;
			case SAMEAS:
				break;
			case TIMES:
				break;
			case WHERE:
				break;
			case WITHOUT:
				break;
			default:
				break;
			}

		} else {
			// possible matches for a concept

			// if we have an observable, no more observables
			if (haveObservable()) {
				ret.constraints.add(Constraint.modifiersFor(getSemantics()));
			} else {
				ret.constraints.add(Constraint.allObservables(false).applicableTo(collectTraits()));
			}

			/*
			 * TRAIT constraint: we can put this in the else above, which will cause traits
			 * in the front only and inhibit trait definition after the observable is set.
			 * Probably good design discipline although it may be annoying.
			 */

		}

		return ret;
	}

	private Collection<IConcept> collectTraits() {
		Set<IConcept> ret = new HashSet<>();
		return ret;
	}

	private Set<IKimConcept.Type> getSemantics() {
		if (acceptedMatch.semantics != null) {
			return acceptedMatch.semantics;
		}
		return parent == null ? EnumSet.noneOf(IKimConcept.Type.class) : parent.getSemantics();
	}

	private boolean haveObservable() {

		// TODO must check for observable at the current depth, not all depths. Depths
		// are changed by parentheses
		// and interrupted by modifiers.

		if (acceptedMatch.semantics != null && acceptedMatch.semantics.contains(IKimConcept.Type.OBSERVABLE)) {
			return true;
		}

		return parent == null || parent.isScopeStart() ? false : parent.haveObservable();
	}

	private boolean isScopeStart() {
		// TODO parenthesized blocks require children
		return acceptedMatch != null && (acceptedMatch.getMatchType() == Type.MODIFIER
				|| acceptedMatch.getMatchType() == Type.OPEN_PARENTHESIS);
	}

	public boolean isAllowed(Type type) {
		return constrainttypes.isEmpty() || constrainttypes.contains(type);
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

	public String dump() {
		return dump(0);
	}

	private String dump(int offset) {
		String prefix = StringUtil.spaces(offset);
		String ret = prefix + "CTYPES: " + constrainttypes + "\n";
		if (acceptedMatch != null) {
			ret += prefix + "ACCEPTED: " + acceptedMatch + "\n";
		}
		if (constraints != null && !constraints.isEmpty()) {
			ret += prefix + "CONSTRAINTS:\n";
			for (Constraint c : constraints) {
				ret += prefix + "  " + c + "\n";
			}
		}
		if (parent != null) {
			parent.dump(offset + 3);
		}
		return ret;
	}

}
