package org.integratedmodelling.klab.clitool.console.commands.kbox;

import java.util.ArrayList;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.rest.ModelReference;

public class List implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		boolean verbose = call.getParameters().get("verbose", false);
		java.util.List<ModelReference> params = null;
		if (call.getParameters().get("arguments", java.util.List.class).size() > 0) {
			params = new ArrayList<>();
			for (Object o : call.getParameters().get("arguments", java.util.List.class)) {
				params.add(Models.INSTANCE.getModelReference(o.toString()));
			}
			verbose = true;
		} 		
		for (ModelReference model : params == null ? Models.INSTANCE.listModels(true) : params) {
			ret += (ret.isEmpty() ? "" : "\n") + describe(model, verbose);
		}
		ret += "\nKnown definitions:\n";
		for (String definition : Models.INSTANCE.getKbox().getKnownDefinitions()) {
			ret += "   " + definition + "\n";
		}
		
		return ret;
	}

	private String describe(ModelReference urn, boolean verbose) {
		String ret = urn.getUrn();
		if (verbose) {
			ret += "\n  " + urn.getObservable();
			ret += "\n  " + urn.getShape();
			ret += "\n  " + urn.getObservationType();
		} 
		return ret;
	}

}
