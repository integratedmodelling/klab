package org.integratedmodelling.klab.services.scope;

import org.integratedmodelling.klab.api.geometry.KGeometry;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KContextScope;
import org.integratedmodelling.klab.api.knowledge.observation.scope.KSessionScope;

public class SessionScope extends Scope implements KSessionScope {

    private static final long serialVersionUID = -5840277560139759406L;

    private Status status = Status.STARTED;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    SessionScope(Scope parent) {
        super(parent);
    }

    @Override
    public KGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public KContextScope createContext(String id) {

        final ContextScope ret = new ContextScope(this);
        ret.setName(id);
        ret.setStatus(Status.WAITING);

//        CompletionStage<SessionAgent.ContextCreated> sessionFuture = AskPattern.ask(getAgent(),
//                replyTo -> new SessionAgent.CreateContext(id, ret, replyTo), Duration.ofSeconds(25),
//                EngineService.INSTANCE.getActorSystem().scheduler());
//
//        sessionFuture.whenComplete((reply, failure) -> {
//            if (reply instanceof SessionAgent.ContextCreated) {
//                ret.setAgent(reply.contextAgent);
//                ret.setToken(getToken() + "/" + id);
//                ret.setStatus(Status.STARTED);
//            } else {
//                ret.setStatus(Status.ABORTED);
//            }
//        });
//
//        sessionFuture.toCompletableFuture().join();

        return ret;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
