package org.integratedmodelling.klab.client.messaging;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;

import org.integratedmodelling.contrib.jgrapht.Graph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultDirectedGraph;
import org.integratedmodelling.contrib.jgrapht.graph.DefaultEdge;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status;
import org.integratedmodelling.klab.rest.ContextualizationNotification;
import org.integratedmodelling.klab.rest.ContextualizationNotification.Target;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ObservationChange;
import org.integratedmodelling.klab.rest.ObservationReference;
import org.integratedmodelling.klab.rest.TaskReference;

/**
 * A {@link ContextMonitor} that also connects tasks, dataflows and notifications to each context.
 * 
 * @author Ferd
 *
 */
public abstract class SessionMonitor extends ContextMonitor {

    private static final Logger logger = LoggerFactory.getLogger(SessionMonitor.class);
    
    static private Object lock = new Object();
    AtomicLong notificationCounter = new AtomicLong(0);

    public class ContextDescriptor {

        String contextId;
        ContextGraph observationGraph;

        // all notifications, tasks and observations go in a map
        Map<String, Object> beans = new HashMap<>();
        /*
         * we keep the structure here
         */
        Graph<String, DefaultEdge> structure = new DefaultDirectedGraph<>(DefaultEdge.class);
        // list of top-level tasks registered in order of appearance
        List<String> observationTasks = new ArrayList<>();
        ContextualizationNotification dataflow;

        public ObservationReference getRoot() {
            return (ObservationReference) beans.get(contextId);
        }

        public synchronized List<Object> getChildren() {
            List<Object> ret = new ArrayList<>();
            if (dataflow != null) {
                ret.add(dataflow);
            } else if (contextId != null) {
                ret.add(new ContextualizationNotification(contextId, ContextualizationNotification.Target.DATAFLOW));
            }
            for (String task : observationTasks) {
                ret.add(beans.get(task));
            }
            return ret;
        }

        public ContextGraph getStructure() {
            return observationGraph;
        }

        public synchronized List<Object> getChildren(String parentId, Level notificationLevel) {
            List<Object> ret = new ArrayList<>();
            if (parentId != null) {
                for (DefaultEdge edge : structure.incomingEdgesOf(parentId)) {
                    Object object = beans.get(structure.getEdgeSource(edge));
                    if (object instanceof Notification
                            && notificationLevel.intValue() > Level.parse(((Notification) object).getLevel()).intValue()) {
                        object = null;
                    }
                    if (object != null) {
                        ret.add(object);
                    }
                }
            }
            return ret;
        }

        synchronized Object getParent(String s) {
            Set<DefaultEdge> edges = structure.outgoingEdgesOf(s);
            if (edges.size() == 0) {
                return null;
            }
            return beans.get(structure.getEdgeTarget(edges.iterator().next()));
        }

        public synchronized Object getParent(Object element) {
            if (element instanceof ContextualizationNotification
                    || element instanceof ObservationReference && ((ObservationReference) element).getId().equals(contextId)) {
                return this;
            }
            if (element instanceof Notification) {
                return getParent(((Notification) element).getId());
            } else if (element instanceof ObservationReference) {
                return getParent(((ObservationReference) element).getId());
            } else if (element instanceof TaskReference) {
                return getParent(((TaskReference) element).getId());
            }
            return null;
        }
    }

    public interface Listener {
        /**
         * Called whenever an object is added to a task (the hierarchy is only made of tasks so the
         * parent is always a task). The added object may be a task, an observation or a
         * notification.
         * 
         * @param rootContext the root observation
         * @param added
         * @param objectParent
         */
        void onStructureChange(ObservationReference rootContext, Object added, TaskReference objectParent);

        /**
         * Called when the status of a task changes
         * 
         * @param rootContext
         * @param task
         * @param status
         */
        void onTaskStatusChange(ObservationReference rootContext, TaskReference task, Status status);

        /**
         * System notifications
         * 
         * @param notification
         */
        void onSystemNotification(Notification notification);

        /**
         * Called when the dataflow changes.
         * 
         * @param root
         */
        void onDataflowChange(ObservationReference root, ContextualizationNotification dataflow);
    }

    Map<String, ContextDescriptor> contexts = new HashMap<>();
    Map<String, ContextDescriptor> contextsByTask = new HashMap<>();
    List<Notification> systemNotifications = new ArrayList<>();

    List<Listener> listeners = new ArrayList<>();

    /*
     * temporary storage for tasks when they arrive w/o a context. Notification starts when the
     * context observation arrives and the task is moved to the correspondent context descriptor.
     */
    Map<String, TaskReference> orphanTasks = new HashMap<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public TaskReference register(TaskReference task) {
        synchronized (lock) {

            if (task.getRootContextId() == null) {
                // observation will come later, don't do anything for the time being.
                orphanTasks.put(task.getId(), task);
            } else {
                ContextDescriptor context = contexts.get(task.getRootContextId());
                if (context == null && task.getParentId() != null) {
                    context = contextsByTask.get(task.getParentId());
                }
                if (context == null) {
                    return null;
                }
                if (context.beans.containsKey(task.getId())) {
                    return (TaskReference) context.beans.get(task.getId());
                }
                context.beans.put(task.getId(), task);
                context.structure.addVertex(task.getId());
                if (task.getParentId() != null) {
                    context.structure.addEdge(task.getId(), task.getParentId());
                }
                contextsByTask.put(task.getId(), context);
                if (task.getParentId() == null) {
                    context.observationTasks.add(task.getId());
                }
            }

            return task;
        }
    }

    @Override
    protected void notifyObservation(ObservationReference observation) {

        synchronized (lock) {

            if (observation.getParentId() == null) {
                /*
                 * its task must exist; if it's a root observation, it's an orphan task
                 */
                TaskReference task = orphanTasks.get(observation.getTaskId());
                if (task != null) {
                    ContextDescriptor context = new ContextDescriptor();
                    context.observationGraph = this.getGraph(observation.getId());
                    // context.dataflowChanged = false;
                    context.structure.addVertex(observation.getId());
                    context.structure.addVertex(task.getId());
                    context.structure.addEdge(observation.getId(), task.getId());
                    context.beans.put(task.getId(), task);
                    context.beans.put(observation.getId(), observation);
                    context.contextId = observation.getId();
                    orphanTasks.remove(observation.getTaskId());
                    context.observationTasks.add(task.getId());
                    contextsByTask.put(task.getId(), context);
                    contexts.put(observation.getId(), context);
                    for (Listener listener : listeners) {
                        listener.onStructureChange((ObservationReference) context.beans.get(context.contextId), observation,
                                (TaskReference) context.beans.get(observation.getTaskId()));
                    }
                } else {
                    // internal error, shouldn't happen but might during debugging, just leaving
                    // this to triple-check it doesn't happen at production
                    logger.error("INTERNAL ERROR - ORPHAN TASK MISSING on " + observation);
                }

            } else {
                ContextDescriptor context = contexts.get(observation.getRootContextId());
                if (context != null && !context.beans.containsKey(observation.getId())) {
                    context.structure.addVertex(observation.getId());
                    context.structure.addEdge(observation.getId(), observation.getTaskId());
                    context.beans.put(observation.getId(), observation);
                    for (Listener listener : listeners) {
                        listener.onStructureChange((ObservationReference) context.beans.get(context.contextId), observation,
                                (TaskReference) context.beans.get(observation.getTaskId()));
                    }
                } else {
                    // internal error, shouldn't happen but might during debugging, just leaving
                    // this to triple-check it doesn't happen at production
                    logger.error("INTERNAL ERROR - CONTEXT MISSING on " + observation);
                }
            }
        }
    }

    @Override
    protected void notifyObservationChange(ObservationReference observation, ObservationChange change) {
    }

    public void register(Notification notification) {
        synchronized (lock) {
            if (notification.getIdentity().startsWith("t")) {
                ContextDescriptor context = contextsByTask.get(notification.getIdentity());
                if (context == null) {
                    // came before the context is set, like when resolving a model for it: just
                    // throw it away for now.
                    return;
                }
                if (context.beans.containsKey(notification.getId())) {
                    return;
                }
                context.beans.put(notification.getId(), notification);
                context.structure.addVertex(notification.getId());
                context.structure.addEdge(notification.getId(), notification.getIdentity());
                for (Listener listener : listeners) {
                    listener.onStructureChange((ObservationReference) context.beans.get(context.contextId), notification,
                            (TaskReference) context.beans.get(notification.getIdentity()));
                }
            } else if (notification.getIdentity().startsWith("o")) {
                // Ehm. Shouldn't happen but I don't feel like storing all observation IDs
                logger.error("GOT OBSERVATION NOTIFICATION " + notification);
            } else {
                systemNotifications.add(notification);
                for (Listener listener : listeners) {
                    listener.onSystemNotification(notification);
                }
            }
        }
    }

    public String getDataflow(String contextId) {
        synchronized (lock) {

        }
        return "";
    }

    ContextDescriptor findContext(Object bean) {
        String id = null;
        if (bean instanceof TaskReference) {
            ContextDescriptor ret = contexts.get(((TaskReference) bean).getRootContextId());
            if (ret == null) {
                id = ((TaskReference) bean).getId();
            }
        } else if (bean instanceof ObservationReference) {
            ContextDescriptor ret = contexts.get(((ObservationReference) bean).getRootContextId());
            if (ret == null) {
                id = ((ObservationReference) bean).getId();
            }
        } else if (bean instanceof Notification) {
            id = ((Notification) bean).getId();
        }

        if (id != null) {
            for (ContextDescriptor cd : contexts.values()) {
                if (cd.beans.containsKey(id)) {
                    return cd;
                }
            }
        }

        return null;
    }

    public void update(TaskReference task, Status status) {

        synchronized (lock) {
            ContextDescriptor context = contextsByTask.get(task.getId());
            if (context == null) {
                register(task);
                context = contextsByTask.get(task.getId());
            }
            if (context != null) {
                TaskReference tref = (TaskReference) context.beans.get(task.getId());
                if (tref == null) {
                    tref = register(task);
                }
                tref.setStatus(status);
                for (Listener listener : listeners) {
                    listener.onTaskStatusChange(context.getRoot(), tref, status);
                }
            }
        }
    }

    public void register(ContextualizationNotification dataflow) {
        synchronized (lock) {
            if (dataflow.getTarget() == Target.DATAFLOW) {
                ContextDescriptor context = contextsByTask.get(dataflow.getContextId());
                if (context != null) {
                    context.dataflow = dataflow;
                    for (Listener listener : listeners) {
                        listener.onDataflowChange(context.getRoot(), dataflow);
                    }
                }
            }
        }
    }

    public List<Notification> getSystemNotifications(Level logLevel) {
        List<Notification> ret = new ArrayList<>();
        synchronized (lock) {
            for (Notification notification : systemNotifications) {
                if (logLevel.intValue() <= Level.parse(notification.getLevel()).intValue()) {
                    ret.add(notification);
                }
            }
        }
        return ret;
    }

    public ContextDescriptor getContextDescriptor(ObservationReference rootContext) {
        return contexts.get(rootContext.getId());
    }

    public ContextDescriptor getContextDescriptor(String id) {
        return contexts.get(id);
    }

}
