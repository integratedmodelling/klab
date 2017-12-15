package org.integratedmodelling.klab.api.runtime;

import java.util.concurrent.Future;

import org.integratedmodelling.klab.api.auth.ITaskIdentity;

public interface ITask extends ITaskIdentity, Future<IContext> {

}
