package org.integratedmodelling.geoprocessing.core;

import static org.hortonmachine.gears.libs.modules.HMConstants.doubleNovalue;
import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.moment.StandardDeviation;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.GridGeometry2D;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.geotools.process.vector.BarnesSurfaceInterpolator;
import org.hortonmachine.gears.libs.modules.HMConstants;
import org.hortonmachine.gears.libs.modules.HMRaster;
import org.hortonmachine.gears.utils.coverage.CoverageUtilities;
import org.hortonmachine.gears.utils.math.matrixes.MatrixException;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.geometry.Envelope;

public class BarnesSurfaceResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	private String artifactId;
	private IConcept observable;
	private Double lenscale;
	private Double passes;
	private Double maxdis;
	private Double minobs;
	private Double conv;
	

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

		final Grid grid = Space.extractGrid(target);
		if (grid == null) {
			throw new KlabValidationException("Interpolation must be done on a grid extent");
		}

		boolean warned = false;
		String stateId = null;
		

		GridGeometry2D inInterpolationGrid = CoverageUtilities.gridGeometryFromRegionValues(grid.getNorth(), grid.getSouth(),
				grid.getEast(), grid.getWest(), (int) grid.getXCells(), (int) grid.getYCells(),
				grid.getProjection().getCoordinateReferenceSystem());
				
		Envelope env = inInterpolationGrid.getEnvelope();
		
		Double minY = env.getMinimum(0);
		Double maxY = env.getMaximum(0);
		Double minX = env.getMinimum(1);
		Double maxX = env.getMaximum(1);
		
		org.locationtech.jts.geom.Envelope Env2 = new org.locationtech.jts.geom.Envelope(minX, maxX, minY, maxY);
		
		
		int totfeat = 0;
		
		for (IArtifact feature : context.getArtifact(this.artifactId)) {
			Shape shape = (Shape) ((IObservation) feature).getScale().getSpace().getShape();
			if (shape.getGeometryType() != IShape.Type.POINT) {
				if (!warned) {
					warned = true;
					context.getMonitor().warn("cannot use non-point features for the time being");
				}
				continue;
			}

			IState svalue = null;
			
			if (this.observable == null) {
				if (((IDirectObservation) feature).getStates().size() == 1) {
					this.observable = ((IDirectObservation) feature).getStates().iterator().next().getObservable()
							.getType();
				} else {
					throw new KlabValidationException(
							"states in features: please pass a concept in the 'state' parameter");
				}
			}

			for (IState state : ((IDirectObservation) feature).getStates()) {
				if (stateId == null) {
					if (state.getObservable().getType().equals(this.observable)) {
						svalue = state;
						stateId = state.getObservable().getName();
						break;
					}
				} else {
					if (state.getObservable().getName().equals(stateId)) {
						svalue = state;
						break;
					}
				}
			}

			if (svalue == null) {
				if (!warned) {
					context.getMonitor().warn("missing states encountered in features: check parameters");
					warned = true;
				}
				continue;
			}

			totfeat++;
		}
		
		Coordinate[] inputObs = new Coordinate[totfeat];

		// set up the features
		int featureId = 0;
		
		for (IArtifact feature : context.getArtifact(this.artifactId)) {

			Shape shape = (Shape) ((IObservation) feature).getScale().getSpace().getShape();
			if (shape.getGeometryType() != IShape.Type.POINT) {
				if (!warned) {
					warned = true;
					context.getMonitor().warn("cannot use non-point features for the time being");
				}
				continue;
			}

			Point point = (Point) shape.getJTSGeometry();
			IState svalue = null;
			
			if (this.observable == null) {
				if (((IDirectObservation) feature).getStates().size() == 1) {
					this.observable = ((IDirectObservation) feature).getStates().iterator().next().getObservable()
							.getType();
				} else {
					throw new KlabValidationException(
							">1 states in features: please pass a concept in the 'state' parameter");
				}
			}

			for (IState state : ((IDirectObservation) feature).getStates()) {
				if (stateId == null) {
					if (state.getObservable().getType().equals(this.observable)) {
						svalue = state;
						stateId = state.getObservable().getName();
						break;
					}
				} else {
					if (state.getObservable().getName().equals(stateId)) {
						svalue = state;
						break;
					}
				}
			}

			if (svalue == null) {
				if (!warned) {
					context.getMonitor().warn("missing states encountered in features: check parameters");
					warned = true;
				}
				continue;
			}

			double v = svalue.get(((IObservation) feature).getScale().at(0), Double.class);

			// NaN destroy everything
			v = Double.isNaN(v) ? floatNovalue : v;
			
			// The variable to interpolate should be stored in z coordinate
			Coordinate coord = new Coordinate(point.getCoordinate().y, point.getCoordinate().x, v);
			
			inputObs[featureId] =  coord; 
			
			featureId++;

		}
		
		BarnesSurfaceInterpolator barnes = new BarnesSurfaceInterpolator(inputObs);
		
		if (lenscale != null) { 
			
			barnes.setLengthScale(lenscale);
			
		} else {
			
			barnes.setLengthScale( (Double) ((((maxX - minX)/grid.getXCells()) + ((maxY - minY)/grid.getYCells()))/2.0));
			
		}
		
		barnes.setPassCount(passes.intValue());
		barnes.setMaxObservationDistance(maxdis);
		barnes.setMinObservationCount(minobs.intValue());
		barnes.setConvergenceFactor(conv);
		barnes.setNoData(floatNovalue);
		
		float[][] interpArray = barnes.computeSurface(Env2, (int) (grid.getYCells()), (int) (grid.getXCells()));
		
		float[][] interpArrayRev = new float[interpArray.length][interpArray[0].length];
		
		//Output results are reversed (need to check why), therefore need to reverse the rows the output array
		
		int C = interpArray.length;
		
		for (int i = 0; i < C; i++) {
			
			interpArrayRev[i] = interpArray[C-(i+1)];
			
		}
		
		GridCoverageFactory gcf = new GridCoverageFactory();
		
		GridCoverage2D finalcov = gcf.create("interpolated", interpArrayRev, env);
		
		if (!context.getMonitor().isInterrupted()) {

			GeotoolsUtils.INSTANCE.coverageToState(finalcov, target, context.getScale(), (a) -> {
				if (a == (double) floatNovalue) {
					return Double.NaN;
				}
				return a;
			});
	   }
		
		return target;
	}

	@Override
	public Object eval(IContextualizationScope context, Object...params) throws KlabException {

	    Parameters<String> parameters = Parameters.create(params);
		BarnesSurfaceResolver ret = new BarnesSurfaceResolver();

		IKimConcept observable = parameters.get("state", IKimConcept.class);
		if (observable != null) {
			ret.observable = Concepts.INSTANCE.declare(observable);
		}

		ret.artifactId = parameters.get("artifact", String.class);
		
		ret.lenscale = parameters.get("scale", Double.class);
		
		ret.passes = parameters.get("iterations", Double.class);
		
		if (ret.passes == null) {ret.passes = 1.0;}
		
		ret.maxdis = parameters.get("maxdist", Double.class);
		
		if (ret.maxdis == null) {ret.maxdis = 0.0;}
		
		ret.minobs = parameters.get("minobs", Double.class);
		
		if (ret.minobs == null) {ret.minobs = 2.0;}
		
		ret.conv = parameters.get("convergence", Double.class);
		
		if (ret.conv == null) {ret.conv = 0.3;}
		
		return ret;
	}
}
