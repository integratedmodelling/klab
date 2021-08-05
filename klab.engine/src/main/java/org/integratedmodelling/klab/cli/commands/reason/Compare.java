package org.integratedmodelling.klab.cli.commands.reason;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class Compare implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		IObservable context = null;
		Parameters<String> parameters = call.getParameters();
		if (parameters.get("context", false)) {
			context = session.getState().getCurrentContext() == null ? null
					: session.getState().getCurrentContext().getObservable();
		}

		String ret = "";

		List<String> declarations = new ArrayList<>();
		String declaration = "";
		for (Object p : (List<?>) call.getParameters().get("arguments")) {
			if (p.toString().equals("to")) {
				declarations.add(declaration.trim());
				declaration = "";
			} else {
				declaration += (declaration.isEmpty() ? "" : " ") + p.toString();
			}
		}

		List<IConcept> concepts = new ArrayList<>();
		if (!declarations.isEmpty()) {
			if (!declaration.isEmpty()) {
				declarations.add(declaration);
			}
			for (String d : declarations) {
				concepts.add(Observables.INSTANCE.declare(d).getType());
			}
		}

		for (int i = 0; i < concepts.size(); i++) {
			for (int j = 0; j < concepts.size(); j++) {
				if (j != i) {
					ret += (ret.isEmpty() ? "" : "\n")
							+ compare(concepts.get(i), concepts.get(j), context == null ? null : context.getType());
				}
			}
		}

		return ret;
	}

	private String compare(IConcept c1, IConcept c2, IConcept context) {

		String ret = "[" + c1.getDefinition() + "] vs. [" + c2.getDefinition() + "]:\n";

//		if (c1.is(Type.OBSERVABLE) && c2.is(Type.OBSERVABLE)) {
//			ret += "   Observable compatibility: "
//					+ (Observables.INSTANCE.isCompatible(c1, c2) ? "compatible" : "not compatible") + "\n";
//		} else {
//			ret += "   Observable compatibility: not observables\n";
//		}

		int c1c2distance = c1.getSemanticDistance(c2, context);
		int c2c1distance = c2.getSemanticDistance(c1, context);

		ret += "   " + c1.getDefinition() + (c1c2distance < 0 ? " DOES NOT RESOLVE " : "    RESOLVES    ")
				+ c2.getDefinition() + ": distance is " + c1c2distance + "\n";
		ret += "   " + c2.getDefinition() + (c2c1distance < 0 ? " DOES NOT RESOLVE " : "    RESOLVES    ")
				+ c1.getDefinition() + ": distance is " + c2c1distance + "\n";

		IConcept common = Concepts.INSTANCE.getLeastGeneralCommonConcept(c1, c2);

		ret += "   Least general common parent: " + (common == null ? "none" : common.toString()) + "\n";

		if (common != null) {
			ret += "      " + c1 + ": asserted distance from common = "
					+ Concepts.INSTANCE.getAssertedDistance(c1, common) + "\n";
			ret += "      " + c2 + ": asserted distance from common = "
					+ Concepts.INSTANCE.getAssertedDistance(c2, common) + "\n";
		}

		int specificity = Concepts.INSTANCE.compareSpecificity(c1, c2, c1.is(Type.TRAIT) && c2.is(Type.TRAIT));

		ret += "   Specificity: " + c1
				+ (specificity == Integer.MIN_VALUE ? " has nothing in common with "
						: (specificity > 0 ? " is more specific than "
								: (specificity == 0 ? " is at the same level of " : " is less specific than ")))
				+ c2 + "\n";

		return ret;
	}

}
