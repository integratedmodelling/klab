package org.integratedmodelling.klab.ide;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.core.resources.IProject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimLoader;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.kim.model.KimLoader;
import org.integratedmodelling.kim.ui.internal.KimActivator;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.client.http.EngineMonitor;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.model.Klab;
import org.integratedmodelling.klab.ide.model.KlabEngine;
import org.integratedmodelling.klab.ide.model.KlabExplorer;
import org.integratedmodelling.klab.ide.model.KlabSession;
import org.integratedmodelling.klab.ide.model.KlabUser;
import org.integratedmodelling.klab.ide.navigator.model.beans.EResourceReference;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.ProjectLoadRequest;
import org.integratedmodelling.klab.rest.ProjectReference;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Pair;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

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

		/*
		 * TODO retrieve from preferences if so configured.
		 */
		String initialSessionId = null;

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

		});

		plugin = this;

		this.engineStatusMonitor = new EngineMonitor(EngineMonitor.ENGINE_DEFAULT_URL, () -> engineOn(),
				() -> engineOff(), initialSessionId);

		/*
		 * setup the language helper with access to the grammar
		 */
		Kim.INSTANCE.setup(KimActivator.getInstance().getInjector(KimActivator.ORG_INTEGRATEDMODELLING_KIM_KIM));
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

		this.engineStatusMonitor.start(relayId);

	}

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

		System.out.println("--------------\nEngine went off\n----------------");
	}

	private void engineOn() {

		String sessionId = this.engineStatusMonitor.getSessionId();
		/**
		 * Add communication peers for engine, session and explorer
		 */
		this.engine = new KlabEngine(this.engineStatusMonitor.getEngineId());
		this.session = new KlabSession(sessionId);
		this.explorer = new KlabExplorer(relayId);
		this.engineStatusMonitor.getBus().subscribe(this.engineStatusMonitor.getEngineId(), this.engine);
		this.engineStatusMonitor.getBus().subscribe(sessionId, this.session);
		this.engineStatusMonitor.getBus().subscribe(relayId, this.explorer);
		this.engine.send(Message.create(this.engineStatusMonitor.getEngineId(), IMessage.MessageClass.EngineLifecycle,
				IMessage.Type.EngineUp, this.engineStatusMonitor.getCapabilities()));

		/*
		 * offer to import any k.LAB local projects that are not in the workspace and
		 * have the engine load those projects it does not have. TODO may also offer to
		 * import the worldview for developers.
		 */
		Display.getDefault().asyncExec(() -> {

			List<ProjectReference> missingProjects = new ArrayList<>();
			Set<String> localProjectIds = new HashSet<>();
			for (ProjectReference project : this.engineStatusMonitor.getCapabilities().getLocalWorkspaceProjects()) {
				if (Eclipse.INSTANCE.getProject(project.getName()) == null) {
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

	public static void post(Object... object) {
		if (get().engineStatusMonitor.isRunning()) {
			get().engineStatusMonitor.getBus().post(Message.create(get().engineStatusMonitor.getSessionId(), object));
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
