package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatial regions of interest. Values should be in decimal
 * latitude and longitude.
 * 
 * @author ferdinando.villa
 *
 */
public class SpatialExtent {

	private double east;
	private double west;
	private double north;
	private double south;
	private Double gridResolution;
	private String gridUnit;

	public SpatialExtent() {
	}

	public SpatialExtent(SpatialExtent ext) {
		this.east = ext.east;
		this.west = ext.west;
		this.north = ext.north;
		this.south = ext.south;
		// do not copy resolution
	}

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

	@Override
	public String toString() {
		return "SpatialExtent [west=" + west + ", east=" + east + ", south=" + south + ", north=" + north + "]";
	}

	/*
	 * Stuff coming out of WCS sometimes needs this, particularly large layers that
	 * assume they go around the world the wrong way after being transformed from
	 * another projection. Call it to ensure the bounding box is kosher.
	 */
	public SpatialExtent normalize() {
		SpatialExtent ret = new SpatialExtent();
		if (east < west) {
			ret.east = west;
			ret.west = east;
		} else {
			ret.west = west;
			ret.east = east;
		}
		if (north < south) {
			ret.north = south;
			ret.west = north;
		} else {
			ret.south = south;
			ret.north = north;
		}
		return ret;
	}

	public String getGridUnit() {
		return gridUnit;
	}

	public void setGridUnit(String gridUnit) {
		this.gridUnit = gridUnit;
	}

	public Double getGridResolution() {
		return gridResolution;
	}

	public void setGridResolution(Double gridResolution) {
		this.gridResolution = gridResolution;
	}

}
