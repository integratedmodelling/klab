package org.integratedmodelling.klab.components.testing.assertions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

public class AssertRange implements Assertion, IExpression {

	List<String> details = new ArrayList<>();

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new AssertRange();
	}

	@Override
	public boolean evaluate(List<String> targets, Parameters<String> parameters, IContextualizationScope context)
			throws KlabValidationException {
		int errors = 0;
		for (String target : targets) {
			if (context.getArtifact(target) == null) {
				errors++;
				details.add("artifact '" + target + "' not found");
			} else {
				IArtifact artifact = context.getArtifact(target);
				if (artifact instanceof IState) {
					StateSummary summary = Observations.INSTANCE.getStateSummary((IState) artifact, context.getScale());
					if (parameters.contains("within")) {
						Range range = parameters.get("range", Range.class);
						if (!(range.isWithin(summary.getRange().get(0)) && range.isWithin(summary.getRange().get(1)))) {
							errors++;
							details.add("value range is outside the expected range " + range);
						}
					}
					if (parameters.contains("outside")) {
						Range range = parameters.get("outside", Range.class);
						if (range.isWithin(summary.getRange().get(0)) || range.isWithin(summary.getRange().get(1))) {
							errors++;
							details.add("value range is inside the exclusion range " + range);
						}
					}
				}
			}
		}
		return errors == 0;
	}

	@Override
	public List<String> getDetails() {
		return details;
	}

}
