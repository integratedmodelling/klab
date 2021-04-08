package org.integratedmodelling.klab.cli.commands.model;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.utils.StringUtils;

public class Compatible implements ICommand {

	/**
	 * TODO add context parameter
	 */
	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		String ctx = call.getParameters().get("context", String.class);

		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

		IConcept context = null;
		if (ctx != null) {
			context = Concepts.c(ctx);
		}

		IConcept concept = null;
		if (declaration.startsWith("k:")) {
			concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
		}
		if (concept == null) {
			IObservable observable = Observables.INSTANCE.declare(declaration);
			concept = observable.getType();
		}

		IObservable observable = Observable.promote(concept);
		Set<Long> ids = Models.INSTANCE.getKbox().getCompatibleTypeIds(observable, context,
				observable.getDescriptionType().getResolutionMode());

		for (long id : ids) {
			String idst = "[" + id + "]";
			ret += StringUtils.rightPad(idst, 8) + Models.INSTANCE.getKbox().getTypeDefinition(id) + "\n";
		}

		return ret;
	}

}
