package org.integratedmodelling.klab.components.geospace.extents;

import java.util.BitSet;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Mask;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.services.IConfigurationService;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.processing.Rasterizer;

public class GridMask extends BitSet implements IGrid.Mask {

    private static final long serialVersionUID = 1066602180194149853L;
    // disabled - prepared shape works OK with lots less memory footprint
    private static final int MAX_GRID_SIZE_FOR_RASTERIZATION = 0;
    private IGrid grid;
    private IShape shape;
    private boolean useShape;

    public GridMask(IGrid grid, IShape shape) {
        this.grid = grid;
        this.shape = shape;
        if (grid.getCellCount() < MAX_GRID_SIZE_FOR_RASTERIZATION) {
            Rasterizer<Boolean> rasterizer = new Rasterizer<>(grid);
            rasterizer.add(shape, (s) -> true);
            rasterizer.finish((b, xy) -> {
                if (b != null && b)
                    set((int) grid.getOffset(xy[0], xy[1]));
            });
        } else {
            useShape = true;
            if (Boolean.parseBoolean(
                    Configuration.INSTANCE.getProperty(IConfigurationService.KLAB_LENIENT_GRID_INTERSECTION, "true"))) {
                this.shape = shape.buffer(Math.max(grid.getCellWidth(), grid.getCellHeight())/2.0);
            }
        }
    }

    @Override
    public void merge(Mask other, LogicalConnector connector) {
        if (connector.equals(LogicalConnector.UNION)) {
            or((GridMask) other);
        } else if (connector.equals(LogicalConnector.UNION)) {
            and((GridMask) other);
        } else
            throw new IllegalArgumentException("grid masks can only merge in UNION or INTERSECTION");
    }

    @Override
    public boolean isActive(long x, long y) {
        if (useShape) {
            double[] coords = grid.getCoordinates(grid.getOffset(x, y));
            return ((Shape) shape).containsPoint(coords);
            // return ((Shape) shape).getJTSGeometry().contains(((Shape)
            // shape).getJTSGeometry().getFactory()
            // .createPoint(new Coordinate(coords[0], coords[1])));
        }
        return get((int) grid.getOffset(x, y));
    }

    @Override
    public void activate(long x, long y) {
        if (!useShape) {
            set((int) grid.getOffset(x, y));
        }
    }

    @Override
    public void deactivate(long x, long y) {
        if (!useShape) {
            set((int) grid.getOffset(x, y), false);
        }
    }

    @Override
    public long totalActiveCells() {
        return useShape ? grid.getCellCount() : cardinality();
    }

    @Override
    public long nextActiveOffset(long fromOffset) {
        // return useShape ? fromOffset + 1 : nextSetBit((int) fromOffset);
        if (useShape) {
            if (fromOffset >= grid.getCellCount() - 1) {
                return -1;
            }
            while(!((Shape) shape).containsPoint(grid.getCoordinates(fromOffset)) && fromOffset < (grid.getCellCount() - 1)) {
                fromOffset++;
            }
            if (fromOffset >= grid.getCellCount() - 1) {
                return -1;
            }
            return fromOffset;
        }
        return nextSetBit((int) fromOffset);
    }

    @Override
    public void invert() {
        if (!useShape) {
            for (int i = 0; i < this.size(); i++) {
                flip(i);
            }
        }
    }

    @Override
    public void deactivate() {
        if (!useShape) {
            clear();
        }
    }

    @Override
    public void activate() {
        if (!useShape) {
            set(0, size());
        }
    }

}
