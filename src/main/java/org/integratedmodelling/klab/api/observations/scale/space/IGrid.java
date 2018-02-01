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
package org.integratedmodelling.klab.api.observations.scale.space;

import java.util.Collection;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.data.utils.IPair;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public interface IGrid extends Iterable<Cell> {

    public interface Cell {

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
        
        int getX();

        int getY();

        /**
         * Create a new cell in a position offset by the passed number of cells in each dimension; return null if
         * cell is off the grid or inactive.
         * 
         * @param xOfs
         * @param yOfs
         * @return moved cell
         */
        Cell move(int xOfs, int yOfs);

        double getEast();

        double getWest();

        double getSouth();

        double getNorth();

        Integer getOffsetInGrid();
        
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

        public abstract IPair<Integer, Integer> getCell(int index);

        public abstract boolean isActive(int linearIndex);

        public abstract boolean isActive(int x, int y);

        public abstract void activate(int x, int y);

        public abstract void deactivate(int x, int y);

        public abstract int totalActiveCells();

        public abstract int nextActiveOffset(int fromOffset);

        public abstract int[] nextActiveCell(int fromX, int fromY);

        public abstract IPair<Integer, Integer> nextActiveCell(int fromOffset);

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

    /*
     * constant for snapping
     */
	public static final int LEFT = 1;
	public static final int RIGHT = 2;
	public static final int TOP = 3;
	public static final int BOTTOM = 4;

	/**
	 * 
	 * @param xCoordinate
	 * @param direction
	 * @return
	 */
	double snapX(double xCoordinate, int direction);
	
	/**
	 * 
	 * @param yCoordinate
	 * @param direction
	 * @return
	 */
	double snapY(double yCoordinate, int direction);
	
    /**
     * Number of cells on horizontal (W-E) axis.
     * @return Y cells
     */
    int getYCells();

    /**
     * Number of cells on vertical (S-N) axis.
     * @return X cells
     */
    int getXCells();

    /**
     * Total number of cells.
     * 
     * @return total cells
     */
    int getCellCount();
    
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
    int getOffset(int x, int y);

    /**
     * Return whether the grid cell at the passed coordinates is part of the active
     * area of the grid.
     * 
     * @param x
     * @param y
     * @return true if active
     */
    boolean isActive(int x, int y);
    
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
    int getOffsetFromWorldCoordinates(double lon, double lat);

    /**
     * Convert from linear index.
     * 
     * @param index
     * @return xy offsets from linear
     */
    int[] getXYOffsets(int index);

    /**
     * Get the most accurate geospatial coordinates (w-e, s-n) for the linear offset passed,
     * corresponding to the center of the correspondent cell. Use current coordinate reference
     * system.
     * 
     * @param index
     * @return world coordinates for linear offset
     */
    double[] getCoordinates(int index);

    /**
     * Get a locator for the passed grid coordinates. Use this instead of creating a
     * locator from scratch, to ensure that the grid coordinates conform to the
     * arrangement of this grid.
     * 
     * @param x
     * @param y
     * @return locator for x,y cell
     */
    IScale.Locator getLocator(int x, int y);

    double getCellWidth();

    double getCellHeight();

    double[] getWorldCoordinatesAt(int x, int y);

    int[] getGridCoordinatesAt(double x, double y);
    
    IProjection getProjection();

}
