package org.integratedmodelling.klab.api.knowledge;

import java.util.Map;

import org.integratedmodelling.kim.api.IKimConcept.ObservableRole;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.resolution.IResolutionScope.Mode;

/**
 * A container for an observable and a resolution mode with appropriate equality assessment that
 * matches interoperability rules (only consider the concept definition and any value operators, but
 * no units, range, currency or name). Used as a key in contextualizations where the observation
 * mode makes a difference, to create dependency trees in the contextualization scope. Can also
 * carry arbitrary data through a generic map.
 * 
 * TODO this must evolve into the implementation of IProvenance.Activity, returned by
 * Actuator.compute() and Scheduler.run().
 * 
 * @author Ferd
 *
 */
public interface IObservedConcept {

    /**
     * The observable. Never null.
     * 
     * @return
     */
    IObservable getObservable();

    /**
     * Observable type, for fluency.
     * 
     * @return
     */
    IConcept getConcept();

    /**
     * The observation mode.
     */
    Mode getMode();

    /**
     * Arbitrary data, such as an actuator to repeat the observation when dependencies change.
     * 
     * @return
     */
    Map<String, Object> getData();

    /**
     * The scope must be set explicitly or is null: it is used only to remember the original scope
     * during resolution if there are further resolution to be made after the first, so that we can
     * use the same namespace and conditions of the original resolution.
     * 
     * @return
     */
    IResolutionScope getScope();

    /**
     * Remove whatever plays the given role and return. For fluency in resolution.
     * 
     * @param role
     * @return
     */
	IObservedConcept without(ObservableRole role);

}
