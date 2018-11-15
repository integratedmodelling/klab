package org.integratedmodelling.klab.components.geospace.extents;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.xtext.util.Arrays;
import org.geotools.geometry.jts.JTS;
import org.geotools.geometry.jts.ReferencedEnvelope;
import org.geotools.referencing.CRS;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Units;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.mediation.IUnit;
import org.integratedmodelling.klab.api.observations.scale.IExtent;
import org.integratedmodelling.klab.api.observations.scale.IScaleMediator;
import org.integratedmodelling.klab.api.observations.scale.ITopologicallyComparable;
import org.integratedmodelling.klab.api.observations.scale.space.IProjection;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.api.observations.scale.space.ISpace;
import org.integratedmodelling.klab.common.LogicalConnector;
import org.integratedmodelling.klab.components.geospace.Geospace;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.api.IGrid.Cell;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToFeatures;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToGrid;
import org.integratedmodelling.klab.components.geospace.extents.mediators.ShapeToShape;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.SpatialExtent;
import org.integratedmodelling.klab.scale.AbstractExtent;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Lineal;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;
import com.vividsolutions.jts.geom.Polygonal;
import com.vividsolutions.jts.geom.prep.PreparedGeometry;
import com.vividsolutions.jts.geom.prep.PreparedGeometryFactory;
import com.vividsolutions.jts.io.ParseException;
import com.vividsolutions.jts.io.WKBReader;
import com.vividsolutions.jts.io.WKBWriter;
import com.vividsolutions.jts.io.WKTReader;
import com.vividsolutions.jts.simplify.TopologyPreservingSimplifier;

public class Shape extends AbstractExtent implements IShape {

	private static WKBWriter wkbWriter = new WKBWriter();

	Geometry geometry;
	// the geometry in WGS84, only cached if asked for and originally not in it.
	Geometry standardizedGeometry;
	Envelope envelope;
	IShape.Type type = null;
	Projection projection;

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
		return projection.getSimpleSRS() + " " + geometry;
	}

	public static Shape create(String wkt) throws KlabValidationException {
		Shape ret = new Shape();
		ret.parseWkt(wkt);
		if (ret.geometry != null) {
			ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
		}
		return ret;
	}

	public static Shape create(double x1, double y1, double x2, double y2, Projection projection) {
		Shape ret = new Shape();
		ret.geometry = makeCell(x1, y1, x2, y2);
		ret.projection = projection;
		ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
		ret.type = IShape.Type.POLYGON;
		return ret;
	}
	
	public static Shape create(double x1, double y1, Projection projection) {
		Shape ret = new Shape();
		ret.geometry = makePoint(x1, y1);
		ret.projection = projection;
		ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
		ret.type = IShape.Type.POINT;
		return ret;
	}

	public static Shape create(Envelope envelope) {
		return create(envelope.getMinX(), envelope.getMinY(), envelope.getMaxX(), envelope.getMaxY(),
				(Projection) envelope.getProjection());
	}

	public static Shape create(Geometry geometry, IProjection projection) {
		Shape ret = new Shape();
		ret.geometry = geometry;
		ret.projection = (Projection) projection;
		ret.envelope = Envelope.create(ret.geometry.getEnvelopeInternal(), ret.projection);
		return ret;
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

	@Override
	public Projection getProjection() {
		return projection;
	}

	private Geometry getMeteredShape() {
		if (metered == null) {
			if (projection.isMeters()) {
				metered = geometry;
			} else {
				metered = transform(Projection.getUTM(getEnvelope())).getJTSGeometry();
			}
		}
		return metered;
	}

	@Override
	public IShape.Type getGeometryType() {
		if (type == null) {
			if (geometry instanceof Polygon) {
				type = IShape.Type.POLYGON;
			} else if (geometry instanceof MultiPolygon) {
				type = IShape.Type.MULTIPOLYGON;
			} else if (geometry instanceof Point) {
				type = IShape.Type.POINT;
			} else if (geometry instanceof MultiLineString) {
				type = IShape.Type.MULTILINESTRING;
			} else if (geometry instanceof LineString) {
				type = IShape.Type.LINESTRING;
			} else if (geometry instanceof MultiPoint) {
				type = IShape.Type.MULTIPOINT;
			}
		}
		return type;
	}

	public double getNativeArea() {
		return geometry.getArea();
	}

	@Override
	public double getArea(IUnit unit) {
		return unit.convert(getMeteredShape().getArea(), Units.INSTANCE.SQUARE_METERS).doubleValue();
	}

	public Shape getCentroid() {
		return create(geometry.getCentroid(), projection);
	}

	@Override
	public boolean isEmpty() {
		return geometry == null || geometry.isEmpty();
	}

	public Geometry getJTSGeometry() {
		return geometry;
	}

	@Override
	public Shape transform(IProjection otherProjection) throws KlabValidationException {

		if (this.projection.equals(otherProjection)) {
			return this;
		}
		Geometry g = null;

		try {
			g = JTS.transform(geometry, CRS.findMathTransform(projection.crs, ((Projection) otherProjection).crs));
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
		return create(geometry.intersection(((Shape) other).geometry), projection);
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
		return create(geometry.union(((Shape) other).geometry), projection);
	}

	public boolean containsCoordinates(double x, double y) {
		checkPreparedShape();
		return preparedShape == null ? geometry.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)))
				: preparedShape.contains(Geospace.gFactory.createPoint(new Coordinate(x, y)));
	}

	private void checkPreparedShape() {
		if (this.preparedShape == null && !preparationAttempted) {
			preparationAttempted = true;
			try {
				this.preparedShape = PreparedGeometryFactory.prepare(geometry);
			} catch (Throwable t) {
				//
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
				return gm.intersects(geometry) ? 1.0 : 0.0;
			}
			Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
			return gm.covers(geometry) ? 1.0 : (gm.intersection(geometry).getArea() / gm.getArea());
		}
		if (simpleIntersection) {
			return preparedShape.covers(makePoint(cell)) ? 1 : 0;
		}
		Geometry gm = makeCell(cell.getEast(), cell.getSouth(), cell.getWest(), cell.getNorth());
		return preparedShape.covers(gm) ? 1.0 : (gm.intersection(geometry).getArea() / gm.getArea());
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
		this.geometry = geometry;
	}

	public Geometry getStandardizedGeometry() {
		if (this.isEmpty() || this.projection.equals(Projection.getLatLon())) {
			return this.geometry;
		}
		Shape shape = this.transform(Projection.getLatLon());
		this.standardizedGeometry = shape.geometry;
		return this.standardizedGeometry;
	}

	@Override
	public IShape getShape() {
		return this;
	}

	@Override
	public ISpace at(ILocator locator) {
		// TODO Auto-generated method stub
		return null;
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
	public IExtent merge(IExtent extent) throws KlabException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getCoverage() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public long size() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean contains(IExtent o) throws KlabException {
		if (this.equals(o)) {
			return true;
		}
		// TODO Auto-generated method stub
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
	public Iterator<IExtent> iterator() {
		return Collections.singleton((IExtent) this).iterator();
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
		if (Arrays.contains(geometry.getClass().getInterfaces(), Lineal.class)) {
			ret = 1;
		} else if (Arrays.contains(geometry.getClass().getInterfaces(), Polygonal.class)) {
			ret = 2;
		}
		return ret;
	}

	@Override
	public long[] getDimensionOffsets(long linearOffset) {
		if (linearOffset != 0) {
			throw new IllegalArgumentException("shape extents don't use offset addressing");
		}
		return new long[] { 0 };
	}

	@Override
	public long getOffset(long[] dimOffsets) {
		if (dimOffsets.length != 1 && dimOffsets[0] != 0) {
			throw new IllegalArgumentException("shape extents don't use offset addressing");
		}
		return 0;
	}

	public ReferencedEnvelope getJTSEnvelope() {
		return new ReferencedEnvelope(geometry.getEnvelopeInternal(), projection.crs);
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
	public long getOffset(ILocator index) {
		// TODO may use a latlon where the point is included.
		if (index instanceof Shape && this.equals(index)) {
			return 0;
		}
		throw new IllegalArgumentException("cannot use " + index + " as a shape locator");
	}

	@Override
	public IExtent getExtent() {
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((geometry == null) ? 0 : geometry.hashCode());
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
		if (geometry == null) {
			if (other.geometry != null) {
				return false;
			}
		} else if (!geometry.equals(other.geometry)) {
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

	@Override
	public IExtent merge(ITopologicallyComparable<?> other, LogicalConnector how) {

		Shape shape = other instanceof Shape ? (Shape) other : null;
		if (shape == null && other instanceof ISpace) {
			shape = (Shape) ((ISpace) other).getShape();
		}
		if (how == LogicalConnector.UNION) {
			return create(geometry.union(shape.transform(this.projection).getJTSGeometry()), this.projection);
		} else if (how == LogicalConnector.INTERSECTION) {
			return create(geometry.intersection(shape.transform(this.projection).getJTSGeometry()), this.projection);
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
		return WKBWriter.toHex(wkbWriter.write(geometry));
	}

	@Override
	public String encode() {
		return "s2(1,1){shape=" + ((Shape) getShape()).getWKB() + "," + getEnvelope().encode() + ",proj="
				+ getProjection().getSimpleSRS() + "}";
	}

	@Override
	public IScaleMediator getMediator(IExtent extent) {
		ISpace other = (ISpace) extent;
		if (other instanceof Space && ((Space) other).getGrid() != null) {
			return new ShapeToGrid(this, (Grid) ((Space) other).getGrid());
		} else if (other instanceof Space && ((Space) other).getTessellation() != null) {
			return new ShapeToFeatures(this, ((Space) other).getTessellation());
		} else {
			return new ShapeToShape(this, (Shape) other.getShape());
		}
	}

	@Override
	public IExtent mergeCoverage(IExtent other, LogicalConnector connector) {
		return merge(other, connector);
	}

	@Override
	public AbstractExtent copy() {
		return create(geometry, projection);
	}

	@Override
	public IServiceCall getKimSpecification() {
		List<Object> args = new ArrayList<>(2);
		args.add("shape");
		args.add(toString());
		return new KimServiceCall("space", args.toArray());
	}

	@Override
	public IShape getBoundary() {
		return create(geometry.getBoundary(), projection);
	}

	@Override
	public Collection<IShape> getHoles() {
		List<IShape> ret = new ArrayList<>();
		if (geometry instanceof Polygonal) {
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
		return (T) envelope.asLocator();
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
	public double getStandardizedDepth() {
		return Double.NaN;
	}

	public void simplify(double simplifyFactor) {
		this.geometry = TopologyPreservingSimplifier.simplify(geometry, simplifyFactor);
		this.envelope = Envelope.create(this.geometry.getEnvelopeInternal(), this.projection);
	}

}
