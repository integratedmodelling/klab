package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Parameters;

public class StreamNetworkResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	private static final double DEFAULT_TCA_THRESHOLD = 0.001;

	double threshold = Double.NaN;
		
	@Override
	public Type getType() {
		return Type.BOOLEAN;
	}

	@Override
	public IState resolve(IState target, IContextualizationScope context) throws KlabException {

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
	public Object eval(IContextualizationScope context, Object...parameters) throws KlabException {
		StreamNetworkResolver ret = new StreamNetworkResolver();
		ret.threshold = Parameters.create(parameters).get("tca.threshold", Double.NaN);
		return ret;
	}
}
