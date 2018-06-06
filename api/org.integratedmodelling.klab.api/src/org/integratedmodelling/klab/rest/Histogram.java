package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.List;

public class Histogram {
    
	private String           description;
    private List<Integer>    bins;
    private List<Double>     boundaries;
    private List<String>     binLegends;
    boolean                  nodata          = false;
    long                     nodataCount     = 0;
    double                   aggregatedMean  = 0;
    double                   aggregatedTotal = Double.NaN;
    HashMap<String, Integer> occurrences;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Integer> getBins() {
		return bins;
	}
	public void setBins(List<Integer> bins) {
		this.bins = bins;
	}
	public List<Double> getBoundaries() {
		return boundaries;
	}
	public void setBoundaries(List<Double> boundaries) {
		this.boundaries = boundaries;
	}
	public List<String> getBinLegends() {
		return binLegends;
	}
	public void setBinLegends(List<String> binLegends) {
		this.binLegends = binLegends;
	}
	public boolean isNodata() {
		return nodata;
	}
	public void setNodata(boolean nodata) {
		this.nodata = nodata;
	}
	public long getNodataCount() {
		return nodataCount;
	}
	public void setNodataCount(long nodataCount) {
		this.nodataCount = nodataCount;
	}
	public double getAggregatedMean() {
		return aggregatedMean;
	}
	public void setAggregatedMean(double aggregatedMean) {
		this.aggregatedMean = aggregatedMean;
	}
	public double getAggregatedTotal() {
		return aggregatedTotal;
	}
	public void setAggregatedTotal(double aggregatedTotal) {
		this.aggregatedTotal = aggregatedTotal;
	}
	public HashMap<String, Integer> getOccurrences() {
		return occurrences;
	}
	public void setOccurrences(HashMap<String, Integer> occurrences) {
		this.occurrences = occurrences;
	}
    
    
    
}
