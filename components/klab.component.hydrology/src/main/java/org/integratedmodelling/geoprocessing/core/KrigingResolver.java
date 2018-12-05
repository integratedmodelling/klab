package org.integratedmodelling.geoprocessing.core;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class KrigingResolver implements IResolver<IState>, IExpression {

	private static final double DEFAULT_TCA_THRESHOLD = 0.001;

	double threshold = Double.NaN;

	@Override
	public IGeometry getGeometry() {
		return Geometry.create("S2");
	}

	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public IState resolve(IState target, IComputationContext context) throws KlabException {

		IState tca = context.getArtifact("upstream_cell_count", IState.class);
		Grid grid = Space.extractGrid(target);

		if (grid == null) {
			throw new KlabValidationException("Flow accumulation must be computed on a grid extent");
		}

		if (Double.isNaN(threshold) || threshold == 0) {
			threshold = DEFAULT_TCA_THRESHOLD;
		}

		double ttreshold = (int) (grid.getCellCount() * threshold);
		context.getMonitor().info("TCA threshold is " + ttreshold);

		for (ILocator locator : target.getSpace()) {
			if (context.getMonitor().isInterrupted()) {
				break;
			}
			double tcav = tca.get(locator, Double.class).doubleValue();
			target.set(locator, Double.isNaN(tcav) ? null : tcav >= ttreshold);
		}
		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		KrigingResolver ret = new KrigingResolver();
		ret.threshold = parameters.get("tca.threshold", Double.NaN);
		return ret;
	}
}
