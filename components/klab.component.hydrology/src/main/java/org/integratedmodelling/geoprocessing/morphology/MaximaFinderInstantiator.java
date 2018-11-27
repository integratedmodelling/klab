package org.integratedmodelling.geoprocessing.morphology;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class MaximaFinderInstantiator implements IInstantiator, IExpression {

	static enum Mode {
		CUSTOM,
		MIXED_PINES_AND_DECIDUOUS,
		DECIDUOUS,
		CONIFER
	}
	
	private double threshold;
	private double maxRadius;
	private double downsize = 0.6;
	private boolean circular;
	private int windowSize = 3;
	private Mode mode = Mode.CUSTOM;
	
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
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IComputationContext context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		Grid grid = Space.extractGrid(context.getContextObservation());
		if (grid == null) {
			throw new KlabValidationException("Local maxima must be computed on a grid extent");
		}
//
//		OmsMarkoutlets algorithm = new OmsMarkoutlets();
//		algorithm.inFlow = GeotoolsUtils.INSTANCE.stateToCoverage(flowDir, DataBuffer.TYPE_FLOAT, floatNovalue);
//		algorithm.pm = new TaskMonitor(context.getMonitor());
//		algorithm.doProcess = true;
//		algorithm.doReset = false;
//		context.getMonitor().info("finding outlets...");
//		try {
//			algorithm.process();
//		} catch (Exception e) {
//			throw new KlabException(e);
//		}

//		List<OutletData> outlets = new ArrayList<>();
//
//		for (int x = 0; x < image.getWidth(); x++) {
//			for (int y = 0; y < image.getHeight(); y++) {
//				if (itera.getSampleDouble(x, y, 0) == FlowNode.OUTLET) {
//					double importance = (itca.getSampleDouble(x, y, 0) / (npix));
//					if (importance >= threshold) {
//						context.getMonitor()
//								.debug("outlet at " + x + "," + y + " drains " + (importance * 100) + "% of context");
//						double[] xy = grid.getWorldCoordinatesAt(x, y);
//						outlets.add(new OutletData(xy[0], xy[1], importance));
//					}
//				}
//			}
//		}
//
//		context.getMonitor().info("found " + outlets.size() + " outlets with basins covering "
//				+ StringUtils.percent(threshold) + " or more");
//
//		Collections.sort(outlets, new Comparator<OutletData>() {
//
//			@Override
//			public int compare(OutletData arg0, OutletData arg1) {
//				return new Double(arg1.importance).compareTo(arg0.importance);
//			}
//		});
//
//		for (int i = 0; i < outlets.size(); i++) {
//			if (extract >= 0 && i >= extract) {
//				break;
//			}
//			ret.add(context.newObservation(semantics, "outlet_" + (i + 1), Scale.substituteExtent(context.getScale(),
//					Shape.create(outlets.get(i).x, outlets.get(i).y, grid.getProjection()))));
//		}

		return ret;
	}

}
