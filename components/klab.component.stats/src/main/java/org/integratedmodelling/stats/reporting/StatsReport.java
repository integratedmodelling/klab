package org.integratedmodelling.stats.reporting;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.components.time.extents.TemporalExtension;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.database.StatsDatabase;

import com.ibm.icu.text.NumberFormat;

/**
 * A report, including definition, generation and encoding to Markdown.
 * 
 * @author Ferd
 *
 */
public class StatsReport {

	boolean isComputed = false;

	public enum Target {
		Resources, Models, Operations, Contexts, Observations, Observables, Users, Downloads, Applications
	}

	public enum Frequency {
		Monthly, Daily, Yearly, Weekly, Hourly
	}

	public enum Type {
		HighestTargets, LowestTargets, Chronological
	}

	public enum Metric {
		Credits, Time, Size, Cost, Count;
	}

	boolean adjustInterval = true;
	
	// filters
	long start = -1;
	long end = -1;

	/*
	 * we aggregate either by period or by target. We keep a list of these and use
	 * it to build the report in the order of aggregation specified.
	 */
	class Aggregator {
		Frequency aggregationInterval = null;
		int aggregationMultiplier = 1;
		Target target = null;

		@Override
		public String toString() {
			return "<" + (aggregationInterval == null ? target : (aggregationMultiplier + " " + aggregationInterval))
					+ ">";
		}
	}

	/**
	 * Data per target is a list of constraints; each constraint contains either a
	 * blacklist or a whitelist of strings to match the target to, based on the
	 * second element (true = whitelist).
	 */
	Map<Target, List<Pair<Collection<String>, Boolean>>> filterTargets = new HashMap<>();

	Set<Metric> metrics = EnumSet.of(Metric.Time, Metric.Size, Metric.Credits);
	List<Aggregator> aggregators = new ArrayList<>();

	boolean anonymized = true;
	boolean includeErrors = false;
	boolean includeSuccess = true;

	/**
	 * Indexable aggregation set that serves as a key for the "chapters" in the
	 * report. Contains all keys used to group the data and sorts intelligently
	 * based on the order of aggregation.
	 * 
	 * @author Ferd
	 *
	 */
	class AggregationSet implements Comparable<AggregationSet> {

		long start;
		long end;
		String context_id;
		String query_id; // contains context + id
		String user;
		String group;
		String resource;
		String operation;
		String observable;
		String model;
		String download;
		String application;

		// not a key
		String observation;
		String context_name;
		boolean sequential = true;

		@Override
		public int hashCode() {
			return Objects.hash(end, group, model, observable, operation, resource, start, user, download, application,
					context_id, query_id);
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
			return end == other.end && Objects.equals(query_id, other.query_id) && Objects.equals(group, other.group)
					&& Objects.equals(model, other.model) && Objects.equals(application, other.application)
					&& Objects.equals(download, other.download) && Objects.equals(observable, other.observable)
					&& Objects.equals(operation, other.operation) && Objects.equals(resource, other.resource)
					&& start == other.start && Objects.equals(user, other.user)
					&& Objects.equals(context_id, other.context_id);
		}

		@Override
		public String toString() {
			String ret = "";
			if (start > 0 && end > 0) {
				ret += "(" + TimeInstant.create(start) + " - " + TimeInstant.create(end) + ")";
			}
			if (context_id != null) {
				ret += (ret.isEmpty() ? "" : " ") + "ctx=" + context_id;
			}
			if (query_id != null) {
				ret += (ret.isEmpty() ? "" : " ") + "qid=" + query_id;
			}
			if (user != null) {
				ret += (ret.isEmpty() ? "" : " ") + "usr=" + user;
			}
			if (group != null) {
				ret += (ret.isEmpty() ? "" : " ") + "grp=" + group;
			}
			if (resource != null) {
				ret += (ret.isEmpty() ? "" : " ") + "res=" + resource;
			}
			if (operation != null) {
				ret += (ret.isEmpty() ? "" : " ") + "opr=" + operation;
			}
			if (observable != null) {
				ret += (ret.isEmpty() ? "" : " ") + "obs=" + observable;
			}
			if (model != null) {
				ret += (ret.isEmpty() ? "" : " ") + "mod=" + model;
			}
			if (download != null) {
				ret += (ret.isEmpty() ? "" : " ") + "dwn=" + download;
			}
			if (application != null) {
				ret += (ret.isEmpty() ? "" : " ") + "app=" + application;
			}

			return ret;
		}

		@Override
		public int compareTo(AggregationSet o) {

			/*
			 * compare in order of aggregation so that the original sequence is kept.
			 */
			for (Aggregator aggregator : aggregators) {

				if (aggregator.aggregationInterval != null) {
					// start is enough as we're never going to use this for overlapping intervals.
					if (start > 0 && o.start > 0) {
						int ret = Long.compare(start, o.start);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Users) {

					if (user != null && o.user != null) {
						int ret = user.compareTo(o.user);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Observations) {

					if (query_id != null && o.query_id != null) {
						int ret = query_id.compareTo(o.query_id);
						if (ret != 0) {
							return ret;
						}
					}

				} else if (aggregator.target == Target.Observables) {
					if (observable != null && o.observable != null) {
						int ret = observable.compareTo(o.observable);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Resources) {

					if (resource != null && o.resource != null) {
						int ret = resource.compareTo(o.resource);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Models) {

					if (model != null && o.model != null) {
						int ret = model.compareTo(o.model);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Operations) {

					if (operation != null && o.operation != null) {
						int ret = operation.compareTo(o.operation);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Downloads) {

					if (download != null && o.download != null) {
						int ret = download.compareTo(o.download);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Applications) {

					if (application != null && o.application != null) {
						int ret = application.compareTo(o.application);
						if (ret != 0) {
							return ret;
						}
					}
				} else if (aggregator.target == Target.Contexts) {

					if (context_id != null && o.context_id != null) {
						int ret = context_id.compareTo(o.context_id);
						if (ret != 0) {
							return ret;
						}
					}
				}
			}

			return 0;
		}

		public boolean isAsset() {
			return this.download != null || this.model != null || this.observable != null || this.operation != null
					|| this.resource != null;
		}

		public boolean setTarget(Target target, IParameters<String> result) {

			switch (target) {
			case Downloads:
				if (!"Export".equals(result.get("asset_type"))) {
					return false;
				}
				String s = result.get("asset", String.class);
				if (s != null) {
					this.download = s;
				}
				break;
			case Models:
				if (!"Model".equals(result.get("asset_type"))) {
					return false;
				}
				s = result.get("asset", String.class);
				if (s != null) {
					this.model = s;
				}
				break;
			case Observables:
				if (!"ResolvedObservable".equals(result.get("asset_type"))) {
					return false;
				}
				s = result.get("asset", String.class);
				if (s != null) {
					this.observable = s;
				}
				break;
			case Observations:
				this.query_id = result.get("context_id", String.class) + "_" + result.get("query_id", Long.class);
				this.observation = result.get("query_observable", String.class);
				break;
			case Operations:
				if (!"Operation".equals(result.get("asset_type"))) {
					return false;
				}
				s = result.get("asset", String.class);
				if (s != null) {
					this.operation = s;
				}
				break;
			case Resources:
				if (!"Resource".equals(result.get("asset_type"))) {
					return false;
				}
				s = result.get("asset", String.class);
				if (s != null) {
					this.resource = s;
				}
				break;
			case Users:
				s = result.get("principal", String.class);
				if (s != null) {
					this.user = s;
				}
				break;
			case Applications:
				s = result.get("application", String.class);
				if (s != null) {
					this.application = s;
				}
				break;
			case Contexts:
				this.context_id = result.get("context_id", String.class);
				String cname = result.get("context_name", String.class);
				this.context_name = (cname == null ? "" : (cname + " ")) + "["
						+ result.get("context_observable", String.class) + "]";
				break;
			}

			return true;
		}

		/*
		 * return a pair key, string
		 */
		public Pair<String, String> getTitle(Aggregator aggregator) {
			if (aggregator.aggregationInterval != null && start > 0 && end > 0) {
				String ret = TimeInstant.create(start) + " to " + TimeInstant.create(end);
				return new Pair<>(ret, ret);
			} else if (aggregator.target != null) {
				switch (aggregator.target) {
				case Applications:
					return new Pair<>(application, application);
				case Downloads:
					return new Pair<>(download, download);
				case Models:
					return new Pair<>(model, model);
				case Observables:
					return new Pair<>(observable, observable);
				case Contexts:
					return new Pair<>(context_id, context_name);
				case Observations:
					return new Pair<>(query_id, observation);
				case Operations:
					return new Pair<>(operation, operation);
				case Resources:
					return new Pair<>(resource, resource);
				case Users:
					return new Pair<>(user, user);
				}
			}
			return new Pair<>("", "");
		}

	}

	class Data {

		Map<Metric, Double> data = new HashMap<>();
		boolean error = false;
		boolean done = false;
		int count = 0;

		/**
		 * If the aggregation set isn't selecting a specified asset, accumulate time
		 * instead of asset time only for the first asset, and use the others just to
		 * scan for errors.
		 * 
		 * 
		 * @param result
		 * @param set
		 */
		public void accumulate(IParameters<String> result, AggregationSet set) {

			if (!error && !"Success".equals(result.get("outcome"))) {
				error = true;
			}

			boolean useQuery = !set.isAsset();
			if (useQuery && done) {
				return;
			}

			count++;

			for (Metric metric : metrics) {
				Double val = data.get(metric);
				if (val == null) {
					data.put(metric, 0.0);
				}
				data.put(metric, data.get(metric).doubleValue() + getValue(result, metric, useQuery));
				done = true;
			}
		}

		@Override
		public String toString() {
			String ret = "";
			for (Metric metric : metrics) {
				ret += (ret.isEmpty() ? "" : "\t") + NumberFormat.getInstance().format(data.get(metric));
			}
			return "[" + count + "] " + ret;
		}

		private double getValue(IParameters<String> result, Metric metric, boolean useQueryTime) {
			switch (metric) {
			case Cost:
				break;
			case Count:
				return 1.0;
			case Credits:
				break;
			case Size:
				return result.get("scale_size", Number.class).doubleValue();
			case Time:
				return result.get(useQueryTime ? "query_time" : "time", Number.class).doubleValue();
			default:
				break;
			}
			return 0;
		}

	}

	/*
	 * this holds the computed report. The sets are sorted after computation.
	 */
	Map<AggregationSet, Data> report = new HashMap<>();
	TemporalExtension timeline = null;
	private boolean errorLogged;

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
		Aggregator aggregation = new Aggregator();
		aggregation.aggregationInterval = frequency;
		aggregation.aggregationMultiplier = multiplier;
		aggregators.add(aggregation);
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
		timeline = null;

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

		/**
		 * if start and/or end isn't specified, retrieve it from the database and set
		 * it.
		 */
		AtomicLong st = new AtomicLong(this.start);
		AtomicLong en = new AtomicLong(this.end);
		if (st.get() < 0 || en.get() < 0) {
			db.scan("SELECT MIN(contexts.created) as min, MAX(contexts.created) as max from contexts;", (rs) -> {
				if (st.get() < 0) {
					st.set(rs.get("min", Long.class));
				}
				if (en.get() < 0) {
					en.set(rs.get("max", Long.class) + 1);
				}
			});
		}

		if (st.get() /* still */ < 0 || en.get() /* still */ < 0) {
			// no errors but nothing in DB
			return true;
		}

		db.scan(getQuery(st.get(), en.get()), (result) -> {
			try {
				AggregationSet set = getAggregationSet(result, st.get(), en.get());
				if (set != null) {
					Data data = report.get(set);
					if (data == null) {
						data = new Data();
						report.put(set, data);
					}
					data.accumulate(result, set);
				}
			} catch (SQLException e) {
				if (!errorLogged) {
					Logging.INSTANCE.error(e);
					errorLogged = true;
				}
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
	 * @throws SQLException
	 */
	private AggregationSet getAggregationSet(IParameters<String> result, long st, long en) throws SQLException {

		for (Target filterTarget : filterTargets.keySet()) {
			for (Pair<Collection<String>, Boolean> filter : filterTargets.get(filterTarget)) {
				if (!matchFilter(filterTarget, filter.getFirst(), filter.getSecond())) {
					return null;
				}
			}
		}

		long created = result.get("created", Long.class);
		if (created < st || created >= en) {
			// shouldn't happen as the limits are usually in the query
			return null;
		}

		AggregationSet ret = new AggregationSet();

		for (Aggregator aggregator : aggregators) {
			if (aggregator.aggregationInterval != null) {
				if (timeline == null) {
					timeline = new TemporalExtension(st, en, aggregator.aggregationMultiplier,
							getResolution(aggregator.aggregationInterval));
				}

				long[] period = timeline.getExtensionAt(created);
				ret.start = period[0];
				ret.end = period[1];
			} else if (aggregator.target != null) {
				if (!ret.setTarget(aggregator.target, result)) {
					// row doesn't apply
					return null;
				}
			}
		}
		return ret;
	}

	private boolean matchFilter(Target filterTarget, Collection<String> first, Boolean second) {
		// TODO handle blacklists and whitelists for all filters
		return true;
	}

	private Resolution.Type getResolution(Frequency f) {
		switch (f) {
		case Hourly:
			return Resolution.Type.HOUR;
		case Daily:
			return Resolution.Type.DAY;
		case Monthly:
			return Resolution.Type.MONTH;
		case Weekly:
			return Resolution.Type.WEEK;
		case Yearly:
			return Resolution.Type.YEAR;
		}

		throw new KlabInternalErrorException("unexpected reporting frequency " + f);
	}

	/*
	 * Return the appropriate query for the target, using standardized field names
	 */
	private String getQuery(long start, long end) {

		/*
		 * Monster join (on an assets resolution)
		 */
		String ret = "SELECT \n" + "	contexts.observable as context_observable, \n"
				+ "	contexts.created, contexts.scenarios, contexts.engine_type, contexts.application,\n"
				+ "	contexts.principal, contexts.scale_size, contexts.space_resolution, contexts.groups, \n"
				+ "	contexts.space_complexity, contexts.context_name,\n"
				+ "	queries.observable as query_observable, queries.total_time_sec as query_time,\n"
				+ "	assets.total_time_sec as time, assets.total_passes as passes, assets.name as asset, \n"
				+ "	assets.outcome as outcome, assets.asset_type as asset_type, queries.id as query_id, \n"
				+ " contexts.id as context_id\n" + "FROM contexts, queries, assets\n"
				+ "		WHERE assets.context_id = contexts.id AND assets.query_id = queries.id \n"
				+ "		      AND queries.context_id = contexts.id";

		if (start > 0) {
			ret += "		      AND contexts.created >= " + start + "\n";
		}
		if (end > 0) {
			ret += "		      AND contexts.created < " + end + "\n";
		}

		if (includeErrors && !includeSuccess) {
			ret += "		      AND assets.outcome != 'Success'" + "\n";
		} else if (includeSuccess && !includeErrors) {
			ret += "		      AND assets.outcome = 'Success'" + "\n";
		}

		String assetType = getAssetType();
		if (assetType != null) {
			// query is for specific type of asset
			ret += "		      AND assets.asset_type = '" + assetType + "'";
		}

		return ret + ";" /* I know */;
	}

	private String getAssetType() {

		for (Aggregator a : aggregators) {
			if (a.target != null) {
				switch (a.target) {
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
				default:
					break;
				}
			}
		}
		return null;
	}

	public String compile() {

		if (!isComputed) {
			compute();
		}

		// TODO preamble
		StringBuffer ret = new StringBuffer(1024);

		List<AggregationSet> classifiers = new ArrayList<>(this.report.keySet());
		Collections.sort(classifiers);
		List<Pair<String, String>> headers = new ArrayList<>();
		for (int i = 0; i < aggregators.size() - 1; i++) {
			headers.add(new Pair<>("-_-_-_", "-_-_-_"));
		}

		for (AggregationSet classifier : classifiers) {
			Data data = this.report.get(classifier);
			for (int level = 0; level < aggregators.size() - 1; level++) {
				Pair<String, String> title = classifier.getTitle(aggregators.get(level));
				if (!headers.get(level).getFirst().equals(title.getFirst())) {
					headers.set(level, title);
					for (int i = level + 1; i < headers.size(); i++) {
						// force redefinition of lower headers
						headers.set(i, new Pair<>("-_-_-", "-_-_-"));
					}
					ret.append(StringUtil.spaces(level * 3) + title.getSecond() + "\n");
				}
			}
			ret.append(StringUtil.spaces((aggregators.size() - 1) * 3)
					+ classifier.getTitle(aggregators.get(aggregators.size() - 1)).getSecond() + " " + data + "\n");
		}

		return ret.toString();
	}

	public void setTargetClassifier(Target target) {
		Aggregator a = new Aggregator();
		a.target = target;
		if (!checkAssets(target)) {
			throw new KlabIllegalArgumentException("only one asset target can be used in a report");
		}
		this.aggregators.add(a);
	}

	/*
	 * check if adding this new target would cause double counting
	 */
	private boolean checkAssets(Target target) {
		Set<Target> assets = EnumSet.of(Target.Models, Target.Observables, Target.Operations, Target.Resources);
		for (Aggregator a : aggregators) {
			if (a.target != null && assets.contains(a.target) && assets.contains(target)) {
				return false;
			}
		}
		return true;
	}
}
