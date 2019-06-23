package org.integratedmodelling.klab.components.runtime.contextualizers.dereifiers;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IDataArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.indexing.DistanceCalculator;
import org.integratedmodelling.klab.components.geospace.indexing.SpatialIndex;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
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

	String artifactId;
	IUnit unit;

	// don't remove - only used as expression
	public DistanceResolver() {
	}

	public DistanceResolver(IParameters<String> parameters, IComputationContext context) {
		this.artifactId = parameters.get("artifact", String.class);
		this.unit = Units.INSTANCE.getUnit(parameters.get("unit", String.class));
	}

	public static IServiceCall getServiceCall(IObservable availableType, IObservable desiredObservation) {
		IServiceCall ret = KimServiceCall.create(FUNCTION_ID);
		ret.getParameters().put("artifact", availableType.getName());
		ret.getParameters().put("unit", desiredObservation.getUnit().toString());
		return ret;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		if (context.getScale().getSpace() == null) {
			throw new IllegalArgumentException("cannot compute distances in a non-spatial context");
		}
		return new DistanceResolver(parameters, context);
	}

	@Override
	public IDataArtifact resolve(IDataArtifact ret, IComputationContext context) throws KlabException {

		if (context.getArtifact(this.artifactId) == null || context.getArtifact(this.artifactId).isEmpty()) {
			return ret;
		}
		IArtifact artifact = context.getArtifact(this.artifactId);

		context.getMonitor().info("indexing " + artifact.groupSize() + " spatial objects...");
		
		// TODO this will need to be switched to a cost surface analysis when available.
		DistanceCalculator index = new DistanceCalculator(context.getScale().getSpace(), artifact.groupSize());
		for (IArtifact a : artifact) {
			if (a instanceof IDirectObservation && ((IDirectObservation) a).getSpace() != null) {
				IDirectObservation obs = (IDirectObservation) a;
				index.add(obs);
			}
		}

		context.getMonitor().info("computing distances over a " + ret.getGeometry().size() + "-pixel grid...");

		for (ILocator locator : (IScale) ret.getGeometry()) {
			if (context.getMonitor().isInterrupted()) {
				break;
			}
			ret.set(locator, index.distanceToNearestObjectFrom(locator, this.unit));
		}

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
