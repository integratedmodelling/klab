package org.integratedmodelling.stats.reporting;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.database.StatsDatabase;

/**
 * A report, including definition, generation and encoding to Markdown.
 * 
 * @author Ferd
 *
 */
public class StatsReport {

	boolean isComputed = false;

	public enum Target {
		Resources, Models, Operations, Observations, Observables, Users, Groups, Downloads
	}

	public enum Frequency {
		Monthly, Daily, Yearly, Weekly
	}

	public enum Type {
		HighestTargets, LowestTargets, Chronological
	}

	public enum Metric {
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

	// modifiers. Default report is a detailed report of observations with the
	// associated credits.
	Frequency aggregationInterval = null;
	int aggregationMultiplier = 1;
	Set<Target> byTarget = EnumSet.of(Target.Groups);
	Set<Metric> metrics = EnumSet.of(Metric.Credits);
	Target target = Target.Observations;
	boolean anonymized = true;
	boolean includeErrors = false;
	boolean includeSuccess = true;

	/*
	 * Each report type (target) collects data from a view specified, at the moment,
	 * as a query (we should create view in the main database after this is stable).
	 * 
	 * After retrieval, data consist of a map indexed by "aggregation sets", which
	 * produce a "chapter" in which the asset data are arranged. Asset data (a "row"
	 * per each assed ID) are also a map that links the asset ID to the statistical
	 * data, which are accumulated into containers linked to the asset ID. The
	 * statistical data themselves are a map indexed by Metric.
	 * 
	 * Aggregation sets contain a match() function for a row from the main view
	 * corresponding to the chosen report type. They have
	 */

	/**
	 * Indexable aggregation set that serves as a key for the "chapters" in the
	 * report. Apart from time intervals, which are generated sequentially, the set
	 * may signal a no-match with the output of a new set which will match a new
	 * observation. These have "" instead of nulls in the string fields that can be
	 * matched to a new or existing observation.
	 * 
	 * @author Ferd
	 *
	 */
	class AggregationSet {

		long start;
		long end;
		String user;
		String group;
		String resource;
		String operation;
		String observable;
		String observation;
		String model;
		String title;

		boolean sequential = true;

		@Override
		public int hashCode() {
			return Objects.hash(end, group, model, observable, observation, operation, resource, start, user);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			AggregationSet other = (AggregationSet) obj;
			return end == other.end && Objects.equals(group, other.group) && Objects.equals(model, other.model)
					&& Objects.equals(observable, other.observable) && Objects.equals(observation, other.observation)
					&& Objects.equals(operation, other.operation) && Objects.equals(resource, other.resource)
					&& start == other.start && Objects.equals(user, other.user);
		}

	}

	class Data {

		public void accumulate(ResultSet result) {
			// TODO Auto-generated method stub

		}

	}

	/*
	 * this holds the computed report. The sets are sorted after computation.
	 */
	Map<AggregationSet, Data> report = new HashMap<>();

	public StatsReport(Target target) {
		this.target = target;
	}

	public void setReportingStart(long start) {
		this.start = start;
	}

	public void setReportingEnd(long end) {
		this.end = end;
	}

	public void includeErrors() {
		includeErrors = true;
	}

	public void excludeSuccess() {
		includeSuccess = false;
	}

	public void setAggregationInterval(Frequency frequency, int multiplier) {
		this.aggregationInterval = frequency;
		this.aggregationMultiplier = multiplier;
	}

	public void filter(Target target, boolean include, String... strings) {
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

		report.clear();

		StatsDatabase db = null;
		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null) {
			StatsComponent stats = stc.getImplementation(StatsComponent.class);
			if (stats != null) {
				db = stats.getDatabase();
			}
		}

		if (db == null || !db.isOnline()) {
			return false;
		}

		db.scan(getQuery(), (result) -> {
			AggregationSet set = getAggregationSet(result);
			if (set != null) {
				Data data = report.get(set);
				if (data == null) {
					data = new Data();
					report.put(set, data);
				}
				data.accumulate(result);
			}
		});

		isComputed = true;

		return true;
	}

	/**
	 * Get the aggregation set matching the result, or null if no criteria match it.
	 * 
	 * @param result
	 * @return
	 */
	private AggregationSet getAggregationSet(ResultSet result) {
		return null;
	}

	/*
	 * Return the appropriate query for the target, using standardized field names
	 */
	private String getQuery() {

		String ret = "";
		switch (this.target) {
		case Groups:
//			return reportGroups();
		case Models:
		case Resources:
		case Observables:
		case Operations:
			ret = "SELECT assets.total_time_sec as time, assets.total_passes as passes, assets.name as asset, contexts.created as time, \n"
					+ "	   contexts.application, contexts.scale_size, \n"
					+ "	   queries.observable as observation, contexts.principal as username\n"
					+ "	from assets, queries, contexts\n"
					+ "		WHERE assets.context_id = contexts.id and assets.query_id = queries.id AND queries.context_id = contexts.id\n"
					+ "	          AND assets.asset_type = '" + getAssetType() + "' ";

//			return reportAssets(target);
		case Observations:
//			return reportObservations();
//			return reportOperations();
		case Users:
//			return reportUsers();
		}

		if (includeErrors && !includeSuccess) {
			ret += "AND assets.outcome = 'Error'\n";
		} else if (includeSuccess && !includeErrors) {
			ret += "AND assets.outcome = 'Success'\n";
		}

		if (start > 0) {
			ret += "     AND context.created >= " + start;
		}
		if (end > 0) {
			ret += "     AND context.created < " + end;
		}

		if (aggregationInterval != null) {
			ret += "\n   ORDER BY context.created";
		}

		return ret + ";" /* I know */;
	}

	private String getAssetType() {

		switch (this.target) {
		case Models:
			return "Model";
		case Observables:
			return "ResolvedObservable";
		case Operations:
			return "Operation";
		case Resources:
			return "Resource";
		case Downloads:
			return "Export";
		}
		throw new KlabInternalErrorException("wrong asset type query");
	}

	public String extract() {
		if (!isComputed) {
			compute();
		}
		return null;
	}

	public void setTarget(Target target) {
		this.target = target;
	}
}
