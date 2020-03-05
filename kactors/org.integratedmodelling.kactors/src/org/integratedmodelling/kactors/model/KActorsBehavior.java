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
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.kim.api.IKimMetadata;

/**
 * Syntactic peer for a k.Actors application, to be turned into an
 * appropriate actor system in the engine.
 * 
 * @author Ferd
 *
 */
public class KActorsBehavior extends KActorStatement implements IKActorsBehavior  {

	String name;
	private File file;
	private List<IKActorsBehavior> imports = new ArrayList<>();
	private List<IKActorsAction> actions = new ArrayList<>();
	
	public KActorsBehavior(Model model, BehaviorDescriptor descriptor) {
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

	private void loadPreamble(Preamble preamble) {
		this.name = preamble.getName();
		// TODO the rest
		for (String s : preamble.getImports()) {
			IKActorsBehavior imported = KActors.INSTANCE.getBehavior(s);
			if (imported != null) {
				this.imports.add(imported);
			}
		}
	}

	@Override
	public String getSourceCode() {
		// TODO Auto-generated method stub
		return null;
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
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public int getFirstLine() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getLastLine() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getFirstCharOffset() {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int getLastCharOffset() {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public List<IKimAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public String getDeprecation() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public boolean isDeprecated() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isErrors() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public boolean isWarnings() {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//	@Override
//	public IKimMetadata getMetadata() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
