package org.integratedmodelling.geoprocessing.hydrology;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;

public class InfiltratedWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    /**
     * Fraction of upslope available recharge (upgradient subsidy) that
     * is available for month m or for the selected reference interval.
     */
    double alpha = 1.0;

    /**
     * (spatial availability parameter) the fraction of the upgradient 
     * subsidy that is available for downgradient evapotranspiration, 
     * it is based on local topography and geology
     */
    double beta = 1.0;

    /**
     * the fraction of pixel recharge that is available to downgradient pixels, 
     * represents what extent local recharge enters a local groundwater system 
     * and might be used again as oppose to entering a deeper groundwater system
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

        IState infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);

        if (objsNotNull(rainfallVolumeState, streamPresenceState, flowdirectionState)) {

            Grid runoffGrid = Space.extractGrid(runoffVolumeState);
            Grid rainGrid = Space.extractGrid(rainfallVolumeState);
            Grid streamGrid = Space.extractGrid(streamPresenceState);
            Grid flowGrid = Space.extractGrid(flowdirectionState);
            Grid petGrid = Space.extractGrid(petState);

            if (!objsNotNull(runoffGrid, rainGrid, rainGrid, streamGrid, flowGrid, petGrid)) {
                throw new KlabValidationException("Input states must be computed on a grid extent");
            }

            IScale locator = context.getScale();

            // First collect a list of source cells
            List<Cell> sourceCells = new ArrayList<>();
            long xCells = rainGrid.getXCells();
            long yCells = rainGrid.getYCells();
            for(int y = 0; y < yCells; y++) {
                for(int x = 0; x < xCells; x++) {
                    Cell cell = locator.as(Cell.class);
                    List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirectionState, null);
                    if (upstreamCells.isEmpty()) {
                        sourceCells.add(cell);
                    }
                }
            }
            double[][] lSumAvailableMatrix = new double[(int) yCells][(int) xCells];

            for(Cell sourceCell : sourceCells) {

                double lSumAvailable = 0.0;

                Double pet = petState.get(sourceCell, Double.class);
                Double rain = rainfallVolumeState.get(sourceCell, Double.class);
                Double runoff = runoffVolumeState.get(sourceCell, Double.class);
                Boolean isStream = streamPresenceState.get(sourceCell, Boolean.class);

                lSumAvailableMatrix[(int) sourceCell.getY()][(int) sourceCell.getX()] = lSumAvailable;

                if (Observations.INSTANCE.isData(pet) && Observations.INSTANCE.isData(rain)
                        && Observations.INSTANCE.isData(runoff) && Observations.INSTANCE.isData(isStream)) {

                    double lAvailable = calculateLAvailable(lSumAvailable, pet, rain, runoff, isStream);
                    infiltratedWaterVolumeState.set(sourceCell, lAvailable);

                    // go downstream
                    Pair<Cell, Orientation> downCell = Geospace.getDownstreamCellWithOrientation(sourceCell, flowdirectionState);
                    while (downCell != null) {
                        Cell cell = downCell.getFirst();

                        List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirectionState, null);
                        // check if all upstream have a value
                        boolean canProcess = true;
                        for(Cell upstreamCell : upstreamCells) {
                            Double upstreamLAvailable = infiltratedWaterVolumeState.get(upstreamCell, Double.class);
                            if (!Observations.INSTANCE.isData(upstreamLAvailable)) {
                                // stop, we still need the other upstream values
                                canProcess = false;
                                break;
                            }
                        }

                        if (canProcess) {

                            pet = petState.get(cell, Double.class);
                            rain = rainfallVolumeState.get(cell, Double.class);
                            runoff = runoffVolumeState.get(cell, Double.class);
                            isStream = streamPresenceState.get(cell, Boolean.class);

                            if (Observations.INSTANCE.isData(pet) && Observations.INSTANCE.isData(rain)
                                    && Observations.INSTANCE.isData(runoff) && Observations.INSTANCE.isData(isStream)) {

                                double lAvailableUpstream = 0.0;
                                double lSumAvailableUpstream = 0.0;
                                for(Cell upstreamCell : upstreamCells) {
                                    Double upstreamLAvailable = infiltratedWaterVolumeState.get(upstreamCell, Double.class);

                                    int x = (int) upstreamCell.getX();
                                    int y = (int) upstreamCell.getY();

                                    lSumAvailableUpstream += lSumAvailableMatrix[y][x];
                                    lAvailableUpstream += upstreamLAvailable;
                                }
                                double lSumAvailableCurrentCell = lSumAvailableUpstream + lAvailableUpstream;

                                lSumAvailableMatrix[(int) cell.getY()][(int) cell.getX()] = lSumAvailableCurrentCell;

                                double lAvailableCurrentCell = calculateLAvailable(lSumAvailableCurrentCell, pet, rain, runoff,
                                        isStream);

                                infiltratedWaterVolumeState.set(cell, lAvailableCurrentCell);
                            }

                            downCell = Geospace.getDownstreamCellWithOrientation(cell, flowdirectionState);
                        } else {
                            break;
                        }
                    }

                }

            }

        }

//        boolean isValid = Observations.INSTANCE.isData(infiltrated);
//        if (isValid) {
//            infiltrated += rain + stream + flow;
//        } else {
//            infiltrated = 0.0;
//        }
//        infiltratedWaterVolumeState.set(spl, infiltrated);

        // DUMMY PLACEHOLDER OPERATION
        context.getMonitor().info("Processing Infiltrated Volume the dummy way. Just a placeholder.");

        return infiltratedProcess;
    }

    private double calculateLAvailable(double lSumAvailable, Double pet, Double rain, Double runoff, Boolean isStream) {
        double aet = 0;
        if (!isStream) {
            aet = Math.min(pet, rain - runoff + alpha * beta * lSumAvailable);
        }

        double li = rain - runoff - aet;
        double lAvailable = Math.min(gamma * li, li); // TODO Silli check

        return lAvailable;
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
        InfiltratedWaterVolumeResolver inf = new InfiltratedWaterVolumeResolver();
        inf.alpha = parameters.get("alhpa", 1.0);
        inf.beta = parameters.get("beta", 1.0);
        inf.gamma = parameters.get("gamma", 1.0);
        return inf;
    }

}
