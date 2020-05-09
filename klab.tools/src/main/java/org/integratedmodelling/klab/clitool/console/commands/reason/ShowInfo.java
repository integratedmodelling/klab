package org.integratedmodelling.klab.clitool.console.commands.reason;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Roles;
import org.integratedmodelling.klab.Traits;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IOntology;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Concept;
import org.integratedmodelling.klab.utils.StringUtil;
import org.integratedmodelling.klab.utils.StringUtils;

public class ShowInfo implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String ret = "";
		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

		if (!declaration.contains(":")) {
			INamespace namespace = Namespaces.INSTANCE.getNamespace(declaration);
			if (namespace == null) {
				throw new KlabValidationException(
						"expression '" + declaration + "' does not specify a concept or an ontology");
			}
			ret += describe(namespace.getOntology());

		} else {

			IObservable observable = null;
			if (!declaration.startsWith("k:")) {
				observable = Observables.INSTANCE.declare(declaration);
			}

			IConcept concept = observable == null ? Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2))
					: observable.getType();

			if (concept == null) {
				throw new KlabValidationException("expression '" + declaration + "' does not specify a concept");
			}

			for (IConcept c : concept.getOperands()) {
				ret += (ret.isEmpty() ? "" : (concept.is(Type.UNION) ? "\n  OR\n" : "\n  AND\n")) + Observables.INSTANCE.describe(c);
			}

			if (observable != null) {
				ret += "\nObservation type: " + observable.getDescription() + "\n";
			}

		}
		return ret;
	}

	private String describe(IOntology ontology) {

		String ret = "";
		ret += "Imports:\n" + printImports(ontology, 3, new HashSet<>());
		ret += "Concepts:\n";
		for (IConcept c : ontology.getConcepts()) {
			ret += "   " + c + " [" + c.getDefinition() + "]" + "\n";
		}
		return ret;
	}

	private String printImports(IOntology owlOntology, int i, Set<IOntology> done) {
		String ret = "";
		String spaces = StringUtil.spaces(i);
		for (IOntology o : owlOntology.getImports(false)) {
			boolean added = done.add(o);
			ret += spaces + o + "\n" + (added ? printImports(o, i + 3, done) : "");
		}
		return ret;
	}


}
