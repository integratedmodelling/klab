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

import org.integratedmodelling.klab.api.knowledge.observation.scale.IExtent;
import org.integratedmodelling.klab.api.knowledge.observation.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.lang.LogicalConnector;

/**
 * The Interface ISpace.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public interface ISpace extends IExtent, ISpatial {

	/** Constant <code>MIN_SCALE_RANK=0</code> */
	int MIN_SCALE_RANK = 0;
	/** Constant <code>MAX_SCALE_RANK=21</code> */
	int MAX_SCALE_RANK = 21;

	@Override
	ISpace getExtent(long stateIndex);
	
	/**
	 * Get the envelope, providing boundaries.
	 *
	 * @return the referenced envelope
	 */
	IEnvelope getEnvelope();

	/**
	 * Projection. Just repeats same in envelope and shape. It's not legal to have
	 * different projections in different elements of a spatial extent.
	 *
	 * @return coordinate reference system
	 */
	IProjection getProjection();

//	/**
//	 * Build a lat/lon descriptor for the extent we represent. This shouldn't really
//	 * be API.
//	 * 
//	 * @return
//	 */
//	SpatialExtent getExtentDescriptor();

	/**
	 * Volume in standard SI units (square meters), NaN if < 3D.
	 * 
	 * @return
	 */
	double getStandardizedVolume();

	/**
	 * Area in standard SI units (square meters), NaN if < 2D.
	 * 
	 * @return
	 */
	double getStandardizedArea();

	/**
	 * Width in standard SI units (meters), NaN if < 2D.
	 * 
	 * @return
	 */
	double getStandardizedWidth();

	/**
	 * Centroid in whatever standard coordinates the implementation uses.
	 * 
	 * @return
	 */
	double[] getStandardizedCentroid();

	/**
	 * Height in standard SI units (meters), NaN if 1D.
	 * 
	 * @return
	 */
	double getStandardizedHeight();

	/**
	 * Depth in standard SI units (meters), NaN if 2D.
	 * 
	 * @return
	 */
	double getStandardizedDepth();

	/**
	 * Length in standard SI units (meters), NaN if 0D. Same as
	 * {@link #getStandardizedWidth()} in 2D shapes.
	 * 
	 * @return
	 */
	double getStandardizedLength();

	/**
	 * Edge-to-edge distance in standard SI units (meters).
	 * 
	 * @param shape
	 * @return the distance
	 */
	double getStandardizedDistance(ISpace extent);

	/**
	 * Override the result for fluency
	 */
	@Override
	ISpace getBoundingExtent();

	/**
     * Override the result for fluency
     */
    @Override
	ISpace mergeContext(IExtent extent);

    /**
     * Override the result for fluency
     */
	@Override
	ISpace merge(ITopologicallyComparable<?> other, LogicalConnector how, MergingOption...options);

	/**
	 * Quickly check if the passed string looks like a WKT string in the k.LAB
	 * supported format (potentially with a projection). No validation, just simple
	 * heuristics to discriminate URNs or other obviously different strings.
	 * 
	 * @param urn
	 * @return
	 */
	static boolean isWKT(String urn) {
		if ((urn.contains("POLYGON") || urn.contains("POINT") || urn.contains("LINESTRING")) && urn.contains("(")
				&& urn.contains(")")) {
			return true;
		}
		return false;
	}

}
