package org.integratedmodelling.adapter.copernicus.repositories;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.integratedmodelling.adapter.copernicus.CopernicusComponent;
import org.integratedmodelling.adapter.copernicus.datacubes.CopernicusCDSDatacube;
import org.integratedmodelling.cdm.utils.NetCDFUtils;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.data.Aggregation;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
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
import org.integratedmodelling.klab.rest.ResourceReference;
import org.integratedmodelling.klab.utils.MiscUtilities;

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

	private Map<String, Variable> variables = new HashMap<>();
	private Map<String, String> fileTemplates = new HashMap<>();

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

		WIND_SPEED("m/s",
				"Mean wind speed at a height of 10 metres above the surface over the period 00h-24h local time",
				EnumSet.of(Statistic.MEAN_24H), "wind_speed", EnumSet.noneOf(Timepoint.class), "10m_wind_speed"),
		DEWPOINT_TEMPERATURE("K", "Mean dewpoint temperature at a height of 2 metres above the surface over the"
				+ " period 00h-24h local time. The dew point is the temperature to which air must be cooled to become saturated with water vapor. "
				+ "In combination with the air temperature it is used to assess relative humidity.",
				EnumSet.of(Statistic.MEAN_24H), "dewpoint", EnumSet.noneOf(Timepoint.class), "2m_dewpoint_temperature"),
		RELATIVE_HUMIDITY(null /* percent */,
				"Relative humidity at 06h, 09h, 12h. 15h, 18h (local\r\n"
						+ " time) at a height of 2 metres above the surface. This variable describes the\r\n"
						+ " amount of water vapour present in air expressed as a percentage of the amount\r\n"
						+ " needed for saturation at the same temperature.",
				EnumSet.noneOf(Statistic.class), "relative_humidity", EnumSet.allOf(Timepoint.class), ""),
		AIR_TEMPERATURE("K", "Air temperature at a height of 2 metres above the surface",
				EnumSet.allOf(Statistic.class), "temperature", EnumSet.noneOf(Timepoint.class), "2m_temperature"),
		CLOUD_COVER(null /* percent hours covered/24 */,
				"The number of hours with clouds over the period\r\n" + " 00h-24h local time divided by 24 hours",
				EnumSet.of(Statistic.MEAN_24H), "cloud_cover", EnumSet.noneOf(Timepoint.class), "cloud_cover"),
		LIQUID_PRECIPITATION_DURATION_FRACTION(null /* fraction */,
				"Liquid precipitation duration fraction Dimensionless The number of hours with\r\n"
						+ " precipitation over the period 00h-24h local time divided by 24 hours and per\r\n"
						+ " unit of area. Liquid precipitation is equivalent to the height of the layer\r\n"
						+ " of water that would have formed from precipitation had the water not\r\n"
						+ " penetrated the soil, run off, or evaporated.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_duration_fraction",
				EnumSet.noneOf(Timepoint.class), "liquid_precipitation_duration_fraction"),
		LIQUID_PRECIPITATION_VOLUME("mm/day",
				"Total volume of liquid water (mm3) precipitated over the period 00h-24h local time per unit of area (mm2), per day.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_volume", EnumSet.noneOf(Timepoint.class),
				"precipitation_flux"),
		SNOW_THICKNESS("cm",
				"Mean snow depth over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2)",
				EnumSet.of(Statistic.MEAN_24H), "snow_thickness", EnumSet.noneOf(Timepoint.class), "snow_thickness"),
		SNOW_DDEPTH_LWE("cm",
				"Mean snow depth liquid water equivalent (LWE) over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2) if all the snow had melted and had not penetrated the soil, runoff, or evaporated.me",
				EnumSet.of(Statistic.MEAN_24H), "snow_depth_lwe", EnumSet.noneOf(Timepoint.class),
				"snow_thickness_lwe"),
		SOLAR_RADIATION("J/m^2*day",
				"Total amount of energy provided by solar radiation at the surface over the period 00-24h local time per unit area and time.",
				EnumSet.noneOf(Statistic.class), "solar_radiation", EnumSet.noneOf(Timepoint.class),
				"solar_radiation_flux"),
		SOLID_PRECIPITATION_DURATION_FRACTION(null /*
													 * fraction
													 */,
				"Solid precipitation duration fraction  Dimensionless   The number of hours with solid precipitation (freezing rain, snow, wet snow, mixture of rain and snow, and ice pellets) over the period 00h-24h local time divided by 24 hours and per unit of area.",
				EnumSet.noneOf(Statistic.class), "solid_precipitation_fraction", EnumSet.noneOf(Timepoint.class),
				"solid_precipitation_duration_fraction"),
		VAPOR_PRESSURE("hPa",
				"Contribution to the total atmospheric pressure provided by the water vapour over the period 00-24h local time per unit of time",
				EnumSet.of(Statistic.MEAN_24H), "vapor_pressure", EnumSet.noneOf(Timepoint.class), "vapour_pressure");

		public IUnit unit;
		public String description;
		public Set<Statistic> statistics;
		public Set<Timepoint> timepoints;
		public String codename;
		public String cdsname;

		Variable(String unit, String description, Set<Statistic> statistics, String codename, Set<Timepoint> timepoints,
				String cdsname) {
			if (unit != null) {
				this.unit = Unit.create(unit);
			}
			this.description = description;
			this.statistics = statistics;
			this.codename = codename;
			this.timepoints = timepoints;
			this.cdsname = cdsname;
		}

	}

	public class VariableConfiguration {

		public VariableConfiguration(String string) {
			String[] fields = string.split("\\.");
			this.id = string;
			this.variable = variables.get(fields[0]);

			/*
			 * TODO! fill in statistics and defaults, including the forced time for humidity
			 * data if requested.
			 */

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
	}

	public AgERA5Repository() {
		super("sis-agrometeorological-indicators", TimeInstant.create(1979, 1, 1));
		this.setAggregationPoints(Time.resolution(1, Resolution.Type.WEEK), Time.resolution(1, Resolution.Type.MONTH),
				Time.resolution(1, Resolution.Type.YEAR));
		for (Variable v : Variable.values()) {
			variables.put(v.codename, v);
		}
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
		String template = fileTemplates.get(var.variable.codename);
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
				fileTemplates.put(var.variable.codename, template);
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
					getAggregatedLayer(var.variable.codename, startTick, endTick), destinationFile, null);
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

	private Aggregation getAggregation(Variable variable) {
		return variable == Variable.LIQUID_PRECIPITATION_VOLUME ? Aggregation.SUM : Aggregation.MEAN;
	}

	@Override
	protected Type getResourceType(Urn urn) {
		return Type.PROCESS;
	}

	@Override
	public IResource getResource(String urn) {

		Urn kurn = new Urn(urn);
		ResourceReference ref = new ResourceReference();
		ref.setUrn(kurn.getUrn());
		ref.setAdapterType(CopernicusComponent.ID);
		ref.setLocalName(kurn.getResourceId());
		ref.setGeometry(getResourceGeometry(kurn).encode());
		ref.setVersion(Version.CURRENT);
		ref.setType(getResourceType(kurn));

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
				return null;
			}
		}

		return new Resource(ref);
	}

	@Override
	protected IGeometry getResourceGeometry(Urn urn) {
		// TODO add 10km grid for globe
		return Geometry.create("\u03c41\u03c32");
	}


}
