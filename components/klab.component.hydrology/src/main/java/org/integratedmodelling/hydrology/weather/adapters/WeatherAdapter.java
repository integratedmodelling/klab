package org.integratedmodelling.hydrology.weather.adapters;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IResourceEncoder;
import org.integratedmodelling.klab.api.data.adapters.IResourceImporter;
import org.integratedmodelling.klab.api.data.adapters.IResourcePublisher;
import org.integratedmodelling.klab.api.data.adapters.IResourceValidator;
import org.integratedmodelling.klab.kim.Prototype;

public class WeatherAdapter implements IResourceAdapter {

	@Override
	public String getName() {
		return "weather";
	}

	@Override
	public IResourceValidator getValidator() {
		return new WeatherValidator();
	}

	@Override
	public IResourcePublisher getPublisher() {
		return new WeatherPublisher();
	}

	@Override
	public IResourceEncoder getEncoder() {
		return new WeatherEncoder();
	}

	@Override
	public IResourceImporter getImporter() {
		return new WeatherImporter();
	}

	@Override
	public IPrototype getResourceConfiguration() {
		return new Prototype(Dataflows.INSTANCE
				.declare(getClass().getClassLoader()
						.getResource("components/org.integratedmodelling.weather/adapter/weather.kdl"))
				.getActuators().iterator().next(), null);
	}

}
