package org.integratedmodelling.klab.clitool.console.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class GetProperty implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String ret = "";
		if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
			for (int i = 0; i < ((List<?>) call.getParameters().get("arguments")).size(); i++) {
				String property = ((List<?>) call.getParameters().get("arguments")).get(i).toString();
				ret += "\n" + property + ": " + Configuration.INSTANCE.getProperty(property, "UNKNOWN");
			}
		} else {
			for (Object property : Configuration.INSTANCE.getProperties().keySet()) {
				ret += "\n" + property + ": " + Configuration.INSTANCE.getProperty(property.toString(), "UNKNOWN");
			}
		}
		return ret;
	}

}
