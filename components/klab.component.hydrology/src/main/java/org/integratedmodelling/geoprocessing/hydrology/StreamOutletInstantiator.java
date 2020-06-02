package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.awt.image.RenderedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.media.jai.iterator.RandomIter;
import javax.media.jai.iterator.RandomIterFactory;

import org.hortonmachine.gears.libs.modules.FlowNode;
import org.hortonmachine.hmachine.modules.demmanipulation.markoutlets.OmsMarkoutlets;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.StringUtil;

public class StreamOutletInstantiator implements IInstantiator, IExpression {

	private double threshold = 0.05;
	private int extract = 1;

	class OutletData {
		double x;
		double y;
		double importance;

		/**
		 * Spatial extent with grid for corresponding watershed.
		 */
		ISpace space;

		OutletData(double x, double y, double importance) {
			this.x = x;
			this.y = y;
			this.importance = importance;
		}
	}

	@Override
	public Type getType() {
		return Type.OBJECT;
	}

	@Override
	public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
		StreamOutletInstantiator ret = new StreamOutletInstantiator();
		ret.threshold = parameters.get("threshold", 0.05);
		ret.extract = parameters.get("extract", -1);
		return ret;
	}

	@Override
	public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope context) throws KlabException {

		List<IObjectArtifact> ret = new ArrayList<>();
		Grid grid = Space.extractGrid(context.getContextObservation());
		if (grid == null) {
			throw new KlabValidationException("Flow accumulation must be computed on a grid extent");
		}

		IState flowDir = context.getArtifact("flow_directions_d8", IState.class);
		IState tca = context.getArtifact("upstream_cell_count", IState.class);

		OmsMarkoutlets algorithm = new OmsMarkoutlets();
		algorithm.inFlow = GeotoolsUtils.INSTANCE.stateToCoverage(flowDir, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue);
		algorithm.pm = new TaskMonitor(context.getMonitor());
		algorithm.doProcess = true;
		algorithm.doReset = false;
		context.getMonitor().info("finding outlets...");
		try {
			algorithm.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}

		/*
		 * extract all outlets as single coordinates first, then make objects out of
		 * them.
		 */
		RenderedImage image = algorithm.outFlow.getRenderedImage();
		int npix = image.getHeight() * image.getWidth();
		RandomIter itera = RandomIterFactory.create(image, null);
		RenderedImage imtca = GeotoolsUtils.INSTANCE.stateToCoverage(tca, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue)
				.getRenderedImage();
		RandomIter itca = RandomIterFactory.create(imtca, null);

		List<OutletData> outlets = new ArrayList<>();

		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				if (itera.getSampleDouble(x, y, 0) == FlowNode.OUTLET) {
					double importance = (itca.getSampleDouble(x, y, 0) / (npix));
					if (importance >= threshold) {
						context.getMonitor()
								.debug("outlet at " + x + "," + y + " drains " + (importance * 100) + "% of context");
						double[] xy = grid.getWorldCoordinatesAt(x, y);
						outlets.add(new OutletData(xy[0], xy[1], importance));
					}
				}
			}
		}

		context.getMonitor().info("found " + outlets.size() + " outlets with basins covering "
				+ StringUtil.percent(threshold) + " or more");

		Collections.sort(outlets, new Comparator<OutletData>() {

			@Override
			public int compare(OutletData arg0, OutletData arg1) {
				return new Double(arg1.importance).compareTo(arg0.importance);
			}
		});

		for (int i = 0; i < outlets.size(); i++) {
			if (extract >= 0 && i >= extract) {
				break;
			}
			ret.add(context.newObservation(semantics, "outlet_" + (i + 1),
					Scale.substituteExtent(context.getScale(),
							Shape.create(outlets.get(i).x, outlets.get(i).y, grid.getProjection())),
					/* TODO send useful metadata */null));
		}

		return ret;
	}

}
