package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Authorities;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Encoding;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex;
import org.integratedmodelling.klab.components.geospace.api.ITessellation;
import org.integratedmodelling.klab.components.geospace.extents.Grid.CellImpl;
import org.integratedmodelling.klab.components.geospace.extents.mediators.FeaturesToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.FeaturesToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToFeatures;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.Subgrid;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnimplementedException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.Scale;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.NumberUtils;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Utils;
import org.integratedmodelling.klab.utils.collections.IterableAdapter;
import org.integratedmodelling.klab.utils.collections.IteratorAdapter;

public class Space extends AbstractSpatialExtent implements ISpace {

    private Shape shape;
    private Grid grid;
    private Envelope envelope;
    private Projection projection;
    private ITessellation features;
    private boolean consistent = false;
    private String gridSpecs = null;
    private boolean generic = false;
    // used only if generic, to remember a regular statement from the encoding geometry
    private boolean regular = false;
    private boolean forceGrid;

    private static Space EMPTY_SPACE = new Space(Shape.empty());

    public static Space create(Shape shape) {
        return new Space(shape);
    }

    public static final Space empty() {
        return EMPTY_SPACE;
    }

    public static ISpace create(Dimension dimension) {
        return create(dimension, null);
    }

    public static Space create(IShape shape, IQuantity resolution) {
        return create((Shape) shape, org.integratedmodelling.klab.components.geospace.services.Space.parseResolution(resolution));
    }

    public static ISpace create(Dimension dimension, IQuantity resolution) {

        String authority = dimension.getParameters().get(Geometry.PARAMETER_ENUMERATED_AUTHORITY, String.class);
        String baseidentity = dimension.getParameters().get(Geometry.PARAMETER_ENUMERATED_BASE_IDENTITY, String.class);
        String identity = dimension.getParameters().get(Geometry.PARAMETER_ENUMERATED_IDENTIFIER, String.class);

        if (authority != null) {
            return new EnumeratedSpace(Authorities.INSTANCE.getAuthority(authority), null);
        } else if (baseidentity != null) {
            return new EnumeratedSpace(null, Concepts.INSTANCE.declare(Concepts.INSTANCE.declare(identity)));
        } else if (identity != null) {
            return new EnumeratedSpace(Concepts.INSTANCE.declare(Concepts.INSTANCE.declare(identity)));
        }

        double[] bbox = dimension.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
        double[] llat = dimension.getParameters().get(Geometry.PARAMETER_SPACE_LONLAT, double[].class);
        String gridres = dimension.getParameters().get(Geometry.PARAMETER_SPACE_GRIDRESOLUTION, String.class);
        String projectionSpec = dimension.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
        String shapeSpec = dimension.getParameters().get(Geometry.PARAMETER_SPACE_SHAPE, String.class);
        long[] dims = dimension.shape();
        boolean generic = false;
        boolean simplified = Boolean.parseBoolean(dimension.getParameters().get("simplified", "false"));

        Projection projection = null;
        Shape shape = null;

        if (projectionSpec != null) {
            projection = Projection.create(projectionSpec);
        } else if (llat != null) {
            projection = Projection.getLatLon();
        } else {
            generic = true;
        }

        if (shapeSpec != null) {
            if (projection == null) {
                shape = Shape.create(shapeSpec);
            } else {
                shape = Shape.create(shapeSpec, projection);
            }
        } else if (bbox != null && projection != null) {
            shape = Shape.create(bbox[0], bbox[2], bbox[1], bbox[3], projection);
        } else if (llat != null) {
            shape = Shape.create(llat[0], llat[1], Projection.getLatLon());
        } else {
            generic = true;
        }

        if (shape != null) {

            shape.setSimplified(simplified);

            if (resolution != null && !simplified) {
                shape = shape.getSimplified(resolution);
            }

            if (gridres != null) {
                return create(shape, org.integratedmodelling.klab.components.geospace.services.Space.parseResolution(gridres));
            } else if (dims.length > 1) {
                return create(shape, dims[0], dims[1]);
            }
        }

        Space ret = shape == null ? new Space() : create(shape);

        if (generic && dimension.isRegular()) {
            ret.regular = true;
        }

        if ((ret.generic = generic)) {
            if (ret.projection == null) {
                // keep whatever info we have to use in a merge()
                ret.projection = projection;
                ret.shape = shape;
            }
        }

        return ret;
    }

    public static Space constraint(Shape shape, boolean grid) {
        Space ret = new Space(shape);
        ret.consistent = false;
        ret.forceGrid = true;
        return ret;
    }

    public static Space create(Shape shape, double resolutionInMeters) {
        Grid grid = Grid.create(shape, resolutionInMeters);
        if (grid.isConsistent()) {
            Space ret = new Space(shape, grid);
            ret.gridSpecs = resolutionInMeters + " m";
            return ret;
        }
        return new Space(shape);
    }

    private static Space create(Shape shape, long xCells, long yCells) {
        Grid grid = null;
        if (xCells > 1 || yCells > 1) {
            grid = Grid.create(shape, xCells, yCells);
        }
        Space ret = grid == null ? new Space(shape) : new Space(shape, grid);
        return ret;
    }

    /**
     * Processes the space annotations used to constrain a model's spatial extent.
     * 
     * @param spaceAnnotation
     * @return
     */
    public static ISpace create(IAnnotation spaceAnnotation) {

        if (spaceAnnotation.containsKey(IServiceCall.DEFAULT_PARAMETER_NAME)) {
            if (spaceAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) instanceof Integer) {
                // year (or ms)
                spaceAnnotation.put("year", spaceAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME));
            } else {
                String s = spaceAnnotation.get(IServiceCall.DEFAULT_PARAMETER_NAME).toString();
                if ("grid".equals(s)) {
                    spaceAnnotation.put("grid", null);
                } else if (s.contains(" ")) {
                    spaceAnnotation.put("shape", s);
                } else {
                    spaceAnnotation.put("urn", s);
                }
            }
        }

        return (ISpace) new org.integratedmodelling.klab.components.geospace.services.Space()
                .eval(new Expression.SimpleScope(Klab.INSTANCE.getRootMonitor()), spaceAnnotation);
    }

    private Space() {
        //
    }

    private Space(Space extent) {
        this.shape = extent.shape;
        this.grid = extent.grid;
        this.envelope = extent.envelope;
        this.projection = extent.projection;
        this.gridSpecs = extent.gridSpecs;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((envelope == null) ? 0 : envelope.hashCode());
        result = prime * result + ((features == null) ? 0 : features.hashCode());
        result = prime * result + (generic ? 1231 : 1237);
        result = prime * result + ((grid == null) ? 0 : grid.hashCode());
        result = prime * result + ((projection == null) ? 0 : projection.hashCode());
        result = prime * result + ((shape == null) ? 0 : shape.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Space other = (Space) obj;
        if (envelope == null) {
            if (other.envelope != null)
                return false;
        } else if (!envelope.equals(other.envelope))
            return false;
        if (features == null) {
            if (other.features != null)
                return false;
        } else if (!features.equals(other.features))
            return false;
        if (generic != other.generic)
            return false;
        if (grid == null) {
            if (other.grid != null)
                return false;
        } else if (!grid.equals(other.grid))
            return false;
        if (projection == null) {
            if (other.projection != null)
                return false;
        } else if (!projection.equals(other.projection))
            return false;
        if (shape == null) {
            if (other.shape != null)
                return false;
        } else if (!shape.equals(other.shape))
            return false;
        return true;
    }

    private Space(Shape shape, Grid grid) {

        if (!shape.isSimplified() && grid.getXCells() * grid.getYCells() > 1000) {
            shape = shape.getSimplified(Math.max(grid.getCellHeight(), grid.getCellWidth()));
        }
        this.projection = shape.getProjection();
        this.shape = shape;
        this.grid = grid;
        this.envelope = this.shape.getEnvelope();
        grid.setSpace(this);
    }

    public String toString() {
        return "<SPACE " + (grid != null ? grid.toString() : "") + (features != null ? features.toString() : "") + " @" + shape
                + ">";
    }

    private Space(Shape shape) {
        this.shape = shape;
        if (shape != null) {
            this.projection = shape.getProjection();
            this.envelope = shape.getEnvelope();
        }
        this.consistent = true;
    }

    @Override
    public int getScaleRank() {
        return envelope.getScaleRank();
    }

    @Override
    public IExtent collapse() {
        return isEmpty() ? this : getShape().copy();
    }

    @Override
    public boolean isCovered(long stateIndex) {
        return grid != null ? grid.isCovered(stateIndex) : true;
    }

    @Override
    public ISpace mergeContext(IExtent extent) throws KlabException {
        if (extent instanceof ISpace) {
            return createMergedExtent(this, (ISpace) extent);
        }
        throw new IllegalArgumentException("cannot merge spatial extent " + extent.getClass().getCanonicalName() + "  into "
                + getClass().getCanonicalName());

    }

    @Override
    public boolean isConsistent() {
        return consistent;
    }

    @Override
    public boolean isEmpty() {
        return shape == null || shape.isEmpty();
    }

    @Override
    public long[] getDimensionSizes() {
        if (features != null) {
            return new long[]{features.size()};
        } else if (grid != null) {
            return new long[]{grid.getXCells(), grid.getYCells()};
        }
        return new long[]{1};
    }

    @Override
    public long getOffset(long... dimOffsets) {
        if (features != null) {
            if (dimOffsets.length != 1) {
                throw new IllegalArgumentException("can't address offset: tessellation space has one dimension");
            }
            return dimOffsets[0];
        }
        if (grid != null) {
            if (dimOffsets.length != 2) {
                throw new IllegalArgumentException("can't address offset: grid space has two dimensions");
            }
            return grid.getOffset(dimOffsets[0], dimOffsets[1]);
        }
        if (dimOffsets.length != 1 || dimOffsets[0] != 0) {
            throw new IllegalArgumentException("can't address offset: shape space has one dimension and one extent");
        }
        return 0;
    }

    // @Override
    public long[] getDimensionOffsets(long linearOffset) {
        // useless but was a a parameter in 0.9.x - see if it still serves any purpose
        // before removing
        boolean rowFirst = true;
        if (features != null) {
            return new long[]{linearOffset};
        } else if (grid != null) {
            if (rowFirst) {
                long[] xy = grid.getXYOffsets(linearOffset);
                return new long[]{xy[1], xy[0]};
            }
            return grid.getXYOffsets(linearOffset);
        }
        return new long[]{0};
    }

    // @Override
    // public long locate(Locator locator) {
    //
    // if (locator instanceof SpaceLocator) {
    // if (locator.isAll())
    // return GENERIC_LOCATOR;
    // if (grid != null) {
    // if (((SpaceLocator) locator).isLatLon()) {
    // return grid.getOffsetFromWorldCoordinates(((SpaceLocator) locator).lon,
    // ((SpaceLocator) locator).lat);
    // } else {
    // return grid.getOffset(((SpaceLocator) locator).x, ((SpaceLocator)
    // locator).y);
    // }
    // }
    // }
    // return INAPPROPRIATE_LOCATOR;
    // }

    @Override
    public Mediator getMediator(IExtent extent, IObservable observable, IConcept trait) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long size() {
        if (grid != null) {
            return grid.getCellCount();
        }
        if (features != null) {
            return features.size();
        }
        return 1;
    }

    public ISpace intersection(ITopologicallyComparable<?> obj) throws KlabException {

        Space ret = new Space(this);

        if (this.shape == null) {
            return ret;
        }

        Shape other = null;
        if (obj instanceof Space) {
            other = ((Space) obj).getShape();
        } else if (obj instanceof Shape) {
            other = (Shape) obj;
        }

        if (other == null) {
            return new Space();
        }

        Shape intersection = this.shape.intersection(other);

        return adaptToShape(intersection, other);
    }

    public ISpace difference(ITopologicallyComparable<?> obj) throws KlabException {

        Space ret = new Space(this);

        if (this.shape == null) {
            return ret;
        }

        Shape other = null;
        if (obj instanceof Space) {
            other = ((Space) obj).getShape();
        } else if (obj instanceof Shape) {
            other = (Shape) obj;
        }

        if (other == null) {
            return new Space();
        }

        return adaptToShape(this.shape.difference(other), other);
    }

    private ISpace adaptToShape(Shape common, ISpace other) {

        if (common.isEmpty()) {
            return this;
        }

        if (!hasGrid(other) && !hasFeatures(other)) {
            Grid theGrid = grid;
            if (theGrid != null) {
                while(theGrid instanceof Subgrid) {
                    theGrid = ((Subgrid) theGrid).getOriginalGrid();
                }
                // reset grid boundaries to merged shape
                // review grid, using conformant (snap) if possible (error threshold?)
                // error should be in terms of the max discrepancy compared to size of
                // common shape
                double error = Subgrid.getSubsettingError(theGrid, common);
                if (error <= Configuration.INSTANCE.getAcceptedSubsettingError()) {
                    return new Space(common, Subgrid.create(theGrid, common));
                } else {
                    throw new KlabUnsupportedFeatureException(
                            "Unsupported operation: non-conformant grid to grid (subsetting error = " + error);
                }
            } else if (features != null) {
                // cut features to merged shape
                System.out.println("adapting features to shape");
            }
            // else fall back to default
        } else if (hasGrid(other)) {
            if (grid != null) {
                // merge grids
                System.out.println("adapting grid to grid");
            } else if (features != null) {
                // features stay, non-trivial mediation on a feature-by-feature basis
                System.out.println("adapting grid to features");
            } else {
                // inherit grid
                System.out.println("shape inheriting grid");
            }
        } else if (hasFeatures(other)) {
            if (grid != null) {
                // reset grid boundaries to common shape
                // activate only the cells that fall on any of the other's features within the
                // merged shape
                System.out.println("adapting grid to features");
            } else if (features != null) {
                // topological intersection of features x features within merged shape - this
                // one is tough
                System.out.println("adapting features to features");
            } else {
                // cut up own features to common shape, remove those that don't fit at all
                System.out.println("adapting features to fit in shape");
            }
        }

        // fallback case, just a 1x shape extent with the merged shape
        return new Space(common);
    }

    public static boolean hasGrid(ISpace other) {
        return other instanceof Space && ((Space) other).grid != null;
    }

    private boolean hasFeatures(ISpace other) {
        return other instanceof Space && ((Space) other).features != null;
    }

    public ISpace union(ITopologicallyComparable<?> obj) throws KlabException {

        Space ret = new Space(this);

        if (this.shape == null) {
            return ret;
        }

        Shape other = null;
        if (obj instanceof Space) {
            other = ((Space) obj).getShape();
        } else if (obj instanceof Shape) {
            other = (Shape) obj;
        }

        if (other == null) {
            return new Space();
        }

        return adaptToShape(this.shape.union(other), other);
    }

    @Override
    public boolean contains(IExtent other) throws KlabException {
        if (!(other instanceof ISpace)) {
            throw new IllegalArgumentException(
                    "cannot check containment of a space extent within a " + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        if (this.shape == null || oShape == null) {
            // we contain multitudes or the other doesn't care
            return true;
        }
        return shape.shapeGeometry.contains(oShape.shapeGeometry);
    }

    @Override
    public boolean overlaps(IExtent other) throws KlabException {
        if (!(other instanceof Space)) {
            throw new IllegalArgumentException("cannot overlap a space extent with a " + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        return shape == null || oShape == null ? false : shape.shapeGeometry.overlaps(oShape.shapeGeometry);
    }

    @Override
    public boolean intersects(IExtent other) throws KlabException {
        if (!(other instanceof Space)) {
            throw new IllegalArgumentException("cannot intersect a space extent with a " + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        return shape == null || oShape == null ? false : shape.shapeGeometry.intersects(oShape.shapeGeometry);
    }

    @Override
    public double getCoveredExtent() {
        return getShape().getStandardizedGeometry().getArea();
    }

    @Override
    public Iterator<ILocator> iterator() {
        if (grid != null) {
            return new IteratorAdapter<ILocator>(grid.iterator());
        }
        if (features != null) {
            return features.iterator();
        }
        return shape.iterator();
    }

    @Override
    public ISpace getExtent() {
        return grid == null ? create(getShape()) : create(getShape(), grid.getLinearResolutionMeters());
    }

    @Override
    public Envelope getEnvelope() {
        return envelope;
    }

    @Override
    public IProjection getProjection() {
        return projection;
    }

    // @Override
    public IGrid getGrid() {
        return grid;
    }

    // @Override
    public ITessellation getTessellation() {
        return features;
    }

    @Override
    public Shape getShape() {
        return shape;
    }

    // @Override
    public ISpatialIndex getIndex(boolean makeNew) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Space copy() {

        Space ret = new Space();

        ret.shape = this.shape == null ? null : this.shape.copy();
        ret.grid = this.grid == null ? null : this.grid.copy();
        ret.envelope = this.envelope == null ? null : this.envelope.copy();
        ret.projection = this.projection;
        ret.gridSpecs = this.gridSpecs;
        // TODO really?
        ret.setScaleId(this.getScaleId());
        // TODO Auto-generated method stub
        // TODO REMEMBER TO ALSO COPY THE SCALEID
        return ret;
    }

    @Override
    public ISpace getExtent(long stateIndex) {

        if (this.size() == 1 && stateIndex == 0) {
            return this;
        }
        if (grid != null) {
            return grid.getCell(stateIndex);
        }
        if (features != null) {
            return features.getFeature(stateIndex);
        }
        return null;
    }

    @Override
    public IServiceCall getKimSpecification() {
        List<Object> args = new ArrayList<>(4);
        if (shape != null) {
            args.add("shape");
            args.add(shape.toString());
        }
        if (gridSpecs != null) {
            args.add("grid");
            args.add(gridSpecs);
        }
        return new KimServiceCall("space", args.toArray());
    }

    @Override
    public Type getType() {
        return Dimension.Type.SPACE;
    }

    @Override
    public boolean isRegular() {
        return features == null;
    }

    @Override
    public int getDimensionality() {
        return 2;
    }

    public Iterable<ILocator> over(Type dimension) {
        if (dimension != Dimension.Type.SPACE) {
            throw new IllegalArgumentException("cannot iterate a spatial extent over " + dimension);
        }
        return grid != null
                ? new IterableAdapter<ILocator>(grid)
                : (features != null ? new IterableAdapter<ILocator>(features) : Collections.singleton(shape));
    }

    @Override
    public long[] shape() {
        if (this.grid != null) {
            return new long[]{this.grid.getXCells(), this.grid.getYCells()};
        }
        if (this.features != null) {
            return new long[]{this.features.size()};
        }
        return shape == null ? null : shape.shape();
    }

    @Override
    public ISpace merge(ITopologicallyComparable<?> other, LogicalConnector how, MergingOption... options) {
        if (how == LogicalConnector.UNION) {
            return union(other);
        } else if (how == LogicalConnector.INTERSECTION) {
            return intersection(other);
        } else if (how == LogicalConnector.EXCLUSION) {
            return difference(other);
        }
        throw new IllegalArgumentException("cannot merge a spatial extent with " + other);
    }

    @Override
    public IParameters<String> getParameters() {
        return baseDimension.getParameters();
    }

    @Override
    public String encode(Encoding... options) {

        Set<Encoding> opts = new HashSet<>();
        if (options != null) {
            for (Encoding opt : options) {
                opts.add(opt);
            }
        }

        if (grid != null) {

            return "S2(" + grid.getXCells() + "," + grid.getYCells() + "){" + grid.getEnvelope().encode()
                    + (opts.contains(Encoding.SKIP_GRID_SHAPE) ? "" : (",shape=" + getShape().getWKB())) + ",proj="
                    + getProjection().getSimpleSRS() + "}";

        } else if (features != null) {
            return "s1(" + features.size() + "){proj=" + getProjection().getSimpleSRS() + "," + getEnvelope().encode() + "}";
        }

        if (generic) {
            return regular ? (forceGrid ? "\u03A32" : "\u03A31") : (forceGrid ? "\u03C32" : "\u03C31");
        }

        return shape == null ? (forceGrid ? "S2" : "s1") : getShape().encode();
    }

    @Override
    public IScaleMediator getMediator(IExtent extent) {
        if (!(extent instanceof ISpace)) {
            throw new IllegalArgumentException("extent " + extent + " cannot mediate to " + this);
        }

        ISpace other = (ISpace) extent;

        if (grid != null) {
            if (other instanceof Space && ((Space) other).grid != null) {
                return new GridToGrid(((Space) other).grid, grid);
            } else if (other instanceof Space && ((Space) other).features != null) {
                return new FeaturesToGrid(grid, ((Space) other).features);
            } else {
                return new ShapeToGrid(grid, (Shape) other.getShape());
            }

        } else if (features != null) {
            if (other instanceof Space && ((Space) other).grid != null) {

            } else if (features != null && other instanceof Shape) {

            } else {
                return new ShapeToFeatures((Shape) other.getShape(), features);
            }
        } else {
            if (other instanceof Space && ((Space) other).grid != null) {
                return new GridToShape(getShape(), ((Space) other).grid);
            } else if (other instanceof Space && ((Space) other).features != null) {
                return new FeaturesToShape(getShape(), ((Space) other).features);
            } else {
                return new ShapeToShape((Shape) other.getShape(), getShape());
            }
        }

        /*
         * * if this == extent return identity; if this.grid != null && extent.grid != null &&
         * this.grid isa subgrid && this.grid.originalgrid == extent.grid return conformant offset
         * remapper; if this.grid != null && extent.grid != null && extent.grid isa subgrid &&
         * extent.grid.originalgrid == this.grid return inverse conformant offset remapper;
         * 
         */

        return null;
    }

    @Override
    public IExtent mergeCoverage(IExtent obj, LogicalConnector connector) {

        Space ret = new Space(this);

        if (this.shape == null) {
            return ret;
        }

        Shape other = null;
        if (obj instanceof Space) {
            other = ((Space) obj).getShape();
        } else if (obj instanceof Shape) {
            other = (Shape) obj;
        }

        if (other == null) {
            return new Space();
        }

        Shape common = connector.equals(LogicalConnector.INTERSECTION) ? this.shape.intersection(other) : this.shape.union(other);

        // inherit the grid we're most aligned with
        Grid refgrid = null;
        if (this.grid != null) {
            refgrid = this.grid;
        } else if (obj instanceof Space && ((Space) obj).grid != null) {
            refgrid = ((Space) obj).grid;
        }

        return refgrid == null ? new Space(common) : create(common, refgrid, true);
    }

    @Override
    public SpatialExtent getExtentDescriptor() {
        return getShape().getExtentDescriptor();
    }

    @Override
    public <T extends ILocator> T as(Class<T> cls) {
        // if (ISpaceLocator.class.isAssignableFrom(cls)) {
        // return (T) envelope.asLocator();
        // }
        return null;
    }

    @Override
    public double getStandardizedVolume() {
        return getShape().getStandardizedVolume();
    }

    @Override
    public double getStandardizedArea() {
        return getShape().getStandardizedArea();
    }

    @Override
    public double getStandardizedWidth() {
        return getShape().getStandardizedWidth();
    }

    @Override
    public double getStandardizedHeight() {
        return getShape().getStandardizedHeight();
    }

    @Override
    public double getStandardizedDepth() {
        return getShape().getStandardizedDepth();
    }

    @Override
    public double getStandardizedLength() {
        return getShape().getStandardizedLength();
    }

    public double getStandardizedDistance(ISpace space) {
        return getShape().getStandardizedDistance(space.getShape());
    }

    public static Grid extractGrid(IObservation obs) {
        ISpace ext = obs.getSpace();
        if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
            return null;
        }
        return ((Space) ext).grid;
    }

    public static Grid extractGrid(IScale obs) {
        ISpace ext = obs.getSpace();
        if (!(ext instanceof Space && ((Space) ext).getGrid() != null)) {
            return null;
        }
        return ((Space) ext).grid;
    }

    @Override
    public boolean isGeneric() {
        return generic;
    }

    public static ISpace createMergedExtent(ISpace destination, ISpace other) {

        /*
         * simplest first: if we have no obvious mediation and we pass a grid against a non-grid
         * that fully contains it, we want the other extent and nothing else.
         */
        if (destination.contains(other) && getGrid(destination) == null && getGrid(other) != null) {
            return other;
        }

        IShape resultShape = other.getShape();
        // add other's shape if destination doesn't have it
        if (resultShape == null) {
            resultShape = destination.getShape();
        } else {
            resultShape = destination.getShape().intersection(resultShape);
        }

        IGrid resultGrid = destination instanceof Space ? ((Space) destination).getGrid() : null;
        // same for the grid
        if (resultGrid == null && other instanceof Space) {
            resultGrid = ((Space) other).getGrid();
        }
        // this isn't used right now
        ITessellation resultFeatures = destination instanceof Space ? ((Space) destination).getTessellation() : null;

        /*
         * if destination wants a specific grid and other's is different, take the (possibly merged)
         * extent and make it that grid.
         */
        if (resultShape != null) {
            if (destination instanceof Space && ((Space) destination).forceGrid) {

                if (((Space) destination).gridSpecs == null) {

                    if (resultGrid == null) {

                        /*
                         * destination wants any grid and other doesn't have it: make a "default"
                         * grid using the geographical extent and the configured assumptions.
                         */
                        double resolution = ((Envelope) resultShape.getEnvelope()).getResolutionForZoomLevel(50, 2).getFirst();

                        return create((Shape) resultShape, resolution);

                    } else {

                        // what we want is the destination, already fully defined
                        return destination;
                    }

                } else {

                    if (resultGrid == null) {

                        /*
                         * make a grid according to specs
                         */
                        double resolution = org.integratedmodelling.klab.components.geospace.services.Space
                                .parseResolution(((Space) destination).gridSpecs);
                        return create((Shape) resultShape, resolution);

                    } else {

                        /*
                         * if we have same resolution, keep the grid from the source, otherwise make
                         * a different one and hope for mediators.
                         */
                        double resolution = org.integratedmodelling.klab.components.geospace.services.Space
                                .parseResolution(((Space) destination).gridSpecs);
                        if (NumberUtils.equal(((Grid) resultGrid).getLinearResolutionMeters(), resolution)) {
                            return destination;
                        }

                        return create((Shape) resultShape, (Grid) resultGrid, true);
                    }
                }

            } else if (resultShape != null && resultGrid != null) {

                return create((Shape) resultShape, (Grid) resultGrid, true);

            }
        }

        /*
         * TODO tesselations
         */

        /*
         * we have no mediation: TODO we should probably fail here.
         */

        return destination;
    }

    private static IGrid getGrid(ISpace other) {
        return other instanceof Space ? ((Space) other).getGrid() : null;
    }

    @Override
    public ISpace getBoundingExtent() {
        return getEnvelope().asShape();
    }

    @Override
    public ExtentDimension getExtentDimension() {
        return ExtentDimension.AREAL;
    }

    @Override
    public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
        if (locator == null || locator instanceof UniversalLocator) {
            return new Pair<>(getStandardizedArea(), Units.INSTANCE.SQUARE_METERS);
        }
        if (locator instanceof Scale) {
            return ((Scale) locator).getDimension(Type.SPACE) == null
                    ? null
                    : ((Scale) locator).getDimension(Type.SPACE).getStandardizedDimension(null);
        }
        if (locator instanceof ISpace) {
            return ((ISpace) locator).getStandardizedDimension(null);
        }
        throw new KlabUnimplementedException("Space::getStandardizedDimension()");
    }

    @Override
    public IGeometry getGeometry() {
        return geometry;
    }

    @Override
    public IExtent at(Object... locators) {

        // must be either one long or two doubles or one spatial object with point shape
        long offset = -1;
        double[] coordinates = null;
        long[] offsets = null;

        if (locators != null) {
            if (locators.length == 1) {

                if (locators[0] instanceof ISpace) {
                    if (((ISpace) locators[0]).getDimensionality() == 0) {
                        coordinates = ((ISpace) locators[0]).getStandardizedCentroid();
                    } else if (locators[0] instanceof IGrid.Cell) {
                        /*
                         * may be same grid or other grid.
                         */
                        IGrid.Cell otherCell = (IGrid.Cell) locators[0];
                        if (this.grid != null) {
                            if (((CellImpl) otherCell).getGrid().equals(this.grid)) {
                                return otherCell;
                            }
                            double[] center = otherCell.getCenter();
                            return this.grid.covers(center) ? this.grid.getCellAt(center, false) : null;
                        }
                    }
                } else if (locators[0] instanceof Number && !Utils.isFloatingPoint((Number) locators[0])) {
                    offset = ((Number) locators[0]).longValue();
                }

            } else if (locators.length == 2) {
                // coordinates - must be doubles
                if (locators[0] instanceof Number && locators[1] instanceof Number && Utils.isFloatingPoint((Number) locators[0])
                        && Utils.isFloatingPoint((Number) locators[1])) {
                    coordinates = new double[]{((Number) locators[0]).doubleValue(), ((Number) locators[1]).doubleValue()};
                } else if (locators[0] instanceof Number && locators[1] instanceof Number
                        && !Utils.isFloatingPoint((Number) locators[0]) && !Utils.isFloatingPoint((Number) locators[1])) {
                    offsets = new long[]{((Number) locators[0]).longValue(), ((Number) locators[1]).longValue()};
                }
            }
        }

        if (offset >= 0 || coordinates != null || offsets != null) {

            if (this.grid != null) {

                if (offset >= 0) {
                    return this.grid.getCell(offset);
                } else if (offsets != null) {
                    return this.grid.getCell(offsets[0], offsets[1]);
                } else {
                    return this.grid.getCellAt(coordinates, true);
                }

            } else if (this.features != null) {
                if (offset >= 0) {
                    return this.features.getFeature(offset);
                } else {
                    return this.features.getFeatureAt(coordinates, true);
                }
            }
        }

        throw new IllegalArgumentException("space: can't recognize or apply locators in at()");

    }

    @Override
    public double[] getStandardizedCentroid() {
        return getShape().getCenter(true);
    }

    // @Override
    // public IExtent adopt(IExtent extent, IMonitor monitor) {
    // // TODO Auto-generated method stub
    // return this;
    // }

    @Override
    protected IExtent contextualizeTo(IExtent other, IAnnotation constraint) {
        if (other instanceof Space) {
            // check for trivial 1x1 grid, which may come from networked objects
            if (((Space) other).getGrid() != null && (this.grid == null || (this.grid.getCellCount() == 1))) {
                return create(this.shape, ((Space) other).grid, true);
            }
        }
        return this;
    }

    /**
     * Create a new spatial extent from a shape using an existing grid as a model for gridding. The
     * grid is only a model and does not necessarily cover or intersect the shape.
     * 
     * @param shape the shape for the extent
     * @param grid the definition of a grid, which may or may not extend to cover or intersect the
     *        shape
     * @param align if true, try to align the grids so that cells may overlap, even if the extents
     *        do not
     * @return a new spatial extent
     */
    public static Space create(Shape shape, Grid grid, boolean align) {
        if (!align) {
            return create(shape.copy(), grid.getLinearResolutionMeters());
        }
        Grid newGrid = grid.snapWithinShape(shape);
        if (newGrid.isConsistent()) {
            Space ret = new Space(shape.copy(), newGrid);
            ret.gridSpecs = grid.getLinearResolutionMeters() + ".m";
            return ret;
        }
        return new Space(shape.copy());
    }

    @Override
    public double getDimensionSize(IUnit unit) {
        return getShape().getDimensionSize(unit);
    }

    @Override
    public boolean isDistributed() {
        return size() > 1 || isRegular();
    }

}
