package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatial regions of interest. Values should be in decimal
 * latitude and longitude.
 * 
 * @author ferdinando.villa
 *
 */
public class SpatialExtent {

	double east;
	double west;
	double north;
	double south;

	public double getEast() {
		return east;
	}

	public void setEast(double east) {
		this.east = east;
	}

	public double getWest() {
		return west;
	}

	public void setWest(double west) {
		this.west = west;
	}

	public double getNorth() {
		return north;
	}

	public void setNorth(double north) {
		this.north = north;
	}

	public double getSouth() {
		return south;
	}

	public void setSouth(double south) {
		this.south = south;
	}

}
