package org.integratedmodelling.klab.components.geospace.extents.mediators;

import java.util.Collection;

import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.utils.Pair;

public class GridToGrid implements IScaleMediator {

    boolean conformant = false;
    boolean identity = false;
    Grid from;
    Grid to;

    public GridToGrid(Grid from, Grid to) {

        this.from = from;
        this.to = to;

        if (from.equals(to)) {
            identity = true;
            conformant = true;
        } else if (from instanceof Subgrid && ((Subgrid) from).ogrid.equals(to)) {
            conformant = true;
            // TODO
        } else if (to instanceof Subgrid && ((Subgrid) to).ogrid.equals(from)) {
            conformant = true;
            // TODO
        }
        // TODO
    }

    @Override
    public boolean isConformant() {
        // the way the sampling is done, conformance is guaranteed as long as the resolutions are
        // close
        return true; // conformant;
    }

    @Override
    public long mapConformant(long offset) {

        if (identity) {
            return offset;
        }
        return to.getCellAt(from.getCell(offset).getCenter(), false).getOffsetInGrid();
        
//        if (conformant) {
//            if (this.from instanceof Subgrid) {
//                return ((Subgrid) this.from).getOriginalOffset(offset);
//            }
//            if (this.to instanceof Subgrid) {
//                return ((Subgrid) this.to).getOriginalOffset(offset);
//            }
//            throw new KlabInternalErrorException("grid2grid mediator: non-subgrid conformant grid: check usage");
//        }
//        throw new IllegalAccessError("cannot ask for a conformant offset in a non-conformant mediator");
    }

    @Override
    public Collection<Pair<Long, Double>> map(long originalOffset) {
        throw new KlabUnimplementedException("grid2grid mediator: non-subgrid conformant grid: not implemented");
    }

}
