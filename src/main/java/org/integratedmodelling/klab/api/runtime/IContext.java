package org.integratedmodelling.klab.api.runtime;

import org.integratedmodelling.klab.api.auth.IContextIdentity;

public interface IContext extends IContextIdentity {

	ITask observe(String urn);

}
