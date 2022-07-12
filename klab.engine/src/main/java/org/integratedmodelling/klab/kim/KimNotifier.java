package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimAcknowledgement;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.api.IKimTable;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IViewModel;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.data.table.StructuredTable;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.ConceptStatement;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.KimKnowledgeProcessor;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.rest.CompileNotificationReference;
import org.integratedmodelling.klab.rest.NamespaceCompilationResult;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;

public class KimNotifier implements Kim.Notifier {

    private IMonitor monitor;

    /*
     * wraps the normal monitor to collect all logical errors, to be later sent to any subscribing
     * listener in one shot at the end of validation.
     */
    public class ErrorNotifyingMonitor extends Engine.Monitor {

        INamespace namespace;
        IKimScope mainScope;
        List<ICompileNotification> notifications;

        ErrorNotifyingMonitor(Monitor monitor, INamespace namespace) {
            super(monitor);
            this.namespace = namespace;
            this.notifications = new ArrayList<>();
            // if (namespace.getStatement().getLoader() != null) {
            // // null in scripts that weren't loaded by the loader.
            // this.notifications.addAll(namespace.getStatement().getLoader().getIssues(namespace));
            // }
        }

        public ErrorNotifyingMonitor(ErrorNotifyingMonitor monitor) {
            super(monitor);
            this.namespace = monitor.namespace;
            this.notifications = monitor.notifications;
        }

        public ErrorNotifyingMonitor contextualize(IKimScope statement) {
            ErrorNotifyingMonitor ret = new ErrorNotifyingMonitor(this);
            ret.mainScope = statement;
            return ret;
        }

        private Object[] scanArguments(Level level, Object[] args) {
            List<Object> savedArgs = new ArrayList<>();
            IKimStatement statement = null;
            String message = null;
            String observableUrn = null;
            for (Object o : args) {
                if (o instanceof IKimStatement) {
                    statement = (IKimStatement) o;
                } else if (o instanceof Throwable) {
                    message = NotificationUtils.getMessage(o).getFirst();
                    savedArgs.add(o);
                } else if (o instanceof String) {
                    message = (String) o;
                    savedArgs.add(o);
                } else if (o instanceof Observable) {
                    observableUrn = ((Observable) o).getUrl();
                } else if (o instanceof INotification) {
                    level = Level.parse(((INotification) o).getLevel());
                    message = ((INotification) o).getMessage();
                } else {
                    savedArgs.add(o);
                }
            }

            if (statement != null || observableUrn != null) {
                CompileNotification notification = CompileNotification.create(level, message, namespace.getName(), statement);
                notification.setMainScope(mainScope);
                notifications.add(notification);
                notification.setObservableUrn(observableUrn);
            }

            return statement == null ? args : savedArgs.toArray();
        }

        @Override
        public void info(Object... info) {
            super.info(scanArguments(Level.INFO, info));
        }

        @Override
        public void warn(Object... o) {
            super.warn(scanArguments(Level.WARNING, o));
        }

        @Override
        public void error(Object... o) {
            super.error(scanArguments(Level.SEVERE, o));
        }

        @Override
        public void debug(Object... o) {
            super.debug(scanArguments(Level.FINE, o));
        }

        public void sendReport() {

            String project = namespace.getProject() == null ? null : namespace.getProject().getName();
            List<CompileNotificationReference> nrefs = new ArrayList<>();
            for (ICompileNotification notification : notifications) {
                CompileNotificationReference cref = ((CompileNotification) notification).getReference();
                if (project != null && cref.getStatementUrn() != null) {
                    // cut the URN before the project name so that we can recognize it across
                    // platforms
                    int pidx = cref.getStatementUrn().indexOf(project);
                    if (pidx >= 0) {
                        cref.setStatementUrn(cref.getStatementUrn().substring(pidx));
                    }
                }
                nrefs.add(cref);
            }

            /*
             * Keep these around and send to subscribers
             */
            NamespaceCompilationResult result = new NamespaceCompilationResult(namespace.getName(), namespace.isPublishable(),
                    nrefs);
            Kim.INSTANCE.updateErrors(result);
            send(IMessage.MessageClass.KimLifecycle, IMessage.Type.NamespaceCompilationIssues, result);
        }

    }

    /*
     * holds the mapping between the actual ontology ID and the declared one in root domains where
     * "import <coreUrl> as <prefix>" was used.
     */
    Map<String, String> corePrefixTranslation = new HashMap<>();

    public KimNotifier(IMonitor monitor) {
        this.monitor = monitor;
    }

    public KimNotifier with(IMonitor monitor) {
        return new KimNotifier(monitor, this);
    }

    private KimNotifier(IMonitor monitor, KimNotifier notifier) {
        this.monitor = monitor;
        corePrefixTranslation.putAll(notifier.corePrefixTranslation);
    }

    @Override
    public INamespace synchronizeNamespaceWithRuntime(IKimNamespace namespace) {

        Namespaces.INSTANCE.release(namespace.getName(), monitor);
        Namespace ns = new Namespace(namespace);
        Namespaces.INSTANCE.registerNamespace(ns, monitor);
        ErrorNotifyingMonitor monitor = new ErrorNotifyingMonitor((Monitor) this.monitor, ns);

        for (Pair<String, String> imp : namespace.getOwlImports()) {
            String prefix = Resources.INSTANCE.getUpperOntology().importOntology(imp.getFirst(), imp.getSecond());
            if (prefix == null) {
                monitor.error("cannot resolve import " + imp.getFirst(), namespace);
            } else {
                corePrefixTranslation.put(imp.getSecond(), prefix);
            }
        }

        /*
         * these should never throw exceptions; instead they should notify any errors, no matter how
         * internal, through the monitor.
         * 
         * Indexing is called only if the objects are not private, which includes anything in
         * scripts.
         * 
         * TODO all these must pass the namespace as the "fallback" ontology to anything that
         * creates concepts. The fallback must happen every time, always checking if each derived
         * concept can be built in a stable namespace.
         */
        for (IKimScope statement : namespace.getChildren()) {

            IKimObject object = null;

            if (statement instanceof IKimSymbolDefinition) {

                Object value = ((IKimSymbolDefinition) statement).getValue();
                String name = ((IKimSymbolDefinition) statement).getName();

                if (((IKimSymbolDefinition) statement).getDefineClass() != null) {
                    try {
                        value = Extensions.INSTANCE.processDefinition(((IKimSymbolDefinition) statement), value, ns, monitor);
                        if (value instanceof IViewModel) {
                            ns.getKnowledgeViews().put(name, (IViewModel) value);
                            ns.addObject((IViewModel) value);
                        }
                    } catch (Throwable t) {
                        monitor.error("error processing " + ((IKimSymbolDefinition) statement).getDefineClass() + "  definition: "
                                + t.getMessage(), statement);
                    }

                } else if (value instanceof IKimTable) {
                    value = StructuredTable.create((IKimTable) value);
                }

                ns.getSymbolTable().put(name, value);

            } else if (statement instanceof IKimConceptStatement) {
                object = new ConceptStatement((IKimConceptStatement) statement);
                IConcept concept = KimKnowledgeProcessor.INSTANCE.build((IKimConceptStatement) statement, ns,
                        (ConceptStatement) object, monitor.contextualize(statement));
                if (concept == null) {
                    object = null;
                } else {
                    Concepts.INSTANCE.index((IKimConceptStatement) statement, namespace.getName(), monitor);
                }
            } else if (statement instanceof IKimModel) {
                try {
                    object = Model.create((IKimModel) statement, ns, monitor.contextualize(statement));
                } catch (KlabException e) {
                    monitor.error("error creating model " + ((IKimModel) statement).getName() + ": " + e.getMessage(), statement);
                }
                if (object instanceof IModel) {
                    try {
                        /*
                         * validate units vs. coverage and ensure that they're coherent with the
                         * geometry. Learning models don't get to play.
                         */
                        if (!((Model) object).isErrors() && !((Model) object).isLearning() && !((Model) object).isInactive()) {
                            Models.INSTANCE.index((IModel) object, monitor);
                        }
                    } catch (KlabException e) {
                        monitor.error("error storing valid model " + ((IModel) object).getName() + ": " + e.getMessage());
                    }
                }
            } else if (statement instanceof IKimAcknowledgement) {
                object = ObservationBuilder.INSTANCE.build((IKimAcknowledgement) statement, ns,
                        (Monitor) monitor.contextualize(statement));
                if (object instanceof IAcknowledgement) {
                    try {
                        Observations.INSTANCE.index((IAcknowledgement) object, monitor);
                    } catch (KlabException e) {
                        monitor.error(
                                "error storing valid observation " + ((IAcknowledgement) object).getName() + ": " + e.getMessage());
                    }
                }
            }

            if (object != null) {
                ns.addObject(object);
            }
        }

        Observations.INSTANCE.registerNamespace(ns, (Monitor) monitor);
        Reasoner.INSTANCE.addOntology(ns.getOntology());

        /*
         * store and report errors or lack thereof (so that previous errors can be removed)
         */
        monitor.sendReport();

        /*
         * Execute any annotations recognized by the engine.
         */
        for (IKimObject object : ns.getObjects()) {
            for (IAnnotation annotation : object.getAnnotations()) {
                Annotations.INSTANCE.process(annotation, object, monitor);
            }
        }

        return ns;
    }

}
