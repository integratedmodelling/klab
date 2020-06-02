package org.integratedmodelling.klab.clitool.console.commands.visualization;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.runtime.Session;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		for (IObservation o : ((Session) session).getRootContexts()) {
			ret += (ret.isEmpty() ? "" : "\n") + "   " + o.getId() + ": " + o;
		}
		return ret;
	}

}
