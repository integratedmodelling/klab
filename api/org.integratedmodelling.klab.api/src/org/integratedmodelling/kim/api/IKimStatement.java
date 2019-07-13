package org.integratedmodelling.kim.api;

import java.util.List;

/**
 * 
 * @author Ferd
 *
 */
public interface IKimStatement extends IKimScope {

	/**
	 * Scope is relevant to models and namespaces, where it affects resolution
	 * of models.
	 * 
	 * @author Ferd
	 *
	 */
	public enum Scope {

		NAMESPACE, PROJECT, PUBLIC;

		public Scope narrowest(Scope... scopes) {
			Scope ret = scopes == null || scopes.length == 0 ? null : scopes[0];
			if (ret != null) {
				for (int i = 1; i < scopes.length; i++) {
					if (scopes[i].ordinal() < ret.ordinal()) {
						ret = scopes[i];
					}
				}
			}
			return ret;
		}
	}

	/**
	 * 
	 * @return the first line number
	 */
	int getFirstLine();

	/**
	 * 
	 * @return the last line number
	 */
	int getLastLine();

	/**
	 * 
	 * @return the start offset in the document
	 */
	int getFirstCharOffset();

	/**
	 * 
	 * @return the last offset in the document
	 */
	int getLastCharOffset();

	/**
	 * 
	 * @return the annotations
	 */
	List<IKimAnnotation> getAnnotations();

	/**
	 * 
	 * @return the metadata
	 */
	IKimMetadata getMetadata();

	/**
	 * 
	 * @return the documentation
	 */
	IKimMetadata getDocumentationMetadata();

	/**
	 * 
	 * @return true if deprecated
	 */
	boolean isDeprecated();

	/**
	 * 
	 * @return the reason for deprecation
	 */
	String getDeprecation();

	/**
	 * 
	 * @return the source code
	 */
	String getSourceCode();

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
	IKimStatement getParent();

	/**
	 * Name of the resource path this statement was read from.
	 * 
	 * @return
	 */
	String getResourceId();

	boolean isErrors();

	boolean isWarnings();

}
