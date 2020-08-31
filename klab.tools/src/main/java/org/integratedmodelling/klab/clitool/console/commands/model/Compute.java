package org.integratedmodelling.klab.clitool.console.commands.model;

import java.util.List;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.resolution.ObservationStrategy;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.StringUtils;

public class Compute implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) throws KlabValidationException {

		String declaration = StringUtils.join((List<?>) call.getParameters().get("arguments"), ' ').trim();

		String ctx = call.getParameters().get("context", String.class);

		IConcept context = null;
		if (ctx != null) {
			context = Concepts.c(ctx);
		}

		IObservable observable = null;
		IConcept concept = null;
		if (declaration.startsWith("k:")) {
			concept = Reasoner.INSTANCE.getOntology().getConcept(declaration.substring(2));
		}
		if (concept == null) {
			observable = Observables.INSTANCE.declare(declaration);
		}

		ResolutionScope scope = ResolutionScope.create(session.getMonitor());
		if (context != null) {
			scope = scope.getContextualizedScope(Observable.promote(context));
		}
		return dumpStrategy(ObservationStrategy.computeStrategies(observable, scope,
				observable.getDescriptionType().getResolutionMode()), scope, 0, "");
	}

	
	private String dumpStrategy(List<ObservationStrategy> strategies, IResolutionScope scope, int level, String ret) {

		String spacer = StringUtils.spaces(level * 3);
		int n = 1;
		
		for (ObservationStrategy strategy : strategies) {
//			if (strategies.size() > 1) {
				ret += spacer + StringUtils.leftPad(""+(n++), (level+1)*3) + ". " + strategy.getStrategy() + " ";
				for (IObservable observable : strategy.getObservables()) {
					ret += (strategy.isResolve() ? "Resolve " : "Observe ") + observable + "\n";
					if (!strategy.isTrivial()) {
						ret = dumpStrategy(ObservationStrategy.computeStrategies(observable, scope,
								observable.getDescriptionType().getResolutionMode()), scope, level + 1, ret);
					}
				}
//			}
			for (IContextualizable computation : strategy.getComputation()) {
				ret += spacer + "      Compute " + computation.getServiceCall() + "\n";
			}
		}
		return ret;
	}

}
