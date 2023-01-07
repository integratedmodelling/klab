package org.integratedmodelling.stats.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.klab.exceptions.KlabConfigurationException;
import org.integratedmodelling.stats.StatsComponent;

public class ActivateLocalReporting implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		boolean doNotPersist = call.getParameters().get("temporary", false);
		boolean reset = call.getParameters().get("reset", false);
		boolean isPrivate = call.getParameters().get("private", false);

		if (Klab.INSTANCE.getStatisticsLocalHandler() != null) {
			return "Statistic reporting is already active";
		}

		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null) {
			StatsComponent stats = stc.getImplementation(StatsComponent.class);
			if (stats != null) {
				if (stats.isOnline()) {

					if (reset) {
						stats.clearDatabase();
					}

					stats.activateLocalReporting(session);

					if (!doNotPersist) {
						Configuration.INSTANCE.getProperties()
								.setProperty(IConfigurationService.LOCAL_STATS_ACTIVE_PROPERTY, "true");
						Configuration.INSTANCE.getProperties().setProperty(
								IConfigurationService.LOCAL_STATS_PRIVATE_PROPERTY, isPrivate ? "true" : "false");
						Configuration.INSTANCE.save();
					}

					return "Local statistic reporting activated " + (doNotPersist ? "temporarily" : "persistently");

				} else {
					throw new KlabConfigurationException(
							"Statistics component is installed but the PostgreSQL database is not available or not configured");
				}

			} else {
				throw new KlabConfigurationException("Statistics component is not installed or not activated");
			}
		}

		return "No reports available: statistics not initialized";

	}

}
