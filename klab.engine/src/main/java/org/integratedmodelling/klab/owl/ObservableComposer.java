package org.integratedmodelling.klab.owl;

import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.function.Consumer;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Range;

/**
 * A state machine similar to ObservableBuilder but starting empty and including constraints on what
 * can be specified next, based on the current state. Meant to support interactive, sentence-like
 * building of an observable, and capable of recursing to specify arguments to operators. Contains
 * most of the method of IObservable.Builder plus a scope-setting operator to open or close the
 * current scope.
 * <p>
 * Compared to IObservable.Builder, this can only be used to create an observable from scratch and
 * only supports an undo stack for modifications. It does support nested observation building, which
 * the standard builder does not. It also validates the input at each operation and can produce the
 * acceptable next steps in the current state.
 * 
 * @author Ferd
 *
 */
public class ObservableComposer {

    ObservableComposer parent;

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
     * construction of the observable and validation. This is potentially expensive.
     */
    Consumer<IObservable> validator;

    private ObservableComposer() {
    }

    class State {

        /*
         * the scope of this composer (role that it has been created to fill). Only null in the root
         * scope.
         */
        ObservableRole lexicalScope;

        /**
         * Admitted concept types that can be added in this scope. Empty means everything's allowed,
         * filled means any of those but nothing else.
         */
        Set<IKimConcept.Type> logicalRealm = EnumSet.noneOf(IKimConcept.Type.class);

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
        List<Pair<ValueOperator, Object>> valueOperators;

        String name;
        String unit;
        String currency;
        Range range;

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
        // TODO
        return ret;
    }

    /**
     * If an error handler is set in the root composer, it will be invoked after each wrong setting
     * with an error message and the action will be rejected.
     * 
     * @param handler
     * @return
     */
    public ObservableComposer withErrorHandler(Consumer<String> handler) {
        return this;
    }

    /**
     * Gives back the composer before the last operation.
     * 
     * @return
     */
    public ObservableComposer undo() {
        return null;
    }

    public Set<ObservableRole> admits() {
        return state.peek().lexicalRealm;
    }

    public Set<IKimConcept.Type> admitsComponent() {
        return state.peek().logicalRealm;
    }

    /**
     * Set the observable. Error if already set in same scope. No scoping at this level.
     * 
     * @return
     */
    ObservableComposer withObservable(IConcept concept) {
        return null;
    }

    /**
     * Get the scope for an inherent type to the concept built so far
     * 
     * @param inherent
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer of() {
        return null;
    }

    /**
     * @param compresent
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer with() {
        return null;
    }
    /**
     * @param context
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer within() {
        return null;
    }

    /**
     * @param goal
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer goal() {
        return null;
    }

    /**
     * @param causant
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer from() {
        return null;
    }
    /**
     * @param caused
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer to() {
        return null;
    }

    ObservableComposer operator(UnarySemanticOperator type) {
        return null;
    }

    /**
     * Sets the "comparison" type for operators that admit one, such as ratios or proportions.
     * 
     * @param type
     * @return
     */
    ObservableComposer comparedTo() {
        return null;
    }

    /**
     * Add traits to the concept being built. Pair with (@link {@link #withTrait(Collection)} as
     * Java is WriteEverythingTwice, not DontRepeatYourself.
     *
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    ObservableComposer predicate(IConcept trait) {
        return null;
    }

    /**
     * Build the concept (if necessary) as specified in the configured ontology. If the concept as
     * specified already exists, just return it.
     * 
     * @return the built concept
     * @throws KlabValidationException
     */
    IConcept buildConcept() throws KlabValidationException {
        return null;
    }

    /**
     * Build an observable using the observable-specific options (currency, unit, classification and
     * detail types). Use after constructing from an observable using
     * {@link IObservable#getBuilder()}.
     * 
     * @return the built concept
     * @throws KlabValidationException
     */
    IObservable buildObservable() throws KlabValidationException {
        return null;
    }

    /**
     * Return any exceptions accumulated through the building process before build() is called. If
     * build() is called when getErrors() returns a non-empty collection, it will throw an exception
     * collecting the messages from all exception in the list.
     * 
     * @return any errors accumulated
     */
    Collection<KlabValidationException> getErrors() {
        return null;
    }

    ObservableComposer cooccurrent() {
        return null;
    }

    ObservableComposer adjacent() {
        return null;
    }

    /**
     * Can only call on the root composer.
     * 
     * @param unit
     * @return
     */
    ObservableComposer withUnit(IUnit unit) {
        return null;
    }

    /**
     * Can only call on the root composer.
     * 
     * @param unit
     * @return
     */
    ObservableComposer withCurrency(ICurrency currency) {
        return null;
    }

    /**
     * Return a composer to define a value operator. A simple situation (elevation > 100) need only
     * a call to withOperand() for the returned composer.
     * 
     * @param operator
     * @param valueOperand
     * @return
     */
    ObservableComposer withValueOperator(ValueOperator operator) {
        return null;
    }

    ObservableComposer withOperand(Object operand) {
        return null;
    }

    ObservableComposer linkSource() {
        return null;
    }

    ObservableComposer linkTarget() {
        return null;
    }

    /**
     * Set the stated name for the observable, which will shadow the read-only "given" name based on
     * the semantics (and make it inaccessible). The read-only reference name (uniquely linked to
     * the semantics) remains unaltered. Can only call this on the root composer.
     * 
     * @param name
     * @return
     */
    ObservableComposer named(String name) {
        return null;
    }

    /**
     * Set the temporal inherency for the occurrent observable we specify. Does not change the
     * semantics.
     * 
     * @param concept
     * @return
     */
    ObservableComposer during() {
        return null;
    }

    /**
     * Pass the unit as a string (also checks for correctness at build). Can only call on the root
     * composer.
     * 
     * @param unit
     * @return
     */
    ObservableComposer withUnit(String unit) {
        return null;
    }

    /**
     * Pass a currency as a string (also check for monetary value at build). Can only call on the
     * root composer.
     * 
     * @param currency
     * @return
     */
    ObservableComposer withCurrency(String currency) {
        return null;
    }

    /**
     * Add a numeric range (check that the artifact type is numeric at build)
     * 
     * @param range
     * @return
     */
    ObservableComposer withRange(Range range) {
        return null;
    }

    /**
     * Add an annotation to the result observable.
     * 
     * @param annotation
     * @return
     */
    ObservableComposer withAnnotation(IAnnotation annotation) {
        return null;
    }

    public static void main(String[] args) {

        // simplest
        ObservableComposer composer = create();
        composer.withObservable(Concepts.c("im:Height"));
        composer.of().withObservable(Concepts.c("biology:Tree")).predicate(Concepts.c("im:High"));
        composer.withUnit("m");
        System.out.println(composer.buildObservable().getDefinition());
    }

}
