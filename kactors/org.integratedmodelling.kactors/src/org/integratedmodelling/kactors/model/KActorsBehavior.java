package org.integratedmodelling.kactors.model;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Definition;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.kactors.Preamble;
import org.integratedmodelling.kactors.model.KActors.BehaviorDescriptor;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.rest.BehaviorReference;

/**
 * Syntactic peer for a k.Actors application, to be turned into an appropriate
 * actor system in the engine.
 * 
 * @author Ferd
 *
 */
public class KActorsBehavior extends KActorCodeStatement implements IKActorsBehavior {

	private String name;
	private Version version;
	private String observable;
	private Type type = Type.BEHAVIOR;
	private Platform platform = Platform.ANY;
	private File file;
	private String style;
	private Map<String, String> styleSpecs;
	private List<IKActorsBehavior> imports = new ArrayList<>();
	private List<IKActorsAction> actions = new ArrayList<>();
	private String label;
	private String description;
	private String logo;
	private String projectId;
	private boolean isPublic;

	public KActorsBehavior(Model model, BehaviorDescriptor descriptor) {

		super(model, null);
		this.projectId = descriptor.projectName;
		if (model.getPreamble() != null) {
			loadPreamble(model.getPreamble());
		}
		if (descriptor != null) {
			this.file = descriptor.file;
		}
		for (Definition definition : model.getDefinitions()) {
			actions.add(new KActorsAction(definition, this));
		}
	}

	public KActorsBehavior() {
		this.name = "Empty behavior";
	}

	private void loadPreamble(Preamble preamble) {

		this.name = preamble.getName();

		// TODO metadata and the like
		if (preamble.getVersion() != null) {
			this.version = Version.create(preamble.getVersion());
		}

		if (preamble.getObservable() != null) {
			this.observable = KActorsValue.parseObservable(preamble.getObservable());
		}
		this.style = preamble.getStyle();
		this.label = preamble.getLabel();
		this.description = preamble.getDescription();
		this.logo = preamble.getLogo();
		this.isPublic = preamble.isPublic();

		if (preamble.getInlineStyle() != null) {
			this.styleSpecs = new LinkedHashMap<>();
			Map<KActorsValue, KActorsValue> map = KActorsValue.parseMap(preamble.getInlineStyle(), this);
			// turn into a string map for later serialization
			for (Entry<KActorsValue, KActorsValue> entry : map.entrySet()) {
				this.styleSpecs.put(entry.getKey().getValue().toString(), entry.getValue().getValue().toString());
			}
		}

		if (preamble.isApp()) {
			this.type = Type.APP;
		} else if (preamble.isLibrary()) {
			this.type = Type.TRAITS;
		} else if (preamble.isUser()) {
			this.type = Type.USER;
		} else if (preamble.isTest()) {
			this.type = Type.UNITTEST;
		} else if (preamble.isComponent()) {
			this.type = Type.COMPONENT;
		} else if (preamble.isTask()) {
			this.type = Type.TASK;
		}

		if (preamble.isDesktop()) {
			this.platform = Platform.DESKTOP;
		} else if (preamble.isWeb()) {
			this.platform = Platform.WEB;
		} else if (preamble.isMobile()) {
			this.platform = Platform.MOBILE;
		}

		for (String s : preamble.getImports()) {
			IKActorsBehavior imported = KActors.INSTANCE.getBehavior(s);
			if (imported != null) {
				this.imports.add(imported);
			}
		}
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Type getType() {
		return type;
	}

	@Override
	public List<IKActorsBehavior> getImports() {
		return imports;
	}

	@Override
	public List<IKActorsAction> getActions() {
		return actions;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	@Override
	public Platform getPlatform() {
		return platform;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getLogo() {
		return logo;
	}

	public String getObservable() {
		return observable;
	}

	public Version getVersion() {
		return version;
	}

	@Override
	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	@Override
	public Map<String, String> getStyleSpecs() {
		return styleSpecs;
	}

	@Override
	public boolean isPublic() {
		return isPublic;
	}

	/**
	 * Return a descriptor for the behavior. Use the same bean that describes the
	 * classes from the runtime actions coded in Java. Leave the actions empty but
	 * fill in descriptors for logo, description and label.
	 * 
	 * @return
	 */
	public BehaviorReference getReference() {
		BehaviorReference ret = new BehaviorReference();
		ret.setName(this.getName());
		ret.setLabel(this.getLabel());
		ret.setDescription(this.getDescription());
		ret.setLogo(this.getLogo());
		ret.setPlatform(this.platform);
		return ret;
	}

}
