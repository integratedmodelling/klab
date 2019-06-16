package org.integratedmodelling.klab.components.geospace.processing.viewshed;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;

import com.vividsolutions.jts.geom.Point;

public class ViewshedAlgorithm {

    IState dem = null;
    IState out = null;
    ISpace space = null;
    Grid grid = null;
    Point viewpoint;

    List<ViewEvent> eventList = new ArrayList<>();

    public ViewshedAlgorithm(IState dem, IState output, IShape viewpoint) {

        this.dem = dem;
        this.out = output;
        this.space = dem.getSpace();

        if (this.space == null || !(this.space instanceof Space) || ((Space) this.space).getGrid() == null) {
            throw new IllegalArgumentException("viewshed algorithm must be run on a gridded context");
        }

        this.viewpoint = (Point) ((Shape) viewpoint).getCentroid().getJTSGeometry();
        this.grid = (Grid) ((Space) this.space).getGrid();
    }

    public void run() {

        // TODO finish porting and link up
        int[] observer = new int[2];
        double[][] elev = new double[2][2];
        boolean[][] viewshed = new boolean[2][2];
        int radius = 2; // in cells
        int nrows = 2;
        int ncols = 2;
        double observer_ht = 0;
        double target_ht = observer_ht;
        // the rest just need adapting to the k.LAB grid model
        
        int i;

        int xmin = Math.max(observer[0] - radius, -10);
        int ymin = Math.max(observer[1] - radius, -10);
        int xmax = Math.min(observer[0] + radius, nrows + 9);
        int ymax = Math.min(observer[1] + radius, ncols + 9);
        int xwidth = xmax - xmin;
        int ywidth = ymax - ymin;
        int perimeter = 2 * (xwidth + ywidth); // This formula is subtle

        //	    tiledMatrix<elev_t>& elev = *elevp;
        //	    tiledMatrix<unsigned char>& viewshed = *viewshedp;

        // let's start from whatever we get without resetting, so that we can OR
        // different observation points.
        //	    viewshed.set(0);

        double[] delta = new double[2];
        int[] target = new int[2];
        int[] p = new int[2];

        viewshed[observer[0]][observer[1]] = true; // Observer is visible from itself.

        // Observer distance about sea level, incl distance above ground.
        double observer_alt = elev[observer[0]][observer[1]] + observer_ht;

        // The target is in turn every point along the smaller of the border or a box
        // of side 2*radius around the observer.

        // xmax etc are coords of pixels, not of the edges between the pixels.  I.e.,
        // xmin=5, xmax=7 means 3 pixels.
        // A 3x3 regions has a perimeter of 9.

        if (xmin == xmax || ymin == ymax) {
            return;
        }

        for (int ip = 0; ip < perimeter; ip++) {

            //define cells on square perimeter
            if (ip < ywidth) {
                target[0] = xmax;
                target[1] = ymax - ip;
            } else if (ip < xwidth + ywidth) {
                target[0] = xmax - (ip - ywidth);
                target[1] = ymin;
            } else if (ip < 2 * ywidth + xwidth) {
                target[0] = xmin;
                target[1] = ymin + (ip - xwidth - ywidth);
            } else {
                target[0] = xmin + (ip - 2 * ywidth - xwidth);
                target[1] = ymax;
            }

            // This occurs only when observer is on the edge of the region.
            if (observer[0] == target[0] && observer[1] == target[1]) {
                continue;
            }

            // Run a line of sight out from obs to target.
            delta[0] = target[0] - observer[0];
            delta[1] = target[1] - observer[1];
            boolean inciny = (Math.abs(delta[0]) < Math.abs(delta[1])); // outer parens reqd

            // Step along the coord (X or Y) that varies the most from the observer to
            // the target.  Inciny says which coord that is.  Slope is how fast the
            // other coord varies.
            double slope = delta[1 - (inciny ? 1 : 0)] / (double) delta[inciny ? 1 : 0];

            int sig = (delta[inciny ? 1 : 0] > 0 ? 1 : -1);
            double horizon_slope = -99999; // Slope (in vertical plane) to horizon so far.

            // i=0 would be the observer, which is always visible.
            for (i = sig; i != delta[inciny ? 1 : 0]; i += sig) {
                
                p[inciny ? 1 : 0] = observer[inciny ? 1 : 0] + i;
                p[1 - (inciny ? 1 : 0)] = observer[1 - (inciny ? 1 : 0)] + (int) (i * slope);

                // Have we reached the edge of the area?
                if (p[0] < 0 || p[0] >= nrows || p[1] < 0 || p[1] >= ncols) {
                    break;
                }

                // A little optimization, so we don't need to use long long every time (int is faster)
                if (Math.abs(p[0] - observer[0]) + Math.abs(p[1] - observer[1]) > radius) {
                    //but sometimes we still need to use them...
                    if ((square((long) Math.abs(p[0] - observer[0]))
                            + square((long) Math.abs(p[1] - observer[1])) > square((long) radius))) {
                        break;
                    }
                }

                double pelev = elev[p[0]][p[1]];

                // Slope from the observer, incl the observer_ht, to this point, at ground
                // level.  The slope is projected into the plane XZ or YZ, depending on
                // whether X or Y is varying faster, and thus being iterated thru.
                double s = (double) (pelev - observer_alt)
                        / (double) Math.abs((p[inciny ? 1 : 0] - observer[inciny ? 1 : 0]));
               
                if (horizon_slope < s) {
                    horizon_slope = s;
                }
                
                double horizon_alt = observer_alt
                        + horizon_slope * Math.abs(p[inciny ? 1 : 0] - observer[inciny ? 1 : 0]);

                boolean v = (pelev + target_ht >= horizon_alt);

                if (v) {
                    viewshed[p[0]][p[1]] = true;
                }
            }
        }

    }

    private long square(long abs) {
        return abs * abs;
    }

}
