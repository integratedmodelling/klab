package org.integratedmodelling.klab.engine.runtime;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.ISubject;
import org.integratedmodelling.klab.api.observations.scale.IScale;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IActivity;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.rest.DataflowState.Status;
import org.integratedmodelling.klab.utils.StringUtils;

public class ActivityBuilder {

    enum TargetIdentity {
        Agent, Dataflow, Actuator, Contextualizer, Resource
    }

    /**
     * Check if this has been accounted for (sent to a stat server). If accounted, some of our
     * children may still be unaccounted for.
     */
    private boolean accounted = false;

    String targetId;
    TargetIdentity type;
    String observable;
    long startTime = System.currentTimeMillis();
    long endTime;
    double totalTime;
    long totalContextTime;
    long scaleSize;
    List<ActivityBuilder> children = new ArrayList<>();
    Map<ObservedConcept, ActivityBuilder> actuators = new HashMap<>();
    Map<String, ActivityBuilder> contextualizers = new HashMap<>();
    Map<String, ActivityBuilder> resources = new HashMap<>();
    Status status = Status.WAITING;
    String contextId;
    String contextName;
    long scheduledSteps;

    private String contextCreated;

    public static ActivityBuilder root(IActorIdentity<?> actorIdentity) {
        return new ActivityBuilder(actorIdentity.getId(), TargetIdentity.Agent);
    }

    private ActivityBuilder(String targetId, TargetIdentity targetIdentity) {
        this.targetId = targetId;
        this.type = targetIdentity;
    }

    /**
     * Return a child builder for the passed target, which can be an observer agent (or session
     * representing the "god" agent), a dataflow, an actuator, a contextualizer or a resource. Each
     * scope should expose a builder made for the current target.
     * 
     * @param target
     * @return
     */
    public ActivityBuilder forTarget(Object... targets) {

        ActivityBuilder ret = null;
        IGeometry geometry = null;
        IObservable observable = null;
        IDirectObservation context = null;

        Object target = targets[0];
        for (int i = 1; i < targets.length; i++) {
            if (targets[i] instanceof IGeometry) {
                geometry = (IGeometry) targets[i];
            } else if (targets[i] instanceof IObservable) {
                observable = (IObservable) targets[i];
            } else if (targets[i] instanceof IDirectObservation) {
                context = (IDirectObservation) targets[i];
            }
        }

        if (target instanceof IActorIdentity) {
            ret = new ActivityBuilder(((IActorIdentity<?>) target).getId(), TargetIdentity.Agent);
        } else if (target instanceof IDataflow) {

            String name = observable == null ? ((IDataflow<?>) target).getId() : observable.getDefinition();
            ret = new ActivityBuilder(name, TargetIdentity.Dataflow);

        } else if (target instanceof IActuator) {

            ObservedConcept obs = new ObservedConcept(((Actuator) target).getObservable(), ((Actuator) target).getMode());
            if (this.actuators.containsKey(obs)) {
                ret = this.actuators.get(obs);
                if (ret.endTime > 0) {
                    ret.totalTime += (ret.endTime - ret.startTime);
                }
                ret.startTime = System.currentTimeMillis();
                ret.endTime = 0;
                return ret;
            }
            ret = new ActivityBuilder(((Actuator) target).getModel().getName(), TargetIdentity.Actuator);
            ret.observable = ((Actuator) target).getObservable().getDefinition();
            this.actuators.put(obs, ret);

        } else if (target instanceof AbstractContextualizer) {

            String ctxId = ((AbstractContextualizer) target).getPrototype().getName();
            if (this.contextualizers.containsKey(ctxId)) {
                ret = this.contextualizers.get(ctxId);
                if (ret.endTime > 0) {
                    ret.totalTime += (ret.endTime - ret.startTime);
                }
                ret.startTime = System.currentTimeMillis();
                ret.endTime = 0;
                return ret;
            }
            ret = new ActivityBuilder(((IContextualizer) target).getClass().getCanonicalName(), TargetIdentity.Contextualizer);
            this.contextualizers.put(ctxId, ret);

        } else if (target instanceof IResource) {

            String ctxId = ((IResource) target).getUrn();
            if (this.resources.containsKey(ctxId)) {
                ret = this.resources.get(ctxId);
                if (ret.endTime > 0) {
                    ret.totalTime += (ret.endTime - ret.startTime);
                }
                ret.startTime = System.currentTimeMillis();
                ret.endTime = 0;
                return ret;
            }
            ret = new ActivityBuilder(((IResource) target).getUrn(), TargetIdentity.Resource);
            this.resources.put(ctxId, ret);
        }

        if (ret == null) {
            throw new KlabIllegalArgumentException("internal: cannot create activity for target " + target);
        }

        if (observable != null) {
            ret.observable = observable.getDefinition();
        }
        if (geometry != null) {
            collectGeometryStatistics(geometry);
        }
        if (context != null) {
            ret.contextId = context.getId();
            ret.contextName = context.getName();
        } else {
            ret.contextId = this.contextId;
        }
        
        this.children.add(ret);

        return ret;
    }

    private void collectGeometryStatistics(IGeometry geometry) {
        scaleSize = geometry.size();
        if (geometry instanceof IScale) {

            // space statistics

            // time statistics

            // complexity of spatial shape

        }
    }

    /**
     * If this isn't called, start should be set to the time the builder is created.
     * 
     * @return
     */
    ActivityBuilder start() {
        this.startTime = System.currentTimeMillis();
        this.status = Status.STARTED;
        return this;
    }

    /**
     * Successful end. After this no more activity should be possible.
     * 
     * @return
     */
    public ActivityBuilder success() {
        this.endTime = System.currentTimeMillis();
        this.status = Status.FINISHED;
        return this;
    }

    public Status getStatus() {
        return status;
    }

    /**
     * Intentional interruption. After this no more activity should be possible.
     * 
     * @return
     */
    public ActivityBuilder interrupt() {
        this.endTime = System.currentTimeMillis();
        this.status = Status.INTERRUPTED;
        return this;
    }

    /**
     * Unintentional interruption for controlled causes (no exception). After this no more activity
     * should be possible.
     * 
     * @return
     */
    public ActivityBuilder error() {
        this.endTime = System.currentTimeMillis();
        this.status = Status.ABORTED;
        return this;
    }

    /**
     * Intentional interruption. After this no more activity should be possible.
     * 
     * @return
     */
    public ActivityBuilder exception(Throwable e) {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    ActivityBuilder withGeometry(IGeometry geometry) {
        return this;
    }

    double getTotalTimeSeconds() {
        return endTime == 0 ? Double.NaN : ((double) (endTime - startTime) + totalTime) / 1000.0;
    }

    @Override
    public String toString() {
        StringBuffer ret = new StringBuffer(512);
        dump(ret, 0);
        return ret.toString();
    }

    private void dump(StringBuffer buffer, int offset) {
        buffer.append(StringUtils.spaces(offset) + type + ": " + targetId);
        if (endTime > 0) {
            buffer.append(" (" + NumberFormat.getInstance().format(getTotalTimeSeconds()) + " s)");
        }
        buffer.append(" " + status + "\n");
        for (ActivityBuilder child : children) {
            child.dump(buffer, offset + 2);
        }
    }

    /**
     * Should throw an exception if none of the finish methods was called.
     * 
     * @return
     */
    IActivity build() {
        return null;
    }

    public boolean isAccounted() {
        return accounted;
    }

    public void setAccounted(boolean accounted) {
        this.accounted = accounted;
    }

    public ActivityBuilder schedulerStep() {
        scheduledSteps++;
        return this;
    }

    /**
     * Further specify the target for logging; called on root dataflow stats only.
     * 
     * @param observer
     */
    public void defineTarget(Object... target) {
        for (Object o : target) {
            if (o instanceof IAcknowledgement) {
                this.targetId = ((IAcknowledgement) o).getName() + " (" + ((IAcknowledgement) o).getObservable().getDefinition()
                        + ")";
            } else if (o instanceof String) {
                this.targetId = (String) o;
            } else if (o instanceof IDirectObservation) {
                this.contextId = ((IDirectObservation)o).getId();
            }
        }
    }

    public void notifyContextCreated(ISubject ret) {
        this.contextCreated = ret.getId();
    }
}
