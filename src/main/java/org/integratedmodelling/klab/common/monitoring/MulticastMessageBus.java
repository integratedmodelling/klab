package org.integratedmodelling.klab.common.monitoring;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.exceptions.KlabRuntimeException;
import org.integratedmodelling.klab.utils.IPUtils;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelListener;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MulticastMessageBus extends ReceiverAdapter implements ChannelListener {

    IEngine engine;
    
    /*
     * TODO if this solves any problems, make it configurable.
     */
    protected boolean useLoopback = true;
    
    /**
     * @author ferdinando.villa
     *
     * @param <T>
     */
    public abstract static class Listener<T> {

        Class<T> typeClass;

        /**
         * @param payload
         */
        public abstract void onMessage(T payload);

        /**
         * @param tclass
         */
        public Listener(Class<T> tclass) {
            this.typeClass = tclass;
        }
    }

    /**
     * the data returned by each broadcasting engine in a ping.
     */
    public static class EngineStatus {
        
        long   lastEngineTime; // 1
        String name;                     // 4
        String version;               // 5
        String build;                   // 6
        long   bootTime;             // 7
        long   totalMemory;       // 8
        long   freeMemory;         // 9
        int    nProcessors;       // 10
        
        public long getLastEngineTime() {
            return lastEngineTime;
        }
        public void setLastEngineTime(long lastEngineTime) {
            this.lastEngineTime = lastEngineTime;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public String getBuild() {
            return build;
        }
        public void setBuild(String build) {
            this.build = build;
        }
        public long getBootTime() {
            return bootTime;
        }
        public void setBootTime(long bootTime) {
            this.bootTime = bootTime;
        }
        public long getTotalMemory() {
            return totalMemory;
        }
        public void setTotalMemory(long totalMemory) {
            this.totalMemory = totalMemory;
        }
        public long getFreeMemory() {
            return freeMemory;
        }
        public void setFreeMemory(long freeMemory) {
            this.freeMemory = freeMemory;
        }
        public int getnProcessors() {
            return nProcessors;
        }
        public void setnProcessors(int nProcessors) {
            this.nProcessors = nProcessors;
        }
    }

    private int                 SECONDS_INTERVAL = 5;
    private static final String PING_HEADER      = "@";

    /**
     * After this is called, the listener will be notified of any payload message sent to
     * the channel.
     * 
     * @param channel
     * @param listener
     */
    public void subscribe(String channel, Listener<?> listener) {
        subscribedChannels.put(channel, listener);
    }

    /**
     * Stops listening to a channel.
     * 
     * @param channel
     */
    public void unsubscribe(String channel) {
        subscribedChannels.remove(channel);
    }

    /**
     * @param listener
     */
    public void setBroadcastListener(Listener<?> listener) {
        broadcastListener = listener;
    }

    @Override
    public void channelClosed(Channel arg0) {
        running = false;
    }

    @Override
    public void channelConnected(Channel arg0) {
        running = true;
        Util.registerChannel(channel, "kmodeler");
    }

    @Override
    public void channelDisconnected(Channel arg0) {
        running = false;
    }

    Mode                        mode;
    protected String            cluster_name            = "draw";
    private JChannel            channel                 = null;
    boolean                     no_channel              = false;
    boolean                     jmx;
    private boolean             use_state               = false;
    // private long state_timeout = 5000;
    // private boolean use_unicasts = false;
    protected boolean           send_own_state_on_merge = true;
    private final List<Address> members                 = new ArrayList<>();
    private String              identity;
    private int                 port;
    private boolean             running;
    String                      address;
    private Timer               timer;
    ObjectMapper                objectMapper            = new ObjectMapper();
    Map<String, Listener<?>>    subscribedChannels      = new Hashtable<>();
    Listener<?>                 broadcastListener       = null;

    @Override
    public void viewAccepted(View v) {

        members.clear();
        members.addAll(v.getMembers());

        if (v instanceof MergeView) {
            if (use_state && !members.isEmpty()) {
                Address coord = members.get(0);
                Address local_addr = channel.getAddress();
                if (local_addr != null && !local_addr.equals(coord)) {
                    try {
                        channel.getState(coord, 5000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            Klab.INSTANCE.info("cluster modified: " + v);
        }
    }

    /**
     * Stop broadcasting or receiving.
     */
    public void stop() {
        if (!no_channel) {
            try {
                channel.close();
            } catch (Exception e) {
                throw new KlabRuntimeException(e);
            }
        }
        running = false;
    }

    /**
     * Send an object through a specific channel.
     * 
     * @param object a string or a serializable object that can be turned into JSON.
     * @param channel a channel id (does not need to exist).
     */
    public void send(Object object, String channel) {

        String message = "";
        try {
            message = object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new KlabRuntimeException(e);
        }

        if (channel != null) {
            message = channel + message;
        }

        Message msg = new Message(null, message);
        try {
            this.channel.send(msg);
        } catch (Exception e) {
            Klab.INSTANCE.warn("message lost: " + message);
        }
    }

    /**
     * @return true if advertising is going on.
     */
    public boolean isRunning() {
        return running;
    }

    enum Mode {
        ADVERTISE,
        DISCOVER
    }

    public String toJSON(Object bean) {
        try {
            return objectMapper.writeValueAsString(bean);
        } catch (JsonProcessingException e) {
            throw new KlabRuntimeException(e);
        }
    }

    /**
     * Just access our configured object mapper, for external access when we want to wrap
     * an object into a notification.
     * 
     * @param beanJSON
     * @param cls
     * @return the deserialized object
     */
    @SuppressWarnings("unchecked")
    public <T> T fromJSON(String beanJSON, Class<?> cls) {
        try {
            return (T) objectMapper.readValue(beanJSON, cls);
        } catch (Exception e) {
            throw new KlabRuntimeException(e);
        }
    }

    /**
     * Redefine in discovery mode to react to a ping. Does not get called at messages to
     * specific channels.
     * 
     * @param ipAddress IP address of advertising server
     * @param port port where service is located
     * @param time time of message reception
     */
    protected void onSignalAvailable(String ipAddress, int port, EngineStatus status) {
        System.out.println("Got ping from " + status);
    }

    public void sendToAll(String buf) throws Exception {
        Address local_addr = channel.getAddress();
        for (Address mbr : members) {
            if (local_addr != null && !local_addr.equals(mbr)) {
                channel.send(new Message(mbr, buf));
            }
        }
    }

    public void sendToSelf(String buf) throws Exception {
        Address local_addr = channel.getAddress();
        for (Address mbr : members) {
            if (local_addr != null && local_addr.equals(mbr)) {
                channel.send(new Message(mbr, buf));
            }
        }
    }

    @Override
    public void receive(Message message) {
        if (isReceiving()) {
            signal(message.getObject().toString());
        }
    }

    /**
     * Redefine if control of reception is needed.
     * 
     * @return
     */
    protected boolean isReceiving() {
        return true;
    }

    @SuppressWarnings("unchecked")
    private <T> void signal(String msg) {

        if (mode == Mode.DISCOVER) {

            if (msg.startsWith(PING_HEADER)) {

                String[] md = msg.substring(PING_HEADER.length()).split("#");
                if (md[0].equals(this.identity)) {

                    EngineStatus status = new EngineStatus();

                    status.setLastEngineTime(Long.parseLong(md[1]));
                    status.setName(md[4]);
                    status.setBootTime(Long.parseLong(md[7]));
                    status.setBuild(md[6]);
                    status.setVersion(md[5]);
                    status.setFreeMemory(Long.parseLong(md[9]));
                    status.setTotalMemory(Long.parseLong(md[8]));
                    status.setnProcessors(Integer.parseInt(md[10]));

                    onSignalAvailable(md[2], Integer.parseInt(md[3]), status);
                }
            } else {

                /*
                 * strip channel and reconstruct payload if we're subscribed
                 */
                boolean found = false;
                for (String channel : subscribedChannels.keySet()) {
                    if (msg.startsWith(channel)) {
                        Listener<T> cls = (Listener<T>) subscribedChannels.get(channel);
                        T payload = null;
                        try {
                            payload = objectMapper.readValue(msg.substring(channel.length()), cls.typeClass);
                        } catch (Exception e) {
                            throw new KlabRuntimeException(e);
                        }
                        if (payload != null) {
                            cls.onMessage(payload);
                        }
                        found = true;
                        break;
                    }
                }

                if (!found && broadcastListener != null) {
                    Listener<T> cls = (Listener<T>) broadcastListener;
                    T payload = null;
                    try {
                        payload = objectMapper.readValue(msg, cls.typeClass);
                    } catch (Exception e) {
                        throw new KlabRuntimeException(e);
                    }
                    if (payload != null) {
                        cls.onMessage(payload);
                    }
                }
            }
        } else {

            /*
             * TODO engines may want to be aware of each other, for example to distribute
             * computation.
             */
        }
    }

    /**
     * Start in discovery mode. No advertising is done, onSignal() is called whenever an
     * advertiser on the same identity notifies itself.
     * 
     * @param identity
     */
    public MulticastMessageBus(IEngine engine, String nodeName, String identity) {
        this(engine, identity, 0);
    }

    /**
     * Start in advertising mode. Local address and passed port are broadcast for this
     * identity every SECONDS_INTERVAL seconds.
     * 
     * @param identity
     * @param port
     */
    public MulticastMessageBus(IEngine engine, String identity, int port) {

        this.engine = engine;
        
        // prevents LOTS of issues with user-created metadata from web etc.
        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        
        this.mode = port == 0 ? Mode.DISCOVER : Mode.ADVERTISE;
        this.identity = identity;
        this.port = port;
        try {

            System.setProperty("java.net.preferIPv4Stack", "true");
            System.setProperty("jgroups.bind_addr", Configuration.INSTANCE.getProperties().getProperty("klab.cast.address", "127.0.0.1"));
            
            channel = new JChannel();
            channel.setName((port == 0 ? "client" : "engine") + "-" + engine.getName() + "-" + identity);
            channel.setReceiver(this);
            channel.addChannelListener(this);
            channel.connect(identity);
            
        } catch (Exception e) {
            throw new KlabRuntimeException(e);
        }

        this.running = true;

        if (mode == Mode.ADVERTISE) {
            try {
                startAdvertising();
            } catch (Exception e) {
                this.running = false;
            }
        }
        
    }

    void startAdvertising() throws Exception {
        this.address = useLoopback ? "127.0.0.1" : IPUtils.getLocalIp();
        this.timer = new Timer();
        Klab.INSTANCE.info("advertising engine at " + this.address);
        timer.schedule(new AdvertiseTask(), 0, SECONDS_INTERVAL * 1000);
    }

    class AdvertiseTask extends TimerTask {

        private int sendErrors;

        @Override
        public void run() {
            try {

                Runtime runtime = Runtime.getRuntime();
                String build = "development version";
                if (!Version.VERSION_BUILD.equals("VERSION_BUILD")) {
                    build = " build " + Version.VERSION_BUILD + " (" + Version.VERSION_BRANCH + " "
                            + Version.VERSION_DATE + ")";
                }
                String version = new Version().toString();
                String name = engine.getName();
                long bootTime = engine.getBootTime().getTime();
                long totalMemory = runtime.totalMemory() / 1048576;
                long freeMemory = runtime.freeMemory() / 1048576;
                int processors = runtime.availableProcessors();

                String message = PING_HEADER + identity + "#" // 0
                        + System.currentTimeMillis() + "#" // 1
                        + address + "#" // 2
                        + port + "#" // 3
                        + name + "#" // 4
                        + version + "#" // 5
                        + build + "#" // 6
                        + bootTime + "#" // 7
                        + totalMemory + "#" // 8
                        + freeMemory + "#" // 9
                        + processors; // 10

                sendToAll(message);
                sendErrors = 0;

            } catch (Exception e) {
                // TODO count errors? Do these happen? Have a threshold of
                // errorcount to
                // declare failure?
                sendErrors++;
                Klab.INSTANCE.error(e.getMessage());
            }
        }
    }

}
