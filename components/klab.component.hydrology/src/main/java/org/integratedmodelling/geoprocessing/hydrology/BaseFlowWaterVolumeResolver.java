package org.integratedmodelling.geoprocessing.hydrology;

import static org.hortonmachine.gears.libs.modules.HMConstants.floatNovalue;

import java.awt.image.DataBuffer;
import java.util.List;

import org.geotools.coverage.grid.GridCoordinates2D;
import org.geotools.coverage.grid.GridCoverage2D;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
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

        IState baseflowVolumeState = context.getArtifact("base_flow_water_volume", IState.class);
        IState infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);

        Grid baseflowGrid = Space.extractGrid(baseflowVolumeState);
        if (baseflowGrid == null) {
            throw new KlabValidationException("Baseflow must be computed on a grid extent");
        }
        for(ILocator locator : context.getScale()) {
//          locator.as(null) -> cell

            for(int i = 0; i < baseflowGrid.getCellCount(); i++) {
                long[] xy = baseflowGrid.getXYOffsets(i);
                Double value = itera.getSampleDouble((int) xy[0], (int) xy[1], 0);
                ILocator spl = locator.at(ISpace.class, xy[0], xy[1]);
                if (transformation != null) {
                    value = transformation.apply(value);
                }
                if (coordinateChecker != null) {
                    if (!coordinateChecker.apply(xy)) {
                        value = Double.NaN;
                    }
                }
                for(ILocator spp : spl) {
                    state.set(spp, value);
                }
            }
        }

        GridCoverage2D infiltratedWaterVolumeGC = GeotoolsUtils.INSTANCE.stateToCoverage(infiltratedWaterVolumeState,
                context.getScale(), DataBuffer.TYPE_FLOAT, floatNovalue, false);

        double[] values = new double[1];
        infiltratedWaterVolumeGC.evaluate(new GridCoordinates2D(0, 0), values);
        context.getMonitor().info("Got infiltrated with value in 0,0:  " + values[0]);

//        IState baseflowState = null;
//
//        IModel model = context.getModel();
//        List<IObservable> observables = model.getObservables();
//        for(int i = 1; i < observables.size(); i++) {
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
