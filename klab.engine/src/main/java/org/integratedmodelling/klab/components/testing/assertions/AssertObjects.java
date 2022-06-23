package org.integratedmodelling.klab.components.testing.assertions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class AssertObjects implements Assertion, IExpression {

	List<String> details = new ArrayList<>();

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new AssertObjects();
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
				if (artifact instanceof IObjectArtifact) {
					if (parameters.contains("count")) {
						if (((IObjectArtifact) artifact).groupSize() != parameters.get("count", Integer.class)) {
							errors++;
							details.add("artifact '" + target + "' has " + ((IObjectArtifact) artifact).groupSize()
									+ "observations, different from the expected "
									+ parameters.get("count", Integer.class));
						}
					}
					if (parameters.contains("min")) {
						if (((IObjectArtifact) artifact).groupSize() < parameters.get("min", Integer.class)) {
							errors++;
							details.add("artifact '" + target + "' has " + ((IObjectArtifact) artifact).groupSize()
									+ "observations, less than the minimum " + parameters.get("min", Integer.class));
						}
					}
					if (parameters.contains("max")) {
						if (((IObjectArtifact) artifact).groupSize() > parameters.get("max", Integer.class)) {
							errors++;
							details.add("artifact '" + target + "' has " + ((IObjectArtifact) artifact).groupSize()
									+ "observations, more than the maximum " + parameters.get("max", Integer.class));
						}
					}
				} else {
					throw new IllegalArgumentException(
							"target '" + target + "' is not an object artifact: cannot compute object statistics");
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
