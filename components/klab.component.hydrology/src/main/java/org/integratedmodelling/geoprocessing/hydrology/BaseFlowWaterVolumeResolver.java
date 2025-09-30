package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.gears.modules.r.cutout.OmsCutOut;
import org.hortonmachine.hmachine.modules.hydrogeomorphology.swy.OmsBaseflowWaterVolume;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.utils.NumberUtils;

public class BaseFlowWaterVolumeResolver extends AbstractContextualizer implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess baseflowProcess, IContextualizationScope context) throws KlabException {

        IState netInfiltratedWaterVolumeState = getInput("net_infiltrated_water_volume", IState.class);
        IState infiltratedWaterVolumeState = getInput("infiltrated_water_volume", IState.class);
        IState streamPresenceState = getInput("presence_of_stream", IState.class);
        IState flowdirectionState = getInput("flow_directions_d8", IState.class);

        GeotoolsUtils.INSTANCE.dumpToRaster(context, "Baseflow", netInfiltratedWaterVolumeState, infiltratedWaterVolumeState,
                streamPresenceState, flowdirectionState);

        IState baseflowWaterVolumeState = getOutput("base_flow_water_volume", IState.class);
        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Baseflow");
        OmsBaseflowWaterVolume b = new OmsBaseflowWaterVolume();
        try {
            GridCoverage2D flowGc = getGridCoverage(context, flowdirectionState, null);

            b.pm = taskMonitor;
            b.inInfiltration = getGridCoverage(context, infiltratedWaterVolumeState, flowGc);
            b.inNetInfiltration = getGridCoverage(context, netInfiltratedWaterVolumeState, flowGc);
            b.inNet = getGridCoverage(context, streamPresenceState, flowGc);
            b.inFlowdirections = flowGc;
            b.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            // NOTE: also VRI, B and LSUM maps are produced, but not passed as process
            // output,
            // since it is not defined in the semantics.

            GeotoolsUtils.INSTANCE.coverageToState(b.outB, baseflowWaterVolumeState, context.getScale(),
                    (val) -> NumberUtils.equal(val, -9999) ? Double.NaN : val);
        }

        GeotoolsUtils.INSTANCE.dumpToRaster(context, "Baseflow", baseflowWaterVolumeState);

        return baseflowProcess;
    }

    private GridCoverage2D getGridCoverage(IContextualizationScope context, IState state, GridCoverage2D flowGC)
            throws Exception {
        if (state == null) {
            return null;
        }
        GridCoverage2D gc = GeotoolsUtils.INSTANCE.stateToCoverage(state, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue,
                false);

        if (flowGC != null) {
            OmsCutOut co = new OmsCutOut();
            co.inMask = flowGC;
            co.inRaster = gc;
            co.process();
            return co.outRaster;
        } else {
            return gc;
        }
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new BaseFlowWaterVolumeResolver();
    }

}
