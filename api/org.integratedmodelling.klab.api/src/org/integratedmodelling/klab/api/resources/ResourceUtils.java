package org.integratedmodelling.klab.api.resources;

import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension.Type;
import org.integratedmodelling.klab.common.Geometry;
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
	public static String extractShapeSpecification(ResourceReference resource) {
		
		Geometry geometry = Geometry.create(resource.getGeometry());
		Dimension space = geometry.getDimension(Type.SPACE);
		if (space != null) {
			double[] bbox = space.getParameters().get(Geometry.PARAMETER_SPACE_BOUNDINGBOX, double[].class);
			String projection = space.getParameters().get(Geometry.PARAMETER_SPACE_PROJECTION, String.class);
			if (bbox != null && projection != null) {				
				String ret = projection + " POLYGON(("
					+ bbox[0] + " " + bbox[1] + ", "
					+ bbox[2] + " " + bbox[1] + ", "
					+ bbox[2] + " " + bbox[3] + ", "
					+ bbox[0] + " " + bbox[3] + ", "
					+ bbox[0] + " " + bbox[1] + "))";
				
				return "\"" + ret + "\"";
			}
		}
		return null;
	}
}
