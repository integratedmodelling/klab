package org.integratedmodelling.klab.api.lang.kim;

import org.integratedmodelling.klab.api.collections.KParameters;
import org.integratedmodelling.klab.api.lang.KStatement;

/**
 * 
 * @author Ferd
 *
 */
public interface KKimStatement extends KStatement, KKimScope {

	/**
	 * 
	 * @return the documentation
	 */
	KParameters<String> getDocumentationMetadata();


	/**
	 * The namespace ID for this object. Coincides with getName() if this is a
	 * IKimNamespace.
	 * 
	 * @return
	 */
	String getNamespace();

	/**
	 * All statements have a parent statement except a IKimNamespace, which always
	 * returns null, or any objects built from declarations not in a structured
	 * namespace, such as concept declarations parsed from loose strings.
	 * 
	 * @return the parent, or null in case of a namespace or a k.IM object built
	 *         from a loose statement
	 */
	KKimStatement getParent();

	/**
	 * Name of the resource path this statement was read from.
	 * 
	 * @return
	 */
	String getResourceId();



}
