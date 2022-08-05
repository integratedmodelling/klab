package org.integratedmodelling.klab.ide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActors.CodeAssistant;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.kim.model.KimLoader;
import org.integratedmodelling.kim.ui.elink.KimLinkDetector;
import org.integratedmodelling.kim.ui.elink.KimLinkDetector.LinkOpenListener;
import org.integratedmodelling.kim.ui.internal.KimActivator;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.client.http.EngineClient;
import org.integratedmodelling.klab.client.http.EngineMonitor;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.kim.KimResourceListener;
import org.integratedmodelling.klab.ide.model.Klab;
import org.integratedmodelling.klab.ide.model.KlabEngine;
import org.integratedmodelling.klab.ide.model.KlabExplorer;
import org.integratedmodelling.klab.ide.model.KlabSession;
import org.integratedmodelling.klab.ide.model.KlabUser;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigator;
import org.integratedmodelling.klab.ide.navigator.e3.KlabNavigatorActions;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.AttributeReference;
import org.integratedmodelling.klab.rest.AuthorityIdentity;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.rest.EngineEvent;
import org.integratedmodelling.klab.rest.Notification;
import org.integratedmodelling.klab.rest.ProjectLoadRequest;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.rest.WatchRequest;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.StringUtil;
import org.osgi.framework.BundleContext;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

    private static Logger logger = LoggerFactory.getLogger(Activator.class);
	public static final String PLUGIN_ID = "org.integratedmodelling.klab.ide";
	private static Activator plugin;
	private EngineMonitor engineStatusMonitor;

	/*
	 * identity for relaying messages sent from Web UI to session
	 */
	String relayId = "relay" + NameGenerator.shortUUID();
	private Klab klab = new Klab();
	private KlabEngine engine;
	private KlabExplorer explorer;
	private KlabSession session;
	private KimLoader loader;
	private KlabUser user = new KlabUser();

	/**
	 * The constructor
	 */
	public Activator() {
	}

	public void start(BundleContext context) throws Exception {

		super.start(context);
		configureLogback();

		/*
		 * TODO retrieve from preferences if so configured.
		 */
		String initialSessionId = null;
		/**
		 * Install k.Actors code assistant
		 */
		KActors.INSTANCE.setCodeAssistant(new CodeAssistant() {

			@Override
			public BehaviorId classifyVerb(String call) {
				Set<BehaviorReference> behavior = KimData.INSTANCE.getBehaviorFor(call);
				return behavior == null ? BehaviorId.LOCAL
						: (behavior.size() > 1 ? BehaviorId.AMBIGUOUS : getBehaviorId(behavior.iterator().next()));
			}

			private BehaviorId getBehaviorId(BehaviorReference behavior) {
				switch (behavior.getName()) {
				case "view":
					return BehaviorId.VIEW;
				case "user":
					return BehaviorId.USER;
				case "object":
					return BehaviorId.OBJECT;
				case "state":
					return BehaviorId.STATE;
				case "session":
					return BehaviorId.SESSION;
                case "test":
                    return BehaviorId.TEST;
				case "explorer":
					return BehaviorId.EXPLORER;
				}
				return BehaviorId.IMPORTED;
			}

			@Override
			public String getLabel(String call) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getDescription(String call) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Collection<org.integratedmodelling.kactors.api.IKActorsValue.Type> getFiredType(String call) {
				// TODO Auto-generated method stub
				return null;
			}
		});

		/*
		 * install k.IM validator at client side, talking to engine
		 */
		Kim.INSTANCE.setValidator(new Validator() {

			@Override
			public List<Pair<String, Level>> validateFunction(IServiceCall functionCall, Set<IArtifact.Type> expected) {
				IPrototype prototype = KimData.INSTANCE.getFunctionPrototype(functionCall.getName());
				if (prototype != null) {
					return prototype.validate(functionCall);
					// TODO handle expected type
				}
				return null;
			}

			@Override
			public UrnDescriptor classifyUrn(String urn) {
				UrnDescriptor ret = new UrnDescriptor(0, "URN not validated");
				if (Activator.klab() != null) {
					EResourceReference resource = Activator.klab().getResource(urn);
					if (resource != null) {
						ret.setKnown();
						if (!resource.isUnauthorized()) {
							ret.setAccessible();
						}
						if (resource.isError()) {
							ret.setError();
						} else if (resource.isOnline()) {
							ret.setOnline();
						}
						if (resource.getDependencies() != null) {
							for (AttributeReference dependency : resource.getDependencies()) {
								ret.addDependency(dependency.getName(), dependency.getType());
							}
						}
					}
				}
				return ret;
			}

			@Override
			public EnumSet<Type> classifyCoreType(String string, EnumSet<Type> statedType) {
				// TODO Auto-generated method stub
				return statedType;
			}

			@Override
			public boolean isFunctionKnown(String functionName) {
				return KimData.INSTANCE.getFunctionPrototype(functionName) != null;
			}

			@Override
			public boolean isAnnotationKnown(String annotationName) {
				return KimData.INSTANCE.getAnnotationPrototype(annotationName) != null;
			}

			@Override
			public List<Pair<String, Level>> validateAnnotation(IServiceCall annotationCall, IKimStatement target) {
				IPrototype prototype = KimData.INSTANCE.getAnnotationPrototype(annotationCall.getName());
				if (prototype != null) {
					return prototype.validate(annotationCall);
					// TODO handle target
				}
				return null;
			}

			@Override
			public IPrototype getFunctionPrototype(String functionId) {
				return KimData.INSTANCE.getFunctionPrototype(functionId);
			}

			@Override
			public IPrototype getAnnotationPrototype(String functionId) {
				return KimData.INSTANCE.getAnnotationPrototype(functionId);
			}

			@Override
			public void createWorldviewPeerConcept(String coreConcept, String worldviewConcept) {
				// this is a pure syntactic validator, no action due and no harm done as long
				// as the engine doesn't use the same.
			}

			@Override
			public String getObservableInformation(IKimObservable observable, boolean formatted) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			public String getConceptInformation(IKimConcept observable, boolean formatted) {
				// TODO Auto-generated method stub
				return "";
			}

			@Override
			public Pair<String, Boolean> getIdentityInformation(String authority, String identity, boolean formatted) {

				if (!engineMonitor().isRunning()) {
					return Pair.create(OFFLINE, true);
				}
				AuthorityIdentity id = klab().getIdentityInformation(authority, identity, formatted);
				if (id == null) {
					return Pair.create(UNKNOWN_AUTHORITY, true);
				}

				String notifications = "";
				String errors = "";
				boolean error = false;

				for (Notification notification : id.getNotifications()) {
					if (Level.SEVERE.getName().equals(notification.getLevel())) {
						errors += (errors.isEmpty() ? "ERROR: " : "\n\nERROR: ") + notification.getMessage();
						error = true;
					} else {
						notifications += (errors.isEmpty() ? "" : "\n\n ") + notification.getLevel() + ": "
								+ notification.getMessage();
					}
				}

				return Pair.create(errors + (error ? "\n\n" : "") + id.getDescription()
						+ (notifications.isEmpty() ? "" : "\n\n") + notifications, true);
			}

		});

		/*
		 * this tells us when a project is being closed
		 */
		ResourcesPlugin.getWorkspace().addResourceChangeListener(new KimResourceListener());

		/*
		 * install link helper
		 */
		KimLinkDetector.setListener(new LinkOpenListener() {

			@Override
			public void openLink(String text) {
				int nc = StringUtil.countMatches(text, ":");
				if (nc > 1) {
					// URN - open in editor
					if (klab != null && klab.getResource(text) != null) {
						KlabNavigatorActions.editResource(klab.getResource(text));
					}
				} else if (nc == 1) {
					// MODEL OBJECT
					IKimStatement statement = Kim.INSTANCE.getStatement(text);
					if (statement != null) {
						IFile ifile = Eclipse.INSTANCE.getNamespaceIFile(statement.getNamespace());
						if (ifile != null) {
							Eclipse.INSTANCE.openFile(ifile, statement.getFirstLine());
						}
					}
				} else {
					// DEFINE - either in the definition itself or a reference (non-semantic also?).
					// Hard to do anything without knowing which
					// namespace we are linking from
				}
				// System.out.println("Link me hostia: " + text);
			}
		});

		plugin = this;

		this.engineStatusMonitor = new EngineMonitor(EngineMonitor.ENGINE_DEFAULT_URL, () -> engineOn(),
				() -> engineOff(), initialSessionId) {

			@Override
			protected void error(String string) {
				Eclipse.INSTANCE.alert(string);
				Eclipse.INSTANCE.error(string);
			}

		};

		/*
		 * setup the language helper with access to the grammar
		 */
		Kim.INSTANCE.setup(KimActivator.getInstance().getInjector(KimActivator.ORG_INTEGRATEDMODELLING_KIM_KIM));

		/*
		 * Pre-build workspaces and set up any overriding of others from projects in
		 * Eclipse workspace. The IDE only has the worldview and the user workspace; the
		 * engine has more, which can be put in the workspace of the IDE if desired.
		 */
		Kim.INSTANCE.prebuildWorkspaces(getProjectFiles(), getWorldviewFiles());

		/*
		 * load the workspace. This is the only point where the ECore -> k.IM
		 * translation is invoked.
		 */

		// get the worldview first
		KimLoader worldviewLoader = new KimLoader(
				KimActivator.getInstance().getInjector(KimActivator.ORG_INTEGRATEDMODELLING_KIM_KIM),
				getWorldviewFiles());

		// set the worldview as the external knowledge repo for the workspace
		this.loader = new KimLoader(
				KimActivator.getInstance().getInjector(KimActivator.ORG_INTEGRATEDMODELLING_KIM_KIM), worldviewLoader);

		// load the workspace, which may include the worldview
		this.loader.loadProjectFiles(getProjectFiles());

		plugin = this;

		/*
		 * ensure we can be stopped by an external controller
		 */
		new FileBasedMonitor(5).start(5);

		this.engineStatusMonitor.start(relayId);

	}
	
	private void configureLogback() {
	    ILoggerFactory iLoggerFactory = LoggerFactory.getILoggerFactory();  
        LoggerContext loggerContext = (LoggerContext) iLoggerFactory;  
        loggerContext.reset();  
        JoranConfigurator configurator = new JoranConfigurator();  
        configurator.setContext(loggerContext);  
        try {  
          configurator.doConfigure(getClass().getResourceAsStream("/logback.xml"));  
        } catch (JoranException e) {
            // log will not work, but we continue
            System.err.println(e);  
        }  
	}

	/**
	 * Call this after adding or removing projects
	 */
	public void reloadWorkspace() {
		this.loader.loadProjectFiles(getProjectFiles());
	}

	// private Object getWorkspaceFiles() {
	// // TODO Auto-generated method stub
	// return null;
	// }

	private Collection<File> getProjectFiles() {
		List<File> projectFiles = new ArrayList<>();
		for (IProject project : Eclipse.INSTANCE.getProjects()) {
			projectFiles.add(project.getLocation().toFile());
			// this preloads all resources with offline status so they can be updated when
			// the engine is connected
			klab.synchronizeProjectResources(project.getName(), project.getLocation().toFile());
		}
		return projectFiles;
	}

	private Collection<File> getWorldviewFiles() {
		List<File> ret = new ArrayList<>();
		File file = Configuration.INSTANCE.getDataPath("worldview");
		if (file.isDirectory()) {
			for (File pfile : file.listFiles()) {
				if (Kim.INSTANCE.isKimProject(pfile)) {
					ret.add(pfile);
				}
			}
		}
		return ret;
	}

	public String getRelayId() {
		return relayId;
	}

	private void engineOff() {
		this.engine.send(Message.create(this.engineStatusMonitor.getEngineId(), IMessage.MessageClass.EngineLifecycle,
				IMessage.Type.EngineDown, this.engineStatusMonitor.getCapabilities()));
		this.user = new KlabUser();
		logger.info("--------------\nEngine went off\n----------------");
	}

	private void engineOn() {

		String sessionId = this.engineStatusMonitor.getSessionId();
		/**
		 * Add communication peers for engine, session and explorer
		 */
		this.engine = new KlabEngine(this.engineStatusMonitor.getEngineId());
		this.session = new KlabSession(sessionId);
		this.explorer = new KlabExplorer(relayId);

		this.user = new KlabUser(this.engineStatusMonitor.getOwner());
		this.engineStatusMonitor.getBus().subscribe(this.engineStatusMonitor.getEngineId(), this.engine);
		this.engineStatusMonitor.getBus().subscribe(sessionId, this.session);
		this.engineStatusMonitor.getBus().subscribe(relayId, this.explorer);
		this.engine.send(Message.create(this.engineStatusMonitor.getEngineId(), IMessage.MessageClass.EngineLifecycle,
				IMessage.Type.EngineUp, this.engineStatusMonitor.getCapabilities()));
		/*
		 * subscribe to notifications for resource validation events
		 */
		WatchRequest request = new WatchRequest();
		request.setEventType(EngineEvent.Type.ResourceValidation);
		request.setActive(true);
		this.engineStatusMonitor.getBus().post(Message.create(this.engineStatusMonitor.getSessionId(),
				IMessage.MessageClass.Notification, IMessage.Type.EngineEvent, request));

		/*
		 * offer to import any k.LAB local projects that are not in the workspace and
		 * have the engine load those projects it does not have. TODO may also offer to
		 * import the worldview for developers.
		 */
		Display.getDefault().asyncExec(() -> {

			List<ProjectReference> missingProjects = new ArrayList<>();
			Set<String> localProjectIds = new HashSet<>();
			for (ProjectReference project : this.engineStatusMonitor.getCapabilities().getLocalWorkspaceProjects()) {
				if (Eclipse.INSTANCE.getProject(project.getName()) == null
						&& /* TODO OBSOLETE - REMOVE THIS AFTER DEALING WITH EXISTING INSTALLATIONS */!project.getName()
								.equals("local")) {
					missingProjects.add(project);
				}
				localProjectIds.add(project.getName());
			}
			if (missingProjects.size() > 0) {
				Collection<ProjectReference> choices = Eclipse.INSTANCE.chooseMany(
						"The following projects are in k.LAB but not in the workspace. Do you want to import them?",
						missingProjects,
						(project) -> ResourceManager.getPluginImage(Activator.PLUGIN_ID, "icons/k-lab-icon-16.gif"));
				for (ProjectReference choice : choices) {
					Eclipse.INSTANCE.importExistingProject(choice.getRootPath());
				}
			}
			/*
			 * find out which projects in our workspace are not known to the engine and send
			 * them over.
			 */
			List<File> projectFiles = new ArrayList<>();
			for (IProject project : Eclipse.INSTANCE.getProjects()) {
				if (!localProjectIds.contains(project.getName())) {
					projectFiles.add(project.getLocation().toFile());
				}
			}
			if (projectFiles.size() > 0) {
				post(IMessage.MessageClass.ProjectLifecycle, IMessage.Type.NotifyProjects,
						new ProjectLoadRequest(projectFiles));
			}

			KlabNavigator.refresh();
			Eclipse.INSTANCE.refreshOpenEditors();
		});

	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		if (this.engineStatusMonitor != null) {
			this.engineStatusMonitor.stop();
		}
		super.stop(context);
	}

	public static EngineMonitor engineMonitor() {
		return get().engineStatusMonitor;
	}

	public static KlabEngine engine() {
		return get().engine;
	}

	public static KlabSession session() {
		return get().session;
	}

	public static KlabExplorer explorer() {
		return get().explorer;
	}

	public static Klab klab() {
		return get().klab;
	}

	public static EngineClient client() {
		return engineMonitor().getClient();
	}

	public static IKimLoader loader() {
		return get().loader;
	}

	public static KlabUser user() {
		return get().user;
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator get() {
		return plugin;
	}

	public static void download(String url, File file) {
		if (get().engineStatusMonitor.isRunning()) {
			client().with(session().getIdentity()).download(url, file);
		}
	}

	public static void post(Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().post(Message.create(get().engineStatusMonitor.getSessionId(), object));
		}
	}

	public static Future<IMessage> ask(Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			return get().engineStatusMonitor.getBus()
					.ask(Message.create(get().engineStatusMonitor.getSessionId(), object));
		}
		return null;
	}

	public static void reply(IMessage original, Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().post(
					Message.create(get().engineStatusMonitor.getSessionId(), object).inResponseTo(original.getId()));
		}
	}

	public static void reply(String originalMessageId, Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().post(
					Message.create(get().engineStatusMonitor.getSessionId(), object).inResponseTo(originalMessageId));
		}
	}

	public static void post(Consumer<IMessage> responseHandler, Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().post(Message.create(get().engineStatusMonitor.getSessionId(), object),
					responseHandler);
		}
	}

	public static void subscribe(String identity, Object receiver) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().subscribe(identity, receiver);
		}
	}

	public static void unsubscribe(String identity, Object receiver) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().unsubscribe(identity, receiver);
		}
	}

	public static void unsubscribe(String identity) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().unsubscribe(identity);
		}
	}

}
