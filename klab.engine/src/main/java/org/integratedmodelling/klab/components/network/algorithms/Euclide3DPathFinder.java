package org.integratedmodelling.klab.components.network.algorithms;


import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.List;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;

/**
 * Euclidean pathfinder.
 * 
 * @author Gilles Vuidel
 */
public class Euclide3DPathFinder extends EuclidePathFinder {
    
    private Raster heightRaster;

    /**
     * Creates a new 3D Euclidean pathfinder
     * @param project the current project
     * @param heightRaster raster of height object
     */
    public Euclide3DPathFinder(Project project, Raster heightRaster) {
        super(project);
        this.heightRaster = heightRaster;
    }

    @Override
    public Path createPath(Feature patch1, Feature patch2) {
        Path p = super.createPath(patch1, patch2);
        
        List<Coordinate> coords = new ArrayList<>();
        Coordinate c = new Coordinate(p.getGeometry().getCoordinates()[0]);
        setCoordInsidePatch(patch1.getGeometry(), c);
        Coordinate c0 = project.getSpace2grid().transform(c, new Coordinate());
        double h0 = heightRaster.getSampleDouble((int)c0.x, (int)c0.y, 0);
        c.setZ(h0);
        coords.add(c);
        
        c = new Coordinate(p.getGeometry().getCoordinates()[1]);
        setCoordInsidePatch(patch2.getGeometry(), c);
        Coordinate c1 = project.getSpace2grid().transform(c, new Coordinate());
        double h1 = heightRaster.getSampleDouble((int)c1.x, (int)c1.y, 0);
        c.setZ(h1);
        coords.add(c);
        
        int x0 = (int)c0.x; int y0 = (int) c0.y;
        int x1 = (int)c1.x; int y1 = (int) c1.y;
        final int dx = (int)Math.abs(x1-x0);
        final int dy = (int)Math.abs(y1-y0);
        final int sx = x0 < x1 ? 1 : -1;
        final int sy = y0 < y1 ? 1 : -1;
        int err = dx-dy;
        double slope = (h1-h0) / c0.distance(c1);
        while(x0 != x1 || y0 != y1) {   
            final int e2 = (err << 1);
            if(e2 > -dy) {
                err -= dy;
                x0 += sx;
            }
            if(e2 < dx) {
                err += dx;
                y0 += sy;
            }
            double h = heightRaster.getSampleDouble(x0, y0, 0);
            double s = (h-h0) / c0.distance(new Coordinate(x0, y0));
            if(s > slope) {
                c0 = new Coordinate(x0, y0);
                h0 = h;
                slope = (h1-h0) / c0.distance(c1);
                c0.x += 0.5;
                c0.y += 0.5;
                c = project.getGrid2space().transform(c0, new Coordinate());
                c.setZ(h);
                coords.add(coords.size()-1, c);
            }
        }
        
        LineString line = new GeometryFactory().createLineString(coords.toArray(new Coordinate[0]));
        
        double dist = 0;
        Coordinate prec = null;
        for(Coordinate co : coords) {
            if(prec != null) {
                dist += co.distance3D(prec);
            }
            prec = co;
        }
        
        return new Path(p.getPatch1(), p.getPatch2(), dist, line);
    }
    
    private void setCoordInsidePatch(Geometry patch, Coordinate c) {
        double res = project.getResolution();
        c.x += res/2;
        c.y += res/2;
        if(patch.contains(patch.getFactory().createPoint(c))) {
            return;
        }
        c.x += -res;
        if(patch.contains(patch.getFactory().createPoint(c))) {
            return;
        }
        c.y += -res;
        if(patch.contains(patch.getFactory().createPoint(c))) {
            return;
        }
        c.x += res;
        if(!patch.contains(patch.getFactory().createPoint(c))) {
            throw new IllegalArgumentException("Coordinate is not at the border of the patch");
        }
    }

}