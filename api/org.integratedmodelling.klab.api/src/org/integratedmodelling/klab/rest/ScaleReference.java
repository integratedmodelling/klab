package org.integratedmodelling.klab.rest;

/**
 * Used to communicate spatio/temporal regions of interest. Space values should
 * be in decimal latitude and longitude.
 * 
 * Sent from back-end to communicate new resolution when extent is changed on
 * the front end. Sent by front-end when user wants to set resolution, which
 * locks the scale to the user choice. A front-end message with just unlockSpace
 * == true resets the behavior to automatic resolution definition.
 * 
 * @author ferdinando.villa
 *
 */
public class ScaleReference {

	private double east;
	private double west;
	private double north;
	private double south;
	private long start;
	private long end;
	private long step;
	private int spaceScale;
	private int timeScale;
	private double spaceResolution;
	private String spaceResolutionDescription;
	private String spaceUnit;
	private double timeResolution;
	private String timeResolutionDescription;
	private String timeUnit;
	private boolean unlockSpace;
	private boolean unlockTime;

	public double getEast() {
		return east;
	}

	public String getSpaceUnit() {
		return spaceUnit;
	}

	public void setSpaceUnit(String spaceUnit) {
		this.spaceUnit = spaceUnit;
	}

	public String getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(String timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * Sent from front end when user wants to go back to automatic spatial scale
	 * resolution.
	 * 
	 * @return
	 */
	public boolean isUnlockSpace() {
		return unlockSpace;
	}

	/**
	 * Sent from front end when user wants to go back to automatic spatial scale
	 * resolution.
	 * 
	 * @return
	 */
	public void setUnlockSpace(boolean unlockSpace) {
		this.unlockSpace = unlockSpace;
	}

	public boolean isUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(boolean unlockTime) {
		this.unlockTime = unlockTime;
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

	public double getSpaceResolution() {
		return spaceResolution;
	}

	public void setSpaceResolution(double resolution) {
		this.spaceResolution = resolution;
	}

	public String getSpaceResolutionDescription() {
		return spaceResolutionDescription;
	}

	public void setSpaceResolutionDescription(String resolutionDescription) {
		this.spaceResolutionDescription = resolutionDescription;
	}

	@Override
	public String toString() {
		return "ScaleReference [east=" + east + ", west=" + west + ", north=" + north + ", south=" + south
				+ ", spaceScale=" + spaceScale + ", resolutionDescription=" + spaceResolutionDescription + "]";
	}

	public double getTimeResolution() {
		return timeResolution;
	}

	public void setTimeResolution(double timeResolution) {
		this.timeResolution = timeResolution;
	}

	public String getTimeResolutionDescription() {
		return timeResolutionDescription;
	}

	public void setTimeResolutionDescription(String timeResolutionDescription) {
		this.timeResolutionDescription = timeResolutionDescription;
	}

}
