package org.integratedmodelling.klab.components.geospace.processing;

import org.integratedmodelling.klab.api.observations.scale.space.IGrid;

import com.vividsolutions.jts.geom.GeometryFactory;

/**
 * 
 */
public class Viewshed<T> {

	private IGrid extent;
	private GeometryFactory geoFactory = new GeometryFactory();

	/**
	 * 
	 * @param grid
	 *            The grid we rasterize to
	 * @param encoder
	 * @param setter
	 */
	public Viewshed(IGrid grid) {
		this.extent = grid;
	}

}
