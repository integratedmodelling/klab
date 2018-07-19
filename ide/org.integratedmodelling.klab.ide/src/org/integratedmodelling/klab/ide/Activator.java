package org.integratedmodelling.klab.ide;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.logging.Level;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.integratedmodelling.kim.api.IKimConcept.Type;
import org.integratedmodelling.kim.api.IKimStatement;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.Kim;
import org.integratedmodelling.kim.model.Kim.UrnDescriptor;
import org.integratedmodelling.kim.model.Kim.Validator;
import org.integratedmodelling.kim.model.KimWorkspace;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.client.http.EngineMonitor;
import org.integratedmodelling.klab.ide.kim.KimData;
import org.integratedmodelling.klab.ide.model.KlabEngine;
import org.integratedmodelling.klab.ide.model.KlabExplorer;
import org.integratedmodelling.klab.ide.model.KlabSession;
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
	String relayId = "relay"+NameGenerator.shortUUID();
	private KimWorkspace kimWorkspace;

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

		// TODO this is a caret listener for the k.IM editors - somehow the 
//		workbenchWindow.getActivePage().addPartListener(new PartListener() {
//		    public void partOpened(IWorkbenchPartReference partRef) {
//		        //Check if this is an editor and its input is what I need
//		        AbstractTextEditor e =
//		            (AbstractTextEditor)((IEditorReference) partRef).getEditor(false);
//		        ((StyledText)e.getAdapter(Control.class)).addCaretListener(l);
//		    }
//		});
		
		plugin = this;

		URI uri = ResourcesPlugin.getWorkspace().getRoot().getLocationURI();
		List<File> projectRoots = new ArrayList<>();
		for (IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {
			projectRoots.add(project.getLocation().toFile());
		}

		/**
		 * TODO presync the worldview. Use an interval and lock file to avoid multiple
		 * loads from engine or new instances.
		 */

		/**
		 * Preload the workspace so that the navigator can work right away.
		 */
		this.kimWorkspace = Kim.INSTANCE.loadWorkspace(uri.toString(), projectRoots);
		
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
		/**
		 * Add communication peers for engine, session and explorer
		 */
		this.engineStatusMonitor.getBus().subscribe(this.engineStatusMonitor.getEngineId(), new KlabEngine());
		this.engineStatusMonitor.getBus().subscribe(sessionId, new KlabSession(sessionId));
		this.engineStatusMonitor.getBus().subscribe(relayId, new KlabExplorer(relayId));

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
			engineStatusMonitor.getBus().post(Message.create(engineStatusMonitor.getSessionId(), object));
		}
	}

	public void post(Consumer<IMessage> responseHandler, Object... object) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.getBus().post(Message.create(engineStatusMonitor.getSessionId(), object), responseHandler);
		}
	}

	public void subscribe(String identity, Object receiver) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.getBus().subscribe(identity, receiver);
		}
	}

	public void unsubscribe(String identity, Object receiver) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.getBus().unsubscribe(identity, receiver);
		}
	}

	public void unsubscribe(String identity) {
		if (engineStatusMonitor.isRunning()) {
			engineStatusMonitor.getBus().unsubscribe(identity);
		}
	}

}
