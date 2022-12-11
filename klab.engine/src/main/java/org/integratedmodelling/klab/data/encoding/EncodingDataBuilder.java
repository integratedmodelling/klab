package org.integratedmodelling.klab.data.encoding;

import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.ILocator;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.common.Offset;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.Notification;
import org.integratedmodelling.klab.data.encoding.Encoding.KlabData.Severity;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.utils.NotificationUtils;

/**
 * A builder that encodes the data into a Protobuf object which will be sent over the network for
 * reconstruction by a matching {@link IKlabData} object at the client end. The build() step is
 * normally not called here, but uses the {@link RemoteData} class for completeness and testing.
 * 
 * @author ferdinando.villa
 *
 */
public class EncodingDataBuilder implements IKlabData.Builder {

    KlabData.Builder builder = KlabData.newBuilder();
    KlabData.State.Builder stateBuilder = null;
    KlabData.Object.Builder objectBuilder = null;
    EncodingDataBuilder parent = null;
    String stateUnit;

    private IConcept semantics;

    public EncodingDataBuilder() {
    }

    class Monitor implements IMonitor {

        private int waitTime;
        private Inspector inspector;

        @Override
        public void info(Object... info) {
            Notification.Builder nb = Notification.newBuilder();
            nb.setSeverity(Severity.INFO);
            nb.setText(NotificationUtils.getMessage(info).getFirst());
            builder.addNotifications(nb.build());
        }

        @Override
        public void warn(Object... o) {
            Notification.Builder nb = Notification.newBuilder();
            nb.setSeverity(Severity.WARNING);
            nb.setText(NotificationUtils.getMessage(o).getFirst());
            builder.addNotifications(nb.build());
        }

        @Override
        public void error(Object... o) {
            Notification.Builder nb = Notification.newBuilder();
            nb.setSeverity(Severity.ERROR);
            nb.setText(NotificationUtils.getMessage(o).getFirst());
            builder.addNotifications(nb.build());
        }

        @Override
        public void debug(Object... o) {
            Notification.Builder nb = Notification.newBuilder();
            nb.setSeverity(Severity.DEBUG);
            nb.setText(NotificationUtils.getMessage(o).getFirst());
            builder.addNotifications(nb.build());
        }

        @Override
        public void send(Object... message) {
            // TODO Auto-generated method stub

        }

        @Override
        public Future<IMessage> ask(Object... message) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public IIdentity getIdentity() {
            return null;
        }

        @Override
        public boolean isInterrupted() {
            return false;
        }

        @Override
        public boolean hasErrors() {
            return false;
        }

        @Override
        public void post(Consumer<IMessage> handler, Object... message) {
            // TODO Auto-generated method stub

        }

        @Override
        public void addWait(int seconds) {
            // TODO improve with specific messages
            this.waitTime = seconds;
            warn("Please try this operation again in " + seconds + " seconds");
        }

        @Override
        public int getWaitTime() {
            // TODO Auto-generated method stub
            return this.waitTime;
        }
        
    }

    /**
     * Use this monitor to build a context that will notify any notification to the underlying
     * KlabData object.
     * 
     * @return
     */
    public IMonitor getMonitor() {
        return new Monitor();
    }

    public EncodingDataBuilder(EncodingDataBuilder root) {
        this.parent = root;
        this.builder = root.builder;
        this.stateBuilder = root.stateBuilder;
        this.objectBuilder = root.objectBuilder;
    }

    @Override
    public Builder startState(String name, String unit, IContextualizationScope scope) {
        EncodingDataBuilder ret = new EncodingDataBuilder(this);
        ret.stateBuilder = KlabData.State.newBuilder();
        ret.stateBuilder.setName(name);
        if (unit != null) {
            ret.stateBuilder.putMetadata("originalUnit", unit);
        }
        return ret;
    }

    @Override
    public Builder finishState() {
        if (this.parent.objectBuilder != null) {
            this.parent.objectBuilder.addStates(this.stateBuilder.build());
        } else {
            this.parent.builder.addStates(stateBuilder.build());
        }
        return this.parent;
    }

    @Override
    public Builder startObject(String artifactName, String objectName, IGeometry scale) {
        EncodingDataBuilder ret = new EncodingDataBuilder(this);
        ret.objectBuilder = KlabData.Object.newBuilder();
        ret.objectBuilder.setName(objectName);
        ret.objectBuilder.setGeometry(scale.encode());
        return ret;
    }

    @Override
    public Builder finishObject() {
        this.parent.builder.addObjects(this.objectBuilder.build());
        return this.parent;
    }

    @Override
    public Builder withMetadata(String property, Object object) {
        if (this.stateBuilder != null) {
            this.stateBuilder.putMetadata(property, object.toString());
        } else if (this.objectBuilder != null) {
            this.objectBuilder.putMetadata(property, object.toString());
        }
        return this;
    }

    @Override
    public Builder addNotification(INotification notification) {
        // TODO Auto-generated method stub
        return this;
    }

    public KlabData buildEncoded() {
        return builder.build();
    }

    @Override
    public IKlabData build() {
        throw new IllegalStateException("build() should not be called on an encoding builder");
    }

    @Override
    public void add(Object value) {

        if (this.stateBuilder == null) {
            this.stateBuilder = KlabData.State.newBuilder();
            this.stateBuilder.setName("result");
        }

        if (this.stateBuilder != null) {
            if (value instanceof Number) {
                this.stateBuilder.addDoubledata(((Number) value).doubleValue());
            } else if (value instanceof Boolean) {
                this.stateBuilder.addBooleandata((Boolean) value);
            } else if (value instanceof String) {
                this.stateBuilder.addTabledata(getTableValue((String) value, this.builder));
            }
        }
    }

    private int getTableValue(String value, KlabData.Builder builder) {
        // TODO update lookup table
        return 0;
    }

    @Override
    public void set(Object value, ILocator offset) {
        if (this.stateBuilder != null) {
            long index = -1;
            // TODO there should be a simpler way to turn a locator into an index, given
            // that this is a
            // universal feature of locators.
            if (offset instanceof Offset) {
                index = ((Offset) offset).linear;
            } else {
                throw new IllegalArgumentException("EncodingDataBuilder only accepts offset locators");
            }
            if (value instanceof Number) {
                this.stateBuilder.setDoubledata((int) index, ((Number) value).doubleValue());
            } else if (value instanceof Boolean) {
                this.stateBuilder.setBooleandata((int) index, (Boolean) value);
            } else if (value instanceof String) {
                this.stateBuilder.setTabledata((int) index, getTableValue((String) value, this.builder));
            }
        }
    }

    @Override
    public Builder withSemantics(IConcept semantics) {
        this.semantics = semantics;
        return this;
    }
}
