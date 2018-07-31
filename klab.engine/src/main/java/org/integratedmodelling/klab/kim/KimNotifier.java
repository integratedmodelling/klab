package org.integratedmodelling.klab.kim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.integratedmodelling.kim.api.IKimConceptStatement;
import org.integratedmodelling.kim.api.IKimModel;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimObserver;
import org.integratedmodelling.kim.api.IKimScope;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Concepts;
import org.integratedmodelling.klab.Models;
import org.integratedmodelling.klab.Namespaces;
import org.integratedmodelling.klab.Observations;
import org.integratedmodelling.klab.Reasoner;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.engine.Engine;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.model.ConceptStatement;
import org.integratedmodelling.klab.model.Model;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.KimKnowledgeProcessor;
import org.integratedmodelling.klab.rest.CompilationResult;
import org.integratedmodelling.klab.rest.CompileNotificationReference;
import org.integratedmodelling.klab.utils.NotificationUtils;
import org.integratedmodelling.klab.utils.Pair;

public class KimNotifier implements Kim.Notifier {

    private IMonitor monitor;

    /*
     * wraps the normal monitor to collect all logical errors to be later sent to
     * any subscribing UI in one shot at the end of validation.
     */
    private class ErrorNotifyingMonitor extends Engine.Monitor {

        INamespace namespace;
        List<ICompileNotification> notifications = new ArrayList<>();

        ErrorNotifyingMonitor(Monitor monitor, INamespace namespace) {
            super(monitor);
            this.namespace = namespace;
            if (namespace.getStatement().getLoader() != null) {
            	// null in scripts that weren't loaded by the loader.
            	this.notifications.addAll(namespace.getStatement().getLoader().getIssues(namespace));
            }
        }

        private Object[] scanArguments(Level level, Object[] args) {
            List<Object> savedArgs = new ArrayList<>();
            IKimStatement statement = null;
            String message = null;
            for (Object o : args) {
                if (o instanceof IKimStatement) {
                    statement = (IKimStatement) o;
                } else if (o instanceof Throwable) {
                    message = NotificationUtils.getMessage(o);
                    savedArgs.add(o);
                } else if (o instanceof String) {
                    message = (String) o;
                    savedArgs.add(o);
                } else {
                    savedArgs.add(o);
                }

                if (statement != null) {
                    notifications.add(CompileNotification.create(level, message, namespace.getName(), statement));
                }

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

            List<CompileNotificationReference> nrefs = new ArrayList<>();
            for (ICompileNotification notification : notifications) {
                nrefs.add(((CompileNotification) notification).getReference());
            }
            send(IMessage.MessageClass.KimLifecycle, IMessage.Type.NamespaceCompilationIssues,
                    new CompilationResult(namespace.getName(), nrefs));
        }

    }

    /*
     * holds the mapping between the actual ontology ID and the declared one in root
     * domains where "import <coreUrl> as <prefix>" was used.
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
         * these should never throw exceptions; instead they should notify any errors,
         * no matter how internal, through the monitor.
         * 
         * Indexing is called only if the objects are not private, which includes anything 
         * in scripts.
         */
        for (IKimScope statement : namespace.getChildren()) {

            IKimObject object = null;

            if (statement instanceof IKimConceptStatement) {
                object = new ConceptStatement((IKimConceptStatement) statement);
                IConcept concept = KimKnowledgeProcessor.INSTANCE.build((IKimConceptStatement) statement, ns,
                        (ConceptStatement) object, monitor);
                if (concept == null) {
                    object = null;
                } else {
                    Concepts.INSTANCE.index((IKimConceptStatement) statement, namespace.getName(), monitor);
                }
            } else if (statement instanceof IKimModel) {
                object = Model.create((IKimModel) statement, ns, monitor);
                if (object instanceof IModel) {
                    try {
                        Models.INSTANCE.index((IModel) object, monitor);
                    } catch (KlabException e) {
                        monitor.error(
                                "error storing valid model " + ((IModel) object).getName() + ": " + e.getMessage());
                    }
                }
            } else if (statement instanceof IKimObserver) {
                object = ObservationBuilder.INSTANCE.build((IKimObserver) statement, ns, (Monitor) monitor);
                if (object instanceof IObserver) {
                    try {
                        Observations.INSTANCE.index((IObserver) object, monitor);
                    } catch (KlabException e) {
                        monitor.error(
                                "error storing valid model " + ((IObserver) object).getName() + ": " + e.getMessage());
                    }
                }
            }

            if (object != null) {
                ns.addObject(object);
            }
        }

        /*
         * TODO finalize namespace, send any notification
         */
        Namespaces.INSTANCE.registerNamespace(ns, monitor);
        Observations.INSTANCE.registerNamespace(ns, (Monitor) monitor);

        Reasoner.INSTANCE.addOntology(ns.getOntology());

        /*
         * report errors or lack thereof (so that previous errors can be removed)
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
