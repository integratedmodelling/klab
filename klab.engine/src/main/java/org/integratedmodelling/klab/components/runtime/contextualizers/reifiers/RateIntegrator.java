package org.integratedmodelling.klab.components.runtime.contextualizers.reifiers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class RateIntegrator extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.reifiers.integrate";

	// don't remove - only used as expression
	public RateIntegrator() {
	}

	public RateIntegrator(IParameters<String> parameters, IContextualizationScope context) {
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
		ret.getParameters().put("artifact", availableType.getName());
		return ret;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		return new RateIntegrator(Parameters.create(parameters), context);
	}

	@Override
	public Type getType() {
		return Type.PROCESS;
	}

	@Override
	public IProcess resolve(IProcess ret, IContextualizationScope scope) {
		// TODO Auto-generated method stub
		return null;
	}

}
