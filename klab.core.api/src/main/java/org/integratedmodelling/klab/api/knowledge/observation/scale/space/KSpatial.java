package org.integratedmodelling.klab.api.knowledge.observation.scale.space;

/**
 * Anything that is spatial has a shape.
 * 
 * @author ferdinando.villa
 *
 */
public interface KSpatial {
	
	/**
	 * Get the shape.
	 * 
	 * @return
	 */
	KShape getGeometricShape();
	
}
