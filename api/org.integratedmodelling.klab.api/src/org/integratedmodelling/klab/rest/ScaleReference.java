package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatial regions of interest. Values should be in decimal
 * latitude and longitude.
 * 
 * @author ferdinando.villa
 *
 */
public class ScaleReference {

	private double east;
	private double west;
	private double north;
	private double south;
	private long  start;
	private long end;
	private long step;
	private int spaceScale;
	private int timeScale;
	private double resolution;
	private String resolutionDescription;
	
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

	public long getStart() {
		return start;
	}

	public void setStart(long start) {
		this.start = start;
	}

	public long getEnd() {
		return end;
	}

	public void setEnd(long end) {
		this.end = end;
	}

	public long getStep() {
		return step;
	}

	public void setStep(long step) {
		this.step = step;
	}

	public int getSpaceScale() {
		return spaceScale;
	}

	public void setSpaceScale(int spaceScale) {
		this.spaceScale = spaceScale;
	}

	public int getTimeScale() {
		return timeScale;
	}

	public void setTimeScale(int timeScale) {
		this.timeScale = timeScale;
	}

	public double getResolution() {
		return resolution;
	}

	public void setResolution(double resolution) {
		this.resolution = resolution;
	}

	public String getResolutionDescription() {
		return resolutionDescription;
	}

	public void setResolutionDescription(String resolutionDescription) {
		this.resolutionDescription = resolutionDescription;
	}

	@Override
	public String toString() {
		return "ScaleReference [east=" + east + ", west=" + west + ", north=" + north + ", south=" + south
				+ ", spaceScale=" + spaceScale + ", resolutionDescription=" + resolutionDescription + "]";
	}

}
