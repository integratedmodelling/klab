package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.ml.clustering.Cluster;
import org.apache.commons.math3.ml.clustering.DBSCANClusterer;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.ConcaveHull;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;

import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;

public class PointClusterInstantiator implements IExpression, IInstantiator {

	Descriptor exprDescriptor = null;
	private IGrid grid;
	int minPoints = 15;
	double radius = 0;
	boolean convex = true;

	public PointClusterInstantiator() {
	}

	/**
	 * Use this to extract features through
	 * {@link #extractShapes(IState, IExpression, IMonitor)} or
	 * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IContextualizationScope)}
	 * outside of a k.LAB contextualizer.
	 * 
	 * @param grid
	 */
	public PointClusterInstantiator(IGrid grid) {
		this.grid = grid;
	}

	public PointClusterInstantiator(IParameters<String> parameters, IContextualizationScope context)
			throws KlabValidationException {

		IScale scale = context.getScale();
		if (!(scale.isSpatiallyDistributed() && scale.getDimension(Type.SPACE).size() > 1
				&& scale.getDimension(Type.SPACE).isRegular())) {
			throw new KlabValidationException(
					"point clustering only works on regular distributed spatial extents (grids)");
		}

		this.grid = ((Space) scale.getSpace()).getGrid();

		if (parameters.containsKey("select")) {
			Object expression = parameters.get("select");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.exprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context.getExpressionContext());
		}

		if (parameters.containsKey("radius")) {
			this.radius = context.getScale().getSpace().getEnvelope()
					.metersToDistance(parameters.get("radius", Double.class));
		} else if (parameters.containsKey("cellradius")) {
			this.radius = (double) parameters.get("cellradius", Integer.class) * grid.getCellWidth();
		}

		if (parameters.containsKey("minpoints")) {
			this.minPoints = parameters.get("minpoints", Integer.class);
		}

		if (parameters.containsKey("convex")) {
			this.convex = parameters.get("convex", Boolean.class);
		}

		/*
		 * adaptive radius if not supplied - allowed to skip one cell but not two
		 */
		if (this.radius == 0) {
			this.radius = grid.getCellWidth() * 6;
		}
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		List<IState> sourceStates = new ArrayList<>();
		List<IObjectArtifact> ret = new ArrayList<>();
		Map<IState, String> stateIdentifiers = new HashMap<>();
		StateSummary stateSummary = null;

		IExpression expression = null;
		if (exprDescriptor != null) {
			// check inputs and see if the expr is worth anything in this context
			for (String input : exprDescriptor.getIdentifiers()) {
				if (exprDescriptor.isScalar(input) && context.getArtifact(input, IState.class) != null) {
					IState state = context.getArtifact(input, IState.class);
					sourceStates.add(state);
					stateIdentifiers.put(state, input);
				}
			}
			if (sourceStates.isEmpty()) {
				throw new KlabResourceNotFoundException(
						"cluster extractor: the selection expression does not reference any known state");
			}
			expression = exprDescriptor.compile();
		}

		Parameters<String> parameters = new Parameters<>();
		boolean warned = false;
		List<DoublePoint> dpoints = new ArrayList<>();

		for (Cell cell : grid) {

			Object o = null;
			if (expression != null) {

				parameters.clear();
				for (IState state : sourceStates) {
					o = state.get(cell, Object.class);
					parameters.put(stateIdentifiers.get(state), o);
				}

				o = expression.eval(parameters, context);
				if (o == null) {
					o = Boolean.FALSE;
				}
				if (!(o instanceof Boolean)) {
					throw new KlabValidationException(
							"cluster extractor: feature extraction selector must return true/false");
				}

			} else if (!warned) {
				context.getMonitor().warn("cluster extractor: no input: specify either select or select fraction");
				warned = true;
			}

			if (o instanceof Boolean && (Boolean) o) {
				Coordinate cdc = ((Shape) cell.getShape().getCentroid()).getJTSGeometry().getCoordinate();
				// geometries.add(cdc);
				dpoints.add(new DoublePoint(new double[] { cdc.x, cdc.y }));
			}
		}

		int nc = 0;
		if (dpoints.size() > 0) {

			DBSCANClusterer<DoublePoint> clusterer = new DBSCANClusterer<>(radius, minPoints);
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
						Scale.substituteExtent(context.getScale(), Shape.create(geom, grid.getProjection())),
						/* TODO send useful metadata */null));

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
		return new PointClusterInstantiator(parameters, context);
	}

}
