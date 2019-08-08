package org.integratedmodelling.klab.components.testing.assertions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.components.time.extents.Time;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.utils.Parameters;

public class AssertNodata implements Assertion, IExpression {

	List<String> details = new ArrayList<>();

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new AssertNodata();
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
					StateSummary summary = Observations.INSTANCE.getStateSummary((IState) artifact,
							Time.INITIALIZATION);
					if (parameters.contains("max.percent")) {
						if (summary.getNodataPercentage() > parameters.get("max.percent", Double.class)) {
							errors++;
							details.add("nodata percentage is " + summary.getNodataPercentage()
									+ ", higher than maximum allowed " + parameters.get("max.percent", Double.class));
						}
					}
					if (parameters.contains("min.percent")) {
						if (summary.getNodataPercentage() < parameters.get("min.percent", Double.class)) {
							errors++;
							details.add("nodata percentage is " + summary.getNodataPercentage()
									+ ", lower than minimum allowed " + parameters.get("min.percent", Double.class));
						}
					}
					if (parameters.contains("percent")) {
						if (summary.getNodataPercentage() != parameters.get("percent", Double.class)) {
							errors++;
							details.add("nodata percentage is " + summary.getNodataPercentage()
									+ ", different from expected " + parameters.get("percent", Double.class));
						}

					}
				} else {
					throw new IllegalArgumentException(
							"target '" + target + "' is not a state: cannot compute nodata statistics");
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
