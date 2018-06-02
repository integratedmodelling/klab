package org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.exceptions.KlabException;

/**
 * Resolver that evaluates an expression computing a data artifact. If the
 * expression returns a data artifact, that substitutes the previous artifact.
 * 
 * @author Ferd
 *
 */
public class PresenceResolver implements IResolver<IDataArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.dereifiers.presence";

	// don't remove - only used as expression
	public PresenceResolver() {
	}

	public PresenceResolver(IParameters parameters, IComputationContext context) {
		// TODO Auto-generated constructor stub
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		return KimServiceCall.create(FUNCTION_ID);
	}

	@Override
	public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
		return new PresenceResolver(parameters, context);
	}

	@Override
	public IDataArtifact resolve(IDataArtifact ret, IComputationContext context) throws KlabException {

		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}

}
