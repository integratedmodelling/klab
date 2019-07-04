package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.ArrayList;
import java.util.List;

import org.hortonmachine.hmachine.modules.demmanipulation.wateroutlet.OmsExtractBasin;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.processing.PolygonInstantiator;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;

import com.vividsolutions.jts.geom.Point;

public class WatershedInstantiator implements IInstantiator, IExpression {

	private boolean whole = false;

	private class WTaskMonitor extends TaskMonitor {

		public boolean touched = false;

		public WTaskMonitor(IMonitor monitor) {
			super(monitor);
		}

		@Override
		public void errorMessage(String arg0) {
			if (arg0.contains("touched boundaries")) {
				touched = true;
			} else {
				super.errorMessage(arg0);
			}
		}

	};

//	@Override
//	public IGeometry getGeometry() {
//		return Geometry.create("#s2");
//	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		WatershedInstantiator ret = new WatershedInstantiator();
		ret.whole = parameters.get("threshold", Boolean.FALSE);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		Grid grid = Space.extractGrid(context.getContextObservation());
		if (grid == null) {
			throw new KlabValidationException("Flow accumulation must be computed on a grid extent");
		}

		IState flowDir = context.getArtifact("flow_directions_d8", IState.class);
		PolygonInstantiator extractor = new PolygonInstantiator(grid);

		for (IArtifact artifact : context.getArtifact("stream_outlet")) {

			ISpace space = ((IObservation) artifact).getSpace();

			if (space == null) {
				continue;
			}

			Point point = ((Shape) space.getShape()).getJTSGeometry().getCentroid();
			WTaskMonitor monitor = new WTaskMonitor(context.getMonitor());

			OmsExtractBasin ebasin = new OmsExtractBasin();
			ebasin.inFlow = GeotoolsUtils.INSTANCE.stateToCoverage(flowDir, DataBuffer.TYPE_FLOAT, floatNovalue);
			ebasin.pm = monitor;
			ebasin.pEast = point.getX();
			ebasin.pNorth = point.getY();
			ebasin.doProcess = true;
			ebasin.doReset = false;

			// again, would be great but a long-standing JAI bug makes it throw an NPE when
			// inside a jar.
			ebasin.doVector = false;

			try {
				ebasin.process();
			} catch (Exception e) {
				throw new KlabException(e);
			}

			if (monitor.errors > 0) {
				context.getMonitor().warn("Watershed corresponding to " + ((IDirectObservation) artifact).getName()
						+ " skipped due to errors during extraction");
				continue;
			} else if (monitor.touched) {
				if (whole) {
					context.getMonitor().warn("Watershed corresponding to " + ((IDirectObservation) artifact).getName()
							+ " crosses region boundaries: skipped");
					continue;
				} else {
					context.getMonitor().warn("Watershed corresponding to " + ((IDirectObservation) artifact).getName()
							+ " crosses region boundaries");
				}
			}

			for (IShape shape : extractor.extractShapes(ebasin.outBasin, Extensions.INSTANCE
					.compileExpression("value == 1.0", context.getExpressionContext(), Extensions.DEFAULT_EXPRESSION_LANGUAGE, false), context)) {
				ret.add(context.newObservation(semantics, "watershed_of_" + ((IDirectObservation) artifact).getName(),
						Scale.substituteExtent(context.getScale(), shape), /* TODO send useful metadata */null));
			}

			// adios - come back when JAI bug is addressed. Sad as the above is a lot
			// slower.
			// if (ebasin.outVectorBasin != null && ebasin.outVectorBasin.size() > 0) {
			//
			// List<com.vividsolutions.jts.geom.Geometry> geoms = FeatureUtilities
			// .featureCollectionToGeometriesList(ebasin.outVectorBasin, false, null);
			// Shape shape = Shape.create(geoms,
			// context.getScale().getSpace().getProjection());
			// ret.add(context.newObservation(semantics, "watershed_of_" +
			// ((IDirectObservation) artifact).getName(),
			// Scale.substituteExtent(context.getScale(), shape)));
			// }

		}

		return ret;
	}

}
