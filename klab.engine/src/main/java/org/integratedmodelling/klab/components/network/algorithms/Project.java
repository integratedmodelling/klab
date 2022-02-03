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

//import org.thema.graphab.pointset.PointsetLayer;
//import org.thema.graphab.links.LinkLayer;
//import au.com.bytecode.opencsv.CSVReader;
//import au.com.bytecode.opencsv.CSVWriter;
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.converters.Converter;
//import com.thoughtworks.xstream.converters.MarshallingContext;
//import com.thoughtworks.xstream.converters.UnmarshallingContext;
//import com.thoughtworks.xstream.io.HierarchicalStreamReader;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//import com.thoughtworks.xstream.security.AnyTypePermission;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Envelope;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.util.AffineTransformation;
import org.locationtech.jts.geom.util.NoninvertibleTransformationException;
import org.locationtech.jts.index.strtree.ItemBoundable;
import org.locationtech.jts.index.strtree.ItemDistance;
import org.locationtech.jts.index.strtree.STRtree;
import org.locationtech.jts.operation.union.CascadedPolygonUnion;
import java.awt.Color;
import java.awt.color.ColorSpace;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ComponentColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import java.awt.image.Raster;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.coverage.grid.GridCoverageBuilder;
import org.geotools.coverage.grid.GridCoverageFactory;
import org.geotools.coverage.grid.GridEnvelope2D;
import org.geotools.feature.SchemaException;
import org.geotools.geometry.Envelope2D;
//import org.geotools.graph.structure.Graph;
//import org.geotools.graph.structure.Node;
import org.geotools.referencing.CRS;
import org.geotools.referencing.crs.DefaultEngineeringCRS;
import org.opengis.referencing.FactoryException;
import org.opengis.referencing.crs.CoordinateReferenceSystem;
//import org.thema.common.Config;
//import org.thema.common.JTS;
//import org.thema.common.ProgressBar;
//import org.thema.common.Util;
//import org.thema.common.io.IOFile;
//import org.thema.common.io.tab.CSVTabReader;
//import org.thema.common.parallel.AbstractParallelFTask;
//import org.thema.common.parallel.ParallelFExecutor;
//import org.thema.common.parallel.SimpleParallelTask;
//import org.thema.data.GlobalDataStore;
//import org.thema.data.IOFeature;
//import org.thema.data.IOImage;
//import org.thema.data.feature.DefaultFeature;
//import org.thema.data.feature.Feature;
//import org.thema.drawshape.image.CoverageShape;
//import org.thema.drawshape.image.RasterShape;
//import org.thema.drawshape.layer.DefaultGroupLayer;
//import org.thema.drawshape.layer.FeatureLayer;
//import org.thema.drawshape.layer.Layer;
//import org.thema.drawshape.layer.RasterLayer;
//import org.thema.drawshape.style.LineStyle;
//import org.thema.drawshape.style.RasterStyle;
//import org.thema.graph.shape.GraphGroupLayer;
//import org.thema.graphab.CapaPatchDialog.CapaPatchParam;
//import org.thema.graphab.graph.GraphGenerator;
//import org.thema.graphab.graph.GraphPathFinder;
//import org.thema.graphab.links.CircuitRaster;
//import org.thema.graphab.links.EuclidePathFinder;
//import org.thema.graphab.links.PlanarLinks;
//import org.thema.graphab.links.Linkset;
//import org.thema.graphab.links.Path;
//import org.thema.graphab.links.RasterPathFinder;
//import org.thema.graphab.links.SpacePathFinder;
//import org.thema.graphab.metric.Metric;
//import org.thema.graphab.metric.global.AbstractLocal2GlobalMetric.TypeElem;
//import org.thema.graphab.metric.global.CCPMetric;
//import org.thema.graphab.metric.global.DeltaPCMetric;
//import org.thema.graphab.metric.global.ECMetric;
//import org.thema.graphab.metric.global.ECSMetric;
//import org.thema.graphab.metric.global.GDMetric;
//import org.thema.graphab.metric.global.GlobalMetric;
//import org.thema.graphab.metric.global.HMetric;
//import org.thema.graphab.metric.global.IICMetric;
//import org.thema.graphab.metric.global.MSCMetric;
//import org.thema.graphab.metric.global.NCMetric;
//import org.thema.graphab.metric.global.PCMetric;
//import org.thema.graphab.metric.global.SLCMetric;
//import org.thema.graphab.metric.global.SumLocal2GlobalMetric;
//import org.thema.graphab.metric.global.WilksMetric;
//import org.thema.graphab.metric.local.BCLocalMetric;
//import org.thema.graphab.metric.local.CCLocalMetric;
//import org.thema.graphab.metric.local.CFLocalMetric;
//import org.thema.graphab.metric.local.ClosenessLocalMetric;
//import org.thema.graphab.metric.local.ConCorrLocalMetric;
//import org.thema.graphab.metric.local.DgLocalMetric;
//import org.thema.graphab.metric.local.EccentricityLocalMetric;
//import org.thema.graphab.metric.local.FLocalMetric;
//import org.thema.graphab.metric.local.IFInterLocalMetric;
//import org.thema.graphab.metric.local.IFLocalMetric;
//import org.thema.graphab.metric.local.LocalMetric;
//import org.thema.graphab.pointset.Pointset;
//import org.thema.graphab.util.DistanceOp;
//import org.thema.graphab.util.RSTGridReader;
//import org.thema.graphab.util.SpatialOp;
//import org.thema.parallel.AbstractParallelTask;
//import org.thema.parallel.ExecutorService;

/**
 * Contains all the data of a project. - the landscape raster - the patch raster
 * - the feature patches - the linksets - the graphs - the pointsets - the DEM
 * raster if any - and all project parameters The class is serialized into the
 * xml project file. This class creates new project, loads and saves whole
 * project.
 * 
 * @author Gilles Vuidel
 */
public final class Project {

//    public enum Method {
//        GLOBAL, COMP, LOCAL, DELTA
//    }
//
//    // Patch attributes
//    public static final String CAPA_ATTR = "Capacity";
//    public static final String AREA_ATTR = "Area";
//    public static final String PERIM_ATTR = "Perim";
//    private static final List<String> PATCH_ATTRS = Arrays.asList(AREA_ATTR, PERIM_ATTR, CAPA_ATTR);
//    
//    // Pointset attributes
//    public static final String EXO_IDPATCH = "IdPatch";
//    public static final String EXO_COST = "Cost";
//    
//    // project files
//    public static final String LAND_RASTER = "source.tif";
//    public static final String PATCH_RASTER = "patches.tif";
//    public static final String PATCH_SHAPE = "patches.shp";
//    public static final String VORONOI_SHAPE = "voronoi.shp";
//    public static final String LINKS_SHAPE = "links.shp";
//    
//    private transient File dir;
//
//    private String name;
//    
//    private TreeSet<Integer> codes;
	private Set<Integer> patchCodes;
//    private boolean merge;
//    private double noData;
//    private boolean con8;
//    /**
//     * taille minimale pour un patch en unité du système de coordonnées
//     * donc normalement m2
//     */
//    private double minArea;
//    
//    private double maxSize;
//    
//    /** Keep for compatibility project, parameter no more used*/
//    private boolean simplify;
//    
//    private CapaPatchDialog.CapaPatchParam capacityParams;
//
	private double resolution;
//    private AffineTransformation grid2space, space2grid;
//
//    private TreeMap<String, Linkset> costLinks;
//
//    private TreeMap<String, Pointset> exoDatas;
//
//    private TreeMap<String, GraphGenerator> graphs;
//
//    private Rectangle2D zone;
//    
//    private String wktCRS;
//    
//    private File demFile;
//	
	private transient List<DefaultFeature> patches;

//    private transient List<Feature> voronoi;
//
//    private transient PlanarLinks planarLinks;
//
//    private transient DefaultGroupLayer rootLayer, linkLayers, exoLayers, graphLayers, analysisLayers;
	private transient STRtree patchIndex;
//
//    private transient double totalPatchCapacity;
//
//    private transient HashMap<Integer, Integer> removedCodes;
//    private transient Ref<WritableRaster> srcRaster;
//    private transient Ref<WritableRaster> patchRaster;
//    private transient HashMap<File, SoftRef<Raster>> extRasters;
//
//    /**
//     * Generates a new project and saves it.
//     * The planar topology is calculated, but no linkset.
//     * @param name the name of the project
//     * @param prjPath the directory of the project
//     * @param cov the landscape map
//     * @param codes the value set of the landscape map
//     * @param patchCodes the codes for the patches
//     * @param merge are patches of different codes merged ?
//     * @param noData the nodata value or NaN
//     * @param con8 is 8 connex or 4 connex for patch extraction ?
//     * @param minArea the minimum area for a patch
//     * @param maxSize maximum width or height of patch envelope in pixel
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public Project(String name, File prjPath, GridCoverage2D cov, TreeSet<Integer> codes, Set<Integer> patchCodes, boolean merge,
//            double noData, boolean con8, double minArea, double maxSize) throws IOException, SchemaException {
//        this(name, prjPath, cov, codes, patchCodes, merge, noData, con8, minArea, maxSize, true);
//    }
//    
//    /**
//     * Generates a new project and saves it.
//     * No linkset is created.
//     * @param name the name of the project
//     * @param prjPath the directory of the project
//     * @param cov the landscape map
//     * @param codes the value set of the landscape map
//     * @param patchCodes the codes for the patches
//     * @param merge are patches of different codes merged ?
//     * @param noData the nodata value or NaN
//     * @param con8 is 8 connex or 4 connex for patch extraction ?
//     * @param minArea the minimum area for a patch
//     * @param maxSize maximum width or height of patch envelope in pixel
//     * @param calcVoronoi calculate planar topology ?
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public Project(String name, File prjPath, GridCoverage2D cov, TreeSet<Integer> codes, Set<Integer> patchCodes, boolean merge,
//            double noData, boolean con8, double minArea, double maxSize, boolean calcVoronoi) throws IOException, SchemaException {
//
//        this.name = name;
//        this.dir = prjPath;
//        this.codes = codes;
//        this.patchCodes = patchCodes;
//        this.merge = merge;
//        this.noData = noData;
//        this.con8 = con8;
//        this.minArea = minArea;
//        this.simplify = false;
//        this.capacityParams = new CapaPatchDialog.CapaPatchParam();
//        
//        if(!Double.isNaN(noData)) {
//            codes.remove((int)noData);
//        }
//        
//        Envelope2D gZone = cov.getEnvelope2D();
//        zone = gZone.getBounds2D();
//        CoordinateReferenceSystem crs = cov.getCoordinateReferenceSystem2D();
//        if(crs instanceof DefaultEngineeringCRS) {
//            crs = null;
//        }
//        if(crs != null)   {  
//            wktCRS = crs.toWKT();
//        }
//
//        GeometryFactory geomFac = new GeometryFactory();
//        GridEnvelope2D range = cov.getGridGeometry().getGridRange2D();
//        grid2space = new AffineTransformation(zone.getWidth() / range.getWidth(), 0,
//                    zone.getMinX() - zone.getWidth() / range.getWidth(),
//                0, -zone.getHeight() / range.getHeight(),
//                    zone.getMaxY() + zone.getHeight() / range.getHeight());
//        try {
//            space2grid = grid2space.getInverse();
//        } catch (NoninvertibleTransformationException ex) {
//            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        resolution = grid2space.getMatrixEntries()[0];
//
//        Envelope2D extZone = new Envelope2D(crs,
//                gZone.x-resolution, gZone.y-resolution, gZone.width+2*resolution, gZone.height+2*resolution);
//        
//        TreeMap<Integer, Envelope> envMap = new TreeMap<>();
//
//        WritableRaster rasterPatchs = SpatialOp.extractPatch(cov.getRenderedImage(), patchCodes, merge, noData, con8, (int)(maxSize/resolution), envMap);      
//
//        Logger.getLogger(MainFrame.class.getName()).log(Level.INFO, "Nb patch : " + envMap.size());
//
//        ProgressBar monitor = Config.getProgressBar(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Vectorizing..."), envMap.size());
//
//        List<String> attrNames = new ArrayList<>(PATCH_ATTRS);
//        // parallelize the recoding may be dangerous if envMap.firstKey() <= envMap.size(), it may have overlay in id while recoding in parallel
//        if(envMap.firstKey() <= envMap.size()) {
//            throw new IllegalStateException("Error in patch extraction, id overlays");
//        }
//        final SortedMap<Integer, DefaultFeature> sortedPatch = Collections.synchronizedSortedMap(new TreeMap<>());
//        
//        SimpleParallelTask<Integer> task = new SimpleParallelTask<Integer>(
//                new ArrayList<Integer>(envMap.keySet()), monitor) {
//            int nbRem = 0;
//            @Override
//            protected void executeOne(Integer id) {
//                Geometry g = geomFac.toGeometry(envMap.get(id));
//                g.apply(grid2space);
//                if(minArea == 0 || (g.getArea() / minArea) > (1-1E-9)) {
//                    Geometry geom = SpatialOp.vectorize(rasterPatchs, envMap.get(id), id.doubleValue());
//                    geom.apply(grid2space);
//                    if(minArea == 0 || (geom.getArea() / minArea) > (1-1E-9)) {
//                        List lst = new ArrayList(Arrays.asList(geom.getArea(), geom.getLength(), geom.getArea()));
//                        sortedPatch.put(SpatialOp.getFirstPixel(rasterPatchs, geom, id, space2grid), new DefaultFeature(id, geom, attrNames, lst));
//                    }
//                    else {
//                        synchronized(Project.this) {
//                            SpatialOp.recode(rasterPatchs, geom, id, 0, space2grid);
//                            nbRem++;
//                        }
//                    }
//                } else {
//                    synchronized(Project.this) {
//                        SpatialOp.recode(rasterPatchs, g, id, 0, space2grid);
//                        nbRem++;
//                    }
//                }
//
//            }
//            
//        };
//        new ParallelFExecutor(task).executeAndWait();
//
//        if(sortedPatch.isEmpty()) {
//            throw new IllegalStateException("There is no patch in the map. Check patch code and min area.");
//        }
//        int n = 1;
//        patches = new ArrayList<>();
//        for(DefaultFeature patch : sortedPatch.values()) {
//            patches.add(new DefaultFeature(n, patch.getGeometry(), attrNames, patch.getAttributes()));
//            SpatialOp.recode(rasterPatchs, patch.getGeometry(), (int)patch.getId(), n, space2grid);
//            n++;
//        }
//        sortedPatch.clear();
//        
//        monitor.setNote(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Saving..."));
//        monitor.setIndeterminate(true);
//        dir.mkdirs();
//        
//        GridCoverageBuilder covBuilder = new GridCoverageBuilder();
//        covBuilder.setEnvelope(extZone);
//        covBuilder.setBufferedImage(new BufferedImage(new ComponentColorModel(ColorSpace.getInstance(ColorSpace.CS_GRAY),
//                    false, false, ColorModel.OPAQUE, DataBuffer.TYPE_INT),
//                rasterPatchs, false, null));
//        covBuilder.newVariable("Cluster", null);
//        covBuilder.setSampleRange(1, patches.size());
//        GridCoverage2D clustCov = covBuilder.getGridCoverage2D();
//        
//        IOImage.saveTiffCoverage(new File(dir, LAND_RASTER), cov);
//        IOImage.saveTiffCoverage(new File(dir, PATCH_RASTER), clustCov);
//        
//        IOFeature.saveFeatures(patches, new File(dir, PATCH_SHAPE), crs);
//        
//        clustCov = null;
//        covBuilder = null;
//
////        Logger.getLogger(MainFrame.class.getName()).log(Level.INFO, "Nb small patch removed : " + nbRem);
//
//        if(calcVoronoi) {
//            WritableRaster voronoiR = rasterPatchs;
//            planarLinks = neighborhoodEuclid(patches, voronoiR, grid2space, resolution);
//            monitor.setNote("Saving...");
//            IOFeature.saveFeatures(planarLinks.getFeatures(), new File(dir, LINKS_SHAPE), crs);
//
//            List<? extends Feature> voronois = SpatialOp.vectorizeVoronoi(voronoiR, grid2space);
//            IOFeature.saveFeatures(voronois, new File(prjPath, VORONOI_SHAPE), crs);
//        }
//        
//        exoDatas = new TreeMap<>();
//
//        graphs = new TreeMap<>();
//
//        costLinks = new TreeMap<>();
//        
//        removedCodes = new HashMap<>();
//        
//        save();
//        
//        monitor.close();
//    }
//    
//    /**
//     * copy constructor for a new sub project
//     * @param name
//     * @param prj 
//     */
//    private Project(String name, Project prj) {
//        this.name = name;
//        codes = prj.codes;
//        patchCodes = prj.patchCodes;
//        noData = prj.noData;
//        con8 = prj.con8;
//        minArea = prj.minArea;
//        simplify = prj.simplify;
//        capacityParams = prj.capacityParams;
//        resolution = prj.resolution;
//        grid2space = prj.grid2space;
//        space2grid = prj.space2grid;
//        zone = prj.zone;
//        wktCRS = prj.wktCRS;
//        
//        costLinks = new TreeMap<>();
//        exoDatas = new TreeMap<>();
//        graphs = new TreeMap<>();
//    }
//    
//    /**
//     * Creates planar topology.
//     * Fill the raster voronoi with the nearest patch id.
//     * @param patches
//     * @param voronoi an empty raster
//     * @param grid2space
//     * @param resolution
//     * @return the planar topology
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    private static PlanarLinks neighborhoodEuclid(List<DefaultFeature> patches, final WritableRaster voronoi, 
//            final AffineTransformation grid2space, double resolution) throws IOException, SchemaException {
//        final STRtree index = new STRtree();
//        final int NPOINTS = 50;
//        final GeometryFactory factory = new GeometryFactory();
//        ProgressBar monitor = Config.getProgressBar(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Neighbor"), voronoi.getHeight());
//        for(Feature f : patches) {
//            Geometry geom = f.getGeometry();
//            if(geom.getNumPoints() > NPOINTS) {
//                for(int i = 0; i < geom.getNumGeometries(); i++) {
//                    Polygon p = (Polygon)geom.getGeometryN(i);
//                    Coordinate [] coords = p.getExteriorRing().getCoordinates();
//                    int ind = 0;
//                    while(ind < coords.length-1) {
//                        LineString line = factory.createLineString(Arrays.copyOfRange(coords, ind,
//                                ind+NPOINTS+1 >= coords.length-1 ? coords.length : ind+NPOINTS+1));
//                        DefaultFeature df = new DefaultFeature(f.getId(), line, null, null);
//                        index.insert(line.getEnvelopeInternal(), df);
//                        ind += NPOINTS;
//                    }
//                    for(int j = 0; j < p.getNumInteriorRing(); j++) {
//                        index.insert(p.getInteriorRingN(j).getEnvelopeInternal(),
//                                new DefaultFeature(f.getId(), p.getInteriorRingN(j), null, null));
//                    }
//                }
//            } else {
//                index.insert(geom.getEnvelopeInternal(), f);
//            }
//        }
//        
//        index.build();
//        
//        final ItemDistance distComp = new ItemDistance() {
//            @Override
//            public double distance(ItemBoundable i1, ItemBoundable i2) {
//                Geometry g1 = ((Feature)i1.getItem()).getGeometry();
//                Geometry g2 = ((Feature)i2.getItem()).getGeometry();
//                Coordinate c = g1 instanceof Point ? ((Point)g1).getCoordinate() : ((Point)g2).getCoordinate();
//                Geometry geom = g1 instanceof Point ? g2 : g1;
//                if(geom instanceof LineString) {
//                    return DistanceOp.distancePointLine(c, geom.getCoordinates());
//                } else {
//                    double d = Double.MAX_VALUE;
//                    for(int g = 0; g < geom.getNumGeometries(); g++) {
//                        Polygon poly = ((Polygon)geom.getGeometryN(g));
//                        double dd = DistanceOp.distancePointLine(c, poly.getExteriorRing().getCoordinates());
//                        if(dd < d) {
//                            d = dd;
//                        }
//                        int n = poly.getNumInteriorRing();
//                        for(int i = 0; i < n; i++) {
//                            dd = DistanceOp.distancePointLine(c, poly.getInteriorRingN(i).getCoordinates());
//                            if(dd < d) {
//                                d = dd;
//                            }
//                        }
//                    }
//                    return d;
//                }
//            }
//        };
//
//        AbstractParallelTask task = new AbstractParallelTask<Void, Void>(monitor) {
//            @Override
//            public Void execute(int start, int end) {
//                Coordinate c = new Coordinate();
//                Envelope env = new Envelope();
//        
//                for(int y = start; y < end; y++) {
//                    for(int x = 0; x < voronoi.getWidth(); x++) {
//                        if(voronoi.getSample(x, y, 0) == 0) {
//                            c.x = x+0.5; c.y = y+0.5;
//                            grid2space.transform(c, c);
//                            env.init(c);
//                            DefaultFeature cell = new DefaultFeature("c", factory.createPoint(c));
//                            Feature nearest = null;
//                            int k = 5;
//                            int i = k;
//                            // loop while all k nearest features have equal distance (eps : res/100)
//                            while(i == k) {
//                                k *= 2;
//                                Object[] nearests = index.nearestNeighbour(env, cell, distComp, k);
//                                nearest = (Feature)nearests[0];
//                                double dMin = cell.getGeometry().distance(nearest.getGeometry());
//                                i = 1;
//                                while(Math.abs(dMin - cell.getGeometry().distance(((Feature)nearests[i]).getGeometry())) < resolution/100 && i < k) {
//                                    if((int)((Feature)nearests[i]).getId() < (int)nearest.getId()) {
//                                        nearest = (Feature)nearests[i];
//                                    }
//                                    i++;
//                                }
//                            }
//                            voronoi.setSample(x, y, 0, (Integer)nearest.getId());
//                        }
//                    }
//                    incProgress(1);
//                }
//                return null;
//            }
//
//            @Override
//            public int getSplitRange() {
//                return voronoi.getHeight();
//            }
//
//            @Override
//            public void gather(Void results) {
//            }
//            @Override
//            public Void getResult() {
//                throw new UnsupportedOperationException(); 
//            }
//        };
//        
//        long time = System.currentTimeMillis();
//        
//        ExecutorService.execute(task);
//
//        System.out.println("Temps calcul : " + (System.currentTimeMillis() - time) / 1000);
//
//        PlanarLinks links = createLinks(voronoi, patches, monitor);
//
//        monitor.close();
//
//        return links;
//    }
//
//    /**
//     * @return the costs associated with a existing Linkset if any, null otherwise
//     */
//    public double[] getLastCosts() {
//        for(Linkset cost : costLinks.values()) {
//            if(cost.getType_dist() == Linkset.COST) {
//                return cost.getCosts();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * @return the planar topology
//     */
//    public PlanarLinks getPlanarLinks() {
//        if(planarLinks == null) {
//            throw new IllegalStateException("The project does not contain voronoi.");
//        }
//        return planarLinks;
//    }
//
//    /**
//     * @param linkName a linkset name
//     * @return the linkset named linkName or null if it does not exist
//     */
//    public Linkset getLinkset(String linkName) {
//        if(linkName == null) {
//            return null;
//        } else {
//            return costLinks.get(linkName);
//        }
//    }
//
//    /**
//     * @return all linkset names
//     */
//    public Set<String> getLinksetNames() {
//        return costLinks.keySet();
//    }
//    
//    /**
//     * @return all linksets
//     */
//    public Collection<Linkset> getLinksets() {
//        return costLinks.values();
//    }
//
//    /**
//     * @return the project's name
//     */
//    public String getName() {
//        return name;
//    }
//
//    /**
//     * Create the planar topology from the voronoi calculated by {@link #neighborhoodEuclid }
//     * @param voronoiRaster
//     * @param patches
//     * @param monitor
//     * @return 
//     */
//    private static PlanarLinks createLinks(Raster voronoiRaster, List<DefaultFeature> patches, ProgressBar monitor) {
//        monitor.setNote("Planar topology...");
//        monitor.setProgress(0);
//
//        Path.newSetOfPaths();
//        
//        PlanarLinks links = new PlanarLinks(patches.size());
//
//        for(int y = 1; y < voronoiRaster.getHeight()-1; y++) {
//            monitor.setProgress(y);
//            for(int x = 1; x < voronoiRaster.getWidth()-1; x++) {
//                int id = voronoiRaster.getSample(x, y, 0);
//                if(id <= 0) {
//                    continue;
//                }
//
//                Feature f = patches.get(id-1);
//                int id1 = voronoiRaster.getSample(x-1, y, 0);
//                if(id1 > 0 && id != id1) {
//                    Feature f1 = patches.get(id1-1);
//                    if(!links.isLinkExist(f, f1)) {
//                        links.addLink(new Path(f, f1));
//                    }
//                }
//                id1  = voronoiRaster.getSample(x, y-1, 0);
//                if(id1 > 0 && id != id1) {
//                    Feature f1 = patches.get(id1-1);
//                    if(!links.isLinkExist(f, f1)) {
//                        links.addLink(new Path(f, f1));
//                    }
//                }
//            }
//        }
//
//        return links;
//    }
//
//    /**
//     * Calculates the new linkset and add it to the project
//     * @param linkset the linkset to create
//     * @param save save the linkset and the project ?
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public void addLinkset(Linkset linkset, boolean save) throws IOException, SchemaException {
//        ProgressBar progressBar = Config.getProgressBar();
//        
//        if(linkset.getProject() == null) {
//            linkset.setProject(this);
//        }
//        
//        linkset.compute(progressBar);
//
//        
//        if(save) {
//            if(linkset.isRealPaths() && !linkset.getPaths().isEmpty()) {
//                IOFeature.saveFeatures(linkset.getPaths(), new File(dir, linkset.getName() + "-links.shp"), getCRS());
//                linkset.saveIntraLinks();
//            }
//            linkset.saveLinks();
//        }
//        
//        costLinks.put(linkset.getName(), linkset);
//        
//        if(save) {
//            save();
//        }
//        
//        if(linkLayers != null) {
//            Layer l = new LinkLayer(linkset);
//            if(linkset.getTopology() == Linkset.COMPLETE && linkset.getDistMax() == 0) {
//                l.setVisible(false);
//            }
//            linkLayers.addLayerFirst(l);
//        }
//        
//        progressBar.close();
//    }
//    
//    
//    /**
//     * Removes the linkset from the project and saves the project.
//     * @param linkset the linkset to remove
//     * @param force remove graphs using this linkset ?
//     * @throws IOException 
//     * @throws IllegalStateException if the linkset is used by pointsets or if force == false and the linkset is used by graphs
//     */
//    public void removeLinkset(Linkset linkset, boolean force) throws IOException {
//        List<String> exoNames = new ArrayList<>();
//        for (Pointset exo : getPointsets()) {
//            if (exo.getLinkset().getName().equals(linkset.getName())) {
//                exoNames.add(exo.getName());
//            }
//        }
//        if (!exoNames.isEmpty()) {
//            throw new IllegalStateException(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Links_is_used_by_exogenous_data") + Arrays.deepToString(exoNames.toArray()));
//        }
//        
//        List<String> graphNames = new ArrayList<>();
//        for (GraphGenerator g : getGraphs()) {
//            if (g.getLinkset().getName().equals(linkset.getName())) {
//                graphNames.add(g.getName());
//            }
//        }
//        if(!graphNames.isEmpty() && !force) {
//            throw new IllegalStateException("The linkset is used by the graphs : " + Arrays.deepToString(exoNames.toArray()));
//        }
//        for(String gName : graphNames) {
//            removeGraph(gName);
//        }
//        
//        FileFilter filter = (File f) -> f.getName().startsWith(linkset.getName() + "-links.") && f.getName().length()-3 == (linkset.getName() + "-links.").length()
//                || f.getName().equals(linkset.getName() + "-links-intra.csv");
//        for(File f : dir.listFiles(filter)) {
//            f.delete();
//        }
//        
//        costLinks.remove(linkset.getName());
//        save();
//    }
//    
//    
//    /**
//     * Calculates and add a new pointset to the project
//     * @param pointset the definition of the new point set
//     * @param attrNames the attributes to retain from the point features
//     * @param features the point features
//     * @param save save the pointset and the project ?
//     * @throws SchemaException
//     * @throws IOException 
//     */
//    public void addPointset(Pointset pointset, List<String> attrNames, List<DefaultFeature> features, boolean save) throws SchemaException, IOException {
//        for(Feature f : features){
//            Coordinate c = f.getGeometry().getCoordinate();
//            if(!zone.contains(c.x, c.y)) {
//                throw new RuntimeException("Point outside zone !");
//            }
//        }
//
//        attrNames = new ArrayList<>(attrNames);
//        attrNames.remove(Project.EXO_IDPATCH);
//        attrNames.remove(Project.EXO_COST);
//
//        if(pointset.isAgreg()) {
//            for(String attr : attrNames) {
//                DefaultFeature.addAttribute(pointset.getName() + "." + attr, patches, Double.NaN);
//            }
//            DefaultFeature.addAttribute(pointset.getName() + ".NbPoint", patches, 0);
//        }
//
//        // recréé les features avec les 2 attributs en plus
//        List<DefaultFeature> tmpLst = new ArrayList<>(features.size());
//        List<String> attrs = new ArrayList<>(attrNames);
//        attrs.add(Project.EXO_IDPATCH);
//        attrs.add(Project.EXO_COST);
//        for(Feature f : features) {
//            List lst = new ArrayList(attrs.size());
//            for(String attr : attrNames) {
//                Object v = f.getAttribute(attr);
//                if(v instanceof String) {
//                    v = Double.parseDouble(v.toString());
//                }
//                lst.add(v);
//            }
//            lst.add(-1); lst.add(-1.0);
//            tmpLst.add(new DefaultFeature(f.getId(), f.getGeometry(), attrs, lst));
//        }
//        features = tmpLst;
//
//        ProgressBar monitor = Config.getProgressBar("Add point set", 2*features.size());
//
//        for(DefaultFeature f : features) {
//            Envelope env = f.getGeometry().getEnvelopeInternal();
//            env.expandBy(1);
//            int nb = 0;
//            List patch = getPatchIndex().query(env);
//            for(Object p : patch) {
//                DefaultFeature fp = (DefaultFeature) p;
//                if(fp.getGeometry().intersects(f.getGeometry())) {
//                   f.setAttribute(EXO_IDPATCH, fp.getId());
//                   f.setAttribute(EXO_COST, 0);
//                   nb++;
//                }
//            }
//            if(nb > 1) {
//                Logger.getLogger(Project.class.getName()).log(Level.WARNING, "Point intersect " + nb + "patches !!");
//            }
//            monitor.incProgress(1);
//        }
//
////        monitor.setNote("Point outside patch...");
//
//        Linkset linkset = pointset.getLinkset();
//        boolean circuit = false;
//        if(linkset.getType_dist() == Linkset.CIRCUIT) {
//            linkset = linkset.getCostVersion();
//            circuit = true;
//        }
//        SpacePathFinder pathFinder = getPathFinder(linkset);
//
//        int nErr = 0;
//        for(DefaultFeature f : features) {
//            monitor.incProgress(1);
//            if(monitor.isCanceled()) {
//                monitor.setNote(monitor.getNote() + " - canceled");
//                return;
//            }
//            if(((Number)f.getAttribute(EXO_IDPATCH)).intValue() == -1) {
//                try {
//                    double [] res = pathFinder.calcPathNearestPatch((Point)f.getGeometry());
//                    DefaultFeature p = getPatch((int)res[0]);
//                    double cost = linkset.isCostLength() ? res[1] : res[2];
//                    f.setAttribute(EXO_IDPATCH, p.getId());
//                    f.setAttribute(EXO_COST, circuit ? 0 : cost);
//                } catch(Exception e) {
//                    nErr++;
//                    Logger.getLogger(Project.class.getName()).log(Level.WARNING, "Chemin non calculé pour le point " + f.getId(), e);
//                    continue;
//                }
//            }
//            
//            if(pointset.isAgreg()) {
//                double alpha = -Math.log(0.05) / pointset.getMaxCost();
//                HashMap<DefaultFeature, Path> distPatch = pathFinder.calcPaths(f.getGeometry().getCoordinate(), pointset.getMaxCost(), false);
//                for(DefaultFeature p : distPatch.keySet()) {
//                    for(String attr : attrNames) {
//                       double val = ((Number)p.getAttribute(pointset.getName() + "." + attr)).doubleValue();
//                       if(Double.isNaN(val)) {
//                           val = 0;
//                       }
//                       double dist = pointset.getLinkset().isCostLength() ? distPatch.get(p).getCost() : distPatch.get(p).getDist();
//                       double s = 0;
//                       if(f.getAttribute(attr) != null) {
//                           s = ((Number)f.getAttribute(attr)).doubleValue() * Math.exp(-alpha*dist);
//                       }
//                       p.setAttribute(pointset.getName() + "." + attr, s + val);
//                   }
//                   p.setAttribute(pointset.getName() + ".NbPoint", 1+(Integer)p.getAttribute(pointset.getName() + ".NbPoint"));
//                }
//            }
//            
//        }
//
//        List<DefaultFeature> exoFeatures = new ArrayList<>();
//        for(DefaultFeature f : features) {
//            if(((Number)f.getAttribute(EXO_IDPATCH)).intValue() > 0) {
//                exoFeatures.add(f);
//            }
//        }
//
//        pointset.setFeatures(exoFeatures);
//        exoDatas.put(pointset.getName(), pointset);
//        
//        if(save) {
//            IOFeature.saveFeatures(exoFeatures, new File(dir, "Exo-" + pointset.getName() + ".shp"));
//            savePatch();
//            save();
//        }
//
//        if(exoLayers != null) {
//            exoLayers.setExpanded(true);
//            exoLayers.addLayerFirst(new PointsetLayer(pointset, this));
//        }
//        monitor.close();
//
//        if(nErr > 0) {
//            JOptionPane.showMessageDialog(null, nErr + " points have been removed -> no path found.");
//        }
//    }
//
//    /**
//     * Creates the layers of the project if not already created.
//     * @return the root group layer of the project
//     */
//    public synchronized DefaultGroupLayer getRootLayer() {
//        if(rootLayer == null) {
//            createLayers();
//        }
//        return rootLayer;
//    }
//    
//    /**
//     * Creates the layers of the project if not already created.
//     * @return the group layer of the linksets
//     */
//    public synchronized DefaultGroupLayer getLinksetLayers() {
//        if(linkLayers == null) {
//            createLayers();
//        }
//        return linkLayers;
//    }
//    
//    /**
//     * Creates the layers of the project if not already created.
//     * @return the group layer of the graphs
//     */
//    public synchronized DefaultGroupLayer getGraphLayers() {
//        if(graphLayers == null) {
//            createLayers();
//        }
//        return graphLayers;
//    }
//    
//    /**
//     * Creates the layers of the project if not already created.
//     * @return the group layer of the pointsets
//     */
//    public synchronized DefaultGroupLayer getPointsetLayers() {
//        if(exoLayers == null) {
//            createLayers();
//        }
//        return exoLayers;
//    }
//    
//    /**
//     * Creates the layers of the project if not already created.
//     * @return the group layer for other analysis
//     */
//    public synchronized DefaultGroupLayer getAnalysisLayer() {
//        if(analysisLayers == null) {
//            analysisLayers = new DefaultGroupLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle")
//                                                        .getString("MainFrame.analysisMenu.text"), true);
//            getRootLayer().addLayerLast(analysisLayers);
//        }
//        return analysisLayers;
//    }
//
//    /**
//     * Add a layer to the root project layer
//     * @param l the layer to add
//     */
//    public void addLayer(Layer l) {
//        getRootLayer().addLayerLast(l);
//    }
//
//    /**
//     * Remove a layer from the root project layer
//     * @param l the layer to remove
//     */
//    public void removeLayer(Layer l) {
//        getRootLayer().removeLayer(l);
//    }

	/**
	 * Creates the index if it does not exist already
	 * 
	 * @return a spatial index on patch features
	 */
	public synchronized STRtree getPatchIndex() {
		if (patchIndex == null) {
			patchIndex = new STRtree();
			for (Feature f : getPatches()) {
				patchIndex.insert(f.getGeometry().getEnvelopeInternal(), f);
			}
		}

		return patchIndex;
	}

//
	/**
	 * @param id the identifier of the patch
	 * @return the patch with identifier id
	 * @throws IllegalArgumentException if the patch does not exist
	 */
	public final DefaultFeature getPatch(int id) {
		if (id > 0 && id <= patches.size()) {
			return patches.get(id - 1);
		} else {
			throw new IllegalArgumentException("Unknown patch id : " + id);
		}
	}

//
//    /**
//     * The voronoi polygon associated with the patch id
//     * @param id the patch identifier
//     * @return a polygon feature representing the voronoi polygon of the patch id
//     */
//    public final Feature getVoronoi(int id) {
//        return getVoronoi().get(id-1);
//    }
//
//    /**
//     * @return the boundary of the map
//     */
//    public Rectangle2D getZone() {
//        return zone;
//    }
//
	/**
	 * The patches are ordered by their id. list(0) : patch id 1 list(n-1) : patch
	 * id n
	 * 
	 * @return all the patches
	 */
	public List<DefaultFeature> getPatches() {
		return patches;
	}

//
//    /**
//     * @return the landscape map codes
//     */
//    public TreeSet<Integer> getCodes() {
//        return codes;
//    }
//
//    public boolean isMerge() {
//        return merge;
//    }
//    
//
//    /**
//     * Creates a pathfinder for the given linkset
//     * @param linkset a linkset
//     * @return a new pathfinder
//     * @throws IOException 
//     * @throws IllegalArgumentException if the linkset is a circuit linkset
//     */
//    public SpacePathFinder getPathFinder(Linkset linkset) throws IOException {
//        if(linkset.getType_dist() == Linkset.CIRCUIT) {
//            throw new IllegalArgumentException("Circuit linkset is not supported");
//        }
//        return linkset.getType_dist() == Linkset.EUCLID ?
//            new EuclidePathFinder(this) : getRasterPathFinder(linkset);
//    }
//    
//    /**
//     * Creates a raster pathfinder for the given linkset
//     * @param linkset a linkset of COST type
//     * @return a new raster pathfinder
//     * @throws IOException 
//     * @throws IllegalArgumentException if the linkset if not of type COST
//     */
//    public RasterPathFinder getRasterPathFinder(Linkset linkset) throws IOException {
//        if(linkset.getType_dist() != Linkset.COST) {
//            throw new IllegalArgumentException();
//        }
//        if(linkset.isExtCost()) {
//            if(linkset.getExtCostFile().exists()) {
//                Raster extRaster = getExtRaster(linkset.getExtCostFile());
//                return new RasterPathFinder(this, extRaster, linkset.getCoefSlope());
//            } else {
//                throw new RuntimeException("Cost raster file " + linkset.getExtCostFile() + " not found");
//            }
//        } else {
//            return new RasterPathFinder(this, getImageSource(), linkset.getCosts(), linkset.getCoefSlope());
//        }
//    }
//    
//    /**
//     * Creates a raster circuit for the given linkset
//     * @param linkset a linkset of CIRCUIT type
//     * @return a new raster circuit
//     * @throws IOException 
//     * @throws IllegalArgumentException if the linkset if not of type CIRCUIT
//     */
//    public CircuitRaster getRasterCircuit(Linkset linkset) throws IOException {
//        if(linkset.getType_dist() != Linkset.CIRCUIT) {
//            throw new IllegalArgumentException();
//        }
//        if(linkset.isExtCost()) {
//            if(linkset.getExtCostFile().exists()) {
//                Raster extRaster = getExtRaster(linkset.getExtCostFile());
//                return new CircuitRaster(this, extRaster, true, linkset.isOptimCirc(), linkset.getCoefSlope());
//            } else {
//                throw new RuntimeException("Cost raster file " + linkset.getExtCostFile() + " not found");
//            }
//        } else {
//            return new CircuitRaster(this, getImageSource(), linkset.getCosts(), true, linkset.isOptimCirc(), linkset.getCoefSlope());
//        }
//    }
//
//    /**
//     * Creates and add the graph to the project
//     * @param graphGen the graph definition
//     * @param save save the graph and the project ?
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public void addGraph(GraphGenerator graphGen, boolean save) throws IOException, SchemaException {
//        ProgressBar progressBar = Config.getProgressBar(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Create_graph..."));
//        progressBar.setIndeterminate(true);
//        
//        if(save && hasVoronoi()) {
//            IOFeature.saveFeatures(graphGen.getComponentFeatures(),
//                    new File(dir, graphGen.getName() + "-voronoi.shp"), getCRS());
//
//            graphGen.setSaved(true);
//        }
//        
//        graphs.put(graphGen.getName(), graphGen);
//        if(save) {
//            save();
//        }
//
//        if(rootLayer != null) {
//            rootLayer.setLayersVisible(false);
//            graphLayers.setExpanded(true);
//            GraphGroupLayer gl = graphGen.getLayers();
//            gl.setExpanded(true);
//            graphLayers.addLayerFirst(gl);
//        }
//        
//        progressBar.close();
//    }
//
//    /**
//     * @param graphName a name of a graph
//     * @return the patch attribute names containing the graph name
//     */
//    public List<String> getGraphPatchAttr(String graphName) {
//        List<String> attrs = new ArrayList<>();
//        for(String attr : patches.get(0).getAttributeNames()) {
//            if(attr.endsWith("_" + graphName)) {
//                attrs.add(attr);
//            }
//        }
//        return attrs;
//    }
//
//    /**
//     * Returns the metrics detailed names calculated for graph graphName
//     * @param graphName a graph name
//     * @return the attribute names containing the graph name removing the graph name
//     */
//    public List<String> getGraphPatchVar(String graphName) {
//        List<String> attrs = new ArrayList<>();
//        for(String attr : patches.get(0).getAttributeNames()) {
//            if(attr.endsWith("_" + graphName)) {
//                attrs.add(attr.substring(0, attr.length() - graphName.length() - 1));
//            }
//        }
//        return attrs;
//    }
//
//    /**
//     * @param g a project graph
//     * @return the path attribute names containing the graph name
//     */
//    private List<String> getGraphLinkAttr(GraphGenerator g) {
//        List<String> attrs = new ArrayList<>();
//        for(String attr : g.getLinkset().getPaths().get(0).getAttributeNames()) {
//            if(attr.endsWith("_" + g.getName())) {
//                attrs.add(attr);
//            }
//        }
//        return attrs;
//    }
//
//    /**
//     * Removes a graph from the project and saves the project.
//     * Removes patch and path attributes associated with this graph.
//     * Deletes the voronoi shape file of the graph and removes the graph layer.
//     * @param name a name of a graph
//     */
//    public void removeGraph(final String name) {
//        GraphGenerator g = graphs.remove(name);
//
//        for(String attr : getGraphPatchAttr(name)) {
//            DefaultFeature.removeAttribute(attr, patches);
//        }
//
//        for(String attr : getGraphLinkAttr(g)) {
//            DefaultFeature.removeAttribute(attr, g.getLinkset().getPaths());
//        }
//
//        try {
//            g.getLinkset().saveLinks();
//            savePatch();
//            save();
//        } catch (IOException | SchemaException ex) {
//            Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
//        }
//
//        FileFilter filter = (File f) -> 
//                f.getName().startsWith(name + "-voronoi.") && f.getName().length()-3 == (name + "-voronoi.").length()
//                || f.getName().startsWith(name + "-topo_links.") && f.getName().length()-3 == (name + "-topo_links.").length();
//        for(File f : dir.listFiles(filter)) {
//            f.delete();
//        }
//
//        if(graphLayers != null) {
//            for(Layer l : new ArrayList<>(graphLayers.getLayers())) {
//                if(l.getName().equals(name)) {
//                    graphLayers.removeLayer(l);
//                }
//            }
//        }
//    }
//
//    /**
//     * @return all graph in the project
//     */
//    public Collection<GraphGenerator> getGraphs() {
//        return graphs.values();
//    }
//    
//    /**
//     * Return the corresponding graph.
//     * If the graph name contains "!", creates a new graph removing patches and paths following "!"
//     * @param name the name of a graph
//     * @return the graph 
//     * @throws IllegalArgumentException if the graph does not exist
//     */
//    public GraphGenerator getGraph(String name) {
//        if(graphs.containsKey(name)) {
//            return graphs.get(name);
//        }
//        if(name.contains("!")) {
//            String[] split = name.split("!");
//            return new GraphGenerator(graphs.get(split[0]), split[1], split[2]);
//        }
//        throw new IllegalArgumentException("Unknown graph " + name);
//    }
//    
//    /**
//     * @return all graph names
//     */
//    public Set<String> getGraphNames() {
//        return graphs.keySet();
//    }
//
//    /**
//     * @return all pointset names
//     */
//    public Set<String> getPointsetNames() {
//        return exoDatas.keySet();
//    }
//    
//    /**
//     * @param name a pointset name
//     * @return the poinset or null
//     */
//    public Pointset getPointset(String name) {
//        return exoDatas.get(name);
//    }
//
//    /**
//     * @return all pointsets
//     */
//    public Collection<Pointset> getPointsets() {
//        return exoDatas.values();
//    }
//
//    /**
//     * Removes a pointset from the project and saves the project.
//     * Removes patch attributes associated with this pointset.
//     * Deletes the pointset shapefile
//     * @param name a pointset name
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public void removePointset(final String name) throws IOException, SchemaException {
//        exoDatas.remove(name);
//
//        List<String> attrs = new ArrayList<>();
//        for(String attr : patches.get(0).getAttributeNames()) {
//            if(attr.startsWith(name + ".")) {
//                attrs.add(attr);
//            }
//        }
//        
//        for(String attr : attrs) {
//            DefaultFeature.removeAttribute(attr, patches);
//        }
//
//        savePatch();
//        save();
//
//        FileFilter filter = new FileFilter() {
//            @Override
//            public boolean accept(File f) {
//                return f.getName().startsWith("Exo-" + name + ".") && f.getName().length()-3 == ("Exo-" + name + ".").length();
//            }
//        };
//        for(File f : dir.listFiles(filter)) {
//            f.delete();
//        }
//    }
//
//    /**
//     * @return true if the project has voronoi ie. planar topology
//     */
//    public boolean hasVoronoi() {
//        return new File(dir, VORONOI_SHAPE).exists();
//    }
//    
//    /**
//     * @return the voronoi features 
//     * @throws IllegalStateException if the project has no voronoi
//     */
//    public synchronized List<Feature> getVoronoi() {
//        if(voronoi == null) {
//            File file = new File(dir, VORONOI_SHAPE);
//            if(!file.exists()) {
//                throw new IllegalStateException("The project does not contain voronoi.");
//            }
//            try {
//                List<DefaultFeature> features = GlobalDataStore.getFeatures(file, "Id", null);
//                voronoi = new ArrayList<Feature>(features);
//                for(Feature f : features) {
//                    voronoi.set((Integer)f.getId()-1, f);
//                }
//            } catch (IOException ex) {
//                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//
//        return voronoi;
//    }
//
//    /**
//     * Loads the polygon features representing components of the graph, based on voronoi polygon.
//     * Loads the shapefile and the CSV file if exists, containing component metric results.
//     * @param name a name of a graph
//     * @return the polygon features
//     * @throws IOException 
//     */
//    public List<DefaultFeature> loadVoronoiGraph(String name) throws IOException {
//        List<DefaultFeature> features = GlobalDataStore.getFeatures(new File(dir, name + "-voronoi.shp"), "Id", null);
//
//        HashMap<Object, DefaultFeature> map = new HashMap<>();
//        for(DefaultFeature f : features) {
//            map.put(f.getId(), f);
//        }
//
//        File fCSV = new File(dir, name + "-voronoi.csv");
//        if(fCSV.exists()) {
//            try (CSVReader r = new CSVReader(new FileReader(fCSV))) {
//                String [] attr = r.readNext();
//                for(int i = 0; i < attr.length; i++) {
//                    if(features.get(0).getAttributeNames().contains(attr[i])) {
//                        attr[i] = null;
//                    } else {
//                        DefaultFeature.addAttribute(attr[i], features, Double.NaN);
//                    }
//                }
//                String [] tab = r.readNext();
//                while(tab != null) {
//                    DefaultFeature f = map.get(Integer.parseInt(tab[0]));
//                    for(int i = 1; i < tab.length; i++) {
//                        if(attr[i] != null) {
//                            if(tab[i].isEmpty()) {
//                                f.setAttribute(attr[i], null);
//                            } else {
//                                f.setAttribute(attr[i], Double.parseDouble(tab[i]));
//                            }
//                        }
//                    }
//                    tab = r.readNext();
//                }
//            }
//        }
//
//        return features;
//    }
//
//    /**
//     * If it is not already loaded, load the raster.
//     * @return the raster patch
//     */
//    public synchronized WritableRaster getRasterPatch() {
//        WritableRaster raster = patchRaster != null ? patchRaster.get() : null;
//        if(raster == null) {
//            try {
//                RenderedImage img = IOImage.loadTiffWithoutCRS(new File(dir, PATCH_RASTER)).getRenderedImage();
//                if(img.getNumXTiles() == 1 && img.getNumYTiles() == 1) {
//                    raster = (WritableRaster) img.getTile(0, 0);
//                } else {
//                    raster = (WritableRaster) img.getData();
//                }
//                patchRaster = new SoftRef<>(raster);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//        
//        return raster;
//    }
//
//    /**
//     * If it is not already loaded, load the landscape map.
//     * @return the landscape raster map
//     */
//    public synchronized WritableRaster getImageSource() {
//        WritableRaster raster = srcRaster != null ? srcRaster.get() : null;
//        if(raster == null) {
//            try {
//                WritableRaster r = IOImage.loadTiffWithoutCRS(new File(dir, LAND_RASTER)).getRenderedImage().copyData(null);
//                raster = r.createWritableTranslatedChild(1, 1);
//                srcRaster = new SoftRef<>(raster);
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//        }
//        return raster;
//    }
//
//    /**
//     * Saves the csv file containing component metric results of the graph.
//     * @param graph a graph name
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public void saveGraphVoronoi(String graph) throws IOException, SchemaException {
//        List<DefaultFeature> comps = graphs.get(graph).getComponentFeatures();
//        try (CSVWriter w = new CSVWriter(new FileWriter(new File(dir, graph + "-voronoi.csv")))) {
//            List<String> header = new ArrayList<>();
//            header.add("Id");
//            header.addAll(comps.get(0).getAttributeNames());
//            w.writeNext(header.toArray(new String[0]));
//            
//            String [] tab = new String[comps.get(0).getAttributeNames().size()+1];
//            for(Feature f : comps) {
//                tab[0] = f.getId().toString();
//                for(int i = 1; i < tab.length; i++) {
//                    tab[i] = f.getAttribute(i-1).toString();
//                }
//                w.writeNext(tab);
//            }
//        }
//    }
//
//    /**
//     * Saves the csv file containing patch metric results.
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public void savePatch() throws IOException, SchemaException {
//        List<String> attrNames = new ArrayList<>(patches.get(0).getAttributeNames());
//        attrNames.add(0, "Id");
//        try (CSVWriter w = new CSVWriter(new FileWriter(new File(dir, "patches.csv")))) {
//            w.writeNext(attrNames.toArray(new String[0]));
//            String [] tab = new String[attrNames.size()];
//            for(Feature f : getPatches()) {
//                for(int i = 0; i < tab.length; i++) {
//                    if(i == 0) {
//                        tab[i] = f.getId().toString();
//                    } else {
//                        tab[i] = f.getAttribute(i-1).toString();
//                    }
//                }
//                w.writeNext(tab);
//            }
//        }
//    }
//
//    /**
//     * Saves the xml project file.
//     * @throws IOException 
//     */
//    public void save() throws IOException {
//        try (FileWriter fw = new FileWriter(getProjectFile())) {
//            getXStream().toXML(this, fw);
//        }
//    }
//    
//    /**
//     * Creates a new project based on this one by aggregating patches in meta patch.
//     * The project is created in a subfolder of this project.
//     * In the landscape map, patch codes that are not contained in patch any more are recoded to maxcode+1
//     * @param prjName the new project name
//     * @param graph the graph for aggregating patches
//     * @param alpha the exponential coefficient for distance decreasing or zero
//     * @param minCapa the minimum capacity for metapatch
//     * @return the new project file
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public File createMetaPatchProject(String prjName, GraphGenerator graph, double alpha, double minCapa) throws IOException, SchemaException {
//        ProgressBar monitor = Config.getProgressBar("Meta patch...");
//        monitor.setIndeterminate(true);
//        File dir = new File(getDirectory(), prjName);
//        dir.mkdir();
//        
//        List<Graph> components = new ArrayList<>(graph.getComponents());
//        int[] idMetaPatch = new int[getPatches().size()+1];
//        Iterator<Graph> it = components.iterator();
//        int ind = 1;
//        boolean remPatch = false;
//        while(it.hasNext()) {
//            Graph comp = it.next();
//            double capa = 0;
//            for(Node n : (Collection<Node>)comp.getNodes()) {
//                capa += getPatchCapacity(n);
//            }
//            int id;
//            if(capa >= minCapa) {
//                id = ind++;
//            } else {
//                id = 0;
//                it.remove();
//                remPatch = true;
//            }
//            for(Node n : (Collection<Node>)comp.getNodes()) {
//                idMetaPatch[(Integer)((Feature)n.getObject()).getId()] = id;
//            }
//        }
//        // create new raster patch
//        WritableRaster newRaster = getRasterPatch().createCompatibleWritableRaster();
//        newRaster.setRect(getRasterPatch());
//        DataBufferInt buf = (DataBufferInt) newRaster.getDataBuffer();
//        for(int i = 0; i < buf.getSize(); i++) {
//            if(buf.getElem(i) <= 0) {
//                continue;
//            }
//            buf.setElem(i, idMetaPatch[buf.getElem(i)]);
//        }
//
//        GridCoverage2D gridCov = new GridCoverageFactory().create("rasterpatch",
//                newRaster, new Envelope2D(getCRS(), zone));
//        IOImage.saveTiffCoverage(new File(dir, PATCH_RASTER), gridCov);
//        
//        TreeSet<Integer> newCodes = new TreeSet<>(codes);
//        // recode les anciens patchs
//        if(remPatch) {
//            WritableRaster rasterPatch = getRasterPatch();
//            GridCoverage2D landCov = IOImage.loadTiff(new File(getDirectory(), LAND_RASTER));
//            WritableRaster land = (WritableRaster) landCov.getRenderedImage().getData();
//            int newCode = codes.last()+1;
//            for(int y = 0; y < rasterPatch.getHeight(); y++) {
//                for(int x = 0; x < rasterPatch.getWidth(); x++) {
//                    int id = rasterPatch.getSample(x, y, 0);
//                    if(id <= 0) {
//                        continue;
//                    }
//                    if(idMetaPatch[id] == 0) {
//                        land.setSample(x-1, y-1, 0, newCode);
//                    }
//                }
//
//            }
//            gridCov = new GridCoverageFactory().create("land", land, landCov.getEnvelope2D());
//            IOImage.saveTiffCoverage(new File(dir, LAND_RASTER), gridCov);
//            newCodes.add(newCode);
//        } else {
//            IOFile.copyFile(new File(getDirectory(), LAND_RASTER), new File(dir, LAND_RASTER));
//            File f = new File(getDirectory(), "source.tfw");
//            if(f.exists()) {
//                IOFile.copyFile(f, new File(dir, "source.tfw"));
//            }
//        }
//        
//        List<DefaultFeature> metaPatches = new ArrayList<>();
//        List<DefaultFeature> metaVoronois = new ArrayList<>();
//        // create patches and voronoi features
//        for(int i = 0; i < components.size(); i++) {
//            Graph comp = components.get(i);
//            List<Geometry> metaPatch = new ArrayList<>();
//            List<Geometry> metaVoronoi = new ArrayList<>();
//            double capa = 0;
//            for(Node n : (Collection<Node>)comp.getNodes()) {
//                DefaultFeature patch = (DefaultFeature)n.getObject();
//                if(alpha > 0) {
//                    GraphPathFinder pathfinder = graph.getPathFinder(n);
//                    for(Node n2 : (Collection<Node>)comp.getNodes()) {
//                        capa += getPatchCapacity(n2)*Math.exp(-alpha*pathfinder.getCost(n2)) / comp.getNodes().size();
//                    }
//                } else {
//                    capa += getPatchCapacity(patch);
//                }
//                metaPatch.add(patch.getGeometry());
//                if(hasVoronoi()) {
//                    metaVoronoi.add(getVoronoi((Integer)patch.getId()).getGeometry());
//                }
//            }
//            Geometry patchGeom = JTS.flattenGeometryCollection(new GeometryFactory().buildGeometry(metaPatch));
//            metaPatches.add(new DefaultFeature(i+1, patchGeom, PATCH_ATTRS, 
//                    Arrays.asList(patchGeom.getArea(), patchGeom.getBoundary().getLength(), capa)));
//            if(hasVoronoi()) {
//                Geometry voronoiGeom = CascadedPolygonUnion.union(metaVoronoi);
//                metaVoronois.add(new DefaultFeature(i+1, voronoiGeom, new ArrayList<String>(0), new ArrayList(0)));
//            }
//        }
//        
//        IOFeature.saveFeatures(metaPatches, new File(dir, PATCH_SHAPE), getCRS());
//        if(!remPatch && hasVoronoi()) {
//            IOFeature.saveFeatures(metaVoronois, new File(dir, VORONOI_SHAPE), getCRS());
//        }
//        
//        if(hasVoronoi()) {
//            PlanarLinks links;
//            if(remPatch) {
//                links = neighborhoodEuclid(metaPatches, newRaster, grid2space, resolution);
//                metaVoronois = (List<DefaultFeature>) SpatialOp.vectorizeVoronoi(newRaster, grid2space);
//                IOFeature.saveFeatures(metaVoronois, new File(dir, VORONOI_SHAPE), getCRS());
//            } else {
//                links = new PlanarLinks(metaPatches.size());
//                for(Path p : planarLinks.getFeatures()) {
//                    int id1 = idMetaPatch[(Integer)p.getPatch1().getId()];
//                    int id2 = idMetaPatch[(Integer)p.getPatch2().getId()];
//                    DefaultFeature p1 = metaPatches.get(id1-1);
//                    DefaultFeature p2 = metaPatches.get(id2-1);
//                    if(id1 == id2 || links.isLinkExist(p1, p2)) {
//                        continue;
//                    }
//                    links.addLink(new Path(p1, p2));
//                }
//            }
//            if(!links.getFeatures().isEmpty()) {
//                IOFeature.saveFeatures(links.getFeatures(), new File(dir, LINKS_SHAPE), getCRS());
//            }
//        }
//        
//        Project prj = new Project(prjName, this);
//        prj.dir = dir;
//        prj.codes = newCodes;
//        prj.save();
//        monitor.close();
//        return prj.getProjectFile();
//    }
//    
//    /**
//     * Creates a new project based on this one by removing patches with capacity lower than minCapa.
//     * The project is created in a subfolder of this project.
//     * In the landscape map, patch codes that are not contained in patch any more are recoded to maxcode+1
//     * @param prjName the new project name
//     * @param minCapa the minimum capacity for the patch
//     * @return the new project file
//     * @throws IOException
//     * @throws SchemaException 
//     */
//    public File createProject(String prjName, double minCapa) throws IOException, SchemaException {
//        ProgressBar monitor = Config.getProgressBar("Create project...");
//        monitor.setIndeterminate(true);
//        File dir = new File(getDirectory(), prjName);
//        dir.mkdir();
//        
//        int [] idPatch = new int[patches.size()+1];
//        // create patches
//        List<DefaultFeature> patchList = new ArrayList<>();
//        int ind = 1;
//        for(Feature patch : patches) {
//            if(getPatchCapacity(patch) < minCapa) {
//                continue;
//            }
//            idPatch[(Integer)patch.getId()] = ind;
//            patchList.add(new DefaultFeature(ind, patch.getGeometry(), PATCH_ATTRS, 
//                    Arrays.asList(patch.getGeometry().getArea(), patch.getGeometry().getBoundary().getLength(), getPatchCapacity(patch))));
//            ind++;
//        }
//        
//        IOFeature.saveFeatures(patchList, new File(dir, PATCH_SHAPE), getCRS());
//        
//        // create new raster patch
//        WritableRaster newRaster = getRasterPatch().createCompatibleWritableRaster();
//        newRaster.setRect(getRasterPatch());
//        DataBufferInt buf = (DataBufferInt) newRaster.getDataBuffer();
//        for(int i = 0; i < buf.getSize(); i++) {
//            if(buf.getElem(i) <= 0) {
//                continue;
//            }
//            buf.setElem(i, idPatch[buf.getElem(i)]);
//        }
//
//        GridCoverage2D gridCov = new GridCoverageFactory().create("rasterpatch",
//                newRaster, new Envelope2D(getCRS(), zone));
//        IOImage.saveTiffCoverage(new File(dir, PATCH_RASTER), gridCov);
//        
//        TreeSet<Integer> newCodes = new TreeSet<>(codes);
//        // recode les anciens patchs
//        WritableRaster rasterPatch = getRasterPatch();
//        GridCoverage2D landCov = IOImage.loadTiff(new File(getDirectory(), LAND_RASTER));
//        WritableRaster land = (WritableRaster) landCov.getRenderedImage().getData();
//        int newCode = codes.last()+1;
//        for(int y = 0; y < rasterPatch.getHeight(); y++) {
//            for(int x = 0; x < rasterPatch.getWidth(); x++) {
//                int id = rasterPatch.getSample(x, y, 0);
//                if(id > 0 && idPatch[id] == 0) {
//                    land.setSample(x-1, y-1, 0, newCode);
//                }
//            }
//        }
//        gridCov = new GridCoverageFactory().create("land", land, landCov.getEnvelope2D());
//        IOImage.saveTiffCoverage(new File(dir, LAND_RASTER), gridCov);
//        newCodes.add(newCode);
//        
//        if(hasVoronoi()) {
//            PlanarLinks links = neighborhoodEuclid(patchList, newRaster, grid2space, resolution);
//            List<? extends Feature> voronois = SpatialOp.vectorizeVoronoi(newRaster, grid2space);
//            IOFeature.saveFeatures(voronois, new File(dir, VORONOI_SHAPE), getCRS());
//            
//            if(!links.getFeatures().isEmpty()) {
//                IOFeature.saveFeatures(links.getFeatures(), new File(dir, LINKS_SHAPE), getCRS());
//            }
//        }
//        
//        Project prj = new Project(prjName, this);
//        prj.dir = dir;
//        prj.codes = newCodes;
//        prj.save();
//        monitor.close();
//        return prj.getProjectFile();
//    }
//    
//    /**
//     * @return the xml project file
//     */
//    public File getProjectFile() {
//        return new File(dir, name + ".xml");
//    }
//
//    /**
//     * @return the directory of the project
//     */
//    public File getDirectory() {
//        return dir;
//    }
//
//    /**
//     * @return is patch extraction is 8 connex or 4 connex ?
//     */
//    public boolean isCon8() {
//        return con8;
//    }
//
//    /**
//     * @return the minimal area for patches
//     */
//    public double getMinArea() {
//        return minArea;
//    }
//
//    public double getMaxSize() {
//        return maxSize;
//    }
//
//    /**
//     * @return no data value of landscape map
//     */
//    public double getNoData() {
//        return noData;
//    }
//
//    /**
//     * Create project layers
//     */
//    private void createLayers() {
//        CoordinateReferenceSystem crs = getCRS();
//        rootLayer = new DefaultGroupLayer(name, true);
//        rootLayer.addLayerFirst(new PatchLayer(this));
//
//        linkLayers = new DefaultGroupLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Link_sets"));
//        rootLayer.addLayerFirst(linkLayers);
//       
//        RasterLayer lr = new RasterLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Landscape_map"), new RasterShape(
//            getImageSource().getParent(), zone, new RasterStyle(), true), crs);
//        lr.getStyle().setNoDataValue(noData);
//        lr.setVisible(false);
//        lr.setDrawLegend(false);
//        rootLayer.addLayerLast(lr);
//        
//        for(Linkset linkset : costLinks.values()) {
//            Layer l = new LinkLayer(linkset);
//            l.setVisible(false);
//            linkLayers.addLayerLast(l);
//        }
//
//        if(planarLinks != null) {
//            Layer l = new FeatureLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Voronoi_links"), 
//                    planarLinks.getFeatures(), new LineStyle(new Color(0xbcc3ac)), crs);
//            l.setVisible(false);
//            linkLayers.addLayerLast(l);
//        }
//        
//        graphLayers = new DefaultGroupLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Graphs"));
//        rootLayer.addLayerFirst(graphLayers);      
//        for(GraphGenerator g : graphs.values()) {
//            try {
//                GraphGroupLayer l = g.getLayers();
//                l.setLayersVisible(false);
//                graphLayers.addLayerLast(l);
//            } catch(Exception e) {
//                Logger.getLogger(Project.class.getName()).log(Level.WARNING, null, e);
//            }
//        }
//        
//        exoLayers = new DefaultGroupLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Exo_data"));
//        rootLayer.addLayerFirst(exoLayers);
//        for(Pointset dataset : exoDatas.values())  {
//            Layer l = new PointsetLayer(dataset, this);
//            l.setVisible(false);
//            exoLayers.addLayerLast(l);
//        }
//        
//        if(isDemExist()) {
//            if(getAbsoluteFile(demFile).exists()) {
//                try {
//                    GridCoverage2D g = IOImage.loadCoverage(getAbsoluteFile(demFile));
//                    RasterLayer l = new RasterLayer(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("DEM"), 
//                            new CoverageShape(g, new RasterStyle()));
//                    l.setVisible(false);
//                    l.setDrawLegend(false);
//                    rootLayer.addLayerLast(l);
//                } catch (IOException ex) {
//                    Logger.getLogger(Project.class.getName()).log(Level.WARNING, null, ex);
//                    JOptionPane.showMessageDialog(null, "DEM file cannot be loaded.", "DEM", JOptionPane.WARNING_MESSAGE);
//                }
//            } else {
//                demFile = null;
//            }
//        }
//        
//    }
//    
//    public static XStream getXStream() {
//        XStream xstream = Util.getXStream();
//        xstream.alias("Project", Project.class);
//        xstream.alias("Pointset", Pointset.class);
//        xstream.alias("Linkset", Linkset.class);
//        xstream.alias("Graph", GraphGenerator.class);
//        
//        return xstream;
//    }
//    
//    /**
//     * Loads a project.
//     * @param file the xml project file
//     * @param all preloads all linksets ?
//     * @return the loaded project 
//     * @throws IOException 
//     */
//    public static synchronized Project loadProject(File file, boolean all) throws IOException {
//        
//        XStream xstream = getXStream();
//        
//        Project prj;
//        try (FileReader fr = new FileReader(file)) {
//            prj = (Project) xstream.fromXML(fr);
//        }
//
//        prj.dir = file.getAbsoluteFile().getParentFile();
//        
//        ProgressBar monitor = Config.getProgressBar(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Loading_project..."), 
//                100*(2+prj.costLinks.size()) + 10*prj.exoDatas.size());
//        List<DefaultFeature> features = GlobalDataStore.getFeatures(new File(prj.dir, PATCH_SHAPE), 
//                "Id", monitor.getSubProgress(100));
//        prj.patches = new ArrayList<>(features);
//        for(DefaultFeature f : features) {
//            prj.patches.set((Integer)f.getId()-1, f);
//        }
//
//        File fCSV = new File(prj.dir, "patches.csv");
//        if(fCSV.exists()) {
//            try (CSVReader r = new CSVReader(new FileReader(fCSV))) {
//                List<String> attrNames = new ArrayList<>(Arrays.asList(r.readNext()));
//                attrNames.remove(0);
//                String [] tab = r.readNext();
//                while(tab != null) {
//                    int id = Integer.parseInt(tab[0]);
//                    DefaultFeature f = prj.getPatch(id);
//                    List values = new ArrayList();
//                    for(int i = 1; i < tab.length; i++) {
//                        values.add(Double.parseDouble(tab[i]));
//                    }
//                    
//                    prj.patches.set(id-1, new DefaultFeature(f.getId(),
//                            f.getGeometry(), attrNames, values));
//                    tab = r.readNext();
//                }
//            }
//        }
//
//        File linkFile = new File(prj.dir, LINKS_SHAPE);
//        if(linkFile.exists()) {
//            features = GlobalDataStore.getFeatures(linkFile, "Id", monitor.getSubProgress(100));
//            List<Path> paths = new ArrayList<>(features.size());
//            for(Feature f : features) {
//                paths.add(Path.loadPath(f, prj));
//            }
//
//            prj.planarLinks = new PlanarLinks(paths, prj.patches.size());
//        } else if(prj.patches.size() == 1) {
//            prj.planarLinks = new PlanarLinks(1);
//        }
//
//        for(Linkset linkset : prj.costLinks.values()) {
//            linkset.setProject(prj);
//            if(all) {
//                linkset.loadPaths(monitor.getSubProgress(100));
//            }
//        }
//
//        for(String name : prj.exoDatas.keySet()) {
//            prj.exoDatas.get(name).setFeatures(GlobalDataStore.getFeatures(
//                    new File(prj.dir,"Exo-" + name + ".shp"), "Id", monitor.getSubProgress(10)));
//        }
//        
//        prj.removedCodes = new HashMap<>();
//
//        monitor.close();
//        
//        return prj;
//    }
//
//    /**
//     * Loads a coverage.
//     * Supported formats are : tif, asc, and rst.
//     * Checks if the grid geometry matches the landscape map.
//     * @param file the file coverage
//     * @return the grid coverage
//     * @throws IOException 
//     * @throws IllegalArgumentException if the grid geometry does not match the landscape map
//     */
//    private GridCoverage2D loadCoverage(File file) throws IOException {
//        GridCoverage2D cov;
//        if(file.getName().toLowerCase().endsWith(".tif")) {
//            cov = IOImage.loadTiffWithoutCRS(file);
//        } else if(file.getName().toLowerCase().endsWith(".asc")) {
//            cov = IOImage.loadArcGrid(file);
//        } else {
//            cov = new RSTGridReader(file).read(null);
//        }
//
//        GridEnvelope2D grid = cov.getGridGeometry().getGridRange2D();
//        Envelope2D env = cov.getEnvelope2D();
//        double res = env.getWidth() / grid.getWidth();
//        if(res != resolution) {
//            throw new IllegalArgumentException(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Resolution_does_not_match."));
//        }
//        if(env.getWidth() != zone.getWidth() || env.getHeight() != zone.getHeight()) {
//            throw new IllegalArgumentException(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Raster_extent_does_not_match."));
//        }
//        if(Math.abs(env.getX() - zone.getX()) > res || Math.abs(env.getY() - zone.getY()) > res) {
//            throw new IllegalArgumentException(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Raster_position_does_not_match."));
//        }
//
//        return cov;
//    }
//    
//    /**
//     * Loads an external raster.
//     * The raster is cached in soft reference to avoid multiple loading.
//     * Supported formats are : tif, asc, and rst.
//     * Checks if the grid geometry matches the landscape map.
//     * @param file the image file
//     * @return the raster
//     * @throws IOException 
//     */
//    public synchronized Raster getExtRaster(File file) throws IOException {
//        Raster raster = null;
//        
//        if(extRasters == null) {
//            extRasters = new HashMap<>();
//        }
//        if(extRasters.containsKey(file)) {
//            raster = extRasters.get(file).get();
//        }
//        
//        if(raster == null) {
//            raster = loadCoverage(file).getRenderedImage().getData();
//            raster = raster.createTranslatedChild(1, 1);
//            extRasters.put(file, new SoftRef<>(raster));
//        }
//        
//        return raster;
//    }
//
//    /**
//     * @return the area of the landscape map
//     */
//    public double getArea() {
//        return zone.getWidth()*zone.getHeight();
//    }
//
//    /**
//     * @param node a node of a graph
//     * @return the area of the patch associated with this graph node
//     */
//    public static double getPatchArea(org.geotools.graph.structure.Node node) {
//        return getPatchArea((Feature)node.getObject());
//    }
//
//    /**
//     * @param patch a patch
//     * @return the area of the patch 
//     */
//    public static double getPatchArea(Feature patch) {
//        return ((Number)patch.getAttribute(Project.AREA_ATTR)).doubleValue();
//    }
//
//    /**
//     * @return the sum of the capacity of all patches
//     */
//    public synchronized double getTotalPatchCapacity() {
//        if(totalPatchCapacity == 0) {
//            double sum = 0;
//            for(Feature f : getPatches()) {
//                sum += getPatchCapacity(f);
//            }
//            totalPatchCapacity = sum;
//        }
//
//        return totalPatchCapacity;
//    }
//
//    /**
//     * @param node a node of a graph
//     * @return the patch associated with this graph node
//     */
//    public static DefaultFeature getPatch(org.geotools.graph.structure.Node node) {
//        return (DefaultFeature)node.getObject();
//    }
//    
//    /**
//     * @param node a node of a graph
//     * @return the capacity of the patch associated with this graph node
//     */
//    public static double getPatchCapacity(org.geotools.graph.structure.Node node) {
//        return getPatchCapacity((Feature)node.getObject());
//    }
//    
//    /**
//     * @param patch a patch
//     * @return the capacity of the patch 
//     */
//    public static double getPatchCapacity(Feature patch) {
//        return ((Number)patch.getAttribute(Project.CAPA_ATTR)).doubleValue();
//    }
//
//    /**
//     * @return the parameters used for calculating the capacity of the patches
//     */
//    public CapaPatchParam getCapacityParams() {
//        return capacityParams;
//    }
//    
//    /**
//     * Sets the capcity of a patch.
//     * Reset the total capacity.
//     * @param patch a patch
//     * @param capa the new capacity
//     */
//    private void setCapacity(DefaultFeature patch, double capa) {
//        patch.setAttribute(Project.CAPA_ATTR, capa);
//        totalPatchCapacity = 0;
//    }
//
	/**
	 * @return the DEM raster
	 * @throws IOException
	 * @throws IllegalStateException if the project does not contain a DEM file
	 */
	public Raster getDemRaster() {
//		if (!isDemExist()) {
//			throw new IllegalStateException("No DEM file");
//		}
//
//		return getExtRaster(getAbsoluteFile(demFile));
		return null;
	}

//    
//    /**
//     * Sets the DEM file for this project, loads it and save the project.
//     * The file format can be tif, asc or rst.
//     * @param demFile the DEM file
//     * @throws IOException 
//     */
//    public void setDemFile(File demFile, boolean save) throws IOException {
//        String prjPath = getDirectory().getAbsolutePath();
//        if(demFile.getAbsolutePath().startsWith(prjPath)) { 
//            this.demFile = new File(demFile.getAbsolutePath().substring(prjPath.length()+1));
//        } else {
//            this.demFile = demFile.getAbsoluteFile();
//        }
//
//        if(rootLayer != null) {
//            // if the DEM layer already exists, remove it
//            String layerName = java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("DEM");
//            for(Layer l : new ArrayList<>(rootLayer.getLayers())) {
//                if(l.getName().equals(layerName)) {
//                    rootLayer.removeLayer(l);
//                }
//            }
//
//            // Add new layer
//            try {
//                GridCoverage2D g = IOImage.loadCoverage(demFile);
//                RasterLayer l = new RasterLayer(layerName, 
//                        new CoverageShape(g, new RasterStyle()));
//                l.setVisible(false);
//                rootLayer.addLayerLast(l);
//            } catch (IOException ex) {
//                Logger.getLogger(Project.class.getName()).log(Level.WARNING, null, ex);
//                JOptionPane.showMessageDialog(null, "DEM file cannot be loaded.", "DEM", JOptionPane.WARNING_MESSAGE);
//            }
//        }
//        if(save) {
//            save();
//        }
//    }
//    
//    /**
//     * @return is the project contain a DEM
//     */
//    public boolean isDemExist() {
//        return demFile != null;
//    }
//    
//    /**
//     * If the path of the f is relative, sets the project directory as parent directory and return a new absolute file
//     * @param f 
//     * @return the absolute path for a file
//     */
//    public File getAbsoluteFile(File f) {
//        if(f.isAbsolute()) {
//            return f;
//        } else {
//            return new File(getDirectory(), f.getPath());
//        }
//    }
//    
	/**
	 * @return the resolution of the landscape map
	 */
	public double getResolution() {
		return resolution;
	}

	/**
	 * @return the transformation between the grid coordinate and the world
	 *         coordinate
	 */
	public AffineTransformation getGrid2space() {
		return null; // grid2space;
	}

	/**
	 * @return the transformation between the world coordinate and the grid
	 *         coordinate
	 */
	public AffineTransformation getSpace2grid() {
		return null; // space2grid;
	}

//
//    /**
//     * @return the projection of the landscape if exists, null otherwise.
//     */
//    public CoordinateReferenceSystem getCRS() {
//        if(wktCRS != null && !wktCRS.isEmpty()) {
//            try {
//                return CRS.parseWKT(wktCRS);
//            } catch (FactoryException ex) {
//                Logger.getLogger(Project.class.getName()).log(Level.WARNING, null, ex);
//            }
//        }
//        return null;
//    }
//    
	/**
	 * @return the codes defining patches in the landscape map
	 */
	public Set<Integer> getPatchCodes() {
		return patchCodes;
	}
//    
//    /**
//     * Check if point (x,y) in world coordinate lies in the landscape map, and does not fall in nodata pixel.
//     * @param x in world coordinate
//     * @param y in world coordinate
//     * @return true if this coordinate is in the study area
//     * @throws IOException if the land use cannot be loaded
//     */
//    public boolean isInZone(double x, double y) throws IOException {
//        if(!zone.contains(x, y)) {
//            return false;
//        }
//        Coordinate cg = space2grid.transform(new Coordinate(x, y), new Coordinate());
//        return getImageSource().getSample((int)cg.x, (int)cg.y, 0) != noData;
//    }
//
//    /**
//     * Create a patch without adding it in the project
//     * @param geom the geometry of the patch (Point or Polygon)
//     * @param capa the capacity of the patch
//     * @return the new patch
//     */
//    public DefaultFeature createPatch(Geometry geom, double capa) {
//        List<String> attrNames = new ArrayList<>(PATCH_ATTRS);
//        List attrs = new ArrayList(Arrays.asList(new Double[attrNames.size()]));
//        attrs.set(attrNames.indexOf(CAPA_ATTR), capa);
//        attrs.set(attrNames.indexOf(AREA_ATTR), geom.getDimension() == 0 ? resolution*resolution : geom.getArea());
//        attrs.set(attrNames.indexOf(PERIM_ATTR), geom.getDimension() == 0 ? resolution*4 : geom.getLength());
//        return new DefaultFeature(patches.size()+1, geom, attrNames, attrs);
//    }
//    
//    /**
//     * Creates and adds a new patch into the project.
//     * The rasters patch and landscape are modified
//     * @param point the geometry of the patch 
//     * @param capa the capacity of the patch
//     * @return the new patch
//     * @throws IOException 
//     * @throws IllegalArgumentException if a patch already exists at the same position
//     */
//    public synchronized DefaultFeature addPatch(Point point, double capa) throws IOException {
//        // tester si pas dans un patch ou touche un patch
//        if(!canCreatePatch(point)) {
//            throw new IllegalArgumentException("Patch already exists at the same position : " + point.toString());
//        }
//        DefaultFeature patch = createPatch(point, capa);
//        int id = (Integer)patch.getId();
//        int patchCode = this.patchCodes.iterator().next();
//        Coordinate cg = space2grid.transform(point.getCoordinate(), new Coordinate());
//        // on passe les raster en strong reference pour qu'ils ne puissent pas être supprimé
//        patchRaster = new StrongRef<>(getRasterPatch());
//        srcRaster = new StrongRef<>(getImageSource());
//        removedCodes.put(id, getImageSource().getSample((int)cg.x, (int)cg.y, 0));
//        getRasterPatch().setSample((int)cg.x, (int)cg.y, 0, id);
//        getImageSource().setSample((int)cg.x, (int)cg.y, 0, patchCode);
//        
//        patches.add(patch);
//        patchIndex = null;
//        return patch;
//    }
//    
//    /**
//     * Creates and adds a new patch into the project.
//     * The rasters patch and landscape are modified
//     * @param geom the geometry of the patch 
//     * @param capa the capacity of the patch
//     * @return the new patch
//     * @throws IOException 
//     * @throws IllegalArgumentException if a patch already exists at the same position
//     */
//    public synchronized DefaultFeature addPatch(Geometry geom, double capa) throws IOException {
//        if(geom instanceof Point) { // pas sûr que ce soit utile...
//            return addPatch((Point) geom, capa);
//        }
//        // tester si pas dans un patch ou touche un patch
//        if(!canCreatePatch(geom)) {
//            throw new IllegalArgumentException("Patch already exist at the same position");
//        }
//                    
//        DefaultFeature patch = createPatch(geom, capa);
//        int id = (Integer)patch.getId();
//        int patchCode = this.patchCodes.iterator().next();
//        // on passe les raster en strong reference pour qu'ils ne puissent pas être supprimé
//        patchRaster = new StrongRef<>(getRasterPatch());
//        srcRaster = new StrongRef<>(getImageSource());
//        GeometryFactory geomFactory = geom.getFactory();
//        Geometry geomGrid = getSpace2grid().transform(geom);
//        Envelope env = geomGrid.getEnvelopeInternal();
//        for(double y = (int)env.getMinY() + 0.5; y <= Math.ceil(env.getMaxY()); y++) {
//            for(double x = (int)env.getMinX() + 0.5; x <= Math.ceil(env.getMaxX()); x++) {
//                Point p = geomFactory.createPoint(new Coordinate(x, y));
//                if(geomGrid.contains(p)) {
//                    getRasterPatch().setSample((int)x, (int)y, 0, id);
//                    getImageSource().setSample((int)x, (int)y, 0, patchCode);
//                }
//            }
//        }
//        patches.add(patch);
//        patchIndex = null;
//        return patch;
//    }
//
//    /**
//     * Checks if the geometry is contained in the landscape map and if none of the pixels belong to a patch 
//     * @param geom the geometry to test
//     * @return true if the patch can be created
//     * @throws IOException 
//     */
//    public boolean canCreatePatch(Geometry geom) throws IOException {
//        if(geom instanceof Point) {// pas sûr que ce soit utile...
//            return canCreatePatch((Point) geom);
//        }
//        // tester si pas dans un patch ou touche un patch
//        GeometryFactory geomFactory = geom.getFactory();
//        Geometry geomGrid = getSpace2grid().transform(geom);
//        Envelope env = geomGrid.getEnvelopeInternal();
//        for(double y = (int)env.getMinY() + 0.5; y <= Math.ceil(env.getMaxY()); y++) {
//            for(double x = (int)env.getMinX() + 0.5; x <= Math.ceil(env.getMaxX()); x++) {
//                Point p = geomFactory.createPoint(new Coordinate(x, y));
//                if(geomGrid.contains(p)) {
//                    if(!canCreatePatch((Point)getGrid2space().transform(p))) {
//                        return false;
//                    }
//                }
//            }
//        }
//        return true;
//    }
//    
//    /**
//     * Checks if the point is contained in the landscape map and if the pixel does not belong to a patch 
//     * @param p the point to test
//     * @return true if the patch can be created
//     * @throws IOException 
//     */
//    public boolean canCreatePatch(Point p) throws IOException {
//        if(!isInZone(p.getX(), p.getY())) {
//            return false;
//        }
//        Coordinate cg = space2grid.transform(p.getCoordinate(), new Coordinate());
//        if(getRasterPatch().getSample((int)cg.x, (int)cg.y, 0) > 0) {
//            return false;
//        }
//        if(con8 && getRasterPatch().getSample((int)cg.x-1, (int)cg.y-1, 0) > 0) {
//            return false;
//        }
//        if(getRasterPatch().getSample((int)cg.x-1, (int)cg.y, 0) > 0) {
//            return false;
//        }
//        if(con8 && getRasterPatch().getSample((int)cg.x-1, (int)cg.y+1, 0) > 0) {
//            return false;
//        }
//        if(getRasterPatch().getSample((int)cg.x, (int)cg.y-1, 0) > 0) {
//            return false;
//        }
//        if(getRasterPatch().getSample((int)cg.x, (int)cg.y+1, 0) > 0) {
//            return false;
//        }
//        if(con8 && getRasterPatch().getSample((int)cg.x+1, (int)cg.y-1, 0) > 0) {
//            return false;
//        }
//        if(getRasterPatch().getSample((int)cg.x+1, (int)cg.y, 0) > 0) {
//            return false;
//        }
//        if(con8 && getRasterPatch().getSample((int)cg.x+1, (int)cg.y+1, 0) > 0) {
//            return false;
//        }
//        
//        return true;
//    }
//    
//    /**
//     * Remove a point patch previously added by {@link #addPatch }.
//     * Only the latter added patch can be removed.
//     * @param patch the patch to remove
//     * @throws IOException 
//     * @throws IllegalArgumentException if the patch geometry is not a Point or if the patch is not the last
//     */
//    public synchronized void removePointPatch(Feature patch) throws IOException {
//        if(!(patch.getGeometry() instanceof Point)) {
//            throw new IllegalArgumentException("Cannot remove patch with geometry different of Point");
//        }
//        Coordinate cg = space2grid.transform(patch.getGeometry().getCoordinate(), new Coordinate());
//        if(!patchCodes.contains(getImageSource().getSample((int)cg.x, (int)cg.y, 0))) {
//            throw new RuntimeException("No patch to remove at " + patch.getGeometry());
//        }
//        int id = (Integer)patch.getId();
//        if(id != patches.size()) {
//            throw new IllegalArgumentException("The patch to remove is not the last one - id : " + patch.getId());
//        }
//        getImageSource().setSample((int)cg.x, (int)cg.y, 0, removedCodes.remove(id));
//        getRasterPatch().setSample((int)cg.x, (int)cg.y, 0, 0);
//        patches.remove(patches.size()-1);
//    }
//    
//    /**
//     * Sets the capacities of the patches given the parameters
//     * @param param the parameters for calculating the new capacities
//     * @param save save the project ?
//     * @throws IOException 
//     */
//    public void setCapacities(CapaPatchParam param, boolean save) throws IOException, SchemaException {
//        if(param.importFile != null) {
//            CSVTabReader r = new CSVTabReader(param.importFile);
//            r.read(param.idField);
//
//            if(r.getKeySet().size() < getPatches().size()) {
//                throw new IOException(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("Some_patch_ids_are_missing."));
//            }
//
//            for(Number id : (Set<Number>)r.getKeySet()) {
//                DefaultFeature p = getPatch(id.intValue());
//                setCapacity(p, ((Number)r.getValue(id, param.capaField)).doubleValue());
//            }
//            r.dispose();
//        } else if(param.isCalcArea()) {
//            if(!param.isWeightedArea()) {
//                for(DefaultFeature patch : patches) {
//                    setCapacity(patch, Math.pow(patch.getGeometry().getArea(), param.exp));
//                }
//            } else {
//                // weighted area
//                Raster rPatch = getRasterPatch();
//                Raster land = getImageSource();
//                for(DefaultFeature patch : patches) {
//                    int id = (int) patch.getId();
//                    double sum = 0;
//                    Envelope env = getSpace2grid().transform(patch.getGeometry()).getEnvelopeInternal();
//                    for(double y = (int)env.getMinY() + 0.5; y <= Math.ceil(env.getMaxY()); y++) {
//                        for(double x = (int)env.getMinX() + 0.5; x <= Math.ceil(env.getMaxX()); x++) {
//                            if(rPatch.getSample((int)x, (int)y, 0) == id) {
//                                double w = param.codeWeight.get(land.getSample((int)x, (int)y, 0));
//                                sum += w * getResolution()*getResolution();
//                            }
//                        }
//                    }
//                    setCapacity(patch, Math.pow(sum, param.exp));
//                }
//            }
//        } else {
//            calcNeighborAreaCapacity(getLinkset(param.costName), param.maxCost, param.codes, param.weightCost);
//        }
//        capacityParams = param;
//        
//        if(save) {
//            savePatch();
//            save();
//        }
//    }
//    
//    private void calcNeighborAreaCapacity(final Linkset linkset, final double maxCost, final HashSet<Integer> codes, final boolean weight) {
//        ProgressBar progressBar = Config.getProgressBar(java.util.ResourceBundle.getBundle("org/thema/graphab/Bundle").getString("CALC PATCH CAPACITY..."));
//        AbstractParallelFTask task = new AbstractParallelFTask(progressBar) {
//            @Override
//            protected Object execute(int start, int end) {
//                if(isCanceled()) {
//                    return null;
//                }
//               
//                try {
//                    RasterPathFinder pathfinder;
//                    if(linkset == null || linkset.getType_dist() == Linkset.EUCLID) {
//                        double [] costs = new double[getCodes().last()+1];
//                        Arrays.fill(costs, 1);
//                        pathfinder = new RasterPathFinder(Project.this, getImageSource(), costs, 0);
//                    } else {
//                        pathfinder = getRasterPathFinder(linkset);
//                    }
//
//                    for(int i = start; i < end; i++) {
//                        DefaultFeature patch = patches.get(i);
//                        double capa = pathfinder.getNeighborhood(patch, maxCost,
//                                getImageSource(), codes, weight);
//                        setCapacity(patch, capa);
//                        incProgress(1);
//                    }
//                } catch (Exception ex) {
//                    cancelTask();
//                    throw new RuntimeException(ex);
//                }
//
//               return null;
//            }
//            @Override
//            public int getSplitRange() {
//                return patches.size();
//            }
//            @Override
//            public void finish(Collection results) {}
//            @Override
//            public Object getResult() {
//                return null;
//            }
//
//        };
//
//        new ParallelFExecutor(task).executeAndWait();
//
//        progressBar.close();
//        
//    }
//    
//    /**
//     * @param method
//     * @return the global metrics which can be used for a given method
//     */
//    public List<GlobalMetric> getGlobalMetricsFor(Method method) {
//        List<GlobalMetric> indices = new ArrayList<>();
//        for(GlobalMetric ind : GLOBAL_METRICS) {
//            if(ind.isAcceptMethod(method, getCapacityParams().isArea())) {
//                indices.add(ind);
//            }
//        }
//        return indices;
//    }
//    
//    /**
//     * @return the local metrics
//     */
//    public static List<LocalMetric> getLocalMetrics() {
//        List<LocalMetric> indices = new ArrayList<>();
//        for(LocalMetric ind : LOCAL_METRICS) {
//            indices.add(ind);
//        }
//        return indices;
//    }
//    
//    /**
//     * 
//     * @param metrics a list of metrics
//     * @param shortName the shortname metric to search
//     * @return true if the list contains a metric with this short name
//     */
//    private static boolean containsMetric(List<? extends Metric> metrics, String shortName) {
//        for(Metric ind : metrics) {
//            if(ind.getShortName().equals(shortName)) {
//                return true;
//            }
//        }
//        return false;
//    }
//    
//    /**
//     * @param shortName the short name of the global metric
//     * @return the global metric given its short name
//     * @throws IllegalArgumentException if the metric is not found
//     */
//    public static GlobalMetric getGlobalMetric(String shortName) {
//        for(GlobalMetric ind : GLOBAL_METRICS) {
//            if(ind.getShortName().equals(shortName)) {
//                return ind;
//            }
//        }
//        throw new IllegalArgumentException("Unknown metric " + shortName);
//    }
//    
//    /**
//     * @param shortName the short name of the local metric
//     * @return the local metric given its short name
//     * @throws IllegalArgumentException if the metric is not found
//     */
//    public static LocalMetric getLocalMetric(String shortName) {
//        for(LocalMetric ind : LOCAL_METRICS) {
//            if(ind.getShortName().equals(shortName)) {
//                return ind;
//            }
//        }
//        throw new IllegalArgumentException("Unknown metric " + shortName);
//    }
//    
//    /** Global metrics available */
//    public static List<GlobalMetric> GLOBAL_METRICS;
//    /** Local metrics available */
//    public static List<LocalMetric> LOCAL_METRICS;
//    
//    static {
//        GLOBAL_METRICS = new ArrayList(Arrays.asList(new SumLocal2GlobalMetric(new FLocalMetric(), TypeElem.NODE), 
//                new ECMetric(), new PCMetric(), new IICMetric(), new CCPMetric(),
//                new MSCMetric(), new SLCMetric(), new ECSMetric(), new GDMetric(), new HMetric(), new NCMetric(),
//                new DeltaPCMetric(), new WilksMetric()));
//        LOCAL_METRICS = new ArrayList(Arrays.asList((LocalMetric)new FLocalMetric(), new BCLocalMetric(), new IFLocalMetric(), 
//                new CFLocalMetric(), new DgLocalMetric(), new CCLocalMetric(), new ClosenessLocalMetric(), 
//                new ConCorrLocalMetric(), new EccentricityLocalMetric(), new IFInterLocalMetric()));
//    }
//    
//    /**
//     * Loads additional metrics available in the subdirectory named plugins of the directory where is the jar file.
//     * 
//     * @throws Exception 
//     */
//    public static void loadPluginMetric() throws Exception {
//        URL url = Project.class.getProtectionDomain().getCodeSource().getLocation();
//        File dir = new File(url.toURI()).getParentFile();
//        File loc = new File(dir, "plugins");
//
//        if(!loc.exists()) {
//            return;
//        }
//        
//        File[] flist = loc.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File file) {return file.getPath().toLowerCase().endsWith(".jar");}
//        });
//        if(flist == null || flist.length == 0) {
//            return;
//        }
//        URL[] urls = new URL[flist.length];
//        for (int i = 0; i < flist.length; i++) {
//            urls[i] = flist[i].toURI().toURL();
//        }
//        URLClassLoader ucl = new URLClassLoader(urls);
//
//        loadPluginMetric(ucl);
//    }
//    
//    /**
//     * Loads other metrics given a specific class loader
//     * @param loader
//     */
//    public static void loadPluginMetric(ClassLoader loader)  {
//        ServiceLoader<Metric> sl = ServiceLoader.load(Metric.class, loader);
//        Iterator<Metric> it = sl.iterator();
//        while (it.hasNext()) {
//            Metric metric = it.next();
//            if(metric instanceof GlobalMetric) {
//                if(!containsMetric(GLOBAL_METRICS, metric.getShortName())) {
//                    GLOBAL_METRICS.add((GlobalMetric)metric);
//                }
//            } else if(metric instanceof LocalMetric) {
//                if(!containsMetric(LOCAL_METRICS, metric.getShortName())) {
//                    LOCAL_METRICS.add((LocalMetric)metric);
//                }
//            } else {
//                throw new RuntimeException("Class " +metric.getClass().getCanonicalName() + " does not inherit from GlobalMetric or LocalMetric");
//            }
//        }
//    }
//
//    /**
//     * A reference to an object of type T
//     * @param <T> 
//     */
//    public interface Ref<T> {
//        /**
//         * @return the object or null if the object has been cleared
//         */
//        public T get();
//    }
//    
//    /**
//     * Soft reference to an object of type T.
//     * Encapsulates the java class {@link SoftReference} to implement interface {@link Ref}
//     * @param <T> 
//     */
//    public static class SoftRef<T> implements Ref<T>{
//
//        private final SoftReference<T> ref;
//
//        /**
//         * Creates a SoftReference to the object val
//         * @param val an object
//         */
//        public SoftRef(T val) {
//            ref = new SoftReference<>(val);
//        }
//        
//        @Override
//        public T get() {
//            return ref.get();   
//        }
//    }
//    
//    /**
//     * Strong reference to an object of type T.
//     * Keeps a reference to an object to ensure that it will not claimed by the garbage collector
//     * @param <T> 
//     */
//    public static class StrongRef<T> implements Ref<T>{
//
//        private final T ref;
//
//        /**
//         * Keeps the object val
//         * @param val 
//         */
//        public StrongRef(T val) {
//            ref = val;
//        }
//        
//        @Override
//        public T get() {
//            return ref;   
//        }
//    }
}
