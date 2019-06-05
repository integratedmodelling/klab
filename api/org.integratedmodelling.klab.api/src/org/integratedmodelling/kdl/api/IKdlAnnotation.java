package org.integratedmodelling.kdl.api;

import org.integratedmodelling.kim.api.IParameters;

/**
 * Annotations for KDL specifications
 * 
 * @author ferdinando.villa
 *
 */
public interface IKdlAnnotation extends IParameters<String> {
	
	public static final String DEFAULT_PARAMETER_NAME = "value";
	
	String getName();
}
