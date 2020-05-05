package org.integratedmodelling.klab.clitool.console.commands.model;

import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.StringUtils;

/**
 * Should compute coverage for both a concept and a model. For now just a model.
 * 
 * @author ferdinando.villa
 *
 */
public class Coverage implements ICommand {

	/**
	 * TODO add context of resolution
	 */
	
	@Override
	public Object execute(IServiceCall call, ISession session) throws Exception {

		String ret = "";
		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

		if (!declaration.contains(":")) {

			// it's a model
			IKimObject mob = Resources.INSTANCE.getModelObject(declaration);
			if (mob instanceof IModel) {
				Scale scale = ((Model) mob).getCoverage(session.getMonitor());
				ret += scale.asGeometry().toString();
			}

		} else {

			// it's a concept
			// TODO compute union of coverage of all models resolving it.

			IConcept concept = null;
			if (declaration.startsWith("k:")) {
				concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
			}
			if (concept == null) {
				IObservable observable = Observables.INSTANCE.declare(declaration);
				concept = observable.getType();
			}

			IObservable observable = Observable.promote(concept);
			Set<Long> ids = Models.INSTANCE.getKbox().getCompatibleTypeIds(observable, null,
					observable.getDescription().getResolutionMode());

			for (long id : ids) {
				String idst = "[" + id + "]";
				ret += StringUtils.rightPad(idst, 8) + Models.INSTANCE.getKbox().getTypeDefinition(id) + "\n";
			}
		}
		return ret;
	}

}
