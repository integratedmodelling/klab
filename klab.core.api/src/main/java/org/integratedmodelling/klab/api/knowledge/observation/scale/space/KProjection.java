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
package org.integratedmodelling.klab.api.knowledge.observation.scale.space;

/**
 * Opaque interface for a coordinate reference system.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface KProjection {

	/**
	 * Unique identifier of projection, enough to rebuild it at another endpoint.
	 *
	 * @return a {@link java.lang.String} object.
	 */
	String getCode();

	/**
	 * Check if the values projected according to this projection express meters.
	 * 
	 * @return true if a meter projection
	 */
	boolean isMeters();

	/**
	 * If true, the projection uses the first coordinate for the S->N direction and
	 * the second for the W->E. This applies to the default lat-lon projection unless
	 * it was forced into sanity.
	 * 
	 * @return true if projection is silly
	 */
	boolean flipsCoordinates();

	/**
	 * Return a simple string in the format "EPSG:nnnn". Used to interface to dumber
	 * projection APIs. Do not expect this to work for non-EPSG CRSs though.
	 * @return
	 */
	String getSimpleSRS();

	/**
	 * Units 
	 * @return
	 */
	String getUnits();
}
