package org.integratedmodelling.klab.owl;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.kim.api.UnarySemanticOperator;
import org.integratedmodelling.kim.api.ValueOperator;
import org.integratedmodelling.klab.api.data.mediation.ICurrency;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.Builder;
import org.integratedmodelling.klab.api.knowledge.IObservable.Resolution;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Range;
import org.jgrapht.graph.DefaultDirectedGraph;

/**
 * A state machine similar to ObservableBuilder but starting empty and including constraints on what
 * can be specified next, based on the current state. Meant to support interactive, sentence-like
 * building of an observable, and capable of recursing to specify arguments to operators. Contains
 * most of the method of IObservable.Builder plus a scope-setting operator to open or close the
 * current scope.
 * 
 * @author Ferd
 *
 */
public class ObservableComposer extends DefaultDirectedGraph<ObservableBuilder, ObservableComposer.Link> {

    private static final long serialVersionUID = -3803600178476529848L;

    public static class Link {
        ObservableRole role;
    }

    ObservableComposer() {
        super(Link.class);
    }

    /*
     * the current element. Never null. The role is only null in the root element.
     */
    ObservableBuilder currentBuilder;
    ObservableComposer parent;
    Set<ObservableRole> currentConstraints = EnumSet.noneOf(ObservableRole.class);

    /**
     * Get a scope for the passed role. All operations on the returned builder will affect the
     * concepts in the scope. The scope MUST be closed for the operations to have effect.
     * Corresponds to the typing of an open parenthesis in any semantic scope that requires an
     * argument. If a scope isn't opened, only one argument will be used: X of Trait Y will be (X of
     * Trait) Y.
     * 
     * @param role
     * @return
     */
    public ObservableComposer openScope(ObservableRole role) {
        return null;
    }

    public ObservableComposer closeScope() {
        if (parent == null) {
            throw new KlabIllegalStateException("can't close the root observable composer scope");
        }
        return parent;
    }

    /**
     * Add an inherent type to the concept built so far.
     * 
     * @param inherent
     * @return the same builder this was called on, for chaining calls
     */
    Builder of(IConcept inherent) {
        return null;
    }

    /**
     * @param compresent
     * @return the same builder this was called on, for chaining calls
     */
    Builder with(IConcept compresent) {
        return null;
    }
    /**
     * @param context
     * @return the same builder this was called on, for chaining calls
     */
    Builder within(IConcept context) {
        return null;
    }

    /**
     * @param goal
     * @return the same builder this was called on, for chaining calls
     */
    Builder withGoal(IConcept goal) {
        return null;
    }

    /**
     * @param causant
     * @return the same builder this was called on, for chaining calls
     */
    Builder from(IConcept causant) {
        return null;
    }
    /**
     * @param caused
     * @return the same builder this was called on, for chaining calls
     */
    Builder to(IConcept caused) {
        return null;
    }
    /**
     * Add roles that become part of the semantics of the observable (Role Trait ... Observable)
     * 
     * @param role
     * @return the same builder this was called on, for chaining calls
     */
    Builder withRole(IConcept role) {
        return null;
    }
    /**
     * Transform the original concept into its equivalent filtered by the passed semantic operator.
     * For example, transform an original event into its probability by passing
     * SemanticOperator.PROBABILITY. If the operator implies additional operands (for example a
     * ratio) these should be passed after the semantic type. This one transforms the concept in the
     * builder right away, leaving nothing to do for build() but return the transformed concept,
     * unless more build actions are called after it. If the original concept cannot be transformed
     * into the specified one, build() will return an informative exception, but no error will be
     * reported when the method is called. The getErrors() call will report the exceptions
     * accumulated if necessary.
     * 
     * @param type
     * @param participants
     * @return the same builder this was called on, for chaining calls
     * @throws KlabValidationException
     */
    Builder as(UnarySemanticOperator type, IConcept... participants) {
        return null;
    }

    /**
     * Add traits to the concept being built. Pair with (@link {@link #withTrait(Collection)} as
     * Java is WriteEverythingTwice, not DontRepeatYourself.
     *
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    Builder withTrait(IConcept... concepts) {
        return null;
    }

    /**
     * Add traits to the concept being built. Pair with (@link {@link #withTrait(IConcept...)} as
     * Java is WriteEverythingTwice, not DontRepeatYourself.
     * 
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    Builder withTrait(Collection<IConcept> concepts) {
        return null;
    }

    /**
     * Remove traits or roles from the concept being built. Do nothing if the concept so far does
     * not have those traits or roles. Pair with (@link {@link #without(IConcept...)} as Java is
     * WriteEverythingTwice, not DontRepeatYourself.
     * 
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    Builder without(Collection<IConcept> concepts) {
        return null;
    }

    /**
     * Remove traits or roles from the concept being built. Do nothing if the concept so far does
     * not have those traits or roles. Pair with (@link {@link #without(Collection)} as Java is
     * WriteEverythingTwice, not DontRepeatYourself.
     *
     * @param concepts
     * @return the same builder this was called on, for chaining calls
     */
    Builder without(IConcept... concepts) {
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

    Builder withCooccurrent(IConcept cooccurrent) {
        return null;
    }

    Builder withAdjacent(IConcept adjacent) {
        return null;
    }

    Builder withoutAny(Collection<IConcept> concepts) {
        return null;
    }

    Builder withoutAny(IKimConcept.Type... type) {
        return null;
    }

    Builder withoutAny(IConcept... concepts) {
        return null;
    }

    Builder withUnit(IUnit unit) {
        return null;
    }

    Builder withCurrency(ICurrency currency) {
        return null;
    }

    /**
     * Value operators are added in the order they are received.
     * 
     * @param operator
     * @param valueOperand
     * @return
     */
    Builder withValueOperator(ValueOperator operator, Object valueOperand) {
        return null;
    }

    /**
     * After any of the "without" functions get called, this can be checked on the resulting builder
     * to see what exactly was removed.
     * 
     * @return
     */
    Collection<IConcept> getRemoved() {
        return null;
    }

    Builder linking(IConcept source, IConcept target) {
        return null;
    }

    /**
     * Set the stated name for the observable, which will shadow the read-only "given" name based on
     * the semantics (and make it inaccessible). The read-only reference name (uniquely linked to
     * the semantics) remains unaltered.
     * 
     * @param name
     * @return
     */
    Builder named(String name) {
        return null;
    }

    /**
     * Set the flag that signifies distributed inherency (of each).
     * 
     * @param ofEach
     * @return
     */
    Builder withDistributedInherency(boolean ofEach) {
        return null;
    }

    /**
     * Remove any value operators
     * 
     * @return
     */
    Builder withoutValueOperators() {
        return null;
    }

    /**
     * Tags the classifier of an abstract attribute as targeting a specific concrete attribute, so
     * that any classified objects that won't have that specific attribute can be recognized as
     * irrelevant to this observation and hidden.
     * 
     * @param targetPredicate
     * @return
     */
    Builder withTargetPredicate(IConcept targetPredicate) {
        return null;
    }

    /**
     * Set the observable resulting from buildObservable() as optional.
     * 
     * @param optional
     * @return
     */
    Builder optional(boolean optional) {
        return null;
    }

    /**
     * Remove all the elements <em>directly</em> stated in the current concept corresponding to the
     * passed role, if existing, and return a builder for the concept without them.
     * 
     * @param roles
     * @return
     */
    Builder without(ObservableRole... roles) {
        return null;
    }

    /**
     * Set the temporal inherency for the occurrent observable we specify. Does not change the
     * semantics.
     * 
     * @param concept
     * @return
     */
    Builder withTemporalInherent(IConcept concept) {
        return null;
    }

    /**
     * Add the dereified attribute to the observable. Will only affect the computations built from
     * it after it's resolved.
     * 
     * @param dereifiedAttribute
     * @return
     */
    Builder withDereifiedAttribute(String dereifiedAttribute) {
        return null;
    }

    /**
     * Set both the name and the reference name, to preserve a previous setting
     * 
     * @param name
     * @param referenceName
     * @return
     */
    Builder named(String name, String referenceName) {
        return null;
    }

    /**
     * Pass the unit as a string (also checks for correctness at build)
     * 
     * @param unit
     * @return
     */
    Builder withUnit(String unit) {
        return null;
    }

    /**
     * | Pass a currency as a string (also check for monetary value at build)
     * 
     * @param currency
     * @return
     */
    Builder withCurrency(String currency) {
        return null;
    }

    /**
     * Add an inline value to the observable (will check with the IArtifact.Type of the observable
     * at build).
     * 
     * @param value
     * @return
     */
    Builder withInlineValue(Object value) {
        return null;
    }

    /**
     * Add a numeric range (check that the artifact type is numeric at build)
     * 
     * @param range
     * @return
     */
    Builder withRange(Range range) {
        return null;
    }

    /**
     * Make this observable generic or not
     * 
     * @param generic
     * @return
     */
    Builder generic(boolean generic) {
        return null;
    }

    /**
     * Define the resolution type for the observable.
     * 
     * @param only
     * @return
     */
    Builder withResolution(Resolution only) {
        return null;
    }

    /**
     * Give or remove the fluid units trait
     * 
     * @param b
     * @return
     */
    Builder fluidUnits(boolean b) {
        return null;
    }

    /**
     * Add an annotation to the result observable.
     * 
     * @param annotation
     * @return
     */
    Builder withAnnotation(IAnnotation annotation) {
        return null;
    }

    /**
     * TODO check if still used
     * 
     * @param global
     * @return
     */
    Builder global(boolean global) {
        return null;
    }

}
