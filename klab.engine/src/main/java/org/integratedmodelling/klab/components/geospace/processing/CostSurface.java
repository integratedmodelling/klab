package org.integratedmodelling.klab.components.geospace.processing;

import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.api.IGrid;

import com.vividsolutions.jts.geom.Coordinate;

/**
 * 
 */
public class CostSurface {

	private IGrid extent;
	private IState cost;
	private Rasterizer<Boolean> rasterizer;
	Set<Coordinate> startingPoints = new HashSet<>();

	/**
	 * 
	 * @param grid
	 *            The grid we rasterize to
	 * @param encoder
	 * @param setter
	 */
	public CostSurface(IGrid grid) {
		this.extent = grid;
	}

	public CostSurface withCost(IState state) {
		this.cost = state;
		return this;
	}
	
	public CostSurface withStart(IShape shape) {
		startingPoints.addAll(rasterizer.getCoordinates(shape.getBoundingExtent()));
		for (IShape hole : shape.getHoles()) {
			startingPoints.addAll(rasterizer.getCoordinates(hole.getBoundingExtent()));
		}
		rasterizer.add(shape, null);
		return this;
	}
	
	public void finish(IState surface) {
		
	}
	
}
