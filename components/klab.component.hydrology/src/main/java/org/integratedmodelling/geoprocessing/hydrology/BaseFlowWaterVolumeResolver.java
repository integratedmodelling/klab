package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;

import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class BaseFlowWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    String outputId = "baseflow_water_volume";

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess baseflowProcess, IContextualizationScope context) throws KlabException {

        IState infiltratedWaterVolume = context.getArtifact("infiltrated_water_volume", IState.class);
        GridCoverage2D infiltratedWaterVolumeGC = GeotoolsUtils.INSTANCE.stateToCoverage(infiltratedWaterVolume,
                context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);
        context.getMonitor().info("Got infiltrated:  " + infiltratedWaterVolumeGC);

//        IState baseflowState = null;
//
//        for(int i = 1; i < context.getModel().getObservables().size(); i++) {
//
//            if (outputId.equals(context.getModel().getObservables().get(i).getName())) {
//                IState state = context.getArtifact(outputId, IState.class);
//                if (state == null) {
//                    context.getMonitor().warn("cannot find state for " + outputId);
//                } else {
//                    baseflowState = state;
//                }
//                break;
//            }
//        }
//
//        if (baseflowState == null) {
//            return baseflowProcess;
//        }
//
//        Grid grid = Space.extractGrid(baseflowState);
//
//        if (grid == null) {
//            throw new KlabValidationException("Baseflow must be computed on a grid extent");
//        }

        // DUMMY PLACEHOLDER OPERATION
        context.getMonitor().info("Processing Baseflow the dummy way. Just a placeholder.");

        return baseflowProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new BaseFlowWaterVolumeResolver();
    }

}
