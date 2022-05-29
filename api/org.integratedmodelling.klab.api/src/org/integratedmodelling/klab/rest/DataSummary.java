package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class DataSummary {

	private double nodataProportion;
	private double minValue = Double.NaN;
	private double maxValue = Double.NaN;
	private double mean = Double.NaN;
	
	/**
	 * is need on k.Explorer to know that the min and max has no sense
	 */
	private boolean categorized = false;
	private List<Integer> histogram = new ArrayList<>();
	private List<String> categories = new ArrayList<>();

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
	 * @return the categorized
	 */
	public boolean isCategorized() {
		return categorized;
	}

	/**
	 * @param categorized the categorized to set
	 */
	public void setCategorized(boolean categorized) {
		this.categorized = categorized;
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

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

}
