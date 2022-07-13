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
import org.integratedmodelling.kim.api.SemanticModifier;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Context;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.api.services.IIndexingService.Match.Type;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.rest.SearchMatch.TokenClass;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * 
 * @author Ferd
 *
 */
public class SearchContext implements IIndexingService.Context {

	/*
	 * we send this one up the chain of accepted matches to collect the meaning so
	 * far.
	 */
	public class Meaning {

		Set<IKimConcept.Type> semantics;
		IConcept observable;
		Set<IConcept> traits = new HashSet<>();
		Set<IConcept> roles = new HashSet<>();
		boolean boundary;

		/**
		 * True when we hit a semantic boundary, such as an infix operator or a closed
		 * parenthesis.
		 * 
		 * @return
		 */
		boolean isBoundary() {
			return boundary;
		}

		public boolean is(IKimConcept.Type type) {
			return semantics == null ? false : semantics.contains(type);
		}

		private final char[] declarationChars = new char[] { ' ', '(' };

		void accept(SearchMatch match) {

			if (match != null) {
				switch (match.matchType) {
				case CONCEPT:
				case PRESET_OBSERVABLE:
					IConcept concept = StringUtils.containsAny(match.id, declarationChars)
							? Observables.INSTANCE.declare(match.id).getType()
							: Concepts.c(match.id);
					if (concept != null) {
						if (concept.is(IKimConcept.Type.OBSERVABLE)) {
							this.observable = concept;
							this.semantics = EnumSet.copyOf(((Concept) concept).getTypeSet());
						} else if (concept.is(IKimConcept.Type.TRAIT)) {
							this.traits.add(concept);
						} else if (concept.is(IKimConcept.Type.ROLE)) {
							this.roles.add(concept);
						}
					}
					break;
				case CLOSED_PARENTHESIS:
				case INFIX_OPERATOR:
					this.boundary = true;
					break;
				case PREFIX_OPERATOR:
					Set<IKimConcept.Type> type = Kim.INSTANCE.getType(match.unaryOperator, null);
					// we don't have an observable
					type.remove(IKimConcept.Type.OBSERVABLE);
					if (type != null) {
						this.semantics = type;
					}
					break;
				case MODEL:
					break;
				case MODIFIER:
					break;
				case OBSERVATION:
					break;
				case SEPARATOR:
				case OPEN_PARENTHESIS:
					// won't happen
					break;
				default:
					break;

				}
			}
		}

		public Set<IKimConcept.Type> getSemantics() {
			return semantics;
		}

		public IConcept getObservable() {
			return observable;
		}

		public Collection<IConcept> getTraits() {
			return traits;
		}

		public Collection<IConcept> getRoles() {
			return roles;
		}

		@Override
		public String toString() {
			String ret = "";
			ret += "Meaning: " + this.semantics + " ";
			ret += "[OBS=" + this.observable + "]";
			ret += "[TRT=" + this.traits + "]";
			ret += "[ROL=" + this.roles + "]";
			return ret;
		}

	}

	// these are in OR - anything matching any of these is acceptable. No
	// constraints means everything is acceptable.
	private List<Constraint> constraints = new ArrayList<>();
	private Set<Type> constrainttypes = EnumSet.noneOf(Type.class);
	// previous in list, containing the preceding meaning.
	private SearchContext previous = null;
	// non-null in children of previous context. Must have parent == null to be
	// complete.
	private SearchContext parent = null;
	private SearchMatch acceptedMatch;
	private TokenClass nextTokenType = TokenClass.TOKEN;
	private SearchContext childContext;
	private int parenthesisDepth;

	public static Context createNew() {
		SearchContext ret = new SearchContext();
		ret.allow(Constraint.allPrefixOperators());
		ret.allow(Constraint.allObservables(false));
		ret.allow(Constraint.allTraits(false));
		return ret;
	}

	private SearchContext() {
	}

	public void addConstraint(Constraint constraint) {
		allow(constraint);
		constrainttypes.add(constraint.getType());
	}

	private SearchContext(SearchContext parent) {
		this.previous = parent;
	}

	private SearchContext(SearchContext parent, SearchMatch match) {
		this.previous = parent;
		this.acceptedMatch = match;
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
		private static Set<SemanticModifier> allModifiers;

		static {
			allModifiers = new HashSet<>();
			for (SemanticModifier modifier : SemanticModifier.values()) {
				allModifiers.add(modifier);
			}
		}

		boolean filter;
		boolean query;
		boolean matcher;
		Set<SemanticModifier> modifiers = null;
		Set<IConcept> baseTraitBlacklist;

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
			ret += matcher ? " MATCH"
					: "" + (type == Type.MODIFIER ? (modifiers == null ? "[ALL]" : modifiers.toString()) : "");
			ret += semantics == null || semantics.isEmpty() ? "" : (" " + semantics);
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
					for (SemanticModifier op : (modifiers == null ? allModifiers : modifiers)) {
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

		public static Constraint otherTraits(Collection<IConcept> traits) {
			Constraint ret = new Constraint(Type.CONCEPT);
			ret.semantics = EnumSet.of(IKimConcept.Type.TRAIT);
			ret.query = true;
			ret.filter = true;
			ret.allowAbstract = true;
			ret.baseTraitBlacklist = new HashSet<>();
			for (IConcept trait : traits) {
				IConcept base = Traits.INSTANCE.getBaseParentTrait(trait);
				if (base != null) {
					ret.baseTraitBlacklist.add(base);
				}
			}
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
		 * Call only after with() to remove any of the types already accepted.
		 * 
		 * @param types
		 * @return
		 */
		public Constraint without(Set<IKimConcept.Type> types) {
			EnumSet<IKimConcept.Type> set = EnumSet.copyOf(this.semantics);
			set.removeAll(types);
			this.semantics = set;
			return this;
		}

		public Constraint plus(IKimConcept.Type... types) {
			this.semantics.addAll(CollectionUtils.arrayToList(types));
			return this;
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
		 * We get here when we have an observable. TODO there also should be no
		 * modifiers within the same scope.
		 * 
		 * @param semantics
		 * @return
		 */
		public static Constraint modifiersFor(Set<IKimConcept.Type> semantics) {

			Constraint ret = new Constraint(Type.MODIFIER);
			ret.matcher = true;
			ret.modifiers = new HashSet<>();
			if (Kim.INSTANCE.is(semantics, IKimConcept.Type.QUALITY)) {

				ret.modifiers.add(SemanticModifier.BY);
				ret.modifiers.add(SemanticModifier.WHERE);
				ret.modifiers.add(SemanticModifier.IS);
				ret.modifiers.add(SemanticModifier.SAMEAS);
				ret.modifiers.add(SemanticModifier.WITHOUT);

				if (Kim.INSTANCE.isNumeric(semantics)) {

					ret.modifiers.add(SemanticModifier.PLUS);
					ret.modifiers.add(SemanticModifier.TIMES);
					ret.modifiers.add(SemanticModifier.GREATER);
					ret.modifiers.add(SemanticModifier.GREATEREQUAL);
					ret.modifiers.add(SemanticModifier.LESS);
					ret.modifiers.add(SemanticModifier.LESSEQUAL);
					ret.modifiers.add(SemanticModifier.MINUS);
					ret.modifiers.add(SemanticModifier.OVER);

					if (Kim.INSTANCE.is(semantics, IKimConcept.Type.EXTENSIVE_PROPERTY)
							|| Kim.INSTANCE.is(semantics, IKimConcept.Type.INTENSIVE_PROPERTY)) {
						ret.modifiers.add(SemanticModifier.IN);
					}

					if (Kim.INSTANCE.is(semantics, IKimConcept.Type.NUMEROSITY)) {
						ret.modifiers.add(SemanticModifier.PER);
					}

				}

			}
			ret.modifiers.add(SemanticModifier.ADJACENT_TO);
			ret.modifiers.add(SemanticModifier.CAUSED_BY);
			ret.modifiers.add(SemanticModifier.CAUSING);
			ret.modifiers.add(SemanticModifier.CONTAINED_IN);
			ret.modifiers.add(SemanticModifier.CONTAINING);
			ret.modifiers.add(SemanticModifier.DOWN_TO);
			ret.modifiers.add(SemanticModifier.DURING);
			ret.modifiers.add(SemanticModifier.FOR);
			ret.modifiers.add(SemanticModifier.OF);
			ret.modifiers.add(SemanticModifier.WITH);
			ret.modifiers.add(SemanticModifier.WITHIN);

			return ret;
		}

		public Constraint applicableTo(Collection<IConcept> collectTraits) {
			// TODO Auto-generated method stub
			return this;
		}

		// Unit or currency
		public static Constraint unit(Set<IKimConcept.Type> semantics) {
			return null;
		}

		// public static Constraint with(IKimConcept.Type type) {
		// return with(EnumSet.of(type));
		// }

		public static Constraint with(IKimConcept.Type... types) {
			return with(EnumSet.copyOf(CollectionUtils.arrayToList(types)));
		}

		public Constraint applyingTo(Set<IKimConcept.Type> semantics) {
			// TODO Auto-generated method stub
			return this;
		}

		/**
		 * Literal value matching the passed semantics. Either a concept or a literal.
		 * 
		 * @param semantics
		 * @return
		 */
		public static Constraint value(Set<IKimConcept.Type> semantics) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public SearchContext accept(Match m) {

		SearchMatch match = (SearchMatch) m;

		switch (nextTokenType) {
		case BOOLEAN:
			break;
		case DOUBLE:
			break;
		case INTEGER:
			break;
		case TEXT:
			break;
		case TOKEN:
			return acceptToken(match);
		case CURRENCY:
		case UNIT:
			break;
		}

		return null;

	}

	public void allow(Constraint constraint) {
		constraints.add(constraint);
	}

	private SearchContext acceptToken(SearchMatch match) {

		/*
		 * Accepts it
		 */
		SearchContext ret = new SearchContext(this, match);

		/*
		 * Meaning BEFORE acceptance
		 */
		Meaning meaning = collectMeaning();

//		System.out.println("Meaning before accepting: " + meaning);

		if (match.getUnaryOperator() != null) {
			ret.allow(Constraint.with(match.getUnaryOperator().getAllowedOperandTypes()));
			switch (match.getUnaryOperator()) {
			case COUNT:
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case DISTANCE:
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.DISTANCE));
				break;
			case MAGNITUDE:
			case LEVEL:
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.QUALITY));
				break;
			case NOT:
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.ATTRIBUTE)
						.applyingTo(IKimConcept.Type.DENIABLE));
				break;
//			case OBSERVABILITY:
//				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
//				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
//				break;
			case OCCURRENCE:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case PRESENCE:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case PROBABILITY:
				ret.allow(Constraint.with(IKimConcept.Type.EVENT));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.EVENT));
				break;
			case PROPORTION:
				// TODO also quality? Then need to allow the second piece (in)
				ret.allow(Constraint.with(IKimConcept.Type.TRAIT));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.TRAIT));
				break;
			case RATIO:
				// TODO second piece (over)
				ret.allow(Constraint.with(IKimConcept.Type.QUALITY));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.QUALITY));
				break;
			case TYPE:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.OBSERVABLE));
				break;
			case UNCERTAINTY:
				ret.allow(Constraint.with(IKimConcept.Type.QUALITY));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.QUALITY));
				break;
			case VALUE:
				ret.allow(Constraint.with(IKimConcept.Type.OBSERVABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.OBSERVABLE));
				break;
			default:
				break;

			}
		} else if (match.getBinaryOperator() != null) {

			switch (match.getBinaryOperator()) {
			case FOLLOWS:
				ret.allow(Constraint.with(IKimConcept.Type.EVENT));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.EVENT));
				break;
			case INTERSECTION:
			case UNION:
				ret.allow(Constraint.with(meaning.getSemantics()));
				ret.allow(Constraint.allTraits(false).applyingTo(meaning.getSemantics()));
				break;
//			case BY:
//				break;
			default:
				break;
			}

		} else if (match.getModifier() != null) {

			switch (match.getModifier()) {
			case ADJACENT_TO:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case BY:
				ret.allow(Constraint.with(IKimConcept.QUALITY_TYPES)
						.plus(IKimConcept.Type.COUNTABLE, IKimConcept.Type.TRAIT)
						.without(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				// ret.allow(Constraint.allTraits(false)/*
				// .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case CAUSED_BY:
				ret.allow(Constraint.with(IKimConcept.Type.PROCESS, IKimConcept.Type.EVENT));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case CAUSING:
				ret.allow(Constraint.with(IKimConcept.Type.PROCESS, IKimConcept.Type.EVENT));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case CONTAINED_IN:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case CONTAINING:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case DOWN_TO:
				ret.allow(Constraint.with(IKimConcept.Type.CLASS, IKimConcept.Type.TRAIT));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case DURING:
				ret.allow(Constraint.with(IKimConcept.Type.EVENT));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case FOR:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false)/* .applyingTo(IKimConcept.Type.EVENT) */);
				break;
			case IN:
				ret.nextTokenType = meaning.getSemantics().contains(IKimConcept.Type.MONEY)
						|| meaning.getSemantics().contains(IKimConcept.Type.MONETARY_VALUE) 
							? TokenClass.CURRENCY
							: TokenClass.UNIT;
				ret.allow(Constraint.unit(meaning.getSemantics()));
				break;
			case OF:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case PER:
				ret.nextTokenType = TokenClass.UNIT;
				ret.allow(Constraint.unit(meaning.getSemantics()));
				break;
			case WITH:
				ret.allow(Constraint.with(IKimConcept.Type.OBSERVABLE));
				ret.allow(Constraint.allTraits(false));
				break;
			case WITHIN:
				ret.allow(Constraint.with(IKimConcept.Type.COUNTABLE));
				ret.allow(Constraint.allTraits(false).applyingTo(IKimConcept.Type.COUNTABLE));
				break;
			case GREATER:
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case GREATEREQUAL:
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case IS:
				if (Kim.INSTANCE.isNumeric(meaning.getSemantics())) {
					ret.nextTokenType = TokenClass.DOUBLE;
				}
				break;
			case LESS:
				ret.nextTokenType = TokenClass.DOUBLE;
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case LESSEQUAL:
				ret.nextTokenType = TokenClass.DOUBLE;
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case MINUS:
				ret.nextTokenType = TokenClass.DOUBLE;
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case OVER:
				ret.nextTokenType = TokenClass.DOUBLE;
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case PLUS:
				ret.nextTokenType = TokenClass.DOUBLE;
				ret.allow(Constraint.with(IKimConcept.CONTINUOUS_QUALITY_TYPES));
				break;
			case SAMEAS:
				if (Kim.INSTANCE.isNumeric(meaning.getSemantics())) {
					ret.nextTokenType = TokenClass.DOUBLE;
				}
				ret.allow(Constraint.with(meaning.getSemantics()));
				ret.allow(Constraint.value(meaning.getSemantics()));
				break;
			case TIMES:
				ret.nextTokenType = TokenClass.DOUBLE;
				break;
			case WHERE:
				break;
			case WITHOUT:
				if (Kim.INSTANCE.isNumeric(meaning.getSemantics())) {
					ret.nextTokenType = TokenClass.DOUBLE;
				} else {
					ret.allow(Constraint.allTraits(false));
					// TODO must reconstruct the semantics so far and pass it as this
					// ret.allow(Constraint.traitsIncarnating(getAcceptedSemantics()));
				}
				break;
			default:
				break;
			}

		} else {

			/*
			 * Convert to meaning AFTER acceptance
			 */
			meaning = ret.collectMeaning();

			// if we have an observable, no more observables
			if (meaning.getObservable() != null) {
				ret.allow(Constraint.modifiersFor(meaning.getSemantics()));
			} else {
				// add traits with another base trait
				ret.allow(Constraint.otherTraits(meaning.getTraits()).applyingTo(meaning.getSemantics()));
				ret.allow(Constraint.allObservables(false).applicableTo(meaning.getTraits()));
			}

			/*
			 * TRAIT constraint: we can put this in the else above, which will cause traits
			 * in the front only and inhibit trait definition after the observable is set.
			 * Probably good design discipline although it may be annoying.
			 */

		}

		// System.out.println("Meaning after accepting: " + ret.collectMeaning());

		return ret;
	}

	// private Set<IKimConcept.Type> getSemantics() {
	// return collectMeaning().semantics;
	// }

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
		return previous == null;
	}

	@Override
	public Context previous() {
		return previous;
	}

	public static Context createNew(Set<Type> matchTypes,
			Set<org.integratedmodelling.kim.api.IKimConcept.Type> semanticTypes) {
		SearchContext ret = new SearchContext();
		if (matchTypes.isEmpty() && semanticTypes.isEmpty()) {
			// first context can select operators, non-abstract traits or non-abstract
			// observables
			ret.allow(Constraint.allPrefixOperators());
			ret.allow(Constraint.allObservables(false));
			ret.allow(Constraint.allTraits(false));
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
		if (previous != null) {
			previous.dump(offset + 3);
		}
		return ret;
	}

	public TokenClass getNextTokenType() {
		return nextTokenType;
	}

	public void setNextTokenType(TokenClass nextTokenType) {
		this.nextTokenType = nextTokenType;
	}

	@Override
	public boolean isComposite() {
		return childContext != null;
	}

	@Override
	public Context getChildContext() {
		return childContext;
	}

	public Meaning collectMeaning() {
		Meaning meaning = new Meaning();
		SearchContext current = this;
		while (current != null && !meaning.isBoundary()) {
			meaning.accept(current.acceptedMatch);
			current = current.previous;
		}
		return meaning;
	}

	@Override
	public Match getAcceptedMatch() {
		return acceptedMatch;
	}

	@Override
	public int getDepth() {
		return parenthesisDepth;
	}

}
