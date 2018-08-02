package org.integratedmodelling.klab.api.resources;

import org.integratedmodelling.klab.rest.ResourceReference;

/**
 * Resource operations that can work simply with the {@link ResourceReference
 * syntactic peer} for a resource.
 * 
 * @author ferdinando.villa
 *
 */
public class ResourceUtils {

	/**
	 * If the resource has a geometry that includes space, extract the bounding box
	 * as a shape specification usable in k.LAB.
	 * 
	 * @return the shape specifications, or null if the resource has no space
	 */
	public String extractShapeSpecification(ResourceReference resource) {
		return null;
	}
}
