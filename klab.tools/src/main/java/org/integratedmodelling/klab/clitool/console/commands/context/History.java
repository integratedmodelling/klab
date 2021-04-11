package org.integratedmodelling.klab.clitool.console.commands.context;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.JsonUtils;

public class History implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {
		return JsonUtils.printAsJson(session.getState().getHistory());
	}

}
