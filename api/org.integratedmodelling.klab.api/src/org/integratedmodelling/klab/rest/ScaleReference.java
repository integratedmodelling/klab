package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.observations.scale.time.ITime;

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
	private int spaceScale;
	private int timeScale;
	/**
	 * Resolution is always in m
	 */
	private double spaceResolution;
	/**
	 * Description is in whatever unit is more convenient
	 */
	private String spaceResolutionDescription;
	private double spaceResolutionConverted;
	private String spaceUnit;
	private double timeResolutionMultiplier;
	private ITime.Resolution.Type timeUnit;
	private String timeResolutionDescription;

	private long start;
	private long end;

	// unused for now, or enabled in developer mode
	private long step;

	/*
	 * If this is not empty, user wants to change the scale for an existing context.
	 */
	private String contextId = null;

	// FIXME REMOVE
	private String resolutionDescription;

	public double getEast() {
		return east;
	}

	public String getSpaceUnit() {
		return spaceUnit;
	}

	public void setSpaceUnit(String spaceUnit) {
		this.spaceUnit = spaceUnit;
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

	public void setResolutionDescription(String string) {
		this.resolutionDescription = string;
	}

	public String getResolutionDescription() {
		return this.resolutionDescription;
	}

	public double getSpaceResolutionConverted() {
		return spaceResolutionConverted;
	}

	public void setSpaceResolutionConverted(double spaceResolutionConverted) {
		this.spaceResolutionConverted = spaceResolutionConverted;
	}

	public String getContextId() {
		return contextId;
	}

	public void setContextId(String contextId) {
		this.contextId = contextId;
	}

	public double getTimeResolutionMultiplier() {
		return timeResolutionMultiplier;
	}

	public void setTimeResolutionMultiplier(double timeResolutionMultiplier) {
		this.timeResolutionMultiplier = timeResolutionMultiplier;
	}

	public ITime.Resolution.Type getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(ITime.Resolution.Type timeUnit) {
		this.timeUnit = timeUnit;
	}

	public String getTimeResolutionDescription() {
		return timeResolutionDescription;
	}

	public void setTimeResolutionDescription(String timeResolutionDescription) {
		this.timeResolutionDescription = timeResolutionDescription;
	}

}
