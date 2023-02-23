package org.integratedmodelling.klab.api.knowledge.observation.scale;

/**
 * Type of utilization of a specific extent in an observation.
 * @author ferdinando.villa
 *
 */
public enum ExtentDistribution {
	
	/**
	 * If extensive over an extent, an observation yields aggregated states in it (e.g. kilograms
	 * of carbon mass per cell) which will depend on the extent.
	 */
	EXTENSIVE,
	/**
	 * If intensive over an extent, an observation yields states that are distributed over a unit
	 * of that extent (e.g. a cell of arbitrary size will yield kg/m^2) and won't depend on the
	 * extent.
	 */
	INTENSIVE
	
}
