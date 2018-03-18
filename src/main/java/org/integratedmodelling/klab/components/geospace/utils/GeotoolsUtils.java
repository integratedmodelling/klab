package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import javax.media.jai.RasterFactory;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Space;

public enum GeotoolsUtils {

  INSTANCE;


  Map<IConcept, Integer> conceptMap = new HashMap<>();
  AtomicInteger conceptIdx = new AtomicInteger(1);
  GridCoverageFactory rasterFactory = new GridCoverageFactory();

  /**
   * Turn a state into a grid coverage. 
   * 
   * @param state
   * @return a Geotools grid coverage
   * @throws IllegalArgumentException if the state is not suitable for a raster representation.
   */
  public GridCoverage2D stateToCoverage(IState state) {

    Space space = (Space) state.getScale().getSpace();
    if (space == null || !space.getGrid().isPresent()) {
      throw new IllegalArgumentException("cannot make a raster coverage from a non-gridded state");
    }
    Grid grid = (Grid) space.getGrid().get();

    /*
     * build a coverage
     * 
     * TODO use a raster of the appropriate type - for now there is apparently a bug in geotools
     * that makes it work only with float.
     */
    WritableRaster raster = RasterFactory.createBandedRaster(DataBuffer.TYPE_FLOAT,
        (int) grid.getXCells(), (int) grid.getYCells(), 1, null);

    /*
     * TODO raster should be pre-filled with a chosen nodata value TODO use activation layer
     */
    // IGridMask act = extent.requireActivationLayer(true);

    for (Cell cell : grid) {
      Object o = state.getData().get(cell);
      if (o == null || (o instanceof Double && Double.isNaN((Double) o))) {
        raster.setSample((int) cell.getX(), (int) cell.getY(), 0, (float) 0);
      } else if (o instanceof Number) {
        raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) o).floatValue());
      } else if (o instanceof Boolean) {
        raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Boolean) o) ? 1 : 0);
      } else if (o instanceof IConcept) {
        raster.setSample((int) cell.getX(), (int) cell.getY(), 0, getConceptIndex((IConcept) o));
      }
    }

    return rasterFactory.create(state.getObservable().getLocalName(), raster,
        space.getShape().getJTSEnvelope());

  }

  private int getConceptIndex(IConcept o) {
    
    Integer n = conceptMap.get(o);
    if (n == null) {
      n = conceptIdx.getAndIncrement();
      conceptMap.put(o, n);
    }
    return n;
  }
}
