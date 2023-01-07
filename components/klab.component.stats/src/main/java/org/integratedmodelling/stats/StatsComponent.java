package org.integratedmodelling.stats;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.rest.ObservationResultStatistics;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.stats.database.StatsDatabase;
import org.integratedmodelling.stats.reporting.StatsReport;
import org.integratedmodelling.stats.reporting.StatsReport.Frequency;
import org.integratedmodelling.stats.reporting.StatsReport.Target;

@Component(id = StatsComponent.ID, version = Version.CURRENT)
public class StatsComponent {

	public static final String ID = "org.integratedmodelling.statistics";

	StatsDatabase database;

	@Initialize
	public boolean initialize() {
		database = new StatsDatabase();
		if (database.isOnline()) {
			final boolean isActive = Boolean.parseBoolean(Configuration.INSTANCE.getProperties()
					.getProperty(IConfigurationService.LOCAL_STATS_ACTIVE_PROPERTY, "false"));
			if (isActive) {
				Klab.INSTANCE.addSessionInitializer((session) -> activateLocalReporting(session));
			}
		}
		return database.isOnline();
	}

	public void submit(ObservationResultStatistics obs, String user, String groups) {
		if (database.isOnline()) {
			database.add(new ObservationResultStatistics[] { obs }, user, groups);
		} else {
			Logging.INSTANCE.error("Stats DB offline: lost " + obs);
		}
	}

	public StatsDatabase getDatabase() {
		return database;
	}

	/**
	 * TODO list option keys and types
	 * 
	 * @param target
	 * @param options
	 * @return
	 */
	public StatsReport createReport(Object... options) {

		if (!database.isOnline()) {
			return null;
		}

		StatsReport ret = new StatsReport();

		if (options != null) {
			for (int i = 0; i < options.length; i++) {
				if ("start".equals(options[i])) {
					ret.setReportingStart((Long) options[++i]);
				} else if ("end".equals(options[i])) {
					ret.setReportingEnd((Long) options[++i]);
				} else if (options[i] instanceof Target) {
					ret.setTargetClassifier((Target) options[i]);
				} else if (options[i] instanceof Frequency) {
					ret.setAggregationInterval((Frequency) options[i], 1);
				} // TODO handle white/blacklists
			}
		}

		return ret;
	}

	public void updateReferenceDatabase(boolean clear) {

	}

	public void clearDatabase() {
		database.clearDatabase();
	}

	public boolean isOnline() {
		return database != null && database.isOnline();
	}

	public void activateLocalReporting(ISession session) {

		Klab.INSTANCE.setStatisticsLocalHandler((obs) -> {
			submit(obs, session.getUser().getUsername(),
					StringUtil.join(session.getUser().getGroups().stream().map((d) -> d.getId()).toList(), ","));
		});
	}

}
