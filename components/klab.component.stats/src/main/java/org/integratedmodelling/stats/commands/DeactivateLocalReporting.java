package org.integratedmodelling.stats.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;

public class DeactivateLocalReporting implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		boolean doNotPersist = call.getParameters().get("temporary", false);

		if (Klab.INSTANCE.getStatisticsLocalHandler() != null) {
			Klab.INSTANCE.setStatisticsLocalHandler(null);
			if (!doNotPersist) {
				Configuration.INSTANCE.getProperties().setProperty(IConfigurationService.LOCAL_STATS_ACTIVE_PROPERTY,
						"false");
				Configuration.INSTANCE.save();
			}

			return "Local statistic reporting deactivated " + (doNotPersist ? "temporarily" : "persistently");

		}
		return "Local statistic reporting is already inactive";

	}

}
