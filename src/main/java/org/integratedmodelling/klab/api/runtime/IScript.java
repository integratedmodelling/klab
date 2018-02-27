package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;
import org.integratedmodelling.klab.api.auth.IScriptIdentity;

public interface IScript extends IScriptIdentity, Future<Object> {

}
