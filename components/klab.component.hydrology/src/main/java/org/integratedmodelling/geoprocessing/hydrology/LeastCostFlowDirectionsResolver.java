package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.function.Function;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.hmachine.modules.geomorphology.flow.OmsLeastCostFlowDirections;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;

public class LeastCostFlowDirectionsResolver implements IResolver<IState>, IExpression {

    boolean computeAngles = false;
    boolean doTca = false;

    @Override
    public Type getType() {
        return Type.NUMBER;
    }

    @Override
    public IState resolve(IState target, IContextualizationScope context) throws KlabException {

        IState dem = context.getArtifact("elevation", IState.class);

        OmsLeastCostFlowDirections algorithm = new OmsLeastCostFlowDirections();
        algorithm.inElev = GeotoolsUtils.INSTANCE.stateToCoverage(dem, context.getScale(), DataBuffer.TYPE_INT, floatNovalue,
                false);
        algorithm.doAspect = false;
        algorithm.doSlope = false;
        algorithm.doTca = doTca;

        algorithm.pm = new TaskMonitor(context.getMonitor());
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
            Function<Double, Double> transformation = doTca ? null : (a) -> {
                if (a == (double) floatNovalue) {
                    return Double.NaN;
                }
                if (computeAngles) {
                    return toAngle(a);
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
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        LeastCostFlowDirectionsResolver ret = new LeastCostFlowDirectionsResolver();
        ret.computeAngles = parameters.get("angles", Boolean.FALSE);
        ret.doTca = parameters.get("dotca", Boolean.FALSE);
        return ret;
    }
}
