/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other authors listed
 * in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular, collaborative,
 * integrated development of interoperable data and model components. For details, see
 * http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the Affero
 * General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without even the
 * implied warranty of merchantability or fitness for a particular purpose. See the Affero General Public
 * License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if not, write
 * to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA. The license
 * is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.api.observations.scale.space;

import java.util.Optional;

import org.integratedmodelling.klab.api.observations.scale.IExtent;

public interface ISpace extends IExtent {

    /**
     * Opaque interface for a coordinate reference system.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Projection {

        /**
         * Unique identifier of projection, enough to rebuild it at another
         * endpoint.
         * 
         * @return
         */
        String getCode();
    }

    /**
     * Anything that has coordinates is referenced.
     * 
     * @author ferdinando.villa
     *
     */
    public abstract interface Referenced {

        /**
         * 
         * @return
         */
        Projection getProjection();
    }

    /**
     * Opaque interface for a referenced envelope.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Envelope extends Referenced {

        /**
         * 
         * @return
         */
        double getMinX();

        /**
         * 
         * @return
         */
        double getMaxX();

        /**
         * 
         * @return
         */
        double getMinY();

        /**
         * 
         * @return
         */
        double getMaxY();

        /**
         * 
         * @return
         */
        Shape asShape();
    }

    /**
     * Opaque interface for a 2D geometry.
     * 
     * @author ferdinando.villa
     *
     */
    public interface Shape extends Referenced {

        public enum Type {
            EMPTY,
            POINT,
            LINESTRING,
            POLYGON,
            MULTIPOINT,
            MULTILINESTRING,
            MULTIPOLYGON
        }

        /**
         * Geometry type
         * 
         * @return the type
         */
        Type getType();

        /**
         * Return a suitable measure of area. Units not guaranteed - only comparability between conformant
         * shapes.
         * 
         * @return area in stable unit
         */
        double getArea();

        /**
         * Shapes may be empty or inconsistent.
         * 
         * @return true if not really a shape
         */
        boolean isEmpty();

        /**
         * 
         * @param projection
         * @return
         */
        Shape transform(Projection projection);

        /**
         * 
         * @return
         */
        Envelope getEnvelope();

        /**
         * 
         * @param other
         * @return
         */
        Shape intersection(Shape other);

        /**
         * 
         * @param other
         * @return
         */
        Shape union(Shape other);

    }

    int MIN_SCALE_RANK = 0;
    int MAX_SCALE_RANK = 21;

    @Override
    ISpace getExtent();

    @Override
    ISpace getExtent(int stateIndex);

    /**
     * Get the envelope, providing boundaries.
     * 
     * @return
     */
    Envelope getEnvelope();

    /**
     * Projection. Just repeats same in envelope and shape. It's not legal to have different projections in
     * different elements of a spatial extent.
     * 
     * @return coordinate reference system
     */
    Projection getProjection();

    /**
     * Return the grid topology if we are using one, or null.
     * 
     * @return the grid, or null
     */
    Optional<IGrid> getGrid();

    /**
     * Return the tessellation topology if we are using one, or null.
     * 
     * @return tessellation
     */
    Optional<ITessellation> getTessellation();

    /**
     * Get the shape of this extent - usually it's the same as getExtent but with the additional
     * type constraint.
     * 
     * @return full shape
     */
    Shape getShape();

    /**
     * Return a spatial index capable of keeping track of other extents relative to this one. Each extent can
     * have an index stored in it, and may create new (empty) ones as requested.
     * 
     * @param makeNew
     *            if true, create and return a new index for this extent. If false, return the stored index
     *            for the extent, creating it only if necessary.
     * 
     * @return a spatial index set to our extent
     */
    ISpatialIndex getIndex(boolean makeNew);
}
