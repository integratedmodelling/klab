package org.integratedmodelling.stats.reporting;

import static org.integratedmodelling.klab.components.time.extents.Time.resolution;

import java.sql.SQLException;
import java.util.ArrayList;
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
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.components.time.extents.TemporalExtension;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.utils.Escape;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.StatsComponent;
import org.integratedmodelling.stats.database.StatsDatabase;
import org.springframework.util.StringUtils;

import com.ibm.icu.text.NumberFormat;

/**
 * A report, including definition, generation and encoding to
 * text/Markdown/html/csv.
 * 
 * @author Ferd
 *
 */
public class StatsReport {

	boolean isComputed = false;

	public enum Target {
		Resources, Models, Operations, Contexts, Observations, Observables, Users, Downloads, Applications, Engines,
		/*
		 * This one only for filtering
		 */
		Groups;

		public boolean isAsset() {
			return this == Resources || this == Models || this == Operations || this == Observables;
		}
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

	public enum Format {
		Text, Html, Csv, Markdown
	}

	/*
	 * these are all handled directly as SQL constraints in the query
	 */
	class Filter {
		Target target;
		List<String> whitelist = new ArrayList<>();
		List<String> blacklist = new ArrayList<>();

		public String asSQLRestriction() {

			String field = null;
			boolean partialMatch = false;
			Target mustHave = null;

			switch (target) {
			case Applications:
				field = "application";
				break;
			case Engines:
				field = "engine_type";
				break;
			case Groups:
				field = "groups";
				partialMatch = true;
				break;
			case Models:
				field = "asset";
				mustHave = Target.Models;
				break;
			case Observables:
				field = "asset";
				mustHave = Target.Observables;
				break;
			case Observations:
				field = "query_observable";
				break;
			case Contexts:
				field = "context_name";
				break;
			case Operations:
				field = "asset";
				mustHave = Target.Operations;
				break;
			case Resources:
				field = "asset";
				mustHave = Target.Resources;
				break;
			case Users:
				field = "principal";
				break;
			default:
				break;
			}

			if (mustHave != null && !hasTarget(mustHave)) {
				throw new KlabIllegalArgumentException(
						"cannot filter on " + target + " without reporting the same asset");
			}

			if (field != null) {
				StringBuffer ret = new StringBuffer(512);
				for (String s : whitelist) {
					ret.append(ret.length() == 0 ? "" : " OR ");
					ret.append(field + " LIKE " + makePattern(s, partialMatch));
				}
				for (String s : blacklist) {
					ret.append(ret.length() == 0 ? "" : " OR ");
					ret.append(field + " NOT LIKE " + makePattern(s, partialMatch));
				}
				if (ret.length() > 0) {
					return "AND (" + ret + ")";
				}
			}

			return "";
		}

		private String makePattern(String s, boolean partialMatch) {
			String ret = s.replace("_", "#_").replace("$", "_").replace("*", "%");
			return "'" + (partialMatch ? "%" : "") + Escape.forSQL(ret) + (partialMatch ? "%" : "") + "' ESCAPE '#'";
		}
	}

	boolean adjustInterval = true;

	/*
	 * costs are associated only with contexts, and their computation must see all
	 * assets.
	 */
	boolean computeCosts = false;

	Format format = Format.Text;

	/*
	 * we aggregate either by period or by target. We keep a list of these and use
	 * it to build the report in the order of aggregation specified.
	 */
	static class Aggregator {

		Frequency aggregationInterval = null;
		int aggregationMultiplier = 1;
		Target target = null;

		@Override
		public int hashCode() {
			return Objects.hash(aggregationInterval, aggregationMultiplier, target);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Aggregator other = (Aggregator) obj;
			return aggregationInterval == other.aggregationInterval
					&& aggregationMultiplier == other.aggregationMultiplier && target == other.target;
		}

		@Override
		public String toString() {
			return "<" + (aggregationInterval == null ? target : (aggregationMultiplier + " " + aggregationInterval))
					+ ">";
		}
	}

	// the main targets, either a target type or a temporal span
	List<Aggregator> aggregators = new ArrayList<>();

	// filters
	long start = -1;
	long end = -1;
	List<Filter> filters = new ArrayList<>();

	boolean anonymized = true;
	boolean includeErrors = false;
	boolean includeSuccess = true;

	/**
	 * Indexable aggregation set that serves as a key for the "chapters" in the
	 * report. Contains all keys used to group the data and sorts intelligently
	 * based on the order of aggregation.
	 * 
	 * Asset types use the asset name as key, i.e. different uses of the same asset
	 * aggregate. Contexts and queries use the observation ID as key so they only
	 * aggregate within the same observation.
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
		String engine;

		// not a key
		String observation;
		String context_name;
		boolean sequential = true;
		boolean isDownloadQuery = false;

		@Override
		public int hashCode() {
			return Objects.hash(end, group, model, observable, operation, resource, start, user, download, application,
					context_id, query_id, engine);
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
					&& Objects.equals(context_id, other.context_id) && Objects.equals(engine, other.engine);
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
			if (engine != null) {
				ret += (ret.isEmpty() ? "" : " ") + "eng=" + engine;
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
				} else if (aggregator.target == Target.Engines) {

					if (engine != null && o.engine != null) {
						int ret = engine.compareTo(o.engine);
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
				if (s == null) {
					s = "k.Explorer";
				}
				this.application = s;
				break;
			case Engines:
				s = result.get("engine_type", String.class);
				if (s != null) {
					this.engine = s;
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
				String ret = StringUtils.capitalize(getResolution(aggregator.aggregationInterval).name().toLowerCase())
						+ " (" + Time.INSTANCE.printPeriod(end - start) + ") " + TimeInstant.create(start) + " to "
						+ TimeInstant.create(end);
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
				case Engines:
					return new Pair<>(engine, engine);
				case Groups:
					throw new KlabIllegalArgumentException("groups can only be used as filters");
				}
			}
			return new Pair<>("", "");
		}

	}

	class Data {

		long start = 0, end = 0;
		boolean error = false;
		int count = 0;
		boolean export = false;
		boolean setCredits = false;
		double totalTime;
		double totalSize;
		double downloadSize;
		long totalAccesses;
		int totalAssets;
		int totalObservations;
		double totalComplexity;
		String created;
		Aggregator aggregator;

		// to compute aggregated observation time avoiding double counting
		Set<String> observations = new HashSet<>();

		public Data(long start, long end) {
			this.start = start;
			this.end = end;
		}

		/**
		 * Each data bag will see all assets connected to a particular key, i.e. an
		 * asset bag will see a single asset (with N passes), an observation bag will
		 * see all assets pertaining to that observation, etc. If there is an asset
		 * aggregator, only assets of that type (and all of them) will come through
		 * here, no matter the level of aggregation.
		 * 
		 * If we want to compute costs, we do that on a context base, and we need to see
		 * every asset, so it should be forbidden to use an asset classifier. If assets
		 * for a given context have individual "pricing", only those prices should be
		 * computed; otherwise use the reference database and compute the k.LAB credits
		 * based on the averages.
		 * 
		 * @param result
		 * @param set
		 */
		public void accumulate(IParameters<String> result, Aggregator aggregator) {

			if (!error && !"Success".equals(result.get("outcome"))) {
				error = true;
			}

			// substituted every time but the type will be consistent
			this.aggregator = aggregator;

			if (aggregator.target != null) {
				if (aggregator.target.isAsset()) {
					totalTime += result.get("time", Number.class).doubleValue();
					totalAccesses += result.get("passes", Number.class).longValue();
				}
				if (aggregator.target == Target.Downloads) {
					downloadSize += result.get("byte_size", Number.class).doubleValue();
				} else if (aggregator.target == Target.Observations) {
					// =, not +=! - redefine (with same value) every time.
					if (result.get("is_download", Boolean.class)) {
						export = true;
					} else {
						totalTime = result.get("query_time", Number.class).doubleValue();
					}
				} else if (aggregator.target == Target.Contexts) {

					created = TimeInstant.create(result.get("created", Number.class).longValue()).toString();

					/*
					 * sum up values for each query.
					 */
					String qid = result.get("context_id", String.class) + "_" + result.get("query_id", Long.class);
					if (!observations.contains(qid)) {
						totalTime += result.get("query_time", Number.class).doubleValue();
						observations.add(qid);
					}

					/*
					 * complexity 
					 */
					double complexity = result.get("space_complexity", Number.class).doubleValue();
					complexity /= 5.0;
					if (complexity == 1) {
						complexity = 0;
					}
					totalComplexity += complexity;
				} 
			}

			if (aggregator.target != Target.Downloads) {
				totalSize += result.get("scale_size", Number.class).doubleValue();
			}

			count++;
		}

		@Override
		public String toString() {

			if (aggregator.target != null) {
				if (aggregator.target == Target.Contexts) {
					double contextSize = totalSize / (double) count;
					double complexity = (double) totalComplexity / (double) count;
					return "[" + count + " assets; size = " + NumberFormat.getInstance().format(contextSize)
							+ "; complexity = " + NumberFormat.getInstance().format(complexity) + "; total time = "
							+ NumberFormat.getInstance().format(totalTime) + "s]" + " created " + created;
				} else if (aggregator.target.isAsset()) {
					return "[accessed " + (totalAccesses == 0 ? 1 : totalAccesses) + " times; total time = "
							+ NumberFormat.getInstance().format(totalTime) + "s] ";// + ret;
				} else if (aggregator.target == Target.Downloads) {
					return "[bytes downloaded = " + NumberFormat.getIntegerInstance().format(downloadSize) + "] ";
				} else if (aggregator.target == Target.Observations) {
					return "["
							+ (export ? " export "
									: (count + " assets; total time = " + NumberFormat.getInstance().format(totalTime)))
							+ "] ";
				} else if (aggregator.target == Target.Users) {
					return "[total observation time = " + NumberFormat.getInstance().format(totalTime) + "]";
				}
			}

			return "";
		}

	}

	/*
	 * this holds the keys for the computed report. The sets are sorted after
	 * computation.
	 */
	Set<AggregationSet> report = new HashSet<>();

	/*
	 * As we accumulate each asset, the relative data are accumulated at each of the
	 * aggregation levels stored in the aggregators list.
	 */
	Map<Aggregator, Map<String, Data>> accountingData = new HashMap<>();

	TemporalExtension timeline = null;
	private boolean errorLogged;
	private Resolution.Type temporalAggregation;

	public void setReportingStart(long start) {
		this.start = start;
	}

	public boolean hasTarget(Target mustHave) {
		for (Aggregator aggregator : aggregators) {
			if (mustHave == aggregator.target) {
				return true;
			}
		}
		return false;
	}

	public void setReportingEnd(long end) {
		this.end = end;
	}

	public void setFormat(Format format) {
		this.format = format;
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
		this.temporalAggregation = getResolution(frequency);
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

		if (adjustInterval && this.temporalAggregation != null) {
			st.set(TimeInstant.create(st.get()).beginOf(temporalAggregation).getMilliseconds());
			en.set(TimeInstant.create(en.get()).endOf(temporalAggregation).getMilliseconds());
		}

		db.scan(getQuery(st.get(), en.get()), (result) -> {
			try {
				AggregationSet set = getAggregationSet(result, st.get(), en.get());
				if (set != null) {
					report.add(set);
					accumulateData(result, set);
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
				+ " queries.start_time as query_start_time, queries.is_download, "
				+ "	assets.total_time_sec as time, assets.total_passes as passes, assets.name as asset,"
				+ " assets.total_byte_size as byte_size, \n"
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

		for (Filter filter : filters) {
			ret += "		      " + filter.asSQLRestriction() + "\n";
		}

		/*
		 * this is only for speed, non-applying assets are still weeded out by the
		 * logics
		 */
		String assetType = getAssetType();
		if (assetType != null
				&& !computeCosts /* TODO && costs are not computed and aggregators do not include applications */) {
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

		List<AggregationSet> classifiers = new ArrayList<>(report);
		Collections.sort(classifiers);

		// TODO preamble
		StringBuffer ret = new StringBuffer(1024);
		List<Pair<String, String>> headers = new ArrayList<>();
		for (int i = 0; i < aggregators.size() - 1; i++) {
			headers.add(new Pair<>("-_-_-_", "-_-_-_"));
		}

		for (AggregationSet classifier : classifiers) {
			for (int level = 0; level < aggregators.size() - 1; level++) {
				Pair<String, String> title = classifier.getTitle(aggregators.get(level));
				if (!headers.get(level).getFirst().equals(title.getFirst())) {
					headers.set(level, title);
					for (int i = level + 1; i < headers.size(); i++) {
						// force redefinition of lower headers
						headers.set(i, new Pair<>("-_-_-", "-_-_-"));
					}
					ret.append(StringUtil.spaces(level * 3) + title.getSecond() + " "
							+ getData(classifier, aggregators.get(level)) + "\n");
				}
			}
			ret.append(StringUtil.spaces((aggregators.size() - 1) * 3)
					+ classifier.getTitle(aggregators.get(aggregators.size() - 1)).getSecond() + " "
					+ getData(classifier, aggregators.get(aggregators.size() - 1)) + "\n");
		}

		return ret.toString();
	}

	private void accumulateData(Parameters<String> result, AggregationSet set) {

		for (Aggregator aggregator : aggregators) {
			String key = set.getTitle(aggregator).getFirst();
			Map<String, Data> data = accountingData.get(aggregator);
			if (data == null) {
				data = new HashMap<>();
				accountingData.put(aggregator, data);
			}

			Data bag = data.get(key);
			if (bag == null) {
				bag = new Data(set.start, set.end);
				data.put(key, bag);
			}

			bag.accumulate(result, aggregator);

		}
	}

	private String getData(AggregationSet classifier, Aggregator aggregator) {
		String key = classifier.getTitle(aggregator).getFirst();
		Map<String, Data> data = accountingData.get(aggregator);
		Data ret = data.get(key);
		return ret == null ? "" : ret.toString();
	}

	public void addTargetClassifier(Target target) {
		if (target == Target.Groups) {
			throw new KlabIllegalArgumentException("groups can only be used as filters");
		}
		if (!checkAssets(target)) {
			throw new KlabIllegalArgumentException("only one asset target can be used in a report");
		}
		Aggregator a = new Aggregator();
		a.target = target;
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

	public void setSpan(String[] span) {
		Pair<Long, Long> s = parseSpan(span);
		this.start = s.getFirst();
		this.end = s.getSecond();
	}

	/**
	 * Parse a span specification for the report. Used also in other places, so
	 * static and public.
	 * 
	 * @param span
	 * @return
	 */
	public static Pair<Long, Long> parseSpan(String[] span) {

		Resolution lag = null;

		ITimeInstant s = null;
		ITimeInstant e = null;
		long lastLong = -1;

		for (int i = 0; i < span.length; i++) {
			if (NumberUtils.encodesLong(span[i])) {
				lastLong = Long.parseLong(span[i]);
				if (lastLong > 10000) {
					if (s == null) {
						s = TimeInstant.create(lastLong);
					} else if (e == null) {
						e = TimeInstant.create(lastLong);
					} else {
						throw new KlabIllegalArgumentException("bad span specification " + span);
					}
				}
			} else {
				switch (span[i]) {
				case "year":
				case "years":
					lag = resolution(1, Resolution.Type.YEAR);
					break;
				case "day":
				case "days":
					lag = resolution(1, Resolution.Type.DAY);
					break;
				case "month":
				case "months":
					lag = resolution(1, Resolution.Type.MONTH);
					break;
				case "week":
				case "weeks":
					lag = resolution(1, Resolution.Type.WEEK);
					break;
				case "hour":
				case "hours":
					lag = resolution(1, Resolution.Type.HOUR);
					break;
				default:
					throw new KlabIllegalArgumentException("bad span specification " + span);
				}
			}
		}

		int multiplier = 1;
		if (lag != null) {
			if (lastLong > 0 && lastLong < 10000) {
				multiplier = (int) lastLong;
			}
			e = TimeInstant.create().endOf(lag.getType());
			s = e.minus(multiplier, lag);
		}

		if (e == null || s == null) {
			throw new KlabIllegalArgumentException("bad span specification " + span);
		}

		return new Pair<>(s.getMilliseconds(), e.getMilliseconds());
	}

	public void filterFor(Target target, String[] targets) {
		Filter filter = new Filter();
		filter.target = target;
		for (String t : targets) {
			if (t.startsWith("!")) {
				filter.blacklist.add(t.substring(1));
			} else {
				filter.whitelist.add(t.startsWith("+") ? t.substring(1) : t);
			}
		}

		filters.add(filter);
	}

	public void reportCosts(boolean doit) {
		this.computeCosts = doit;
	}

	public void reportErrors(boolean doit) {
		if (doit) {
			this.includeErrors = true;
			this.includeSuccess = false;
		} else {
			this.includeErrors = false;
			this.includeSuccess = true;
		}
	}
}
