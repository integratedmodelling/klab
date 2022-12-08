package org.integratedmodelling.klab.engine.runtime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.groovy.parser.antlr4.GroovyParser.ThisFormalParameterContext;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.runtime.dataflow.IActuator;
import org.integratedmodelling.klab.api.runtime.dataflow.IDataflow;
import org.integratedmodelling.klab.api.runtime.monitoring.IActivity;
import org.integratedmodelling.klab.components.runtime.contextualizers.AbstractContextualizer;
import org.integratedmodelling.klab.dataflow.Actuator;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;

public class ActivityBuilder {

    enum TargetIdentity {
        Agent,
        Dataflow,
        Actuator,
        Contextualizer,
        Resource
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
    long totalTime;
    long totalContextTime;
    List<ActivityBuilder> children = new ArrayList<>();
    Map<ObservedConcept, ActivityBuilder> actuators = new HashMap<>();
    Map<String, ActivityBuilder> contextualizers = new HashMap<>();

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
    public ActivityBuilder forTarget(Object target) {

        ActivityBuilder ret = null;

        if (target instanceof IActorIdentity) {
            ret = new ActivityBuilder(((IActorIdentity<?>) target).getId(), TargetIdentity.Agent);
        } else if (target instanceof IDataflow) {
            ret = new ActivityBuilder(((IDataflow<?>) target).getId(), TargetIdentity.Dataflow);
        } else if (target instanceof IActuator) {

            ObservedConcept obs = new ObservedConcept(((Actuator) target).getObservable(),
                    ((Actuator) target).getMode());
            if (this.actuators.containsKey(obs)) {
                ret = this.actuators.get(obs);
                ret.totalContextTime += (ret.endTime - ret.startTime);
                return ret;
            }
            ret = new ActivityBuilder(((Actuator) target).getModel().getName(), TargetIdentity.Actuator);
            ret.observable = ((Actuator) target).getObservable().getDefinition();
            this.actuators.put(obs, ret);
            return ret;

        } else if (target instanceof AbstractContextualizer) {

            String ctxId = ((AbstractContextualizer) target).getPrototype().getName();
            if (this.contextualizers.containsKey(ctxId)) {
                ret = this.contextualizers.get(ctxId);
                ret.totalContextTime += (ret.endTime - ret.startTime);
                return ret;
            }
            ret = new ActivityBuilder(((IContextualizer) target).getClass().getCanonicalName(),
                    TargetIdentity.Contextualizer);
            this.contextualizers.put(ctxId, ret);
            return ret;

        } else if (target instanceof IResource) {
            ret = new ActivityBuilder(((IResource) target).getUrn(), TargetIdentity.Resource);
        }

        if (ret == null) {
            throw new KlabIllegalArgumentException("internal: cannot create activity for target " + target);
        }

        this.children.add(ret);

        return ret;
    }

    /**
     * If this isn't called, start should be set to the time the builder is created.
     * 
     * @return
     */
    ActivityBuilder start() {
        this.startTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Successful end. After this no more activity should be possible.
     * 
     * @return
     */
    ActivityBuilder success() {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Intentional interruption. After this no more activity should be possible.
     * 
     * @return
     */
    ActivityBuilder interrupt() {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Unintentional interruption for controlled causes (no exception). After this no more activity
     * should be possible.
     * 
     * @return
     */
    ActivityBuilder error() {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    /**
     * Intentional interruption. After this no more activity should be possible.
     * 
     * @return
     */
    ActivityBuilder exception(Throwable e) {
        this.endTime = System.currentTimeMillis();
        return this;
    }

    ActivityBuilder withGeometry(IGeometry geometry) {
        return this;
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
}
