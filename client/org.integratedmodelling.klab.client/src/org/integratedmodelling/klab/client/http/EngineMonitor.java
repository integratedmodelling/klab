package org.integratedmodelling.klab.client.http;

import java.util.concurrent.atomic.AtomicBoolean;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.client.messaging.StompMessageBus;

/**
 * Engine monitor that checks the engine status at regular intervals, notifying of
 * down or up events and exposing a websockets-driven message bus to communicate
 * with the engine.
 * 
 * @author ferdinando.villa
 *
 */
public class EngineMonitor {

    public static final String ENGINE_DEFAULT_URL = "http://127.0.0.1:8283/modeler";

    String engineUrl = ENGINE_DEFAULT_URL;
    String engineKey;
    protected long recheckSecondsWhenOnline = 30;
    protected long recheckSecondsWhenOffline = 15;
    long uptime = -1;
    Client client;
    //    KlabPOJOGenerator pojoGenerator;
    StompMessageBus messageBus;
    String sessionId;

    AtomicBoolean stop = new AtomicBoolean(false);

    private Runnable onEngineUp;

    private Runnable onEngineDown;

    public EngineMonitor(String url, Runnable onEngineUp, Runnable onEngineDown) {
        engineUrl = url;
        this.onEngineUp = onEngineUp;
        this.onEngineDown = onEngineDown;
        this.client = Client.create(url);
    }

    public void start() {
        new RepeatingJob().schedule();
    }

    public void stop() {
        stop.set(true);
    }

    public boolean isRunning() {
        return uptime > 0;
    }

    public String getEngineUrl() {
        return engineUrl;
    }

    public void setEngineUrl(String url) {
        this.engineUrl = url;
        this.client = Client.create(url);
    }

    public class RepeatingJob extends Job {

        public RepeatingJob() {
            super("Checking engine status...");
        }

        protected IStatus run(IProgressMonitor monitor) {

            long delay = recheckSecondsWhenOffline;
            long up = client.ping();
            if (uptime < 0 && up > 0) {
                engineUp();
                delay = recheckSecondsWhenOnline;
            } else if (up < 0 && uptime > 0) {
                engineDown();
            }

            uptime = up;

            if (!stop.get()) {
                schedule(delay * 1000);
            } else {
                stop.set(false);
            }
            return Status.OK_STATUS;
        }

    }

    /**
     * Ops performed when an engine appears online.
     */
    private void engineUp() {

        this.sessionId = client.openSession(/* TODO pass previous if persisted */null);
        if (this.sessionId != null) {
            this.messageBus = new StompMessageBus(
                    engineUrl.replaceAll("http://", "ws://").replaceAll("https://", "ws://") + "/message", sessionId);
            /*
             * call user notifier
             */
            onEngineUp.run();

        } else {
            stop();
            throw new RuntimeException("engine session negotiation failed");
        }
    }

    /**
     * Ops performed when an engine goes down.
     */
    private void engineDown() {
        // TODO Auto-generated method stub
        onEngineDown.run();
    }

    public IMessageBus bus() {

        if (this.messageBus == null) {
            if (isRunning() && sessionId != null) {
                this.messageBus = new StompMessageBus(engineUrl, sessionId);
            }
        }

        if (this.messageBus == null) {
            throw new IllegalAccessError(
                    "EngineMonitor: bus() called with engine not running or before a session was established");
        }

        return this.messageBus;
    }

}
