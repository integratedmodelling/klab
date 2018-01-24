package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

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
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.services.IModelService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.xtext.KimInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

public enum Models implements IModelService {

    INSTANCE;

    @Inject
    ParseHelper<Model> modelParser;

    @Inject
    IResourceValidator validator;

    private Models() {
        IInjectorProvider injectorProvider = new KimInjectorProvider();
        Injector injector = injectorProvider.getInjector();
        if (injector != null) {
            injector.injectMembers(this);
        }
    }

    @Override
    public INamespace load(URL url) throws KlabException {
        try (InputStream stream = url.openStream()) {
            return load(stream);
        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException)e : new KlabIOException(e);
        }
    }

    @Override
    public INamespace load(File file) throws KlabException {
        try (InputStream stream = new FileInputStream(file)) {
            return load(stream);
        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException)e : new KlabIOException(e);
        }
    }

    @Override
    public INamespace load(InputStream input) throws KlabException {
        INamespace ret = null;
        try {
            String definition = IOUtils.toString(input);
            Model model = modelParser.parse(definition);

            if (model != null && model.getNamespace() != null) {
                List<Issue> issues = validator
                        .validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
                for (Issue issue : issues) {
                    if (issue.getSeverity() == Severity.ERROR) {
                        Kim.INSTANCE.reportLibraryError(issue);
                    }
                }

                // recover the namespace that was parsed
                IKimNamespace namespace = Kim.INSTANCE.getCommonProject()
                        .getNamespace(EcoreUtil.getURI(model.getNamespace()), model.getNamespace(), true);
                
                if (namespace != null && Kim.INSTANCE.getValidator() != null) {
                    ret = (INamespace) Kim.INSTANCE.getValidator().synchronizeNamespaceWithRuntime(namespace);
                }
            }

        } catch (Exception e) {
            throw e instanceof KlabException ? (KlabException)e : new KlabValidationException(e);
        }
        return ret;
    }

    @Override
    public void releaseNamespace(String name) {
        // TODO remove all artifacts from local kbox
    }

    @Override
    public void index(IModel model) {

    }

    @Override
    public List<RankedModel> resolve(IObservable observable, IResolutionScope scope) {
        // TODO Auto-generated method stub
        return null;
    }

}
