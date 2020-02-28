package org.integratedmodelling.weather;

import java.util.Collection;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Maintain;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.weather.data.Weather;
import org.integratedmodelling.weather.data.WeatherEvents;
import org.integratedmodelling.weather.data.WeatherFactory;
import org.integratedmodelling.weather.data.WeatherStation;

@Component(id = "org.integratedmodelling.weather", version = Version.CURRENT)
public class WeatherComponent {

	public static final String ID = "org.integratedmodelling.weather";

	@Initialize
	public boolean initialize() {
		Logging.INSTANCE.info("Initializing storage for weather caches");
		WeatherFactory.checkStorage();
		return true;
	}

	@Setup(asynchronous = true)
	public boolean setupEvents() {
		WeatherEvents.INSTANCE.setup();
		return true;
	}

	@Setup(asynchronous = true)
	public boolean setupCRUStations() {
		WeatherFactory.INSTANCE.setupCRUStations();
		return true;
	}

	@Setup(asynchronous = true)
	public boolean setupGHNCDStations() {
		WeatherFactory.INSTANCE.setupGHCNDStations();
		return true;
	}

	/**
	 * Default maintenance of GHCND stations is every 3 days. Should also eventually
	 * schedule a storm detection step.
	 */
	@Maintain(intervalMinutes = 60 * 24 * 3)
	public void updateStations() {
//		WeatherFactory.INSTANCE.setupGHCNDStations();
	}

	public static String[] normalizeVariableNames(String[] vars) {

		String[] ret = new String[vars.length];

		int i = 0;
		for (String var : vars) {
			switch (var.toLowerCase()) {
			case "prec":
			case "precipitation":
				ret[i++] = Weather.PRECIPITATION_MM;
				break;
			case "snowfall":
			case "snow":
				ret[i++] = Weather.SNOWFALL_MM;
				break;
			case "snowdepth":
			case "snow_depth":
				ret[i++] = Weather.SNOWDEPTH_MM;
				break;
			case "maxt":
			case "max_t":
			case "tmax":
			case "t_max":
				ret[i++] = Weather.MAX_TEMPERATURE_C;
				break;
			case "mint":
			case "min_t":
			case "tmin":
			case "t_min":
				ret[i++] = Weather.MIN_TEMPERATURE_C;
				break;
			case "cloudcover":
			case "clouds":
				ret[i++] = Weather.CLOUD_COVER_PERCENTAGE;
				break;
			case "trange":
			case "t_range":
			case "t_minmax":
			case "t-range":
				ret[i++] = Weather.DIURNAL_TEMPERATURE_RANGE_C;
				break;
			case "frostdaysmonth":
			case "frostdays":
				ret[i++] = Weather.FROST_DAYS_IN_MONTH;
				break;
			case "pet":
			case "evapotranspiration":
			case "potential_evapotranspiration":
				ret[i++] = Weather.POTENTIAL_EVAPOTRANSPIRATION_MM;
				break;
			case "relhumidity":
			case "humidity":
			case "relative_humidity":
				ret[i++] = Weather.RELATIVE_HUMIDITY_PERCENT;
				break;
			case "sunshine_duration":
			case "sunshine":
				ret[i++] = Weather.SUNSHINE_DURATION_TOTAL_MINUTES;
				break;
			case "wetdays":
			case "wet_days":
				ret[i++] = Weather.WET_DAYS_IN_PERIOD;
				break;
			default:
				ret[i++] = var.toUpperCase();
			}
		}

		return ret;
	}

	public static Weather getWeather(ISpace space, ITime time, String source, String... vars) {
		Collection<WeatherStation> wss = WeatherFactory.INSTANCE.within(space.getShape(), source, vars);
		return new Weather(wss, time, 10, vars, 75, true);
	}

}
