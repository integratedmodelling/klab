package org.integratedmodelling.kactors.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsAction;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Definition;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.kactors.Preamble;
import org.integratedmodelling.kactors.model.KActors.BehaviorDescriptor;
import org.integratedmodelling.klab.Version;

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
	private List<IKActorsBehavior> imports = new ArrayList<>();
	private List<IKActorsAction> actions = new ArrayList<>();

	public KActorsBehavior(Model model, BehaviorDescriptor descriptor) {
		
		super(model, null);
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
		
		this.observable = preamble.getObservable();
		this.style = preamble.getStyle();
		
		if (preamble.isApp()) {
			this.type = Type.APP;
		} else if (preamble.isLibrary()) {
			this.type = Type.TRAITS;
		} else if (preamble.isUser()) {
			this.type = Type.USER;
		} else if (preamble.isTest()) {
			this.type = Type.UNITTEST;
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
		// TODO Auto-generated method stub
		return null;
	}

}
