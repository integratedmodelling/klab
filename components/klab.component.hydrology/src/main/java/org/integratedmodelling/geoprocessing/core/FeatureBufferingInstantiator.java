package org.integratedmodelling.geoprocessing.core;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;

public class FeatureBufferingInstantiator implements IInstantiator, IExpression {

	private double distance;
	private String artifact;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("#s2");
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		FeatureBufferingInstantiator ret = new FeatureBufferingInstantiator();
		ret.distance = parameters.get("radius", Double.class);
		ret.artifact = parameters.get("artifact", String.class);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		IArtifact source = null;
		boolean transform = false;
		if (artifact == null) {
			// transform target's extents
			source = context.getTargetArtifact();
			transform = true;
		} else {
			source = context.getArtifact(artifact);
		}

		if (source == null && !(source instanceof IObjectArtifact)) {
			throw new IllegalArgumentException(
					"buffer instantiator: source artifact does not exist or is not an object artifact");
		}

		double bdistance = context.getScale().getSpace().getEnvelope().convertDistance(this.distance);

		List<IObjectArtifact> ret = new ArrayList<>();
		context.getMonitor().info("starting spatial buffer operation");
		
		int tot = 0;
		int spc = 0;
		for (IArtifact obj : source) {
			ISpace ospace = ((IObservation) obj).getSpace();
			if (ospace != null) {
				IShape newShape = ospace.getShape().buffer(bdistance);
				IScale newScale = Scale.substituteExtent(((IObservation) obj).getScale(), newShape);
				if (transform) {
					((ObservedArtifact) obj).setGeometry(newScale);
				} else {
					ret.add(context.newObservation(semantics, ((IDirectObservation) obj).getName() + "_buffered",
							newScale));
				}
				spc++;
			}
			tot++;
		}

		context.getMonitor().info("buffer operation " + (transform ? "modified" : "created") + " " + spc + " objects"
				+ (tot > spc ? (" (" + (tot - spc) + " skipped)") : ""));

		return ret;
	}

}
