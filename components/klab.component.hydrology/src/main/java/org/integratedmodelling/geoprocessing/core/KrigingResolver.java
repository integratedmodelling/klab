package org.integratedmodelling.geoprocessing.core;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;
import static org.hortonmachine.gears.libs.modules.HMConstants.doubleNovalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.moment.Variance;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.hortonmachine.gears.utils.coverage.CoverageUtilities;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.geoprocessing.algorithms.OmsKriging;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Point;

public class KrigingResolver implements IResolver<IState>, IExpression {

	private String artifactId;
	private String elevationId;
	private double radius;
	private double sill;
	private double nugget = 0.01;
	private IConcept observable;
	private boolean gaussian = true;
	private boolean logarithmic = false;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
	public IState resolve(IState target, IComputationContext context) throws KlabException {

		Grid grid = Space.extractGrid(target);
		if (grid == null) {
			throw new KlabValidationException("Kriging must be computed on a grid extent");
		}

		boolean warned = false;
		String stateId = null;

		OmsKriging kriging = new OmsKriging();
		IState elevation = context.getArtifact("elevation", IState.class);

		kriging.pMode = 1;
		kriging.pSemivariogramType = 0; // gaussian
		// TODO pA pS etc if gaussian; include zeros; etc
		kriging.inInterpolationGrid = CoverageUtilities.gridGeometryFromRegionValues(grid.getNorth(), grid.getSouth(),
				grid.getEast(), grid.getWest(), (int) grid.getXCells(), (int) grid.getYCells(),
				grid.getProjection().getCoordinateReferenceSystem());
		kriging.inStations = new DefaultFeatureCollection();
		kriging.inData = new HashMap<>();
		kriging.fStationsid = "id";
		
		SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();
		b.setName("kriging");
		b.setCRS(grid.getProjection().getCoordinateReferenceSystem());
		b.add("the_geom", Point.class);
		b.add("id", Integer.class);
		if (elevation != null) {
			kriging.fStationsZ = "elev";
			b.add("elev", Double.class);
		}
		SimpleFeatureType featureType = b.buildFeatureType();

		// set up the features
		int featureId = 1;
		List<Double> vals = new ArrayList<>();
		for (IArtifact feature : context.getArtifact(this.artifactId)) {

			Shape shape = (Shape) ((IObservation) feature).getScale().getSpace().getShape();
			if (shape.getGeometryType() != IShape.Type.POINT) {
				if (!warned) {
					warned = true;
					context.getMonitor().warn("kriging: cannot use non-point features for the time being");
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
							"kriging: >1 states in features: please pass a concept in the 'state' parameter");
				}
			}

			for (IState state : ((IDirectObservation) feature).getStates()) {
				if (stateId == null) {
					if (state.getObservable().getType().equals(this.observable)) {
						svalue = state;
						stateId = state.getObservable().getLocalName();
						break;
					}
				} else {
					if (state.getObservable().getLocalName().equals(stateId)) {
						svalue = state;
						break;
					}
				}
			}

			if (svalue == null) {
				if (!warned) {
					context.getMonitor().warn("kriging: missing states encountered in features: check parameters");
					warned = true;
				}
				continue;
			}

			double v = svalue.get(((IObservation) feature).getScale().getLocator(0), Double.class);
			double z = 0.0;
			if (elevation != null) {
				z = elevation.get(shape, Double.class);
			}
			// NaN destroy everything
			z = Double.isNaN(z) ? doubleNovalue : z;
			v = Double.isNaN(v) ? doubleNovalue : v;
			
			point.getCoordinate().z = z;

			SimpleFeatureBuilder builder = new SimpleFeatureBuilder(featureType);
			builder.addAll(
					elevation == null ? new Object[] { point, featureId } : new Object[] { point, featureId, z });
			((DefaultFeatureCollection) kriging.inStations).add(builder.buildFeature("feature." + featureId));

			kriging.inData.put(featureId, new double[] { v });

			vals.add(v);

			featureId++;
		}

		if (gaussian) {
			kriging.pA = radius; // context.getScale().getSpace().getEnvelope().convertDistance(radius);
			kriging.pNug = nugget;
			kriging.pS = sill == 0 ? new Variance().evaluate(NumberUtils.doubleArrayFromCollection(vals)) : sill;
			kriging.defaultVariogramMode = 1;
		} else {
			// https://gis.stackexchange.com/questions/156349/in-jgrasstools-kriging-what-is-the-variogram-model-used-when-defaultvariogrammo
			throw new KlabUnimplementedException("kriging: exponential parameterization is still unimplemented");
		}

		kriging.pm = new TaskMonitor(context.getMonitor());
		kriging.doProcess = true;
		kriging.doReset = false;
		context.getMonitor().info("computing kriging...");
		try {
			kriging.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}

		if (!context.getMonitor().isInterrupted()) {
			GeotoolsUtils.INSTANCE.coverageToState(kriging.outGrid, target, (a) -> {
				if (a == (double) floatNovalue) {
					return Double.NaN;
				}
				return a;
			});
		}
		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {

		KrigingResolver ret = new KrigingResolver();

		IKimConcept observable = parameters.get("state", IKimConcept.class);
		if (observable != null) {
			ret.observable = Concepts.INSTANCE.declare(observable);
		}

		ret.artifactId = parameters.get("artifact", String.class);
		ret.elevationId = parameters.get("elevation", String.class);
		ret.radius = parameters.get("radius", 0.0);
		ret.sill = parameters.get("sill", 0.0);
		ret.nugget = parameters.get("nugget", 0.01);

		return ret;
	}
}
