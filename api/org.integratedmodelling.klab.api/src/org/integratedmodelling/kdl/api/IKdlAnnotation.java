package org.integratedmodelling.kdl.api;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.api.IAnnotationStatement;

/**
 * Annotations for KDL specifications
 * 
 * @author ferdinando.villa
 * @deprecated use {@link IAnnotationStatement}
 *
 */
public interface IKdlAnnotation extends IParameters<String> {
	
	public static final String DEFAULT_PARAMETER_NAME = "value";
	
	String getName();
}
