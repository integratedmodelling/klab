package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.api.observations.scale.space.ISpaceLocator;
import org.integratedmodelling.klab.common.Geometry;
import org.integratedmodelling.klab.common.IndexLocator;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.api.ISpatialIndex;
import org.integratedmodelling.klab.components.geospace.api.ITessellation;
import org.integratedmodelling.klab.components.geospace.extents.mediators.FeaturesToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToFeatures;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToFeatures;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.Subgrid;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabUnsupportedFeatureException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.Extent;
import org.integratedmodelling.klab.scale.Scale.Mediator;
import org.integratedmodelling.klab.utils.collections.IterableAdapter;
import org.integratedmodelling.klab.utils.collections.IteratorAdapter;

public class Space extends Extent implements ISpace {

    private Shape         shape;
    private Grid          grid;
    private Envelope      envelope;
    private Projection    projection;
    private ITessellation features;
    private boolean       consistent    = false;
    private String        gridSpecs     = null;
    private boolean       generic       = false;
    private Dimension     originalSpecs = null;

    private static Space  EMPTY_SPACE   = new Space(Shape.empty());

    public static Space create(Shape shape) {
        return new Space(shape);
    }

    public static final Space empty() {
        return EMPTY_SPACE;
    }

    public static Space create(Dimension dimension) {

        double[] bbox = dimension.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
        double[] llat = dimension.getParameters().get(Geometry.PARAMETER_SPACE_LONLAT, double[].class);
        String gridres = dimension.getParameters().get(Geometry.PARAMETER_SPACE_GRIDRESOLUTION, String.class);
        String projectionSpec = dimension.getParameters()
                .get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
        String shapeSpec = dimension.getParameters().get(Geometry.PARAMETER_SPACE_SHAPE, String.class);
        long[] dims = dimension.shape();
        boolean generic = false;

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
            shape = Shape.create(shapeSpec);
        } else if (bbox != null && projection != null) {
            shape = Shape.create(bbox[0], bbox[2], bbox[1], bbox[3], projection);
        } else if (llat != null) {
            shape = Shape.create(llat[0], llat[1], Projection.getLatLon());
        } else {
            generic = true;
        }

        if (shape != null) {
            if (gridres != null) {
                return create(shape, org.integratedmodelling.klab.components.geospace.services.Space
                        .parseResolution(gridres));
            } else if (dims.length > 1) {
                return create(shape, dims[0], dims[1]);
            }
        }

        Space ret = shape == null ? new Space() : create(shape);

        if ((ret.generic = generic)) {
            if (ret.projection == null) {
                // keep whatever info we have to use in a merge()
                ret.projection = projection;
                ret.shape = shape;
                ret.originalSpecs = dimension;
            }
        }

        return ret;
    }

    public static Space create(Shape shape, double resolutionInMeters) {
        Grid grid = Grid.create(shape, resolutionInMeters);
        Space ret = new Space(shape, grid);
        ret.gridSpecs = resolutionInMeters + " m";
        return ret;
    }

    public static Space create(Shape shape, long xCells, long yCells) {
        Grid grid = Grid.create(shape, xCells, yCells);
        Space ret = new Space(shape, grid);
        return ret;
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

        this.projection = shape.getProjection();
        this.shape = shape;
        this.grid = grid;
        // if (grid != null) {
        // this.grid = Grid.create(this.shape, grid.getXCells(), grid.getYCells());
        // if (grid instanceof Grid) {
        // this.grid.offsetInSupergrid = ((Grid) grid).offsetInSupergrid;
        // this.grid.superGridId = ((Grid) grid).superGridId;
        // }
        // this.envelope = ((Grid) grid).getEnvelope();
        // } else {
        this.envelope = this.shape.getEnvelope();
        // }
    }

    public String toString() {
        return "<SPACE " + (grid != null ? grid.toString() : "")
                + (features != null ? features.toString() : "") + " @"
                + shape + ">";
    }

    private Space(Shape shape) {
        this.shape = shape;
        this.projection = shape.getProjection();
        this.envelope = shape.getEnvelope();
        this.consistent = true;
    }

    @Override
    public int getScaleRank() {
        return envelope.getScaleRank();
    }

    @Override
    public IExtent collapse() {
        return getShape().copy();
    }

    @Override
    public boolean isCovered(long stateIndex) {
        return grid != null ? grid.isCovered(stateIndex) : true;
    }

    @Override
    public IExtent merge(IExtent extent) throws KlabException {
        if (extent instanceof ISpace) {
            return Space.createMergedExtent(this, (ISpace) extent);
        }
        throw new IllegalArgumentException("a Shape cannot merge an extent of type " + extent.getType());

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
            return new long[] { features.size() };
        } else if (grid != null) {
            return new long[] { grid.getXCells(), grid.getYCells() };
        }
        return new long[] { 1 };
    }

    @Override
    public long getOffset(long[] dimOffsets) {
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

    @Override
    public long[] getDimensionOffsets(long linearOffset) {
        // useless but was a a parameter in 0.9.x - see if it still serves any purpose
        // before removing
        boolean rowFirst = true;
        if (features != null) {
            return new long[] { linearOffset };
        } else if (grid != null) {
            if (rowFirst) {
                long[] xy = grid.getXYOffsets(linearOffset);
                return new long[] { xy[1], xy[0] };
            }
            return grid.getXYOffsets(linearOffset);
        }
        return new long[] { 0 };
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

    public IExtent intersection(ITopologicallyComparable<?> obj) throws KlabException {

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

        return adaptToShape(this.shape.intersection(other), other);
    }

    public IExtent difference(ITopologicallyComparable<?> obj) throws KlabException {

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

    private IExtent adaptToShape(Shape common, ISpace other) {

        if (common.isEmpty()) {
            return this;
        }
        
        if (!hasGrid(other) && !hasFeatures(other)) {
        	Grid theGrid = grid;
            if (theGrid != null) {
            	while (theGrid instanceof Subgrid) {
            		theGrid = ((Subgrid)theGrid).getOriginalGrid();
            	}
                // reset grid boundaries to merged shape
                // review grid, using conformant (snap) if possible (error threshold?)
                // error should be in terms of the max discrepancy compared to size of
                // common shape
                double error = Subgrid.getSubsettingError(theGrid, common);
                if (error <= Configuration.INSTANCE.getAcceptedSubsettingError()) {
                    return new Space(common, Subgrid.create(theGrid, common));
                } else {
                    throw new KlabUnsupportedFeatureException("Unsupported operation: non-conformant grid to grid (subsetting error = "
                            + error);
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

    public IExtent union(ITopologicallyComparable<?> obj) throws KlabException {

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
            throw new IllegalArgumentException("cannot check containment of a space extent within a "
                    + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        return shape == null || oShape == null ? false : shape.geometry.contains(oShape.geometry);
    }

    @Override
    public boolean overlaps(IExtent other) throws KlabException {
        if (!(other instanceof Space)) {
            throw new IllegalArgumentException("cannot overlap a space extent with a "
                    + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        return shape == null || oShape == null ? false : shape.geometry.overlaps(oShape.geometry);
    }

    @Override
    public boolean intersects(IExtent other) throws KlabException {
        if (!(other instanceof Space)) {
            throw new IllegalArgumentException("cannot intersect a space extent with a "
                    + other.getClass().getCanonicalName());
        }
        Shape oShape = (Shape) ((ISpace) other).getShape();
        return shape == null || oShape == null ? false : shape.geometry.intersects(oShape.geometry);
    }

    @Override
    public double getCoveredExtent() {
        return getShape().getStandardizedGeometry().getArea();
    }

    @Override
    public Iterator<IExtent> iterator() {
        if (grid != null) {
            return new IteratorAdapter<IExtent>(grid.iterator());
        }
        if (features != null) {
            return features.iterator();
        }
        return shape.iterator();
    }

    @Override
    public ISpace getExtent() {
        return create(getShape());
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
        // TODO Auto-generated method stub
        // TODO REMEMBER TO ALSO COPY THE SCALEID
        return null;
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
        args.add("shape");
        args.add(shape.toString());
        if (gridSpecs != null) {
            args.add("grid");
            args.add(gridSpecs);
        }
        return new KimServiceCall("space", args.toArray());
    }

    @Override
    public double getCoverage() {
        // TODO Auto-generated method stub
        return 0;
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

    @Override
    public ISpace at(ILocator locator) {
        return null;
    }

    // @Override
    public Iterable<ILocator> over(Type dimension) {
        if (dimension != Dimension.Type.SPACE) {
            throw new IllegalArgumentException("cannot iterate a spatial extent over " + dimension);
        }
        return grid != null ? new IterableAdapter<ILocator>(grid)
                : (features != null ? new IterableAdapter<ILocator>(features) : Collections.singleton(shape));
    }

    @Override
    public long[] shape() {
        if (this.grid != null) {
            return new long[] { this.grid.getXCells(), this.grid.getYCells() };
        }
        if (this.features != null) {
            return new long[] { this.features.size() };
        }
        return shape.shape();
    }

    @Override
    public long getOffset(ILocator index) {

        if (this.grid != null) {
            if (index instanceof Cell) {
                return this.grid.getOffset((Cell) index);
            } else if (index instanceof IndexLocator && ((IndexLocator) index).getCoordinates().length == 2) {
                return this.grid.getOffset(((IndexLocator) index).getCoordinates()[0], ((IndexLocator) index)
                        .getCoordinates()[1]);
            } else if (index instanceof ISpace
                    && ((ISpace) index).getShape().getGeometryType() == IShape.Type.POINT) {
                Shape shape = (Shape) ((ISpace) index).getShape().transform(getProjection());
                return this.grid.getOffsetFromWorldCoordinates(shape.getJTSGeometry()
                        .getCoordinates()[0].x, shape.getJTSGeometry().getCoordinates()[0].y);
            }
        } else if (this.features != null) {
            // TODO support direct indexing with IndexLocator and point indexing with latlon
            // and point coordinates
        }
        throw new IllegalArgumentException("cannot use " + index + " as a space locator");
    }

    @Override
    public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {
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
    public String encode() {
        if (grid != null) {
            return "S2(" + grid.getXCells() + "," + grid.getYCells() + "){" + grid.getEnvelope().encode()
                    + ",shape="
                    + getShape().getWKB() + ",proj=" + getProjection().getSimpleSRS() + "}";
        } else if (features != null) {
            return "s1(" + features.size() + "){proj=" + getProjection().getSimpleSRS() + ","
                    + getEnvelope().encode()
                    + "}";
        }
        return getShape().encode();
    }

    @Override
    public IScaleMediator getMediator(IExtent extent) {
        // TODO Auto-generated method stub

        if (!(extent instanceof ISpace)) {
            throw new IllegalArgumentException("extent " + extent + " cannot mediate to " + this);
        }

        ISpace other = (ISpace) extent;

        if (grid != null) {
            if (other instanceof Space && ((Space) other).grid != null) {
                return new GridToGrid(grid, ((Space) other).grid);
            } else if (other instanceof Space && ((Space) other).features != null) {
                return new GridToFeatures(grid, ((Space) other).features);
            } else {
                return new GridToShape(grid, (Shape) other.getShape());
            }

        } else if (features != null) {
            if (other instanceof Space && ((Space) other).grid != null) {

            } else if (features != null && other instanceof Shape) {

            } else {
                return new FeaturesToShape(features, (Shape) other.getShape());
            }
        } else {
            if (other instanceof Space && ((Space) other).grid != null) {
                return new ShapeToGrid(getShape(), ((Space) other).grid);
            } else if (other instanceof Space && ((Space) other).features != null) {
                return new ShapeToFeatures(getShape(), ((Space) other).features);
            } else {
                return new ShapeToShape(getShape(), (Shape) other.getShape());
            }
        }

        /*
         * * if this == extent return identity; if this.grid != null && extent.grid !=
         * null && this.grid isa subgrid && this.grid.originalgrid == extent.grid return
         * conformant offset remapper; if this.grid != null && extent.grid != null &&
         * extent.grid isa subgrid && extent.grid.originalgrid == this.grid return
         * inverse conformant offset remapper;
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

        Shape common = connector.equals(LogicalConnector.INTERSECTION) ? this.shape.intersection(other)
                : this.shape.union(other);

        return new Space(common);
    }

    @Override
    public SpatialExtent getExtentDescriptor() {
        return getShape().getExtentDescriptor();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends ILocator> T as(Class<T> cls) {
        if (ISpaceLocator.class.isAssignableFrom(cls)) {
            return (T) envelope.asLocator();
        }
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

    @Override
    public boolean isGeneric() {
        return generic;
    }

    public static IExtent createMergedExtent(ISpace destination, ISpace source) {
        // TODO Auto-generated method stub
        // if (!(extent instanceof Space)) {
        // throw new KlabValidationException("space extent cannot merge non-space extent");
        // }
        //
        // Space ret = new Space(this);
        // Space oth = (Space) extent;

        /*
         * TODO figure out mandatory vs. not. These are all false, which probably
         * shouldn't be - either pass to merge or be smarter.
         */
        // if (oth.grid != null) {
        // ret.set(oth.grid, force);
        // } else if (oth.features != null) {
        // ret.set(oth.features, oth.shape, force);
        // } else if (oth.shape != null) {
        // ret.set(oth.shape, force);
        // } else if (oth.gridResolution > 0.0) {
        // ret.setGridResolution(oth.gridResolution, force);
        // }
        // return ret;

        return destination;
    }
}
