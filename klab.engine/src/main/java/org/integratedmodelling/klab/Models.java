package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
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
import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimNamespace;
import org.integratedmodelling.kim.api.IKimStatement.Scope;
import org.integratedmodelling.kim.kim.Model;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.Notifier;
import org.integratedmodelling.klab.api.data.IGeometry.Dimension;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.resolution.IResolutionScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IModelService;
import org.integratedmodelling.klab.dataflow.ObservedConcept;
import org.integratedmodelling.klab.engine.Engine.Monitor;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.engine.resources.MergedResource;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalArgumentException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.KimNotifier;
import org.integratedmodelling.klab.model.Namespace;
import org.integratedmodelling.klab.owl.Observable;
import org.integratedmodelling.klab.persistence.ModelKbox;
import org.integratedmodelling.klab.resolution.ObservationStrategy;
import org.integratedmodelling.klab.resolution.RankedModel;
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

	public void releaseNamespace(String namespaceId, IMonitor monitor) throws KlabException {
		kbox.remove(namespaceId, monitor);
	}

	// @Override
	public synchronized boolean index(IModel model, IMonitor monitor) throws KlabException {

		// wrong models don't get indexed; non-semantic models do (as private)
		if (model.getStatement().isErrors() || model.getObservables().size() == 0) {
			return true;
		}

		try {
			kbox.store(model, monitor);
			if (model.getScope() != Scope.NAMESPACE) {
				Indexer.INSTANCE.index(model.getStatement(), model.getNamespace().getName());
			}
//			monitor.info("model " + model.getName() + " was successfully indexed");
		} catch (Throwable e) {
			// happens with URN resources in space specs
			monitor.error("error indexing model " + model.getName() + ": model will be inactive"/* + e.getMessage() */);
			((org.integratedmodelling.klab.model.Model) model).setInactive(true);
			return false;
		}

		return true;
	}

	@Override
	public List<IRankedModel> resolve(IObservable observable, IResolutionScope scope) throws KlabException {
		List<IRankedModel> ret = kbox.query(observable, (ResolutionScope) scope);
		((ResolutionScope) scope).notifyResolution(observable, ret, scope.getMode());
		return ret;
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
		List<IRankedModel> ret = kbox.query(Observable.promote(trait), (ResolutionScope) scope);
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

	public boolean isAvailable(String modelName) {
		IKimObject model = Resources.INSTANCE.getModelObject(modelName);
		if (!(model instanceof IModel)) {
			return false;
		}
		for (IContextualizable resource : ((IModel) model).getResources()) {
			if (!resource.isAvailable()) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Called when a candidate observable has more than one model and/or a
	 * computation, making it a derived strategy to observe a given concepts. Must
	 * return a list with one single model with all the candidates as dependencies
	 * and the computations as computables, belonging to the namespace that
	 * resolution is currently happening into, wrapped into a ranked model with
	 * maximum rank.
	 * 
	 * When the whole thing becomes more intelligent, this may return a number of
	 * ranked alternative strategies, e.g. depending on previous paths taken and/or
	 * reasoning to find more complex pathways to the observation.
	 * 
	 * @param candidateObservable
	 * 
	 * @param ret
	 * 
	 * @return
	 */
	public List<IRankedModel> createDerivedModel(Observable mainObservable, ObservationStrategy candidateObservable,
			ResolutionScope scope) {
		ObservedConcept oc = new ObservedConcept(mainObservable);
		if (scope.getResolutions().containsKey(oc) && scope.getResolutions().get(oc).size() > 0) {
			return scope.getResolutions().get(oc);
		}
		org.integratedmodelling.klab.model.Model inner = new org.integratedmodelling.klab.model.Model(mainObservable,
				candidateObservable, scope);
		RankedModel outer = new RankedModel(inner);
		List<IRankedModel> ret = Collections.singletonList(outer);
		scope.getResolutions().put(oc, ret);
		return ret;
	}

	/*
	 * Create a new model for the change in the passed observable, based on the
	 * changing data resolved by the a dynamic resource. Used to pre-fill the kbox
	 * catalog when a merged resource covering the period of occurrence has resolved
	 * the unchanging observable. A previously existing model will be passed in case
	 * there are multiple partitions for this observable, which need to be dealt
	 * with internally.
	 * 
	 * @return
	 */
	public IRankedModel createChangeModel(IObservable observable, IObservable changeObservable, IModel model,
			ResolutionScope scope) {
		MergedResource resource = ((org.integratedmodelling.klab.model.Model) model).getMergedResource();
		org.integratedmodelling.klab.model.Model inner = null;
		if (resource != null) {
			if (!model.changesIn(Dimension.Type.TIME, scope.getScale())) {
				throw new KlabIllegalArgumentException(
						"Cannot create a merged resource change model from a model that does not contain dynamic resources");
			}
			inner = new org.integratedmodelling.klab.model.Model(observable, resource, model, scope);
		} else {
			// basic copy of model, which will also handle change
			inner = new org.integratedmodelling.klab.model.Model(changeObservable, model, scope);
		}
		int priority = -1;
		if (model instanceof RankedModel) {
			priority = ((RankedModel)model).getPriority();
		}
		return inner == null ? null : new RankedModel(inner).withPriority(priority);
	}

    public void releaseProject(IProject project) {
        for (INamespace namespace : project.getNamespaces()) {
            releaseNamespace(namespace.getId(), Klab.INSTANCE.getRootMonitor());
        }
    }

}
