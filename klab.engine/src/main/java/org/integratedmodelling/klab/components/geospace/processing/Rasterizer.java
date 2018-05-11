package org.integratedmodelling.klab.components.geospace.processing;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.integratedmodelling.klab.api.observations.scale.space.IShape;
import org.integratedmodelling.klab.components.geospace.api.IGrid;

public class Rasterizer {

	/**
	 * 
	 * @param grid The grid we rasterize to
	 * @param encoder a function that takes a shape and returns its value for the grid
	 * @param setter a consumer that gets each value from the rasterizer and the grid offset to put it in
	 */
	public Rasterizer(IGrid grid, Function<IShape,Object> encoder, BiConsumer<Object, Long> setter) {
		
	}
	
	public void add(IShape shape) {
		
	}
	
	public void finish() {
		
	}
	
}
