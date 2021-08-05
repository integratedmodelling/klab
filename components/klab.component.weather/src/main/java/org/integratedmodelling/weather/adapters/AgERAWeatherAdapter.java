package org.integratedmodelling.weather.adapters;

import org.integratedmodelling.adapter.datacube.GenericDatacubeAdapter;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.weather.adapters.agera.AgERADatacube;

@UrnAdapter(type = "agera", version = Version.CURRENT)
public class AgERAWeatherAdapter extends GenericDatacubeAdapter {

	protected AgERAWeatherAdapter() {
		super("agera", new AgERADatacube());
	}

	
	
}
