package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.IObserver;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IAnnotationService;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.provenance.Artifact;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Annotations implements IAnnotationService {

	/**
	 * The global instance singleton.
	 */
	INSTANCE;

	/**
	 * To be implemented by the handlers of annotations mentioned in the
	 * corresponding prototypes. These are executed on the corresponding objects
	 * after the entire namespace is loaded, in order of declaration.
	 * 
	 * @author ferdinando.villa
	 *
	 */
	public interface Handler {

		/**
		 * Handle the passed object with the passed annotation arguments. Do not throw
		 * exceptions but use the monitor for notifications.
		 * 
		 * @param target
		 * @param arguments
		 * @param monitor
		 * @return any value deemed necessary. The return value is ignored for now,
		 *         reserved for future applications.
		 * @throws Exception
		 */
		Object process(IKimObject target, IParameters<Object> arguments, IMonitor monitor) throws Exception;
	}

	Map<String, Handler> handlers = Collections.synchronizedMap(new HashMap<>());
	Map<String, IPrototype> prototypes = Collections.synchronizedMap(new HashMap<>());

	@Override
	public IPrototype getPrototype(String annotation) {
		return prototypes.get(annotation);
	}

	@Override
	public Object process(IAnnotation annotation, IKimObject object, IMonitor monitor) {

		Handler handler = handlers.get(annotation.getName());
		if (handler != null) {
			try {
				return handler.process(object, annotation, monitor);
			} catch (Exception e) {
				monitor.error(e);
			}
		}
		return null;
	}

	public void declareServices(URL manifest) throws KlabException {

		IKdlDataflow declaration = Dataflows.INSTANCE.declare(manifest);

		String namespace = declaration.getPackageName();
		for (IKdlActuator actuator : declaration.getActuators()) {
			IPrototype prototype = new Prototype(actuator, namespace);
			prototypes.put(prototype.getName(), prototype);
			if (prototype.getType() != IArtifact.Type.ANNOTATION) {
				throw new KlabInternalErrorException(
						"annotation prototype for " + prototype.getName() + " does not specify an annotation");
			} else if (prototype.getExecutorClass() != null) {
				try {
					Object handler = prototype.getExecutorClass().getDeclaredConstructor().newInstance();
					if (handler instanceof Handler) {
						handlers.put(prototype.getName(), (Handler) handler);
					} else {
						throw new KlabInternalErrorException("error creating handler for " + prototype.getName()
								+ ": handler is not an instance of Annotations.Handler");
					}
				} catch (Exception e) {
					throw new KlabInternalErrorException(e);
				}
			}
		}
	}

	public void exportPrototypes(File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Prototype.class);
			mapper.writerFor(type).writeValue(file, this.prototypes);
		} catch (IOException e) {
			Logging.INSTANCE.error(e);
		}
	}

	/**
	 * Collect the annotations from an k.IM object and its semantic lineage,
	 * ensuring that downstream annotations of the same name override those
	 * upstream.
	 * 
	 * @param object
	 * @return all annotations from upstream
	 */
	public Collection<IAnnotation> collectAnnotations(Object... objects) {
		Map<String, IAnnotation> ret = new HashMap<>();
		for (Object object : objects) {
			if (object instanceof IKimObject) {
				collectAnnotations((IKimObject) object, ret);
			} else if (object instanceof ISemantic) {
				collectAnnotations((ISemantic) object, ret);
			} else if (object instanceof Artifact) {
				for (IAnnotation annotation : ((Artifact)object).getAnnotations()) {
					if (!ret.containsKey(annotation.getName())) {
						ret.put(annotation.getName(), annotation);
					}
				}
			}
		}
		return ret.values();
	}

	/**
	 * Collect the annotations from anything semantic lineage, ensuring that
	 * downstream annotations of the same name override those upstream.
	 * 
	 * @param object
	 * @return all annotations from upstream
	 */
	public Collection<IAnnotation> collectAnnotations(ISemantic object) {
		Map<String, IAnnotation> ret = new HashMap<>();
		collectAnnotations(object, ret);
		return ret.values();
	}

	private void collectAnnotations(IKimStatement object, Map<String, IAnnotation> collection) {

		for (IKimAnnotation annotation : object.getAnnotations()) {
			if (!collection.containsKey(annotation.getName())) {
				Annotation a = new Annotation(annotation);
				collection.put(a.getName(), a);
			}
		}

		if (object.getParent() != null) {
			collectAnnotations(object.getParent(), collection);
		}
	}

	private void collectAnnotations(IKimObject object, Map<String, IAnnotation> collection) {

		for (IAnnotation annotation : object.getAnnotations()) {
			if (!collection.containsKey(annotation.getName())) {
				collection.put(annotation.getName(), annotation);
			}
		}

		if (object instanceof IModel) {
			collectAnnotations(((IModel) object).getObservables().get(0), collection);
		} else if (object instanceof IObserver) {
			collectAnnotations(((IModel) object).getObservables().get(0), collection);
		} else if (object instanceof IConceptDefinition) {
			collectAnnotations(((IConceptDefinition) object).getStatement(), collection);
		}
	}

	private void collectAnnotations(ISemantic object, Map<String, IAnnotation> collection) {

		if (object instanceof IObservable) {

			/*
			 * collect from roles, traits and main in this order
			 */
			for (IConcept role : Roles.INSTANCE.getRoles(((IObservable) object).getType())) {
				collectAnnotations(role, collection);
			}
			for (IConcept trait : Traits.INSTANCE.getTraits(((IObservable) object).getType())) {
				collectAnnotations(trait, collection);
			}

			/*
			 * if we are classifying 'by', we use the classifier, not the main type
			 */
			IConcept mainType = ((IObservable) object).getBy() != null ? ((IObservable) object).getBy()
					: ((IObservable) object).getMain();
			
			collectAnnotations(mainType, collection);

		} else if (object instanceof IConcept) {
			IKimObject mobject = Resources.INSTANCE.getModelObject(object.toString());
			if (mobject != null) {
				collectAnnotations(mobject, collection);
			}
		}
	}

}
