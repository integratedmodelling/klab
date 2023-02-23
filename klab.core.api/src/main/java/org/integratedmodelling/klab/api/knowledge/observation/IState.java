/*
 * This file is part of k.LAB.
 * 
 * k.LAB is free software: you can redistribute it and/or modify it under the terms of the Affero
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * A copy of the GNU Affero General Public License is distributed in the root directory of the k.LAB
 * distribution (LICENSE.txt). If this cannot be found see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned in author tags. All
 * rights reserved.
 */
package org.integratedmodelling.klab.api.knowledge.observation;

import java.util.Iterator;

import org.integratedmodelling.klab.api.data.mediation.IValueMediator;
import org.integratedmodelling.klab.api.geometry.ILocator;
import org.integratedmodelling.klab.api.knowledge.IArtifact;
import org.integratedmodelling.klab.api.knowledge.IDataArtifact;

/**
 * A {@link org.integratedmodelling.klab.api.observations.IState} is the semantic
 * {@link org.integratedmodelling.klab.api.observations.IObservation} that specializes a
 * non-semantic {@link IDataArtifact data artifact}. Its {@link #getObservable() observable} is
 * always a quality.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IState extends IObservation, IDataArtifact {

    /**
     * If this is called with a type different from the original one returned by {@link #getType()},
     * an additional layer of storage is created and a corresponding state view is returned,
     * preserving the mapping so that calling
     * {@link #as(org.integratedmodelling.klab.api.provenance.SemanticType.Type)} on the returned state
     * with the original type will yield back this state. This is used to enable differently typed
     * intermediate computations when creating an artifact.
     * 
     * @param type
     * @return a typed view of this state.
     */
    IState as(IArtifact.Type type);

    /**
     * Iterate the values in the state as the specified type, converting when possible.
     * 
     * @param index
     * @param cls
     * @return a valid iterator. Never null.
     * @throws IllegalArgumentException if an iterator cannot be produced for the passed type.
     */
    <T> Iterator<T> iterator(ILocator index, Class<? extends T> cls);

    /**
     * Create a state that will see this state through a value mediator, both when setting and
     * getting. Will only create a new state if the mediator has an effect. Will appropriately
     * handle units that can be made compatible with the original by collapsing or distributing
     * scale extents.
     * 
     * @param mediator
     * @return a mediated state, or this.
     * @throws IllegalArgumentException if the mediator does not apply to the state.
     */
    IState in(IValueMediator mediator);

    @Override
    IState at(ILocator locator);

//    @Override
//    ISubjectiveState reinterpret(IDirectObservation observer);

    /**
     * Return the state value aggregated over entire state or a subset defined by a set of locators.
     * 
     * @param geometry
     * @param cls
     * @return the aggregated value
     * @throws IllegalArgumentException if the type can't fit the data or the geometry is not
     *         covered by the original geometry.
     */
    Object aggregate(ILocator... locators);

    /**
     * Fill all states with the passed value.
     * 
     * @param value
     * @param locators
     */
    void fill(Object value);


}
