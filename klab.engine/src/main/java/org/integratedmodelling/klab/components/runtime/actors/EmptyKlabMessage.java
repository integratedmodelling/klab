package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Collections;
import java.util.List;

import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage.Semaphore.Type;

public class EmptyKlabMessage implements KlabMessage {

    @Override
    public KlabMessage direct() {
        return this;
    }

    @Override
    public KlabMessage withSemaphore(Semaphore semaphor) {
        return this;
    }

    @Override
    public List<Semaphore> getSemaphores(Type type) {
        return Collections.emptyList();
    }

}
