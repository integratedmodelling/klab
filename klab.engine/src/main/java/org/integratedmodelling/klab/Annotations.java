package org.integratedmodelling.klab;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.ISemantic;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IConceptDefinition;
import org.integratedmodelling.klab.api.model.IKimObject;
import org.integratedmodelling.klab.api.model.IModel;
import org.integratedmodelling.klab.api.model.IAcknowledgement;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IAnnotationService;
import org.integratedmodelling.klab.engine.resources.CoreOntology;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.model.Annotation;
import org.integratedmodelling.klab.provenance.Artifact;
import org.integratedmodelling.klab.utils.Parameters;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum Annotations implements IAnnotationService {

	/**
	 * The global instance singleton.
	 */
	INSTANCE;

	private Annotations() {
		Services.INSTANCE.registerService(this, IAnnotationService.class);
	}

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
		Object process(IKimObject target, IParameters<String> arguments, IMonitor monitor) throws Exception;
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

	public void declareServices(InputStream manifest) throws KlabException {

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

	public void declareServices(URL manifest) throws KlabException {

		IKdlDataflow declaration = Dataflows.INSTANCE.declare(manifest);

		String namespace = declaration.getPackageName();
		for (IKdlActuator actuator : declaration.getActuators()) {
			IPrototype prototype = new Prototype(actuator, namespace);
			prototypes.put(prototype.getName(), prototype);
			if (prototype.getType() != IArtifact.Type.ANNOTATION) {
				throw new KlabInternalErrorException(
						"annotation prototype for " + prototype.getName() + " does not specify an annotation in " + manifest);
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
	 * upstream. Any string parameter filters the annotations collected.
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
				for (IAnnotation annotation : ((Artifact) object).getAnnotations()) {
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
		} else if (object instanceof IAcknowledgement) {
			collectAnnotations(((IModel) object).getObservables().get(0), collection);
		} else if (object instanceof IConceptDefinition) {
			collectAnnotations(((IConceptDefinition) object).getStatement(), collection);
		}
	}

	private void collectAnnotations(ISemantic object, Map<String, IAnnotation> collection) {

		if (object instanceof IObservable) {

			for (IAnnotation annotation : ((IObservable)object).getAnnotations()) {
				if (!collection.containsKey(annotation.getName())) {
					collection.put(annotation.getName(), annotation);
				}
			}

			/*
			 * collect from roles, traits and main in this order
			 */
			// for (IConcept role : Roles.INSTANCE.getRoles(((IObservable)
			// object).getType())) {
			// collectAnnotations(role, collection);
			// }
			for (IConcept trait : Traits.INSTANCE.getTraits(((IObservable) object).getType())) {
				// FIXME REMOVE ugly hack: landcover is a type, but it's used as an attribute in
				// various places so the change
				// is deep. This makes landcover colormaps end up in places they shouldn't be.
				// TODO check - may not be relevant anymore now that landcover is correctly a type of and not a trait.
				if (!trait.getNamespace().equals("landcover")) {
					collectAnnotations(trait, collection);
				}
			}

			collectAnnotations(((IObservable) object).getType(), collection);

		} else if (object instanceof IConcept) {
			IKimObject mobject = Resources.INSTANCE.getModelObject(object.toString());
			if (mobject != null) {
				collectAnnotations(mobject, collection);
			}
			if (((IConcept) object).is(Type.CLASS)) {
				// collect annotations from what is classified
				IConcept classified = Observables.INSTANCE.getDescribedType((IConcept) object);
				if (classified != null) {
					collectAnnotations(classified, collection);
				}
			}
			for (IConcept parent : ((IConcept) object).getParents()) {
				if (!CoreOntology.CORE_ONTOLOGY_NAME.equals(parent.getNamespace())) {
					collectAnnotations(parent, collection);
				}
			}
		}
	}

	public void register(Prototype prototype) {
		prototypes.put(prototype.getName(), prototype);
	}

	public boolean hasAnnotation(IObservable observable, String s) {
		for (IAnnotation annotation : observable.getAnnotations()) {
			if (annotation.getName().equals(s)) {
				return true;
			}
		}
		return false;
	}

	public IAnnotation getAnnotation(IObservable observable, String s) {
		for (IAnnotation annotation : observable.getAnnotations()) {
			if (annotation.getName().equals(s)) {
				return annotation;
			}
		}
		return null;
	}

	public boolean hasAnnotation(IKimObject object, String s) {
		for (IAnnotation annotation : object.getAnnotations()) {
			if (annotation.getName().equals(s)) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnnotation(IKimStatement object, String s) {
		for (IKimAnnotation annotation : object.getAnnotations()) {
			if (annotation.getName().equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public IAnnotation getAnnotation(IKimObject object, String s) {
		for (IAnnotation annotation : object.getAnnotations()) {
			if (annotation.getName().equals(s)) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * Shorthand to check whether the default parameter (list or individual value)
	 * of an annotation contains the passed string.
	 * 
	 * @param string
	 * @return
	 */
	public boolean defaultsContain(IAnnotation annotation, String string) {
		if (annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) instanceof List) {
			return ((List<?>) annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME)).contains(string);
		} else if (annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME) != null) {
			return annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME).equals(string);
		}
		return false;
	}

	/**
	 * Simple methods that are messy to keep writing explicitly
	 * 
	 * @param annotations
	 * @param id
	 * @return
	 */
	public IAnnotation getAnnotation(List<IAnnotation> annotations, String id) {
        for (IAnnotation annotation : annotations) {
            if (id.equals(annotation.getName())) {
                return annotation;
            }
        }
        return null;
    }

    public IParameters<String> collectVariables(List<IAnnotation> annotations) {
        IParameters<String> ret = Parameters.create();
        for (IAnnotation annotation : annotations) {
            if ("var".equals(annotation.getName())) {
                for (String key : annotation.getNamedKeys()) {
                    ret.put(key, annotation.get(key));
                }
            }
        }
        return ret;
    }

}
