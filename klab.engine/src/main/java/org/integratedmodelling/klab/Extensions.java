package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.integratedmodelling.kim.api.IContextualizable;
import org.integratedmodelling.kim.api.IKimSymbolDefinition;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.data.adapters.IResourceAdapter;
import org.integratedmodelling.klab.api.data.adapters.IUrnAdapter;
import org.integratedmodelling.klab.api.data.general.IExpression;
import org.integratedmodelling.klab.api.extensions.Component;
import org.integratedmodelling.klab.api.extensions.ILanguageExpression;
import org.integratedmodelling.klab.api.extensions.ILanguageProcessor;
import org.integratedmodelling.klab.api.extensions.ResourceAdapter;
import org.integratedmodelling.klab.api.extensions.UrnAdapter;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.model.contextualization.IContextualizer;
import org.integratedmodelling.klab.api.observations.IDirectObservation;
import org.integratedmodelling.klab.api.observations.IState;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid;
import org.integratedmodelling.klab.api.observations.scale.space.IGrid.Cell;
import org.integratedmodelling.klab.api.observations.scale.space.Orientation;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.IContextualizationScope;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IExtensionService;
import org.integratedmodelling.klab.components.geospace.extents.Space;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.code.Expression;
import org.integratedmodelling.klab.engine.runtime.code.groovy.GroovyProcessor;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabResourceNotFoundException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.model.extensions.GraphView;
import org.integratedmodelling.klab.model.extensions.TableView;
import org.integratedmodelling.klab.rest.ServicePrototype;
import org.integratedmodelling.klab.utils.FileUtils;
import org.integratedmodelling.klab.utils.Parameters;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public enum Extensions implements IExtensionService {

	/**
	 * The global instance singleton.
	 */
	INSTANCE;

	/**
	 * Each known function parsed from k.IM gets its prototype as a hidden parameter.
	 */
	public static final String PROTOTYPE_PARAMETER = "__prototype";

	/**
	 * Passed to contextualizer-creating functions so that they know if the contextualizer is
	 * being targeted to an observable other than the main one.
	 */
	public static final String TARGET_OBSERVABLE_PARAMETER = "__targetObservable";

	Map<String, IComponent> components = Collections.synchronizedMap(new HashMap<>());
	Map<String, Prototype> prototypes = Collections.synchronizedMap(new HashMap<>());
	// keep the component properties separately from the components themselves to allow 
	// usage during testing
	Map<String, Properties> componentProperties = Collections.synchronizedMap(new HashMap<>());

	private Extensions() {
		Services.INSTANCE.registerService(this, IExtensionService.class);
	}

	@Override
	public Collection<IComponent> getComponents() {
		return components.values();
	}

	public org.integratedmodelling.klab.engine.extensions.Component getComponent(String componentId) {
		return (org.integratedmodelling.klab.engine.extensions.Component) components.get(componentId);
	}

	@Override
	public Prototype getPrototype(String service) {
		return prototypes.get(service);
	}

	@Override
	public Object processDefinition(IKimSymbolDefinition statement, Object definition, INamespace namespace) {

		/*
		 * For now no plugin-based mechanism. Maybe later. Not hard - just annotate a provider and give it a 
		 * process method.
		 */
		switch (statement.getDefineClass()) {
		case "table":
			return new TableView(definition, statement, namespace);
		case "graph":
			return new GraphView(definition, statement, namespace);
		}
		
		return definition;
	}
	
	public org.integratedmodelling.klab.engine.extensions.Component registerComponent(Component annotation,
			Class<?> cls) {

		org.integratedmodelling.klab.engine.extensions.Component ret = new org.integratedmodelling.klab.engine.extensions.Component(
				annotation, cls);

		Logging.INSTANCE.info("Registering component " + ret.getName() + " version " + ret.getVersion());

		ret.initialize(Klab.INSTANCE.getRootMonitor());

		for (IPrototype service : ret.getAPI()) {
			prototypes.put(service.getName(), (Prototype) service);
		}

		Logging.INSTANCE
				.info("Component " + ret.getName() + " initialized: " + ret.getAPI().size() + " services provided");

		// /*
		// * ingest all .kdl files in the component's path
		// */
		// for (String kdl : new Reflections(cls.getPackage().getName(), new
		// ResourcesScanner())
		// .getResources(Pattern.compile(".*\\.kdl"))) {
		// try (InputStream input = cls.getClassLoader().getResourceAsStream(kdl)) {
		// declareServices(ret, Dataflows.INSTANCE.declare(input));
		// } catch (Throwable e) {
		// throw new KlabValidationException(e);
		// }
		// }
		//
		this.components.put(annotation.id(), ret);

		return ret;
	}

	@Override
	public Object callFunction(IServiceCall functionCall, IMonitor monitor) throws KlabException {
		return callFunction(functionCall, Expression.emptyContext(monitor));
	}

	public Object callFunction(IServiceCall functionCall, IContextualizationScope context) throws KlabException {

		Object ret = null;

		Prototype prototype = getPrototype(functionCall.getName());
		if (prototype == null) {
			throw new KlabResourceNotFoundException(
					"cannot find a function implementation for " + functionCall.getName());
		}

		Class<?> cls = prototype.getExecutorClass();

		if (cls != null) {
			if (IExpression.class.isAssignableFrom(cls)) {
				try {
					IExpression expr = (IExpression) cls.getDeclaredConstructor().newInstance();
					Parameters<String> parameters = new Parameters<String>(functionCall.getParameters());
					parameters.put(PROTOTYPE_PARAMETER, prototype);
					ret = expr.eval(parameters, context);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new KlabInternalErrorException(e);
				}
			} else if (IContextualizer.class.isAssignableFrom(cls)) {
				try {
					ret = cls.getDeclaredConstructor().newInstance();
					// TODO initialize with the parameters and monitor
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					throw new KlabInternalErrorException(e);
				}
			}
		}

		return ret;
	}

	/**
	 * Call the no-arg constructor for a class and return the instance, managing
	 * exceptions according to k.LAB conventions.
	 * 
	 * @param cls
	 * @param expected
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T createDefaultInstance(Class<?> cls, Class<? extends T> expected) {
		try {
			if (!expected.isAssignableFrom(cls)) {
				throw new IllegalArgumentException("Cannot produce an object of class " + expected + " from " + cls);
			}
			return (T) cls.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new KlabInternalErrorException(e);
		}
	}

	public Object createDefaultInstance(Class<?> cls) {
		return createDefaultInstance(cls, cls);
	}

	/**
	 * Produce the javabean describing the passed service prototype.
	 * 
	 * @param prototype
	 * @return a new prototype descriptor
	 */
	public ServicePrototype describePrototype(IPrototype prototype) {
		ServicePrototype ret = new ServicePrototype();
		ret.setName(prototype.getName());
		ret.setDescription(prototype.getDescription());
		for (Argument argument : prototype.listArguments()) {
			ServicePrototype.Argument arg = new ServicePrototype.Argument();
			arg.setDefaultValue(argument.getDefaultValue() == null ? null : argument.getDefaultValue().toString());
			arg.setDescription(argument.getDescription());
			arg.setName(argument.getName());
			arg.setType(argument.getType());
			arg.setRequired(!argument.isOptional());
			arg.setFinal(argument.isFinal());
			ret.getArguments().add(arg);
		}
		return ret;
	}

	public void registerUrnAdapter(UrnAdapter annotation, Class<?> cls) throws KlabException {
		/*
		 * class must be a IResourceAdapter
		 */
		if (IUrnAdapter.class.isAssignableFrom(cls)) {
			try {
				IUrnAdapter adapter = (IUrnAdapter) cls.getDeclaredConstructor().newInstance();
				Resources.INSTANCE.registerUrnAdapter(annotation.type(), adapter);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new KlabInternalErrorException(e);
			}
			Logging.INSTANCE
					.info("Registered URN adapter " + cls.getCanonicalName() + " for '" + annotation.type() + "'");
		} else {
			throw new KlabValidationException(
					annotation.type() + ": URN adapter annotations must be used on IResourceAdapter classes");
		}
	}

	public void registerResourceAdapter(ResourceAdapter annotation, Class<?> cls) throws KlabException {
		/*
		 * class must be a IResourceAdapter
		 */
		if (IResourceAdapter.class.isAssignableFrom(cls)) {
			try {
				IResourceAdapter adapter = (IResourceAdapter) cls.getDeclaredConstructor().newInstance();
				Resources.INSTANCE.registerResourceAdapter(annotation.type(), adapter);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				throw new KlabInternalErrorException(e);
			}
			Logging.INSTANCE
					.info("Registered resource adapter " + cls.getCanonicalName() + " for '" + annotation.type() + "'");
		} else {
			throw new KlabValidationException(
					annotation.type() + ": resource adapter annotations must be used on IResourceAdapter classes");
		}
	}
	
	public void validateArguments(IPrototype prototype, Map<String, Object> arguments) {
		// TODO
	}

	public void exportPrototypes(File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
			mapper.setSerializationInclusion(Include.NON_NULL);
			JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, Prototype.class);
			mapper.writerFor(type).writeValue(file, this.prototypes);
		} catch (IOException e) {
			Logging.INSTANCE.error(e);
		}
	}

	/**
	 * TODO! These are the Groovy/expr extension packages.
	 * 
	 * @return all the Kim imports
	 */
	public Collection<Class<?>> getKimImports() {
		List<Class<?>> ret = new ArrayList<>();
		return ret;
	}

	public IExpression compileExpression(String expressionCode, String language, boolean forcedScalar) {
		return getLanguageProcessor(language).compile(expressionCode, null, forcedScalar);
	}

	public IExpression compileExpression(String expressionCode, IExpression.Context context, String language,
			boolean forcedScalar) {
		return getLanguageProcessor(language).compile(expressionCode, context, forcedScalar);
	}

	@Override
	public ILanguageProcessor getLanguageProcessor(String language) {
		// TODO
		return (language == null || language.equals(DEFAULT_EXPRESSION_LANGUAGE)) ? GroovyProcessor.INSTANCE : null;
	}

	/**
	 * Check all possible values that may evaluate to true in a computable and
	 * return their logical result. Send an error through the monitor and return
	 * false if no such conversion is possible
	 * 
	 * @param condition
	 * @param context
	 * @return
	 */
	public boolean callAsCondition(IContextualizable condition, IContextualizationScope context) {
		if (condition.getLiteral() != null) {
			if (condition.getLiteral() instanceof Boolean) {
				return (Boolean) condition.getLiteral();
			} else {
				context.getMonitor().error("cannot use value " + condition.getLiteral() + " as a logical value");
			}
		} else if (condition.getExpression() != null) {
			IExpression expression = getLanguageProcessor(DEFAULT_EXPRESSION_LANGUAGE).compile(
					condition.getExpression().getCode(), context.getExpressionContext(),
					condition.getExpression().isForcedScalar());
			Object o = expression.eval(context, context);
			if (o instanceof Boolean) {
				return (Boolean) o;
			} else {
				context.getMonitor().error("cannot use expression result " + o + " from [" + condition.getExpression()
						+ "] as a logical value");
			}
		} else if (condition.getServiceCall() != null) {
			Object o = callFunction(condition.getServiceCall(), context);
			if (o instanceof Boolean) {
				return (Boolean) o;
			} else {
				context.getMonitor().error("cannot use result " + o + " from function call "
						+ condition.getServiceCall().getName() + " as a logical value");
			}
		}
		return false;
	}

	/**
	 * A short descriptive text for the service as called, created using the
	 * prototype label, if any, with interpolation of parameter values if warranted.
	 * If the prototype has no label, return the service name.
	 * 
	 * @return a descriptive label. Never null.
	 */
	public String getServiceLabel(IServiceCall call) {
		IPrototype prototype = getPrototype(call.getName());
		if (prototype == null || prototype.getLabel() == null) {
			return call.getName();
		}
		String ret = prototype.getLabel();
		if (ret.contains("$")) {
			for (String s : call.getParameters().keySet()) {
				if (ret.contains("$" + s)) {
					ret = ret.replace("$" + s, call.getParameters().get(s).toString());
				}
			}
		}
		return ret;
	}

	/**
	 * Called from Groovy when expressions like "id@nw" are found. Applies some
	 * memoizing to handle the context IDs quicker.
	 * 
	 * @param targetId
	 * @param contextIds
	 * @param context
	 * @return
	 */
	public Object recontextualizeIdentifier(String targetId, String contextId, IRuntimeScope context,
			ILanguageExpression expression, Map<?, ?> variables) {

		/*
		 * must be a state
		 */
		Object ret = null;
		// TODO remove
		// String dio = "boh";

		IArtifact artifact = context.getArtifact(targetId);
		if (!(artifact instanceof IState)) {
			throw new IllegalArgumentException(
					"cannot recontextualize " + targetId + " to " + contextId + ": not a state");
		}
		IState state = (IState) artifact;

		switch (contextId) {
		case "w":
		case "n":
		case "s":
		case "e":
		case "sw":
		case "nw":
		case "se":
		case "ne":

			// space must be a grid
			if (state.getSpace() == null || ((Space) state.getSpace()).getGrid() == null
					|| !(context.getScale().getSpace() instanceof IGrid.Cell)) {
				throw new IllegalArgumentException(
						"cannot recontextualize " + targetId + " to " + contextId + ": not on grid space");
			}

			Cell cell = (IGrid.Cell) context.getScale().getSpace();
			ret = state.get(cell.getNeighbor(Orientation.valueOf(contextId.toUpperCase())));
			break;

		case "previous":
			// we must have time > start
			break;
		default:
			// recontextualize to view of other object
			Object o = expression.unwrap(variables.get(contextId));
			if (o instanceof IDirectObservation) {
				ret = state.at(((IDirectObservation) o).getScale()).aggregate();
				// dio = ((IDirectObservation) o).getScale().encode();
			} else {
				throw new IllegalArgumentException("cannot recontextualize " + targetId + " to " + contextId
						+ ": new context is not a direct observation");
			}
			break;
		}

		// System.out.println("GOT " + ret + " for " + targetId + " @ " + dio);

		return ret;
	}

	@Override
	public <T> T getComponentImplementation(String componentId, Class<? extends T> requestedClass) {
		org.integratedmodelling.klab.engine.extensions.Component component = getComponent(componentId);
		return component == null ? null : component.getImplementation(requestedClass);
	}
	

	public Properties getComponentProperties(String componentId) {
		if (!componentProperties.containsKey(componentId)) {
			Properties cp = new Properties();
			File pfile = new File(Configuration.INSTANCE.getDataPath() + File.separator + componentId + ".properties");
			if (pfile.exists()) {
				try (InputStream inp = new FileInputStream(pfile)) {
					cp.load(inp);
				} catch (Exception e) {
					throw new KlabIOException(e);
				}
			} else {
				try { 
					FileUtils.touch(pfile);
				} catch (IOException e) {
					// screw it
				}
			}
			componentProperties.put(componentId, cp);
		}
		return componentProperties.get(componentId);
	}

	public void saveComponentProperties(String componentId) {
		
		Properties cp = getComponentProperties(componentId);
		File pfile = new File(Configuration.INSTANCE.getDataPath() + File.separator + componentId + ".properties");
		try (OutputStream out = new FileOutputStream(pfile)) {
			cp.store(out, null);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

}
