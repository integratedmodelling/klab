package org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
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
public class DistanceResolver implements IResolver<IDataArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.dereifiers.distance";

	// don't remove - only used as expression
	public DistanceResolver() {
	}

	public DistanceResolver(IParameters parameters, IComputationContext context) {
		// TODO Auto-generated constructor stub
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
	    ret.getParameters().put("artifact", availableType.getLocalName());
		ret.getParameters().put("unit", desiredObservation.getUnit().toString());
		return ret;
	}

	@Override
	public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
		return new DistanceResolver(parameters, context);
	}

	@Override
	public IDataArtifact resolve(IDataArtifact ret, IComputationContext context) throws KlabException {

		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}
	
	@Override
	public IArtifact.Type getType() {
		return Type.NUMBER;
	}

}
