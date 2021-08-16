package org.integratedmodelling.adapter.copernicus;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.GetStatus;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Maintain;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

@Component(id = CopernicusComponent.ID, version = Version.CURRENT)
public class CopernicusComponent {

	public static final String ID = "org.integratedmodelling.copernicus";

	@Initialize
	public boolean initialize() {
		Logging.INSTANCE.info("Initializing storage for weather caches");
//		WeatherFactory.checkStorage();
		return true;
	}

	@Setup(asynchronous = true)
	public boolean setupEvents() {
		return true;
	}
	
	@GetStatus
	public void getStatus(IMetadata metadata) {
//		metadata.put("stations.count", "" + WeatherFactory.INSTANCE.getStationsCount());
//		metadata.put("events.count", "" + WeatherEvents.INSTANCE.getEventsCount());
	}

	/**
	 * Default maintenance of GHCND stations is every 3 days. Should also eventually
	 * schedule a storm detection step.
	 */
	@Maintain(intervalMinutes = 60 * 24 * 3)
	public void updateStations() {
	}

}
