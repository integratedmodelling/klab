package org.integratedmodelling.hydrology.weather;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Setup;

@Component(id="org.integratedmodelling.weather", version=Version.CURRENT)
public class WeatherComponent {

	@Initialize
	public boolean initialize() {
		// check for setup done
		return true;
	}
	
	@Setup(asynchronous = true)
	public boolean setup() {
		return false;
	}
	
}
