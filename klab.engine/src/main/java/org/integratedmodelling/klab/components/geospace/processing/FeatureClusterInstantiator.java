package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.utils.ConcaveHull;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;

import com.vividsolutions.jts.algorithm.ConvexHull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.Point;

public class FeatureClusterInstantiator implements IExpression, IInstantiator {

	double radius = 0;
	boolean convex = true;
	private IObjectArtifact artifact;

	public FeatureClusterInstantiator() {
	}

	public FeatureClusterInstantiator(IParameters<String> parameters, IContextualizationScope context)
			throws KlabValidationException {

		IScale scale = context.getScale();
		if (!(scale.isSpatiallyDistributed())) {
			throw new KlabValidationException("feature clustering only works on spatial extents");
		}
		
		if (parameters.get("artifact") == null || !(context.getArtifact(parameters.get("artifact", String.class)) instanceof IObjectArtifact)) {
			throw new KlabValidationException("target artifact is unknown or does not contain objects");
		}
		
		this.artifact = (IObjectArtifact)context.getArtifact(parameters.get("artifact", String.class));

		if (parameters.containsKey("radius")) {
			this.radius = context.getScale().getSpace().getEnvelope()
					.metersToDistance(parameters.get("radius", Double.class));
		} else {
			this.radius = Math
					.sqrt(scale.getSpace().getEnvelope().getWidth() * scale.getSpace().getEnvelope().getWidth()
							+ scale.getSpace().getEnvelope().getHeight() * scale.getSpace().getEnvelope().getHeight())
					/ 200;
		}

		if (parameters.containsKey("convex")) {
			this.convex = parameters.get("convex", Boolean.class);
		}

	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		List<DoublePoint> dpoints = new ArrayList<>();
		for (IArtifact artifact : this.artifact) {

			ISpace space = ((IObservation) artifact).getSpace();
			if (space == null) {
				continue;
			}
			Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
			dpoints.add(new DoublePoint(new double[] { point.getCoordinate().x, point.getCoordinate().y }));
		}

		int nc = 0;
		if (dpoints.size() > 0) {

			DBSCANClusterer<DoublePoint> clusterer = new DBSCANClusterer<>(radius, 2);
			for (Cluster<DoublePoint> cluster : clusterer.cluster(dpoints)) {
				List<DoublePoint> pts = cluster.getPoints();
				Coordinate[] points = new Coordinate[pts.size()];
				for (int i = 0; i < pts.size(); i++) {
					points[i] = new Coordinate(pts.get(i).getPoint()[0], pts.get(i).getPoint()[1]);
				}
				GeometryCollection shape = (GeometryCollection) Geospace.gFactory.createMultiPoint(points);
				Geometry geom = null;
				if (convex) {
					ConvexHull hull = new ConvexHull(shape);
					geom = hull.getConvexHull();
				} else {
					geom = new ConcaveHull().transform(shape);
				}
				ret.add(context.newObservation(semantics, Observables.INSTANCE.getDisplayName(semantics) + "_" + (nc + 1),
						Scale.substituteExtent(context.getScale(), Shape.create(geom, context.getScale().getSpace().getProjection())), /* TODO send useful metadata */null));

				nc++;
			}
		}

		return ret;
	}

//	@Override
//	public IGeometry getGeometry() {
//		return org.integratedmodelling.klab.common.Geometry.create("#s2");
//	}

	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		return new FeatureClusterInstantiator(parameters, context);
	}

}
