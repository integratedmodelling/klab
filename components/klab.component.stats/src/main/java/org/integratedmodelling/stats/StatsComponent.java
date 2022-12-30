package org.integratedmodelling.stats;

import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.stats.database.StatsDatabase;

import org.integratedmodelling.klab.Logging;

@Component(id = StatsComponent.ID, version = Version.CURRENT)
public class StatsComponent {

	public static final String ID = "org.integratedmodelling.statistics";

	StatsDatabase database;

	@Initialize
	public boolean initialize() {
		database = new StatsDatabase();
		return database.isOnline();
	}

	public void submit(ObservationResultStatistics obs, String user, String groups) {
		if (database.isOnline()) {
			database.add(new ObservationResultStatistics[] { obs }, user, groups);
		} else {
			Logging.INSTANCE.error("Stats DB offline: lost " + obs);
		}
	}

}
