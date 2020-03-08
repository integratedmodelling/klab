package org.integratedmodelling.kactors.api;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.IAnnotationStatement;

/**
 * Annotations for KDL specifications
 * 
 * @author ferdinando.villa
 * @deprecated use {@link IAnnotationStatement}
 *
 */
public interface IKActorsAnnotation extends IParameters<String> {
	
	public static final String DEFAULT_PARAMETER_NAME = "value";
	
	String getName();
}
