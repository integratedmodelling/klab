package org.integratedmodelling.klab.components.testing.assertions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class AssertExists implements Assertion, IExpression {

	List<String> details = new ArrayList<>();

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new AssertExists();
	}

	@Override
	public boolean evaluate(List<String> targets, Parameters<String> parameters, IComputationContext context)
			throws KlabValidationException {
		int errors = 0;
		for (String target : targets) {
			if (context.getArtifact(target) == null) {
				errors ++;
				details.add("artifact '" + target + "' not found");
			}
		}
		return errors == 0;
	}

	@Override
	public List<String> getDetails() {
		return details;
	}

}
