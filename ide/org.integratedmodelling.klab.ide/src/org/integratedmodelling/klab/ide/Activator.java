package org.integratedmodelling.klab.ide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wb.swt.ResourceManager;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.client.http.EngineMonitor;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.model.KlabEngine;
import org.integratedmodelling.klab.ide.model.KlabExplorer;
import org.integratedmodelling.klab.ide.model.KlabSession;
import org.integratedmodelling.klab.ide.utils.Eclipse;
import org.integratedmodelling.klab.monitoring.Message;
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
	private KlabEngine engine;
	private KlabExplorer explorer;
	private KlabSession session;

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
				return null;
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

		plugin = this;

		this.engineStatusMonitor.start(relayId);

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
		 * offer to import any k.LAB local projects that are not in the workspace. TODO
		 * may also offer to import the worldview for developers.
		 */
		Display.getDefault().asyncExec(() -> {
			
			List<ProjectReference> missingProjects = new ArrayList<>();
			for (ProjectReference project : this.engineStatusMonitor.getCapabilities().getLocalWorkspaceProjects()) {
				if (Eclipse.INSTANCE.getProject(project.getName()) == null) {
					missingProjects.add(project);
				}
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
