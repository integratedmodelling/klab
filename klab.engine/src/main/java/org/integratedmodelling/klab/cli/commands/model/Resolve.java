package org.integratedmodelling.klab.cli.commands.model;

import java.util.Collections;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.services.IModelService.IRankedModel;
import org.integratedmodelling.klab.components.runtime.observations.Subject;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.StringUtils;

public class Resolve implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		Subject context = null;
		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();
		INamespace ns = null;
		if (call.getParameters().get("context", false)) {
			context = (Subject)session.getState().getCurrentContext();
			ns = context.getNamespace();
		}
		String namespace = call.getParameters().get("namespace", String.class);
		if (namespace != null) {
			ns = Namespaces.INSTANCE.getNamespace(namespace);
		}
		IObservable observable = null;
		IConcept concept = null;
		if (declaration.startsWith("k:")) {
			concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
		}
		if (concept == null) {
			observable = Observables.INSTANCE.declare(declaration);
		}
		String ret = "Resolving " + observable + " in " + (context == null ? "no context" : context) + ", namespace "
				+ (ns == null ? "unspecified" : ns.getName()) + "\n\n";
		
		ResolutionScope scope = context == null ? ResolutionScope.create(session.getMonitor(), null)
				: ResolutionScope.create(context, session.getMonitor(), ns, Collections.EMPTY_LIST);

		int n = 1;

		for (IRankedModel model : Models.INSTANCE.resolve(observable, scope)) {
			ret += (n++) + ". " + model + "\n";
			for (String key : model.getRanks().keySet()) {
				ret += "   " + key + ": " + model.getRanks().get(key) + "\n";
			}
		}

		return ret;
	}

}
