package org.integratedmodelling.klab.api.model;

import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IParameters;

/**
 * Annotation from k.IM code. Exposed as a {@link IParameters} object with a
 * name. Does not preserve the relationship with the {@link IKimAnnotation
 * statement} after construction.
 * 
 * @author Ferd
 *
 */
public interface IAnnotation extends IParameters {

	/**
	 * The name of the annotation.
	 * 
	 * @return
	 */
	String getName();
}
