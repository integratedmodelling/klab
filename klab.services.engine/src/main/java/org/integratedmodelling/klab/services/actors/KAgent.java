package org.integratedmodelling.klab.services.actors;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.components.runtime.actors.vm.KActorsVM;
import org.integratedmodelling.klab.services.actors.messages.kactor.RunBehavior;

import io.reacted.core.config.reactors.ReActorConfig;
import io.reacted.core.messages.reactors.ReActorInit;
import io.reacted.core.messages.reactors.ReActorStop;
import io.reacted.core.reactors.ReActions;
import io.reacted.core.reactors.ReActor;
import io.reacted.core.reactorsystem.ReActorContext;

/**
 * The basic k.LAB actor is a simple object that can run a k.Actors behavior. The behavior runs
 * asynchronously in a separate VM that has a reference to the actor and the scope.
 * 
 * @author ferdinando villa
 *
 */
public class KAgent implements ReActor {

    private String name;
    private Map<String, Object> globalState = new HashMap<>();
    private KActorsVM vm;
    
    public KAgent(String name) {
        this.name = name;
    }

    @Override
    public ReActorConfig getConfig() {
        return configure().build();
    }

    /**
     * Extend this (call super.configure()!) for further configuration
     * 
     * @return
     */
    protected ReActorConfig.Builder configure() {
        return ReActorConfig.newBuilder()
                .setReActorName(name)/*
                                      * .setTypedSubscriptions(TypedSubscriptionPolicy.FULL.forType(
                                      * KlabException.class))
                                      */;
    }

    @Override
    public ReActions getReActions() {
        return setBehavior().build();
    }

    /**
     * Extend this (call super.setBehavior()!) to handle more messages
     * 
     * @return
     */
    protected ReActions.Builder setBehavior() {
        return ReActions.newBuilder()
                .reAct(ReActorInit.class, this::initialize)
                .reAct(ReActorStop.class, this::stop)
                .reAct(RunBehavior.class, this::runBehavior);
    }

    protected void run(IBehavior behavior, IScope scope) {
        if (vm != null) {
            // ? ehm
        }
    }
    
    
    /*
     * ---- message handlers ---------------------------------------------
     */

    private void runBehavior(ReActorContext rctx, RunBehavior behavior) {
        
    }
    
    /**
     * Extend to provide initialization
     * 
     * @param rctx
     * @param message
     */
    protected void initialize(ReActorContext rctx, ReActorInit message) {
        
    }

    /**
     * Extend to provide finalization
     * 
     * @param rctx
     * @param message
     */
    protected void stop(ReActorContext rctx, ReActorStop message) {
        
    }

}
