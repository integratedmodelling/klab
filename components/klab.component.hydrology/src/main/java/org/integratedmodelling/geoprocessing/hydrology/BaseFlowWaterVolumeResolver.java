package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.gears.utils.time.UtcTimeUtilities;
import org.hortonmachine.hmachine.modules.hydrogeomorphology.baseflow.OmsBaseflowWaterVolume;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.joda.time.DateTime;

public class BaseFlowWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess baseflowProcess, IContextualizationScope context) throws KlabException {
        IState netInfiltratedWaterVolumeState = context.getArtifact("net_infiltrated_water_volume", IState.class);
        IState infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);
        IState streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        IState flowdirectionState = context.getArtifact("flow_directions_d8", IState.class);

        IState baseflowWaterVolumeState = context.getArtifact("base_flow_water_volume", IState.class);

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Baseflow");
        OmsBaseflowWaterVolume b = new OmsBaseflowWaterVolume();
        b.pm = taskMonitor;
        b.inInfiltration = getGridCoverage(context, infiltratedWaterVolumeState);
        b.inNetInfiltration = getGridCoverage(context, netInfiltratedWaterVolumeState);
        b.inNet = getGridCoverage(context, streamPresenceState);
        b.inFlowdirections = getGridCoverage(context, flowdirectionState);
        try {
            b.process();
        } catch (Exception e) {
            throw new KlabException(e);
        }
        if (!context.getMonitor().isInterrupted()) {
            // NOTE: also VRI, B and LSUM maps are produced, but not passed as process output,
            // since it is not defined in the semantics.

            GeotoolsUtils.INSTANCE.coverageToState(b.outBaseflow, baseflowWaterVolumeState, context.getScale(), null);
        }

        return baseflowProcess;
    }

    private GridCoverage2D getGridCoverage(IContextualizationScope context, IState state) {
        if (state == null) {
            return null;
        }
        return GeotoolsUtils.INSTANCE.stateToCoverage(state, context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new BaseFlowWaterVolumeResolver();
    }

}
