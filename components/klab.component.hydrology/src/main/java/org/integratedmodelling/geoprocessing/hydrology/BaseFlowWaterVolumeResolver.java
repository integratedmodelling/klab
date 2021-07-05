package org.integratedmodelling.geoprocessing.hydrology;

import java.util.ArrayList;
import java.util.List;

import org.hortonmachine.gears.utils.time.UtcTimeUtilities;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.model.contextualization.IResolver;
import org.integratedmodelling.klab.api.observations.IProcess;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;

public class BaseFlowWaterVolumeResolver implements IResolver<IProcess>, IExpression {

    private IState netInfiltratedWaterVolumeState;
    private IState infiltratedWaterVolumeState;
    private IState streamPresenceState;
    private IState flowdirectionState;
    private IState baseflowWaterVolumeState;

    @Override
    public Type getType() {
        return Type.PROCESS;
    }

    @Override
    public IProcess resolve(IProcess baseflowProcess, IContextualizationScope context) throws KlabException {
        if (Configuration.INSTANCE.isEchoEnabled()) {
            ITime time = context.getScale().getTime();
            String start = UtcTimeUtilities.toStringWithMinutes(new DateTime( time.getStart().getMilliseconds()));
            String end = UtcTimeUtilities.toStringWithMinutes(new DateTime( time.getEnd().getMilliseconds()));
            System.out.println("Enter BaseFlowWaterVolumeResolver at timestep : " + start + " -> " + end);
        }
        netInfiltratedWaterVolumeState = context.getArtifact("net_infiltrated_water_volume", IState.class);
        infiltratedWaterVolumeState = context.getArtifact("infiltrated_water_volume", IState.class);
        streamPresenceState = context.getArtifact("presence_of_stream", IState.class);
        flowdirectionState = context.getArtifact("flow_directions_d8", IState.class);

        baseflowWaterVolumeState = context.getArtifact("base_flow_water_volume", IState.class);

        if (objsNotNull(infiltratedWaterVolumeState, streamPresenceState, flowdirectionState)) {

            Grid grid = Space.extractGrid(infiltratedWaterVolumeState.getScale());

            if (!objsNotNull(grid)) {
                throw new KlabValidationException("Input states must be computed on a grid extent");
            }

            // first collect all basin exits, being it real basins
            // or just cells with no more downstream cells
            List<ILocator> exitCells = new ArrayList<>();
            List<ILocator> sourceCells = new ArrayList<>();
            long xCells = grid.getXCells();
            long yCells = grid.getYCells();
            
            for(int y = 0; y < yCells; y++) {
                for(int x = 0; x < xCells; x++) {
                    Cell cell = grid.getCell(x, y);
                    Pair<Cell, Orientation> downstreamCellWithOrientation = Geospace.getDownstreamCellWithOrientation(cell,
                            flowdirectionState);
                    if (downstreamCellWithOrientation == null || downstreamCellWithOrientation.getFirst() == null) {
                        exitCells.add(context.getScale().at(cell));
                    }
                    // get source cells
                    List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirectionState, null);
                    if (upstreamCells.isEmpty()) {
                        sourceCells.add(context.getScale().at(cell));
                    }
                }
            }

            // calculate matrix of cumulated infiltration
            double[][] lSumMatrix = new double[(int) yCells][(int) xCells];
            calculateLsumMatrix(sourceCells, context, lSumMatrix);

            // calculate matrix of cumulated baseflow
            double[][] bSumMatrix = new double[(int) yCells][(int) xCells];
            for(ILocator exitCell : exitCells) {
                walkUpAndProcess(exitCell, context, bSumMatrix, lSumMatrix);
            }
        }
        if (Configuration.INSTANCE.isEchoEnabled()) {
            System.out.println("Exit BaseFlowWaterVolumeResolver.");
        }
        return baseflowProcess;
    }

    private void calculateLsumMatrix(List<ILocator> sourceCells, IContextualizationScope scope, double[][] lSumMatrix) {
        for(ILocator locator : sourceCells) {
        	
        	Cell sourceCell = locator.as(Cell.class);
            Double li = infiltratedWaterVolumeState.get(sourceCell, Double.class);
            int x = (int) sourceCell.getX();
            int y = (int) sourceCell.getY();
            lSumMatrix[y][x] = li; // no upstream contribution

            // go downstream
            Pair<Cell, Orientation> downCell = Geospace.getDownstreamCellWithOrientation(sourceCell, flowdirectionState);
            while (downCell != null) {
                Cell cell = downCell.getFirst();

                List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirectionState, null);
                // check if all upstream have a value
                boolean canProcess = true;
                for(Cell upstreamCell : upstreamCells) {
                    Double upstreamLi = infiltratedWaterVolumeState.get(scope.getScale().at(upstreamCell), Double.class);
                    if (!Observations.INSTANCE.isData(upstreamLi)) {
                        // stop, we still need the other upstream values
                        canProcess = false;
                        break;
                    }
                }

                if (canProcess) {


                	Double currentCellLi = infiltratedWaterVolumeState.get(locator, Double.class);

                    if (Observations.INSTANCE.isData(currentCellLi)) {

                        double lSumCurrentCell = 0.0;
                        for(Cell upstreamCell : upstreamCells) {
                            int subX = (int) upstreamCell.getX();
                            int subY = (int) upstreamCell.getY();

                            lSumCurrentCell += lSumMatrix[subY][subX];
                        }
                        lSumMatrix[y][x] = currentCellLi + lSumCurrentCell;
                    }
                    downCell = Geospace.getDownstreamCellWithOrientation(cell, flowdirectionState);
                } else {
                    break;
                }
            }
        }
    }

    // ACHTUNG must pass locator
    private void walkUpAndProcess(ILocator locator, IContextualizationScope scope, double[][] bSumMatrix, double[][] lSumMatrix) {
        // process current cell
    	Cell cell = locator.as(Cell.class);
        Boolean isStream = streamPresenceState.get(locator, Boolean.class);

        double bSum = 0.0;
        int x = (int) cell.getX();
        int y = (int) cell.getY();

        Pair<Cell, Orientation> downCellPair = Geospace.getDownstreamCellWithOrientation(cell, flowdirectionState);
        if (downCellPair == null || isStream) {
            // is it is and outlet or on the stream
            bSum = lSumMatrix[y][x];
        } else {
            Cell downstreamCell = downCellPair.getFirst();
            int downY = (int) downstreamCell.getY();
            int downX = (int) downstreamCell.getX();
            double downBSum = bSumMatrix[downY][downX];
            double downLSum = lSumMatrix[downY][downX];
            Double downLi = infiltratedWaterVolumeState.get(scope.getScale().at(downstreamCell), Double.class);
            Double downLAvailable = netInfiltratedWaterVolumeState.get(scope.getScale().at(downstreamCell), Double.class);

            if (downLSum != 0 && downLSum - downLi != 0) {
                bSum = lSumMatrix[y][x] * (1 - (downLAvailable / downLSum) * downBSum / (downLSum - downLi));
            } else {
                bSum = lSumMatrix[y][x];
            }
        }
        bSumMatrix[y][x] = bSum;
        baseflowWaterVolumeState.set(locator, bSum);

        // recursively move to upstream cells
        List<Cell> upstreamCells = Geospace.getUpstreamCells(cell, flowdirectionState, null);
        for(Cell upCell : upstreamCells) {
            walkUpAndProcess(scope.getScale().at(upCell), scope, bSumMatrix, lSumMatrix);
        }
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
        return new BaseFlowWaterVolumeResolver();
    }

}
