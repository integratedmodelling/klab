package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatial locations of interest. Values should be in
 * decimal latitude and longitude: assume Easting = the value on the HORIZONTAL
 * axis (longitude) and NORTHING for the one on the vertical axis (latitude).
 * 
 * @author ferdinando.villa
 *
 */
public class SpatialLocation {

	double easting;
	double northing;

	/**
	 * Easting (longitude)
	 * @return the longitude in decimal degrees
	 */
	public double getEasting() {
		return easting;
	}

	public void setEast(double easting) {
		this.easting = easting;
	}

	/**
	 * Northing (latitude)
	 * @return the latitude in decimal degrees 
	 */
	public double getNorthing() {
		return northing;
	}

	public void setNorthing(double northing) {
		this.northing = northing;
	}

}
