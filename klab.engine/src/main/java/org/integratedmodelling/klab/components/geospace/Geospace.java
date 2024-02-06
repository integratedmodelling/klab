package org.integratedmodelling.klab.components.geospace;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.geotools.util.factory.Hints;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.geospace.utils.AdditionalEpsg;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Pair;
import org.locationtech.jts.geom.GeometryFactory;

@Component(id = "org.integratedmodelling.geospace", version = Version.CURRENT)
public class Geospace {

    public static final double AUTHALIC_EARTH_RADIUS_M = 6371007.2;

    public static GeometryFactory gFactory = new GeometryFactory();

    static {
    }

    public Geospace() {
        /**
         * Ensure that all CRSs use a X = horizontal axis convention, independent of what the
         * projection says. Do not change this or things will stop working entirely.
         */
        System.setProperty("org.geotools.referencing.forceXY", "true");
        Hints hints = new Hints(Hints.FORCE_LONGITUDE_FIRST_AXIS_ORDER, Boolean.TRUE);
        javax.imageio.spi.IIORegistry.getDefaultInstance().registerApplicationClasspathSpis();

        /**
         * enable additional epsg codes
         */
        try {
            AdditionalEpsg.registerAdditionalEpsgCodes();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Get the orientation corresponding to the standard d8 (1-8)
     * 
     * @param d8
     * @return
     */
    public static Orientation getOrientation(int d8) {

        switch(d8) {
        case 1:
            return Orientation.E;
        case 2:
            return Orientation.SE;
        case 3:
            return Orientation.S;
        case 4:
            return Orientation.SW;
        case 5:
            return Orientation.W;
        case 6:
            return Orientation.NW;
        case 7:
            return Orientation.N;
        case 8:
            return Orientation.NE;
        }
        return null;
    }

    public static int getD8(double heading) {

        if (heading >= 315) {
            return 6;
        } else if (heading >= 270) {
            return 5;
        } else if (heading >= 225) {
            return 4;
        } else if (heading >= 180) {
            return 3;
        } else if (heading >= 135) {
            return 2;
        } else if (heading >= 90) {
            return 1;
        } else if (heading >= 45) {
            return 8;
        } else if (heading >= 0) {
            return 7;
        }
        return -1;
    }

    public static double getHeading(int d8) {
        switch(d8) {
        case 1:
            return 90;
        case 2:
            return 135;
        case 3:
            return 180;
        case 4:
            return 225;
        case 5:
            return 270;
        case 6:
            return 315;
        case 7:
            return 0;
        case 8:
            return 45;
        }
        return Double.NaN;

    }

    /**
     * Translate D8 code to insane ArcGIS output format, for the corporate lovers.
     * 
     * @param d8
     * @return
     */
    public static int getD8Pow(int d8) {
        return (int) Math.pow(2, (8 - d8 + 1) % 8);
    }

    public static List<Cell> getUpstreamCells(Cell cell, IState flowDirectionsD8, Function<Cell, Boolean> check) {
        return getUpstreamCells(cell, flowDirectionsD8, null, check);
    }

    /**
     * Util: return a list of all the cells that flow into the passed one. Use when time is explicit
     * in the context.
     * 
     * @param cell
     * @param flowDirectionsD8 the flow direction as exponent to 2 in the standard scheme (values
     *        1-8)
     * @return
     */
    public static List<Cell> getUpstreamCells(Cell cell, IState flowDirectionsD8, ITime time, Function<Cell, Boolean> check) {

        List<Cell> ret = new ArrayList<>();

        Cell neighbor = cell.getNeighbor(Orientation.NW);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 8) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.N);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 7) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.NE);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 6) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.E);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 5) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.SE);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 4) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.S);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 3) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.SW);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 2) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        neighbor = cell.getNeighbor(Orientation.W);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 1) {
            if (check == null || check.apply(neighbor))
                ret.add(neighbor);
        }
        return ret;
    }

    /**
     * Util: return a list of all the cells that flow into the passed one along with their relative
     * orientation to the focal cell.
     * 
     * @param cell
     * @param flowDirectionsD8 the flow direction as exponent to 2 in the standard scheme (values
     *        1-8)
     * @return
     */
    public static List<Pair<Cell, Orientation>> getUpstreamCellsWithOrientation(Cell cell, IState flowDirectionsD8,
            ITime time, Function<Cell, Boolean> check) {

        List<Pair<Cell, Orientation>> ret = new ArrayList<>();

        Cell neighbor = cell.getNeighbor(Orientation.NW);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 8) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.NW));
        }
        neighbor = cell.getNeighbor(Orientation.N);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 7) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.N));
        }
        neighbor = cell.getNeighbor(Orientation.NE);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 6) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.NE));
        }
        neighbor = cell.getNeighbor(Orientation.E);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 5) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.E));
        }
        neighbor = cell.getNeighbor(Orientation.SE);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 4) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.SE));
        }
        neighbor = cell.getNeighbor(Orientation.S);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 3) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.S));
        }
        neighbor = cell.getNeighbor(Orientation.SW);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 2) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.SW));
        }
        neighbor = cell.getNeighbor(Orientation.W);
        if (neighbor != null && flowDirectionsD8.get(time == null ? neighbor : Scale.create(time, neighbor), Double.class) == 1) {
            if (check == null || check.apply(neighbor))
                ret.add(new Pair<>(neighbor, Orientation.W));
        }
        return ret;
    }

    /**
     * Downstream cell with its orientation, based on simple d8 (unique cell) scheme.
     * 
     * @param cell
     * @param flowDirectionsD8
     * @return the pair of cell and orientation, or null if flowdirs are NaN.
     */
    public static Pair<Cell, Orientation> getDownstreamCellWithOrientation(Cell cell, IState flowDirectionsD8) {
        int fdir = flowDirectionsD8.get(cell, Double.class).intValue();
        Orientation orientation = getOrientation(fdir);
        Cell neighbor = orientation == null ? null : cell.getNeighbor(orientation);
        return neighbor == null ? null : new Pair<>(neighbor, orientation);
    }

    @Initialize
    public void initialize() {
        // TODO create the desired geometry factory
        // TODO set up defaults for projections etc.
    }

    // void registerAdditionalCRS() throws KlabException {
    //
    // URL epsg = null;
    //
    // File epp = new File(Configuration.INSTANCE.getDataPath() + File.separator +
    // "epsg.properties");
    // if (epp.exists()) {
    // try {
    // epsg = epp.toURI().toURL();
    // } catch (MalformedURLException e) {
    // throw new KlabIOException(e);
    // }
    // }
    //
    // if (epsg != null) {
    // Hints hints = new Hints(Hints.CRS_AUTHORITY_FACTORY,
    // PropertyAuthorityFactory.class);
    // ReferencingFactoryContainer referencingFactoryContainer =
    // ReferencingFactoryContainer.instance(hints);
    // PropertyAuthorityFactory factory;
    // try {
    // factory = new PropertyAuthorityFactory(referencingFactoryContainer,
    // Citations.fromName("EPSG"), epsg);
    // ReferencingFactoryFinder.addAuthorityFactory(factory);
    // } catch (IOException e) {
    // throw new KlabIOException(e);
    // }
    // }
    // }

    // public JCS getWFSCache() throws KlabException {
    //
    // if (_wfsCache == null) {
    //
    // try {
    // _wfsCache = JCS.getInstance("wfs");
    // } catch (CacheException e) {
    // throw new KlabIOException(e);
    // }
    //
    // }
    // return _wfsCache;
    // }
    //
    // public JCS getWCSCache() throws KlabException {
    //
    // if (_wcsCache == null) {
    //
    // try {
    // _wcsCache = JCS.getInstance("wcs");
    // } catch (CacheException e) {
    // throw new KlabIOException(e);
    // }
    //
    // }
    // return _wcsCache;
    // }

}
