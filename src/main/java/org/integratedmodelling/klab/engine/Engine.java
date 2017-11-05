package org.integratedmodelling.klab.engine;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.engine.ICapabilities;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.runtime.ISession;

public class Engine implements IEngine {

	/*
	 * Exists after engine creation; other user may be authenticated using any strategy
	 * the engine wants to implement.
	 */
	IEngineUserIdentity user;
	
	
	public Engine(ICertificate certificate) {
		// TODO
	}
	
	@Override
	public INetworkSessionIdentity getParentIdentity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToken() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends IIdentity> T getParentIdentity(Class<? extends IIdentity> type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEngineUserIdentity authenticateUser(IUserCredentials credentials) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICapabilities getCapabilities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ISession createSession(IEngineUserIdentity user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IEngineUserIdentity getUser() {
		return user;
	}
	
	/**
	 * Create an engine using the default k.LAB certificate and start it. Return after startup
	 * is complete.
	 * 
	 * @return a new running engine, or null if startup failed.
	 */
	public static IEngine start() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
