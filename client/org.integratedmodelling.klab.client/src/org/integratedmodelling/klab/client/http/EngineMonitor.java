package org.integratedmodelling.klab.client.http;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.integratedmodelling.klab.api.API;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.client.messaging.StompMessageBus;
import org.integratedmodelling.klab.rest.Capabilities;
import org.integratedmodelling.klab.rest.IdentityReference;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.PingResponse;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Engine monitor that checks the engine status at regular intervals, notifying of down or up events
 * and handling automatically the opening or rejoining of a session when the engine comes up,
 * exposing a websockets-driven message bus to communicate with the engine. If a relay was installed
 * in the session to capture messages from the web explorer, adds a bus for that so the
 * correspondent peers can be notified.
 * 
 * @author ferdinando.villa
 *
 */
public class EngineMonitor {

    private static final Logger logger = LoggerFactory.getLogger(EngineMonitor.class);
    public static final String ENGINE_DEFAULT_URL = "http://127.0.0.1:8283/modeler";

    String engineUrl = ENGINE_DEFAULT_URL;
    String engineKey;
    protected long recheckSecondsWhenOnline = 5;
    protected long recheckSecondsWhenOffline = 5;
    long uptime = -1;
    EngineClient client;
    StompMessageBus bus;

    String sessionId;

    AtomicBoolean stop = new AtomicBoolean(false);

    private Runnable onEngineUp;
    private Runnable onEngineDown;

    /**
     * If passed to start(), the ID of a relay object so that messages from the Explorer to the
     * engine can be relayed to it.
     */
    private String relayId;

    private Capabilities capabilities;

    public EngineMonitor(String url, Runnable onEngineUp, Runnable onEngineDown, @Nullable String initialSessionId) {
        engineUrl = url;
        this.onEngineUp = onEngineUp;
        this.onEngineDown = onEngineDown;
        this.client = EngineClient.create(url);
        this.sessionId = initialSessionId;
        this.client = client.with(this.sessionId);
    }

    /**
     * Use in a engineUp handler to store the session ID after the engine was started.
     * 
     * @return the session ID
     */
    public String getSessionId() {
        return this.sessionId;
    }

    public void start(String relayId) {
        this.relayId = relayId;
        new RepeatingJob().schedule();
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
        this.client = EngineClient.create(url);
    }

    public class RepeatingJob extends Job {

        public RepeatingJob() {
            super("Checking engine status...");
        }

        protected IStatus run(IProgressMonitor monitor) {
//
//            try {
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
//            } catch (Throwable t) {
//            }
            return Status.OK_STATUS;
        }

    }

    /**
     * Ops performed when an engine appears online.
     */
    private void engineUp() {

        /*
         * TODO if engine is not local, must first authenticate with stored username/password
         */
        PingResponse ping = client.get(API.PING, PingResponse.class);
        this.engineKey = ping.getEngineId();
        this.capabilities = client.get(API.CAPABILITIES, Capabilities.class);
        this.sessionId = client.openSession(this.sessionId != null ? this.sessionId : ping.getLocalSessionId(), relayId);
        if (this.sessionId != null) {
            if (this.bus != null) {
                // shouldn't happen
                this.bus.stop();
            }
            this.bus = new StompMessageBus(engineUrl.replaceAll("http://", "ws://").replaceAll("https://", "ws://") + "/message"){

                @Override
                protected void error(String string) {
                    EngineMonitor.this.error(string);
                }

            };
            onEngineUp.run();
        } else {
            stop();
            throw new RuntimeException("engine session negotiation failed");
        }
    }

    public ObservationReference[] getChildren(ObservationReference observation, int offset, int count) {
        return client.with(this.sessionId)
                .get(API.ENGINE.OBSERVATION.VIEW.GET_CHILDREN_OBSERVATION.replace("{observation}", observation.getId())
                        + "?count=" + count + "&offset=" + offset, ObservationReference[].class);
    }

    public IdentityReference getOwner() {
        return this.capabilities == null ? null : this.capabilities.getOwner();
    }

    /**
     * Override for better error handling
     * 
     * @param string
     */
    protected void error(String string) {
        logger.error(string);
    }

    public String getEngineId() {
        return this.engineKey;
    }

    /**
     * Return all local projects known to the engine.
     * 
     * @return reference beans for each project
     */
    public List<ProjectReference> getLocalProjects() {
        return capabilities == null ? new ArrayList<>() : capabilities.getLocalWorkspaceProjects();
    }

    /**
     * Ops performed when an engine goes down.
     */
    private void engineDown() {
        uptime = 0;
        onEngineDown.run();
        bus.stop();
        bus = null;
        sessionId = null;
    }

    public IMessageBus getBus() {
        return bus;
    }

    public Capabilities getCapabilities() {
        return this.capabilities;
    }

    public EngineClient getClient() {
        return this.client;
    }

}
