package org.integratedmodelling.stats.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.extensions.Component;
import org.integratedmodelling.stats.StatsComponent;

public class UpdateRefDb implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		boolean clear = call.getParameters().get("html", false);
		Component stc = Extensions.INSTANCE.getComponent(StatsComponent.ID);
		if (stc != null) {
			StatsComponent stats = stc.getImplementation(StatsComponent.class);
			if (stats != null) {
				stats.updateReferenceDatabase(clear);
				// TODO report?
			}
		}

		return "No reports available: statistics not initialized";
	}

}
