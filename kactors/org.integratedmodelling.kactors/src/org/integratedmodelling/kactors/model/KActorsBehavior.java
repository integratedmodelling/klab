package org.integratedmodelling.kactors.model;

import java.io.File;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
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
public class KActorsBehavior implements IKActorsBehavior {

	String name;
	private File file;
	
	public KActorsBehavior(Model model, BehaviorDescriptor descriptor) {
		this.name = model.getPreamble().getName();
		if (descriptor != null) {
			this.file = descriptor.file;
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

	@Override
	public int getFirstLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastLine() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFirstCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getLastCharOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IKimAnnotation> getAnnotations() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeprecation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isDeprecated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isErrors() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isWarnings() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public IKimMetadata getMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

}
