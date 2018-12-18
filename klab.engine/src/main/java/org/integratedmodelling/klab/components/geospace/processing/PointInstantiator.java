package org.integratedmodelling.klab.components.geospace.processing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Parameters;

import com.vividsolutions.jts.geom.Geometry;

public class PointInstantiator implements IExpression, IInstantiator {

	Descriptor exprDescriptor = null;
	private IGrid grid;

	public PointInstantiator() {
	}

	/**
	 * Use this to extract features through
	 * {@link #extractShapes(IState, IExpression, IMonitor)} or
	 * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IComputationContext)}
	 * outside of a k.LAB contextualizer.
	 * 
	 * @param grid
	 */
	public PointInstantiator(IGrid grid) {
		this.grid = grid;
	}

	public PointInstantiator(IParameters<String> parameters, IComputationContext context)
			throws KlabValidationException {

		IScale scale = context.getScale();
		if (!(scale.isSpatiallyDistributed() && scale.getDimension(Type.SPACE).size() > 1
				&& scale.getDimension(Type.SPACE).isRegular())) {
			throw new KlabValidationException(
					"feature extraction only works on regular distributed spatial extents (grids)");
		}

		if (parameters.containsKey("select")) {
			Object expression = parameters.get("select");
			if (expression instanceof IKimExpression) {
				expression = ((IKimExpression) expression).getCode();
			}
			this.exprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
					.describe(expression.toString(), context);
		}

		this.grid = ((Space) scale.getSpace()).getGrid();
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IState> sourceStates = new ArrayList<>();
		List<Pair<String, IState>> inheritedStates = new ArrayList<>();
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

		for (Pair<String, IState> contextStates : context.getArtifacts(IState.class)) {
			/*
			 * if the semantics is compatible with the quality's context, the instance
			 * inherits a view of each state.
			 */
			IConcept scontext = contextStates.getSecond().getObservable().getContext();
			// the first condition should never happen
			if (scontext != null && Observables.INSTANCE.isCompatible(semantics.getType(), scontext)) {
				inheritedStates.add(contextStates);
				context.getMonitor().info(
						"feature extractor: instances will inherit a rescaled view of " + contextStates.getSecond().getObservable());
			}
		}

		Parameters<String> parameters = new Parameters<>();
		boolean warned = false;
		List<Geometry> geometries = new ArrayList<>();

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
							"point extractor: feature extraction selector must return true/false");
				}

			} else if (!warned) {
				context.getMonitor().warn("point extractor: no input: specify either select or select fraction");
				warned = true;
			}

			if (o instanceof Boolean && (Boolean) o) {
				geometries.add(((Shape) cell.getShape().getCentroid()).getJTSGeometry());
			}
		}

		for (int i = 0; i < geometries.size(); i++) {

			IScale instanceScale = Scale.substituteExtent(context.getScale(),
					Shape.create(geometries.get(i), grid.getProjection()));
			IObjectArtifact instance = context.newObservation(semantics, semantics.getLocalName() + "_" + (i + 1),
					instanceScale);

			for (Pair<String, IState> inherited : inheritedStates) {
				IState stateView = Observations.INSTANCE.getStateView(inherited.getSecond(), instanceScale, context);
				((IRuntimeContext) context).link(instance, stateView);
			}
		}

		return ret;
	}

	@Override
	public IGeometry getGeometry() {
		return org.integratedmodelling.klab.common.Geometry.create("#s0");
	}

	@Override
	public IArtifact.Type getType() {
		return IArtifact.Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new PointInstantiator(parameters, context);
	}

}
