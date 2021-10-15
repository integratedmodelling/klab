//package org.integratedmodelling.klab.owl;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.EnumSet;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Set;
//import java.util.Stack;
//import java.util.function.Consumer;
//
//import org.integratedmodelling.kim.api.BinarySemanticOperator;
//import org.integratedmodelling.kim.api.IKimConcept;
//import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
//import org.integratedmodelling.kim.api.IKimConcept.Type;
//import org.integratedmodelling.kim.api.SemanticModifier;
//import org.integratedmodelling.kim.api.UnarySemanticOperator;
//import org.integratedmodelling.kim.api.ValueOperator;
//import org.integratedmodelling.kim.model.Kim;
//import org.integratedmodelling.klab.Klab;
//import org.integratedmodelling.klab.Observables;
//import org.integratedmodelling.klab.Units;
//import org.integratedmodelling.klab.api.data.IGeometry;
//import org.integratedmodelling.klab.api.data.mediation.ICurrency;
//import org.integratedmodelling.klab.api.data.mediation.IUnit;
//import org.integratedmodelling.klab.api.data.mediation.IUnit.UnitContextualization;
//import org.integratedmodelling.klab.api.knowledge.IConcept;
//import org.integratedmodelling.klab.api.knowledge.IObservable;
//import org.integratedmodelling.klab.api.model.IAnnotation;
//import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
//import org.integratedmodelling.klab.common.mediation.Currency;
//import org.integratedmodelling.klab.common.mediation.Unit;
//import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
//import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//import org.integratedmodelling.klab.rest.StyledKimToken;
//import org.integratedmodelling.klab.utils.Pair;
//import org.integratedmodelling.klab.utils.Range;
//import org.integratedmodelling.klab.utils.StringUtil;
//import org.integratedmodelling.klab.utils.StringUtils;
//import org.integratedmodelling.klab.utils.Utils;
//
///**
// * A state machine similar to ObservableBuilder but starting empty and including constraints on what
// * can be specified next, based on the current state. Meant to support interactive, sentence-like
// * building of an observable, and capable of recursing to specify arguments to operators. Contains
// * most of the method of IObservable.Builder plus a scope-setting operator to open or close the
// * current scope.
// * <p>
// * Compared to IObservable.Builder, this can only be used to create an observable from scratch and
// * the only modifications admitted are through an undo stack. It does support nested observation
// * building, which the standard builder does not. It also validates the input at each operation and
// * can produce the acceptable next steps in the current state, to inform assisted UIs that need to
// * filter options.
// * 
// * @author Ferd
// *
// */
//public class ObservableComposer {
//
//    ObservableComposer parent;
//
//    /**
//     * If a context subject type is passed, the concept built will be checked for context
//     * compatibility. This is only applicable at root level.
//     */
//    IConcept contextSubject;
//
//    /**
//     * The current state is the one at the top of the stack. Undo will pop it and revert to the
//     * previous. All operations that modify the stack push a new one.
//     */
//    Stack<State> state = new Stack<>();
//
//    /**
//     * Can be set to report errors during interactive building. If the UI is right, there should not
//     * be any need of this because the choices should only be legal.
//     */
//    Consumer<String> errorHandler;
//
//    /**
//     * If set, all calls in a scope that allows the building of a valid observable are followed by a
//     * construction of the observable and whatever validation this does. This is potentially
//     * expensive.
//     */
//    Consumer<IObservable> validator;
//
//    /**
//     * This is set in case of error, before calling the error handler. Singleton across the
//     * hierarchy, never null.
//     */
//    List<String> errors;
//
//    IMonitor monitor;
//
//    /**
//     * If set, will be used to validate the units for extensive properties.
//     */
//    IGeometry geometry;
//
//    // user data to remember stuff in interactive use.
//    private Map<String, Object> data = new HashMap<>();
//
//    private ObservableComposer() {
//        this.state.push(new State());
//    }
//
//    private ObservableComposer(ObservableComposer parent, ObservableRole role) {
//
//        this.errorHandler = parent.errorHandler;
//        this.validator = parent.validator;
//        this.geometry = parent.geometry;
//        this.state.push(new State());
//        this.parent = parent;
//        this.state.peek().lexicalScope = role;
//        this.monitor = parent.monitor;
//        this.errors = parent.errors;
//
//    }
//
//    /**
//     * A logical constraint. If not negated, the arguments are required, otherwise they are
//     * prohibited. The argument list may contain IKimConcept.Type, IConcept, or other constraints.
//     * Multiple constraints in the logicalScope field are in OR; individual arguments in the
//     * constraint are in AND.
//     * 
//     * @author Ferd
//     *
//     */
//    public static class Constraint {
//        public boolean negated = false;
//        public Collection<Object> arguments = new HashSet<>();
//
//        private Constraint() {
//        }
//
//        public static Constraint not(IConcept arg) {
//            Constraint ret = new Constraint();
//            ret.arguments.add(arg);
//            ret.negated = true;
//            return ret;
//        }
//
//        public static Constraint of(Object... objects) {
//            Constraint ret = new Constraint();
//            for (Object o : objects) {
//                ret.arguments.add(o);
//            }
//            return ret;
//        }
//
//        public boolean matches(IConcept concept) {
//            for (Object o : arguments) {
//                if (o instanceof IKimConcept.Type && (!concept.is((Type) o) || (negated && concept.is((Type) o)))) {
//                    return false;
//                } else if (o instanceof Constraint && (!((Constraint) o).matches(concept))
//                        || (negated && ((Constraint) o).matches(concept))) {
//                    return false;
//                } // TODO continue
//            }
//            return negated ? false : true;
//        }
//
//        public String toString() {
//            return (negated ? "<NOT " : "<") + arguments + ">";
//        }
//
//    }
//
//    class Token {
//    }
//
//    class ConceptHolder {
//
//        public IConcept simple;
//        private IConcept resolved;
//        public ObservableComposer complex;
//
//        public ConceptHolder(ObservableComposer composer) {
//            this.complex = composer;
//        }
//
//        public ConceptHolder(IConcept concept) {
//            this.simple = concept;
//        }
//
//        public IConcept resolved() {
//            if (simple != null) {
//                return simple;
//            }
//            if (resolved == null) {
//                resolved = this.complex.buildConcept();
//            }
//            return resolved;
//        }
//    }
//
//    class State {
//
//        public State() {
//
//        }
//
//        public State(State other) {
//
//            this.lexicalScope = other.lexicalScope;
//            this.lexicalRealm = EnumSet.copyOf(other.lexicalRealm);
//            this.logicalRealm = new HashSet<>(other.logicalRealm);
//            this.inherent = other.inherent;
//            this.cooccurrent = other.cooccurrent;
//            this.context = other.context;
//            this.adjacent = other.adjacent;
//            this.caused = other.caused;
//            this.causant = other.causant;
//            this.goal = other.goal;
//            this.relationshipSource = other.relationshipSource;
//            this.relationshipTarget = other.relationshipTarget;
//            this.comparisonTarget = other.comparisonTarget;
//            this.valueOperators.addAll(other.valueOperators);
//            this.concepts.addAll(other.concepts);
//            this.unaryOperator = other.unaryOperator;
//            this.unaryOperatorArgument = other.unaryOperatorArgument;
//            this.name = other.name;
//            this.unit = other.unit;
//            this.currency = other.currency;
//            this.range = other.range;
//        }
//
//        /*
//         * the scope of this composer (role that it has been created to fill). Only null in the root
//         * scope.
//         */
//        ObservableRole lexicalScope;
//
//        /**
//         * Admitted concept types that can be added in this scope. Empty means everything's allowed,
//         * filled means any of those but nothing else.
//         */
//        Set<Constraint> logicalRealm = new HashSet<>();
//
//        /**
//         * Admitted lexical realm for this scope. Calls that fulfill a role which isn't here are
//         * illegal. Empty means everything's allowed, filled means any of those but nothing else.
//         */
//        Set<ObservableRole> lexicalRealm = EnumSet.noneOf(ObservableRole.class);
//
//        /*
//         * a composer for each of the possible components.
//         */
//        ObservableComposer inherent = null;
//        ObservableComposer cooccurrent = null;
//        ObservableComposer context = null;
//        ObservableComposer adjacent = null;
//        ObservableComposer caused = null;
//        ObservableComposer causant = null;
//        ObservableComposer compresent = null;
//        ObservableComposer goal = null;
//        ObservableComposer relationshipSource = null;
//        ObservableComposer relationshipTarget = null;
//        ObservableComposer comparisonTarget = null;
//        List<Pair<ValueOperator, ObservableComposer>> valueOperators = new ArrayList<>();
//        // this is either a concept or another composer (parenthesized)
//        List<ConceptHolder> concepts = new ArrayList<>();
//        UnarySemanticOperator unaryOperator = null;
//        ObservableComposer unaryOperatorArgument = null;
//        String name;
//        IUnit unit;
//        ICurrency currency;
//        Range range;
//
//        public boolean hasObservable;
//
//        public Type type;
//    }
//
//    private State pushState() {
//        State ret = new State(this.state.peek());
//        this.state.push(ret);
//        return ret;
//    }
//
//    /**
//     * Initialization parameters can be a concept or observable (to initialize with), an
//     * IObservable.Builder, or any ObservableRole or IKimConcept.Type to use as constraints for the
//     * possible content.
//     * <p>
//     * 
//     * 
//     * @param constraints
//     */
//    public static ObservableComposer create(Object... init) {
//        ObservableComposer ret = new ObservableComposer();
//        // admits unary ops, predicates and observables. No groups at root level.
//        ret.state.peek().lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
//        ret.state.peek().logicalRealm.add(Constraint.of(IKimConcept.Type.OBSERVABLE));
//        ret.state.peek().logicalRealm.add(Constraint.of(IKimConcept.Type.PREDICATE));
//        ret.errors = new ArrayList<>();
//        for (Object o : init) {
//            if (o instanceof IMonitor) {
//                ret.monitor = (IMonitor) o;
//            } else if (o instanceof ObservableRole) {
//                ret.state.peek().lexicalRealm.add((ObservableRole) o);
//            } else if (o instanceof Type) {
//                ret.state.peek().logicalRealm.add(Constraint.of(o));
//            } else {
//                throw new KlabIllegalStateException("ObservableComposer: can't use initialization parameter " + o);
//            }
//            // TODO more constraints
//        }
//        if (ret.monitor == null) {
//            ret.monitor = Klab.INSTANCE.getRootMonitor();
//        }
//
//        return ret;
//    }
//
//    public ObservableComposer constrainFor(UnarySemanticOperator role) {
//
//        this.state.peek().logicalRealm.clear();
//        this.state.peek().lexicalRealm.clear();
//        this.state.peek().concepts.clear();
//
//        // TODO! ADD OUTPUT TYPE
//        switch(role) {
//        case ASSESSMENT:
//            break;
//        case CHANGE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case CHANGED:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case COUNT:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case DISTANCE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case LEVEL:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case MAGNITUDE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case MONETARY_VALUE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
//        case NOT:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.DENIABLE));
//            break;
//        case OBSERVABILITY:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
//        case OCCURRENCE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case PERCENTAGE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case PRESENCE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case PROBABILITY:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.EVENT));
//            break;
//        case PROPORTION:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case RATE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case RATIO:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case TYPE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.CLASS));
//            break;
//        case UNCERTAINTY:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            break;
//        case VALUE:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
//        default:
//            break;
//
//        }
//
//        return this;
//    }
//
//    public ObservableComposer constrainFor(SemanticModifier role) {
//
//        this.state.peek().logicalRealm.clear();
//        this.state.peek().lexicalRealm.clear();
//        this.state.peek().concepts.clear();
//
//        switch(role) {
//        case ADJACENT_TO:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case CAUSED_BY:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.QUALITY));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.PROCESS));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.EVENT));
//            break;
//        case CAUSING:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.PROCESS));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.EVENT));
//            break;
//        // case CONTAINED_IN:
//        // break;
//        // case CONTAINING:
//        // break;
//        case DURING:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.PROCESS));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.EVENT));
//            break;
//        case FOR:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
//        case LINKING:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case OF:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case TO:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//            break;
//        case WITH:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            break;
//        case WITHIN:
//            this.state.peek().logicalRealm.add(Constraint.of(Type.SUBJECT));
//            this.state.peek().logicalRealm.add(Constraint.of(Type.AGENT));
//            break;
//        default:
//            break;
//
//        }
//        return this;
//    }
//
//    private ObservableComposer get(ObservableRole scope, Object... objects) {
//        ObservableComposer ret = new ObservableComposer(this, scope);
//        ret.state.peek().lexicalRealm.clear();
//        ret.state.peek().logicalRealm.clear();
//        for (Object o : objects) {
//            if (o instanceof ObservableRole) {
//                ret.state.peek().lexicalRealm.add((ObservableRole) o);
//            } else if (o instanceof Type) {
//                ret.state.peek().logicalRealm.add(Constraint.of(o));
//            } else if (o instanceof UnarySemanticOperator) {
//                /*
//                 * prepare the child to receive the operator's argument
//                 */
//            } else if (o instanceof State) {
//                /*
//                 * copy the constraints from the passed state. If there's an observable add
//                 * predicates. Called when opening a group.
//                 */
//                ret.state.peek().logicalRealm.addAll(((State) o).logicalRealm);
//                ret.state.peek().lexicalRealm.addAll(((State) o).lexicalRealm);
//                if (ret.admits(Type.OBSERVABLE) && !ret.admits(Type.PREDICATE)) {
//                    ret.state.peek().logicalRealm.add(Constraint.of(Type.PREDICATE));
//                }
//            } else {
//                throw new KlabIllegalStateException("ObservableComposer: can't use initialization parameter " + o);
//            }
//        }
//
//        return ret;
//    }
//
//    private boolean admits(Type predicate) {
//        for (Constraint c : state.peek().logicalRealm) {
//            if (c.arguments.contains(predicate) && !c.negated) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Should be checked after each operation
//     * 
//     * @return
//     */
//    public boolean isError() {
//        return !this.errors.isEmpty();
//    }
//
//    /**
//     * Latest error recorded. Each new error overwrites it.
//     * 
//     * @return
//     */
//    public Collection<String> getErrors() {
//        return this.errors;
//    }
//
//    /**
//     * If this composer is used to elicit observables in a known context, set the context type in
//     * here for further validation of the root-level observable.
//     * 
//     * @param context
//     * @return
//     */
//    public ObservableComposer setContext(IConcept context, IGeometry geometry) {
//        if (parent != null) {
//            throw new KlabIllegalStateException("ObservableComposer: cannot set the observation context at non-root level");
//        }
//        this.contextSubject = context;
//        this.geometry = geometry;
//        return this;
//    }
//
//    /**
//     * If an error handler is set in the root composer, it will be invoked after each wrong setting
//     * with an error message and the action will be rejected.
//     * 
//     * @param handler
//     * @return
//     */
//    public ObservableComposer withErrorHandler(Consumer<String> handler) {
//        this.errorHandler = handler;
//        return this;
//    }
//
//    /**
//     * Gives back the composer before the last operation.
//     * 
//     * @return
//     */
//    public ObservableComposer undo() {
//        if (this.state.size() > 1) {
//            this.state.pop();
//        } else if (this.parent != null) {
//            return this.parent;
//        }
//        return null;
//    }
//
//    /**
//     * Return the allowed inputs categories from a logical perspective, including operators, values,
//     * units and specific observable roles within the observable (e.g. inherent, cooccurrent etc.).
//     * Meant to select the possible non-concept tokens that can be used in the current state.
//     * 
//     * @return
//     */
//    public Set<ObservableRole> getAdmittedLexicalInput() {
//        return state.peek().lexicalRealm;
//    }
//
//    /**
//     * Return the concept constraints admitted for the conceptual input at this stage. If empty, no
//     * concepts can be input at this time. If both the lexical and the logical inputs are empty, the
//     * only possible input is undo.
//     * <p>
//     * FIXME this will need to admit AND-like collections to contrast in OR with their siblings.
//     * E.g. (ABSTRACT CLASS) or (SUBJECT). It will also need to admit (NOT <concept>) or (NOT
//     * <role>).
//     * 
//     * @return
//     */
//    public Set<Constraint> getAdmittedLogicalInput() {
//        return state.peek().logicalRealm;
//    }
//
//    /**
//     * Open a "parenthesized" scope.
//     * 
//     * @return
//     */
//    public ObservableComposer open() {
//        State s = pushState();
//        ObservableComposer ret = get(ObservableRole.GROUP_OPEN, s);
//        s.concepts.add(new ConceptHolder(ret));
//        return ret;
//    }
//
//    /**
//     * Close the inner scope. Note that proper use is a responsibility of the caller.
//     * 
//     * @return
//     */
//    public ObservableComposer close() {
//        return parent;
//    }
//
//    /**
//     * Get the scope for an inherent type to the concept built so far
//     * 
//     * @param inherent
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer of() {
//
//        if (!state.peek().lexicalRealm.contains(ObservableRole.INHERENT)) {
//            error("Current logics does not admit inherency");
//        }
//
//        State s = pushState();
//        s.inherent = new ObservableComposer(this, ObservableRole.INHERENT);
//        s.inherent.state.peek().lexicalRealm.clear();
//        s.inherent.state.peek().logicalRealm.clear();
//        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.COUNTABLE));
//        return s.inherent;
//    }
//
//    /**
//     * @param compresent
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer with() {
//        State s = pushState();
//        s.compresent = new ObservableComposer(this, ObservableRole.COMPRESENT);
//        return s.compresent;
//    }
//
//    /**
//     * @param context
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer within() {
//        State s = pushState();
//        s.context = new ObservableComposer(this, ObservableRole.CONTEXT);
//        return s.context;
//    }
//
//    /**
//     * @param goal
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer goal() {
//        State s = pushState();
//        s.goal = new ObservableComposer(this, ObservableRole.GOAL);
//        return s.goal;
//    }
//
//    /**
//     * @param causant
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer from() {
//        State s = pushState();
//        s.causant = new ObservableComposer(this, ObservableRole.CAUSANT);
//        return s.causant;
//    }
//
//    /**
//     * @param caused
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer to() {
//        State s = pushState();
//        s.caused = new ObservableComposer(this, ObservableRole.CAUSED);
//        return s.caused;
//    }
//
//    // public ObservableComposer operator(UnarySemanticOperator type) {
//    // if (state.peek().unaryOperator != null || defines(Type.OBSERVABLE)) {
//    // error("cannot add an operator once another operator or an observable is defined");
//    // return this;
//    // }
//    // state.peek().unaryOperatorArgument = get(ObservableRole.UNARY_OPERATOR, type);
//    // return state.peek().unaryOperatorArgument;
//    // }
//
//    /**
//     * Sets the "comparison" type for operators that admit one, such as ratios or proportions.
//     * 
//     * @param type
//     * @return
//     */
//    public ObservableComposer comparedTo() {
//        return null;
//    }
//
//    /**
//     * Accept an input from the client side and behave accordingly. Input can be concept, semantic
//     * modifier, operator (value, unary or binary) or a syntactic element like a string (only
//     * parentheses and "in" for units are accepted).
//     *
//     * @param concepts
//     * @return the same builder this was called on, for chaining calls
//     */
//    public ObservableComposer accept(Object input) {
//
//        if (input instanceof IConcept) {
//
//            IConcept concept = (IConcept) input;
//            /*
//             * check if concept is acceptable
//             */
//            if (!state.peek().logicalRealm.isEmpty()) {
//                if (!checkLogicalConstraints(concept)) {
//                    error("concept " + concept + " is not compatible with the current definition: expecting one of "
//                            + state.peek().logicalRealm);
//                    return this;
//                }
//            }
//
//            if (!validateConcept(concept)) {
//                return this;
//            }
//
//            // boolean inGroup = state.peek().lexicalRealm.contains(ObservableRole.GROUP_CLOSE);
//
//            State s = pushState();
//            s.concepts.add(new ConceptHolder(concept));
//            // reviseRealms(concept);
//
//            if (concept.is(Type.OBSERVABLE) && parent != null) {
//
//                /*
//                 * If our parent is a group (FIXME we should look it up, not just look at the direct
//                 * parent) and the group is under an operator, we don't close the group, otherwise
//                 * the observable is the end of the inner specification.
//                 */
//                boolean isGroupOperand = parent.state.peek().lexicalScope == ObservableRole.GROUP_OPEN && parent.parent != null
//                        && parent.parent.state.peek().lexicalScope != null
//                        && parent.parent.state.peek().lexicalScope.subsumesObservable;
//
//                if (!isGroupOperand) {
//                    return this.parent.reviseConstraints();
//                }
//            }
//
//        } else if (input instanceof UnarySemanticOperator) {
//
//            if (state.peek().unaryOperator != null || defines(Type.OBSERVABLE)) {
//                error("cannot add an operator once another operator or an observable is defined");
//                return this;
//            }
//            // put the naked operator on the undo stack and set the argument into the current for
//            // later completion.
//            state.peek().unaryOperator = (UnarySemanticOperator) input;
//            State s = pushState();
//            s.unaryOperatorArgument = get(ObservableRole.UNARY_OPERATOR, input).constrainFor((UnarySemanticOperator) input);
//            return this;
//
//        } else if (input instanceof SemanticModifier) {
//
//            State s = pushState();
//            switch((SemanticModifier) input) {
//            case ADJACENT_TO:
//                return (s.adjacent = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case CAUSED_BY:
//                return (s.causant = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case CAUSING:
//                return (s.caused = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            // case CONTAINED_IN:
//            // return (s.compresent = new ObservableComposer(this, ((SemanticModifier)input).role));
//            // case CONTAINING:
//            // return (s.compresent = new ObservableComposer(this, ((SemanticModifier)input).role));
//            case DURING:
//                return (s.cooccurrent = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case FOR:
//                return (s.goal = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case LINKING:
//                return (s.relationshipSource = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case OF:
//                return (s.inherent = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case TO:
//                return (s.relationshipTarget = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case WITH:
//                return (s.compresent = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            case WITHIN:
//                return (s.context = new ObservableComposer(this, ((SemanticModifier) input).role)
//                        .constrainFor((SemanticModifier) input));
//            default:
//                break;
//            }
//
//            throw new KlabInternalErrorException("obsolete or unsupported semantic modifier value!");
//
//        } else if (input instanceof BinarySemanticOperator) {
//
//        } else if (input instanceof ValueOperator) {
//
//        }
//
//        return this.reviseConstraints();
//    }
//
//    private boolean checkLogicalConstraints(IConcept concept) {
//        for (Constraint constraint : state.peek().logicalRealm) {
//            if (constraint.matches(concept)) {
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * Check the logical consistency of a proposed added concept added besides the logical types
//     * admitted. If any inconsistency is detected, set the error and return.
//     * 
//     * This check involves:
//     * 
//     * observable vs. trait compatibility (enforce "applies to" if present)
//     * 
//     * context compatibility if a context is set for the composer
//     * 
//     * @param concept
//     */
//    private boolean validateConcept(IConcept concept) {
//
//        if (concept.is(Type.OBSERVABLE) && contextSubject != null) {
//            IConcept ctx = Observables.INSTANCE.getContext(concept);
//            if (!Observables.INSTANCE.isCompatible(ctx, contextSubject)) {
//                error("Observable " + concept + " cannot be observed in context " + contextSubject);
//                return false;
//            }
//        }
//        if (concept.is(Type.PREDICATE) && defines(Type.OBSERVABLE)) {
//            IConcept observable = getConcept(Type.OBSERVABLE);
//            Collection<IConcept> applicables = Observables.INSTANCE.getApplicableObservables(concept);
//            if (applicables.size() > 0) {
//                boolean ret = false;
//                for (IConcept applicable : applicables) {
//                    if (Observables.INSTANCE.isCompatible(observable, applicable)) {
//                        ret = true;
//                        break;
//                    }
//                }
//                if (!ret) {
//                    error("Predicate " + concept + " is not applicable for observable " + observable);
//                    return false;
//                }
//            }
//        }
//        return true;
//    }
//
//    private IConcept getConcept(Type type) {
//
//        if (this.state.peek().concepts != null) {
//            for (ConceptHolder c : this.state.peek().concepts) {
//                if (c.resolved().is(type)) {
//                    return c.resolved();
//                }
//            }
//        }
//
//        return null;
//    }
//
//    private Collection<IConcept> getConcepts(Type type) {
//
//        Set<IConcept> ret = new HashSet<>();
//
//        if (this.state.peek().concepts != null) {
//            for (ConceptHolder c : this.state.peek().concepts) {
//                if (type == null || c.resolved().is(type)) {
//                    ret.add(c.resolved());
//                }
//            }
//        }
//
//        return ret;
//    }
//
//    private Collection<IConcept> getConcepts() {
//        return getConcepts(null);
//    }
//
//    /**
//     * Call after accepting any modification to redefine what is allowed.
//     * 
//     * @return
//     */
//    private ObservableComposer reviseConstraints() {
//
//        State s = this.state.peek();
//
//        /*
//         * clear current
//         */
//        s.logicalRealm.clear();
//        s.lexicalRealm.clear();
//        s.type = null;
//
//        /*
//         * Check if we have an observable
//         */
//        IConcept observable = null;
//        List<IConcept> predicates = new ArrayList<>();
//
//        for (ConceptHolder ch : s.concepts) {
//            if (ch.resolved().is(Type.OBSERVABLE)) {
//                observable = ch.resolved();
//            } else {
//                predicates.add(ch.resolved());
//            }
//        }
//
//        /*
//         * if not and we have a unary operator, allow only the observables compatible with it in the
//         * next step.
//         */
//        s.hasObservable = observable != null;
//        if (observable == null) {
//            if (s.unaryOperator != null) {
//                if (s.unaryOperatorArgument != null && s.unaryOperatorArgument.defines(Type.OBSERVABLE)) {
//                    s.hasObservable = true;
//                    s.type = s.unaryOperator.returnType;
//                }
//            } else {
//                s.logicalRealm.add(Constraint.of(Type.PREDICATE));
//                // TODO no more than one per family of existing predicates
//                s.logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//            }
//        } else {
//            s.type = Kim.INSTANCE.getFundamentalType(((Concept) observable).getTypeSet());
//        }
//
//        if (s.hasObservable) {
//
//            /*
//             * If we do have it, no more concepts are allowed. Otherwise we can have more predicates
//             * (of a different base trait than those we have) and one observable, possibly
//             * constrained by the step above.
//             */
//
//            /*
//             * If we have an observable, we can have all the semantic modifiers that we don't have
//             * already and are compatible with it. WITH and DURING are alternative for continuants
//             * or occurrents.
//             */
//            if (s.adjacent == null && ObservableRole.ADJACENT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.ADJACENT);
//            }
//            if (s.context == null && ObservableRole.CONTEXT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.CONTEXT);
//            }
//            if (s.inherent == null && ObservableRole.INHERENT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.INHERENT);
//            }
//            if (s.cooccurrent == null && ObservableRole.COOCCURRENT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.COOCCURRENT);
//            }
//            if (s.compresent == null && ObservableRole.COMPRESENT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.COMPRESENT);
//            }
//            if (s.causant == null && ObservableRole.CAUSANT.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.CAUSANT);
//            }
//            if (s.caused == null && ObservableRole.CAUSED.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.CAUSED);
//            }
//
//            if (s.relationshipSource == null && ObservableRole.RELATIONSHIP_SOURCE.appliesTo(s.type)) {
//                s.lexicalRealm.add(ObservableRole.RELATIONSHIP_SOURCE);
//            } else if (s.relationshipTarget == null) {
//                s.lexicalRealm.add(ObservableRole.RELATIONSHIP_TARGET);
//            }
//
//            /*
//             * If we have a quality observable we admit value operators.
//             */
//            if (s.type != null && s.type.isQuality()) {
//
//                s.lexicalRealm.add(ObservableRole.VALUE_OPERATOR);
//
//                /*
//                 * If we have monetary values we admit currencies; if we have a physical property or
//                 * a count we admit units.
//                 */
//                if (parent == null) {
//                    if (s.type.admitsUnits()) {
//                        s.lexicalRealm.add(ObservableRole.UNIT);
//                    } else if (s.type.admitsCurrency()) {
//                        s.lexicalRealm.add(ObservableRole.CURRENCY);
//                    }
//                }
//            }
//        }
//
//        return this;
//    }
//
//    /**
//     * Revise the realms in the current state to match the added element contextually to the
//     * existing state. FIXME substitute with reviseConstraints() as the object has been added
//     * already.
//     * 
//     * @param added
//     * @deprecated use reviseConstraints() after any accepted operation.
//     */
//    private void reviseRealms(Object added) {
//
//        if (added instanceof IConcept) {
//
//            /*
//             * redefine all realms from scratch
//             */
//            this.state.peek().logicalRealm.clear();
//            this.state.peek().lexicalRealm.clear();
//
//            boolean canHazObservable = !((Concept) added).is(Type.OBSERVABLE) && !defines(Type.OBSERVABLE);
//
//            /*
//             * admitted concepts
//             */
//            if (canHazObservable) {
//                this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
//                if (this.state.peek().unaryOperator == null) {
//                    this.state.peek().lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
//                }
//            } else {
//                // we enforce that predicates are specified before the observable to reduce the
//                // possible states and enable "applies to" validation.
//                this.state.peek().logicalRealm.add(Constraint.of(Type.PREDICATE));
//            }
//
//            /*
//             * admitted roles
//             */
//            if (((Concept) added).is(Type.QUALITY)) {
//                this.state.peek().lexicalRealm.add(ObservableRole.VALUE_OPERATOR);
//                if (parent == null) {
//                    if (((Concept) added).is(Type.EXTENSIVE_PROPERTY) || ((Concept) added).is(Type.INTENSIVE_PROPERTY)
//                            || ((Concept) added).is(Type.NUMEROSITY)) {
//                        this.state.peek().lexicalRealm.add(ObservableRole.UNIT);
//                    } else if (((Concept) added).is(Type.MONETARY_VALUE)) {
//                        this.state.peek().lexicalRealm.add(ObservableRole.CURRENCY);
//                    }
//                }
//            }
//
//            if (!isError() && ((Concept) added).is(Type.OBSERVABLE)) {
//                // TODO all the applicable modifiers
//                if (!defines(ObservableRole.INHERENT)) {
//                    this.state.peek().lexicalRealm.add(ObservableRole.INHERENT);
//                }
//            }
//        }
//
//    }
//
//    private boolean defines(ObservableRole inherent) {
//        switch(inherent) {
//        case ADJACENT:
//            return this.state.peek().adjacent != null;
//        case CAUSANT:
//            return this.state.peek().causant != null;
//        case CAUSED:
//            return this.state.peek().caused != null;
//        case COMPRESENT:
//            return this.state.peek().compresent != null;
//        case CONTEXT:
//            return this.state.peek().context != null;
//        case COOCCURRENT:
//            return this.state.peek().cooccurrent != null;
//        case CURRENCY:
//            return this.state.peek().currency != null;
//        case GOAL:
//            return this.state.peek().goal != null;
//        case INHERENT:
//            return this.state.peek().inherent != null;
//        case RELATIONSHIP_SOURCE:
//            return this.state.peek().relationshipSource != null;
//        case RELATIONSHIP_TARGET:
//            return this.state.peek().relationshipTarget != null;
//        case INLINE_VALUE:
//            break;
//        case LOGICAL_OPERATOR:
//            break;
//        case ROLE:
//            break;
//        case TEMPORAL_INHERENT:
//            break;
//        case TRAIT:
//            break;
//        case UNARY_OPERATOR:
//            return this.state.peek().unaryOperator != null;
//        case UNIT:
//            return this.state.peek().unit != null;
//        case VALUE_OPERATOR:
//            break;
//        default:
//            break;
//
//        }
//        return false;
//    }
//
//    private Object isCompleting() {
//        return null;
//    }
//
//    private boolean defines(Type type) {
//        if (this.state.peek().concepts != null) {
//            for (ConceptHolder c : this.state.peek().concepts) {
//                if (c.resolved().is(type)) {
//                    return true;
//                }
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public String toString() {
//        return dump(0);
//    }
//    //
//    // public ObservableComposer submit(String trait) {
//    // IConcept c = Concepts.INSTANCE.getConcept(trait);
//    // if (c == null) {
//    // return error("Concept " + trait + " is unknown");
//    // }
//    // return accept(c);
//    // }
//
//    private ObservableComposer error(String string) {
//        if (errorHandler != null) {
//            errorHandler.accept(string);
//        }
//        this.errors.add(string);
//        return this;
//    }
//
//    public IConcept buildConcept() {
//
//        if (isError()) {
//            throw new KlabValidationException("Errors in definition: " + StringUtil.join(errors, "; "));
//        }
//
//        IConcept main = null;
//        List<IConcept> others = new ArrayList<>();
//
//        for (IConcept concept : getConcepts()) {
//            boolean mainSet = false;
//            if (main == null) {
//                main = concept;
//                mainSet = true;
//            }
//            if (concept.is(Type.OBSERVABLE)) {
//                main = concept;
//                mainSet = true;
//            }
//
//            if (!mainSet) {
//                others.add(concept);
//            }
//        }
//
//        ObservableBuilder builder = new ObservableBuilder(main, monitor);
//
//        for (IConcept c : others) {
//            if (c.is(Type.ROLE)) {
//                builder.withRole(c);
//            } else if (c.is(Type.PREDICATE)) {
//                builder.withTrait(c);
//            }
//        }
//
//        if (this.state.peek().unaryOperator != null) {
//            builder = (ObservableBuilder) builder.as(this.state.peek().unaryOperator,
//                    this.state.peek().comparisonTarget == null ? null : this.state.peek().comparisonTarget.buildConcept());
//        }
//
//        if (this.state.peek().adjacent != null) {
//            builder.withAdjacent(this.state.peek().adjacent.buildConcept());
//        }
//        if (this.state.peek().causant != null) {
//            builder.from(this.state.peek().causant.buildConcept());
//        }
//        if (this.state.peek().caused != null) {
//            builder.to(this.state.peek().caused.buildConcept());
//        }
//        if (this.state.peek().compresent != null) {
//            builder.with(this.state.peek().compresent.buildConcept());
//        }
//        if (this.state.peek().context != null) {
//            builder.within(this.state.peek().context.buildConcept());
//        }
//        if (this.state.peek().cooccurrent != null) {
//            builder.withCooccurrent(this.state.peek().cooccurrent.buildConcept());
//        }
//        if (this.state.peek().goal != null) {
//            builder.withGoal(this.state.peek().goal.buildConcept());
//        }
//        if (this.state.peek().inherent != null) {
//            builder.of(this.state.peek().inherent.buildConcept());
//        }
//        if (this.state.peek().relationshipSource != null) {
//            IConcept source = this.state.peek().relationshipSource.buildConcept();
//            builder.linking(source,
//                    this.state.peek().relationshipTarget == null ? source : this.state.peek().relationshipTarget.buildConcept());
//        }
//
//        return builder.buildConcept();
//    }
//
//    /**
//     * Build an observable using the observable-specific options (currency, unit, classification and
//     * detail types). Use after constructing from an observable using
//     * {@link IObservable#getBuilder()}.
//     * 
//     * @return the built concept
//     * @throws KlabValidationException
//     */
//    public IObservable buildObservable() {
//        if (parent != null) {
//            throw new KlabIllegalStateException("ObservableComposer: can only call buildObservable on the root composer");
//        }
//
//        ObservableBuilder builder = new ObservableBuilder(buildConcept(), monitor);
//
//        // TODO the rest
//
//        if (this.state.peek().unit != null) {
//            builder.withUnit(this.state.peek().unit);
//        }
//
//        if (this.state.peek().currency != null) {
//            builder.withCurrency(this.state.peek().currency);
//        }
//
//        if (this.state.peek().name != null) {
//            builder.named(this.state.peek().name);
//        }
//
//        return builder.buildObservable();
//    }
//
//    public ObservableComposer cooccurrent() {
//        State s = pushState();
//        s.cooccurrent = new ObservableComposer(this, ObservableRole.COOCCURRENT);
//        return s.cooccurrent;
//    }
//
//    public ObservableComposer adjacent() {
//        State s = pushState();
//        s.adjacent = new ObservableComposer(this, ObservableRole.ADJACENT);
//        return s.adjacent;
//    }
//
//    /**
//     * Can only call on the root composer.
//     * 
//     * @param unit
//     * @return
//     */
//    public ObservableComposer withUnit(IUnit unit) {
//
//        if (parent != null) {
//            error("cannot set units on an inner observation");
//        }
//
//        IConcept observable = getConcept(Type.OBSERVABLE);
//        for (IConcept trait : getConcepts(Type.PREDICATE)) {
//            if (trait.is(Type.RESCALING)) {
//                error("cannot use unit " + unit + " on observable " + observable + " because the predicate " + trait
//                        + " removes it");
//                return this;
//            }
//        }
//
//        if (this.geometry != null) {
//            /*
//             * check the contextualization (no added constraints: if this is used for a semantic
//             * assistant in editor, may have to read annotations in context).
//             */
//            UnitContextualization ctx = Units.INSTANCE.getContextualization(Observable.promote(observable), geometry, null);
//
//            boolean ok = unit.isCompatible(ctx.getChosenUnit());
//            if (!ok) {
//                for (IUnit cun : ctx.getCandidateUnits()) {
//                    if (unit.isCompatible(cun)) {
//                        ok = true;
//                        break;
//                    }
//                }
//            }
//
//            if (!ok) {
//                error("unit " + unit + " is not compatible with observable " + observable + " in this geometry");
//            }
//
//        } else {
//            IUnit def = Units.INSTANCE.getDefaultUnitFor(observable);
//            if (!unit.isCompatible(def)) {
//                error("unit " + unit + " is not compatible with observable " + observable);
//            }
//        }
//
//        return this;
//    }
//
//    /**
//     * Can only call on the root composer.
//     * 
//     * @param unit
//     * @return
//     */
//    public ObservableComposer withCurrency(ICurrency currency) {
//        if (parent != null) {
//            error("cannot set currency on an inner observation");
//        }
//        return this;
//    }
//
//    /**
//     * Return a composer to define a value operator. A simple situation (elevation > 100) need only
//     * a call to withOperand() for the returned composer.
//     * 
//     * @param operator
//     * @param valueOperand
//     * @return
//     */
//    public ObservableComposer withValueOperator(ValueOperator operator) {
//        if (!state.peek().lexicalRealm.contains(ObservableRole.VALUE_OPERATOR)) {
//            error("the observable does not admit value operators");
//            return this;
//        }
//        State s = pushState();
//        ObservableComposer argument = this;
//        switch(operator) {
//        case AVERAGED:
//        case SUMMED:
//        case TOTAL:
//            argument = get(ObservableRole.VALUE_OPERATOR);
//        case BY:
//            // complex: either abstract class or subject. No easy way to express the
//            // "either" with
//            break;
//        case DOWN_TO:
//            // non-base class or number
//            break;
//        case LESS:
//        case LESSEQUAL:
//        case MINUS:
//        case OVER:
//        case PLUS:
//        case GREATER:
//        case GREATEREQUAL:
//            break;
//        case IS:
//            break;
//        case SAMEAS:
//            break;
//        case TIMES:
//            break;
//        case WHERE:
//            break;
//        case WITHOUT:
//            break;
//        default:
//            break;
//
//        }
//        s.valueOperators.add(new Pair<>(operator, argument));
//        return argument;
//    }
//
//    // public ObservableComposer withOperand(Object operand) {
//    // return null;
//    // }
//    //
//    // public ObservableComposer linkSource() {
//    // State s = pushState();
//    // s.relationshipSource = new ObservableComposer(this, ObservableRole.RELATIONSHIP_SOURCE);
//    // return s.relationshipSource;
//    // }
//    //
//    // public ObservableComposer linkTarget() {
//    // State s = pushState();
//    // s.relationshipTarget = new ObservableComposer(this, ObservableRole.RELATIONSHIP_TARGET);
//    // return s.relationshipTarget;
//    // }
//
//    /**
//     * Set the stated name for the observable, which will shadow the read-only "given" name based on
//     * the semantics (and make it inaccessible). The read-only reference name (uniquely linked to
//     * the semantics) remains unaltered. Can only call this on the root composer.
//     * 
//     * @param name
//     * @return
//     */
//    public ObservableComposer named(String name) {
//        if (parent != null) {
//            error("cannot set currency on an inner observable");
//        }
//        this.state.peek().name = name;
//        return this;
//    }
//
//    /**
//     * Pass the unit as a string (also checks for correctness at build). Can only call on the root
//     * composer.
//     * 
//     * @param unit
//     * @return
//     */
//    public ObservableComposer withUnit(String unit) {
//        IUnit u = Unit.create(unit);
//        if (u == null) {
//            error("unparseable unit: " + unit);
//        }
//        return withUnit(u);
//    }
//
//    /**
//     * Pass a currency as a string (also check for monetary value at build). Can only call on the
//     * root composer.
//     * 
//     * @param currency
//     * @return
//     */
//    public ObservableComposer withCurrency(String currency) {
//        ICurrency u = Currency.create(currency);
//        if (u == null) {
//            error("unparseable unit: " + currency);
//        }
//        return withCurrency(u);
//    }
//
//    /**
//     * Add a numeric range (check that the artifact type is numeric at build)
//     * 
//     * @param range
//     * @return
//     */
//    public ObservableComposer withRange(Range range) {
//        return null;
//    }
//
//    /**
//     * Add an annotation to the result observable.
//     * 
//     * @param annotation
//     * @return
//     */
//    public ObservableComposer withAnnotation(IAnnotation annotation) {
//        return null;
//    }
//
//    /**
//     * User data for interactive tracking of contexts, match proposals etc.
//     * 
//     * @return
//     */
//    public <T> T getData(String key, Class<T> cls) {
//        return Utils.asType(data.get(key), cls);
//    }
//
//    public void setData(String key, Object value) {
//        this.data.put(key, value);
//    }
//
//    /**
//     * Get the root composer, which can be used at any moment to produce the finished observable
//     * reflecting the current state.
//     * 
//     * @return
//     */
//    public ObservableComposer getRoot() {
//        ObservableComposer ret = this;
//        while(ret.parent != null) {
//            ret = ret.parent;
//        }
//        return ret;
//    }
//
//    /**
//     * Find out if we have an observable and which type it is. If it's unknown return null. Call on
//     * root.
//     * 
//     * @return
//     */
//    public IKimConcept.Type getObservableType() {
//        IKimConcept.Type ret = null;
//        for (ConceptHolder concept : state.peek().concepts) {
//            if (concept.resolved().is(Type.OBSERVABLE)) {
//                ret = Kim.INSTANCE.getFundamentalType(((Concept) concept.resolved()).getTypeSet());
//            }
//        }
//        if (ret != null && state.peek().concepts.size() > 0) {
//            ret = Kim.INSTANCE.getFundamentalType(((Concept) state.peek().concepts.get(0).resolved()).getTypeSet());
//        }
//
//        if (ret != null && state.peek().unaryOperator != null && state.peek().unaryOperatorArgument != null) {
//            ret = state.peek().unaryOperator.returnType;
//        }
//        return null;
//    }
//
//    public List<StyledKimToken> getStyledCode() {
//
//        List<StyledKimToken> ret = new ArrayList<>();
//
//        State s = state.peek();
//
//        if (s.lexicalScope == ObservableRole.GROUP_OPEN) {
//            ret.add(StyledKimToken.create("("));
//        }
//
//        List<StyledKimToken> traits = new ArrayList<>();
//        List<StyledKimToken> roles = new ArrayList<>();
//        StyledKimToken observable = null;
//        for (ConceptHolder c : s.concepts) {
//            if (c.resolved().is(Type.TRAIT)) {
//                traits.add(StyledKimToken.create(c.resolved()));
//            } else if (c.resolved().is(Type.ROLE)) {
//                roles.add(StyledKimToken.create(c.resolved()));
//            } else if (c.resolved().is(Type.OBSERVABLE)) {
//                observable = StyledKimToken.create(c.resolved());
//            }
//        }
//
//        if (!roles.isEmpty()) {
//            Collections.sort(roles, new Comparator<StyledKimToken>(){
//                @Override
//                public int compare(StyledKimToken o1, StyledKimToken o2) {
//                    return o1.getValue().compareTo(o2.getValue());
//                }
//            });
//            ret.addAll(roles);
//        }
//
//        if (!traits.isEmpty()) {
//            Collections.sort(traits, new Comparator<StyledKimToken>(){
//                @Override
//                public int compare(StyledKimToken o1, StyledKimToken o2) {
//                    return o1.getValue().compareTo(o2.getValue());
//                }
//            });
//            ret.addAll(traits);
//        }
//
//        if (s.unaryOperator != null) {
//            ret.add(StyledKimToken.create(s.unaryOperator));
//            if (s.unaryOperatorArgument.defines(Type.OBSERVABLE)) {
//                List<StyledKimToken> arg = s.unaryOperatorArgument.getStyledCode();
//                ret.addAll(arg);
//            } else {
//                ret.add(StyledKimToken.unknown());
//            }
//            if (s.comparisonTarget != null) {
//                ret.add(StyledKimToken.create(s.unaryOperator, true));
//                addStyledTokens(s.unaryOperator, s.comparisonTarget, ret, true);
//            }
//        }
//
//        if (observable != null) {
//            ret.add(observable);
//        }
//
//        addStyledTokens(SemanticModifier.OF, state.peek().inherent, ret, false);
//        addStyledTokens(SemanticModifier.WITHIN, state.peek().context, ret, false);
//        addStyledTokens(SemanticModifier.WITH, state.peek().compresent, ret, false);
//        addStyledTokens(SemanticModifier.CAUSED_BY, state.peek().causant, ret, false);
//        addStyledTokens(SemanticModifier.CAUSING, state.peek().caused, ret, false);
//        addStyledTokens(SemanticModifier.FOR, state.peek().goal, ret, false);
//        addStyledTokens(SemanticModifier.ADJACENT_TO, state.peek().adjacent, ret, false);
//
//        // don't switch the next two
//        addStyledTokens(SemanticModifier.LINKING, state.peek().relationshipSource, ret, false);
//        addStyledTokens(SemanticModifier.TO, state.peek().relationshipTarget, ret, false);
//
//        if (s.unit != null) {
//            ret.add(StyledKimToken.create("in"));
//            ret.add(StyledKimToken.create(s.unit));
//        } else if (s.currency != null) {
//            ret.add(StyledKimToken.create("in"));
//            ret.add(StyledKimToken.create(s.currency));
//        }
//
//        if (s.valueOperators != null) {
//
//        }
//
//        if (s.lexicalScope == ObservableRole.GROUP_OPEN) {
//            ret.add(StyledKimToken.create(")"));
//        }
//
//        return ret;
//    }
//
//    private void addStyledTokens(Object modifier, ObservableComposer composer, List<StyledKimToken> ret, boolean alternative) {
//
//        if (composer == null) {
//            return;
//        }
//
//        List<StyledKimToken> arg = composer.getStyledCode();
//        ret.add(StyledKimToken.create(modifier, alternative));
//
//        if (arg.size() > 1 || (arg.size() == 1 && !composer.defines(Type.OBSERVABLE))) {
//
//            ret.addAll(arg);
//            if (!composer.defines(Type.OBSERVABLE)) {
//                ret.add(StyledKimToken.unknown());
//            }
//
//        } else if (arg.size() > 0) {
//            ret.addAll(arg);
//        } else {
//            ret.add(StyledKimToken.unknown());
//        }
//    }
//
//    private String dump(int level) {
//        
//        String spacer = StringUtils.spaces(level);
//        
//        StringBuffer ret = new StringBuffer(1024);
//        ret.append(spacer + "Scope: " + state.peek().lexicalScope + "\n");
//        ret.append(spacer + "Log: " + state.peek().logicalRealm + "\n");
//        ret.append(spacer + "Lex: " + state.peek().lexicalRealm + "\n");
//        
//        return ret.toString();
//    }
//
//}
