package org.integratedmodelling.kactors.model;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Level;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.GrammarUtil;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.validation.CheckMode;
import org.eclipse.xtext.validation.IResourceValidator;
import org.eclipse.xtext.validation.Issue;
import org.integratedmodelling.kactors.KactorsStandaloneSetup;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Scope;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.utils.KActorsResourceSorter;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.errormanagement.ICompileNotification;
import org.integratedmodelling.klab.common.CompileNotification;
import org.integratedmodelling.klab.rest.BehaviorReference;

import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Singleton holding the results of parsing behaviors across the workspace.
 * 
 * @author Ferd
 *
 */
public enum KActors {

	INSTANCE;

	private Map<String, BehaviorReference> behaviorManifest = Collections.synchronizedMap(new HashMap<>());

	@Inject
	private IGrammarAccess grammarAccess;

	public interface Notifier {
		void notify(IKActorsBehavior behavior);
	}

	/**
	 * Call before keyword list can be obtained
	 * 
	 * @param injector
	 */
	public void setup(Injector injector) {
		injector.injectMembers(this);
	}

	public interface ValueTranslator {

		/**
		 * Translate to whatever the type requires. If needed, the setData() function of
		 * the container can be used to store costly objects.
		 * 
		 * @param container
		 * @param value
		 * @return
		 */
		Object translate(KActorsValue container, IIdentity identity, Scope scope);
	}

	/**
	 * Install one of these to enable in-editor documentation, highlighting and
	 * call/fire validation
	 * 
	 * @author Ferd
	 *
	 */
	public interface CodeAssistant {

		enum BehaviorId {
			VIEW, SESSION, LOCAL, IMPORTED, OBJECT, STATE, USER, UNKNOWN, AMBIGUOUS, EXPLORER, TEST
		}

		BehaviorId classifyVerb(String call);

		String getLabel(String call);

		String getDescription(String call);

		Collection<KActorsValue.Type> getFiredType(String call);
	}

	class BehaviorDescriptor {
		String name;
		File file;
		List<ICompileNotification> notifications = new ArrayList<>();
		int nInfo;
		int nWarning;
		int nErrors;
		IKActorsBehavior behavior;
		String projectName;
	}

	Injector injector;
	IResourceValidator validator;

	List<Notifier> notifiers = new ArrayList<>();
	private ValueTranslator valueTranslator = null;
	private CodeAssistant codeAssistant = null;
	Map<String, BehaviorDescriptor> behaviors = new HashMap<>();

	private Injector getInjector() {
		if (this.injector == null) {
			this.injector = new KactorsStandaloneSetup().createInjectorAndDoEMFRegistration();
		}
		return this.injector;
	}

	public IKActorsBehavior declare(Model model) {
		return new KActorsBehavior(model, null);
	}

	public Set<String> getKeywords() {
		return GrammarUtil.getAllKeywords(grammarAccess.getGrammar());
	}

	public boolean isKActorsFile(File file) {
		return file.toString().endsWith(".kactor");
	}

	private IResourceValidator getValidator() {
		if (this.validator == null) {
			this.validator = getInjector().getInstance(IResourceValidator.class);
		}
		return this.validator;
	}

	public void addNotifier(Notifier notifier) {
		this.notifiers.add(notifier);
	}

	public void loadResources(List<File> behaviorFiles) {

		KActorsResourceSorter bsort = new KActorsResourceSorter(behaviorFiles);
		IResourceValidator validator = getValidator();

		for (Resource resource : bsort.getResources()) {

			BehaviorDescriptor ret = new BehaviorDescriptor();

			ret.name = ((Model) resource.getContents().get(0)).getPreamble().getName();
			ret.file = bsort.getFile(resource);
			ret.projectName = getProjectName(resource.getURI().toString());

			List<Issue> issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl);
			for (Issue issue : issues) {
				ICompileNotification notification = getNotification(ret.name, issue, ret);
				if (notification != null) {
					ret.notifications.add(notification);
				}
			}

			ret.behavior = new KActorsBehavior(((Model) resource.getContents().get(0)), ret);

			behaviors.put(ret.name, ret);

			for (Notifier notifier : notifiers) {
				notifier.notify(ret.behavior);
			}
		}
	}

	/**
	 * Get a behavior by name.
	 * 
	 * @param id
	 * @return
	 */
	public IKActorsBehavior getBehavior(String id) {
		BehaviorDescriptor desc = behaviors.get(id);
		return desc == null ? null : desc.behavior;
	}

	/**
	 * This will be filled in by the implementation, differently in the engine or
	 * the client.
	 * 
	 * @return
	 */
	public Map<String, BehaviorReference> getBehaviorManifest() {
		return behaviorManifest;
	}

	/**
	 * Return all regular behaviors defined in the src/ directory alongside models
	 * for the project.
	 * 
	 * @param project
	 * @return
	 */
	public List<IKActorsBehavior> getBehaviors(String project, IKActorsBehavior.Type... type) {
		List<IKActorsBehavior> ret = new ArrayList<>();
		for (BehaviorDescriptor bd : behaviors.values()) {
			if (project.equals(bd.projectName)) {
				boolean ok = true;
				if (type != null) {
					ok = false;
					for (IKActorsBehavior.Type t : type) {
						if (bd.behavior.getType() == t) {
							ok = true;
							break;
						}
					}
				}
				if (ok) {
					ret.add(bd.behavior);
				}
			}
		}
		return ret;
	}

	/**
	 * Get the project name from the string form of any Xtext resource URI.
	 * 
	 * @param resourceURI
	 * @return
	 */
	public String getProjectName(String resourceURI) {

		String ret = null;
		try {
			URL url = new URL(resourceURI);
			String path = url.getPath();
			Properties properties = null;
			URL purl = null;
			while ((path = chopLastPathElement(path)) != null) {
				purl = new URL(url.getProtocol(), url.getAuthority(), url.getPort(),
						path + "/META-INF/klab.properties");
				try (InputStream is = purl.openStream()) {
					properties = new Properties();
					properties.load(is);
					break;
				} catch (IOException exception) {
					continue;
				}
			}
			if (properties != null) {
				ret = path.substring(path.lastIndexOf('/') + 1);
			}

		} catch (Exception e) {
			// just return null;
		}

		return ret;
	}

	private ICompileNotification getNotification(String name, Issue issue, BehaviorDescriptor desc) {

		Level level = null;
		ICompileNotification ret = null;

		switch (issue.getSeverity()) {
		case ERROR:
			desc.nErrors++;
			level = Level.SEVERE;
			break;
		case INFO:
			desc.nInfo++;
			level = Level.INFO;
			break;
		case WARNING:
			desc.nWarning++;
			level = Level.WARNING;
			break;
		default:
			break;
		}

		if (level != null) {
			ret = CompileNotification.create(level, issue.getMessage(), name, KActorCodeStatement.createDummy(issue));
		}

		return ret;
	}

	private String chopLastPathElement(String path) {
		int idx = path.lastIndexOf('/');
		if (idx <= 0) {
			return null;
		}
		return path.substring(0, idx);
	}

	public ValueTranslator getValueTranslator() {
		return valueTranslator;
	}

	/**
	 * Installing one of these will enable translation of a value to a type suitable
	 * for the implementation.
	 * 
	 * @param valueTranslator
	 */
	public void setValueTranslator(ValueTranslator valueTranslator) {
		this.valueTranslator = valueTranslator;
	}

	public void add(File file) {
		loadResources(Collections.singletonList(file));
	}

	public void delete(File file) {
		// TODO Auto-generated method stub

	}

	public void touch(File file) {
		loadResources(Collections.singletonList(file));
	}

	public CodeAssistant getCodeAssistant() {
		return codeAssistant;
	}

	public void setCodeAssistant(CodeAssistant codeAssistant) {
		this.codeAssistant = codeAssistant;
	}

}
