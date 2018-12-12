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
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IObservable.ObservationType;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.ConcaveHull;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

import com.vividsolutions.jts.algorithm.ConvexHull;
import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;

public class ClusterExtractor implements IExpression, IInstantiator {

	Descriptor exprDescriptor = null;
	private IGrid grid;
	int minPoints = 15;
	double radius = 0;
	boolean convex = true;

	public ClusterExtractor() {
	}

	/**
	 * Use this to extract features through
	 * {@link #extractShapes(IState, IExpression, IMonitor)} or
	 * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IComputationContext)}
	 * outside of a k.LAB contextualizer.
	 * 
	 * @param grid
	 */
	public ClusterExtractor(IGrid grid) {
		this.grid = grid;
	}

	public ClusterExtractor(IParameters<String> parameters, IComputationContext context)
			throws KlabValidationException {

		// TODO support clustering of point features in other contextualizer
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
					.describe(expression.toString(), context);
		}

		if (parameters.containsKey("radius")) {
			this.radius = context.getScale().getSpace().getEnvelope()
					.convertDistance(parameters.get("radius", Double.class));
		} else if (parameters.containsKey("cellradius")) {
			this.radius = context.getScale().getSpace().getEnvelope()
					.convertDistance(parameters.get("radius", Integer.class)) * grid.getCellWidth();
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
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IState> sourceStates = new ArrayList<>();
		List<IState> inheritedStates = new ArrayList<>();
		List<IObjectArtifact> ret = new ArrayList<>();
		Map<IState, String> stateIdentifiers = new HashMap<>();
		StateSummary stateSummary = null;

		// TODO
		double selectFraction = Double.NaN;
		// TODO
		boolean topFraction = true;

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

		if (context.contains("source-state")) {
			IState sourceState = context.getArtifact(context.get("source-state", String.class), IState.class);
			if (sourceState == null) {
				throw new KlabResourceNotFoundException("feature extractor: source state "
						+ context.get("source-state", String.class) + " not found or not a state");
			}
			sourceStates.add(sourceState);
		}

		for (IState sourceState : sourceStates) {
			/*
			 * if the semantics is compatible with the quality's context, the instance
			 * inherits a view of each state.
			 */
			IConcept scontext = sourceState.getObservable().getContext();
			// the first condition should never happen
			if (scontext != null && Observables.INSTANCE.isCompatible(semantics.getType(), scontext)) {
				inheritedStates.add(sourceState);
				context.getMonitor().info(
						"cluster extractor: instances will inherit a rescaled view of " + sourceState.getObservable());
			}
		}

		// TODO
		IState fractionState = null;
		Range limits = null;
		if (sourceStates.size() == 1 && !Double.isNaN(selectFraction)) {
			fractionState = sourceStates.get(0);
			if (!(fractionState.getObservable().getObservationType() == ObservationType.QUANTIFICATION)) {
				throw new KlabValidationException(
						"cluster extractor: state for fraction extraction " + fractionState + " must be numeric");
			}
			// TODO
			// StateSummary stateSummary =
			// Observations.INSTANCE.getStateSummary(fractionState, )
		}

		Parameters<String> parameters = new Parameters<>();
		boolean warned = false;
		// List<Coordinate> geometries = new ArrayList<>();
		List<DoublePoint> dpoints = new ArrayList<>();

		for (Cell cell : grid) {

			Object o = null;

			if (fractionState != null) {

				o = Boolean.FALSE;
				double d = fractionState.get(cell, Double.class);
				if (!Double.isNaN(d)) {

					double perc = 0;
					if (topFraction) {
						perc = (stateSummary.getRange().get(1) - d)
								/ (stateSummary.getRange().get(1) - stateSummary.getRange().get(0));
					} else {
						perc = (d - stateSummary.getRange().get(0))
								/ (stateSummary.getRange().get(1) - stateSummary.getRange().get(0));
					}
					o = perc <= selectFraction;
				}

			} else if (expression != null) {

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
				ret.add(context.newObservation(semantics, semantics.getLocalName() + "_" + (nc + 1),
						Scale.substituteExtent(context.getScale(), Shape.create(geom, grid.getProjection()))));

				nc++;
			}
		}

		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return org.integratedmodelling.klab.common.Geometry.create("#s2");
	}

	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new ClusterExtractor(parameters, context);
	}

}
