package org.integratedmodelling.geoprocessing.morphology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

import org.geotools.data.simple.SimpleFeatureIterator;
import org.hortonmachine.lesto.modules.vegetation.rastermaxima.OmsRasterMaximaFinder;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.StateSummary;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.opengis.feature.simple.SimpleFeature;

public class MaximaFinderInstantiator implements IInstantiator, IExpression {

	static enum Mode {
		CUSTOM, MIXED_PINES_AND_DECIDUOUS_TREES, DECIDUOUS, CONIFER
	}

	private double threshold;
	private double relativeThreshold;
	private double maxRadius;
	private double borderDistanceThreshold = -1;
	private double downsize = 0.6;
	private boolean circular;
	private int windowSize = 3;
	private Mode mode = Mode.CUSTOM;
	private String chmId = null;
	private String surfaceId = null;
	int size = 3;
	private String thresholdExpression;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("#s0");
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		MaximaFinderInstantiator ret = new MaximaFinderInstantiator();
		ret.chmId = parameters.get("chm", String.class);
		ret.surfaceId = parameters.get("surface", String.class);
		ret.circular = parameters.get("circular", Boolean.FALSE);
		ret.maxRadius = parameters.get("radius", 0.0);
		ret.relativeThreshold = parameters.get("relative-threshold", 0.0);
		ret.downsize = parameters.get("downsize", 0.6);
		if (parameters.get("threshold") instanceof IKimExpression) {
			ret.thresholdExpression = parameters.get("threshold", IKimExpression.class).getCode();
		} else {
			ret.threshold = parameters.get("threshold", -1.0);
		}
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		Grid grid = Space.extractGrid(context.getContextObservation());
		if (grid == null) {
			throw new KlabValidationException("Local maxima must be computed on a grid extent");
		}

		OmsRasterMaximaFinder algorithm = new OmsRasterMaximaFinder();

		if (chmId != null) {
			IState state = context.getArtifact(chmId, IState.class);
			if (state == null) {
				throw new IllegalArgumentException(
						"maxima extractor: no input state named '" + chmId + "' found in context");
			}
			algorithm.inDsmDtmDiff = GeotoolsUtils.INSTANCE.stateToCoverage(state, DataBuffer.TYPE_FLOAT, floatNovalue);
		} else if (surfaceId != null) {

			// differentiate the surface
			IState state = context.getArtifact(surfaceId, IState.class);
			if (state == null) {
				throw new IllegalArgumentException(
						"maxima extractor: no input state named '" + surfaceId + "' found in context");
			}
			StateSummary summary = Observations.INSTANCE.getStateSummary(state, ITime.INITIALIZATION);
			algorithm.inDsmDtmDiff = GeotoolsUtils.INSTANCE.stateToCoverage(state, DataBuffer.TYPE_FLOAT, floatNovalue,
					(value) -> {
						if (value instanceof Number && !Double.isNaN(((Number) value).doubleValue())) {
							value = ((Number) value).doubleValue() - summary.getRange().get(0);
						}
						return value;
					});

			if (relativeThreshold > 0) {
				double cutoff = 0;
				if (threshold > 0) {
					cutoff = threshold;
				}
				threshold = ((summary.getRange().get(1) - summary.getRange().get(0)) * relativeThreshold);
				if (threshold < cutoff) {
					threshold = cutoff;
				}
			} else {
				if (thresholdExpression != null) {

					IExpression threx = Extensions.INSTANCE.compileExpression(thresholdExpression,
							Extensions.DEFAULT_EXPRESSION_LANGUAGE);

					Object o = threx.eval(
							Parameters.create("min", summary.getRange().get(0), "max", summary.getRange().get(1),
									"mean", summary.getMean(), "std", summary.getStandardDeviation(), "target", state),
							context);

					if (!(o instanceof Number)) {
						throw new IllegalStateException(
								"maxima extractor: threshold expression does not evaluate to a number");
					}

					threshold = ((Number) o).doubleValue();

				} else if (threshold < 0) {
					// top third
					threshold = (summary.getRange().get(1) - summary.getRange().get(0) / 1.5);
				} else {
					// threshold is in non-differentiated values
					threshold -= summary.getRange().get(0);
				}
			}

		} else {
			// should not happen but it can for now (no xor arg validation yet).
			throw new IllegalArgumentException(
					"maxima extractor: no input state provided. Give either 'chm' or 'surface'.");
		}

		if (maxRadius == 0) {
			maxRadius = Math.sqrt(grid.getEnvelope().getWidth() * grid.getEnvelope().getWidth()
					+ grid.getEnvelope().getHeight() * grid.getEnvelope().getHeight()) / 50;
			maxRadius = grid.getEnvelope().distanceToMeters(maxRadius);
		}

		algorithm.pMode = mode.name().toLowerCase();
		algorithm.pThreshold = threshold;
		algorithm.pSize = mode == Mode.CUSTOM
				? (int) (maxRadius / grid.getEnvelope().distanceToMeters(grid.getCellWidth()))
				: size;
		if (algorithm.pSize < 2) {
			algorithm.pSize = 2;
		} else if (algorithm.pSize > grid.getXCells() / 10) {
			algorithm.pSize = (int) grid.getXCells() / 10;
		}
		algorithm.pPercent = (int) (downsize * 100);
		algorithm.pMaxRadius = maxRadius;
		// see this - should probably be larger in small-scale, high-res contexts.
		// Default was 2 and prevented any results.
		algorithm.pTopBufferThresCellCount = 0;
		algorithm.doCircular = circular;
		algorithm.pBorderDistanceThres = borderDistanceThreshold;
		algorithm.pm = new TaskMonitor(context.getMonitor());
		algorithm.doProcess = true;
		algorithm.doReset = false;
		try {
			algorithm.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}

		SimpleFeatureIterator it = algorithm.outMaxima.features();
		int i = 1;
		while (it.hasNext()) {
			SimpleFeature feature = it.next();
			if (feature.getDefaultGeometry() instanceof com.vividsolutions.jts.geom.Geometry) {
				IScale instanceScale = Scale.substituteExtent(context.getScale(), Shape.create(
						(com.vividsolutions.jts.geom.Geometry) feature.getDefaultGeometry(), grid.getProjection()));
				ret.add(context.newObservation(semantics, Observables.INSTANCE.getDisplayName(semantics) + "_" + (i++), instanceScale,
						/* TODO send useful metadata */null));
			}
		}

		return ret;
	}

}
