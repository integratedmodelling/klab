package org.integratedmodelling.klab.cli.commands.model;

import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.owl.Concept;

public class Info implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		for (Object id : call.getParameters().get("arguments", List.class)) {
			IKimObject mo = Resources.INSTANCE.getModelObject(id.toString());
			if (mo instanceof Model) {

				Model model = (Model) mo;

				ret += "Model " + model;

				ret += "\n   " + "Stated geometry: " + model.getGeometry();
				ret += "\n   " + "Stated coverage: " + model.getCoverage(session.getMonitor());
				ret += "\n   " + "Scope: " + model.getScope();
				ret += "\n   " + (model.isSemantic() ? "SEMANTIC" : "NON-SEMANTIC");
				ret += "\n   " + (model.isAvailable() ? "AVAILABLE" : "NOT AVAILABLE");
				ret += "\n   " + (model.isDeprecated() ? "DEPRECATED" : "NOT DEPRECATED");
				ret += "\n   " + (model.isInstantiator() ? "INSTANTIATOR" : "RESOLVER");
				ret += "\n   " + (model.isErrors() ? "HAS ERRORS" : "NO ERRORS");
				if (model.isErrors()) {
					for (String error : model.getErrors()) {
						ret += "\n      " + error;
					}
				}
				ret += "\n   " + (model.isInactive() ? "INACTIVE" : "ACTIVE");
				ret += "\n   " + (model.isLearning() ? "LEARNING" : "NOT LEARNING");
				ret += "\n   " + (model.isResolved() ? "RESOLVED" : "UNRESOLVED");

				ret += "\nObservables:";
				for (IObservable obs : model.getObservables()) {
					ret += "\n" + describeObservable(obs, "   ");
				}

				ret += "\nDependencies:";
				for (IObservable obs : model.getDependencies()) {
					ret += "\n" + describeObservable(obs, "   ");
				}
				
				if (model.isAbstract()) {
					ret += "\nAbstract in:";
					for (IConcept c : model.getAbstractTraits()) {
						ret += "\n   " + c.getDefinition();
					}
				}

			} else {
				ret += "Object " + mo + " is not a model\n";
			}
		}

		return ret;
	}

	public static String describeObservable(IObservable obs, String prefix) {
		String ret = prefix + obs;
		ret += "\n" + prefix + "  Typeset: " + ((Concept)obs.getType()).getTypeSet();
		ret += "\n" + prefix + "  Observation type: " + obs.getDescriptionType();
		// units (fluid, needed, not, etc)
		// 
		return ret;
	}

}
