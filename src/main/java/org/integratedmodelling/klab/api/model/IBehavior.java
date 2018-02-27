package org.integratedmodelling.klab.api.model;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * The context-dependent part of a k.IM object's specification, including all scale constraints and
 * any action that the associated object executes at initialization, transitions or events.
 * 
 * TODO the behavior should imply a geometry that the validator should use to estabish proper
 * usage of concepts (e.g. densities and rates).
 * 
 * @author Ferd
 *
 */
public interface IBehavior extends Iterable<IAction> {

    /**
     * All extents specified in the behavior, which may or may not be associated to transitions
     * actions. Any extent returned here specifies the corresponding view in the associated object,
     * and will need to be harmonized with the context's before contextualization.
     * 
     * @param monitor a monitor to handle any conditions that may happen when evaluating the extent
     *        function calls
     * 
     * @return the extents specified; never null, possibly empty
     * @throws KlabException if functions raise exceptions or do not produce extents
     */
    Collection<IExtent> getExtents(IMonitor monitor) throws KlabException;
        
    /**
     * All the actions specified in the behavior for the specified trigger.
     *
     * @param trigger 
     * 
     * @return the actions in order of declaration.
     */
    List<IAction> getActions(IKimAction.Trigger trigger);

}
