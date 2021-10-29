package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.geotools.coverage.grid.GridCoverage2D;
import org.hortonmachine.gears.modules.r.cutout.OmsCutOut;
import org.hortonmachine.hmachine.modules.hydrogeomorphology.infiltration.OmsInfiltratedWaterVolume;
import org.integratedmodelling.geoprocessing.TaskMonitor;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;

public class InfiltratedWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    /**
     * Fraction of upslope available recharge (upgradient subsidy) that is available
     * for month m or for the selected reference interval.
     */
    double alpha = 1.0;

    /**
     * (spatial availability parameter) the fraction of the upgradient subsidy that
     * is available for downgradient evapotranspiration, it is based on local
     * topography and geology
     */
    double beta = 1.0;

    /**
     * the fraction of pixel recharge that is available to downgradient pixels,
     * represents what extent local recharge enters a local groundwater system and
     * might be used again as oppose to entering a deeper groundwater system
     */
    double gamma = 1.0;

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess infiltratedProcess, IContextualizationScope context) throws KlabException {
        IState petState = context.getArtifact("potential_evapotranspired_water_volume", IState.class);
        IState rainfallVolumeState = context.getArtifact("rainfall_volume", IState.class);
        IState runoffVolumeState = context.getArtifact("runoff_water_volume", IState.class);
        IState streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        IState flowdirectionState = context.getArtifact("flow_directions_d8", IState.class);

        GeotoolsUtils.INSTANCE.dumpToRaster(context, "Infiltration", petState, rainfallVolumeState, runoffVolumeState,
                streamPresenceState, flowdirectionState);

        IState netInfiltratedWaterVolumeState = context.getArtifact("net_infiltrated_water_volume", IState.class);
        IState infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);

        TaskMonitor taskMonitor = new TaskMonitor(context.getMonitor());
        taskMonitor.setTaskName("Infiltration");

        if (petState != null && rainfallVolumeState != null && flowdirectionState != null && streamPresenceState != null
                && runoffVolumeState != null) {

            OmsInfiltratedWaterVolume v = new OmsInfiltratedWaterVolume();
            try {
                GridCoverage2D flowGC = getGridCoverage(context, flowdirectionState, null);
                v.pm = taskMonitor;
                v.pAlpha = alpha;
                v.inPet = getGridCoverage(context, petState, flowGC);
                v.inFlowdirections = flowGC;
                v.inNet = getGridCoverage(context, streamPresenceState, null);
                v.inRainfall = getGridCoverage(context, rainfallVolumeState, flowGC);
                v.inRunoff = getGridCoverage(context, runoffVolumeState, flowGC);
                v.process();
            } catch (Exception e) {
                throw new KlabException(e);
            }
            if (!context.getMonitor().isInterrupted()) {
                // NOTE: also AET and LSUM maps are produced, but not passed as process output,
                // since it is not defined in the semantics.

                GeotoolsUtils.INSTANCE.coverageToState(v.outInfiltration, infiltratedWaterVolumeState, context.getScale(), null);
                GeotoolsUtils.INSTANCE.coverageToState(v.outNetInfiltration, netInfiltratedWaterVolumeState, context.getScale(),
                        null);
            }
            GeotoolsUtils.INSTANCE.dumpToRaster(context, "Infiltration", netInfiltratedWaterVolumeState,
                    infiltratedWaterVolumeState);
        } else {
            taskMonitor.errorMessage("Can't proceed with null input maps.");
        }

        return infiltratedProcess;
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
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        InfiltratedWaterVolumeResolver inf = new InfiltratedWaterVolumeResolver();
        inf.alpha = parameters.get("alhpa", 1.0);
        inf.beta = parameters.get("beta", 1.0);
        inf.gamma = parameters.get("gamma", 1.0);
        return inf;
    }

}
