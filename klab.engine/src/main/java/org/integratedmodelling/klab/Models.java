package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.Notifier;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.KimNotifier;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.persistence.ModelKbox;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.utils.xtext.KimInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Models implements IModelService {

    INSTANCE;

    private static final String KBOX_NAME = "models";

    @Inject
    ParseHelper<Model> modelParser;

    @Inject
    IResourceValidator validator;

    /*
     * index for local models
     */
    private ModelKbox kbox = null;
    Map<String, Integer> recheckModelNS = new HashMap<>();

    private Models() {
        IInjectorProvider injectorProvider = new KimInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
        this.kbox = ModelKbox.create(KBOX_NAME);
    }

    @Override
    public INamespace load(URL url, IMonitor monitor) throws KlabException {
        try (InputStream stream = url.openStream()) {
            return load(stream, monitor);
        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException) e : new KlabIOException(e);
        }
    }

    @Override
    public INamespace load(File file, IMonitor monitor) throws KlabException {
        try (InputStream stream = new FileInputStream(file)) {
            return load(stream, monitor);
        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException) e : new KlabIOException(e);
        }
    }

    @Override
    public Namespace load(InputStream input, IMonitor monitor) throws KlabException {

        Namespace ret = null;
        try {
            String definition = IOUtils.toString(input);
            Model model = modelParser.parse(definition);

            if (model != null && model.getNamespace() != null) {
                List<Issue> issues = validator.validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
                for (Issue issue : issues) {
                    if (issue.getSeverity() == Severity.ERROR) {
                        Kim.INSTANCE.reportLibraryError(issue);
                    }
                }

                // recover the namespace that was parsed
                IKimNamespace namespace = Kim.INSTANCE.getCommonProject()
                        .getNamespace(EcoreUtil.getURI(model.getNamespace()), model.getNamespace(), true);

                if (namespace != null) {
                    for (Notifier notifier : Kim.INSTANCE.getNotifiers()) {
                        if (notifier instanceof KimNotifier) {
                            ret = (Namespace) ((KimNotifier) notifier).with((Monitor) monitor)
                                    .synchronizeNamespaceWithRuntime(namespace);
                        }
                    }
                }
            }

        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException) e : new KlabValidationException(e);
        }
        return ret;
    }

    @Override
    public void releaseNamespace(INamespace namespace, IMonitor monitor) throws KlabException {
        int cmodel = kbox.removeIfOlder(namespace, monitor);
        if (cmodel > 0) {
            recheckModelNS.put(namespace.getName(), cmodel);
        }
    }

    @Override
    public void index(IModel model, IMonitor monitor) throws KlabException {
        kbox.store(model, monitor);
    }

    @Override
    public List<IRankedModel> resolve(IObservable observable, IResolutionScope scope) throws KlabException {
        return kbox.query(observable, (ResolutionScope) scope);
    }

    /*
     * Non-API - finalize namespace storage in kbox for proper synchronization
     * 
     * @param namespace
     * 
     * @param monitor
     */
    public void finalizeNamespace(INamespace namespace, IMonitor monitor) {

        Integer storingNamespace = recheckModelNS.remove(namespace.getId());
        if (storingNamespace != null && storingNamespace > 0 && !(namespace.getProject().isRemote())) {
            try {
                kbox.store(namespace, monitor);
            } catch (Exception e) {
                monitor.error("error storing namespace", e);
            }
        }
    }

}
