package org.integratedmodelling.klab.owl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.mediation.IUnit.UnitContextualization;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.Utils;

/**
 * A state machine similar to ObservableBuilder but starting empty and including constraints on what
 * can be specified next, based on the current state. Meant to support interactive, sentence-like
 * building of an observable, and capable of recursing to specify arguments to operators. Contains
 * most of the method of IObservable.Builder plus a scope-setting operator to open or close the
 * current scope.
 * <p>
 * Compared to IObservable.Builder, this can only be used to create an observable from scratch and
 * the only modifications admitted are through an undo stack. It does support nested observation
 * building, which the standard builder does not. It also validates the input at each operation and
 * can produce the acceptable next steps in the current state, to inform assisted UIs that need to
 * filter options.
 * 
 * @author Ferd
 *
 */
public class ObservableComposer {

    ObservableComposer parent;

    /**
     * If a context subject type is passed, the concept built will be checked for context
     * compatibility. This is only applicable at root level.
     */
    IConcept contextSubject;

    /**
     * The current state is the one at the top of the stack. Undo will pop it and revert to the
     * previous. All operations that modify the stack push a new one.
     */
    Stack<State> state = new Stack<>();

    /**
     * Can be set to report errors during interactive building. If the UI is right, there should not
     * be any need of this because the choices should only be legal.
     */
    Consumer<String> errorHandler;

    /**
     * If set, all calls in a scope that allows the building of a valid observable are followed by a
     * construction of the observable and whatever validation this does. This is potentially
     * expensive.
     */
    Consumer<IObservable> validator;

    /**
     * This is set in case of error, before calling the error handler. Singleton across the
     * hierarchy, never null.
     */
    List<String> errors;

    IMonitor monitor;

    /**
     * If set, will be used to validate the units for extensive properties.
     */
    IGeometry geometry;

    // user data to remember stuff in interactive use.
    private Map<String, Object> data = new HashMap<>();

    private ObservableComposer() {
        this.state.push(new State());
    }

    private ObservableComposer(ObservableComposer parent, ObservableRole role) {
        this.errorHandler = parent.errorHandler;
        this.validator = parent.validator;
        this.geometry = parent.geometry;
        this.state.push(new State());
        this.parent = parent;
        this.state.peek().lexicalScope = role;
        this.monitor = parent.monitor;
        this.errors = parent.errors;
    }

    /**
     * A logical constraint. If not negated, the arguments are required, otherwise they are
     * prohibited. The argument list may contain IKimConcept.Type, IConcept, or other constraints.
     * Multiple constraints in the logicalScope field are in OR; individual arguments in the
     * constraint are in AND.
     * 
     * @author Ferd
     *
     */
    public static class Constraint {
        public boolean negated = false;
        public Collection<Object> arguments = new HashSet<>();

        private Constraint() {
        }

        public static Constraint not(IConcept arg) {
            Constraint ret = new Constraint();
            ret.arguments.add(arg);
            ret.negated = true;
            return ret;
        }

        public static Constraint of(Object... objects) {
            Constraint ret = new Constraint();
            for (Object o : objects) {
                ret.arguments.add(o);
            }
            return ret;
        }

        public boolean matches(IConcept concept) {
            for (Object o : arguments) {
                if (o instanceof IKimConcept.Type && !concept.is((Type) o)) {
                    return false;
                } // TODO the rest
            }
            return negated ? false : true;
        }

    }

    class Token {
    }

    class State {

        public State() {

        }

        public State(State other) {

            this.lexicalScope = other.lexicalScope;
            this.lexicalRealm = EnumSet.copyOf(other.lexicalRealm);
            this.logicalRealm = new HashSet<>(other.logicalRealm);
            this.inherent = other.inherent;
            this.cooccurrent = other.cooccurrent;
            this.context = other.context;
            this.adjacent = other.adjacent;
            this.caused = other.caused;
            this.causant = other.causant;
            this.goal = other.goal;
            this.relationshipSource = other.relationshipSource;
            this.relationshipTarget = other.relationshipTarget;
            this.comparisonTarget = other.comparisonTarget;
            this.valueOperators = other.valueOperators;
            this.concepts.addAll(other.concepts);
            this.groups.addAll(other.groups);
            this.unaryOperator = other.unaryOperator;
            this.name = other.name;
            this.unit = other.unit;
            this.currency = other.currency;
            this.range = other.range;
        }

        /*
         * the scope of this composer (role that it has been created to fill). Only null in the root
         * scope.
         */
        ObservableRole lexicalScope;

        /**
         * Admitted concept types that can be added in this scope. Empty means everything's allowed,
         * filled means any of those but nothing else.
         */
        Set<Constraint> logicalRealm = new HashSet<>();

        /**
         * Admitted lexical realm for this scope. Calls that fulfill a role which isn't here are
         * illegal. Empty means everything's allowed, filled means any of those but nothing else.
         */
        Set<ObservableRole> lexicalRealm = EnumSet.noneOf(ObservableRole.class);

        /*
         * a composer for each of the possible components.
         */
        ObservableComposer inherent = null;
        ObservableComposer cooccurrent = null;
        ObservableComposer context = null;
        ObservableComposer adjacent = null;
        ObservableComposer caused = null;
        ObservableComposer causant = null;
        ObservableComposer compresent = null;
        ObservableComposer goal = null;
        ObservableComposer relationshipSource = null;
        ObservableComposer relationshipTarget = null;
        ObservableComposer comparisonTarget = null;
        List<Pair<ValueOperator, Object>> valueOperators;
        List<IConcept> concepts = new ArrayList<>();
        List<ObservableComposer> groups = new ArrayList<>();
        UnarySemanticOperator unaryOperator = null;
        String name;
        String unit;
        String currency;
        Range range;
    }

    private State pushState() {
        State ret = new State(this.state.peek());
        this.state.push(ret);
        return ret;
    }

    /**
     * Initialization parameters can be a concept or observable (to initialize with), an
     * IObservable.Builder, or any ObservableRole or IKimConcept.Type to use as constraints for the
     * possible content.
     * <p>
     * 
     * 
     * @param constraints
     */
    public static ObservableComposer create(Object... init) {
        ObservableComposer ret = new ObservableComposer();
        // admits unary ops, predicates and observables. No groups at root level.
        ret.state.peek().lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
        ret.state.peek().logicalRealm.add(Constraint.of(IKimConcept.Type.OBSERVABLE));
        ret.state.peek().logicalRealm.add(Constraint.of(IKimConcept.Type.PREDICATE));
        ret.errors = new ArrayList<>();
        for (Object o : init) {
            if (o instanceof IMonitor) {
                ret.monitor = (IMonitor) o;
            } else if (o instanceof ObservableRole) {
                ret.state.peek().lexicalRealm.add((ObservableRole) o);
            } else if (o instanceof Type) {
                ret.state.peek().logicalRealm.add(Constraint.of(o));
            } else {
                throw new KlabIllegalStateException("ObservableComposer: can't use initialization parameter " + o);
            }
            // TODO more constraints
        }
        if (ret.monitor == null) {
            ret.monitor = Klab.INSTANCE.getRootMonitor();
        }

        return ret;
    }

    private ObservableComposer get(ObservableRole scope, Object... objects) {
        ObservableComposer ret = new ObservableComposer(this, scope);
        ret.state.peek().lexicalRealm.clear();
        ret.state.peek().logicalRealm.clear();
        for (Object o : objects) {
            if (o instanceof ObservableRole) {
                ret.state.peek().lexicalRealm.add((ObservableRole) o);
            } else if (o instanceof Type) {
                ret.state.peek().logicalRealm.add(Constraint.of(o));
            } else {
                throw new KlabIllegalStateException("ObservableComposer: can't use initialization parameter " + o);
            }
        }
        return ret;
    }

    /**
     * Should be checked after each operation
     * 
     * @return
     */
    public boolean isError() {
        return !this.errors.isEmpty();
    }

    /**
     * Latest error recorded. Each new error overwrites it.
     * 
     * @return
     */
    public Collection<String> getErrors() {
        return this.errors;
    }

    /**
     * If this composer is used to elicit observables in a known context, set the context type in
     * here for further validation of the root-level observable.
     * 
     * @param context
     * @return
     */
    public ObservableComposer setContext(IConcept context, IGeometry geometry) {
        if (parent != null) {
            throw new KlabIllegalStateException("ObservableComposer: cannot set the observation context at non-root level");
        }
        this.contextSubject = context;
        this.geometry = geometry;
        return this;
    }

    /**
     * If an error handler is set in the root composer, it will be invoked after each wrong setting
     * with an error message and the action will be rejected.
     * 
     * @param handler
     * @return
     */
    public ObservableComposer withErrorHandler(Consumer<String> handler) {
        this.errorHandler = handler;
        return this;
    }

    /**
     * Gives back the composer before the last operation.
     * 
     * @return
     */
    public ObservableComposer undo() {
        if (this.state.size() > 1) {
            this.state.pop();
        } // else beep loudly and raise a shitstorm
        return this;
    }

    /**
     * Return the allowed inputs categories from a logical perspective, including operators, values,
     * units and specific observable roles within the observable (e.g. inherent, cooccurrent etc.).
     * Meant to select the possible non-concept tokens that can be used in the current state.
     * 
     * @return
     */
    public Set<ObservableRole> getAdmittedLexicalInput() {
        return state.peek().lexicalRealm;
    }

    /**
     * Return the concept constraints admitted for the conceptual input at this stage. If empty, no
     * concepts can be input at this time. If both the lexical and the logical inputs are empty, the
     * only possible input is undo.
     * <p>
     * FIXME this will need to admit AND-like collections to contrast in OR with their siblings.
     * E.g. (ABSTRACT CLASS) or (SUBJECT). It will also need to admit (NOT <concept>) or (NOT
     * <role>).
     * 
     * @return
     */
    public Set<Constraint> getAdmittedLogicalInput() {
        return state.peek().logicalRealm;
    }

    /**
     * Get the scope for an inherent type to the concept built so far
     * 
     * @param inherent
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer of() {

        if (!state.peek().lexicalRealm.contains(ObservableRole.INHERENT)) {
            error("Current logics does not admit inherency");
        }

        State s = pushState();
        s.inherent = new ObservableComposer(this, ObservableRole.INHERENT);
        s.inherent.state.peek().lexicalRealm.clear();
        s.inherent.state.peek().logicalRealm.clear();
        s.inherent.state.peek().lexicalRealm.add(ObservableRole.GROUP_OPEN);
        // TODO add all the roles compatible with a subject/agent/relationship
        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.SUBJECT));
        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.AGENT));
        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.EVENT));
        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.RELATIONSHIP));
        s.inherent.state.peek().logicalRealm.add(Constraint.of(Type.PREDICATE));
        return s.inherent;
    }

    /**
     * @param compresent
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer with() {
        State s = pushState();
        s.compresent = new ObservableComposer(this, ObservableRole.COMPRESENT);
        return s.compresent;
    }

    /**
     * @param context
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer within() {
        State s = pushState();
        s.context = new ObservableComposer(this, ObservableRole.CONTEXT);
        return s.context;
    }

    /**
     * @param goal
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer goal() {
        State s = pushState();
        s.goal = new ObservableComposer(this, ObservableRole.GOAL);
        return s.goal;
    }

    /**
     * @param causant
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer from() {
        State s = pushState();
        s.causant = new ObservableComposer(this, ObservableRole.CAUSANT);
        return s.causant;
    }

    /**
     * @param caused
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer to() {
        State s = pushState();
        s.caused = new ObservableComposer(this, ObservableRole.CAUSED);
        return s.caused;
    }

    public ObservableComposer operator(UnarySemanticOperator type) {
        return null;
    }

    /**
     * Sets the "comparison" type for operators that admit one, such as ratios or proportions.
     * 
     * @param type
     * @return
     */
    public ObservableComposer comparedTo() {
        return null;
    }

    /**
     * Add traits to the concept being built. Pair with (@link {@link #withTrait(Collection)} as
     * Java is WriteEverythingTwice, not DontRepeatYourself.
     *
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    public ObservableComposer submit(IConcept trait) {

        /*
         * check if concept is acceptable
         */
        if (!state.peek().logicalRealm.isEmpty()) {
            if (!checkLogicalConstraints(trait)) {
                error("concept " + trait + " is not compatible with the current definition: expecting one of "
                        + state.peek().logicalRealm);
                return this;
            }
        }

        if (!validateConcept(trait)) {
            return this;
        }

        // boolean inGroup = state.peek().lexicalRealm.contains(ObservableRole.GROUP_CLOSE);

        State s = pushState();
        s.concepts.add(trait);
        reviseRealms(trait);

        // if (!isError() && inGroup) {
        // s.lexicalRealm.add(ObservableRole.GROUP_CLOSE);
        // }

        return this;
    }

    private boolean checkLogicalConstraints(IConcept concept) {
        for (Constraint constraint : state.peek().logicalRealm) {
            if (constraint.matches(concept)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check the logical consistency of a proposed added concept added besides the logical types
     * admitted. If any inconsistency is detected, set the error and return.
     * 
     * This check involves:
     * 
     * observable vs. trait compatibility (enforce "applies to" if present)
     * 
     * context compatibility if a context is set for the composer
     * 
     * @param concept
     */
    private boolean validateConcept(IConcept concept) {

        if (concept.is(Type.OBSERVABLE) && contextSubject != null) {
            IConcept ctx = Observables.INSTANCE.getContext(concept);
            if (!Observables.INSTANCE.isCompatible(ctx, contextSubject)) {
                error("Observable " + concept + " cannot be observed in context " + contextSubject);
                return false;
            }
        }
        if (concept.is(Type.PREDICATE) && defines(Type.OBSERVABLE)) {
            IConcept observable = getConcept(Type.OBSERVABLE);
            Collection<IConcept> applicables = Observables.INSTANCE.getApplicableObservables(concept);
            if (applicables.size() > 0) {
                boolean ret = false;
                for (IConcept applicable : applicables) {
                    if (Observables.INSTANCE.isCompatible(observable, applicable)) {
                        ret = true;
                        break;
                    }
                }
                if (!ret) {
                    error("Predicate " + concept + " is not applicable for observable " + observable);
                    return false;
                }
            }
        }
        return true;
    }

    private IConcept getConcept(Type type) {

        if (this.state.peek().concepts != null) {
            for (IConcept c : this.state.peek().concepts) {
                if (c.is(type)) {
                    return c;
                }
            }
        }
        if (this.state.peek().groups != null) {
            for (ObservableComposer c : this.state.peek().groups) {
                if (c.defines(type)) {
                    return c.buildConcept();
                }
            }
        }

        return null;
    }

    private Collection<IConcept> getConcepts(Type type) {

        Set<IConcept> ret = new HashSet<>();

        if (this.state.peek().concepts != null) {
            for (IConcept c : this.state.peek().concepts) {
                if (type == null || c.is(type)) {
                    ret.add(c);
                }
            }
        }
        if (this.state.peek().groups != null) {
            for (ObservableComposer c : this.state.peek().groups) {
                if (type == null || c.defines(type)) {
                    ret.add(c.buildConcept());
                }
            }
        }

        return ret;
    }

    private Collection<IConcept> getConcepts() {
        return getConcepts(null);
    }

    /**
     * Revise the realms in the current state to match the added element contextually to the
     * existing state.
     * 
     * @param added
     */
    private void reviseRealms(Object added) {

        if (added instanceof IConcept) {

            /*
             * redefine all realms from scratch
             */
            this.state.peek().logicalRealm.clear();
            this.state.peek().lexicalRealm.clear();

            boolean canHazObservable = !((Concept) added).is(Type.OBSERVABLE) && !defines(Type.OBSERVABLE);

            /*
             * admitted concepts
             */
            if (canHazObservable) {
                this.state.peek().logicalRealm.add(Constraint.of(Type.OBSERVABLE));
                if (this.state.peek().unaryOperator == null) {
                    this.state.peek().lexicalRealm.add(ObservableRole.UNARY_OPERATOR);
                }
            } else {
                // we enforce that predicates are specified before the observable to reduce the
                // possible states and enable "applies to" validation.
                this.state.peek().logicalRealm.add(Constraint.of(Type.PREDICATE));
            }

            /*
             * admitted roles
             */
            if (((Concept) added).is(Type.QUALITY)) {
                this.state.peek().lexicalRealm.add(ObservableRole.VALUE_OPERATOR);
                if (parent == null) {
                    if (((Concept) added).is(Type.EXTENSIVE_PROPERTY) || ((Concept) added).is(Type.INTENSIVE_PROPERTY)
                            || ((Concept) added).is(Type.NUMEROSITY)) {
                        this.state.peek().lexicalRealm.add(ObservableRole.UNIT);
                    } else if (((Concept) added).is(Type.MONETARY_VALUE)) {
                        this.state.peek().lexicalRealm.add(ObservableRole.CURRENCY);
                    }
                }
            }

            if (!isError() && ((Concept) added).is(Type.OBSERVABLE)) {
                // TODO all the applicable modifiers
                if (!defines(ObservableRole.INHERENT)) {
                    this.state.peek().lexicalRealm.add(ObservableRole.INHERENT);
                }
            }
        }

    }

    private boolean defines(ObservableRole inherent) {
        switch(inherent) {
        case ADJACENT:
            return this.state.peek().adjacent != null;
        case CAUSANT:
            return this.state.peek().causant != null;
        case CAUSED:
            return this.state.peek().caused != null;
        case COMPRESENT:
            return this.state.peek().compresent != null;
        case CONTEXT:
            return this.state.peek().context != null;
        case COOCCURRENT:
            return this.state.peek().cooccurrent != null;
        case CURRENCY:
            return this.state.peek().currency != null;
        case GOAL:
            return this.state.peek().goal != null;
        case INHERENT:
            return this.state.peek().inherent != null;
        case RELATIONSHIP_SOURCE:
            return this.state.peek().relationshipSource != null;
        case RELATIONSHIP_TARGET:
            return this.state.peek().relationshipTarget != null;
        case INLINE_VALUE:
            break;
        case LOGICAL_OPERATOR:
            break;
        case ROLE:
            break;
        case TEMPORAL_INHERENT:
            break;
        case TRAIT:
            break;
        case UNARY_OPERATOR:
            return this.state.peek().unaryOperator != null;
        case UNIT:
            return this.state.peek().unit != null;
        case VALUE_OPERATOR:
            break;
        default:
            break;

        }
        return false;
    }

    private Object isCompleting() {
        return null;
    }

    private boolean defines(Type type) {
        if (this.state.peek().concepts != null) {
            for (IConcept c : this.state.peek().concepts) {
                if (c.is(type)) {
                    return true;
                }
            }
        }
        if (this.state.peek().groups != null) {
            for (ObservableComposer c : this.state.peek().groups) {
                if (c.defines(type)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Scope: " + state.peek().lexicalScope + "\n" + "Log: " + state.peek().logicalRealm + "\n" + "Lex: "
                + state.peek().lexicalRealm + "\n";
    }

    public ObservableComposer submit(String trait) {
        IConcept c = Concepts.INSTANCE.getConcept(trait);
        if (c == null) {
            return error("Concept " + trait + " is unknown");
        }
        return submit(c);
    }

    private ObservableComposer error(String string) {
        if (errorHandler != null) {
            errorHandler.accept(string);
        }
        this.errors.add(string);
        return this;
    }

    public IConcept buildConcept() {

        if (isError()) {
            throw new KlabValidationException("Errors in definition: " + StringUtil.join(errors, "; "));
        }

        IConcept main = null;
        List<IConcept> others = new ArrayList<>();

        for (IConcept concept : getConcepts()) {
            boolean mainSet = false;
            if (main == null) {
                main = concept;
                mainSet = true;
            }
            if (concept.is(Type.OBSERVABLE)) {
                main = concept;
                mainSet = true;
            }

            if (!mainSet) {
                others.add(concept);
            }
        }

        ObservableBuilder builder = new ObservableBuilder(main, monitor);

        for (IConcept c : others) {
            if (c.is(Type.ROLE)) {
                builder.withRole(c);
            } else if (c.is(Type.PREDICATE)) {
                builder.withTrait(c);
            }
        }

        if (this.state.peek().unaryOperator != null) {
            builder = (ObservableBuilder) builder.as(this.state.peek().unaryOperator,
                    this.state.peek().comparisonTarget == null ? null : this.state.peek().comparisonTarget.buildConcept());
        }

        if (this.state.peek().adjacent != null) {
            builder.withAdjacent(this.state.peek().adjacent.buildConcept());
        }
        if (this.state.peek().causant != null) {
            builder.from(this.state.peek().causant.buildConcept());
        }
        if (this.state.peek().caused != null) {
            builder.to(this.state.peek().caused.buildConcept());
        }
        if (this.state.peek().compresent != null) {
            builder.with(this.state.peek().compresent.buildConcept());
        }
        if (this.state.peek().context != null) {
            builder.within(this.state.peek().context.buildConcept());
        }
        if (this.state.peek().cooccurrent != null) {
            builder.withCooccurrent(this.state.peek().cooccurrent.buildConcept());
        }
        if (this.state.peek().goal != null) {
            builder.withGoal(this.state.peek().goal.buildConcept());
        }
        if (this.state.peek().inherent != null) {
            builder.of(this.state.peek().inherent.buildConcept());
        }
        if (this.state.peek().relationshipSource != null) {
            IConcept source = this.state.peek().relationshipSource.buildConcept();
            builder.linking(source,
                    this.state.peek().relationshipTarget == null ? source : this.state.peek().relationshipTarget.buildConcept());
        }

        return builder.buildConcept();
    }

    /**
     * Build an observable using the observable-specific options (currency, unit, classification and
     * detail types). Use after constructing from an observable using
     * {@link IObservable#getBuilder()}.
     * 
     * @return the built concept
     * @throws KlabValidationException
     */
    public IObservable buildObservable() {
        if (parent != null) {
            throw new KlabIllegalStateException("ObservableComposer: can only call buildObservable on the root composer");
        }

        ObservableBuilder builder = new ObservableBuilder(buildConcept(), monitor);

        // TODO the rest

        if (this.state.peek().unit != null) {
            builder.withUnit(this.state.peek().unit);
        }

        if (this.state.peek().currency != null) {
            builder.withCurrency(this.state.peek().currency);
        }

        if (this.state.peek().name != null) {
            builder.named(this.state.peek().name);
        }

        return builder.buildObservable();
    }

    public ObservableComposer cooccurrent() {
        State s = pushState();
        s.cooccurrent = new ObservableComposer(this, ObservableRole.COOCCURRENT);
        return s.cooccurrent;
    }

    public ObservableComposer adjacent() {
        State s = pushState();
        s.adjacent = new ObservableComposer(this, ObservableRole.ADJACENT);
        return s.adjacent;
    }

    /**
     * Can only call on the root composer.
     * 
     * @param unit
     * @return
     */
    public ObservableComposer withUnit(IUnit unit) {

        if (parent != null) {
            error("cannot set units on an inner observation");
        }

        IConcept observable = getConcept(Type.OBSERVABLE);
        for (IConcept trait : getConcepts(Type.PREDICATE)) {
            if (trait.is(Type.RESCALING)) {
                error("cannot use unit " + unit + " on observable " + observable + " because the predicate " + trait
                        + " removes it");
                return this;
            }
        }

        if (this.geometry != null) {
            /*
             * check the contextualization (no added constraints: if this is used for a semantic
             * assistant in editor, may have to read annotations in context).
             */
            UnitContextualization ctx = Units.INSTANCE.getContextualization(Observable.promote(observable), geometry, null);

            boolean ok = unit.isCompatible(ctx.getChosenUnit());
            if (!ok) {
                for (IUnit cun : ctx.getCandidateUnits()) {
                    if (unit.isCompatible(cun)) {
                        ok = true;
                        break;
                    }
                }
            }

            if (!ok) {
                error("unit " + unit + " is not compatible with observable " + observable + " in this geometry");
            }

        } else {
            IUnit def = Units.INSTANCE.getDefaultUnitFor(observable);
            if (!unit.isCompatible(def)) {
                error("unit " + unit + " is not compatible with observable " + observable);
            }
        }

        return this;
    }

    /**
     * Can only call on the root composer.
     * 
     * @param unit
     * @return
     */
    public ObservableComposer withCurrency(ICurrency currency) {
        if (parent != null) {
            error("cannot set currency on an inner observation");
        }
        return this;
    }

    /**
     * Return a composer to define a value operator. A simple situation (elevation > 100) need only
     * a call to withOperand() for the returned composer.
     * 
     * @param operator
     * @param valueOperand
     * @return
     */
    public ObservableComposer withValueOperator(ValueOperator operator) {
        if (!state.peek().lexicalRealm.contains(ObservableRole.VALUE_OPERATOR)) {
            error("the observable does not admit value operators");
            return this;
        }
        State s = pushState();
        ObservableComposer argument = this;
        switch(operator) {
        case AVERAGED:
        case SUMMED:
        case TOTAL:
            argument = get(ObservableRole.VALUE_OPERATOR);
        case BY:
            // complex: either abstract class or subject. No easy way to express the
            // "either" with
            break;
        case DOWN_TO:
            // non-base class or number
            break;
        case LESS:
        case LESSEQUAL:
        case MINUS:
        case OVER:
        case PLUS:
        case GREATER:
        case GREATEREQUAL:
            break;
        case IS:
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
        s.valueOperators.add(new Pair<>(operator, argument));
        return argument;
    }

    public ObservableComposer withOperand(Object operand) {
        return null;
    }

    public ObservableComposer linkSource() {
        State s = pushState();
        s.relationshipSource = new ObservableComposer(this, ObservableRole.RELATIONSHIP_SOURCE);
        return s.relationshipSource;
    }

    public ObservableComposer linkTarget() {
        State s = pushState();
        s.relationshipTarget = new ObservableComposer(this, ObservableRole.RELATIONSHIP_TARGET);
        return s.relationshipTarget;
    }

    /**
     * Set the stated name for the observable, which will shadow the read-only "given" name based on
     * the semantics (and make it inaccessible). The read-only reference name (uniquely linked to
     * the semantics) remains unaltered. Can only call this on the root composer.
     * 
     * @param name
     * @return
     */
    public ObservableComposer named(String name) {
        if (parent != null) {
            error("cannot set currency on an inner observable");
        }
        this.state.peek().name = name;
        return this;
    }

    /**
     * Pass the unit as a string (also checks for correctness at build). Can only call on the root
     * composer.
     * 
     * @param unit
     * @return
     */
    public ObservableComposer withUnit(String unit) {
        IUnit u = Unit.create(unit);
        if (u == null) {
            error("unparseable unit: " + unit);
        }
        return withUnit(u);
    }

    /**
     * Pass a currency as a string (also check for monetary value at build). Can only call on the
     * root composer.
     * 
     * @param currency
     * @return
     */
    public ObservableComposer withCurrency(String currency) {
        ICurrency u = Currency.create(currency);
        if (u == null) {
            error("unparseable unit: " + currency);
        }
        return withCurrency(u);
    }

    /**
     * Add a numeric range (check that the artifact type is numeric at build)
     * 
     * @param range
     * @return
     */
    public ObservableComposer withRange(Range range) {
        return null;
    }

    /**
     * Add an annotation to the result observable.
     * 
     * @param annotation
     * @return
     */
    public ObservableComposer withAnnotation(IAnnotation annotation) {
        return null;
    }

    /**
     * User data for interactive tracking of contexts, match proposals etc.
     * 
     * @return
     */
    public <T> T getData(String key, Class<T> cls) {
        return Utils.asType(data.get(key), cls);
    }

    public void setData(String key, Object value) {
        this.data.put(key, value);
    }

    /**
     * Get the root composer, which can be used at any moment to produce the finished observable
     * reflecting the current state.
     * 
     * @return
     */
    public ObservableComposer getRoot() {
        ObservableComposer ret = this;
        while(ret.parent != null) {
            ret = ret.parent;
        }
        return ret;
    }
    
    
}

