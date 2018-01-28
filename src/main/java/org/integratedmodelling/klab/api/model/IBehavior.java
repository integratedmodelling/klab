package org.integratedmodelling.klab.api.model;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IExtent;

/**
 * The context-dependent part of a k.IM object's specification, including all
 * scale constraints and any action that the associated object executes at initialization,
 * transitions or events.
 * 
 * @author Ferd
 *
 */
public interface IBehavior extends Iterable<IAction> {

    /**
     * All extents specified in the behavior, which may or may not be associated to
     * transitions actions. Any extent returned here specifies the corresponding view in
     * the associated object, and will need to be harmonized with the context's
     * before contextualization.
     * 
     * @return
     */
    Collection<IExtent> getExtents();

}
