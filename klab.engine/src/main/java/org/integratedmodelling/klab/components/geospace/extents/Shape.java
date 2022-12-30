package org.integratedmodelling.klab.components.geospace.extents;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eclipse.xtext.util.Arrays;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IGeometry.Encoding;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.IQuantity;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.scale.ExtentDimension;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IEnvelope;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.extents.mediators.FeaturesToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.GridToShape;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToShape;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.utils.Pair;
import org.locationtech.jts.algorithm.ConvexHull;
import org.locationtech.jts.awt.ShapeWriter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryCollection;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.Lineal;
import org.locationtech.jts.geom.MultiLineString;
import org.locationtech.jts.geom.MultiPoint;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.geom.Polygonal;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.WKTReader;
import org.locationtech.jts.simplify.TopologyPreservingSimplifier;

/**
 * TODO setup for using as locator.
 * 
 * @author ferdinando.villa
 *
 */
public class Shape extends AbstractSpatialAbstractExtent implements IShape {

	private static WKBWriter wkbWriter = new WKBWriter();

	Geometry shapeGeometry;
	// the geometry in WGS84, only cached if asked for and originally not in it.
	Geometry standardizedGeometry;
	Envelope envelope;
	IShape.Type type = null;
	Projection projection;
	IMetadata metadata;
	// to avoid multiple rounds of simplification
	private boolean simplified = false;

	public boolean isSimplified() {
		return simplified;
	}

	public void setSimplified(boolean simplified) {
		this.simplified = simplified;
	}

	// these are used to speed up repeated point-in-polygon operations like
	// those that RasterActivationLayer does.
	private PreparedGeometry preparedShape;
	private boolean preparationAttempted;

	// same geometry in appropriate meters projection. Set or computed on demand.
	private Geometry metered;

	public static Shape empty() {
		return new Shape();
	}

	@Override
	public String toString() {
		return projection.getSimpleSRS() + " " + shapeGeometry;
	}

	public static Shape create(String wkt) throws KlabValidationException {
		Shape ret = new Shape();
		ret.parseWkt(wkt);
		if (ret.shapeGeometry != null) {
			ret.envelope = Envelope.create(ret.shapeGeometry.getEnvelopeInternal(), ret.projection);
		}
		return ret;
	}

	public static Shape create(String wkt, Projection projection) throws KlabValidationException {
		Shape ret = new Shape();
		ret.parseWkt(wkt);
		ret.projection = projection;
		if (ret.shapeGeometry != null) {
			ret.envelope = Envelope.create(ret.shapeGeometry.getEnvelopeInternal(), ret.projection);
		}
		return ret;
	}

	public static Shape create(double x1, double y1, double x2, double y2, Projection projection) {
		Shape ret = new Shape();
		ret.shapeGeometry = makeCell(x1, y1, x2, y2);
		ret.projection = projection;
		ret.envelope = Envelope.create(ret.shapeGeometry.getEnvelopeInternal(), ret.projection);
		ret.type = IShape.Type.POLYGON;
		return ret;
	}

	public static Shape create(double x1, double y1, Projection projection) {
		Shape ret = new Shape();
		ret.shapeGeometry = makePoint(x1, y1);
		ret.projection = projection;
		ret.envelope = Envelope.create(ret.shapeGeometry.getEnvelopeInternal(), ret.projection);
		ret.type = IShape.Type.POINT;
		return ret;
	}

	public static Shape create(IEnvelope envelope) {
		return create(envelope.getMinX(), envelope.getMinY(), envelope.getMaxX(), envelope.getMaxY(),
				(Projection) envelope.getProjection());
	}

	public static Shape create(Geometry geometry, IProjection projection) {
		Shape ret = new Shape();
		ret.shapeGeometry = geometry;
		ret.projection = (Projection) projection;
		ret.envelope = Envelope.create(ret.shapeGeometry.getEnvelopeInternal(), ret.projection);
		return ret;
	}

	public static Shape create(Collection<Geometry> geometries, IProjection projection) {

		if (geometries.size() == 0) {
			return null;
		}

		if (geometries.size() == 1) {
			return create(geometries.iterator().next(), projection);
		}

		return create(Geospace.gFactory.createGeometryCollection(geometries.toArray(new Geometry[geometries.size()])),
				projection);
	}

	public static Geometry makeCell(double x1, double y1, double x2, double y2) {

		Coordinate[] pts = { new Coordinate(x1, y1), new Coordinate(x2, y1), new Coordinate(x2, y2),
				new Coordinate(x1, y2), new Coordinate(x1, y1) };

		return Geospace.gFactory.createPolygon(Geospace.gFactory.createLinearRing(pts), null);
	}

	public static Geometry makePoint(double x1, double y1) {
		Coordinate coordinate = new Coordinate(x1, y1);
		return Geospace.gFactory.createPoint(coordinate);
	}

	public static Geometry makePoint(IGrid.Cell cell) {
		double[] xy = cell.getCenter();
		return Geospace.gFactory.createPoint(new Coordinate(xy[0], xy[1]));
	}

	private Shape() {
	}

	protected Shape(Shape shape) {
		this.shapeGeometry = shape.shapeGeometry;
		this.geometry = shape.geometry;
		this.projection = shape.projection;
		this.envelope = shape.envelope;
		this.type = shape.type;
	}

	@Override
	public Projection getProjection() {
		return projection;
	}

	private Geometry getMeteredShape() {
		if (metered == null) {
			if (projection.isMeters()) {
				metered = shapeGeometry;
			} else {
				metered = transform(Projection.getUTM(getEnvelope())).getJTSGeometry();
			}
		}
		return metered;
	}

	@Override
	public IShape.Type getGeometryType() {
		if (type == null) {
			if (shapeGeometry instanceof Polygon) {
				type = IShape.Type.POLYGON;
			} else if (shapeGeometry instanceof MultiPolygon) {
				type = IShape.Type.MULTIPOLYGON;
			} else if (shapeGeometry instanceof Point) {
				type = IShape.Type.POINT;
			} else if (shapeGeometry instanceof MultiLineString) {
				type = IShape.Type.MULTILINESTRING;
			} else if (shapeGeometry instanceof LineString) {
				type = IShape.Type.LINESTRING;
			} else if (shapeGeometry instanceof MultiPoint) {
				type = IShape.Type.MULTIPOINT;
			}
		}
		return type;
	}

	public double getNativeArea() {
		return shapeGeometry.getArea();
	}

	@Override
	public double getArea(IUnit unit) {
		return unit.convert(getMeteredShape().getArea(), Units.INSTANCE.SQUARE_METERS).doubleValue();
	}

	public Shape getCentroid() {
		return create(shapeGeometry.getCentroid(), projection);
	}

	@Override
	public boolean isEmpty() {
		return shapeGeometry == null || shapeGeometry.isEmpty();
	}

	public Geometry getJTSGeometry() {
		return shapeGeometry;
	}

	@Override
	public Shape transform(IProjection otherProjection) throws KlabValidationException {

		if (this.projection.equals(otherProjection)) {
			return this;
		}
		Geometry g = null;

		try {
			g = JTS.transform(shapeGeometry, CRS.findMathTransform(projection.crs, ((Projection) otherProjection).crs));
		} catch (Exception e) {
			throw new KlabValidationException(e);
		}

		return Shape.create(g, (Projection) otherProjection);
	}

	@Override
	public Envelope getEnvelope() {
		return envelope;
	}

	@Override
	public Shape intersection(IShape other) {
		if ((projection != null || other.getProjection() != null) && !projection.equals(other.getProjection())) {
			try {
				other = other.transform(projection);
			} catch (KlabValidationException e) {
				return empty();
			}
		}
		return create(fix(shapeGeometry).intersection(fix(((Shape) other).shapeGeometry)), projection);
	}

	public Shape fixInvalid() {
		/*
		 * TODO use next-level JTS functions now available when we can upgrade
		 */
		Geometry geom = this.shapeGeometry.buffer(0);
		return create(geom, projection);
	}

	@Override
	public Shape union(IShape other) {
		if ((projection != null || other.getProjection() != null) && !projection.equals(other.getProjection())) {
			try {
				other = other.transform(projection);
			} catch (KlabValidationException e) {
				return empty();
			}
		}
		return create(fix(shapeGeometry).union(fix(((Shape) other).shapeGeometry)), projection);
	}

	public boolean containsCoordinates(double x, double y) {
		checkPreparedShape();
		return preparedShape == null ? shapeGeometry.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)))
				: preparedShape.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)));
	}

	private void checkPreparedShape() {
		if (this.preparedShape == null && !preparationAttempted) {
			preparationAttempted = true;
			try {
				this.preparedShape = PreparedGeometryFactory.prepare(shapeGeometry);
			} catch (Throwable t) {
			}
		}
	}

	public PreparedGeometry getPreparedGeometry() {
		checkPreparedShape();
		return preparedShape;
	}

	public double getCoverage(Cell cell, boolean simpleIntersection) {

		checkPreparedShape();
		if (preparedShape == null) {
			if (simpleIntersection) {
				Geometry gm = makePoint(cell);
				return gm.intersects(shapeGeometry) ? 1.0 : 0.0;
			}
			Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
			return gm.covers(shapeGeometry) ? 1.0 : (gm.intersection(shapeGeometry).getArea() / gm.getArea());
		}
		if (simpleIntersection) {
			return preparedShape.covers(makePoint(cell)) ? 1 : 0;
		}
		Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
		return preparedShape.covers(gm) ? 1.0 : (gm.intersection(shapeGeometry).getArea() / gm.getArea());
	}

	private void parseWkt(String s) throws KlabValidationException {

		String pcode = Projection.DEFAULT_PROJECTION_CODE;
		Geometry geometry = null;
		boolean wkt = false;
		/*
		 * first see if we start with a token that matches "EPSG:[0-9]*". If so, set the
		 * CRS from it; otherwise it is null (not the plugin default).
		 */
		if (s.startsWith("EPSG:") || s.startsWith("urn:")) {
			int n = s.indexOf(' ');
			pcode = s.substring(0, n);
			s = s.substring(n + 1);
		}
		try {
			if (s.contains("(")) {
				wkt = true;
				geometry = new WKTReader().read(s);
			} else {
				geometry = new WKBReader().read(WKBReader.hexToBytes(s));
			}
		} catch (ParseException e) {
			throw new KlabValidationException("error parsing " + (wkt ? "WKT" : "WBT") + ": " + e.getMessage());
		}

		this.projection = Projection.create(pcode);
		this.shapeGeometry = geometry;
	}

	public Geometry getStandardizedGeometry() {
		if (this.isEmpty() || this.projection.equals(Projection.getLatLon())) {
			return this.shapeGeometry;
		}
		Shape shape = this.transform(Projection.getLatLon());
		this.standardizedGeometry = shape.shapeGeometry;
		return this.standardizedGeometry;
	}

	@Override
	public IShape getShape() {
		return this;
	}

	@Override
	public int getScaleRank() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public IExtent collapse() {
		return copy();
	}

	@Override
	public ISpace mergeContext(IExtent extent) throws KlabException {
		if (extent instanceof ISpace) {
			return Space.createMergedExtent(this, (ISpace) extent);
		}
		throw new IllegalArgumentException("a Shape cannot merge an extent of type " + extent.getType());
	}

	@Override
	public long size() {
		return 1;
	}

	@Override
	public boolean contains(IExtent o) throws KlabException {
		if (this.equals(o)) {
			return true;
		}
		if (o instanceof ISpace) {
			IShape shp = ((ISpace) o).getShape();
			return this.getStandardizedGeometry().contains(((Shape) shp).getStandardizedGeometry());
		}
		return false;
	}

	@Override
	public boolean overlaps(IExtent o) throws KlabException {
		if (this.equals(o)) {
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean intersects(IExtent o) throws KlabException {
		if (this.equals(o)) {
			return true;
		}
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double getCoveredExtent() {
		return getStandardizedGeometry().getArea();
	}

	@Override
	public Iterator<ILocator> iterator() {
		return Collections.singleton((ILocator) this).iterator();
	}

	@Override
	public IGeometry.Dimension.Type getType() {
		return IGeometry.Dimension.Type.SPACE;
	}

	@Override
	public boolean isRegular() {
		return false;
	}

	@Override
	public int getDimensionality() {
		int ret = 0;
		if (Arrays.contains(shapeGeometry.getClass().getInterfaces(), Lineal.class)) {
			ret = 1;
		} else if (Arrays.contains(shapeGeometry.getClass().getInterfaces(), Polygonal.class)) {
			ret = 2;
		}
		return ret;
	}

	public ReferencedEnvelope getJTSEnvelope() {
		return new ReferencedEnvelope(shapeGeometry.getEnvelopeInternal(), projection.crs);
	}

	@Override
	public long[] shape() {
		if (getDimensionality() == 2) {
			return new long[] { 1, 1 };
		} else if (getDimensionality() == 1) {
			return new long[] { 1 };
		}
		return new long[] {};
	}

	@Override
	public IExtent getExtent() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((shapeGeometry == null) ? 0 : shapeGeometry.hashCode());
		result = prime * result + ((projection == null) ? 0 : projection.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {

		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Shape other = (Shape) obj;
		if (shapeGeometry == null) {
			if (other.shapeGeometry != null) {
				return false;
			}
		} else if (other.getGeometryType() != getGeometryType()) {
			return false;
		} else if ((isMultiGeometry() && !other.isMultiGeometry()) || (!isMultiGeometry() && other.isMultiGeometry())) {
			return false;
		} else if (!fix(shapeGeometry).equals(fix(other.shapeGeometry))) {
			return false;
		}
		if (projection == null) {
			if (other.projection != null) {
				return false;
			}
		} else if (!projection.equals(other.projection)) {
			return false;
		}
		return true;
	}

	private boolean isMultiGeometry() {
		return shapeGeometry instanceof GeometryCollection;
	}

	@Override
	public ISpace merge(ITopologicallyComparable<?> other, LogicalConnector how, MergingOption... options) {

		Shape shape = other instanceof Shape ? (Shape) other : null;
		if (shape == null && other instanceof ISpace) {
			shape = (Shape) ((ISpace) other).getShape();
		}
		if (shape == null) {
			return copy();
		}
		if (how == LogicalConnector.UNION) {
			return create(shapeGeometry.union(shape.transform(this.projection).getJTSGeometry()), this.projection);
		} else if (how == LogicalConnector.INTERSECTION) {
			return create(shapeGeometry.intersection(shape.transform(this.projection).getJTSGeometry()),
					this.projection);
		} else if (how == LogicalConnector.EXCLUSION) {
			return create(shapeGeometry.difference(shape.transform(this.projection).getJTSGeometry()), this.projection);
		}
		throw new IllegalArgumentException("cannot merge a shape with " + other);
	}

	@Override
	public IParameters<String> getParameters() {
		return baseDimension.getParameters();
	}

	/**
	 * WKB code without projection
	 * 
	 * @return the WKB code
	 */
	public String getWKB() {
		return WKBWriter.toHex(wkbWriter.write(shapeGeometry));
	}

	@Override
	public String encode(Encoding... options) {
		return "s2(1,1){shape=" + ((Shape) getShape()).getWKB() + "," + getEnvelope().encode() + ",proj="
				+ getProjection().getSimpleSRS() + "}";
	}

	@Override
	public IScaleMediator getMediator(IExtent extent) {
		ISpace other = (ISpace) extent;
		if (other instanceof Space && ((Space) other).getGrid() != null) {
			return new GridToShape(this, (Grid) ((Space) other).getGrid());
		} else if (other instanceof Space && ((Space) other).getTessellation() != null) {
			return new FeaturesToShape(this, ((Space) other).getTessellation());
		} else {
			return new ShapeToShape((Shape) other.getShape(), this);
		}
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
		return merge(other, connector);
	}

	@Override
	public Shape copy() {
		return create((Geometry) shapeGeometry.clone(), projection);
	}

	@Override
	public IServiceCall getKimSpecification() {
		List<Object> args = new ArrayList<>(2);
		args.add("shape");
		args.add(toString());
		return new KimServiceCall("space", args.toArray());
	}

	@Override
	public IShape getBoundingExtent() {
		return create(getEnvelope().asJTSGeometry() , projection);
	}

	@Override
	public Collection<IShape> getHoles() {
		List<IShape> ret = new ArrayList<>();
		if (shapeGeometry instanceof Polygonal) {
			// scan all polygons in multipolygon, one in polygon
			// add all interior rings in each as a new shape
		}
		return ret;
	}

	@Override
	public SpatialExtent getExtentDescriptor() {
		Envelope stdEnvelope = getEnvelope().transform(Projection.getLatLon(), true);
		SpatialExtent ret = new SpatialExtent();
		ret.setEast(stdEnvelope.getMaxX());
		ret.setWest(stdEnvelope.getMinX());
		ret.setSouth(stdEnvelope.getMinY());
		ret.setNorth(stdEnvelope.getMaxY());
		return ret.normalize();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends ILocator> T as(Class<T> cls) {
		return null; // (T) envelope.asLocator();
	}

	@Override
	public double getStandardizedVolume() {
		return Double.NaN;
	}

	@Override
	public double getStandardizedArea() {
		if (getGeometryType() == IShape.Type.POLYGON || getGeometryType() == IShape.Type.MULTIPOLYGON) {
			return getMeteredShape().getArea();
		}
		return 0;
	}

	@Override
	public double getStandardizedLength() {
		return getMeteredShape().getLength();
	}

	@Override
	public double getStandardizedWidth() {
		if (getGeometryType() == IShape.Type.POLYGON || getGeometryType() == IShape.Type.MULTIPOLYGON) {
			return getMeteredShape().getEnvelopeInternal().getWidth();
		}
		return 0;
	}

	@Override
	public double getStandardizedHeight() {
		if (getGeometryType() == IShape.Type.POLYGON || getGeometryType() == IShape.Type.MULTIPOLYGON) {
			return getMeteredShape().getEnvelopeInternal().getHeight();
		}
		return 0;
	}

	@Override
	public double getStandardizedDistance(ISpace space) {
		return getMeteredShape().distance(((Shape) space.getShape()).getMeteredShape());
	}

	@Override
	public double getStandardizedDepth() {
		return Double.NaN;
	}

	public void simplify(double simplifyFactor) {
		this.shapeGeometry = TopologyPreservingSimplifier.simplify(shapeGeometry, simplifyFactor);
		this.envelope = Envelope.create(this.shapeGeometry.getEnvelopeInternal(), this.projection);
	}

	public Shape getSimplified(IQuantity resolution) {
		if (this.simplified) {
			return this;
		}
		Unit unit = Unit.create(resolution.getUnit());
		if (unit == null || !Units.INSTANCE.METERS.isCompatible(unit)) {
			throw new KlabIllegalArgumentException("Can't use a non-length unit to simplify a shape");
		}
		// in m
		double simplifyFactor = Units.INSTANCE.METERS.convert(resolution.getValue(), unit).doubleValue();

		// convert to projection units. FIXME there's certainly a proper method that
		// doesn't require reprojection (ask Andrea)
		double proportion = simplifyFactor / getMeteredShape().getLength();
		simplifyFactor = shapeGeometry.getLength() * proportion;

		Geometry geom = TopologyPreservingSimplifier.simplify(shapeGeometry, simplifyFactor);
		Shape ret = create(geom, this.projection);
		ret.simplified = true;
		return ret;
	}

	public Shape getSimplified(double simplifyFactor) {
		if (this.simplified) {
			return this;
		}
		Geometry geom = TopologyPreservingSimplifier.simplify(shapeGeometry, simplifyFactor);
		Shape ret = create(geom, this.projection);
		ret.simplified = true;
		return ret;
	}

	public boolean containsPoint(double[] coordinates) {
		checkPreparedShape();
		Point point = shapeGeometry.getFactory().createPoint(new Coordinate(coordinates[0], coordinates[1]));
		return preparedShape != null ? preparedShape.contains(point) : shapeGeometry.contains(point);
	}

	@Override
	public IShape buffer(double distance) {
		Geometry geom = this.shapeGeometry.buffer(distance);
		return create(geom, projection);
	}

	@Override
	public Shape difference(IShape shape) {
		Geometry geom = fix(this.shapeGeometry).difference(fix(((Shape) shape).getJTSGeometry()));
		return create(geom, projection);
	}

	private Geometry fix(Geometry jtsGeometry) {
		// if geometry is a point or line, buffer return an empty polygon, so we must
		// check it
		// a double check for a well formed polygon is needed
		if (jtsGeometry instanceof GeometryCollection
				&& !(jtsGeometry instanceof MultiLineString || jtsGeometry instanceof LineString
						|| jtsGeometry instanceof MultiPoint || jtsGeometry instanceof Point)) {
			return jtsGeometry.buffer(0);
		}
		return jtsGeometry;
	}

	/**
	 * If the number of coordinates is higher than a passed threshold, simplify to
	 * the distance that retains the max number of subdivisions along the diagonal
	 * of the envelope.
	 * 
	 * @param maxCoordinates
	 * @param nDivisions
	 * @return
	 */
	public Shape simplifyIfNecessary(int maxCoordinates, int nDivisions) {
		if (shapeGeometry.getNumPoints() > maxCoordinates) {
			double distance = Math.sqrt(Math.pow(getEnvelope().getWidth(), 2) + Math.pow(getEnvelope().getHeight(), 2))
					/ (double) nDivisions;
			return getSimplified(distance);
		}
		return this;
	}

	/**
	 * Join the passed shapes into another shape of the passed type, optionally
	 * including or excluding the shapes themselves.
	 * 
	 * Assumes both shapes have the same projection.
	 * 
	 * @param a
	 * @param b
	 * @param type
	 * @param includeSources
	 * @return a new shape
	 */
	public static Shape join(IShape a, IShape b, IShape.Type type, boolean includeSources) {

		Geometry aj = ((Shape) a).getStandardizedGeometry();
		Geometry bj = ((Shape) b).getStandardizedGeometry();
		Geometry merged = null;

		switch (type) {
		case LINESTRING:
			merged = aj.getFactory().createLineString(
					new Coordinate[] { aj.getCentroid().getCoordinates()[0], bj.getCentroid().getCoordinates()[0] });
			break;
		case POLYGON:
		case MULTIPOLYGON:
			List<Coordinate> cloud = new ArrayList<>();
			for (Coordinate c : aj.getBoundary().getCoordinates()) {
				cloud.add(c);
			}
			for (Coordinate c : bj.getBoundary().getCoordinates()) {
				cloud.add(c);
			}
			ConvexHull hull = new ConvexHull(cloud.toArray(new Coordinate[0]), aj.getFactory());
			merged = hull.getConvexHull().buffer(0);
			break;
		default:
			throw new IllegalArgumentException("cannot join two shapes into a " + type.name().toLowerCase());
		}

		if (merged != null && !includeSources) {
			merged = merged.difference(aj);
			merged = merged.difference(bj);
		}

		return merged == null ? null : Shape.create(merged, a.getProjection());
	}

	@Override
	public double[] getCenter(boolean standardized) {
		Point centroid = standardized ? getStandardizedGeometry().getCentroid() : shapeGeometry.getCentroid();
		return new double[] { centroid.getCoordinate().x, centroid.getCoordinate().y };
	}

	@Override
	public boolean isGeneric() {
		return false;
	}

	public void show() {
		JFrame f = new JFrame();
		f.getContentPane().add(new Paint());
		f.setSize(700, 700);
		f.setVisible(true);
	}

	class Paint extends JPanel {

		private static final long serialVersionUID = 7826836352532417280L;

		public void paint(Graphics g) {
			ShapeWriter sw = new ShapeWriter();
			g.setColor(Color.RED);
			java.awt.Shape polyShape = sw.toShape(shapeGeometry);
			((Graphics2D) g).draw(polyShape);
		}
	}

	@Override
	public ExtentDimension getExtentDimension() {
		return ExtentDimension.AREAL;
	}

	@Override
	public Pair<Double, IUnit> getStandardizedDimension(ILocator locator) {
		// TODO ignoring the locator: should check and throw exceptions
		return new Pair<>(getStandardizedArea(), Units.INSTANCE.SQUARE_METERS);
	}

	@Override
	public IGeometry getGeometry() {
		return geometry;
	}

	@Override
	public long getOffset(long... offsets) {
		return 0;
	}

	@Override
	public IExtent at(Object... locators) {
		if (locators != null && locators.length == 1) {
			if (locators[0] instanceof Number && ((Number) locators[0]).longValue() == 0) {
				return this;
			}
			if (locators[0] instanceof Cell) {
				if (getEnvelope().intersects(((Cell) locators[0]).getEnvelope())) {
					// TODO coverage
					return this;
				}
				return null;
			} else if (locators[0] instanceof IShape) {
				if (getEnvelope().intersects(((Shape) locators[0]).getEnvelope())) {
					// TODO coverage
					return this;
				}
				return null;
			}
		}
		throw new IllegalStateException("an individual shape cannot be further located");
	}

	@Override
	public double[] getStandardizedCentroid() {
		return getCenter(true);
	}

	@Override
	public boolean isCovered(long stateIndex) {
		return true; // stateIndex == 0;
	}

//    @Override
//    public IExtent adopt(IExtent extent, IMonitor monitor) {
//        // TODO Auto-generated method stub
//        return this;
//    }

	@Override
	public ISpace getExtent(long stateIndex) {
		if (stateIndex != 0) {
			throw new IllegalArgumentException("cannot access state #" + stateIndex + " in a Shape");
		}
		return this;
	}

	@Override
	protected IExtent contextualizeTo(IExtent other, IAnnotation constraint) {

		if (constraint != null && constraint.size() == 0) {
			return this;
		}
		if (this.getDimensionality() < 2) {
			// a point remains a point, a line remains a line. Maybe later we can buffer
			// using an annotation and apply the rules.
			return this;
		}

		/*
		 * dimensionality >= 2; if we have a grid, apply it. TODO this will need to be
		 * revised to apply a 2D grid to 2D extents matched with a 3D grid, which we
		 * don't have for now.
		 */
		Grid grid = null;
		if (other instanceof Space) {
			grid = (Grid) ((Space) other).getGrid();
		}

		if (grid != null && grid.isConsistent()) {
			return Space.create(this, grid, true);
		}

		return this;
	}

	@Override
	public boolean contains(double[] coordinate) {
		return this.shapeGeometry.intersects(makePoint(coordinate[0], coordinate[1]));
	}

	@Override
	public IMetadata getMetadata() {
		if (this.metadata == null) {
			this.metadata = new Metadata();
		}
		return this.metadata;
	}

	@Override
	public double getDimensionSize(IUnit unit) {
		return unit.convert(getStandardizedArea(), Units.INSTANCE.SQUARE_METERS).doubleValue();
	}

	@Override
	public boolean isDistributed() {
		return false;
	}

	/**
	 * Returned shape is guaranteed to be a polygon
	 * 
	 * @return
	 */
	public String getStandardizedEnvelopeWKT() {
		return ((Shape) getBoundingExtent().transform(Projection.getLatLon())).shapeGeometry.toString();
	}

	@Override
	public double getComplexity() {
		// TODO improve
		return shapeGeometry.getNumPoints();
	}
}
