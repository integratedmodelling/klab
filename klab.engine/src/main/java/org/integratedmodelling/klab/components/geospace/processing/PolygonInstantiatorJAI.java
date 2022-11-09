package org.integratedmodelling.klab.components.geospace.processing;

import java.awt.image.DataBuffer;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.geotools.coverage.grid.GridCoverage2D;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.NameImpl;
import org.geotools.process.Processors;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimQuantity;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Observables;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.artifacts.IObjectArtifact;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor.Descriptor;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.contextualization.IInstantiator;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.components.geospace.utils.GeotoolsUtils;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.utils.Parameters;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.type.Name;

/**
 * 
 * @author ferdinando.villa
 */
public class PolygonInstantiatorJAI extends AbstractContextualizer implements IExpression, IInstantiator {

    Descriptor selectExprDescriptor = null;
    Descriptor categorizeExprDescriptor = null;
    private IGrid grid;
    private Shape boundingBox;
    GeometryFactory gfact = new GeometryFactory();
    double min_area_m = Double.NaN;
    double max_area_m = Double.NaN;
    boolean intersect = false;
    private boolean createPointFeatures;
    private boolean computeConvexHull;
    // if this is given, the value we use to vectorize ends up in the objects with
    // this semantics
    IObservable attributeSemantics;
    private BidiMap<Object, Integer> valueHash;

    public PolygonInstantiatorJAI() {
    }

    /**
     * Use this to extract features through {@link #extractShapes(IState, IExpression, IMonitor)} or
     * {@link #extractShapes(GridCoverage2D, IProjection, IExpression, IContextualizationScope)}
     * outside of a k.LAB contextualizer.
     * 
     * @param grid
     */
    public PolygonInstantiatorJAI(IGrid grid) {
        this.grid = grid;
    }

    public PolygonInstantiatorJAI(IParameters<String> parameters, IContextualizationScope context)
            throws KlabValidationException {

        if (parameters.containsKey("select")) {
            Object expression = parameters.get("select");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.selectExprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE).describe(
                    expression.toString(),
                    context.getExpressionContext().scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded));
        }

        if (parameters.containsKey("min_area")) {
            Object a = parameters.get("min_area");
            if (a instanceof Number) {
                this.min_area_m = ((Number)a).doubleValue();
            } else if (a instanceof IKimQuantity) {
                IUnit unit = Unit.create(((IKimQuantity)a).getUnit());
                this.min_area_m = Units.INSTANCE.SQUARE_METERS.convert(((IKimQuantity)a).getValue(), unit).doubleValue();
            }
        }

        if (parameters.containsKey("max_area")) {
            Object a = parameters.get("max_area");
            if (a instanceof Number) {
                this.max_area_m = ((Number)a).doubleValue();
            } else if (a instanceof IKimQuantity) {
                IUnit unit = Unit.create(((IKimQuantity)a).getUnit());
                this.max_area_m = Units.INSTANCE.SQUARE_METERS.convert(((IKimQuantity)a).getValue(), unit).doubleValue();
            }
        }

        if (parameters.containsKey("categorize")) {
            Object expression = parameters.get("categorize");
            boolean forceScalar = false;
            if (expression instanceof IKimExpression) {
                forceScalar = ((IKimExpression) expression).isForcedScalar();
                expression = ((IKimExpression) expression).getCode();
            }
            this.categorizeExprDescriptor = Extensions.INSTANCE.getLanguageProcessor(Extensions.DEFAULT_EXPRESSION_LANGUAGE)
                    .describe(expression.toString(),
                            context.getExpressionContext().scalar(forceScalar ? Forcing.Always : Forcing.AsNeeded));
        }

        if (parameters.contains("semantics")) {
            this.attributeSemantics = Observables.INSTANCE.parseObservable(parameters.get("semantics"), context.getMonitor());
        }

        IScale scale = context.getScale();
        if (!(scale.isSpatiallyDistributed() && scale.getDimension(Type.SPACE).size() > 1
                && scale.getDimension(Type.SPACE).isRegular())) {
            throw new KlabValidationException("feature extraction only works on regular distributed spatial extents (grids)");
        }

        this.grid = ((Space) scale.getSpace()).getGrid();
        this.boundingBox = (Shape) scale.getSpace().getShape();
        this.createPointFeatures = parameters.get("points", Boolean.FALSE);

        // TODO these are obviously still unfeasible dimensions for an in-memory image.
        if (this.grid == null || this.grid.getXCells() > Integer.MAX_VALUE || this.grid.getYCells() > Integer.MAX_VALUE) {
            throw new KlabValidationException("feature extractor: the spatial extent is too large or not a grid");
        }

    }

    @Override
    public List<IObjectArtifact> instantiate(IObservable semantics, IContextualizationScope scope) throws KlabException {

        List<IObjectArtifact> ret = new ArrayList<>();
        Name n = new NameImpl("ras", "PolygonExtraction");
        Map<String, Object> params = new HashMap<>();

        int created = 0;
        int skipped = 0;

        if (setupData(scope, params)) {

            org.geotools.process.Process process = Processors.createProcess(n);
            Map<String, Object> result = process.execute(params, null);
            SimpleFeatureCollection featureCollection = (SimpleFeatureCollection) result.get("result");

            String baseName = Observables.INSTANCE.getDisplayName(semantics);

            try (SimpleFeatureIterator iterator = featureCollection.features()) {
                while(iterator.hasNext()) {
                    SimpleFeature feature = iterator.next();
                    // process feature
                    Shape shape = getShape(feature);
                    if (shape != null) {

                        if (!Double.isNaN(this.min_area_m) && this.min_area_m != 0) {
                            if (shape.getStandardizedArea() < this.min_area_m) {
                                continue;
                            }
                        }

                        if (!Double.isNaN(this.max_area_m) && this.max_area_m != 0) {
                            if (shape.getStandardizedArea() > this.max_area_m) {
                                continue;
                            }
                        }

                        Scale instanceScale = Scale.createLike(scope.getScale(), shape);
                        IObjectArtifact object = scope.newObservation(semantics, baseName + "_" + (created + 1), instanceScale,
                                /* TODO send useful metadata */null);

                        if (this.categorizeExprDescriptor != null && this.attributeSemantics != null
                                && shape.getMetadata().get("value") != null) {
                            /*
                             * add the state
                             */
                            ((IRuntimeScope) scope).addState((IDirectObservation) object, attributeSemantics,
                                    this.valueHash == null
                                            ? shape.getMetadata().get("value")
                                            : (shape.getMetadata().get("value") == null
                                                    ? null
                                                    : this.valueHash.getKey(shape.getMetadata().get("value"))));
                        }

                        ret.add(object);
                        created++;

                    } else {
                        skipped++;
                    }
                }
            }
        }

        scope.getMonitor().info("feature extractor: built " + created + " observations of type " + semantics);
        if (skipped > 0) {
            scope.getMonitor().info("feature extractor: skipped " + skipped + " features not conformant with passed options");
        }

        return ret;
    }

    private boolean setupData(IContextualizationScope scope, Map<String, Object> params) {

        boolean isfloat = false;
        List<Number> noDataValues = new ArrayList<>();
        Set<IState> sourceStates = new LinkedHashSet<IState>();

        WritableRaster raster = null;
        IExpression selectExpression = null;
        IExpression categorizeExpression = null;
        this.valueHash = null;

        if (selectExprDescriptor != null) {
            selectExpression = selectExprDescriptor.compile();
        }
        if (categorizeExprDescriptor != null) {
            categorizeExpression = categorizeExprDescriptor.compile();
        }

        if (categorizeExpression == null && selectExpression == null) {
            return false;
        }

        if (scope.contains("source-state")) {
            IState sourceState = scope.getArtifact(scope.get("source-state", String.class), IState.class);
            if (sourceState == null) {
                throw new KlabResourceNotFoundException("feature extractor: source state "
                        + scope.get("source-state", String.class) + " not found or not a state");
            }
            sourceStates.add(sourceState);
        }

        int nextValue = 1;
        boolean ret = false;
        for (ILocator locator : scope.getScale()) {

            Cell cell = null;
            if (locator instanceof IScale && ((IScale) locator).getSpace() instanceof Cell) {
                cell = (Cell) ((IScale) locator).getSpace();
            } else {
                throw new KlabValidationException("polygon instantiator: context is not a spatial grid");
            }

            boolean selected = true;
            Object value = null;
            if (selectExpression != null) {
                Object val = selectExpression.eval(scope, /* parameters, */ "scale", locator);
                if (Observations.INSTANCE.isData(val) && !(val instanceof Boolean)) {
                    throw new KlabValidationException("polygon instantiator: select expression must return a true/false value");
                }
                selected = val instanceof Boolean ? (Boolean) val : false;
            }
            if (selected) {
                if (categorizeExpression != null) {
                    value = categorizeExpression.eval(scope, /* parameters */ "scale", locator);
                    if (Observations.INSTANCE.isData(value) && !(value instanceof Number || value instanceof Boolean)) {
                        if (this.valueHash == null) {
                            this.valueHash = new DualHashBidiMap<>();
                        }
                        if (!valueHash.containsKey(value)) {
                            valueHash.put(value, nextValue);
                            value = nextValue;
                            nextValue++;
                        } else {
                            value = valueHash.get(value);
                        }
                    }
                } else {
                    value = 1;
                }
            }

            if (Observations.INSTANCE.isData(value)) {

                if (raster == null) {

                    if (categorizeExpression != null) {

                        if (value instanceof Integer) {
                            raster = GeotoolsUtils.INSTANCE.makeRaster(scope.getScale().getSpace(), DataBuffer.TYPE_INT, 0);
                            noDataValues.add(0);
                        } else if (value instanceof Double) {
                            isfloat = true;
                            raster = GeotoolsUtils.INSTANCE.makeRaster(scope.getScale().getSpace(), DataBuffer.TYPE_FLOAT,
                                    Float.NaN);
                            noDataValues.add(Float.NaN);
                        } else {
                            throw new KlabValidationException(
                                    "polygon instantiator: raster value is unsupported: " + value.getClass().getCanonicalName());
                        }

                    } else if (selected) {
                        raster = GeotoolsUtils.INSTANCE.makeRaster(scope.getScale().getSpace(), DataBuffer.TYPE_INT, 0);
                        noDataValues.add(0);
                    }

                }

                if (isfloat) {
                    raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) value).floatValue());
                } else {
                    raster.setSample((int) cell.getX(), (int) cell.getY(), 0, ((Number) value).intValue());
                }

                ret = true;

            }

        }

        if (raster == null) {
            return false;
        }

        GridCoverage2D coverage = GeotoolsUtils.INSTANCE.makeCoverage("polygons", raster, scope.getScale());

        params.put("data", coverage);
        params.put("nodata", noDataValues);

        return ret;
    }

    private Shape getShape(SimpleFeature feature) {

        /*
         * TODO apply filters, if any, and cull unsuitable candidates.
         */
        Geometry polygon = feature.getDefaultGeometry() instanceof Geometry ? (Geometry) feature.getDefaultGeometry() : null;

        if (polygon == null) {
            return null;
        }

        try {
            if (polygon.getCoordinates().length < 4) {
                if (this.createPointFeatures) {
                    polygon = getPolygon(polygon.getCentroid());
                }
            } else {

                polygon = polygon.buffer(0);
                if (this.computeConvexHull) {
                    polygon = polygon.convexHull();
                }

                // if (!this.ignoreHoles) {
                // for (LinearRing hole : getLinearRings(blob.getInnerContours())) {
                // Geometry h = new Polygon(hole, null, gfact);
                // h = h.buffer(0);
                // polygon = polygon.difference(h);
                // }
                // }
            }

            /*
             * clip to context shape
             */
            if (polygon != null && intersect) {
                polygon = polygon.intersection(boundingBox.getJTSGeometry());
            }

        } catch (Throwable t) {
            // just return null
            polygon = null;
        }

        if (polygon == null || polygon.isEmpty()) {
            return null;
        }

        /*
         * value attribute as shape metadata. Will only get used if we categorize.
         */
        Object value = feature.getAttribute("value");
        Shape ret = Shape.create(polygon, this.grid.getProjection());

        if (value != null) {
            ret.getMetadata().put("value", value);
        }

        return ret;
    }

    @Override
    public Object eval(IContextualizationScope context, Object... parameters) throws KlabException {
        return new PolygonInstantiatorJAI(Parameters.create(parameters), context);
    }

    private Geometry getPolygon(Point point2d) {

        int x = (int) point2d.getX();
        int y = (int) point2d.getY();
        return ((Shape) ((Grid) grid).getCell(grid.getOffset(x, y)).getShape()).getJTSGeometry();
    }

    @Override
    public IArtifact.Type getType() {
        return IArtifact.Type.OBJECT;
    }

}
