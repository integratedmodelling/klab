package org.integratedmodelling.klab.engine.extensions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IKimProject;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Resources;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.data.IResource;
import org.integratedmodelling.klab.api.extensions.component.GetStatus;
import org.integratedmodelling.klab.api.extensions.component.IComponent;
import org.integratedmodelling.klab.api.extensions.component.Initialize;
import org.integratedmodelling.klab.api.extensions.component.Setup;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.knowledge.IWorkspace;
import org.integratedmodelling.klab.api.model.INamespace;
import org.integratedmodelling.klab.api.runtime.ITicket;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.data.Metadata;
import org.integratedmodelling.klab.documentation.DataflowDocumentation;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabInternalErrorException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.utils.Pair;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

public class Component implements IComponent {

	String name;
	boolean active = true;
	boolean initialized;
	Version version;
	Map<String, IPrototype> services = new HashMap<>();
	Map<String, IPrototype> annotations = new HashMap<>();

	private String initMethod = null;
	private List<Pair<String, Boolean>> setupMethods = new ArrayList<>();
	private List<String> statusMethods = new ArrayList<>();
	private boolean binaryAssetsLoaded;
	private Class<?> implementingClass;
	private Object implementation;
	private Properties properties;

	public Component() {
		// TODO Auto-generated constructor stub
	}

	public Component(org.integratedmodelling.klab.api.extensions.Component annotation, Class<?> implementation) {

		this.name = annotation.id();
		this.version = Version.create(annotation.version());
		this.implementingClass = implementation;
		this.properties = new Properties();

		File propertyFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + name + ".properties");
		if (propertyFile.exists()) {
			try (InputStream input = new FileInputStream(propertyFile)) {
				properties.load(input);
			} catch (IOException e) {
				Logging.INSTANCE.error(e);
			}
		}

		try {
			// TODO scan methods and exec/store setup and initialization
			for (Method method : implementation.getMethods()) {
				if (method.isAnnotationPresent(Initialize.class)) {
					this.initMethod = method.getName();
				}
				if (method.isAnnotationPresent(Setup.class)) {
					Setup setup = method.getAnnotation(Setup.class);
					this.setupMethods.add(new Pair<>(method.getName(), setup.asynchronous()));
				}
				if (method.isAnnotationPresent(GetStatus.class)) {
					this.statusMethods.add(method.getName());
				}
			}
		} catch (Exception e) {
			// don't break for now, just deactivate, log the error and move
			// on.
			Logging.INSTANCE.error(e);
			this.active = false;
		}
	}

	public void initialize(IMonitor monitor) throws KlabException {

		if (initialized) {
			return;
		}

		loadBinaryAssets();

		if (!active) {
			return;
		}

		String missing = "";

		if (!missing.isEmpty()) {
			throw new KlabValidationException(
					"component " + name + " is missing the following exported knowledge IDs: " + missing);
		}

		if (implementingClass == null) {
			active = false;
			return;
		}

		Logging.INSTANCE.info("Initializing component " + name);

		Object executor = getImplementation();
		if (executor == null) {
			throw new KlabInternalErrorException(
					"component " + name + " cannot be initialized for lack of a suitable public constructor");
		}

		ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
		try {
			for (Resource res : patternResolver.getResources("components/" + name + "/services/*.kdl")) {
				try (InputStream input = res.getInputStream()) {
					IKdlDataflow declaration = Dataflows.INSTANCE.declare(input);
					String namespace = declaration.getPackageName();
					for (IKdlActuator actuator : declaration.getActuators()) {
						Prototype prototype = new Prototype(actuator, namespace);
						services.put(prototype.getName(), prototype);
					}
				}
			}
			for (Resource res : patternResolver.getResources("components/" + name + "/annotations/*.kdl")) {
				try (InputStream input = res.getInputStream()) {
					IKdlDataflow declaration = Dataflows.INSTANCE.declare(input);
					String namespace = declaration.getPackageName();
					for (IKdlActuator actuator : declaration.getActuators()) {
						Prototype prototype = new Prototype(actuator, namespace);
						Annotations.INSTANCE.register(prototype);
						annotations.put(prototype.getName(), prototype);
					}
				}
			}
			for (Resource res : patternResolver.getResources("components/" + name + "/doc/*.*")) {
				DataflowDocumentation.INSTANCE.addTemplate("components/" + name + "/doc/" + res.getFilename());
			}
		} catch (KlabException e) {
			throw new KlabValidationException(e);
		} catch (Throwable e) {
//			Logging.INSTANCE.error("error loading component manifest " + name + ": " + e.getMessage());
			// ignore others - means resource path isn't there. These are inside the
			// components so
			// should be safe enough, although it would be nicer to have a simple way to
			// test for the
			// existence of the path without having to fail first.
		}

		if (initMethod == null) {
			active = true;
			initialized = true;
			return;
		}

		/*
		 * find executing method and call it
		 */
		Method method = null;
		try {
			method = executor.getClass().getMethod(initMethod);
		} catch (NoSuchMethodException | SecurityException e) {
			return;
		}

		if (method != null) {
			try {
				Object result = method.invoke(executor);
				if (result instanceof Boolean) {
					active = (Boolean) result;
				}
			} catch (Exception e) {
				throw (e instanceof KlabException ? (KlabException) e
						: new KlabInternalErrorException("error calling component initializer for " + name + ": "
								+ ExceptionUtils.getRootCauseMessage(e)));
			}
		}

		initialized = true;

		Logging.INSTANCE.info("component " + name + " initialized successfully and ready for operation");
	}

	@Override
	public ITicket setup() throws KlabException {

		ITicket ret = null;

		if (implementingClass == null) {
			return null;
		}
		if (setupMethods.isEmpty()) {
			return null;
		}
		Object executor = getImplementation();
		if (executor == null) {
			throw new KlabInternalErrorException("component " + name + " has not been configured properly");
		}

		/*
		 * find executing method and call it
		 */
		List<Method> methods = new ArrayList<>();

		try {
			for (Pair<String, Boolean> m : setupMethods) {
				methods.add(executor.getClass().getMethod(m.getFirst()));
			}
		} catch (NoSuchMethodException | SecurityException e) {
			return null;
		}

		try {

			ret = Klab.INSTANCE.getTicketManager().open("component", this.getName(), ITicket.Type.ComponentSetup);
			final ITicket ticket = ret;
			final Object exec = executor;

			new Thread() {
				@Override
				public void run() {
					try {
						for (Method code : methods) {
							active = (Boolean) code.invoke(exec);
						}
						ticket.resolve();
					} catch (Throwable e) {
						ticket.error(e.getMessage());
					}
				}
			}.start();
		} catch (Throwable e) {
			ret.error(e.getMessage());

//			throw (e instanceof KlabException ? (KlabException) e
//					: new KlabInternalErrorException("error calling component initializer for " + name + ": "
//							+ ExceptionUtils.getRootCauseMessage(e)));
		}

		return ret;

	}

	public void loadBinaryAssets() {

		if (!binaryAssetsLoaded) {

			binaryAssetsLoaded = true;

			/*
			 * load binary assets in /lib - at the very least, that will contain our own
			 * code. Validation through signing should have prevented getting here if the
			 * code is suspicious. This doesn't get hit in development components.
			 */
			// if (getLibDirectory().exists() && getLibDirectory().isDirectory()) {
			// for (Iterator<File> it = FileUtils.iterateFiles(getLibDirectory(), new
			// String[] { "jar" },
			// false); it
			// .hasNext();) {
			// File jarFile = it.next();
			// ClassLoaderUtil.addFileToClassPath(jarFile,
			// this.getClass().getClassLoader());
			// }
			// }

			/*
			 * binary assets - usually from target/classes in development components.
			 */
			// if (getBinDirectory().exists() && getBinDirectory().isDirectory()) {
			// ClassLoaderUtil.addFileToClassPath(getBinDirectory(),
			// this.getClass().getClassLoader());
			// }

			// try {
			// for (Pair<Annotation, Class<?>> decl : KLAB.ENGINE
			// .scanPackage(getProperties().getProperty(COMPONENT_PACKAGE_PROPERTY))) {
			// if (decl.getFirst() instanceof
			// org.integratedmodelling.api.components.Component) {
			// this.implementation = decl.getSecond();
			// this.domain = ((org.integratedmodelling.api.components.Component)
			// decl.getFirst()).worldview();
			// this.version = Version
			// .parse(((org.integratedmodelling.api.components.Component)
			// decl.getFirst()).version()
			// .toString());
			//
			// for (Method method : implementation.getMethods()) {
			// if (method.isAnnotationPresent(Initialize.class)) {
			// initMethod = method.getName();
			// }
			// if (method.isAnnotationPresent(Setup.class)) {
			// setupMethod = method.getName();
			// Setup setup = method.getAnnotation(Setup.class);
			// isSetupAsynchronous = setup.asynchronous();
			// }
			// }
			//
			// } else if (decl.getFirst() instanceof
			// org.integratedmodelling.api.services.annotations.Prototype) {
			// IPrototype prototype = KLAB.ENGINE.getFunctionPrototype(
			// ((org.integratedmodelling.api.services.annotations.Prototype)
			// decl.getFirst()).id());
			// ((Prototype) prototype).setComponentId(id);
			// this.services.add(prototype);
			// }
			//
			// }
			// } catch (KlabException e) {
			// // don't break for now, just deactivate, log the error and move
			// // on.
			// Klab.INSTANCE.error(e);
			// active = false;
			// }

			Logging.INSTANCE.info("component " + name + " loaded  (" + this.services.size() + " services provided)");
		}
	}

	public Optional<Class<?>> getImplementingClass() {
		return implementingClass == null ? Optional.empty() : Optional.of(implementingClass);
	}

	@Override
	public Collection<File> getBinaryAssets() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<IPrototype> getAPI() {
		return services.values();
	}

	@Override
	public boolean isActive() {
		return active;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public File getRoot() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<INamespace> getNamespaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IProject> getPrerequisites() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Version getVersion() {
		return this.version;
	}

	@Override
	public INamespace getUserKnowledge() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCanonical() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isRemote() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getOriginatingNodeId() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Return the implementation of the class if an implementing class is defined,
	 * creating it if necessary.
	 * 
	 * @return the implementation
	 */
	public Object getImplementation() {
		if (implementation == null && implementingClass != null) {
			try {
				implementation = implementingClass.getDeclaredConstructor().newInstance();
			} catch (Throwable e) {
				throw new KlabInternalErrorException(e);
			}
		}
		return implementation;
	}

	@SuppressWarnings("unchecked")
	public <T> T getImplementation(Class<? extends T> cls) {
		Object ret = getImplementation();
		if (!ret.getClass().isAssignableFrom(cls)) {
			throw new IllegalArgumentException(
					"requested component has no implementation or the implementation is not of class "
							+ cls.getCanonicalName());
		}
		return (T) ret;
	}

	@Override
	public Collection<String> getLocalResourceUrns() {
		throw new IllegalAccessError("components cannot have local resources");
	}

	@Override
	public IKimProject getStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IWorkspace getWorkspace() {
		return Resources.INSTANCE.getComponentsWorkspace();
	}

	@Override
	public IResource getLocalResource(String urn) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IBehavior> getBehaviors() {
		// TODO Auto-generated method stub
        return Collections.emptyList();
	}

	@Override
	public List<IBehavior> getApps() {
		// TODO Auto-generated method stub
        return Collections.emptyList();
	}

    @Override
    public List<IBehavior> getUnitTests() {
        // TODO Auto-generated method stub
        return Collections.emptyList();
    }

	@Override
	public IMetadata getStatus() {
		IMetadata ret = new Metadata();
		if (getImplementation() != null) {
			for (String method : statusMethods) {
				try {
					Method m = getImplementation().getClass().getMethod(method, IMetadata.class);
					m.invoke(getImplementation(), ret);
				} catch (Throwable e) {
					throw new KlabInternalErrorException(e);
				}
			}
		}
		return ret;
	}

	@Override
	public Properties getProperties() {
		return properties;
	}
	
	public void persistProperties() {
	    try (OutputStream out = new FileOutputStream(new File(Configuration.INSTANCE.getDataPath() + File.separator + name + ".properties"))) {
	        this.properties.store(out, "Saved by API on " + DateTime.now(DateTimeZone.UTC));
	    } catch (Exception e) {
	        throw new KlabIOException(e);
        }
	}

}
