package org.integratedmodelling.klab.services.actors;

import java.io.Serializable;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.integratedmodelling.kactors.api.IKActorsBehavior.Ref;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.engine.IScope;
import org.integratedmodelling.klab.services.actors.messages.kactor.RunBehavior;

import io.reacted.core.config.reactors.ReActorConfig;
import io.reacted.core.messages.reactors.ReActorInit;
import io.reacted.core.messages.reactors.ReActorStop;
import io.reacted.core.reactors.ReActions;
import io.reacted.core.reactors.ReActor;
import io.reacted.core.reactorsystem.ReActorContext;
import io.reacted.core.reactorsystem.ReActorRef;

/**
 * The basic k.LAB actor is a simple object that can run a k.Actors behavior. The behavior runs
 * asynchronously in a separate VM that has a reference to the actor and the scope.
 * 
 * @author ferdinando villa
 *
 */
public class KAgent implements ReActor {

    /**
     * Max timeout for quick ask in seconds. May be changeable through configuration.
     */
    public static long DEFAULT_TIMEOUT_FOR_ASK = 2;

    private String name;
    private Map<String, Object> globalState = new HashMap<>();
//    private KActorsVM vm;
    private Ref self;

    public KAgent(String name) {
        this.name = name;
    }

    public static class KAgentRef implements Ref {

        private static final long serialVersionUID = -519986929796662952L;
        private ReActorRef ref;
        private static AtomicLong nextId = new AtomicLong(0);

        private KAgentRef(ReActorRef ref) {
            this.setRef(ref);
        }

        /**
         * Obtain a reference to an agent from the ReActor ref.
         * 
         * @param ref
         * @return
         */
        public static KAgentRef get(ReActorRef ref) {
            return new KAgentRef(ref);
        }

        @Override
        public <T extends Serializable> void tell(T message) {
            ref.tell(message);
        }

        @Override
        public <T extends Serializable, R extends Serializable> R ask(T message, Class<? extends R> responseClass) {
            return ask(message, responseClass, Duration.ofSeconds(DEFAULT_TIMEOUT_FOR_ASK));
        }

        @SuppressWarnings("unchecked")
        @Override
        public <T extends Serializable, R extends Serializable> R ask(T message, Class<? extends R> responseClass,
                Duration timeout) {

            boolean referencing = false;
            AtomicReference<R> result = new AtomicReference<>();

            /*
             * If we ask for a Ref, translate to a ReActorRef and reconvert at output, intercepting
             * NO_REACTOR_REF at empty check.
             */
            Class<? extends R> replyClass = responseClass;
            if (Ref.class.isAssignableFrom(responseClass)) {
                replyClass = (Class<? extends R>) ReActorRef.class;
                referencing = true;
            }
            
            final Class<? extends R> rClass = replyClass;
            this.ref.ask(message, replyClass, timeout, "request_" + nextId.incrementAndGet())
                    .whenComplete((reply, failure) -> {
                        if (reply != null && rClass.isAssignableFrom(reply.getClass())) {
                            result.set(reply);
                        }
                    }).toCompletableFuture().join();

            R ret = result.get();
            if (referencing && ret instanceof ReActorRef) {
                ret = (R) new KAgentRef((ReActorRef) ret);
            }

            return ret;
        }

        public ReActorRef getRef() {
            return ref;
        }

        public void setRef(ReActorRef ref) {
            this.ref = ref;
        }

        @Override
        public boolean isEmpty() {
            return ref == null || ref == ReActorRef.NO_REACTOR_REF;
        }
        
        @Override
        public String toString() {
            return isEmpty() ? "Ref[EMPTY]" : ref.toString();
        }

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
                .setReActorName(name);
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
        return ReActions.newBuilder().reAct(ReActorInit.class, this::initialize).reAct(ReActorStop.class, this::stop)
                .reAct(RunBehavior.class, this::runBehavior);
    }

    protected void run(IBehavior behavior, IScope scope) {
//        if (vm != null) {
//            // ? ehm
//        }
//        this.vm = new KActorsVM(self, scope, globalState);
//        this.vm.runBehavior(behavior, null, scope);
    }

    /*
     * ---- message handlers ---------------------------------------------
     */

    private void runBehavior(ReActorContext rctx, RunBehavior behavior) {

    }

    /**
     * Extend to provide initialization. MUST call super.initialize()!
     * 
     * @param rctx
     * @param message
     */
    protected void initialize(ReActorContext rctx, ReActorInit message) {
        this.self = new KAgentRef(rctx.getSelf());
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
