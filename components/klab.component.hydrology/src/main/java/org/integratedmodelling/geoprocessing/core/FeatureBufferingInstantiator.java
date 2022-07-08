package org.integratedmodelling.geoprocessing.core;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.components.runtime.observations.ObservedArtifact;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

public class FeatureBufferingInstantiator extends AbstractContextualizer implements IInstantiator, IExpression {

	private double distance;
	private String artifact;
	private boolean subtract;
	private double simplify = 0;

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...params) throws KlabException {
	    Parameters<String> parameters = Parameters.create(params);
	    FeatureBufferingInstantiator ret = new FeatureBufferingInstantiator();
		ret.distance = parameters.get("radius", Double.class);
		ret.artifact = parameters.get("artifact", String.class);
		ret.subtract = parameters.get("subtract", Boolean.FALSE);
		ret.simplify = parameters.get("simplify", 0);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		IArtifact source = null;
		boolean transform = false;
		if (artifact == null) {
			// transform target's extents
			source = context.getTargetArtifact();
			transform = true;
		} else {
			source = context.getArtifact(artifact);
		}

		if (!(source instanceof IObjectArtifact)) {
			throw new IllegalArgumentException(
					"buffer instantiator: source artifact does not exist or is not an object artifact");
		}

		double bdistance = context.getScale().getSpace().getEnvelope().metersToDistance(this.distance);

		List<IObjectArtifact> ret = new ArrayList<>();
		context.getMonitor().info("starting spatial buffer operation");

		int tot = 0;
		int spc = 0;
		for (IArtifact obj : source) {
			ISpace space = ((IObservation) obj).getSpace();
			if (space != null) {
				IShape oshape = space.getShape();
				if (simplify > 0) {
					oshape = ((Shape)oshape).getSimplified(simplify);
				}
				IShape newShape = oshape.buffer(bdistance);
				if (simplify > 0) {
					((Shape)newShape).simplify(simplify);
				}
				if (subtract && oshape.getGeometryType() != IShape.Type.POINT
						&& oshape.getGeometryType() != IShape.Type.MULTIPOINT) {
					newShape = newShape.difference(oshape);
				}
				IScale newScale = Scale.substituteExtent(((IObservation) obj).getScale(), newShape);
				if (transform) {
					((ObservedArtifact) obj).setGeometry(newScale);
				} else {
					ret.add(context.newObservation(semantics, ((IDirectObservation) obj).getName() + "_buffered",
							newScale, /* TODO send useful metadata */null));
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
