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
package org.integratedmodelling.klab.components.geospace.extents;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.observations.scale.space.ISpaceLocator;

/**
 * Simplest locator for space. Limited to point locations and able to carry
 * grid coordinates when applicable.
 * 
 * @author ferdinando.villa
 *
 */
public class SpaceLocator implements ISpaceLocator {

	private long xGrid = -1;
	private long yGrid = -1;
	private long offset = -1;
	private double x = Double.NaN;
	private double y = Double.NaN;
	private boolean latLon = false;
	
	SpaceLocator(long x, long y, long offset) {
		this.xGrid = x;
		this.yGrid = y;
		this.offset = offset;
	}

	SpaceLocator(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	SpaceLocator(double x, double y, long xgrid, long ygrid, long offset) {
		this.x = x;
		this.y = y;
		this.xGrid = xgrid;
		this.yGrid = ygrid;
		this.offset = offset;
	}

	@Override
	public ILocator at(ILocator locator) {
		throw new IllegalArgumentException("a simple space locator cannot be further located");
	}

	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		throw new IllegalArgumentException("a simple space locator cannot be further located");
	}

	@Override
	public double getXCoordinate() {
		return x;
	}

	@Override
	public double getYCoordinate() {
		return y;
	}

	@Override
	public boolean inGrid() {
		return xGrid >= 0;
	}

	@Override
	public long getXIndex() {
		return xGrid;
	}

	@Override
	public long getYIndex() {
		return yGrid;
	}

	public boolean isLatLon() {
		return latLon;
	}

	public void setLatLon(boolean latLon) {
		this.latLon = latLon;
	}

	public long getOffset() {
		return offset;
	}

	public void setOffset(long offset) {
		this.offset = offset;
	}

	public void setWorldCoordinates(double x, double y) {
		this.x = x;
		this.y = y;
	}


}
