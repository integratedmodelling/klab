package org.integratedmodelling.klab.services.scope;

import org.integratedmodelling.klab.api.data.IGeometry;
import org.integratedmodelling.klab.api.engine.IContextScope;
import org.integratedmodelling.klab.api.engine.ISessionScope;

public class SessionScope extends Scope implements ISessionScope {

    private Status status = Status.STARTED;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    SessionScope(Scope parent) {
        super(parent);
    }

    @Override
    public IGeometry getGeometry() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IContextScope createContext(String id) {

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
