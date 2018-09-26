package org.integratedmodelling.klab.rest;

import java.util.List;

/**
 * Describes the numeric and distributional properties of a state.
 * 
 * @author ferdinando.villa
 *
 */
public class StateSummary {

	public enum DisplayType {
		VALUES, IMAGE
	}

	/**
	 * Timestamp of last modification for the observation this refers to. Checked to
	 * see if the summary needs to be recomputed.
	 */
	private long stateTimestamp;

	/**
	 * A relative URL that will return the detailed state data, either as an image
	 * (for a raster state) or as a list of numbers or categories. The type to be
	 * expected corresponds to the value set in displayType.
	 */
	private String dataUrl;

	/**
	 * The type of value to be expected when the dataUrl URL is invoked on the
	 * server.
	 */
	private DisplayType displayType;

	/**
	 * A relative URL that will return a string description of the detailed state in
	 * one location, including a {locator} variable that will need to be substituted
	 * with an appropriate locator.
	 */
	private String detailUrl;
	/**
	 * The data histogram
	 */
	private Histogram histogram;

	private Colormap colormap;
	
	/**
	 * The data range
	 */
	private List<Double> range;
	private double mean;
	private double standardDeviation;
	private double variance;

	/**
	 * The value count
	 */
	private long valueCount;

	// if false, we have proper intervals and some nodata values
	private boolean degenerate;

	/**
	 * The percentage of no-data values in the value count
	 */
	private double nodataPercentage;

	public String getDataUrl() {
		return dataUrl;
	}

	public void setDataUrl(String dataUrl) {
		this.dataUrl = dataUrl;
	}

	public DisplayType getDisplayType() {
		return displayType;
	}

	public void setDisplayType(DisplayType displayType) {
		this.displayType = displayType;
	}

	public String getDetailUrl() {
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public Histogram getHistogram() {
		return histogram;
	}

	public void setHistogram(Histogram histogram) {
		this.histogram = histogram;
	}

	public List<Double> getRange() {
		return range;
	}

	public void setRange(List<Double> range) {
		this.range = range;
	}

	public long getValueCount() {
		return valueCount;
	}

	public void setValueCount(long valueCount) {
		this.valueCount = valueCount;
	}

	public double getNodataPercentage() {
		return nodataPercentage;
	}

	public void setNodataPercentage(double nodataPercentage) {
		this.nodataPercentage = nodataPercentage;
	}

	public boolean isDegenerate() {
		return this.degenerate;
	}

	public void setDegenerate(boolean b) {
		this.degenerate = b;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public double getVariance() {
		return variance;
	}

	public void setVariance(double variance) {
		this.variance = variance;
	}

	public Long getStateTimestamp() {
		return stateTimestamp;
	}

	public void setStateTimestamp(long stateTimestamp) {
		this.stateTimestamp = stateTimestamp;
	}

	/**
	 * Color map if computed, or null. Put in place by the renderer, so it will be
	 * null until after an image has been created and a colormap is computed. If
	 * present, the colormap will be a list of strings encoding colors in hex RGB
	 * notation (e.g. #ffff).
	 */
	public Colormap getColormap() {
		return colormap;
	}

	public void setColormap(Colormap colormap) {
		this.colormap = colormap;
	}

}
