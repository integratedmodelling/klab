package org.integratedmodelling.kactors.model;

import java.util.Iterator;

import org.integratedmodelling.kactors.api.IKactor;
import org.integratedmodelling.kactors.api.IKactorsApplication;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.klab.utils.Pair;

/**
 * Syntactic peer for a k.Actors application, to be turned into an
 * appropriate actor system in the engine.
 * 
 * @author Ferd
 *
 */
public class KactorsApplication implements IKactorsApplication {

	public KactorsApplication(Model model) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getSourceCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Integer, Integer> getBegin() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Pair<Integer, Integer> getEnd() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterator<IKactor> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

}
