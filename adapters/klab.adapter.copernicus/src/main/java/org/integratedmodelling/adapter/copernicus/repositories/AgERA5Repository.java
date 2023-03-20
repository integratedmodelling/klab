package org.integratedmodelling.adapter.copernicus.repositories;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.adapter.copernicus.CopernicusAdapter;
import org.integratedmodelling.adapter.copernicus.CopernicusStaticAdapter;
import org.integratedmodelling.adapter.copernicus.datacubes.CopernicusCDSDatacube;
import org.integratedmodelling.cdm.utils.NetCDFUtils;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.observations.scale.time.ITimeInstant;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.data.resources.Resource;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;

/**
 * AgERA4 Meteo data. The humidity data are 3-hourly and can only be downloaded
 * daily in this implementation. A time among the available will be forced and
 * can be selected through configuration. If 3-hourly data are needed, a
 * dedicated adapter should be derived from this one.
 * 
 * @author Ferd
 *
 */
public class AgERA5Repository extends CopernicusCDSDatacube {

	public static final String ID = "sis-agrometeorological-indicators";

	private Map<String, Variable> variables = new HashMap<>();
	private Map<String, Variable> svariables = new HashMap<>();
	private Map<String, String> fileTemplates = new HashMap<>();
	private Set<String> queued = new HashSet<>();

	private double STORM_PRECIPITATION_THRESHOLD_MMDAY = 1.0;

	public enum Statistic {

		MAX_24H("24_hour_maximum", "max"), MIN_24H("24_hour_minimum", "min"), DAY_MEAN("day_time_mean", "day"),
		NIGHT_MEAN("night_time_mean", "night"), MEAN_24H("24_hour_mean", "mean"),
		NIGHT_MIN("night_time_minimum", "night_min"), DAY_MAX("day_time_maximum", "day_max");

		public String cdsname;
		public String codename;

		Statistic(String cdsname, String codename) {
			this.cdsname = cdsname;
			this.codename = codename;
		}
	}

	/*
	 * codename is name()
	 */
	public enum Timepoint {

		h06("06_00"), h09("09_00"), h12("12_00"), h15("15_00"), h18("18_00");

		public String cdsname;

		Timepoint(String cdsname) {
			this.cdsname = cdsname;
		}
	}

	/**
	 * Variables with their codenames and applicable statistics and timepoints.
	 * 
	 * @author Ferd
	 *
	 */
	public enum Variable {

		WIND_SPEED("m/s", "wind",
				"Mean wind speed at a height of 10 metres above the surface over the period 00h-24h local time",
				EnumSet.of(Statistic.MEAN_24H), "wind_speed", EnumSet.noneOf(Timepoint.class), "10m_wind_speed"),
		DEWPOINT_TEMPERATURE("K", "dewpoint",
				"Mean dewpoint temperature at a height of 2 metres above the surface over the"
						+ " period 00h-24h local time. The dew point is the temperature to which air must be cooled to become saturated with water vapor. "
						+ "In combination with the air temperature it is used to assess relative humidity.",
				EnumSet.of(Statistic.MEAN_24H), "dewpoint", EnumSet.noneOf(Timepoint.class), "2m_dewpoint_temperature"),
		RELATIVE_HUMIDITY(null /* percent */, "humidity",
				"Relative humidity at 06h, 09h, 12h. 15h, 18h (local\r\n"
						+ " time) at a height of 2 metres above the surface. This variable describes the\r\n"
						+ " amount of water vapour present in air expressed as a percentage of the amount\r\n"
						+ " needed for saturation at the same temperature.",
				EnumSet.noneOf(Statistic.class), "relative_humidity", EnumSet.allOf(Timepoint.class), ""),
		AIR_TEMPERATURE("K", "temperature", "Air temperature at a height of 2 metres above the surface",
				EnumSet.allOf(Statistic.class), "temperature", EnumSet.noneOf(Timepoint.class), "2m_temperature"),
		CLOUD_COVER(null /* percent hours covered/24 */, "clouds",
				"The number of hours with clouds over the period\r\n" + " 00h-24h local time divided by 24 hours",
				EnumSet.of(Statistic.MEAN_24H), "cloud_cover", EnumSet.noneOf(Timepoint.class), "cloud_cover"),
		LIQUID_PRECIPITATION_DURATION_FRACTION(null /* fraction */, "lprecfrac",
				"Liquid precipitation duration fraction Dimensionless The number of hours with\r\n"
						+ " precipitation over the period 00h-24h local time divided by 24 hours and per\r\n"
						+ " unit of area. Liquid precipitation is equivalent to the height of the layer\r\n"
						+ " of water that would have formed from precipitation had the water not\r\n"
						+ " penetrated the soil, run off, or evaporated.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_duration_fraction",
				EnumSet.noneOf(Timepoint.class), "liquid_precipitation_duration_fraction"),
		LIQUID_PRECIPITATION_VOLUME("mm/day", "precipitation",
				"Total volume of liquid water (mm3) precipitated over the period 00h-24h local time per unit of area (mm2), per day.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_volume", EnumSet.noneOf(Timepoint.class),
				"precipitation_flux"),
		SNOW_THICKNESS("cm", "snowdepth",
				"Mean snow depth over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2)",
				EnumSet.of(Statistic.MEAN_24H), "snow_thickness", EnumSet.noneOf(Timepoint.class), "snow_thickness"),
		SNOW_DDEPTH_LWE("cm", "snowlwe",
				"Mean snow depth liquid water equivalent (LWE) over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2) if all the snow had melted and had not penetrated the soil, runoff, or evaporated.me",
				EnumSet.of(Statistic.MEAN_24H), "snow_depth_lwe", EnumSet.noneOf(Timepoint.class),
				"snow_thickness_lwe"),
		SOLAR_RADIATION("J/m^2*day", "solrad",
				"Total amount of energy provided by solar radiation at the surface over the period 00-24h local time per unit area and time.",
				EnumSet.noneOf(Statistic.class), "solar_radiation", EnumSet.noneOf(Timepoint.class),
				"solar_radiation_flux"),
		SOLID_PRECIPITATION_DURATION_FRACTION(null /*
													 * fraction
													 */, "sprecfrac",
				"Solid precipitation duration fraction  Dimensionless   The number of hours with solid precipitation (freezing rain, snow, wet snow, mixture of rain and snow, and ice pellets) over the period 00h-24h local time divided by 24 hours and per unit of area.",
				EnumSet.noneOf(Statistic.class), "solid_precipitation_fraction", EnumSet.noneOf(Timepoint.class),
				"solid_precipitation_duration_fraction"),
		VAPOR_PRESSURE("hPa", "vpress",
				"Contribution to the total atmospheric pressure provided by the water vapour over the period 00-24h local time per unit of time",
				EnumSet.of(Statistic.MEAN_24H), "vapor_pressure", EnumSet.noneOf(Timepoint.class), "vapour_pressure"),

		// specially handled vars not available as Copernicus datasets
		RAINY_DAYS_PER_MONTH(null, "raindaysmonth",
				"Number of rainy day in the month(s) covered by the temporal interval. If >1 month, weighted average based on the"
						+ "number of days.",
				EnumSet.noneOf(Statistic.class), "rainy_days_per_month", EnumSet.noneOf(Timepoint.class),
				"rainy_days_per_month");

		public IUnit unit;
		public String description;
		public Set<Statistic> statistics;
		public Set<Timepoint> timepoints;
		public String codename;
		public String cdsname;
		public String shortname;

		Variable(String unit, String shortname, String description, Set<Statistic> statistics, String codename,
				Set<Timepoint> timepoints, String cdsname) {
			if (unit != null) {
				this.unit = Unit.create(unit);
			}
			this.description = description;
			this.statistics = statistics;
			this.codename = codename;
			this.timepoints = timepoints;
			this.cdsname = cdsname;
			this.shortname = shortname;
		}

	}

	public class VariableConfiguration {

		public VariableConfiguration(String string) {

			String[] fields = string.split("\\.");
			this.id = string;
			this.variable = variables.get(fields[0]);
			if (this.variable == null) {
				this.variable = svariables.get(fields[0]);
			}

			// jeez
			if (this.variable == null) {
				/*
				 * check if this is one of the modified names. Get the first and the last
				 * underscore.
				 */
				if (StringUtil.countMatches(string, "_") > 0) {
					String var = string;
					for (Statistic tp : Statistic.values()) {
						if (var.startsWith(tp.codename + "_")) {
							this.statistic = tp;
							var = var.substring(tp.codename.length() + 1);
							break;
						}
					}
					this.variable = variables.get(var);
					if (this.variable == null) {
						this.variable = svariables.get(var);
					}
					if (this.variable /* still */ == null && StringUtil.countMatches(var, "_") > 0) {
						for (Timepoint tp : Timepoint.values()) {
							if (var.endsWith("_" + tp.cdsname)) {
								this.timepoint = tp;
								var = var.substring(0, var.length() - tp.cdsname.length() + 1);
								break;
							}
						}
						this.variable = variables.get(var);
						if (this.variable == null) {
							this.variable = svariables.get(var);
						}
					}
				}
			}

			for (int i = 1; i < fields.length; i++) {
				for (Timepoint tp : Timepoint.values()) {
					if (fields[i].equals(tp.cdsname)) {
						this.timepoint = tp;
						break;
					}
				}
				for (Statistic tp : Statistic.values()) {
					if (fields[i].equals(tp.codename)) {
						this.statistic = tp;
						break;
					}
				}
			}

			/*
			 * TODO! fill in statistics and defaults, including the forced time for humidity
			 * data if requested.
			 */
			if (this.variable != null) {
				if (this.timepoint == null && this.variable.timepoints.size() == 1) {
					this.timepoint = this.variable.timepoints.iterator().next();
				} else if (this.timepoint == null && !this.variable.timepoints.isEmpty()) {
					throw new KlabValidationException(
							"variable " + variable.codename + " requires the specification of a timepoint");
				}
				if (this.statistic == null && this.variable.statistics.size() == 1) {
					this.statistic = this.variable.statistics.iterator().next();
				} else if (this.statistic == null && !this.variable.statistics.isEmpty()) {
					throw new KlabValidationException(
							"variable " + variable.codename + " requires the specification of a statistic");
				}
			}
		}

		private String id;
		public Variable variable;
		public Timepoint timepoint;
		public Statistic statistic;

		@Override
		public String toString() {
			return id;
		}

		public boolean isOK() {
			return variable != null;
		}

		public String getVariableName() {
			return (statistic == null ? "" : (statistic.codename + "_")) + variable.codename
					+ (timepoint == null ? "" : ("_" + timepoint.cdsname));
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Objects.hash(statistic, timepoint, variable);
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			VariableConfiguration other = (VariableConfiguration) obj;
			return statistic == other.statistic && timepoint == other.timepoint && variable == other.variable;
		}

		public boolean matches(String variable2) {
			if (this.id.equals(variable2) || this.getVariableName().equals(variable2)
					|| this.id.replace(".", "_").equals(variable2)
					|| this.getVariableName().replace(".", "_").equals(variable2)) {
				return true;
			}
			return false;
		}
	}

	public AgERA5Repository() {
		super(ID, TimeInstant.create(1979, 1, 1), -9999.0);
		this.setAggregationPoints(Time.resolution(1, Resolution.Type.WEEK), Time.resolution(1, Resolution.Type.MONTH),
				Time.resolution(1, Resolution.Type.YEAR));
		for (Variable v : Variable.values()) {
			variables.put(v.codename, v);
			svariables.put(v.shortname, v);
		}

		registerSpecialVariable(Variable.RAINY_DAYS_PER_MONTH.codename, (scale) -> {
			return getRainyDaysPerMonth(scale);
		});

		/*
		 * TODO more online checks
		 */
	}

	public Collection<VariableConfiguration> getVariable(Urn urn) {
		return getVariable(urn.getResourceId());
	}

	public Collection<VariableConfiguration> getVariable(String string) {
		String[] vars = string.split(",");
		List<VariableConfiguration> ret = new ArrayList<>();
		for (String var : vars) {
			ret.add(new VariableConfiguration(var));
		}
		return ret;
	}

	@Override
	protected String getStateName(Urn urn, String variable) {

		/**
		 * Must match the var passed in the URN, with the possible exception of turning
		 * dots into underscores.
		 */
		String[] vars = urn.getResourceId().split(",");
		for (String var : vars) {
			VariableConfiguration vc = new VariableConfiguration(var);
			if (vc.matches(variable)) {
				return vc.id.replace(".", "_");
			}
		}
		return variable;

	}

	@Override
	protected void configureRequest(String variable, Map<String, Object> payload) {

		VariableConfiguration var = new VariableConfiguration(variable);
		if (!var.isOK()) {
			throw new KlabValidationException(
					"CDS repository for " + this.getName() + " does not recognize variable " + variable);
		}
		payload.put("variable", var.variable.cdsname);
		if (var.statistic != null) {
			payload.put("statistic", var.statistic.cdsname);
		}
		if (var.timepoint != null) {
			payload.put("time", var.timepoint.cdsname);
		}
	}

	@Override
	protected boolean checkRemoteAvailability(int chunk, String variable) {
		ITimeInstant endChunk = getChunkEnd(chunk);
		return endChunk.isBefore(TimeInstant.create().minus(1, Time.resolution(1, Resolution.Type.WEEK)));
	}

	@Override
	protected String getOriginalDataFilename(String variable, int tick, File containingDirectory) {

		VariableConfiguration var = new VariableConfiguration(variable);
		if (!var.isOK()) {
			throw new KlabValidationException(
					"CDS repository for " + this.getName() + " does not recognize variable " + variable);
		}

		ITimeInstant start = getTickStart(tick);
		String template = fileTemplates.get(var.getVariableName());
		if (template == null) {

			Pattern pattern = Pattern.compile(".*(_[0-9]{8}_).*");
			for (File f : containingDirectory.listFiles(new FileFilter() {

				@Override
				public boolean accept(File pathname) {
					return pathname.toString().endsWith(".nc");
				}
			})) {
				/*
				 * use as template
				 */
				String filename = MiscUtilities.getFileBaseName(f);
				Matcher matcher = pattern.matcher(filename);
				if (!matcher.matches()) {
					continue;
				}
				template = filename.replace(matcher.group(1), "XXXXXXXX");
				fileTemplates.put(var.getVariableName(), template);
				break;
			}
		}

		return template == null ? "no_idea.boh"
				: (template.replace("XXXXXXXX",
						"_" + String.format("%4d%02d%02d", start.getYear(), start.getMonth(), start.getDay()) + "_")
						+ ".nc");
	}

	@Override
	protected boolean createAggregatedLayer(String variable, int startTick, int endTick, ITime.Resolution resolution,
			File destinationFile) {

		VariableConfiguration var = new VariableConfiguration(variable);
		if (!var.isOK()) {
			throw new KlabValidationException(
					"CDS repository for " + this.getName() + " does not recognize variable " + variable);
		}

		List<File> toAggregate = new ArrayList<>();
		for (int tick = startTick; tick <= endTick; tick++) {
			toAggregate.add(getDataFile(variable, tick));
		}

		/*
		 * Create the aggregation (as a GeoTIFF) and return the variable
		 */
		if (NetCDFUtils.aggregateGrid(toAggregate, destinationFile, resolution, getAggregation(var.variable),
				-9999.0)) {
			/*
			 * ingest in GS
			 */
			return getGeoserver().createCoverageLayer(this.getName(),
					getAggregatedLayer(var.getVariableName(), startTick, endTick), destinationFile, null);
		}

		return false;
	}

	@Override
	protected String getAggregatedLayer(String variable, int startTick, int endTick) {
		ITimeInstant start = getTickStart(startTick);
		ITimeInstant end = getTickEnd(endTick);
		return variable + "__" + String.format("%4d%02d%02d", start.getYear(), start.getMonth(), start.getDay()) + "_"
				+ String.format("%4d%02d%02d", end.getYear(), end.getMonth(), end.getDay());
	}

	@Override
	protected String getAggregatedFilename(String variable, int startTick, int endTick) {
		return getAggregatedLayer(variable, startTick, endTick) + ".tiff";
	}

	@Override
	public Aggregation getAggregation(String variable) {
		VariableConfiguration v = new VariableConfiguration(variable);
		return getAggregation(v.variable);
	}

	private Aggregation getAggregation(Variable variable) {
		/*
		 * leave everything as is and take means, so that the mm/day annotation is
		 * honored.
		 */
		return /* variable == Variable.LIQUID_PRECIPITATION_VOLUME ? Aggregation.SUM : */ Aggregation.MEAN;
	}

	@Override
	protected Type getResourceType(Urn urn, boolean dynamic) {
		return dynamic ? Type.PROCESS : Type.NUMBER;
	}

	@Override
	public IResource getResource(String urn, boolean dynamic) {

		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(kurn.getUrn());
		ref.setAdapterType(dynamic ? CopernicusAdapter.ID : CopernicusStaticAdapter.ID);
		ref.setLocalName(kurn.getResourceId());
		// TODO this really needs the bbox and resolution when used statically
		ref.setGeometry(getResourceGeometry(kurn).encode());
		ref.setVersion(Version.CURRENT);
		ref.setType(getResourceType(kurn, dynamic));

		int i = 0;
		ref.setOutputs(new ArrayList<>());
		for (VariableConfiguration v : getVariable(kurn.getResourceId())) {
			try {
				AttributeReference attr = new AttributeReference();
				attr.setIndex(i++);
				attr.setType(Type.NUMBER);
				attr.setName(v.variable.codename);
				ref.getOutputs().add(attr);
			} catch (Throwable t) {
				ref.getNotifications().add(new Notification(
						"Copernicus adapter for " + kurn.getCatalog() + " does not recognize variable '" + v + "'",
						Level.SEVERE.getName()));
			}
		}

		return new Resource(ref);
	}

	@Override
	protected IGeometry getResourceGeometry(Urn urn) {
		return Geometry.create("t1{ttype=GRID}S2");
	}

	@Override
	protected Collection<String> getVariableNames(Urn urn) {
		List<String> ret = new ArrayList<>();
		String[] vars = urn.getResourceId().split(",");
		for (String var : vars) {
			VariableConfiguration vc = new VariableConfiguration(var);
			if (vc.variable != null) {
				ret.add(vc.getVariableName());
			}
		}
		return ret;
	}

	@Override
	protected IUnit getOriginalUnit(String variable) {
		VariableConfiguration vc = new VariableConfiguration(variable);
		if (vc.variable != null && vc.variable.unit != null) {
			return Unit.create(vc.variable.unit);
		}
		return null;
	}

	/**
	 * TODO must have precipitation available and insert the necessary processing in
	 * the granules. Strategy should copy the one for precipitation and add the
	 * processing plus any wait time for it.
	 * 
	 * @param time
	 * @return
	 */
	private Strategy getRainyDaysPerMonth(IScale scale) {

		Strategy ret = getStrategy(Variable.LIQUID_PRECIPITATION_VOLUME.codename, scale);

		/*
		 * no precipitation, no rainy days
		 */
		if (ret.getTimeToAvailabilitySeconds() != 0) {
			return ret;
		}

		List<Granule> granules = new ArrayList<>();
		ITimeInstant start = scale.getTime().getStart();
		int tavail = 0;
		boolean done = true;
		do {
			Granule granule = new Granule();
			int month = start.getMonth();
			int days = 0;
			int year = start.getYear();
			while (start.getMonth() == month && !start.isAfter(scale.getTime().getEnd())) {
				days++;
				start = start.plus(1, Time.resolution(1, Resolution.Type.DAY));
			}
			// this sets the delay to 60 for each missing aggregation
			if (!requireRainyDaysCoverage(month, year, granule)) {
				done = false;
			}

			granule.multiplier = days;
			tavail += granule.aggregationTimeSeconds;
			granules.add(granule);

		} while (start.getMilliseconds() < scale.getTime().getEnd().getMilliseconds());

		ret.setVariable(Variable.RAINY_DAYS_PER_MONTH.codename);
		ret.granules.clear();
		ret.granules.addAll(granules);
		ret.setFinished(done);

		ret.setTimeToAvailability(tavail);

		return ret;
	}

	private boolean requireRainyDaysCoverage(int month, int year, Granule granule) {

		File folder = new File(this.getDataFolder() + File.separator + "derived");
		folder.mkdirs();
		final File retfil = new File(
				folder + File.separator + Variable.RAINY_DAYS_PER_MONTH.cdsname + "_" + month + "_" + year + ".tiff");
		final String layerName = Variable.RAINY_DAYS_PER_MONTH.cdsname + "_" + month + "_" + year;
		final String id = month + "," + year;

		boolean ret = retfil.exists();

		if (!retfil.exists() && !queued.contains(id)) {

			final List<File> sourcePrecipitation = new ArrayList<>();
			ITimeInstant start = TimeInstant.create(year, month, 1);
			ITimeInstant end = start.plus(1, Time.resolution(1, Resolution.Type.MONTH));
			for (Pair<Integer, Integer> tick : getTicks(
					Time.create(start, end, Time.resolution(1, Resolution.Type.DAY)))) {
				sourcePrecipitation.add(getDataFile(Variable.LIQUID_PRECIPITATION_VOLUME.codename, tick.getFirst()));
			}
			queued.add(id);

			executor.execute(new Thread() {

				@Override
				public void run() {

					Logging.INSTANCE.info(
							"Creating monthly counts of days with precipitation for month " + month + " of " + year);

					if (NetCDFUtils.aggregateGrid(sourcePrecipitation, retfil,
							Time.resolution(1, Resolution.Type.MONTH), Aggregation.SUM, Double.NaN, (value) -> {
								return value > STORM_PRECIPITATION_THRESHOLD_MMDAY ? 1.0 : 0.0;
							})) {
						if (!getGeoserver().createCoverageLayer(AgERA5Repository.this.getName(), layerName, retfil,
								null)) {
							Logging.INSTANCE.warn("Geoserver ingestion of " + retfil + " returned a failure code");
						} else {
							Logging.INSTANCE.info("Geoserver ingestion of " + retfil + " successful as "
									+ AgERA5Repository.this.getName() + ":" + layerName);
						}

					} else {
						Logging.INSTANCE.info("Creation of monthly counts of days with precipitation for month " + month
								+ " of " + year + " successful");
					}
				}

			});

			granule.aggregationTimeSeconds += 60;
		}

		granule.layerName = layerName;
		granule.dataFile = retfil;

		return ret;
	}

}
