package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;

public class InfiltratedWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    String outputId = "infiltrated_water_volume";

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess infiltratedProcess, IContextualizationScope context) throws KlabException {

//        IState infiltratedState = null;
//
//        for(int i = 1; i < context.getModel().getObservables().size(); i++) {
//
//            if (outputId.equals(context.getModel().getObservables().get(i).getName())) {
//                IState state = context.getArtifact(outputId, IState.class);
//                if (state == null) {
//                    context.getMonitor().warn("cannot find state for " + outputId);
//                } else {
//                    infiltratedState = state;
//                }
//                break;
//            }
//        }
//
//        if (infiltratedState == null) {
//            return infiltratedProcess;
//        }
//
//        Grid grid = Space.extractGrid(infiltratedState);
//
//        if (grid == null) {
//            throw new KlabValidationException("Infiltrated volume must be computed on a grid extent");
//        }

        // DUMMY PLACEHOLDER OPERATION
        context.getMonitor().info("Processing Infiltrated Volume the dummy way. Just a placeholder.");

        return infiltratedProcess;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new InfiltratedWaterVolumeResolver();
    }

}
