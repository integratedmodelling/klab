package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimMacro;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.kim.Concept;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.validation.KimValidator;

/**
 * Normalized translation of a concept declaration, with a stable ordering of
 * concepts an full parenthesization so that same meaning guarantees same text
 * representation.
 * 
 * The descriptor returned by {@link KimConcept#getDescriptor()} contains all
 * the info about the concept that are available without access to the reasoner.
 * Its description field is the normalized concept declaration.
 * 
 * @author Ferd
 *
 */
public class KimConcept extends KimStatement implements IKimConcept {

	private String name;

	private Expression expressionType = Expression.SINGLETON;

	/**
	 * Main observable concept, null if name is not
	 */
	private KimConcept observable;

	/**
	 * The observation type, if any
	 */
	private UnarySemanticOperator observationType;

	/**
	 * Sorted list of all traits concepts
	 */
	private List<IKimConcept> traits = new ArrayList<>();

	/**
	 * Sorted list of all traits concepts
	 */
	private List<IKimConcept> roles = new ArrayList<>();

	/**
	 * Sorted list of anything unclassified
	 */
	private List<IKimConcept> unclassified = new ArrayList<>();

	/**
	 * If this is not empty, the ExpressionType must be != SINGLETON.
	 */
	private List<IKimConcept> operands = new ArrayList<>();

	/**
	 * The 'of' concept, if any
	 */
	private KimConcept inherent;
	/**
	 * The 'within' concept, if any
	 */
	private KimConcept context;

	private KimConcept otherConcept;
	// private KimConcept byTrait;
	// private KimConcept downTo;

	private EnumSet<Type> type = EnumSet.noneOf(Type.class);

	private String authority;
	private String authorityTerm;

	private KimConcept motivation = null;
	private KimConcept causant = null;
	private KimConcept caused = null;
	private KimConcept compresent = null;
	private KimConcept validParent = null;

	/**
	 * True if main concept is negated ('not')
	 */
	private boolean negated;

	/**
	 * True if any concepts in the declaration are templated
	 */
	private boolean template;

	private ConceptDescriptor descriptor;

	private static final long serialVersionUID = 4160895607335615009L;

	public KimConcept(ConceptDeclaration statement, IKimStatement parent) {
		super(statement, parent);
		// TODO Auto-generated constructor stub
	}

	public KimConcept(Concept statement, IKimStatement parent) {
		super(statement, parent);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Create a normalized KimConcept from a parsed declaration.
	 * 
	 * @param declaration
	 * @return the normalized concept
	 */
	public static KimConcept normalize(ConceptDeclaration declaration, IKimStatement parent) {
		return normalize(declaration, null, parent);
	}

	/**
	 * Create a normalized KimConcept from a parsed declaration, optionally using a
	 * macro instance for substitutions.
	 * 
	 * @param declaration
	 * @param macro
	 *            can be null
	 * @param parent
	 * @return the normalized concept
	 */
	public static KimConcept normalize(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent) {

		if (Kim.INSTANCE.hasErrors(declaration)) {
			return null;
		}

		if (declaration.getMain() == null || declaration.getMain().size() == 0) {
			return null;
		}

		KimConcept ret = new KimConcept(declaration, parent);
		KimConcept observable = null;
		List<KimConcept> unclassified = new ArrayList<>();
		KimConcept last = null;
		boolean subjective = false;

		for (Concept main : declaration.getMain()) {
			last = normalize(main, parent);
			if (last == null) {
				return null;
			}
			if (last.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			unclassified.add(last);
		}

		/**
		 * 2 passes: establish observable first, then all its traits/roles
		 */
		int obspos = -1;
		for (int i = unclassified.size() - 1; i >= 0; i--) {
			if (unclassified.get(i).is(Type.OBSERVABLE)) {
				observable = unclassified.get(i);
				obspos = i;
			}
		}

		if (observable != null) {
			unclassified.remove(obspos);
		} else {
			// use last concept declared
			observable = unclassified.get(unclassified.size() - 1);
			unclassified.remove(unclassified.size() - 1);
		}

		for (KimConcept c : unclassified) {
			if (c.is(Type.TRAIT)) {
				ret.traits.add(c);
			} else if (c.is(Type.ROLE)) {
				ret.roles.add(c);
			} else {
				ret.unclassified.add(c);
			}
		}

		if (observable == null) {
			return null;
		}

		ret.observable = observable;
		ret.type = observable.type;

		if (declaration.getInherency() != null) {
			ret.inherent = normalize(declaration.getInherency(), parent);
			if (ret.inherent == null) {
				return null;
			}
			if (ret.inherent.type.isEmpty()) {
				ret.type.clear();
			} else if (ret.inherent.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.inherent.isTemplate()) {
				ret.template = true;
			}

		}
		if (declaration.getContext() != null) {
			ret.context = normalize(declaration.getContext(), parent);
			if (ret.context == null) {
				return null;
			}
			if (ret.context.type.isEmpty()) {
				ret.type.clear();
				;
			} else if (ret.context.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.context.isTemplate()) {
				ret.template = true;
			}

		}
		if (declaration.getMotivation() != null) {
			ret.motivation = normalize(declaration.getContext(), parent);
			if (ret.motivation == null) {
				return null;
			}
			if (ret.motivation.type.isEmpty()) {
				ret.type.clear();
				;
			} else if (ret.motivation.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.motivation.isTemplate()) {
				ret.template = true;
			}

		}
		if (declaration.getCausant() != null) {
			ret.causant = normalize(declaration.getContext(), parent);
			if (ret.causant == null) {
				return null;
			}
			if (ret.causant.type.isEmpty()) {
				ret.type.clear();
				;
			} else if (ret.causant.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.causant.isTemplate()) {
				ret.template = true;
			}

		}
		if (declaration.getCaused() != null) {
			ret.caused = normalize(declaration.getContext(), parent);
			if (ret.caused == null) {
				return null;
			}
			if (ret.caused.type.isEmpty()) {
				ret.type.clear();
				;
			} else if (ret.caused.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.caused.isTemplate()) {
				ret.template = true;
			}
		}
		if (declaration.getCompresent() != null) {
			ret.compresent = normalize(declaration.getContext(), parent);
			if (ret.compresent == null) {
				return null;
			}
			if (ret.compresent.type.isEmpty()) {
				ret.type.clear();
				;
			} else if (ret.compresent.is(Type.SUBJECTIVE)) {
				subjective = true;
			}
			if (ret.compresent.isTemplate()) {
				ret.template = true;
			}
		}

		ret.traits.sort(new Comparator<IKimConcept>() {

			@Override
			public int compare(IKimConcept o1, IKimConcept o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		ret.roles.sort(new Comparator<IKimConcept>() {

			@Override
			public int compare(IKimConcept o1, IKimConcept o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		ret.unclassified.sort(new Comparator<IKimConcept>() {

			@Override
			public int compare(IKimConcept o1, IKimConcept o2) {
				return o1.getName().compareTo(o2.getName());
			}
		});

		// TODO by and downTo

		if (ret.unclassified.size() > 0) {
			ret.type.clear();
			;
		} else if (subjective) {
			ret.type.add(Type.SUBJECTIVE);
		}

		/*
		 * expression operands (between self and them)
		 */
		int i = 0;
		for (ConceptDeclaration operand : declaration.getOperands()) {
			ret.operands.add(normalize(operand, macro));
			if (i == 0) {
				switch (declaration.getOperators().get(0)) {
				case "and":
					ret.expressionType = Expression.INTERSECTION;
					break;
				case "or":
					ret.expressionType = Expression.UNION;
					break;
				}
			}
		}

		return ret;
	}

	static KimConcept normalize(Concept concept, IKimStatement parent) {

		if (concept.getDeclaration() != null) {
			return normalize(concept.getDeclaration(), parent);
		}

		KimConcept ret = null;

		if (concept.getConcept() != null) {

			// reading a semantic operator
			ret = normalize(concept.getConcept(), parent);
			if (ret == null) {
				return null;
			}
			if (concept.getOther() != null) {
				ret.otherConcept = normalize(concept.getOther(), parent);
			}

			ret.setObservationType(concept);

		} else {

			// reading a basic named concept with potential negation or authority
			ret = new KimConcept(concept, parent);
			if (concept.getName().isTemplate()) {
				// add the declaration character, # for an optional field and $ for a mandatory
				// one
				ret.name = concept.getName().getTemplateType().charAt(0) + concept.getName().getName();
				if (concept.getName().getExtends() != null) {
					ret.validParent = normalize(concept.getName().getExtends(), parent);
					if (ret.validParent != null) {
						ret.type.addAll(ret.validParent.type);
					}
				} else {
					ret.type.addAll(Kim.INSTANCE.getType(concept.getName().getType()));
				}

			} else {
				ret.name = concept.getName().getName();
				if (ret.name != null && !ret.name.contains(":")) {
					Namespace namespace = KimValidator.getNamespace(concept);
					ret.name = (namespace == null ? "UNDEFINED" : Kim.getNamespaceId(namespace)) + ":" + ret.name;
				}
			}
			ret.negated = concept.isNegated();
			ret.authority = concept.getAuthority();
			if (concept.getStringIdentifier() != null) {
				ret.authorityTerm = concept.getStringIdentifier();
			} else if (ret.authority != null) {
				ret.authorityTerm = concept.getIntIdentifier() + "";
			}

			ConceptDescriptor cd = Kim.INSTANCE.getConceptDescriptor(ret.name);
			if (cd != null) {
				ret.type.addAll(cd.getFlags());
			}
		}

		return ret;
	}

	private EnumSet<Type> setObservationType(Concept declaration) {

		Type operator = null;

		if (declaration.isCount()) {
			observationType = UnarySemanticOperator.COUNT;
			operator = Type.NUMEROSITY;
		} else if (declaration.isDistance()) {
			observationType = UnarySemanticOperator.DISTANCE;
			operator = Type.DISTANCE;
		} else if (declaration.isOccurrence()) {
			observationType = UnarySemanticOperator.OCCURRENCE;
			operator = Type.OCCURRENCE;
		} else if (declaration.isPresence()) {
			observationType = UnarySemanticOperator.PRESENCE;
			operator = Type.PRESENCE;
		} else if (declaration.isProbability()) {
			observationType = UnarySemanticOperator.PROBABILITY;
			operator = Type.PROBABILITY;
		} else if (declaration.isProportion()) {
			observationType = UnarySemanticOperator.PROPORTION;
			operator = Type.PROPORTION;
		} else if (declaration.isRatio()) {
			observationType = UnarySemanticOperator.RATIO;
			operator = Type.RATIO;
		} else if (declaration.isValue()) {
			observationType = UnarySemanticOperator.VALUE;
			operator = Type.VALUE;
		} else if (declaration.isUncertainty()) {
			observationType = UnarySemanticOperator.UNCERTAINTY;
			operator = Type.UNCERTAINTY;
		} else if (declaration.isAssessment()) {
			observationType = UnarySemanticOperator.ASSESSMENT;
			operator = Type.ASSESSMENT;
		} else if (declaration.isMagnitude()) {
			observationType = UnarySemanticOperator.MAGNITUDE;
			operator = Type.MAGNITUDE;
		}

		if (operator != null) {
			type = Kim.INSTANCE.makeQuality(type, operator);
		}

		return type;
	}

	@Override
	public String getDefinition() {
		String ret = toString();
		if (ret.startsWith("(")) {
			ret = ret.substring(1);
			ret = ret.substring(0, ret.length() - 1);
		}
		return ret;
	}

	/**
	 * Create a text declaration that can be parsed back into a concept.
	 */
	public String toString() {

		String ret = "";
		boolean complex = false;

		if (observationType != null) {
			ret += (ret.isEmpty() ? "" : " ") + observationType.declaration[0];
			complex = true;
		}

		if (negated) {
			ret += (ret.isEmpty() ? "" : " ") + "not";
			complex = true;
		}

		String concepts = "";
		boolean ccomplex = false;

		for (IKimConcept trait : traits) {
			concepts += (concepts.isEmpty() ? "" : " ") + trait;
			ccomplex = true;
		}

		for (IKimConcept role : roles) {
			concepts += (concepts.isEmpty() ? "" : " ") + role;
			ccomplex = true;
		}

		for (IKimConcept conc : unclassified) {
			concepts += (concepts.isEmpty() ? "" : " ") + conc;
			ccomplex = true;
		}

		concepts += (concepts.isEmpty() ? "" : " ") + (name == null ? observable.toString() : name);

		ret += (ret.isEmpty() ? "" : " ") + (ccomplex ? "(" : "") + concepts + (ccomplex ? ")" : "");

		if (otherConcept != null) {
			ret += " " + observationType.declaration[1] + " " + otherConcept;
			complex = true;
		}

		if (authority != null) {
			ret += " identified as " + stringify(authorityTerm) + " by " + authority;
			complex = true;
		}

		if (inherent != null) {
			ret += " of " + inherent;
			complex = true;
		}

		if (context != null) {
			ret += " within " + context;
			complex = true;
		}

		for (IKimConcept operand : operands) {
			ret += " " + (expressionType == Expression.INTERSECTION ? "and" : "or") + " " + operand;
			complex = true;
		}

		return complex ? ("(" + ret + ")") : ret;
	}

	private String stringify(String term) {

		if (term.startsWith("\"")) {
			return term;
		}

		boolean ws = false;

		// stringify anything that's not a lowercase ID
		for (int i = 0; i < term.length(); i++) {
			if (Character.isWhitespace(term.charAt(i)) || !(Character.isLetter(term.charAt(i))
					|| Character.isDigit(term.charAt(i)) || term.charAt(i) == '_')) {
				ws = true;
				break;
			}
		}

		// TODO should escape any internal double quotes, unlikely
		return ws ? ("\"" + term + "\"") : term;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof KimConcept && toString().equals(o.toString());
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	public ConceptDescriptor getDescriptor() {
		if (descriptor == null) {
			descriptor = new ConceptDescriptor(name, type, this.toString());
		}
		return descriptor;
	}

	@Override
	public KimConcept getObservable() {
		return observable;
	}

	public void setObservable(KimConcept observable) {
		this.observable = observable;
	}

	@Override
	public UnarySemanticOperator getObservationType() {
		return observationType;
	}

	public void setObservationType(UnarySemanticOperator observationType) {
		this.observationType = observationType;
	}

	@Override
	public List<IKimConcept> getTraits() {
		return traits;
	}

	public void setTraits(List<IKimConcept> traits) {
		this.traits = traits;
	}

	@Override
	public List<IKimConcept> getRoles() {
		return roles;
	}

	public void setRoles(List<IKimConcept> roles) {
		this.roles = roles;
	}

	@Override
	public KimConcept getInherent() {
		return inherent;
	}

	public void setInherent(KimConcept inherent) {
		this.inherent = inherent;
	}

	@Override
	public KimConcept getContext() {
		return context;
	}

	public void setContext(KimConcept context) {
		this.context = context;
	}

	@Override
	public KimConcept getComparisonConcept() {
		return otherConcept;
	}

	public void setComparisonConcept(KimConcept otherConcept) {
		this.otherConcept = otherConcept;
	}

	public EnumSet<Type> getType() {
		return type;
	}

	public void setType(EnumSet<Type> type) {
		this.type = type;
	}

	@Override
	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	@Override
	public String getAuthorityTerm() {
		return authorityTerm;
	}

	public void setAuthorityTerm(String authorityTerm) {
		this.authorityTerm = authorityTerm;
	}

	@Override
	public boolean isNegated() {
		return negated;
	}

	public void setNegated(boolean negated) {
		this.negated = negated;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescriptor(ConceptDescriptor descriptor) {
		this.descriptor = descriptor;
	}

	@Override
	public String getName() {
		return name == null ? (observable == null ? null : observable.getName()) : name;
	}

	@Override
	public KimConcept getMotivation() {
		return motivation;
	}

	public void setMotivation(KimConcept motivation) {
		this.motivation = motivation;
	}

	@Override
	public KimConcept getCausant() {
		return causant;
	}

	public void setCausant(KimConcept causant) {
		this.causant = causant;
	}

	@Override
	public KimConcept getCaused() {
		return caused;
	}

	public void setCaused(KimConcept caused) {
		this.caused = caused;
	}

	@Override
	public KimConcept getCompresent() {
		return compresent;
	}

	public void setCompresent(KimConcept compresent) {
		this.compresent = compresent;
	}

	@Override
	public boolean isTemplate() {
		return template;
	}

	@Override
	public boolean is(Type type) {
		return this.type.contains(type);
	}

	@Override
	public void visit(Visitor visitor) {

		if (authority != null) {
			visitor.visitAuthority(authority, authorityTerm);
		}

		for (IKimConcept trait : traits) {
			trait.visit(visitor);
		}

		for (IKimConcept role : roles) {
			role.visit(visitor);
		}

		if (context != null) {
			context.visit(visitor);
		}

		if (inherent != null) {
			inherent.visit(visitor);
		}

		if (causant != null) {
			causant.visit(visitor);
		}

		if (caused != null) {
			caused.visit(visitor);
		}

		if (compresent != null) {
			compresent.visit(visitor);
		}

		if (motivation != null) {
			motivation.visit(visitor);
		}

		if (otherConcept != null) {
			otherConcept.visit(visitor);
		}

		if (name != null) {
			visitor.visitReference(name, type, validParent);
		} else if (observable != null) {
			visitor.visitDeclaration(observable);
		}

		if (observable != null) {
			observable.visit(visitor);
		}

	}

	public KimConcept getOtherConcept() {
		return otherConcept;
	}

	public void setOtherConcept(KimConcept otherConcept) {
		this.otherConcept = otherConcept;
	}

	public KimConcept getValidParent() {
		return validParent;
	}

	public void setValidParent(KimConcept validParent) {
		this.validParent = validParent;
	}

	@Override
	public Expression getExpressionType() {
		return expressionType;
	}

	public void setExpressionType(Expression expressionType) {
		this.expressionType = expressionType;
	}

	@Override
	public List<IKimConcept> getOperands() {
		return operands;
	}

	public void setOperands(List<IKimConcept> operands) {
		this.operands = operands;
	}

	/**
	 * Call to check if the declaration defines an abstract status. Returns the type
	 * for fluency.
	 * 
	 * @return true if abstract according to concept composition rules
	 */
	public Set<Type> checkAbstractStatus() {
		return type;
	}

	@Override
	public Type getFundamentalType() {
		return Kim.INSTANCE.getFundamentalType(this.type);
	}

}
