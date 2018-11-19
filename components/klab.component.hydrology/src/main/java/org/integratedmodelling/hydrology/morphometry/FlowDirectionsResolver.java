package org.integratedmodelling.hydrology.morphometry;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import org.hortonmachine.hmachine.modules.geomorphology.flow.OmsFlowDirections;
import org.integratedmodelling.hydrology.TaskMonitor;
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
import org.integratedmodelling.klab.utils.Utils;

public class FlowDirectionsResolver implements IResolver<IState>, IExpression {

	boolean computeAngles = false;
	
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

		IState dem = context.getArtifact("hydrologically_corrected_elevation", IState.class);

		OmsFlowDirections algorithm = new OmsFlowDirections();
		algorithm.inPit = GeotoolsUtils.INSTANCE.stateToCoverage(dem, floatNovalue);
		algorithm.pm = new TaskMonitor(context.getMonitor());
		algorithm.doProcess = true;
		algorithm.doReset = false;
		context.getMonitor().info("computing flow directions...");
		try {
			algorithm.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}
		if (!context.getMonitor().isInterrupted()) {
			GeotoolsUtils.INSTANCE.coverageToState(algorithm.outFlow, target, (a) -> {
				if (a == (double) floatNovalue) {
					return Double.NaN;
				}
				if (computeAngles) {
					return toAngle(a);
				}
				return a;
			});
		}
		return target;
	}

	public double toAngle(double code) {
		if (Double.isNaN(code)) {
			return code;
		}
		int exp = Utils.log2int((int)code); 
		return 45.0 * (exp > 5 ? exp - 6 : exp + 2);
	}
	
	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		FlowDirectionsResolver ret = new FlowDirectionsResolver();
		ret.computeAngles = parameters.get("angles", Boolean.FALSE);
		return ret;
	}
}
