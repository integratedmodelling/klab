package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.hortonmachine.hmachine.modules.geomorphology.tca.OmsTca;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IComputationContext;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Utils;

public class FlowAccumulationResolver implements IResolver<IState>, IExpression {

	boolean cells = false;

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

		IState flowDir = context.getArtifact("flow_directions_d8", IState.class);
		IUnit tUnit = target.getObservable().getUnit();
		Grid grid = Space.extractGrid(target);
		
		if (grid == null) {
			throw new KlabValidationException("Flow accumulation must be computed on a grid extent");
		}
		
		double cellArea = grid.getCell(0).getStandardizedArea();
		if (tUnit != null && tUnit.equals(Units.INSTANCE.SQUARE_METERS)) {
			tUnit = null;
		}
		
		OmsTca algorithm = new OmsTca();
		algorithm.inFlow = GeotoolsUtils.INSTANCE.stateToCoverage(flowDir, DataBuffer.TYPE_FLOAT, floatNovalue);
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
			final IUnit unit = tUnit;
			GeotoolsUtils.INSTANCE.coverageToState(algorithm.outTca, target, (a) -> {
				if (a == (double) floatNovalue) {
					return Double.NaN;
				}
				if (cells) {
					return a;
				}
				return unit == null ? (a*cellArea) : unit.convert(a*cellArea, Units.INSTANCE.SQUARE_METERS).doubleValue();
			});
		}
		return target;
	}

	public double toAngle(double code) {
		if (Double.isNaN(code)) {
			return code;
		}
		int exp = Utils.log2int((int) code);
		return 45.0 * (exp > 5 ? exp - 6 : exp + 2);
	}

	@Override
	public Object eval(IParameters<String> parameters, IComputationContext context) throws KlabException {
		FlowAccumulationResolver ret = new FlowAccumulationResolver();
		ret.cells = parameters.get("cells", Boolean.FALSE);
		return ret;
	}
}
