package org.integratedmodelling.kim.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import org.integratedmodelling.kim.api.BinarySemanticOperator;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimMacro;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.kim.Concept;
import org.integratedmodelling.kim.kim.ConceptDeclaration;
import org.integratedmodelling.kim.kim.Namespace;
import org.integratedmodelling.kim.model.Kim.ConceptDescriptor;
import org.integratedmodelling.kim.validation.KimValidator;
import org.integratedmodelling.klab.utils.CamelCase;
import org.integratedmodelling.klab.utils.CollectionUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.SemanticType;

/**
 * Normalized translation of a concept declaration, with a stable ordering of concepts and full
 * parenthesization, so that same meaning guarantees same text representation.
 * 
 * The descriptor returned by {@link KimConcept#getDescriptor()} contains all the info about the
 * concept that are available without access to the reasoner. Its description field is the
 * normalized concept declaration.
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
    private KimConcept adjacent = null;
    private KimConcept cooccurrent = null;
    private KimConcept relationshipSource = null;
    private KimConcept relationshipTarget = null;
    private KimConcept temporalInherent = null;

    private KimConcept validParent = null;

    /**
     * True if main concept is negated ('not')
     */
    private boolean negated;

    /**
     * this tracks those concepts that contain attributes or roles with inherency and are only legal
     * when used within specific usages of observables. This happens when a trait or role is
     * followed by of/for/within.
     */
    private boolean traitObservable = false;

    /**
     * if any component was declared as the distributed inherent ('of each'), this returns the
     * correspondent component role.
     */
    private ObservableRole distributedInherent = null;

    /**
     * True if any concepts in the declaration are templated
     */
    private boolean template;

    private ConceptDescriptor descriptor;

    /**
     * If this has been modified by a semantic operator, the type before the argument was applied;
     * otherwise null.
     */
    private EnumSet<Type> argumentType;

    private static final long serialVersionUID = 4160895607335615009L;

    public KimConcept(ConceptDeclaration statement, IKimStatement parent) {
        super(statement, parent);
        // TODO Auto-generated constructor stub
    }

    public KimConcept(String name) {
        this.name = name;
    }

    public KimConcept(KimConcept other) {
        super(other);
        this.name = other.name;
        this.expressionType = other.expressionType;
        this.observable = other.observable;
        this.observationType = other.observationType;
        this.traits.addAll(other.traits);
        this.roles.addAll(other.roles);
        this.unclassified.addAll(other.unclassified);
        this.operands.addAll(other.operands);
        this.inherent = other.inherent;
        this.context = other.context;
        this.otherConcept = other.otherConcept;
        this.type = other.type;
        this.authority = other.authority;
        this.authorityTerm = other.authorityTerm;
        this.motivation = other.motivation;
        this.causant = other.causant;
        this.caused = other.caused;
        this.compresent = other.compresent;
        this.adjacent = other.adjacent;
        this.cooccurrent = other.cooccurrent;
        this.validParent = other.validParent;
        this.negated = other.negated;
        this.template = other.template;
        this.descriptor = other.descriptor;
        this.traitObservable = other.traitObservable;
        this.temporalInherent = other.temporalInherent;
        this.distributedInherent = other.distributedInherent;
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
    public static KimConcept normalize(ConceptDeclaration declaration, IKimStatement parent, boolean root) {
        return normalize(declaration, null, parent, root);
    }

    /**
     * Create a normalized KimConcept from a parsed declaration, optionally using a macro instance
     * for substitutions.
     * 
     * @param declaration
     * @param macro can be null
     * @param parent
     * @return the normalized concept
     */
    public static KimConcept normalize(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent) {
        return normalize(declaration, macro, parent, true);
    }

    private static KimConcept normalize(ConceptDeclaration declaration, IKimMacro macro, IKimStatement parent, boolean root) {

        if (macro != null) {
            /*
             * the template to follow is the parent concept in the macro
             */
            System.out.println("EHILA'");
        }
        
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
            last = normalize(main, parent, root);
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

        if (observable == null) {
            return null;
        }

        /*
         * if the main observable is a macro, gather the mappings to remap into the final concept.
         */
        if (observable.is(Type.MACRO)) {
            KimMacro mac = new KimMacro(Kim.INSTANCE.getConceptStatement(observable));
            Pair<List<ConceptDeclaration>, BinarySemanticOperator> declarations = ((KimMacro)macro).getMacroDefinition();
            List<KimConcept> ccs = new ArrayList<>();
            for (ConceptDeclaration cd : declarations.getFirst()) {
                ccs.add(normalize(cd, mac, parent, root));
            }
            if (declaration.getOperators() != null) {
                
            } else {
                return ccs.isEmpty() ? null : ccs.get(0);
            }
        }
        
        // boolean hasConcretizingTrait = false;
        for (KimConcept c : unclassified) {
            if (c.is(Type.TRAIT)) {
                ret.traits.add(c);
            } else if (c.is(Type.ROLE)) {
                ret.roles.add(c);
            } else {
                ret.unclassified.add(c);
            }
        }

        ret.observable = observable;
        ret.type = observable.type;

        if (declaration.isDistributedOfInherency()) {
            ret.distributedInherent = ObservableRole.INHERENT;
        } else if (declaration.isDistributedForInherency()) {
            ret.distributedInherent = ObservableRole.GOAL;
        } else if (declaration.isDistributedWithinInherency()) {
            ret.distributedInherent = ObservableRole.CONTEXT;
        }

        ConceptDeclaration inherency = declaration.getInherency();
        if (inherency != null) {
            ret.inherent = normalize(inherency, null, parent, false);
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

            if (ret.type.contains(IKimConcept.Type.TRAIT) || ret.type.contains(IKimConcept.Type.ROLE)) {
                /*
                 * inherency is for the trait, i.e. this can only be used in an observable.
                 */
                ret.traitObservable = true;
            }
        }
        if (declaration.getContext() != null) {
            ret.context = normalize(declaration.getContext(), null, parent, false);
            if (ret.context == null) {
                return null;
            }
            if (ret.context.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.context.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.context.isTemplate()) {
                ret.template = true;
            }
            if (ret.type.contains(IKimConcept.Type.TRAIT) || ret.type.contains(IKimConcept.Type.ROLE)) {
                // /*
                // * inherency is for the trait, i.e. this can only be used in an observable.
                // */
                // ret.inherent = ret.context;
                // ret.context = null;
                ret.traitObservable = true;
            }
        }
        if (declaration.getMotivation() != null) {

            ret.motivation = normalize(declaration.getMotivation(), null, parent, false);
            if (ret.motivation == null) {
                return null;
            }
            if (ret.motivation.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.motivation.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.motivation.isTemplate()) {
                ret.template = true;
            }
            if (ret.type.contains(IKimConcept.Type.TRAIT) || ret.type.contains(IKimConcept.Type.ROLE)) {
                /*
                 * inherency is for the trait, i.e. this can only be used in an observable.
                 */
                ret.inherent = ret.motivation;
                ret.motivation = null;
                ret.traitObservable = true;
            }
        }
        if (declaration.getCausant() != null) {
            ret.causant = normalize(declaration.getCausant(), null, parent, false);
            if (ret.causant == null) {
                return null;
            }
            if (ret.causant.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.causant.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.causant.isTemplate()) {
                ret.template = true;
            }

        }
        if (declaration.getCaused() != null) {
            ret.caused = normalize(declaration.getCaused(), null, parent, false);
            if (ret.caused == null) {
                return null;
            }
            if (ret.caused.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.caused.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.caused.isTemplate()) {
                ret.template = true;
            }
        }
        if (declaration.getCompresent() != null) {
            ret.compresent = normalize(declaration.getCompresent(), null, parent, false);
            if (ret.compresent == null) {
                return null;
            }
            if (ret.compresent.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.compresent.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.compresent.isTemplate()) {
                ret.template = true;
            }
        }
        if (declaration.getDuring() != null) {
            KimConcept cocc = normalize(declaration.getDuring(), null, parent, false);
            if (cocc == null) {
                return null;
            }
            if (cocc.type.isEmpty()) {
                ret.type.clear();
            } else if (cocc.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (cocc.isTemplate()) {
                ret.template = true;
            }
            if (declaration.isDistributedTemporalInherency()) {
                ret.temporalInherent = cocc;
            } else {
                ret.cooccurrent = cocc;
            }
        }
        if (declaration.getAdjacent() != null) {
            ret.adjacent = normalize(declaration.getAdjacent(), null, parent, false);
            if (ret.adjacent == null) {
                return null;
            }
            if (ret.adjacent.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.adjacent.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.adjacent.isTemplate()) {
                ret.template = true;
            }
        }
        if (declaration.getRelationshipSource() != null) {
            ret.relationshipSource = normalize(declaration.getRelationshipSource(), null, parent, false);
            if (ret.relationshipSource == null) {
                return null;
            }
            if (ret.relationshipSource.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.relationshipSource.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.relationshipSource.isTemplate()) {
                ret.template = true;
            }
            ret.relationshipTarget = normalize(declaration.getRelationshipTarget(), null, parent, false);
            if (ret.relationshipTarget == null) {
                return null;
            }
            if (ret.relationshipTarget.type.isEmpty()) {
                ret.type.clear();
            } else if (ret.relationshipTarget.is(Type.SUBJECTIVE)) {
                subjective = true;
            }
            if (ret.relationshipTarget.isTemplate()) {
                ret.template = true;
            }
        }
        ret.traits.sort(new Comparator<IKimConcept>(){

            @Override
            public int compare(IKimConcept o1, IKimConcept o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        ret.roles.sort(new Comparator<IKimConcept>(){

            @Override
            public int compare(IKimConcept o1, IKimConcept o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        ret.unclassified.sort(new Comparator<IKimConcept>(){

            @Override
            public int compare(IKimConcept o1, IKimConcept o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        // TODO by and downTo

        if (ret.unclassified.size() > 0) {
            ret.type.clear();;
        } else if (subjective) {
            ret.type.add(Type.SUBJECTIVE);
        }

        if (ret.is(Type.ABSTRACT) && !Kim.INSTANCE.computeAbstractStatus(ret)) {
            ret.type.remove(Type.ABSTRACT);
        }

        /*
         * expression operands (between self and them)
         */
        int i = 0;
        for (ConceptDeclaration operand : declaration.getOperands()) {
            ret.operands.add(normalize(operand, null, macro, false));
            if (i == 0) {
                switch(declaration.getOperators().get(0)) {
                case "and":
                    ret.expressionType = Expression.INTERSECTION;
                    break;
                case "or":
                    ret.expressionType = Expression.UNION;
                    break;
                }
            }
        }

        /*
         * check for special forms that need rearrangement
         */
        if (root) {
            ret.rearrangeSpecialForms();
        }

        return ret;
    }

    /*
     * rearrange the special variant of <single concrete attribute/role> <abstract observable> to
     * <attribute/role> of <observable>. Use only rescaling attributes for qualities, where realms
     * and identities are strictly identifying, and allow identities and realms for countables where
     * ambiguity is unlikely.
     */
    private void rearrangeSpecialForms() {
        if (traits.size() + roles.size() == 1 && observable != null && observable.is(Type.ABSTRACT) && authority == null
                && getSemanticSubsetters().size() == 0 && operands.size() == 0) {
            IKimConcept trait = CollectionUtils.join(traits, roles).iterator().next();

            if (!trait.is(Type.ABSTRACT)) {

                boolean rearrange = trait.is(Type.ROLE) || trait.is(Type.ATTRIBUTE)
                        || (observable.is(Type.COUNTABLE) && (trait.is(Type.IDENTITY) || trait.is(Type.REALM)));

                if (rearrange && this.is(Type.QUALITY)) {
                    rearrange = trait.is(Type.RESCALING);
                }

                if (rearrange) {
                    KimConcept inh = this.observable;
                    this.type.clear();
                    this.type.addAll(trait.getType());
                    this.observable = (KimConcept) trait;
                    this.traits.clear();
                    this.roles.clear();
                    this.inherent = inh;
                }
            }
        }
    }

    public List<IKimConcept> getSemanticSubsetters() {
        List<IKimConcept> ret = new ArrayList<>();
        if (inherent != null) {
            ret.add(inherent);
        }
        if (context != null) {
            ret.add(context);
        }
        if (adjacent != null) {
            ret.add(adjacent);
        }
        if (causant != null) {
            ret.add(causant);
        }
        if (caused != null) {
            ret.add(caused);
        }
        if (compresent != null) {
            ret.add(compresent);
        }
        if (cooccurrent != null) {
            ret.add(cooccurrent);
        }
        if (motivation != null) {
            ret.add(motivation);
        }
        if (otherConcept != null) {
            ret.add(otherConcept);
        }
        return ret;
    }

    public static KimConcept normalize(Concept concept, IKimStatement parent, boolean root) {

        if (concept.getDeclaration() != null) {
            return normalize(concept.getDeclaration(), null, parent, root);
        }

        KimConcept ret = null;

        if (concept.getConcept() != null) {

            // reading a semantic operator
            ret = normalize(concept.getConcept(), null, parent, false);
            if (ret == null) {
                return null;
            }
            if (concept.getOther() != null) {
                ret.otherConcept = normalize(concept.getOther(), null, parent, root);
            }

            ret.setObservationType(concept);

        } else {

            // reading a basic named concept with potential negation or authority
            ret = new KimConcept(concept, parent);
            if (concept.getName().isTemplate()) {
                ret.template = true;
                ret.name = concept.getName().getTemplateType().charAt(0) + concept.getName().getName();
                ret.validParent = normalize(concept.getName().getExtends(), parent, root);
                if (ret.validParent != null) {
                    ret.type.addAll(ret.validParent.type);
                }

            } else {

                if (Character.isUpperCase(concept.getName().getName().charAt(0))
                        && concept.getName().getName().indexOf(':') > 0) {

                    /**
                     * Namespace is an authority
                     */
                    ret.name = concept.getName().getName();
                    ret.authority = Path.getFirst(ret.name, ":");
                    String term = Path.getLast(ret.name, ':');
                    if (term.startsWith("'") || term.startsWith("\"")) {
                        term = term.substring(1, term.length() - 1);
                    }
                    ret.authorityTerm = term;
                    ret.type.addAll(Kim.INSTANCE.getType("identity", null));

                } else {

                    ret.name = concept.getName().getName();
                    if (ret.name != null && !ret.name.contains(":")) {
                        Namespace namespace = KimValidator.getNamespace(concept);
                        ret.namespaceId = namespace == null ? "UNDEFINED" : Kim.getNamespaceId(namespace);
                        ret.name = (namespace == null ? "UNDEFINED" : Kim.getNamespaceId(namespace)) + ":" + ret.name;
                    }
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
        } else if (declaration.isPercentage()) {
            observationType = UnarySemanticOperator.PERCENTAGE;
            operator = Type.PERCENTAGE;
        } else if (declaration.isRatio()) {
            observationType = UnarySemanticOperator.RATIO;
            operator = Type.RATIO;
        } else if (declaration.isValue()) {
            observationType = declaration.isMonetary() ? UnarySemanticOperator.MONETARY_VALUE : UnarySemanticOperator.VALUE;
            operator = declaration.isMonetary() ? Type.MONETARY_VALUE : Type.VALUE;
        } else if (declaration.isUncertainty()) {
            observationType = UnarySemanticOperator.UNCERTAINTY;
            operator = Type.UNCERTAINTY;
        } else if (declaration.isMagnitude()) {
            observationType = UnarySemanticOperator.MAGNITUDE;
            operator = Type.MAGNITUDE;
        } else if (declaration.isType()) {
            observationType = UnarySemanticOperator.TYPE;
            operator = Type.CLASS;
        } else if (declaration.isLevel()) {
            observationType = UnarySemanticOperator.LEVEL;
            operator = Type.CLASS;
        } else if (declaration.isRate()) {
            observationType = UnarySemanticOperator.RATE;
            operator = Type.RATE;
        } else if (declaration.isChanged()) {
            observationType = UnarySemanticOperator.CHANGED;
            operator = Type.CHANGED;
        } else if (declaration.isChange()) {
            observationType = UnarySemanticOperator.CHANGE;
            operator = Type.CHANGE;
        }

        if (operator != null) {
            this.argumentType = this.type;
            this.type = Kim.INSTANCE.applyOperator(type, operator);
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
            ret += " of " + (distributedInherent == null ? "" : "each ") + inherent;
            complex = true;
        }

        if (context != null) {
            ret += " within " + context;
            complex = true;
        }

        if (causant != null) {
            ret += " caused by " + causant;
            complex = true;
        }

        if (caused != null) {
            ret += " causing " + caused;
            complex = true;
        }

        if (compresent != null) {
            ret += " with " + compresent;
            complex = true;
        }

        if (cooccurrent != null) {
            ret += " during " + cooccurrent;
            complex = true;
        }

        if (temporalInherent != null) {
            ret += " during each " + temporalInherent;
            complex = true;
        }

        if (adjacent != null) {
            ret += " adjacent to " + adjacent;
            complex = true;
        }

        if (motivation != null) {
            ret += " for " + motivation;
            complex = true;
        }

        if (relationshipSource != null) {
            ret += " linking " + relationshipSource;
            if (relationshipTarget != null) {
                ret += " to " + relationshipSource;
            }
            complex = true;
        }

        for (IKimConcept operand : operands) {
            ret += " " + (expressionType == Expression.INTERSECTION ? "and" : "or") + " " + operand;
            complex = true;
        }

        return (ccomplex || complex) ? parenthesize(ret) : ret;
    }

    /**
     * Add parentheses around a declaration unless it is already enclosed in parentheses.
     * 
     * @param ret
     * @return
     */
    private static String parenthesize(String ret) {
        int firstOpening = -1;
        int lastClosing = -1;
        int level = 0;
        for (int i = 0; i < ret.length(); i++) {
            if (ret.charAt(i) == '(') {
                if (level == 0) {
                    firstOpening = i;
                }
                level++;
            } else if (ret.charAt(i) == ')') {
                level--;
                if (level == 0) {
                    lastClosing = i;
                }
            }
        }

        boolean enclosed = firstOpening == 0 && lastClosing == ret.length() - 1;

        return enclosed ? ret : ("(" + ret + ")");
    }

    private String stringify(String term) {

        if (term.startsWith("\"")) {
            return term;
        }

        boolean ws = false;

        // stringify anything that's not a lowercase ID
        for (int i = 0; i < term.length(); i++) {
            if (Character.isWhitespace(term.charAt(i))
                    || !(Character.isLetter(term.charAt(i)) || Character.isDigit(term.charAt(i)) || term.charAt(i) == '_')) {
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
    public UnarySemanticOperator getSemanticModifier() {
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

        if (cooccurrent != null) {
            cooccurrent.visit(visitor);
        }

        if (adjacent != null) {
            adjacent.visit(visitor);
        }

        if (temporalInherent != null) {
            temporalInherent.visit(visitor);
        }

        if (motivation != null) {
            motivation.visit(visitor);
        }

        if (relationshipSource != null) {
            relationshipSource.visit(visitor);
        }

        if (relationshipTarget != null) {
            relationshipTarget.visit(visitor);
        }

        if (otherConcept != null) {
            otherConcept.visit(visitor);
        }

        if (name != null) {
            if (template) {
                visitor.visitTemplate(IKimMacro.Field.valueOf(name.substring(1).toUpperCase()), validParent, name.startsWith("$"));
            } else {
                visitor.visitReference(name, type, validParent);
            }
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

    @Override
    public Type getFundamentalType() {
        return Kim.INSTANCE.getFundamentalType(this.type);
    }

    @Override
    public IKimConcept getCooccurrent() {
        return cooccurrent;
    }

    public void setCooccurrent(KimConcept cooccurring) {
        this.cooccurrent = cooccurring;
    }

    public KimConcept getAdjacent() {
        return adjacent;
    }

    public void setAdjacent(KimConcept adjacent) {
        this.adjacent = adjacent;
    }

    public KimConcept removeOperator() {
        KimConcept ret = new KimConcept(this);
        if (this.observationType != null) {
            ret.observationType = null;
            ret.otherConcept = null;
            ret.type = this.argumentType;
        }
        return ret;
    }

    public KimConcept removeComponents(ObservableRole... roles) {

        KimConcept ret = new KimConcept(this);

        for (ObservableRole role : roles) {

            switch(role) {
            case ADJACENT:
                ret.adjacent = null;
                break;
            case CAUSANT:
                ret.causant = null;
                break;
            case CAUSED:
                ret.caused = null;
                break;
            case COMPRESENT:
                ret.compresent = null;
                break;
            case CONTEXT:
                ret.context = null;
                break;
            case COOCCURRENT:
                ret.cooccurrent = null;
                break;
            case GOAL:
                ret.motivation = null;
                break;
            case INHERENT:
                ret.inherent = null;
                break;
            case ROLE:
                ret.roles.clear();
                break;
            case TRAIT:
                ret.traits.clear();
                break;
            case TEMPORAL_INHERENT:
                ret.temporalInherent = null;
                break;
            case UNARY_OPERATOR:
                ret.observable.observationType = null;
                break;
            default:
                break;
            }
        }

        return ret;
    }

    public KimConcept removeComponents(List<String> declarations, List<ObservableRole> roles) {

        KimConcept ret = new KimConcept(this);

        for (int i = 0; i < declarations.size(); i++) {

            String declaration = declarations.get(i);
            ObservableRole role = roles.get(i);

            switch(role) {
            case ADJACENT:
                ret.adjacent = null;
                break;
            case CAUSANT:
                ret.causant = null;
                break;
            case CAUSED:
                ret.caused = null;
                break;
            case COMPRESENT:
                ret.compresent = null;
                break;
            case CONTEXT:
                ret.context = null;
                break;
            case COOCCURRENT:
                ret.cooccurrent = null;
                break;
            case GOAL:
                ret.motivation = null;
                break;
            case INHERENT:
                ret.inherent = null;
                break;
            case TEMPORAL_INHERENT:
                ret.temporalInherent = null;
                break;
            case ROLE:
                ret.roles = copyWithout(ret.roles, declaration);
                break;
            case TRAIT:
                ret.traits = copyWithout(ret.traits, declaration);
                break;
            default:
                break;
            }
        }

        // no need to sort again

        return ret;
    }

    private static List<IKimConcept> copyWithout(List<IKimConcept> concepts, String declaration) {
        List<IKimConcept> ret = new ArrayList<>();
        for (IKimConcept c : concepts) {
            if (!c.toString().equals(declaration)) {
                ret.add(c);
            }
        }
        return ret;
    }

    @Override
    public String getCodeName() {

        String ret = CamelCase.toLowerCase(new SemanticType(getName()).getName(), '-');
        if (observable != null) {
            ret = observable.getCodeName();
        }

        for (IKimConcept trait : traits) {
            ret = trait.getCodeName() + "-" + ret;
        }

        for (IKimConcept role : roles) {
            ret = role.getCodeName() + "-" + ret;
        }

        if (inherent != null) {
            ret += "-of-" + inherent.getCodeName();
        }

        // if (context != null) {
        // ret += "-within-" + context.getCodeName();
        // }

        if (causant != null) {
            ret += "-caused-by-" + causant.getCodeName();
        }

        if (caused != null) {
            ret += "-causing-" + caused.getCodeName();
        }

        if (compresent != null) {
            ret += "-with-" + compresent.getCodeName();
        }

        if (cooccurrent != null) {
            ret += "-during-" + cooccurrent.getCodeName();
        }

        if (temporalInherent != null) {
            ret += "-during-each-" + temporalInherent.getCodeName();
        }

        if (motivation != null) {
            ret += "-for-" + motivation.getCodeName();
        }

        if (observationType != null) {
            ret = observationType.getCodeName(ret, otherConcept == null ? null : otherConcept.getCodeName());
        }

        if (relationshipSource != null) {
            ret = relationshipSource.getCodeName() + "-to-" + relationshipTarget.getCodeName() + "-" + ret;
        }

        if (negated) {
            ret = "not-" + ret;
        }

        return ret;
    }

    @Override
    public KimConcept getRelationshipSource() {
        return relationshipSource;
    }

    @Override
    public KimConcept getTemporalInherent() {
        return temporalInherent;
    }

    public void setTemporalInherent(KimConcept event) {
        temporalInherent = event;
    }

    public void setRelationshipSource(KimConcept relationshipSource) {
        this.relationshipSource = relationshipSource;
    }

    @Override
    public KimConcept getRelationshipTarget() {
        return relationshipTarget;
    }

    public void setRelationshipTarget(KimConcept relationshipTarget) {
        this.relationshipTarget = relationshipTarget;
    }

    @Override
    public boolean isTraitObservable() {
        return traitObservable;
    }

    public void setTraitObservable(boolean traitObservable) {
        this.traitObservable = traitObservable;
    }

    @Override
    public ObservableRole getDistributedInherent() {
        return distributedInherent;
    }
    
    @Override
    public String getNamespace() {
        if (namespaceId == null) {
            String name = getName();
            if (name.contains(":")) {
                namespaceId = Path.getFirst(name, ":");
            }
        }
        return namespaceId;
    }
    

}
