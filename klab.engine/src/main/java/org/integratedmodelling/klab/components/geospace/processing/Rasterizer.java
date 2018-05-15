package org.integratedmodelling.klab.components.geospace.processing;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.api.IGrid;
import org.integratedmodelling.klab.components.geospace.extents.Grid;
import org.integratedmodelling.klab.components.geospace.extents.Shape;
import org.integratedmodelling.klab.utils.Utils;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.LinearRing;
import com.vividsolutions.jts.geom.MultiLineString;
import com.vividsolutions.jts.geom.MultiPoint;
import com.vividsolutions.jts.geom.MultiPolygon;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.Polygon;

/**
 * Simple, flexible rasterizer using an AWT image as a backend and closures to
 * determine (a) the mapping between each shape and the rasterized value, and
 * (b) the final output of that value to an arbitrary x/y indexable collection.
 * The rasterized type can be any number type, boolean, or anything that can
 * be used as key in a map and won't have more than {@link Integer#MAX_VALUE}
 * different keys.
 * <p>
 * The image behind the scenes encodes floats values as RGB values, so the
 * resolution of a float (or a long for categorical values) limits the precision
 * of the value retained in the final product. Changing to doubles is easy if
 * needed.
 * <p>
 * Simple example with presence/absence rasterization:
 * 
 * <pre>
 * Rasterizer&lt;Boolean&gt; rasterizer = new Rasterizer<>(grid);
 * for (IShape shape : shapes) {
 * 	// use anything different from boolean and a more complex function for shapes
 * 	// with attributes
 * 	rasterizer.add(shape, (shape) -> true);
 * }
 * 
 * // paint the shapes over a boolean grid
 * final boolean[][] mask = new boolean[grid.getXCells()][grid.getYCells()];
 * rasterizer.finish((b, xy) -> mask[xy[0]][xy[1]] = b);
 * </pre>
 * 
 * <p>
 * Different shapes can use different encoders if wished. Yet, once the first
 * non-null value has been set, any subsequent non-null values must be of a
 * compatible type, so for example declaring a Rasterizer&lt;Number&gt and
 * assigning a Long for the first shape will require that all subsequent values
 * are Long.
 * 
 * @author ferdinando.villa
 * @author Steve Ansari, NOAA (original RGB-encoding and rasterization logics)
 * 
 * @param <T>
 *            the type of the value rasterized
 */
public class Rasterizer<T> {

	private BufferedImage raster = null;
	private IGrid extent;
	private Class<?> valueClass = null;

	private HashMap<T, Long> objectToId;
	private HashMap<Long, T> idToObject;
	private long maxId = 0;

	private Graphics2D graphics;
	private GeometryFactory geoFactory = new GeometryFactory();

	/**
	 * 
	 * @param grid
	 *            The grid we rasterize to
	 * @param encoder
	 * @param setter
	 */
	public Rasterizer(IGrid grid) {
		this.extent = grid;
		this.raster = new BufferedImage((int) grid.getXCells(), (int) grid.getYCells(), BufferedImage.TYPE_INT_ARGB);
		this.raster.setAccelerationPriority(1.0f);
		this.graphics = this.raster.createGraphics();
		this.graphics.setPaintMode();
		graphics.setComposite(AlphaComposite.Src);
	}

	/**
	 * Rasterize a single shape. Call as many times as necessary.
	 * 
	 * @param shape
	 *            the shape we want to rasterize. No check is made that the
	 *            projection is the same as the original grid, so ensure this is
	 *            called properly.
	 * @param encoder
	 *            a function that takes a shape and returns its value for the grid
	 * @throw IllegalArgumentException if the encoder returns a value of a class
	 *        that is incompatible with a previously used one.
	 */
	public void add(IShape shape, Function<IShape, T> encoder) {

		T value = encoder.apply(shape);
		Geometry geometry = ((Shape) shape).getJTSGeometry();

		if (geometry.intersects(((Grid) extent).getShape().getJTSGeometry())) {

			if (geometry.getClass().equals(MultiPolygon.class) || geometry.getClass().equals(Polygon.class)) {

				for (int i = 0; i < geometry.getNumGeometries(); i++) {
					Polygon poly = (Polygon) geometry.getGeometryN(i);
					LinearRing lr = geoFactory.createLinearRing(poly.getExteriorRing().getCoordinates());
					Polygon part = geoFactory.createPolygon(lr, null);
					drawGeometry(part, false, value);
					for (int j = 0; j < poly.getNumInteriorRing(); j++) {
						lr = geoFactory.createLinearRing(poly.getInteriorRingN(j).getCoordinates());
						part = geoFactory.createPolygon(lr, null);
						drawGeometry(part, true, value);
					}
				}
			} else if (geometry.getClass().equals(MultiLineString.class)) {
				MultiLineString mp = (MultiLineString) geometry;
				for (int n = 0; n < mp.getNumGeometries(); n++) {
					drawGeometry(mp.getGeometryN(n), false, value);
				}
			} else if (geometry.getClass().equals(MultiPoint.class)) {
				MultiPoint mp = (MultiPoint) geometry;
				for (int n = 0; n < mp.getNumGeometries(); n++) {
					drawGeometry(mp.getGeometryN(n), false, value);
				}
			} else {
				drawGeometry(geometry, false, value);
			}
		}
	}

	/**
	 * Produce the final output in an arbitrary collection that exposes a method to
	 * set value V at [x, y].
	 * <p>
	 * Example:
	 * 
	 * <pre>
	 * class Collection {
	 *
	 *   void set(T value, int x, int y) {
	 * 	  // set value at x = xy[0], y = xy[1]
	 *   }
	 * }
	 * 
	 * Rasterizer rasterizer = new Rasterizer(...);
	 * 
	 * //... add shapes
	 * 
	 * // produce the final collection
	 * final Collector collector = new Collector();
	 * rasterizer.finish((value, xy) -> collector.set(value, xy[0], xy[1]);
	 * </pre>
	 * 
	 * The value passed for nodata is null; boolean encoders will have false where
	 * no object data are found.
	 * 
	 * @param setter
	 *            the method that gets each value from the rasterizer and the (x,y)
	 *            grid offsets to put it in
	 * @return the same object passed as collector
	 */
	public void finish(BiConsumer<T, int[]> setter) {

		int[] xy = new int[2];
		for (int x = 0; x < this.raster.getWidth(); x++) {
			for (int y = 0; y < this.raster.getHeight(); y++) {
				xy[0] = x;
				xy[1] = y;
				setter.accept(decodeFloat(Float.intBitsToFloat(raster.getRGB(x, y))), xy);
			}
		}
	}

	// ------------------------------------------------------------------
	// internals
	// ------------------------------------------------------------------

	private T decodeFloat(float fval) {
		T ret = null;
		Object val = fval;
		if (this.valueClass != null && !Float.isNaN(fval)) {
			if (idToObject != null) {
				val = idToObject.get((long) fval);
			}
			ret = Utils.asType(val, valueClass);
		}
		return ret;
	}

	float encodeToFloat(T value) {
		if (value != null) {
			if (this.valueClass == null) {
				this.valueClass = value.getClass();
			} else if (!this.valueClass.isAssignableFrom(value.getClass())) {
				throw new IllegalArgumentException(
						"cannot use value " + value + " in rasterization: incompatible values were assigned earlier");
			}

			if (value instanceof Number) {
				return ((Number) value).floatValue();
			} else if (value instanceof Boolean) {
				return ((Boolean) value) ? 1 : 0;
			} else {

				if (idToObject == null) {
					idToObject = new HashMap<>();
					objectToId = new HashMap<>();
				}

				Long id = objectToId.get(value);
				if (id == null) {
					id = maxId++;
					objectToId.put(value, id);
					idToObject.put(id, value);
				}

				return id.floatValue();

			}
		}
		return Float.NaN;
	}

	private int[] coordGridX = new int[3500];
	private int[] coordGridY = new int[3500];

	private void drawGeometry(Geometry geometry, boolean isHole, T value) {

		Coordinate[] coords = geometry.getCoordinates();

		int rgbVal = floatBitsToInt(isHole ? Float.NaN : encodeToFloat(value));
		graphics.setColor(new Color(rgbVal, true));

		// enlarge if needed
		if (coords.length > coordGridX.length) {
			coordGridX = new int[coords.length];
			coordGridY = new int[coords.length];
		}
		for (int n = 0; n < coords.length; n++) {
			coordGridX[n] = (int) (((coords[n].x - extent.getWest()) / extent.getCellWidth()));
			coordGridY[n] = this.raster.getHeight()
					- (int) (((coords[n].y - extent.getSouth()) / extent.getCellHeight()));
		}

		if (geometry.getClass().equals(Polygon.class)) {
			graphics.fillPolygon(coordGridX, coordGridY, coords.length);
		} else if (geometry.getClass().equals(LinearRing.class)) {
			graphics.drawPolyline(coordGridX, coordGridY, coords.length);
		} else if (geometry.getClass().equals(LineString.class)) {
			graphics.drawPolyline(coordGridX, coordGridY, coords.length);
		} else if (geometry.getClass().equals(Point.class)) {
			graphics.drawLine(coordGridX[0], coordGridY[0], coordGridX[0], coordGridY[0]);
		}
	}

	private int floatBitsToInt(float f) {
		ByteBuffer conv = ByteBuffer.allocate(4);
		conv.putFloat(0, f);
		return conv.getInt(0);
	}

}
