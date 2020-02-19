package org.integratedmodelling.weather;

import java.util.Collection;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.weather.data.Weather;
import org.integratedmodelling.weather.data.WeatherFactory;
import org.integratedmodelling.weather.data.WeatherStation;

@Component(id = "org.integratedmodelling.weather", version = Version.CURRENT)
public class WeatherComponent {

	public static final String ID = "org.integratedmodelling.weather";

	@Initialize
	public boolean initialize() {
		// check for setup done
		return true;
	}

	@Setup(asynchronous = true)
	public boolean setup() {
		return false;
	}

	public static Weather getWeather(ISpace space, ITime time, String source, String... vars) {
		Collection<WeatherStation> wss = WeatherFactory.INSTANCE.within(space.getShape().getBoundingExtent(), source, vars);
		return new Weather(wss, time.getStart().getMilliseconds(), time.getEnd().getMilliseconds(),
				time.getStep().getMilliseconds(), 10, vars, 75, true);
	}

}
