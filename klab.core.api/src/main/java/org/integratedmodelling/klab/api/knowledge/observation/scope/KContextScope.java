package org.integratedmodelling.klab.api.knowledge.observation.scope;

import java.util.Collection;
import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.knowledge.KObservable;
import org.integratedmodelling.klab.api.knowledge.observation.KDirectObservation;
import org.integratedmodelling.klab.api.knowledge.observation.KObservation;
import org.integratedmodelling.klab.api.knowledge.observation.KRelationship;
import org.integratedmodelling.klab.api.knowledge.observation.KState;
import org.integratedmodelling.klab.api.provenance.KProvenance;
import org.integratedmodelling.klab.api.services.runtime.KDataflow;
import org.integratedmodelling.klab.api.services.runtime.KReport;

/**
 * The scope for a context and any observations made within it.
 * 
 * @author Ferd
 *
 */
public interface KContextScope extends KSessionScope {

    /**
     * Return the observer for this context. The original observation scope has the session user as
     * observer.
     * 
     * @return
     */
    KIdentity getObserver();

    /**
     * Return a child scope with the passed observer instead of ours.
     * 
     * @param scenarios
     * @return
     */
    KContextScope withObserver(KIdentity observer);

    /**
     * The context observation for this scope. When an observation scope is created, this is null
     * and must be set using {@link #within(KDirectObservation)} on the parent scope.
     * 
     * @return
     */
    KDirectObservation getContextObservation();

    /**
     * Return a scope focused on a specific root observation as the context for its
     * {@link #observe(Object...)} calls.
     * 
     * @param observation
     * @return a new scope focused on the passed observation.
     */
    KContextScope within(KDirectObservation observation);

    /**
     * Return a new observation scope that sets the passed scenarios for any future observation.
     * 
     * @param scenarios
     * @return
     */
    KContextScope withScenarios(String... scenarios);

    /**
     * Make an observation. Must be called on a context scope, possibly focused on a given root
     * observation using {@link #within(KDirectObservation)}. If no root observation is present in
     * the scope, the arguments must fully specify a subject, either through an
     * {@link IAcknowledgement} or a subject observable + a scale. If the parent session was focused
     * on a scale, this is available through {@link #getGeometry()} and the context can decide to
     * use it as a scale for the root subject.
     * <p>
     * In case the observable specifies a relationship, k.LAB will attempt to instantiate it,
     * observing its source/target endpoints as well, unless two subject observations are passed, in
     * which case a specified relationship will be instantiated between them using them as source
     * and target respectively. In the latter case, each relationship will be resolved but
     * configuration detection will only happen upon exiting the scope where observe() is called.
     * <p>
     * If the observation is at root level, or connecting two root-level subject through a
     * relationship, the overall geometry of the context will be automatically adjusted.
     * 
     * @param observables either a {@link KObservable} (with a {@link KGeometry} if root subject) or
     *        a {@link IAcknowledgement} for a pre-specified root subject.
     * @return a future for the observation being contextualized.
     */
    Future<KObservation> observe(Object... observables);

    /**
     * <p>
     * getProvenance.
     * </p>
     *
     * @return the provenance graph. Null in an empty context.
     */
    KProvenance getProvenance();

    /**
     * There is one report per root context. Actuators will add sections to it as models are
     * computed, based on the documentation templates associated with models and their parts. The
     * report can be compiled and rendered at any time.
     * 
     * @return
     */
    KReport getReport();

    /**
     * During a contextualization there normally is a dataflow being run. This will only be null
     * only in special situations, e.g. when expressions are passed a convenience context in order
     * to be evaluated outside of contextualization.
     * 
     * @return
     */
    KDataflow<?> getDataflow();

    /**
     * Return the parent observation of the passed observation. The runtime context maintains the
     * logical structure graph (ignores grouping of artifacts).
     * 
     * @param observation
     * @return the parent, or null if root subject
     */
    KDirectObservation getParentOf(KObservation observation);

    /**
     * Return all children of the passed observation, using the logical structure (i.e. skipping
     * observation groups). The runtime context maintains the structure graph.
     * 
     * @param observation an observation. {@link KState States} have no children but no error should
     *        be raised.
     * @return the parent, or an empty collection if no children
     */
    Collection<KObservation> getChildrenOf(KObservation observation);

    /**
     * Inspect the network graph of the current context, returning all relationships that have the
     * passed subject as target.
     *
     * @param observation a {@link org.integratedmodelling.klab.api.KDirectObservation.IDirectObservation}
     *        object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<KRelationship> getOutgoingRelationships(KDirectObservation observation);

    /**
     * Inspect the network graph of the current context, returning all relationships that have the
     * passed subject as target.
     *
     * @param observation a {@link org.integratedmodelling.klab.api.KDirectObservation.IDirectObservation}
     *        object.
     * @return a {@link java.util.Collection} object.
     */
    Collection<KRelationship> getIncomingRelationships(KDirectObservation observation);

}