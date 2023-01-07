package org.integratedmodelling.stats.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;

public class LocalReportingInfo implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		if (Klab.INSTANCE.getStatisticsLocalHandler() != null) {
			boolean persisted = Boolean.parseBoolean(Configuration.INSTANCE.getProperties()
					.getProperty(IConfigurationService.LOCAL_STATS_ACTIVE_PROPERTY, "false"));
			return "Statistic reporting is active (persisted: " + (persisted ? "YES" : "NO") + ")";
		}
		return "Statistic reporting is inactive";

	}

}
