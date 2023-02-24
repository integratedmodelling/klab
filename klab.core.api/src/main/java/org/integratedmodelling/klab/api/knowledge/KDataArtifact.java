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
package org.integratedmodelling.klab.api.knowledge;

import org.integratedmodelling.klab.api.data.mediation.classification.KDataKey;
import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.geometry.KLocator;

/**
 * A {@code IDataArtifact} is a
 * {@link org.integratedmodelling.klab.api.provenance.KArtifact} that is typed,
 * owns storage and admits
 * {@link org.integratedmodelling.klab.api.data.KLocator}s as indices for
 * getting and setting POD values in it. The storage must be conformant with the
 * {@link org.integratedmodelling.klab.api.data.KGeometry#size() size} and
 * dimensions of the {@link #getGeometry() linked} {@link KGeometry geometry}.
 * <p>
 * According to the context of computation, the size of a data artifact may
 * differ from {@link org.integratedmodelling.klab.api.data.KGeometry#size()}.
 * For example, a non-dynamic state in a dynamic context (where time advances
 * but the observable cannot be inferred to change in the context) may only
 * receive updates in case of event-related modifications. In such cases the
 * state may only contain the time dimensions where change has happened.
 * <p>
 *
 * @author Ferd
 * @version $Id: $Id
 */
public interface KDataArtifact extends KArtifact {

	/**
	 * Get the POD object pointed to by the locator. If the locator implies
	 * mediation, this should be supported. If the locator is incompatible with the
	 * geometry, throw an exception.
	 *
	 * @param index a locator for the state. If the locator implies mediation,
	 *              propagation or aggregation should be done.
	 * @return value at index
	 * @throws java.lang.IllegalArgumentException if the locator is not compatible
	 *                                            with the artifact's geometry.
	 */
	Object get(KLocator index);

	/**
	 * Get the POD object pointed to by the locator. If the locator implies
	 * mediation, this should be supported. If the locator is incompatible with the
	 * geometry, throw an exception.
	 *
	 * @param index a locator for the state. If the locator implies mediation,
	 *              propagation or aggregation should be done.
	 * @param cls   the class of the result we want
	 * @return value at index
	 * @throws java.lang.IllegalArgumentException if the locator is not compatible
	 *                                            with the artifact's geometry.
	 * @param <T> a T object.
	 */
	<T> T get(KLocator index, Class<T> cls);

	/**
	 * Set the value(s) at given index. Improper values or locators cause an
	 * unchecked exception.
	 *
	 * @param index a locator for the state. If the locator implies mediation,
	 *              propagation or aggregation should be done.
	 * @param value a compatible value. Usually of type T, but can be others - e.g.
	 *              a probability distribution for it. The state is expected to
	 *              quickly promote itself to a different underlying implementation
	 *              if a compatible value of a new type is expected.
	 * @return the linear offset corresponding to the locator in storage (for
	 *         checking and debugging only)
	 * @throws java.lang.IllegalArgumentException if value is incompatible with type
	 *                                            or locator is not compatible with
	 *                                            the geometry.
	 */
	long set(KLocator index, Object value);

	/**
	 * Total number of values. Must be compatible with the size of the dimensions of
	 * the underlying geometry.
	 *
	 * @return total count of states
	 */
	long size();

	/**
	 * If the individual values can be matched to an interpretive key, return it
	 * here.
	 * 
	 * @return the data key, or null.
	 */
	KDataKey getDataKey();

	/**
	 * Return a value aggregated over the passed geometry and converted to the
	 * passed type if necessary and possible.
	 * 
	 * @param geometry
	 * @param cls
	 * @return the aggregated value
	 * @throws IllegalArgumentException if the type can't fit the data or the
	 *                                  geometry is not covered by the original
	 *                                  geometry.
	 */
	<T> T aggregate(KLocator geometry, Class<? extends T> cls);

}
