package org.integratedmodelling.stats.reporting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.klab.utils.Pair;

/**
 * A report, including definition, generation and encoding to Markdown.
 * 
 * @author Ferd
 *
 */
public class StatsReport {

	boolean isComputed = false;
	
	enum Target {
		Resources, Models, Operations, Observations, Users, Groups
	}

	enum Frequency {
		Monthly, Daily, Yearly, Weekly
	}

	enum Type {
		HighestTargets, LowestTargets, Chronological
	}

	enum Metric {
		Credits, Time, Size, Cost, Count
	}

	// filters
	long start = -1;
	long end = -1;
	
	/**
	 * Data per target is a list of constraints; each constraint contains either a
	 * blacklist or a whitelist of strings to match the target to, based on the
	 * second element (true = whitelist).
	 */
	Map<Target, List<Pair<Collection<String>, Boolean>>> filterTargets = new HashMap<>();

	// modifiers. Default report is a detailed report of observations with the associated credits.
	Frequency aggregationInterval = null;
	int aggregationMultiplier = 1;
	Set<Target> byTarget = EnumSet.of(Target.Groups);
	Set<Metric> metrics = EnumSet.of(Metric.Credits);
	Target target = Target.Observations;
	boolean anonymized = true;

	public void setReportingStart(long start) {
		this.start = start;
	}

	public void setReportingEnd(long end) {
		this.end = end;
	}

	public void setAggregationInterval(Frequency frequency, int multiplier) {
		this.aggregationInterval = frequency;
		this.aggregationMultiplier = multiplier;
	}
	
	public void filter(Target target, boolean include, String...strings) {
		Set<String> filters = new HashSet<>();
		for (String s : strings) {
			filters.add(s);
		}
		List<Pair<Collection<String>, Boolean>> constraints = this.filterTargets.get(target);
		if (constraints == null) {
			constraints = new ArrayList<>();
			this.filterTargets.put(target, constraints);
		}
		constraints.add(new Pair<>(filters, include));
	}
	
	public boolean compute() {
		return false;
	}
	
	public String extract() {
		if (!isComputed) {
			compute();
		}
		return null;
	}
}
