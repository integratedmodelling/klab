package org.integratedmodelling.klab.engine;

import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.INetworkSessionIdentity;
import org.integratedmodelling.klab.api.auth.IUserCredentials;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.engine.ICapabilities;
import org.integratedmodelling.klab.api.engine.IEngine;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.common.auth.KlabCertificate;

public class Engine implements IEngine {

	ICertificate certificate;

	public Engine(ICertificate certificate) {
		// TODO
		this.certificate = certificate;
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
		IIdentity identity = certificate.getIdentity();
		if (!(identity instanceof IUserIdentity)) {
			// TODO shit all over the place
		}
		return identity instanceof IEngineUserIdentity ? (IEngineUserIdentity) identity
				: null /* TODO make engine user out of other */;
	}

	/**
	 * Create an engine using the default k.LAB certificate and start it. Return
	 * after startup is complete.
	 * 
	 * @return a new running engine, or null if startup failed.
	 */
	public static IEngine start() {
		Engine ret = new Engine(new KlabCertificate());
		if (!ret.boot()) {
			return null;
		}
		return ret;
	}

	private boolean boot() {
		boolean ret = false;
		// setup logging
		// get worldview from certificate and sync it (cache/use cached if not online, fail if offline and no cache) 
		// init Kim listeners
		// load worldview
		// hop on the network
		// sync and read components
		// read workspace from parameters/properties
		// run any init scripts from configuration
		// run any init scripts from parameters
		// init REST services unless specified otherwise
		return ret;
	}

}
