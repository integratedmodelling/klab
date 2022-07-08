package org.integratedmodelling.klab.components.testing.assertions;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.testing.Assertion;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class AssertExists implements Assertion, IExpression {

	List<String> details = new ArrayList<>();

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new AssertExists();
	}

	@Override
	public boolean evaluate(List<String> targets, Parameters<String> parameters, IContextualizationScope context)
			throws KlabValidationException {
		int errors = 0;
		IArtifact.Type type = null;
		if (parameters.contains("type")) {
			type = IArtifact.Type.valueOf(parameters.get("type", String.class).toUpperCase());
		}
		for (String target : targets) {
			if (context.getArtifact(target) == null) {
				errors ++;
				details.add("artifact '" + target + "' not found");
			} else if (type != null) {
				if (context.getArtifact(target).getType() != type) {
					errors ++;
					details.add("artifact '" + target + "' is of type " + context.getArtifact(target).getType() + ", different from asserted " + type);
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
