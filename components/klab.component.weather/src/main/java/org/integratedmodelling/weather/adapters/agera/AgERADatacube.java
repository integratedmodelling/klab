package org.integratedmodelling.weather.adapters.agera;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.integratedmodelling.adapter.datacube.ChunkedDatacubeRepository;
import org.integratedmodelling.adapter.datacube.Datacube;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Urn;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.time.ITime.Resolution;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.weather.WeatherComponent;

/**
 * The AgERA datacube re-envisioned for k.LAB. Aim is to make everything as
 * seamless as possible despite the weak API and the restrictions.
 * 
 * @author Ferd
 *
 */
public class AgERADatacube extends Datacube {

	private String username;
	private String apiKey;
	private Map<String, Variable> variables = new HashMap<>();
	private ChunkedDatacubeRepository repository;

	public static final String ID = "AgERA5";

	/**
	 * Properties for the adapter
	 */
	public static final String CDS_USER_NUMBER_PROPERTY = "cds.user.number";
	public static final String CDS_API_KEY_PROPERTY = "cds.api.key";
	public static final String CDS_DOWNLOAD_THREADS_PROPERTY = "cds.download.threads";

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
						+ "	amount of water vapour present in air expressed as a percentage of the amount\r\n"
						+ "	needed for saturation at the same temperature.",
				EnumSet.noneOf(Statistic.class), "relative_humidity", EnumSet.allOf(Timepoint.class), ""),
		AIR_TEMPERATURE("K", "Air temperature at a height of 2 metres above the surface",
				EnumSet.allOf(Statistic.class), "temperature", EnumSet.noneOf(Timepoint.class), "2m_temperature"),
		CLOUD_COVER(null /* percent hours covered/24 */,
				"The number of hours with clouds over the period\r\n" + " 00h-24h local time divided by 24 hours",
				EnumSet.of(Statistic.MEAN_24H), "cloud_cover", EnumSet.noneOf(Timepoint.class), "cloud_cover"),
		LIQUID_PRECIPITATION_DURATION_FRACTION(null /* fraction */,
				"Liquid precipitation duration fraction Dimensionless The number of hours with\r\n"
						+ "	precipitation over the period 00h-24h local time divided by 24 hours and per\r\n"
						+ "	unit of area. Liquid precipitation is equivalent to the height of the layer\r\n"
						+ "	of water that would have formed from precipitation had the water not\r\n"
						+ "	penetrated the soil, run off, or evaporated.",
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
				"Solid precipitation duration fraction	Dimensionless	The number of hours with solid precipitation (freezing rain, snow, wet snow, mixture of rain and snow, and ice pellets) over the period 00h-24h local time divided by 24 hours and per unit of area.",
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
		}

		private String id;
		public Variable variable;
		public Timepoint timepoint;
		public Statistic statistic;

		@Override
		public String toString() {
			return id;
		}

	}

	public AgERADatacube() {

		super(ID, AgERAUrnTranslationService.class, AgERAAvailabilityService.class, AgERAIngestionService.class,
				AgERACachingService.class, AgERAEncodingService.class, AgERAMaintenanceService.class);

		((AgERAIngestionService) ingestion).setDatacube(this);
		((AgERAEncodingService) encoding).setDatacube(this);
		((AgERAUrnTranslationService) urnTranslation).setDatacube(this);
		((AgERAAvailabilityService) availability).setDatacube(this);

//		this.repository = new ChunkedDatacubeRepository(Time.resolution(1, Resolution.Type.DAY),
//				Time.resolution(3, Resolution.Type.MONTH), TimeInstant.create(1979, 1, 1),
//				Configuration.INSTANCE.getDataPath(ID));
//
//		this.repository.setAggregationPoints(Time.resolution(1, Resolution.Type.WEEK),
//				Time.resolution(1, Resolution.Type.MONTH), Time.resolution(1, Resolution.Type.YEAR));

		for (Variable v : Variable.values()) {
			variables.put(v.codename, v);
		}

		/*
		 * check for credentials first. Conditional is only to make tests work outside
		 * of a running engine.
		 */
		if (Extensions.INSTANCE.getComponent(WeatherComponent.ID) != null) {
			this.username = Extensions.INSTANCE.getComponent(WeatherComponent.ID).getProperties()
					.getProperty(AgERADatacube.CDS_USER_NUMBER_PROPERTY);
			this.apiKey = Extensions.INSTANCE.getComponent(WeatherComponent.ID).getProperties()
					.getProperty(AgERADatacube.CDS_API_KEY_PROPERTY);
		}

		if (username == null || apiKey == null) {
			Logging.INSTANCE.warn("no credential for Copernicus CDS: AgERA datacube is offline");
			setOnline(false);
		}
	}

	/**
	 * Variable URN examples:
	 * 
	 * <pre>
	 * 		klab:weather:agera5:temperature            -> default statistic (mean)
	 * 		klab:weather:agera5:temperature.min        -> use min statistic
	 * 		klab:weather:agera5:relative_humidity.h12  -> ignore statistic (n/a) and use one timepoint
	 * 		klab:weather:agera5:relative_humidity.h12  -> ignore statistic (n/a) and use one timepoint
	 * 		klab:weather:agera5:relative_humidity      -> ignore statistic (n/a) and aggregate available timepoints
	 * </pre>
	 * 
	 * No variable has both timepoint and statistic options, so the split is
	 * unambiguous
	 * 
	 * @param urn
	 * @return
	 */
	public Collection<VariableConfiguration> getVariable(Urn urn) {
		return getVariable(urn.getResourceId());
	}

	public String getUsername() {
		return username;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getEndpointUrl(String request) {
		return "https://cds.climate.copernicus.eu/api/v2/" + request;
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
	public Type getResourceType(Urn urn) {
		return Type.PROCESS;
	}

	@Override
	protected IGeometry getResourceGeometry(Urn urn) {
		// TODO Auto-generated method stub
		return Geometry.create("\u03c41\u03c32");
	}

	@Override
	protected String getDescription() {
		// TODO Auto-generated method stub
		return "Zio peperone";
	}

}
