///*
// * This file is part of k.LAB.
// * 
// * k.LAB is free software: you can redistribute it and/or modify
// * it under the terms of the Affero GNU General Public License as published
// * by the Free Software Foundation, either version 3 of the License,
// * or (at your option) any later version.
// *
// * A copy of the GNU Affero General Public License is distributed in the root
// * directory of the k.LAB distribution (LICENSE.txt). If this cannot be found 
// * see <http://www.gnu.org/licenses/>.
// * 
// * Copyright (C) 2007-2018 integratedmodelling.org and any authors mentioned
// * in author tags. All rights reserved.
// */
//package org.integratedmodelling.klab.common.monitoring;
//
//import java.util.ArrayList;
//import java.util.Hashtable;
//import java.util.List;
//import java.util.Map;
//import java.util.Timer;
//import java.util.TimerTask;
//import org.integratedmodelling.klab.Configuration;
//import org.integratedmodelling.klab.Logging;
//import org.integratedmodelling.klab.Version;
//import org.integratedmodelling.klab.api.engine.IEngine;
//import org.integratedmodelling.klab.exceptions.KlabIOException;
//import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
//import org.integratedmodelling.klab.exceptions.KlabValidationException;
//import org.integratedmodelling.klab.utils.IPUtils;
//import org.jgroups.Address;
//import org.jgroups.Channel;
//import org.jgroups.ChannelListener;
//import org.jgroups.JChannel;
//import org.jgroups.MergeView;
//import org.jgroups.Message;
//import org.jgroups.ReceiverAdapter;
//import org.jgroups.View;
//import org.jgroups.util.Util;
//import com.fasterxml.jackson.core.JsonParser.Feature;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//// TODO: Auto-generated Javadoc
///**
// * The Class MulticastMessageBus.
// *
// * @author ferdinando.villa
// * @version $Id: $Id
// */
//public class MulticastMessageBus extends ReceiverAdapter implements ChannelListener {
//
//    IEngine engine;
//    
//    /*
//     * TODO if this solves any problems, make it configurable.
//     */
//    protected boolean useLoopback = true;
//    
//    /**
//     * The Class Listener.
//     *
//     * @author ferdinando.villa
//     * @param <T> the generic type
//     */
//    public abstract static class Listener<T> {
//
//        Class<T> typeClass;
//
//        /**
//         * On message.
//         *
//         * @param payload the payload
//         */
//        public abstract void onMessage(T payload);
//
//        /**
//         * Instantiates a new listener.
//         *
//         * @param tclass the tclass
//         */
//        public Listener(Class<T> tclass) {
//            this.typeClass = tclass;
//        }
//    }
//
//    /**
//     * the data returned by each broadcasting engine in a ping.
//     */
//    public static class EngineStatus {
//        
//        long   lastEngineTime; // 1
//        String name;                     // 4
//        String version;               // 5
//        String build;                   // 6
//        long   bootTime;             // 7
//        long   totalMemory;       // 8
//        long   freeMemory;         // 9
//        int    nProcessors;       // 10
//        
//        /**
//         * Gets the last engine time.
//         *
//         * @return the last engine time
//         */
//        public long getLastEngineTime() {
//            return lastEngineTime;
//        }
//        
//        /**
//         * Sets the last engine time.
//         *
//         * @param lastEngineTime the new last engine time
//         */
//        public void setLastEngineTime(long lastEngineTime) {
//            this.lastEngineTime = lastEngineTime;
//        }
//        
//        /**
//         * Gets the name.
//         *
//         * @return the name
//         */
//        public String getName() {
//            return name;
//        }
//        
//        /**
//         * Sets the name.
//         *
//         * @param name the new name
//         */
//        public void setName(String name) {
//            this.name = name;
//        }
//        
//        /**
//         * Gets the version.
//         *
//         * @return the version
//         */
//        public String getVersion() {
//            return version;
//        }
//        
//        /**
//         * Sets the version.
//         *
//         * @param version the new version
//         */
//        public void setVersion(String version) {
//            this.version = version;
//        }
//        
//        /**
//         * Gets the builds the.
//         *
//         * @return the builds the
//         */
//        public String getBuild() {
//            return build;
//        }
//        
//        /**
//         * Sets the builds the.
//         *
//         * @param build the new builds the
//         */
//        public void setBuild(String build) {
//            this.build = build;
//        }
//        
//        /**
//         * Gets the boot time.
//         *
//         * @return the boot time
//         */
//        public long getBootTime() {
//            return bootTime;
//        }
//        
//        /**
//         * Sets the boot time.
//         *
//         * @param bootTime the new boot time
//         */
//        public void setBootTime(long bootTime) {
//            this.bootTime = bootTime;
//        }
//        
//        /**
//         * Gets the total memory.
//         *
//         * @return the total memory
//         */
//        public long getTotalMemory() {
//            return totalMemory;
//        }
//        
//        /**
//         * Sets the total memory.
//         *
//         * @param totalMemory the new total memory
//         */
//        public void setTotalMemory(long totalMemory) {
//            this.totalMemory = totalMemory;
//        }
//        
//        /**
//         * Gets the free memory.
//         *
//         * @return the free memory
//         */
//        public long getFreeMemory() {
//            return freeMemory;
//        }
//        
//        /**
//         * Sets the free memory.
//         *
//         * @param freeMemory the new free memory
//         */
//        public void setFreeMemory(long freeMemory) {
//            this.freeMemory = freeMemory;
//        }
//        
//        /**
//         * Gets the n processors.
//         *
//         * @return the n processors
//         */
//        public int getnProcessors() {
//            return nProcessors;
//        }
//        
//        /**
//         * Sets the n processors.
//         *
//         * @param nProcessors the new n processors
//         */
//        public void setnProcessors(int nProcessors) {
//            this.nProcessors = nProcessors;
//        }
//    }
//
//    private int                 SECONDS_INTERVAL = 5;
//    private static final String PING_HEADER      = "@";
//
//    /**
//     * After this is called, the listener will be notified of any payload message sent to the
//     * channel.
//     *
//     * @param channel the channel
//     * @param listener the listener
//     */
//    public void subscribe(String channel, Listener<?> listener) {
//        subscribedChannels.put(channel, listener);
//    }
//
//    /**
//     * Stops listening to a channel.
//     *
//     * @param channel the channel
//     */
//    public void unsubscribe(String channel) {
//        subscribedChannels.remove(channel);
//    }
//
//    /**
//     * Sets the broadcast listener.
//     *
//     * @param listener the new broadcast listener
//     */
//    public void setBroadcastListener(Listener<?> listener) {
//        broadcastListener = listener;
//    }
//
//    /** {@inheritDoc} */
//    @Override
//    public void channelClosed(Channel arg0) {
//        running = false;
//    }
//
//    /** {@inheritDoc} */
//    @Override
//    public void channelConnected(Channel arg0) {
//        running = true;
//        Util.registerChannel(channel, "kmodeler");
//    }
//
//    /** {@inheritDoc} */
//    @Override
//    public void channelDisconnected(Channel arg0) {
//        running = false;
//    }
//
//    Mode                        mode;
//    protected String            cluster_name            = "draw";
//    private JChannel            channel                 = null;
//    boolean                     no_channel              = false;
//    boolean                     jmx;
//    private boolean             use_state               = false;
//    // private long state_timeout = 5000;
//    // private boolean use_unicasts = false;
//    protected boolean           send_own_state_on_merge = true;
//    private final List<Address> members                 = new ArrayList<>();
//    private String              identity;
//    private int                 port;
//    private boolean             running;
//    String                      address;
//    private Timer               timer;
//    ObjectMapper                objectMapper            = new ObjectMapper();
//    Map<String, Listener<?>>    subscribedChannels      = new Hashtable<>();
//    Listener<?>                 broadcastListener       = null;
//
//    /** {@inheritDoc} */
//    @Override
//    public void viewAccepted(View v) {
//
//        members.clear();
//        members.addAll(v.getMembers());
//
//        if (v instanceof MergeView) {
//            if (use_state && !members.isEmpty()) {
//                Address coord = members.get(0);
//                Address local_addr = channel.getAddress();
//                if (local_addr != null && !local_addr.equals(coord)) {
//                    try {
//                        channel.getState(coord, 5000);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        } else {
//            Logging.INSTANCE.info("cluster modified: " + v);
//        }
//    }
//
//    /**
//     * Stop broadcasting or receiving.
//     */
//    public void stop() {
//        if (!no_channel) {
//            try {
//                channel.close();
//            } catch (Exception e) {
//                throw new KlabIOException(e);
//            }
//        }
//        running = false;
//    }
//
//    /**
//     * Send an object through a specific channel.
//     *
//     * @param object a string or a serializable object that can be turned into JSON.
//     * @param channel a channel id (does not need to exist).
//     */
//    public void send(Object object, String channel) {
//
//        String message = "";
//        try {
//            message = object instanceof String ? (String) object : objectMapper.writeValueAsString(object);
//        } catch (JsonProcessingException e) {
//            throw new KlabIOException(e);
//        }
//
//        if (channel != null) {
//            message = channel + message;
//        }
//
//        Message msg = new Message(null, message);
//        try {
//            this.channel.send(msg);
//        } catch (Exception e) {
//            Logging.INSTANCE.warn("message lost: " + message);
//        }
//    }
//
//    /**
//     * Checks if is running.
//     *
//     * @return true if advertising is going on.
//     */
//    public boolean isRunning() {
//        return running;
//    }
//
//    enum Mode {
//        ADVERTISE,
//        DISCOVER
//    }
//
//    /**
//     * To JSON.
//     *
//     * @param bean the bean
//     * @return the string
//     */
//    public String toJSON(Object bean) {
//        try {
//            return objectMapper.writeValueAsString(bean);
//        } catch (JsonProcessingException e) {
//            throw new KlabValidationException(e);
//        }
//    }
//
//    /**
//     * Just access our configured object mapper, for external access when we want to wrap an object
//     * into a notification.
//     *
//     * @param <T> the generic type
//     * @param beanJSON the bean JSON
//     * @param cls the cls
//     * @return the deserialized object
//     */
//    @SuppressWarnings("unchecked")
//    public <T> T fromJSON(String beanJSON, Class<?> cls) {
//        try {
//            return (T) objectMapper.readValue(beanJSON, cls);
//        } catch (Exception e) {
//            throw new KlabValidationException(e);
//        }
//    }
//
//    /**
//     * Redefine in discovery mode to react to a ping. Does not get called at messages to
//     * specific channels.
//     *
//     * @param ipAddress IP address of advertising server
//     * @param port port where service is located
//     * @param status a {@link org.integratedmodelling.klab.common.monitoring.MulticastMessageBus.EngineStatus} object.
//     */
//    protected void onSignalAvailable(String ipAddress, int port, EngineStatus status) {
//        System.out.println("Got ping from " + status);
//    }
//
//    /**
//     * Send to all.
//     *
//     * @param buf the buf
//     * @throws Exception the exception
//     */
//    public void sendToAll(String buf) throws Exception {
//        Address local_addr = channel.getAddress();
//        for (Address mbr : members) {
//            if (local_addr != null && !local_addr.equals(mbr)) {
//                channel.send(new Message(mbr, buf));
//            }
//        }
//    }
//
//    /**
//     * Send to self.
//     *
//     * @param buf the buf
//     * @throws Exception the exception
//     */
//    public void sendToSelf(String buf) throws Exception {
//        Address local_addr = channel.getAddress();
//        for (Address mbr : members) {
//            if (local_addr != null && local_addr.equals(mbr)) {
//                channel.send(new Message(mbr, buf));
//            }
//        }
//    }
//
//    /** {@inheritDoc} */
//    @Override
//    public void receive(Message message) {
//        if (isReceiving()) {
//            signal(message.getObject().toString());
//        }
//    }
//
//    /**
//     * Redefine if control of reception is needed.
//     *
//     * @return a boolean.
//     */
//    protected boolean isReceiving() {
//        return true;
//    }
//
//    @SuppressWarnings("unchecked")
//    private <T> void signal(String msg) {
//
//        if (mode == Mode.DISCOVER) {
//
//            if (msg.startsWith(PING_HEADER)) {
//
//                String[] md = msg.substring(PING_HEADER.length()).split("#");
//                if (md[0].equals(this.identity)) {
//
//                    EngineStatus status = new EngineStatus();
//
//                    status.setLastEngineTime(Long.parseLong(md[1]));
//                    status.setName(md[4]);
//                    status.setBootTime(Long.parseLong(md[7]));
//                    status.setBuild(md[6]);
//                    status.setVersion(md[5]);
//                    status.setFreeMemory(Long.parseLong(md[9]));
//                    status.setTotalMemory(Long.parseLong(md[8]));
//                    status.setnProcessors(Integer.parseInt(md[10]));
//
//                    onSignalAvailable(md[2], Integer.parseInt(md[3]), status);
//                }
//            } else {
//
//                /*
//                 * strip channel and reconstruct payload if we're subscribed
//                 */
//                boolean found = false;
//                for (String channel : subscribedChannels.keySet()) {
//                    if (msg.startsWith(channel)) {
//                        Listener<T> cls = (Listener<T>) subscribedChannels.get(channel);
//                        T payload = null;
//                        try {
//                            payload = objectMapper.readValue(msg.substring(channel.length()), cls.typeClass);
//                        } catch (Exception e) {
//                            throw new KlabValidationException(e);
//                        }
//                        if (payload != null) {
//                            cls.onMessage(payload);
//                        }
//                        found = true;
//                        break;
//                    }
//                }
//
//                if (!found && broadcastListener != null) {
//                    Listener<T> cls = (Listener<T>) broadcastListener;
//                    T payload = null;
//                    try {
//                        payload = objectMapper.readValue(msg, cls.typeClass);
//                    } catch (Exception e) {
//                        throw new KlabValidationException(e);
//                    }
//                    if (payload != null) {
//                        cls.onMessage(payload);
//                    }
//                }
//            }
//        } else {
//
//            /*
//             * TODO engines may want to be aware of each other, for example to distribute
//             * computation.
//             */
//        }
//    }
//
//    /**
//     * Start in discovery mode. No advertising is done, onSignal() is called whenever an advertiser
//     * on the same identity notifies itself.
//     *
//     * @param engine the engine
//     * @param nodeName the node name
//     * @param identity the identity
//     */
//    public MulticastMessageBus(IEngine engine, String nodeName, String identity) {
//        this(engine, identity, 0);
//    }
//
//    /**
//     * Start in advertising mode. Local address and passed port are broadcast for this identity
//     * every SECONDS_INTERVAL seconds.
//     *
//     * @param engine the engine
//     * @param identity the identity
//     * @param port the port
//     */
//    public MulticastMessageBus(IEngine engine, String identity, int port) {
//
//        this.engine = engine;
//        
//        // prevents LOTS of issues with user-created metadata from web etc.
//        objectMapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
//        
//        this.mode = port == 0 ? Mode.DISCOVER : Mode.ADVERTISE;
//        this.identity = identity;
//        this.port = port;
//        try {
//
//            System.setProperty("java.net.preferIPv4Stack", "true");
//            System.setProperty("jgroups.bind_addr", Configuration.INSTANCE.getProperties().getProperty("klab.cast.address", "127.0.0.1"));
//            
//            channel = new JChannel();
//            channel.setName((port == 0 ? "client" : "engine") + "-" + engine.getName() + "-" + identity);
//            channel.setReceiver(this);
//            channel.addChannelListener(this);
//            channel.connect(identity);
//            
//        } catch (Exception e) {
//            throw new KlabInternalErrorException(e);
//        }
//
//        this.running = true;
//
//        if (mode == Mode.ADVERTISE) {
//            try {
//                startAdvertising();
//            } catch (Exception e) {
//                this.running = false;
//            }
//        }
//        
//    }
//
//    void startAdvertising() throws Exception {
//        this.address = useLoopback ? "127.0.0.1" : IPUtils.getLocalIp();
//        this.timer = new Timer();
//        Logging.INSTANCE.info("advertising engine at " + this.address);
//        timer.schedule(new AdvertiseTask(), 0, SECONDS_INTERVAL * 1000);
//    }
//
//    class AdvertiseTask extends TimerTask {
//
//        private int sendErrors;
//
//        @Override
//        public void run() {
//            try {
//
//                Runtime runtime = Runtime.getRuntime();
//                String build = "development version";
//                if (!Version.VERSION_BUILD.equals("VERSION_BUILD")) {
//                    build = " build " + Version.VERSION_BUILD + " (" + Version.VERSION_BRANCH + " "
//                            + Version.VERSION_DATE + ")";
//                }
//                String version = new Version().toString();
//                String name = engine.getName();
//                long bootTime = engine.getBootTime().getTime();
//                long totalMemory = runtime.totalMemory() / 1048576;
//                long freeMemory = runtime.freeMemory() / 1048576;
//                int processors = runtime.availableProcessors();
//
//                String message = PING_HEADER + identity + "#" // 0
//                        + System.currentTimeMillis() + "#" // 1
//                        + address + "#" // 2
//                        + port + "#" // 3
//                        + name + "#" // 4
//                        + version + "#" // 5
//                        + build + "#" // 6
//                        + bootTime + "#" // 7
//                        + totalMemory + "#" // 8
//                        + freeMemory + "#" // 9
//                        + processors; // 10
//
//                sendToAll(message);
//                sendErrors = 0;
//
//            } catch (Exception e) {
//                // TODO count errors? Do these happen? Have a threshold of
//                // errorcount to
//                // declare failure?
//                sendErrors++;
//                Logging.INSTANCE.error(e.getMessage());
//            }
//        }
//    }
//
//}
