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
package org.integratedmodelling.klab.api.runtime.dataflow;

import java.util.List;

import org.integratedmodelling.kim.api.IComputableResource;

// TODO: Auto-generated Javadoc
/**
 * Each node in a dataflow is an actuator. Compared to other workflow systems (e.g. Ptolemy), an
 * actuator is a composite actor; the individual actors are represented by k.LAB
 * {@link org.integratedmodelling.kim.api.IServiceCall}s.
 *
 * Some actuators may be references, corresponding to "input ports" in other workflow systems. In a
 * k.LAB computation, references are always resolved and the implementing which case the original
 * actuator will always be serialized before any references to it.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IActuator {

    /**
     * All actuators have a name. References may provide a different name for the same actuator.
     *
     * @return the name
     */
    String getName();

    /**
     * All child actuators in order of declaration.
     *
     * @return all the internal actuators in order of declaration.
     */
    public List<IActuator> getActuators();

    /**
     * Return the subset of actuators that reference others in the same dataflow.
     *
     * @return all imported actuators
     */
    List<IActuator> getInputs();

    /**
     * Return all actuators that have been declared as exported.
     *
     * @return all exported actuators
     */
    List<IActuator> getOutputs();

//    /**
//     * Each actuator may have a specific scale.
//     * 
//     * @return the scale. Only the root dataflow and those actuators for which a scale was
//     *         specifically given will return a non-empty scale.
//     */
//    IScale getScale();

    /**
     * Return the list of all computations in this actuator, or an empty list. Should always be empty
     * if the actuator is a reference to another.
     *
     * @return all computations. Never null, possibly empty.
     */
    List<IComputableResource> getComputation();

    /**
     * If this actuator is aliased to a different name within the containing actuator, return the
     * alias.
     *
     * @return the alias or null
     */
    String getAlias();

    /**
     * True if this actuator computes anything. Used when building dependencies (a computed actuator depends on
     * its children, which can otherwise be executed in parallel).
     *
     * @return a boolean.
     */
    boolean isComputed();
}
