package org.integratedmodelling.klab.components.runtime.actors.behavior;

import java.util.List;

import org.integratedmodelling.klab.api.IStatement;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.model.IKimObject;

public class Action implements IKimObject {
	
	/**
	 * Each action is a sequence of asynchronous call groups.
	 */
	private CallSequence calls;

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IStatement getStatement() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IKimObject> getChildren() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAnnotation> getAnnotations() {
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

}
