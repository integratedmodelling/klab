package org.integratedmodelling.klab.ide;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.ui.plugin.AbstractUIPlugin;
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
import org.integratedmodelling.klab.client.messaging.StompMessageBus;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.kim.KlabExplorer;
import org.integratedmodelling.klab.ide.kim.KlabSession;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.BrowserUtils;
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
	String relayId = NameGenerator.shortUUID();

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

		this.engineStatusMonitor.start(relayId);

	}

	public String getRelayId() {
		return relayId;
	}

	private void engineOff() {
		// TODO save session data
		// TODO reassess UI
		System.out.println("ENGINE WENT OFF");
	}

	private void engineOn() {

		String sessionId = this.engineStatusMonitor.getSessionId();
		
		this.engineStatusMonitor.subscribe(sessionId, new KlabSession(sessionId));
		this.engineStatusMonitor.subscribe(relayId, new KlabExplorer(relayId));

		// TODO remove/improve
		BrowserUtils.startBrowser("http://localhost:8283/modeler/ui/viewer?session=" + sessionId + "&mode=ide");
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator get() {
		return plugin;
	}

	public void post(Object... object) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.post(Message.create(engineStatusMonitor.getSessionId(), object));
		}
	}

	public void post(Consumer<IMessage> responseHandler, Object... object) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.post(Message.create(engineStatusMonitor.getSessionId(), object), responseHandler);
		}
	}

}
