package org.integratedmodelling.geoprocessing.hydrology;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
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

        IState infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);
        IState rainfallVolumeState = context.getArtifact("rainfall_volume", IState.class);
        IState streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        IState flowdirectionState = context.getArtifact("flow_directions_d8", IState.class);

        if (objsNotNull(infiltratedWaterVolumeState, rainfallVolumeState, streamPresenceState, flowdirectionState)) {

            Grid infGrid = Space.extractGrid(infiltratedWaterVolumeState);
            Grid rainGrid = Space.extractGrid(rainfallVolumeState);
            Grid streamGrid = Space.extractGrid(streamPresenceState);
            Grid flowGrid = Space.extractGrid(flowdirectionState);

            if (!objsNotNull(infGrid, rainGrid, streamGrid, flowGrid)) {
                throw new KlabValidationException("Input states must be computed on a grid extent");
            }

            IScale locator = context.getScale();

            // DUMMY TEST

            // iterate over the grid
            long xCells = infGrid.getXCells();
            long yCells = infGrid.getYCells();
            for(int y = 0; y < yCells; y++) {
                for(int x = 0; x < xCells; x++) {
                    ILocator spl = locator.at(ISpace.class, x, y);

                    Double infiltrated = infiltratedWaterVolumeState.get(spl, Double.class);
                    Double rain = rainfallVolumeState.get(spl, Double.class);
                    Double stream = streamPresenceState.get(spl, Double.class);
                    Double flow = flowdirectionState.get(spl, Double.class);

                    boolean isValid = Observations.INSTANCE.isData(infiltrated);
                    if (isValid) {
                        infiltrated += rain + stream + flow;
                    } else {
                        infiltrated = 0.0;
                    }
                    infiltratedWaterVolumeState.set(spl, infiltrated);
                }
            }

        }

        // DUMMY PLACEHOLDER OPERATION
        context.getMonitor().info("Processing Infiltrated Volume the dummy way. Just a placeholder.");

        return infiltratedProcess;
    }

    private boolean objsNotNull(Object... objs) {
        for(Object obj : objs) {
            if (obj == null) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object eval(IParameters<String> parameters, IContextualizationScope context) throws KlabException {
        return new InfiltratedWaterVolumeResolver();
    }

}
