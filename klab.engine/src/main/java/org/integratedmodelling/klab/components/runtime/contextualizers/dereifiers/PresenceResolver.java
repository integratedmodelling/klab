package org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype.Type;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.scale.Scale;

/**
 * Resolver that evaluates an expression computing a data artifact. If the
 * expression returns a data artifact, that substitutes the previous artifact.
 * 
 * @author Ferd
 *
 */
public class PresenceResolver implements IResolver<IDataArtifact>, IExpression {

	static final public String FUNCTION_ID = "klab.runtime.dereifiers.presence";

	IArtifact artifact = null;

	// don't remove - only used as expression
	public PresenceResolver() {
	}

	public PresenceResolver(IParameters parameters, IComputationContext context) {
		this.artifact = context.getArtifact(parameters.get("artifact", String.class));
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
		ret.getParameters().put("artifact", availableType.getLocalName());
		return ret;
	}

	@Override
	public Object eval(IParameters parameters, IComputationContext context) throws KlabException {
		return new PresenceResolver(parameters, context);
	}

	@Override
	public IDataArtifact resolve(IDataArtifact ret, IComputationContext context) throws KlabException {

		ISpace space = ((Scale) ret.getGeometry()).getSpace();
		if (!(space instanceof Space) || !((Space) space).getGrid().isPresent()) {
			// TODO only return an appropriate state for existence of artifact in context if
			// artifact is non-empty and either space is there and intersecting, or space isn't there at all.
			throw new KlabUnsupportedFeatureException("cannot yet compute indirect presence over a non-grid extent");
		}

		Rasterizer<Boolean> rasterizer = new Rasterizer<>(((Space) space).getGrid().get());

		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	
}
