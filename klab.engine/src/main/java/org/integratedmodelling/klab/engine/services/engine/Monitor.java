package org.integratedmodelling.klab.engine.services.engine;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.kim.validation.KimNotification;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;

public class Monitor implements IMonitor {

    private int errorCount = 0;
    private AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private int waitTime;
    private IIdentity identity;

    public Monitor(IIdentity identity) {
        this.identity = identity;
    }

    protected Monitor(Monitor monitor) {
        this.identity = monitor.identity;
        this.errorCount = monitor.errorCount;
    }

    public void setError(Throwable e) {
        this.errorCount++;
    }

    @Override
    public void info(Object... info) {
        Pair<String, INotification.Type> message = NotificationUtils.getMessage(info);
        Consumer<String> infoWriter = Logging.INSTANCE.getInfoWriter();
        if (infoWriter != null) {
            infoWriter.accept(message.getFirst());
        }
        send(new KimNotification(message, Level.INFO));
    }

    @Override
    public void warn(Object... o) {
        Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
        Consumer<String> warningWriter = Logging.INSTANCE.getWarningWriter();
        if (warningWriter != null) {
            warningWriter.accept(message.getFirst());
        }
        send(new KimNotification(message, Level.WARNING));
    }

    @Override
    public void error(Object... o) {
        Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
        Consumer<String> errorWriter = Logging.INSTANCE.getErrorWriter();
        if (errorWriter != null) {
            errorWriter.accept(message.getFirst());
        }
        send(new KimNotification(message, Level.SEVERE));
        errorCount++;
    }

    @Override
    public void debug(Object... o) {
        Pair<String, INotification.Type> message = NotificationUtils.getMessage(o);
        Consumer<String> debugWriter = Logging.INSTANCE.getDebugWriter();
        if (debugWriter != null) {
            debugWriter.accept(message.getFirst());
        }
        send(new KimNotification(message, Level.FINE));
    }

    @Override
    public void send(Object... o) {

        IMessage message = null;

        if (o != null && o.length > 0) {
            IMessageBus bus = Klab.INSTANCE.getMessageBus();
            if (bus != null) {
                if (o.length == 1 && o[0] instanceof IMessage) {
                    bus.post(message = (IMessage) o[0]);
                } else if (o.length == 1 && o[0] instanceof INotification) {
                    bus.post(message = Message.create((INotification) o[0], this.identity.getId()));
                } else {
                    bus.post(message = Message.create(this.identity.getId(), o));
                }
            }
        }
    }

    @Override
    public Future<IMessage> ask(Object... o) {
        if (o != null && o.length > 0) {
            IMessageBus bus = Klab.INSTANCE.getMessageBus();
            if (bus != null) {
                if (o.length == 1 && o[0] instanceof IMessage) {
                    return bus.ask((IMessage) o[0]);
                } else if (o.length == 1 && o[0] instanceof INotification) {
                    return bus.ask(Message.create((INotification) o[0], this.identity.getId()));
                } else {
                    return bus.ask(Message.create(this.identity.getId(), o));
                }
            }
        }
        return null;
    }

    @Override
    public void post(Consumer<IMessage> handler, Object... o) {
        if (o != null && o.length > 0) {
            IMessageBus bus = Klab.INSTANCE.getMessageBus();
            if (bus != null) {
                if (o.length == 1 && o[0] instanceof IMessage) {
                    bus.post((IMessage) o[0], handler);
                } else if (o.length == 1 && o[0] instanceof INotification) {
                    bus.post(Message.create((INotification) o[0], this.identity.getId()), handler);
                } else {
                    bus.post(Message.create(this.identity.getId(), o), handler);
                }
            }
        }
    }

    @Override
    public IIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean hasErrors() {
        return errorCount > 0;
    }

    public Monitor get(IIdentity identity) {
        Monitor ret = new Monitor(identity);
        return ret;
    }

    /**
     * Called to notify the start of any runtime job pertaining to our identity (always a
     * {@link IRuntimeIdentity} such as a task or script).
     */
    public void notifyStart() {
        System.out.println(identity + " started");
    }

    /**
     * Called to notify the start of any runtime job pertaining to our identity (always a
     * {@link IRuntimeIdentity} such as a task or script).
     * 
     * @param error true for abnormal exit
     */
    public void notifyEnd(boolean error) {
        ((errorCount > 0 || error) ? System.err : System.out)
                .println(identity + ((errorCount > 0 || error) ? " finished with errors" : " finished with no errors"));
    }

    public void interrupt() {
        isInterrupted.set(true);
        // IIdentity id = getIdentity();
        // // interrupt any parents that are the same class as ours (i.e. tasks)
        // while (id != null &&
        // id.getClass().isAssignableFrom(id.getParentIdentity().getClass())) {
        // id = id.getParentIdentity();
        // ((Monitor)((IRuntimeIdentity)id).getMonitor()).interrupt();
        // }
    }

    @Override
    public boolean isInterrupted() {
        return isInterrupted.get();
    }

    @Override
    public void addWait(int seconds) {
        this.waitTime = seconds;
        warn("Please try this operation again in " + seconds + " seconds");
    }

    @Override
    public int getWaitTime() {
        return this.waitTime;
    }
}
