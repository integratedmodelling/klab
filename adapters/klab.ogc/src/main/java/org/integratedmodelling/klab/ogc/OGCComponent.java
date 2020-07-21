package org.integratedmodelling.klab.ogc;

import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.GetStatus;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.ogc.integration.Geoserver;
import org.integratedmodelling.klab.ogc.integration.Postgis;

@Component(id = OGCComponent.ID, version = Version.CURRENT + "-b1")
public class OGCComponent {

	public static final String ID = "org.integratedmodelling.ogc";
	
	@Setup(asynchronous = false)
	void clearStorage() {

		Logging.INSTANCE.info("Clearing all OGC-related storage...");
		int n = 0;

		if (Geoserver.isEnabled()) {
			Geoserver gs = Geoserver.create();
			if (gs.isOnline()) {
				Logging.INSTANCE.info("Clearing contents of installed Geoserver instance");
				n++;
				gs.clear();
			}
		}
		if (Postgis.isEnabled()) {
			Postgis pg = Postgis.create();
			if (pg.isOnline()) {
				Logging.INSTANCE.info("Clearing contents of installed Postgres instance");
				n++;
				pg.clear();
			}
		}

		Logging.INSTANCE.info("OGC setup: " + n + " operations done");
	}

	@GetStatus
	public void getStatus(IMetadata metadata) {

		boolean pgEnabled = false;
		boolean gsEnabled = false;
		boolean pgOnline = false;
		boolean gsOnline = false;
		if (Postgis.isEnabled()) {
			pgEnabled = true;
			Postgis pg = Postgis.create();
			pgOnline = pg.isOnline();
		}
		if (Geoserver.isEnabled()) {
			gsEnabled = true;
			Geoserver gs = Geoserver.create();
			gsOnline = gs.isOnline();
		}

		metadata.put("geoserver.enabled", gsEnabled ? "true" : "false");
		metadata.put("geoserver.online", gsOnline ? "true" : "false");
		metadata.put("postgis.enabled", pgEnabled ? "true" : "false");
		metadata.put("postgis.online", pgOnline ? "true" : "false");
		
		/*
		 * TODO list GS and PG tables
		 */
	}
}
