package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class DataSummary {

	private double nodataProportion;
	private double minValue = Double.NaN;
	private double maxValue = Double.NaN;
	private List<Integer> histogram = new ArrayList<>();
	private List<String> colormap = new ArrayList<>();
	private List<String> categories = null;

	/**
	 * Proportion of no-data values (0-1).
	 * 
	 * @return
	 */
	public double getNodataProportion() {
		return nodataProportion;
	}

	public void setNodataProportion(double nodataPercentage) {
		this.nodataProportion = nodataPercentage;
	}

	/**
	 * Minimum numeric value, or NaN if no data overall. Set to the minimum integer
	 * category index if categorical.
	 * 
	 * @return
	 */
	public double getMinValue() {
		return minValue;
	}

	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	/**
	 * Maximum numeric value, or NaN if no data overall. Set to the maximum integer
	 * category index found in data if categorical.
	 * 
	 * @return
	 */
	public double getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

	/**
	 * Histogram using all categories if categorical, or the requested number of
	 * bins with equal spacing between min and max. Null if all data are NaN or if
	 * min == max.
	 * 
	 * @return
	 */
	public List<Integer> getHistogram() {
		return histogram;
	}

	public void setHistogram(List<Integer> histogram) {
		this.histogram = histogram;
	}

	/**
	 * Colormap used for display, or null if not applicable. Color values are in hex
	 * notation. Maximum number of values is 256 for interpolated continuous
	 * colormaps.
	 * 
	 * @return
	 */
	public List<String> getColormap() {
		return colormap;
	}

	public void setColormap(List<String> colormap) {
		this.colormap = colormap;
	}

	/**
	 * Category labels. If data are categorical, this should report all the possible
	 * categories even if not represented in data, so the number of labels may
	 * differ from max-min, while the values in data will correspond to the label.
	 * If data are not categorical, the labels will describe the range of bins in
	 * the histogram. If the histogram is null, this will be null.
	 * 
	 * @return
	 */
	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

}
