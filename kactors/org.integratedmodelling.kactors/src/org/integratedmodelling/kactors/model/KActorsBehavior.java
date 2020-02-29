package org.integratedmodelling.kactors.model;

import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kim.api.IKimAnnotation;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Syntactic peer for a k.Actors application, to be turned into an
 * appropriate actor system in the engine.
 * 
 * @author Ferd
 *
 */
public class KActorsBehavior implements IKActorsBehavior {

	public KActorsBehavior(Model model) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSourceCode() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
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

}
