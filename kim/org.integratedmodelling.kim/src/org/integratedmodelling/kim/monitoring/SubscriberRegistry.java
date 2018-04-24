package org.integratedmodelling.kim.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.kim.api.monitoring.IMessage;
import org.integratedmodelling.kim.api.monitoring.IMessageBus.Receiver;

public class SubscriberRegistry {

    public Collection<Receiver> getSubscribers(IMessage message) {
        List<Receiver> ret = new ArrayList<>();
        return ret;
    }

    public void subscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub
    }

    public void unsubscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub

    }
}
