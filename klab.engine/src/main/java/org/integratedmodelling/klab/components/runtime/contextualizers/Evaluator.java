package org.integratedmodelling.klab.components.runtime.contextualizers;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.model.contextualization.IEvaluator;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;

public class Evaluator implements IEvaluator {

	private final static String FUNCTION_ID = "klab.runtime.evaluate";

	@Override
	public Type getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object evaluate(IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

	public static IServiceCall getServiceCall(IContextualizable resource) {
		String functionId = FUNCTION_ID;

		IServiceCall ret = KimServiceCall.create(functionId);
		if (resource.getExpression() != null) {
			ret.getParameters().put("code", resource.getExpression());
		} else if (resource.getLiteral() != null) {
			ret.getParameters().put("literal", resource.getLiteral());
		} else {
			throw new KlabUnimplementedException(
					"Cannot use anything but a literal value or an expression in an auxiliary variable specification");
		}
		if (resource.getCondition() != null) {
			ret.getParameters().put(resource.isNegated() ? "unlesscondition" : "ifcondition", resource.getCondition());
		}

		return ret;
	}

}
