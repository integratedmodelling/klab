/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify
 * it under the terms of the Affero GNU General Public License as published
 * by the Free Software Foundation, either version 3 of the License,
 * or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root
 * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
 * see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
 * in author tags. All rights reserved.
 */
package org.integratedmodelling.klab.api.model;

import java.util.Collection;
import java.util.List;
import org.integratedmodelling.kim.api.IKimAction;
import org.integratedmodelling.klab.api.knowledge.IConcept;
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
 * @version $Id: $Id
 */
public interface IBehavior extends Iterable<IAction> {

    /**
     * All extents specified in the behavior, which may or may not be associated to transitions
     * actions. Any extent returned here specifies the corresponding view in the associated object,
     * and will need to be harmonized with the context's before contextualization.
     *
     * @param monitor a monitor to handle any conditions that may happen when evaluating the extent
     *        function calls
     * @return the extents specified; never null, possibly empty
     * @throws org.integratedmodelling.klab.exceptions.KlabException if functions raise exceptions or do not produce extents
     */
    Collection<IExtent> getExtents(IMonitor monitor) throws KlabException;
        
    /**
     * All the actions specified in the behavior for the specified trigger.
     *
     * @param trigger a {@link org.integratedmodelling.kim.api.IKimAction.Trigger} object.
     * @return the actions in order of declaration.
     */
    List<IAction> getActions(IKimAction.Trigger trigger);

    /**
     * Quickly assess whether the passed event type has any associated actions. This may be
     * called many times so if the operation requires significant reasoning, the results
     * should be cached.
     *
     * @param eventType a {@link org.integratedmodelling.klab.api.knowledge.IConcept} object.
     * @return true if the event concept has actions associated
     */
    boolean respondsTo(IConcept eventType);
    
    /**
     * If true, the behavior specifies no actions.
     *
     * @return whether the behavior contains any actions.
     */
    boolean isEmpty();
}
