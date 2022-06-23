package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.hmachine.modules.geomorphology.flow.OmsLeastCostFlowDirections;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.Parameters;

public class LeastCostFlowDirectionsResolver extends AbstractContextualizer implements IResolver<IState>, IExpression {

	boolean computeAngles = false;
	boolean doTca = false;
	boolean areas = false;

	@Override
	public Type getType() {
		return Type.NUMBER;
	}

	@Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {
    	
        IState dem = context.getArtifact("elevation", IState.class);
        
        double cellArea = 1;
        if (context.getScale().getSpace() instanceof Space && ((Space)context.getScale().getSpace()).getGrid() != null) {
        	cellArea = ((Space)context.getScale().getSpace()).getGrid().getCell(0l).getStandardizedArea();
        	if (target.getObservable().getUnit() != null) {
        		cellArea = target.getObservable().getUnit().convert(cellArea, Units.INSTANCE.SQUARE_METERS).doubleValue();
        	}
        }
        
        // really, the compiler should do this.
        final double carea = cellArea;
        
        OmsLeastCostFlowDirections algorithm = new OmsLeastCostFlowDirections();
        algorithm.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);
        algorithm.doAspect = false;
        algorithm.doSlope = false;
        algorithm.doTca = doTca;
        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Least cost d8");
        algorithm.pm = taskMonitor;
        algorithm.doProcess = true;
        algorithm.doReset = false;
        
        context.getMonitor().info("computing " + (doTca ? "total contributing area" : "flow directions") + "...");
        try {
            algorithm.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            GridCoverage2D outCoverage = doTca ? algorithm.outTca : algorithm.outFlow;
            Function<Double, Double> transformation = /* doTca ? null : */ (a) -> {
                if (a == (double) floatNovalue) {
                    return Double.NaN;
                }
                if (!doTca && computeAngles) {
                    return toAngle(a);
                }
                if (doTca && areas) {
                	return a.doubleValue() * carea;
                }
                return a;
            };
            GeotoolsUtils.INSTANCE.coverageToState(outCoverage, target, context.getScale(), transformation);
        }
        return target;
    }

	public double toAngle(double code) {
		if (Double.isNaN(code)) {
			return code;
		}
		return Geospace.getHeading((int) code);
	}

	@Override
	public Object eval(IContextualizationScope context, Object...params) throws KlabException {
	    Parameters<String> parameters = Parameters.create(params);
	    LeastCostFlowDirectionsResolver ret = new LeastCostFlowDirectionsResolver();
		ret.computeAngles = parameters.get("angles", Boolean.FALSE);
		ret.doTca = parameters.get("dotca", Boolean.FALSE);
		ret.areas = parameters.get("areas", Boolean.FALSE);
		return ret;
	}
}
