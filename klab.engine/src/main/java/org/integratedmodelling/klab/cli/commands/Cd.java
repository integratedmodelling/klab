package org.integratedmodelling.klab.cli.commands;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Cd implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		return "k.LAB engine v" + Version.CURRENT + " build " + Version.VERSION_BUILD + " (" + Version.VERSION_BRANCH
				+ "#" + Version.VERSION_COMMIT + " on " + Version.VERSION_DATE;

	}

}
