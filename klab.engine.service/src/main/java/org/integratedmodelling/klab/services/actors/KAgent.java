package org.integratedmodelling.klab.services.actors;

import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.services.actors.messages.kactor.RunBehavior;

import io.reacted.core.config.reactors.ReActorConfig;
import io.reacted.core.reactors.ReActions;
import io.reacted.core.reactors.ReActions.Builder;
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

    public KAgent(String name) {
        this.name = name;
    }

    @Override
    public ReActorConfig getConfig() {
        return configure().build();
    }

    /**
     * Extend this (call super.configure()!) for further configuration
     * @return
     */
    protected ReActorConfig.Builder configure() {
        return ReActorConfig.newBuilder().setReActorName(name);
    }

    @Override
    public ReActions getReActions() {
        return setBehavior().build();
    }

    /**
     * Extend this (call super.setBehavior()!) to handle more messages
     * @return
     */
    protected ReActions.Builder setBehavior() {
        return ReActions.newBuilder().reAct(RunBehavior.class, this::runBehavior);
    }

    /*
     * ---- message handlers ---------------------------------------------
     */
    
    private void runBehavior(ReActorContext rctx, RunBehavior behavior) {
        
    }

}
