package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.KimNotifier;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.persistence.ModelKbox;
import org.integratedmodelling.klab.resolution.ResolutionScope;
import org.integratedmodelling.klab.rest.ModelReference;
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
	// Map<String, Integer> recheckModelNS = new HashMap<>();

	private Models() {
		IInjectorProvider injectorProvider = new KimInjectorProvider();
		Injector injector = injectorProvider.getInjector();
		if (injector != null) {
			injector.injectMembers(this);
		}
		this.kbox = ModelKbox.create(KBOX_NAME);
		Services.INSTANCE.registerService(this, IModelService.class);
	}

	@Override
	public INamespace load(URL url, IMonitor monitor) throws KlabException {
		try (InputStream stream = url.openStream()) {
			return load(url.toURI(), stream, monitor);
		} catch (Exception e) {
			throw e instanceof KlabException ? (KlabException) e : new KlabIOException(e);
		}
	}

	@Override
	public INamespace load(File file, IMonitor monitor) throws KlabException {
		try (InputStream stream = new FileInputStream(file)) {
			return load(file.toURI(), stream, monitor);
		} catch (Exception e) {
			throw e instanceof KlabException ? (KlabException) e : new KlabIOException(e);
		}
	}

	public Namespace load(URI uri, InputStream input, IMonitor monitor) throws KlabException {

		Namespace ret = null;
		try {
			String definition = IOUtils.toString(input);
			Model model = modelParser.parse(definition);

			if (model != null && model.getNamespace() != null) {
				// treat as orphan
				Kim.INSTANCE.eraseOrphanNamespace(uri.toString());
				model.getNamespace().eResource().setURI(org.eclipse.emf.common.util.URI.createURI(uri.toString()));
				List<Issue> issues = validator.validate(model.eResource(), CheckMode.ALL, CancelIndicator.NullImpl);
				for (Issue issue : issues) {
					if (issue.getSeverity() == Severity.ERROR) {
						Kim.INSTANCE.reportLibraryError(issue);
					}
				}

				// recover the namespace that was parsed
				IKimNamespace namespace = Kim.INSTANCE/* .getCommonProject() */
						.getNamespace(model.getNamespace());

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

	// @Override
	public void releaseNamespace(String namespaceId, IMonitor monitor) throws KlabException {
		/* int cmodel = */kbox.remove(namespaceId, monitor);
		// if (cmodel > 0) {
		// recheckModelNS.put(namespace.getName(), cmodel);
		// }
	}

	// @Override
	public void index(IModel model, IMonitor monitor) throws KlabException {

		// wrong models don't get indexed; non-semantic models do (as private)
		if (model.getStatement().isErrors() || model.getObservables().size() == 0) {
			return;
		}

		kbox.store(model, monitor);
		if (!model.isPrivate()) {
			Indexer.INSTANCE.index(model.getStatement(), model.getNamespace().getName());
		}
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

		// Integer storingNamespace = recheckModelNS.remove(namespace.getId());
		// if (storingNamespace != null && storingNamespace > 0 &&
		// (namespace.getProject() == null || !(namespace.getProject().isRemote()))) {
		// try {
		kbox.store(namespace, monitor);
		// } catch (Exception e) {
		// monitor.error("error storing namespace", e);
		// }
		// }
	}

	/**
	 * Simplest query for any model observing the passed concept. Should only be
	 * used for transformations or other commodity models. May be removed after the
	 * resolver can deal with these quickly and more flexibly.
	 * 
	 * @param trait
	 * @return the "best" model or null. There's no ranking so no need to choose
	 *         from a list.
	 */
	public IModel resolve(IConcept trait, IResolutionScope scope) {
		List<IRankedModel> ret = kbox.query(Observable.promote(trait), (ResolutionScope)scope);
		return ret.isEmpty() ? null : ret.get(0);
	}

    public List<ModelReference> listModels(boolean sort) {
        List<ModelReference> ret = kbox.retrieveAll(Klab.INSTANCE.getRootMonitor());
        if (sort) {
            ret.sort(new Comparator<ModelReference>() {

                @Override
                public int compare(ModelReference o1, ModelReference o2) {
                    return o1.getUrn().compareTo(o2.getUrn());
                }
            });
        }
        return ret;
    }

	public ModelReference getModelReference(String string) {
		return kbox.retrieveModel(string, Klab.INSTANCE.getRootMonitor());
	}

	public ModelKbox getKbox() {
		return kbox;
	}

}
