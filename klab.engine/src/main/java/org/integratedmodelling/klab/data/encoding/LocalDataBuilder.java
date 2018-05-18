package org.integratedmodelling.klab.data.encoding;

import org.integratedmodelling.kim.api.INotification;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.data.adapters.IKlabData.Builder;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeContext;

/**
 * A builder that encodes the data into an existing (or creates a new) local artifact. The
 * build() step merely moves the finished artifact to the data object.
 * 
 * @author ferdinando.villa
 *
 */
public class LocalDataBuilder implements IKlabData.Builder {

	public LocalDataBuilder(IRuntimeContext context) {
		/*
		 * 
		 */
	}

	@Override
	public Builder startState(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(double doubleValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(float floatValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(int intValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void add(long longValue) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Builder finishState() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder startObject(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder finishObject() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder setProperty(String property, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Builder addNotification(INotification notification) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IKlabData build() {
		// TODO Auto-generated method stub
		return null;
	}

}
