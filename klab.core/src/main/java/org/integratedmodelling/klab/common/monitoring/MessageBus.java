package org.integratedmodelling.klab.common.monitoring;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.Iterator;
import java.util.Queue;

import org.integratedmodelling.kim.api.monitoring.IMessage;
import org.integratedmodelling.kim.api.monitoring.IMessageBus;

import com.google.common.collect.Queues;

public class MessageBus implements IMessageBus {

    /**
     * Per-thread queue of events to dispatch.
     */
    private final ThreadLocal<Queue<Event>> queue = new ThreadLocal<Queue<Event>>() {
        @Override
        protected Queue<Event> initialValue() {
            return Queues.newArrayDeque();
        }
    };

    /**
     * Per-thread dispatch state, used to avoid reentrant event dispatching.
     */
    private final ThreadLocal<Boolean> dispatching = new ThreadLocal<Boolean>() {
        @Override
        protected Boolean initialValue() {
            return false;
        }
    };

    void dispatch(IMessage event, Iterator<Receiver> subscribers) {

        checkNotNull(event);
        checkNotNull(subscribers);
        Queue<Event> queueForThread = queue.get();
        queueForThread.offer(new Event(event, subscribers));

        if (!dispatching.get()) {
            dispatching.set(true);
            try {
                Event nextEvent;
                while ((nextEvent = queueForThread.poll()) != null) {
                    while (nextEvent.subscribers.hasNext()) {
                        nextEvent.subscribers.next().message(nextEvent.event);
                    }
                }
            } finally {
                dispatching.remove();
                queue.remove();
            }
        }
    }

    private static final class Event {

        private final IMessage event;
        private final Iterator<Receiver> subscribers;

        private Event(IMessage event, Iterator<Receiver> subscribers) {
            this.event = event;
            this.subscribers = subscribers;
        }
    }

    @Override
    public void post(IMessage message) {
        Iterator<Receiver> eventSubscribers = getSubscribers(message);
        if (eventSubscribers.hasNext()) {
          dispatch(message, eventSubscribers);
        }
    }

    private Iterator<Receiver> getSubscribers(IMessage message) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void subscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unsubscribe(Receiver receiver, Object... filters) {
        // TODO Auto-generated method stub

    }

}
