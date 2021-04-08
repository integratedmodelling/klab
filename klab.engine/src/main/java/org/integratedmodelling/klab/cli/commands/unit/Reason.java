package org.integratedmodelling.klab.cli.commands.unit;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.StringUtils;

public class Reason implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
		IObservable observable = Observables.INSTANCE.declare(declaration);
		if (observable == null) {
			throw new KlabValidationException("Observable is invalid");
		}

		return describe(observable);
	}

	private String describe(IObservable observable) {
		String ret = "";
		if (Units.INSTANCE.needsUnits(observable)) {
			ret += (ret.isEmpty() ? "" : "\n") + "Needs units";
		}
		return ret;
	}

}
