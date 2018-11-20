package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.demmanipulation.pitfiller.OmsPitfiller;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;

public class HydrologicallyCorrectedElevationResolver implements IResolver<IState>, IExpression {

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

		IState dem = context.getArtifact("elevation", IState.class);

		OmsPitfiller pitfiller = new OmsPitfiller();
		pitfiller.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, DataBuffer.TYPE_FLOAT, floatNovalue);
		pitfiller.pm = new TaskMonitor(context.getMonitor());
		pitfiller.doProcess = true;
		pitfiller.doReset = false;
		context.getMonitor().info("filling hydrological sinks...");
		try {
			pitfiller.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}
		if (!context.getMonitor().isInterrupted()) {
			GeotoolsUtils.INSTANCE.coverageToState(pitfiller.outPit, target, (a) -> {
				if (a == (double) floatNovalue)
					return Double.NaN;
				return a;
			});
		}
		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		return new HydrologicallyCorrectedElevationResolver();
	}

}
