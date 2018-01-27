package org.integratedmodelling.klab.engine.runtime;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.integratedmodelling.klab.api.auth.IContextIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.IContext;
import org.integratedmodelling.klab.api.runtime.ITask;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;

public class Task implements ITask {

    public Task() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public String getToken() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean is(Type type) {
        return type == Type.TASK;
    }

    @Override
    public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextIdentity getParentIdentity() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IMonitor getMonitor() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isCancelled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isDone() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public IContext get() throws InterruptedException, ExecutionException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContext get(long timeout, TimeUnit unit)
            throws InterruptedException, ExecutionException, TimeoutException {
        // TODO Auto-generated method stub
        return null;
    }

}
