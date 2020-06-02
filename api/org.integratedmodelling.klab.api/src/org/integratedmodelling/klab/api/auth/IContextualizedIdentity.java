package org.integratedmodelling.klab.api.auth;

import org.integratedmodelling.klab.api.runtime.IContextualizationScope;

/**
 * A contextualized identity is an artifact that has been created in 
 * a contextualization scope, which knows the context as seen from the
 * identity itself.
 * 
 * @author Ferd
 *
 */
public interface IContextualizedIdentity extends IArtifactIdentity {

	IContextualizationScope getScope();
	
}
