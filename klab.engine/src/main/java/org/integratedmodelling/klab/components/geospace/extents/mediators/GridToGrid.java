package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Pair;

public class GridToGrid implements IScaleMediator {

    Grid from;
    Grid to;
    boolean identity = false;

    public GridToGrid(Grid from, Grid to) {
        this.from = from;
        this.to = to;
        this.identity = from.equals(to);
    }

    @Override
    public boolean isConformant() {
        return true;
    }

    @Override
    public long mapConformant(long offset) {
        if (identity) {
            return offset;
        }
        Cell target = to.getCellAt(from.getCell(offset).getCenter(), false);
        return target == null ? -1 : target.getOffsetInGrid();
    }

    @Override
    public Collection<Pair<Long, Double>> map(long originalOffset) {
        throw new KlabUnimplementedException("grid2grid mediator: non-subgrid conformant grid: not implemented");
    }

}
