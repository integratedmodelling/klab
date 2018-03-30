package org.integratedmodelling.klab.api.provenance;

import org.integratedmodelling.klab.api.auth.IEngineIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.provenance.IProvenance.Node;

/**
 * An agent in k.LAB is anything that can make observations. All such agents have a k.LAB
 * {@link IIdentity}. The agent corresponding to a {@link IEngineIdentity} tags choices made by the
 * AI in the system. Observations asserted and made by users using the API will have agents tagged
 * with {@link IUserIdentity}.
 * 
 * @author Ferd
 */
public interface IAgent extends Node {

  IIdentity getIdentity();

}
