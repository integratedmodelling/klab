package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;

public class Contextualize implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		List<IConcept> concepts = new ArrayList<>();

		List<String> declarations = new ArrayList<>();
		String declaration = "";
		for (Object p : (List<?>) call.getParameters().get("arguments")) {
			if (p.toString().equals("to")) {
				declarations.add(declaration.trim());
				declaration = "";
			} else {
				concepts.add(Concepts.c(p.toString()));
				declaration += (declaration.isEmpty() ? "" : " ") + p.toString();
			}
		}

		if (!declarations.isEmpty()) {
			if (!declaration.isEmpty()) {
				declarations.add(declaration);
			}
			concepts.clear();
			for (String d : declarations) {
				concepts.add(Observables.INSTANCE.declare(d).getType());
			}
		}

		if (concepts.size() != 2) {
			throw new IllegalArgumentException("two arguments separated by 'to' expected");
		}

		IObservable observable = Observables.INSTANCE.contextualizeTo(Observable.promote(concepts.get(0)),
				concepts.get(1), session.getMonitor());

		return observable + "\n" + ShowInfo.describe(observable.getType());
	}

}
