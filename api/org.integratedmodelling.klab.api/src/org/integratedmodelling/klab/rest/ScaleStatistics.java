package org.integratedmodelling.klab.rest;

/**
 * Communicates the scale and relative complexity for statistical and
 * potentially accounting purposes.
 * 
 * @author Ferd
 *
 */
public class ScaleStatistics {

	private long size;
	private long spaceSize;
	private long timeSize;
	private double spaceComplexity;
	private String spaceResolution;
	private String timeResolution;
	private String bboxWkt;
	private long timeStart;
	private long timeEnd;

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public long getSpaceSize() {
		return spaceSize;
	}

	public void setSpaceSize(long spaceSize) {
		this.spaceSize = spaceSize;
	}

	public long getTimeSize() {
		return timeSize;
	}

	public void setTimeSize(long timeSize) {
		this.timeSize = timeSize;
	}

	public double getSpaceComplexity() {
		return spaceComplexity;
	}

	public void setSpaceComplexity(double spaceComplexity) {
		this.spaceComplexity = spaceComplexity;
	}

	public String getSpaceResolution() {
		return spaceResolution;
	}

	public void setSpaceResolution(String spaceResolution) {
		this.spaceResolution = spaceResolution;
	}

	public String getTimeResolution() {
		return timeResolution;
	}

	public void setTimeResolution(String timeResolution) {
		this.timeResolution = timeResolution;
	}

	public String getBboxWkt() {
		return bboxWkt;
	}

	public void setBboxWkt(String bboxWkt) {
		this.bboxWkt = bboxWkt;
	}

	public long getTimeStart() {
		return timeStart;
	}

	public void setTimeStart(long timeStart) {
		this.timeStart = timeStart;
	}

	public long getTimeEnd() {
		return timeEnd;
	}

	public void setTimeEnd(long timeEnd) {
		this.timeEnd = timeEnd;
	}

	@Override
	public String toString() {
		return "ScaleStatistics [size=" + size + ", spaceSize=" + spaceSize + ", timeSize=" + timeSize
				+ ", spaceComplexity=" + spaceComplexity + ", spaceResolution=" + spaceResolution + ", timeResolution="
				+ timeResolution + ", bboxWkt=" + bboxWkt + "]";
	}

}
