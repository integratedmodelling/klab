package org.integratedmodelling.klab.components.geospace.processing;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.api.IGrid;

/**
 * Flexible rasterizer using an AWT image as a backend and closures to determine
 * (a) the mapping between each shape and the rasterized value, and (b) the
 * final output of that value on an arbitrary, indexable collection.
 * <p>
 * The image behind the scenes encodes floats values as RGB values, so the resolution
 * of a float (or a long for categorical values) limits the precision of the value
 * retained in the final product.
 * 
 * @author ferdinando.villa
 * @author steve.ansari, NOAA (original RGB-encoding and rasterization logics)
 * 
 * @param <T> the type of the value rasterized
 */
public class Rasterizer<T> {

	BufferedImage raster = null;
	HashMap<T, Long> objectIds;

	/**
	 * 
	 * @param grid
	 *            The grid we rasterize to
	 * @param encoder
	 * @param setter
	 */
	public Rasterizer(IGrid grid) {
		this.raster = new BufferedImage((int)grid.getXCells(), (int)grid.getYCells(), BufferedImage.TYPE_INT_ARGB);
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
	 */
	public void add(IShape shape, Function<IShape, T> encoder) {

	}

	/**
	 * Produce the final output in an arbitrary collection that exposes a method to set value V at [x, y].
	 * <p>
	 * Example:
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
	 * @param setter the method that gets each value from the rasterizer and the (x,y)
	 *         grid offsets to put it in
	 * @return the same object passed as collector
	 */
	public void finish(BiConsumer<T, int[]> setter) {
		for (int x = 0; x < this.raster.getWidth(); x++) {
			for (int y = 0; y < this.raster.getHeight(); y++) {
//				setter.accept(decode(raster.ge), u);
			}
		}
	}
	

}
