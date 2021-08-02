package org.integratedmodelling.weather.adapters.agera;

import org.integratedmodelling.adapter.datacube.Datacube;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.common.mediation.Unit;

/**
 * The AgERA datacube re-envisioned for k.LAB. Aim is to make everything as
 * seamless as possible despite the weak API and the restrictions.
 * 
 * @author Ferd
 *
 */
public class AgERADatacube extends Datacube {

	public enum Variable {

		WIND_SPEED("m/s",
				"Mean wind speed at a height of 10 metres above the surface over the period 00h-24h local time");

//		2m dewpoint temperature	K	Mean dewpoint temperature at a height of 2 metres above the surface over the period 00h-24h local time. The dew point is the temperature to which air must be cooled to become saturated with water vapor. In combination with the air temperature it is used to assess relative humidity.
//		2m relative humidity	%	Relative humidity at 06h, 09h, 12h. 15h, 18h (local time) at a height of 2 metres above the surface. This variable describes the amount of water vapour present in air expressed as a percentage of the amount needed for saturation at the same temperature.
//		2m temperature	K	Air temperature at a height of 2 metres above the surface.
//		Cloud cover	Dimensionless	The number of hours with clouds over the period 00h-24h local time divided by 24 hours.
//		Liquid precipitation duration fraction	Dimensionless	The number of hours with precipitation over the period 00h-24h local time divided by 24 hours and per unit of area. Liquid precipitation is equivalent to the height of the layer of water that would have formed from precipitation had the water not penetrated the soil, run off, or evaporated.
//		Precipitation flux	mm day-1	Total volume of liquid water (mm3) precipitated over the period 00h-24h local time per unit of area (mm2), per day.
//		Snow thickness	cm	Mean snow depth over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2).
//		Snow thickness LWE	cm	Mean snow depth liquid water equivalent (LWE) over the period 00h-24h local time measured as volume of snow (cm3) per unit area (cm2) if all the snow had melted and had not penetrated the soil, runoff, or evaporated.
//		Solar radiation flux	J m-2 day-1	Total amount of energy provided by solar radiation at the surface over the period 00-24h local time per unit area and time.
//		Solid precipitation duration fraction	Dimensionless	The number of hours with solid precipitation (freezing rain, snow, wet snow, mixture of rain and snow, and ice pellets) over the period 00h-24h local time divided by 24 hours and per unit of area.
//		Vapour pressure

		IUnit unit;
		String description;

		Variable(String unit, String description) {
			this.unit = Unit.create(unit);
			this.description = description;
		}

	}

	public AgERADatacube() {
		super(AgERAUrnTranslationService.class, AgERAAvailabilityService.class, AgERAIngestionService.class,
				AgERACachingService.class, AgERAEncodingService.class, AgERAMaintenanceService.class);
	}

}
