package org.integratedmodelling.geoprocessing.morphology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import org.hortonmachine.hmachine.modules.geomorphology.geomorphon.OmsGeomorphon;
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

public class GeomorphonResolver implements IResolver<IState>, IExpression {

	double pRadius = Double.NaN;
	double pThreshold = Double.NaN;

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

		OmsGeomorphon geomorphon = new OmsGeomorphon();
		if (!Double.isNaN(pRadius)) {
			geomorphon.pRadius = pRadius;
		}
		if (!Double.isNaN(pThreshold)) {
			geomorphon.pThreshold = pThreshold;
		}
		geomorphon.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, floatNovalue);
		geomorphon.pm = new TaskMonitor(context.getMonitor());
		geomorphon.doProcess = true;
		geomorphon.doReset = false;
		context.getMonitor().info("Running geomorphon algorithm...");
		try {
			geomorphon.process();
		} catch (Exception e) {
			throw new KlabException(e);
		}
		if (!context.getMonitor().isInterrupted()) {
			GeotoolsUtils.INSTANCE.coverageToState(geomorphon.outRaster, target, (a) -> {
				if (a == (double) floatNovalue)
					return Double.NaN;
				return a;
			});
		}
		return target;
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		GeomorphonResolver ret = new GeomorphonResolver();
		ret.pRadius = parameters.get("radius", Double.NaN);
		ret.pThreshold = parameters.get("threshold", Double.NaN);
		return ret;
	}

}
