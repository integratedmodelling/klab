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
package org.integratedmodelling.klab.api.observations;

import java.util.Iterator;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact;

/**
 * A {@link org.integratedmodelling.klab.api.observations.IState} is the
 * semantic {@link org.integratedmodelling.klab.api.observations.IObservation}
 * that specializes a non-semantic {@link IDataArtifact data artifact}. Its
 * {@link #getObservable() observable} is always a quality.
 * <p>
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface IState extends IObservation, IDataArtifact {

	/**
	 * True if the state has the same value overall independent of scale. Used to
	 * optimize visualization, computation and storage.
	 *
	 * @return true if constant
	 */
	boolean isConstant();

	/**
	 * If this is called with a type different from the original one returned by
	 * {@link #getType()}, an additional layer of storage is created and a
	 * corresponding state view is returned, preserving the mapping so that calling
	 * {@link #as(org.integratedmodelling.klab.api.provenance.IArtifact.Type)} on
	 * the returned state with the original type will yield back this state. This is
	 * used to enable differently typed intermediate computations when creating an
	 * artifact.
	 * 
	 * @param type
	 * @return a typed view of this state.
	 */
	IState as(IArtifact.Type type);

	/**
	 * Iterate the values in the state as the specified type, converting when
	 * possible.
	 * 
	 * @param index
	 * @param cls
	 * @return a valid iterator. Never null.
	 * @throws IllegalArgumentException
	 *             if an iterator cannot be produced for the passed type.
	 */
	<T> Iterator<T> iterator(ILocator index, Class<? extends T> cls);

}
