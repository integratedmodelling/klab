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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(east);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(north);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(south);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(west);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SpatialExtent other = (SpatialExtent) obj;
		if (Double.doubleToLongBits(east) != Double.doubleToLongBits(other.east))
			return false;
		if (Double.doubleToLongBits(north) != Double.doubleToLongBits(other.north))
			return false;
		if (Double.doubleToLongBits(south) != Double.doubleToLongBits(other.south))
			return false;
		if (Double.doubleToLongBits(west) != Double.doubleToLongBits(other.west))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SpatialExtent [east=" + east + ", west=" + west + ", north=" + north + ", south=" + south + "]";
	}

		
}
