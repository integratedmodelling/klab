/*******************************************************************************
 * Copyright (C) 2007, 2015:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.components.geospace.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.geotools.coverage.GridSampleDimension;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.DataUtilities;
import org.geotools.factory.CommonFactoryFinder;
import org.geotools.feature.DefaultFeatureCollection;
import org.geotools.feature.SchemaException;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.geometry.jts.JTS;
import org.geotools.map.FeatureLayer;
import org.geotools.map.GridCoverageLayer;
import org.geotools.map.Layer;
import org.geotools.map.MapContent;
import org.geotools.map.MapViewport;
import org.geotools.styling.ChannelSelection;
import org.geotools.styling.ColorMap;
import org.geotools.styling.ContrastEnhancement;
import org.geotools.styling.FeatureTypeStyle;
import org.geotools.styling.Fill;
import org.geotools.styling.Graphic;
import org.geotools.styling.LineSymbolizer;
import org.geotools.styling.Mark;
import org.geotools.styling.PointSymbolizer;
import org.geotools.styling.PolygonSymbolizer;
import org.geotools.styling.RasterSymbolizer;
import org.geotools.styling.Rule;
import org.geotools.styling.SLD;
import org.geotools.styling.SelectedChannelType;
import org.geotools.styling.Stroke;
import org.geotools.styling.Style;
import org.geotools.styling.StyleBuilder;
import org.geotools.styling.StyleFactory;
import org.geotools.swing.JMapFrame;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.time.ITime;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.Grid.CellImpl;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.visualization.Renderer;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.visualization.Viewport;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;
import org.opengis.filter.FilterFactory;
import org.opengis.style.ContrastMethod;
import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Generic spatial visualizer/debugger that is initialized with a spatial extent
 * and you can just throw shapes and states into.
 * 
 * Can be used by just calling show() or as a part of a more flexible context
 * viewer.
 * 
 * @author Ferd
 *
 */
public class SpatialDisplay {

    int SLID = 0;
    static StyleFactory styleFactory = CommonFactoryFinder.getStyleFactory();
    static FilterFactory filterFactory = CommonFactoryFinder.getFilterFactory();

    class RLDesc {

        IState state;
        String name;

        Layer getLayer() {
            GridCoverage2D coverage = GeotoolsUtils.INSTANCE.stateToCoverage(state);
            Layer layer = new GridCoverageLayer(coverage,
                    SLD.wrapSymbolizers(Renderer.INSTANCE.getRasterSymbolizer(state, ITime.INITIALIZATION)));
            layer.setTitle(state.getObservable().getLocalName());
            return layer;
        }

    }

    class SLDesc {

        SimpleFeatureType fType;
        List<SimpleFeature> features = new ArrayList<>();
        int nShapes = 0;
        SimpleFeatureBuilder featureBuilder;
        String name;
        Class<?> geometryClass;

        SLDesc(String name) {
            this.name = name;
        }

        void addFeature(Geometry shape) {

            if (featureBuilder == null) {
                try {
                    geometryClass = shape.getClass();
                    fType = DataUtilities.createType("Location", "the_geom:" + shape.getGeometryType() + ":srid="
                            + (shape.getSRID() == 0 ? 4326 : shape.getSRID()) + ",number:Integer");
                    featureBuilder = new SimpleFeatureBuilder(fType);
                } catch (SchemaException e) {
                    throw new KlabValidationException(e);
                }
            }

            featureBuilder.add(shape);
            featureBuilder.add(++nShapes);
            features.add(featureBuilder.buildFeature(null));
        }

        void addFeature(Shape shape) {

            if (featureBuilder == null) {
                try {
                    geometryClass = shape.getJTSGeometry().getClass();
                    fType = DataUtilities.createType("Location", "the_geom:" + shape.getJTSGeometry().getGeometryType()
                            + ":srid=" + shape.getProjection().getSRID() + ",number:Integer");
                    featureBuilder = new SimpleFeatureBuilder(fType);
                } catch (SchemaException e) {
                    throw new KlabValidationException(e);
                }
            }

            featureBuilder.add(shape.getJTSGeometry());
            featureBuilder.add(++nShapes);
            features.add(featureBuilder.buildFeature(null));
        }

        Layer getLayer() {

            if (geometryClass == null) {
                return null;
            }

            DefaultFeatureCollection featureCollection = new DefaultFeatureCollection("fc_" + name, fType);
            for (SimpleFeature f : features) {
                featureCollection.add(f);
            }
            return new FeatureLayer(featureCollection, createStyle(geometryClass), name);
        }
    }

    Map<String, RLDesc> rLayers = new HashMap<>();
    Map<String, SLDesc> sLayers = new HashMap<>();
    ISpace space;

    public SpatialDisplay(ISpace space) {
        this.space = space;
    }

    /**
     * Add a shape to the standard layer "shapes_xxx" where xxx is the geometry type
     * of the shape. CRS of shape isn't checked and must be same as passed scale.
     * 
     * @param shape
     */
    public void add(Shape shape) {
        SLDesc slDesc = getSLDesc("shapes_" + shape.getGeometryType().name());
        slDesc.addFeature(shape);
    }

    public void add(Envelope shape, String layer) {
        add(JTS.toGeometry(shape), layer);
    }

    /**
     * Add a spatial extent to a layer
     * 
     * @param space
     * @param layer
     */
    public void add(ISpace space, String layer) {
        /*
         * if (space instanceof Space && ((Space)space).getGrid().isPresent()) {
         * add(((Space)space).getGrid().get(), layer); } else
         */ if (space instanceof Space && ((Space) space).getTessellation().isPresent()) {
            for (IExtent shape : ((Space) space).getTessellation().get()) {
                add((IShape) shape, layer);
            }
        } else {
            add((Shape) space.getShape(), layer);
        }
    }

    /**
     * Add shape to named layer, creating if necessary. All must be projected like
     * the scale.
     * 
     * @param shape
     * @param layer
     */
    public void add(Shape shape, String layer) {

        SLDesc slDesc = getSLDesc(layer);
        slDesc.addFeature(shape);
    }

    /**
     * Add a raster state, complaining loudly if the spatial extent does not fit the
     * space passed in the constructor. That will soon mean that an adapter cannot
     * be found, not that it's not identical.
     * 
     * @param state
     */
    public void add(IState state) {

        RLDesc rlDesc = new RLDesc();
        rlDesc.name = Concepts.INSTANCE.getDisplayName(state.getObservable().getType());
        rlDesc.state = state;
        rLayers.put(rlDesc.name, rlDesc);
    }

    // private RasterSymbolizer getRasterSymbolizer(IState state) {
    //
    // if (!state.getMetadata().contains("colormap")) {
    // return styleFactory.getDefaultRasterSymbolizer();
    // }
    //
    // // create style
    // // TODO use colormaps and do it right, then export to GeotoolsUtils for map
    // // generation also as
    // // Image
    // StyleBuilder sb = new StyleBuilder();
    // ColorMap colorMap = sb.createColorMap(new String[] { "poco", "meglio",
    // "buono", "ostia" }, // labels
    // new double[] { 100, 400, 2000, 3000 }, // values that begin a category, or
    // break points in a
    // // ramp, or isolated values, according to the type of
    // // color map specified by Type
    // new Color[] { new Color(0, 100, 0), new Color(150, 150, 50), new Color(200,
    // 200, 50), Color.WHITE },
    // ColorMap.TYPE_RAMP);
    //
    // return sb.createRasterSymbolizer(colorMap, 1.0 /* opacity */);
    // }

    /**
     * Show the map using a JMapFrame.
     */
    public void show() {

        MapContent content = new MapContent();
        content.setViewport(new MapViewport((((Shape) space.getShape()).getJTSEnvelope())));
        Viewport viewport = new Viewport(800, 800);
        int[] xy = viewport.getSizeFor(space.getEnvelope().getMaxX() - space.getEnvelope().getMinX(),
                space.getEnvelope().getMaxY() - space.getEnvelope().getMinY());
        content.setTitle("Spatial display");
        JMapFrame display = new JMapFrame(content);
        display.setMinimumSize(new Dimension(xy[0] + 300, xy[1]));
        display.enableLayerTable(true);
        display.enableToolBar(true);
        display.enableStatusBar(true);
        display.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        for (RLDesc sld : rLayers.values()) {
            content.addLayer(sld.getLayer());
        }

        for (SLDesc sld : sLayers.values()) {
            content.addLayer(sld.getLayer());
        }

        display.setVisible(true);
    }

    private SLDesc getSLDesc(String layer) {
        if (!sLayers.containsKey(layer)) {
            SLDesc ret = new SLDesc(layer);
            sLayers.put(layer, ret);
        }
        return sLayers.get(layer);
    }

    private Style createStyle(Class<?> gclass) {

        if (Polygon.class.isAssignableFrom(gclass) || MultiPolygon.class.isAssignableFrom(gclass)) {
            return createPolygonStyle();

        } else if (LineString.class.isAssignableFrom(gclass) || MultiLineString.class.isAssignableFrom(gclass)) {
            return createLineStyle();

        } else {
            return createPointStyle();
        }
    }

    /**
     * Create a Style to draw polygon features with a thin blue outline and a cyan
     * fill
     */
    private Style createPolygonStyle() {

        // create a partially opaque outline stroke
        Stroke stroke = styleFactory.createStroke(filterFactory.literal(Color.GRAY), filterFactory.literal(1),
                filterFactory.literal(0.5));

        // create a partial opaque fill
        Fill fill = styleFactory.createFill(filterFactory.literal(Color.LIGHT_GRAY), filterFactory.literal(0.5));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to draw the
         * default geomettry of features
         */
        PolygonSymbolizer sym = styleFactory.createPolygonSymbolizer(stroke, fill, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] { rule });
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    /**
     * Create a Style to draw line features as thin blue lines
     */
    private Style createLineStyle() {
        Stroke stroke = styleFactory.createStroke(filterFactory.literal(Color.GREEN), filterFactory.literal(1));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to draw the
         * default geometry of features
         */
        LineSymbolizer sym = styleFactory.createLineSymbolizer(stroke, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] { rule });
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    /**
     * Create a Style to draw point features as circles with blue outlines and cyan
     * fill
     */
    private Style createPointStyle() {

        Graphic gr = styleFactory.createDefaultGraphic();

        Mark mark = styleFactory.getCircleMark();

        mark.setStroke(styleFactory.createStroke(filterFactory.literal(Color.BLUE), filterFactory.literal(1)));

        mark.setFill(styleFactory.createFill(filterFactory.literal(Color.RED)));

        gr.graphicalSymbols().clear();
        gr.graphicalSymbols().add(mark);
        gr.setSize(filterFactory.literal(5));

        /*
         * Setting the geometryPropertyName arg to null signals that we want to draw the
         * default geomettry of features
         */
        PointSymbolizer sym = styleFactory.createPointSymbolizer(gr, null);

        Rule rule = styleFactory.createRule();
        rule.symbolizers().add(sym);
        FeatureTypeStyle fts = styleFactory.createFeatureTypeStyle(new Rule[] { rule });
        Style style = styleFactory.createStyle();
        style.featureTypeStyles().add(fts);

        return style;
    }

    // /**
    // * This method examines the names of the sample dimensions in the provided
    // * coverage looking for "red...", "green..." and "blue..." (case insensitive
    // * match). If these names are not found it uses bands 1, 2, and 3 for the red,
    // * green and blue channels. It then sets up a raster symbolizer and returns
    // this
    // * wrapped in a Style.
    // *
    // * @return a new Style object containing a raster symbolizer set up for RGB
    // * image
    // */
    // private Style createRGBStyle(IState state, GridCoverage2D cov) {
    //
    // // We need at least three bands to create an RGB style
    // int numBands = cov.getNumSampleDimensions();
    // if (numBands < 3) {
    // return createGreyscaleStyle(state, 1);
    // }
    // // Get the names of the bands
    // String[] sampleDimensionNames = new String[numBands];
    // for (int i = 0; i < numBands; i++) {
    // GridSampleDimension dim = cov.getSampleDimension(i);
    // sampleDimensionNames[i] = dim.getDescription().toString();
    // }
    // final int RED = 0, GREEN = 1, BLUE = 2;
    // int[] channelNum = { -1, -1, -1 };
    // // We examine the band names looking for "red...", "green...", "blue...".
    // // Note that the channel numbers we record are indexed from 1, not 0.
    // for (int i = 0; i < numBands; i++) {
    // String name = sampleDimensionNames[i].toLowerCase();
    // if (name != null) {
    // if (name.matches("red.*")) {
    // channelNum[RED] = i + 1;
    // } else if (name.matches("green.*")) {
    // channelNum[GREEN] = i + 1;
    // } else if (name.matches("blue.*")) {
    // channelNum[BLUE] = i + 1;
    // }
    // }
    // }
    // // If we didn't find named bands "red...", "green...", "blue..."
    // // we fall back to using the first three bands in order
    // if (channelNum[RED] < 0 || channelNum[GREEN] < 0 || channelNum[BLUE] < 0) {
    // channelNum[RED] = 1;
    // channelNum[GREEN] = 2;
    // channelNum[BLUE] = 3;
    // }
    // // Now we create a RasterSymbolizer using the selected channels
    // SelectedChannelType[] sct = new
    // SelectedChannelType[cov.getNumSampleDimensions()];
    // ContrastEnhancement ce =
    // styleFactory.contrastEnhancement(filterFactory.literal(1.0),
    // ContrastMethod.NORMALIZE);
    // for (int i = 0; i < 3; i++) {
    // sct[i] =
    // styleFactory.createSelectedChannelType(String.valueOf(channelNum[i]), ce);
    // }
    // RasterSymbolizer sym = getRasterSymbolizer(state);
    // ChannelSelection sel = styleFactory.channelSelection(sct[RED], sct[GREEN],
    // sct[BLUE]);
    // sym.setChannelSelection(sel);
    //
    // return SLD.wrapSymbolizers(sym);
    // }
    //
    // private Style createGreyscaleStyle(IState state, int band) {
    // ContrastEnhancement ce =
    // styleFactory.contrastEnhancement(filterFactory.literal(1.0),
    // ContrastMethod.NORMALIZE);
    // SelectedChannelType sct =
    // styleFactory.createSelectedChannelType(String.valueOf(band), ce);
    // RasterSymbolizer sym = getRasterSymbolizer(state);
    // ChannelSelection sel = styleFactory.channelSelection(sct);
    // sym.setChannelSelection(sel);
    // return SLD.wrapSymbolizers(sym);
    // }

    public void add(IGrid grid, String layer) {
        for (IExtent cell : grid) {
            add((Cell) cell, layer);
        }
    }

    public void add(Geometry shape, String layer) {
        SLDesc slDesc = getSLDesc(layer);
        slDesc.addFeature(shape);
    }

    public void add(Cell cell, String layer) {
        add(((CellImpl) cell).getShape().getJTSGeometry(), layer);
    }

}
