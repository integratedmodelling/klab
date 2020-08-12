package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A contextualized identity is an artifact that has been created in 
 * a contextualization scope and exposes it.
 * 
 * @author Ferd
 *
 */
public interface IContextualizedIdentity extends IArtifactIdentity {

	/**
	 * The scope of contextualization, giving access to everything about the
	 * context and the structure of the identities in it.
	 * 
	 * @return
	 */
	IContextualizationScope getScope();
	
}
