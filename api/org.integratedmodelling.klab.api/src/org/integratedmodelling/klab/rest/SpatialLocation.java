package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatial locations of interest. Values should be in
 * decimal latitude and longitude: assume Easting = the value on the HORIZONTAL
 * axis (longitude) and NORTHING for the one on the vertical axis (latitude).
 * <p>
 * Can also be used F->B to communicate features created by users through the
 * wktShape field.
 * 
 * @author ferdinando.villa
 *
 */
public class SpatialLocation {

	private double easting;
	private double northing;
	private String wktShape;

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

	public String getWktShape() {
		return wktShape;
	}

	public void setWktShape(String wktShape) {
		this.wktShape = wktShape;
	}

}
