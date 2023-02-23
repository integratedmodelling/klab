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
 * Geographic orientations for a Moore neighborhood 2D model, constants chosen
 * to represent the actual geographic heading.
 *
 * TODO add iterables for clockwise and counterclockwise sequences from the current
 * orientation.
 *
 * @author ferdinando.villa
 * @version $Id: $Id
 */
public enum Orientation {

	N(0), NE(45), E(90), SE(135), S(180), SW(225), W(270), NW(315);

	private int heading;

	Orientation(int h) {
		heading = h;
	}

	/**
	 * Get the geographic heading corresponding to this orientation.
	 * 
	 * @return
	 */
	public int getHeading() {
		return heading;
	}
	
	/**
	 * Get the opposite orientation in a Moore neighborhood.
	 * 
	 * @return
	 */
	public Orientation getOpposite() {
		switch(this) {
		case E:
			return W;
		case N:
			return S;
		case NE:
			return SW;
		case NW:
			return SE;
		case S:
			return N;
		case SE:
			return NW;
		case SW:
			return NE;
		case W:
			return E;
		}
		return null;
	}
}
