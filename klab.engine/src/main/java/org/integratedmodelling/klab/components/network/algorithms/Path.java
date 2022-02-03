/*
 * Copyright (C) 2014 Laboratoire ThéMA - UMR 6049 - CNRS / Université de Franche-Comté
 * http://thema.univ-fcomte.fr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.integratedmodelling.klab.components.network.algorithms;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.LineString;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A path of a linkset ie. a link between 2 patches.
 * 
 * @author Gilles Vuidel
 */
public class Path extends DefaultFeature {
    /** Attribute name for cost */
    public static final String COST_ATTR = "Dist";
    /** Attribute name for distance */
    public static final String DIST_ATTR = "DistM";
    /** Default attributes name list */
    private static final List<String> DEFAULT_ATTR_NAMES = Arrays.asList("ID1", "ID2", COST_ATTR, DIST_ATTR);
    private static List<String> ATTR_NAMES = new ArrayList<>(DEFAULT_ATTR_NAMES);

    private Feature patch1, patch2;
    private double cost;

    /**
     * Constructor used by {@link PlanarLinks} 
     * Create a link between the two patches. Cost and distance are 0. The geometry is a line between the centroid of the two patches.
     * @param patch1 first patch
     * @param patch2 second patch
     */
    public Path(Feature patch1, Feature patch2) {
        this(patch1, patch2, 0, 0);
    }

    /**
     * Create a link without real path geometry.
     * The geometry is a line between the centroid of the two patches.
     * @param patch1 first patch
     * @param patch2 second patch
     * @param cost the cost between the two patches
     * @param dist the distance between the two patches
     */
    public Path(Feature patch1, Feature patch2, double cost, double dist) {
        this(patch1, patch2, cost, dist, ATTR_NAMES);
    }
    
    /**
     * Create a link without real path geometry.
     * The geometry is a line between the centroid of the two patches.
     * @param patch1 first patch
     * @param patch2 second patch
     * @param cost the cost between the two patches
     * @param dist the distance between the two patches
     * @param attrNames the list of attributes name
     */
    public Path(Feature patch1, Feature patch2, double cost, double dist, List<String> attrNames) {
        super(patch1.getId().toString() + "-" + patch2.getId().toString(),
                patch1.getGeometry().getFactory().createLineString(new Coordinate[] {
                    patch1.getGeometry().getCentroid().getCoordinate(),
                    patch2.getGeometry().getCentroid().getCoordinate()}),
            attrNames, new ArrayList(Arrays.asList(patch1.getId(), patch2.getId(),
                cost, dist)));
        this.patch1 = patch1;
        this.patch2 = patch2;
        this.cost = cost;
        
        for(int i = 4; i < attrNames.size(); i++) {
            this.addAttribute(attrNames.get(i), null);
        }
    }

    /**
     * Create a link with real path geometry.
     * The distance is calculated from the path geometry.
     * @param patch1 first patch
     * @param patch2 second patch
     * @param cost the cost between the two patches
     * @param path the path geometry
     */
    public Path(Feature patch1, Feature patch2, double cost, LineString path) {
        this(patch1, patch2, cost, path, ATTR_NAMES);
    }
    
    /**
     * Create a link with real path geometry.
     * The distance is calculated from the path geometry.
     * @param patch1 first patch
     * @param patch2 second patch
     * @param cost the cost between the two patches
     * @param path the path geometry
     * @param attrNames the list of attributes name
     */
    public Path(Feature patch1, Feature patch2, double cost, LineString path, List<String> attrNames) {
        super(patch1.getId().toString() + "-" + patch2.getId().toString(), path, 
                attrNames, new ArrayList(Arrays.asList(patch1.getId(), patch2.getId(), cost, path.getLength())));
        this.patch1 = patch1;
        this.patch2 = patch2;
        this.cost = cost;
        
        for(int i = 4; i < attrNames.size(); i++) {
            this.addAttribute(attrNames.get(i), null);
        }
    }

    private Path(Feature f) {
        super(f, false);
    }

    /**
     * @return the cost of this path
     */
    public final double getCost() {
        return cost;
    }

    /**
     * @return the distance of this path
     */
    public final double getDist() {
        return ((Number)getAttribute(DIST_ATTR)).doubleValue();
    }

    /**
     * @return the first patch
     */
    public Feature getPatch1() {
        return patch1;
    }

    /**
     * @return the second patch
     */    
    public Feature getPatch2() {
        return patch2;
    }

    /**
     * Returns the coordinate where the path touches the patch
     * @param patch the patch, must be getPatch1() or getPatch2()
     * @return the coordinate touching patch
     * @throws IllegalArgumentException if patch != getPatch1() and patch != getPatch2()
     */
    public Coordinate getCoordinate(Feature patch) {
        Coordinate[] coords = getGeometry().getCoordinates();
        if(getPatch1() == patch) {
            return coords[0];
        } if(getPatch2() == patch) {
            return coords[coords.length-1];
        } else {
            throw new IllegalArgumentException("The path does not connect the patch " + patch);
        }
    }
    
    /**
     * Returns the patch which is common to p1 and p2
     * @param p1 a path
     * @param p2 another path
     * @return the patch connected to these two paths
     * @throws IllegalArgumentException if no patch is connected to the both paths
     */
    public static Feature getCommonPatch(Path p1, Path p2) {
        if(p1.getPatch1() == p2.getPatch1() || p1.getPatch1() == p2.getPatch2()) {
            return p1.getPatch1();
        }
        if(p1.getPatch2() == p2.getPatch1() || p1.getPatch2() == p2.getPatch2()) {
            return p1.getPatch2();
        }
        
        throw new IllegalArgumentException("No common patch between path " + p1.getId() + " and " + p2.getId());
    }


    /**
     * Creates a Path from a feature.
     * This method is used when loading a linkset from a shapefile (ie. with real path)
     * @param f the feature corresponding to a path
     * @param prj the project
     * @return the new path
     */
    public static Path loadPath(Feature f, Project prj) {
        Path p = new Path(f);
        p.patch1 = prj.getPatch((Integer)f.getAttribute(ATTR_NAMES.get(0)));
        p.patch2 = prj.getPatch((Integer)f.getAttribute(Path.ATTR_NAMES.get(1)));
        p.cost = ((Number)f.getAttribute(Path.ATTR_NAMES.get(2))).floatValue();
        return p;
    }

    /**
     * Call this method before creating a new linkset.
     */
    public static void newSetOfPaths() {
        ATTR_NAMES = new ArrayList<>(DEFAULT_ATTR_NAMES);
    }
    
    /**
     * Call this method before creating a new linkset.
     */
    public static void newSetOfPaths(List<String> attrNames) {
        ATTR_NAMES = new ArrayList<>(DEFAULT_ATTR_NAMES);
        ATTR_NAMES.addAll(attrNames);
    }

    /**
     * Stores a path in a String array.
     * Does not store the geometry. 
     * This method is used for saving linkset without real paths.
     * 
     * @param p the path to store
     * @return an array of String storing all elements of the path but the geometry
     * @see #deserialPath(java.lang.String[], org.thema.graphab.Project) 
     */
    public static String[] serialPath(Path p) {
        String [] elems = new String[p.getAttributeNames().size()];
        elems[0] = p.patch1.getId().toString();
        elems[1] = p.patch2.getId().toString();
        elems[2] = String.valueOf(p.getCost());
        elems[3] = String.valueOf(p.getDist());
        for(int i = 4; i < p.getAttributeNames().size(); i++) {
            elems[i] = p.getAttribute(i).toString();
        }
        return elems;
    }

    /**
     * Retrieves a Path from an array of String. 
     * This method is used for loading linkset from csv file without real paths.
     * @param line the String array
     * @param prj the project
     * @return a new path reflecting the String array data
     * @see #serialPath(org.thema.graphab.links.Path) 
     */
    public static Path deserialPath(String [] line, Project prj) {
        Feature patch1 = prj.getPatch(Integer.parseInt(line[0]));
        Feature patch2 = prj.getPatch(Integer.parseInt(line[1]));
        double cost = Double.parseDouble(line[2]);
        double dist = Double.parseDouble(line[3]);
        Path p = new Path(patch1, patch2, cost, dist);
        for(int i = 4; i < line.length; i++) {
            p.addAttribute(ATTR_NAMES.get(i), Double.parseDouble(line[i]));
        }
        return p;
    }
}
