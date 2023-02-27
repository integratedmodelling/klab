package org.integratedmodelling.klab.runtime;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.integratedmodelling.klab.api.auth.IRuntimeIdentity;
import org.integratedmodelling.klab.api.collections.impl.Pair;
import org.integratedmodelling.klab.api.identities.KIdentity;
import org.integratedmodelling.klab.api.services.runtime.KChannel;
import org.integratedmodelling.klab.api.services.runtime.KMessage;
import org.integratedmodelling.klab.api.services.runtime.KMessageBus;
import org.integratedmodelling.klab.api.services.runtime.KNotification;
import org.integratedmodelling.klab.api.services.runtime.KNotification.Type;
import org.integratedmodelling.klab.api.services.runtime.impl.Message;
import org.integratedmodelling.klab.logging.Logging;
import org.integratedmodelling.klab.utils.Utils;

public class Monitor implements KChannel {

    private int errorCount = 0;
    private AtomicBoolean isInterrupted = new AtomicBoolean(false);
    private int waitTime;
    private KIdentity identity;
    
    transient KMessageBus messageBus;

    public Monitor(KIdentity identity) {
        this.identity = identity;
    }

    public Monitor(KIdentity identity, KMessageBus messageBus) {
        this.identity = identity;
        this.messageBus = messageBus;
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
        Pair<String, Type> message = Utils.Notifications.getMessage(info);
        Consumer<String> infoWriter = Logging.INSTANCE.getInfoWriter();
        if (infoWriter != null) {
            infoWriter.accept(message.getFirst());
        }
        send(new Notification(message, Level.INFO));
    }

    @Override
    public void warn(Object... o) {
        Pair<String, Type> message = Utils.Notifications.getMessage(o);
        Consumer<String> warningWriter = Logging.INSTANCE.getWarningWriter();
        if (warningWriter != null) {
            warningWriter.accept(message.getFirst());
        }
        send(new Notification(message, Level.WARNING));
    }

    @Override
    public void error(Object... o) {
        Pair<String, Type> message = Utils.Notifications.getMessage(o);
        Consumer<String> errorWriter = Logging.INSTANCE.getErrorWriter();
        if (errorWriter != null) {
            errorWriter.accept(message.getFirst());
        }
        send(new Notification(message, Level.SEVERE));
        errorCount++;
    }

    @Override
    public void debug(Object... o) {
        Pair<String, Type> message = Utils.Notifications.getMessage(o);
        Consumer<String> debugWriter = Logging.INSTANCE.getDebugWriter();
        if (debugWriter != null) {
            debugWriter.accept(message.getFirst());
        }
        send(new Notification(message, Level.FINE));
    }

    @Override
    public void send(Object... o) {

        KMessage message = null;

        if (o != null && o.length > 0) {
            if (messageBus != null) {
                if (o.length == 1 && o[0] instanceof KMessage) {
                    messageBus.post(message = (KMessage) o[0]);
                } else if (o.length == 1 && o[0] instanceof KNotification) {
                    messageBus.post(message = Message.create((KNotification) o[0], this.identity.getId()));
                } else {
                    messageBus.post(message = Message.create(this.identity.getId(), o));
                }
            }
        }
    }

//    @Override
    public Future<KMessage> ask(Object... o) {
        if (o != null && o.length > 0) {
            if (messageBus != null) {
                if (o.length == 1 && o[0] instanceof KMessage) {
                    return messageBus.ask((KMessage) o[0]);
                } else if (o.length == 1 && o[0] instanceof KNotification) {
                    return messageBus.ask(Message.create((KNotification) o[0], this.identity.getId()));
                } else {
                    return messageBus.ask(Message.create(this.identity.getId(), o));
                }
            }
        }
        return null;
    }

    @Override
    public void post(Consumer<KMessage> handler, Object... o) {
        if (o != null && o.length > 0) {
            if (messageBus != null) {
                if (o.length == 1 && o[0] instanceof KMessage) {
                    messageBus.post((KMessage) o[0], handler);
                } else if (o.length == 1 && o[0] instanceof KNotification) {
                    messageBus.post(Message.create((KNotification) o[0], this.identity.getId()), handler);
                } else {
                    messageBus.post(Message.create(this.identity.getId(), o), handler);
                }
            }
        }
    }

    @Override
    public KIdentity getIdentity() {
        return identity;
    }

    @Override
    public boolean hasErrors() {
        return errorCount > 0;
    }

    public Monitor get(KIdentity identity) {
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
