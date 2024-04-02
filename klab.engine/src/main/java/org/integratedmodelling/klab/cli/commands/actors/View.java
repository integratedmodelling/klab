package org.integratedmodelling.klab.cli.commands.actors;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class View implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		boolean json = call.getParameters().get("json", false);

		for (Object id : call.getParameters().get("arguments", List.class)) {
			IBehavior behavior = Actors.INSTANCE.getBehavior(id.toString());
			if (behavior == null) {
				throw new KlabResourceNotFoundException(id.toString());
			}

//			org.integratedmodelling.klab.rest.Layout view = Actors.INSTANCE.getView(behavior, session, null, null);
//
//			ret += (ret.isEmpty() ? "" : "\n\n") + id + ":\n\n"
//					+ (json ? JsonUtils.printAsJson(view) : Actors.INSTANCE.dumpView(view));
		}

		return ret;
	}

}
