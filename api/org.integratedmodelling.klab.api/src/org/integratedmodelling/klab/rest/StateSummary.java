package org.integratedmodelling.klab.rest;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Describes the numeric and distributional properties of a state. Can be merged
 * with another summary to describe the joint properties of >1 states or
 * temporal slices.
 * 
 * @author ferdinando.villa
 *
 */
public class StateSummary {

	public enum DisplayType {
		VALUES, IMAGE
	}

	/**
	 * If state is categorical, the mapping between numbers and concepts is reported
	 * here
	 */
	private Map<Integer, String> dataKey;

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
	private boolean singleValued;
	private double sum;

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

	public double getSum() {
		return this.sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
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

	public boolean isSingleValued() {
		return singleValued;
	}

	public void setSingleValued(boolean b) {
		this.singleValued = b;
	}

	public void merge(StateSummary other) {
		this.degenerate = this.degenerate || other.degenerate;
		this.singleValued = other.singleValued && this.singleValued;
		this.sum += other.sum;
		if (this.range.get(0) > other.range.get(0)) {
			this.range.set(0, other.range.get(0));
		}
		if (this.range.get(1) < other.range.get(1)) {
			this.range.set(1, other.range.get(1));
		}
		this.nodataPercentage = (this.nodataPercentage + other.nodataPercentage) / 2;
		this.mean = (this.mean + other.mean) / 2.0;
		// FIXME these are obviously wrong
		this.standardDeviation = (this.standardDeviation + other.standardDeviation) / 2.0;
		this.variance = (this.variance + other.variance) / 2.0;
	}

	public Map<Integer, String> getDataKey() {
		return dataKey;
	}

	public void setDataKey(Map<Integer, String> dataKey) {
		this.dataKey = dataKey;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "StateSummary [dataKey=" + (dataKey != null ? toString(dataKey.entrySet(), maxLen) : null)
				+ ", stateTimestamp=" + stateTimestamp + ", dataUrl=" + dataUrl + ", displayType=" + displayType
				+ ", detailUrl=" + detailUrl + ", histogram=" + histogram + ", colormap=" + colormap + ", range="
				+ (range != null ? toString(range, maxLen) : null) + ", mean=" + mean + ", standardDeviation="
				+ standardDeviation + ", variance=" + variance + ", singleValued=" + singleValued + ", sum=" + sum
				+ ", valueCount=" + valueCount + ", degenerate=" + degenerate + ", nodataPercentage=" + nodataPercentage
				+ "]";
	}

	private String toString(Collection<?> collection, int maxLen) {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		int i = 0;
		for (Iterator<?> iterator = collection.iterator(); iterator.hasNext() && i < maxLen; i++) {
			if (i > 0)
				builder.append(", ");
			builder.append(" " + iterator.next() + "\n");
		}
		builder.append("]");
		return builder.toString();
	}

	
	
}
