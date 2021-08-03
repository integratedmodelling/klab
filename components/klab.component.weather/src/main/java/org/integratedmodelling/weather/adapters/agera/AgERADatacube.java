package org.integratedmodelling.weather.adapters.agera;

import java.util.EnumSet;
import java.util.Set;

import org.integratedmodelling.adapter.datacube.Datacube;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.weather.WeatherComponent;

/**
 * The AgERA datacube re-envisioned for k.LAB. Aim is to make everything as
 * seamless as possible despite the weak API and the restrictions.
 * 
 * @author Ferd
 *
 */
public class AgERADatacube extends Datacube {

	public static final String ID = "AgERA5";
	
	/**
	 * Properties for the adapter
	 */
	public static final String CDS_USER_NUMBER_PROPERTY = "cds.user.number";
	public static final String CDS_API_KEY_PROPERTY = "cds.api.key";

	public enum Statistic {
		MAX_24H, MIN_24H, DAY_MEAN, NIGHT_MEAN, MEAN_24H, NIGHT_MIN, DAY_MAX
	}

	public enum Timepoint {
		h06, h09, h12, h15, h18
	}

	/**
	 * Variables with their codenames and available statistics and timepoints. 
	 * @author Ferd
	 *
	 */
	public enum Variable {

		WIND_SPEED("m/s",
				"Mean wind speed at a height of 10 metres above the surface over the period 00h-24h local time",
				EnumSet.of(Statistic.MEAN_24H), "wind_speed", EnumSet.noneOf(Timepoint.class)),
		DEWPOINT_TEMPERATURE("K", "Mean dewpoint temperature at a height of 2 metres above the surface over the"
				+ " period 00h-24h local time. The dew point is the temperature to which air must be cooled to become saturated with water vapor. "
				+ "In combination with the air temperature it is used to assess relative humidity.",
				EnumSet.of(Statistic.MEAN_24H), "dewpoint", EnumSet.noneOf(Timepoint.class)),
		RELATIVE_HUMIDITY(null /* percent */,
				"Relative humidity at 06h, 09h, 12h. 15h, 18h (local\r\n"
						+ " time) at a height of 2 metres above the surface. This variable describes the\r\n"
						+ "	amount of water vapour present in air expressed as a percentage of the amount\r\n"
						+ "	needed for saturation at the same temperature.",
				EnumSet.noneOf(Statistic.class), "relative_humidity", EnumSet.allOf(Timepoint.class)),
		AIR_TEMPERATURE("K", "Air temperature at a height of 2 metres above the surface",
				EnumSet.of(Statistic.MEAN_24H), "temperature", EnumSet.noneOf(Timepoint.class)),
		CLOUD_COVER(null /* percent hours covered/24 */,
				"The number of hours with clouds over the period\r\n" + " 00h-24h local time divided by 24 hours",
				EnumSet.allOf(Statistic.class), "cloud_cover", EnumSet.noneOf(Timepoint.class)),
		LIQUID_PRECIPITATION_DURATION_FRACTION(null /* fraction */,
				"Liquid precipitation duration fraction Dimensionless The number of hours with\r\n"
						+ "	precipitation over the period 00h-24h local time divided by 24 hours and per\r\n"
						+ "	unit of area. Liquid precipitation is equivalent to the height of the layer\r\n"
						+ "	of water that would have formed from precipitation had the water not\r\n"
						+ "	penetrated the soil, run off, or evaporated.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_duration_fraction", EnumSet.noneOf(Timepoint.class)),
		LIQUID_PRECIPITATION_VOLUME("mm/day",
				"Total volume of liquid water (mm3) precipitated over the period 00h-24h local time per unit of area (mm2), per day.",
				EnumSet.noneOf(Statistic.class), "liquid_precipitation_volume", EnumSet.noneOf(Timepoint.class)),
		SNOW_THICKNESS("cm",
				"Mean snow depth over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2)",
				EnumSet.of(Statistic.MEAN_24H), "snow_thickness", EnumSet.noneOf(Timepoint.class)),
		SNOW_DDEPTH_LWE("cm",
				"Mean snow depth liquid water equivalent (LWE) over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2) if all the snow had melted and had not penetrated the soil, runoff, or evaporated.me",
				EnumSet.of(Statistic.MEAN_24H), "snow_depth_lwe", EnumSet.noneOf(Timepoint.class)),
		SOLAR_RADIATION("J/m^2*day",
				"Total amount of energy provided by solar radiation at the surface over the period 00-24h local time per unit area and time.",
				EnumSet.noneOf(Statistic.class), "solar_radiation", EnumSet.noneOf(Timepoint.class)),
		SOLID_PRECIPITATION_DURATION_FRACTION(null /* fraction */,
				"Solid precipitation duration fraction	Dimensionless	The number of hours with solid precipitation (freezing rain, snow, wet snow, mixture of rain and snow, and ice pellets) over the period 00h-24h local time divided by 24 hours and per unit of area.",
				EnumSet.noneOf(Statistic.class), "solid_precipitation_fraction", EnumSet.noneOf(Timepoint.class)),
		VAPOR_PRESSURE("hPa",
				"Contribution to the total atmospheric pressure provided by the water vapour over the period 00-24h local time per unit of time",
				EnumSet.of(Statistic.MEAN_24H), "vapor_pressure", EnumSet.noneOf(Timepoint.class));

		IUnit unit;
		String description;
		Set<Statistic> statistics;
		Set<Timepoint> timepoints;
		String codename;

		Variable(String unit, String description, Set<Statistic> statistics, String codename, Set<Timepoint> timepoints) {
			if (unit != null) {
				this.unit = Unit.create(unit);
			}
			this.description = description;
			this.statistics = statistics;
			this.codename = codename;
			this.timepoints = timepoints;
		}

	}

	public AgERADatacube() {
		super(ID, AgERAUrnTranslationService.class, AgERAAvailabilityService.class, AgERAIngestionService.class,
				AgERACachingService.class, AgERAEncodingService.class, AgERAMaintenanceService.class);

		/*
		 * check for credentials first
		 */
		String username = Extensions.INSTANCE.getComponent(WeatherComponent.ID).getProperties()
				.getProperty(AgERADatacube.CDS_USER_NUMBER_PROPERTY);
		String apiKey = Extensions.INSTANCE.getComponent(WeatherComponent.ID).getProperties()
				.getProperty(AgERADatacube.CDS_API_KEY_PROPERTY);

		if (username == null || apiKey == null) {
			Logging.INSTANCE.warn("no credential for Copernicus CDS: AgERA datacube is offline");
			setOnline(false);
		}
	}

}
