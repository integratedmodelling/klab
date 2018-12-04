package org.integratedmodelling.klab.components.geospace.processing;

import java.awt.geom.Point2D;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

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
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.runtime.code.groovy.GroovyExpression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.integratedmodelling.klab.utils.Range;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryCollection;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.impl.CoordinateArraySequence;

import ij.IJ;
import ij.ImagePlus;
import ij.blob.Blob;
import ij.blob.ManyBlobs;
import ij.process.ImageProcessor;

public class SurfaceVectorizer implements IExpression, IInstantiator {

	Descriptor exprDescriptor = null;
	private IGrid grid;
	private Shape boundingBox;
	GeometryFactory gfact = new GeometryFactory();

	public SurfaceVectorizer() {
	}

	/**
	 * Use this to extract features through
	 * {@link #extractShapes(IState, IExpression, IMonitor)} or
	 * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IComputationContext)}
	 * outside of a k.LAB contextualizer.
	 * 
	 * @param grid
	 */
	public SurfaceVectorizer(IGrid grid) {
		this.grid = grid;
	}

	public SurfaceVectorizer(IParameters<String> parameters, IComputationContext context)
			throws KlabValidationException {
		if (parameters.containsKey("select")) {
			Object expression = parameters.get("select");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.exprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context);
		}

		IScale scale = context.getScale();
		if (!(scale.isSpatiallyDistributed() && scale.getDimension(Type.SPACE).size() > 1
				&& scale.getDimension(Type.SPACE).isRegular())) {
			throw new KlabValidationException(
					"feature extraction only works on regular distributed spatial extents (grids)");
		}

		this.grid = ((Space) scale.getSpace()).getGrid();
		this.boundingBox = (Shape) scale.getSpace().getShape();
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
						"feature extractor: the selection expression does not reference any known state");
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
						"feature extractor: instances will inherit a rescaled view of " + sourceState.getObservable());
			}
		}

		// TODO
		IState fractionState = null;
		Range limits = null;
		if (sourceStates.size() == 1 && !Double.isNaN(selectFraction)) {
			fractionState = sourceStates.get(0);
			if (!(fractionState.getObservable().getObservationType() == ObservationType.QUANTIFICATION)) {
				throw new KlabValidationException(
						"feature extractor: state for fraction extraction " + fractionState + " must be numeric");
			}
			// TODO
			// StateSummary stateSummary =
			// Observations.INSTANCE.getStateSummary(fractionState, )
		}

		Parameters<String> parameters = new Parameters<>();
		boolean warned = false;
		List<Geometry> geometries = new ArrayList<>();

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
							"feature extractor: feature extraction selector must return true/false");
				}

			} else if (!warned) {
				context.getMonitor().warn("feature extractor: no input: specify either select or select fraction");
				warned = true;
			}

			if (o instanceof Boolean && (Boolean)o) { 
				geometries.add(((Shape) cell.getShape()).getJTSGeometry());
			}
		}

		/*
		 * build the final geometry
		 */
		GeometryCollection geometryCollection = (GeometryCollection) Geospace.gFactory.buildGeometry(geometries);
		ret.add(context.newObservation(semantics, semantics.getLocalName() + "_0", Scale.substituteExtent(context.getScale(),
				Shape.create(geometryCollection.union(), grid.getProjection()))));

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
		return new SurfaceVectorizer(parameters, context);
	}

}
