/*******************************************************************************
 *  Copyright (C) 2007, 2015:
 *  
 *    - Ferdinando Villa <ferdinando.villa@bc3research.org>
 *    - integratedmodelling.org
 *    - any other authors listed in @author annotations
 *
 *    All rights reserved. This file is part of the k.LAB software suite,
 *    meant to enable modular, collaborative, integrated 
 *    development of interoperable data and model components. For
 *    details, see http://integratedmodelling.org.
 *    
 *    This program is free software; you can redistribute it and/or
 *    modify it under the terms of the Affero General Public License 
 *    Version 3 or any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but without any warranty; without even the implied warranty of
 *    merchantability or fitness for a particular purpose.  See the
 *    Affero General Public License for more details.
 *  
 *     You should have received a copy of the Affero General Public License
 *     along with this program; if not, write to the Free Software
 *     Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
 *     The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.components.geospace.api;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.Direction;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

/**
 * FIXME this is just a space extent - check how to simplify
 * @author Ferd
 *
 */
public interface IGrid extends Iterable<IExtent> {

    public interface Cell extends ISpace {

        Cell N();

        Cell S();

        Cell E();

        Cell W();

        Cell NE();

        Cell NW();

        Cell SE();

        Cell SW();

        Collection<Cell> getNeighbors();

        Cell getNeighbor(Orientation orientation);
        
        long getX();

        long getY();

        /**
         * Create a new cell in a position offset by the passed number of cells in each dimension; return null if
         * cell is off the grid or inactive.
         * 
         * @param xOfs
         * @param yOfs
         * @return moved cell
         */
        Cell move(long xOfs, long yOfs);

        double getEast();

        double getWest();

        double getSouth();

        double getNorth();

        Long getOffsetInGrid();
        
        boolean isAdjacent(Cell cell);
        
         

        /**
         * World coordinates of center, horizontal-first.
         * 
         * @return world coordinates of center
         */
        double[] getCenter();

        IShape getShape();

    }

    
    public interface Mask {

        public abstract void intersect(Mask other) throws KlabValidationException;

        public abstract void or(Mask other) throws KlabValidationException;

        public abstract IPair<Long, Long> getCell(long index);

        public abstract boolean isActive(long linearIndex);

        public abstract boolean isActive(long x, long y);

        public abstract void activate(long x, long y);

        public abstract void deactivate(long x, long y);

        public abstract long totalActiveCells();

        public abstract long nextActiveOffset(long fromOffset);

        public abstract long[] nextActiveCell(long fromX, long fromY);

        public abstract IPair<Long, Long> nextActiveCell(long fromOffset);

        public abstract IGrid getGrid();

        public abstract void invert();

        /**
         * Set every flag to false;
         */
        public abstract void deactivate();

        /**
         * Set every flag to true;
         */
        public abstract void activate();

    }

	/**
	 * 
	 * @param xCoordinate
	 * @param direction
	 * @return
	 */
	double snapX(double xCoordinate, Direction direction);
	
	/**
	 * 
	 * @param yCoordinate
	 * @param direction
	 * @return
	 */
	double snapY(double yCoordinate, Direction direction);
	
    /**
     * Number of cells on horizontal (W-E) axis.
     * @return Y cells
     */
    long getYCells();

    /**
     * Number of cells on vertical (S-N) axis.
     * @return X cells
     */
    long getXCells();

    /**
     * Total number of cells.
     * 
     * @return total cells
     */
    long getCellCount();
    
    /**
     * Return the cell area. Use coordinates if projection not available, else
     * return in default SI unit for area (square meters).
     * 
     * @param unit 
     *
     * @return
     */
    double getCellArea(IUnit unit);

    /**
     * Convert to linear index.
     * 
     * @param x
     * @param y
     * @return linear offset
     */
    long getOffset(long x, long y);

    /**
     * Return whether the grid cell at the passed coordinates is part of the active
     * area of the grid.
     * 
     * @param x
     * @param y
     * @return true if active
     */
    boolean isActive(long x, long y);
    
    double getEast();

    double getWest();

    double getSouth();

    double getNorth();

    /**
     * Get the linear index of the cell where the passed point is located, using 
     * world coordinates in the projection we're in. Use w-e, s-n coordinates no
     * matter the projection.
     * 
     * @param lon
     * @param lat
     * @return linear offset
     */
    long getOffsetFromWorldCoordinates(double lon, double lat);

    /**
     * Convert from linear index.
     * 
     * @param index
     * @return xy offsets from linear
     */
    long[] getXYOffsets(long index);

    /**
     * Get the most accurate geospatial coordinates (w-e, s-n) for the linear offset passed,
     * corresponding to the center of the correspondent cell. Use current coordinate reference
     * system.
     * 
     * @param index
     * @return world coordinates for linear offset
     */
    double[] getCoordinates(long index);

//    /**
//     * Get a locator for the passed grid coordinates. Use this instead of creating a
//     * locator from scratch, to ensure that the grid coordinates conform to the
//     * arrangement of this grid.
//     * 
//     * @param x
//     * @param y
//     * @return locator for x,y cell
//     */
//    IScale.Locator getLocator(long x, long y);

    double getCellWidth();

    double getCellHeight();

    double[] getWorldCoordinatesAt(long x, long y);

    long[] getGridCoordinatesAt(double x, double y);
    
    IProjection getProjection();

}
