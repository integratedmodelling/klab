package org.integratedmodelling.klab.cli.commands.visualization;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.utils.graph.Graphs;

public class ShowDependencies implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {
		Graphs.showDependencies();
		return null;
	}

}
