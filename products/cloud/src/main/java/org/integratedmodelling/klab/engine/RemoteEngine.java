package org.integratedmodelling.klab.engine;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.integratedmodelling.klab.Authentication;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Logo;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.integratedmodelling.klab.auth.AnonymousEngineCertificate;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.services.ConsulDnsService;
import org.integratedmodelling.klab.exceptions.KlabAuthorizationException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.springframework.beans.factory.annotation.Autowired;

public class RemoteEngine extends Engine {
	
    /**
	 * 
	 */
	@Autowired
	ConsulDnsService consulDnsService;
	
	private static final long serialVersionUID = -7180871922872370852L;
	
	public RemoteEngine(ICertificate certificate) {
        super(certificate);
        //consulDnsService.setIntialServiceWeight();
    }
    
   
	public static RemoteEngine start(ICertificate certificate, IEngineStartupOptions options) {

		if (certificate == null) {

			if (options.isAnonymous()) {
				certificate = new AnonymousEngineCertificate();
			} else {

				if (options.getCertificateResource() != null) {
					certificate = KlabCertificate.createFromClasspath(options.getCertificateResource());
				} else {
					File certFile = options.getCertificateFile();
					if (!certFile.exists()) {
						// check for legacy certificate
						certFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "im.cert");
					}
					certificate = certFile.exists() ? KlabCertificate.createFromFile(certFile)
							: KlabCertificate.createDefault();
				}
			}
		}

		if (!certificate.isValid()) {
			throw new KlabAuthorizationException("certificate is invalid: " + certificate.getInvalidityCause());
		}

		RemoteEngine ret = new RemoteEngine(certificate);

		if (!ret.boot(options)) {
			throw new KlabException("engine failed to start");
		}

		if (certificate.getType() == ICertificate.Type.ENGINE) {
			System.out.println("\n" + Logo.ENGINE_BANNER);
			System.out.println(
					"\nStartup successful: " + ret.getUsername() + " v" + Version.CURRENT + " on " + new Date());
		}
		return ret;
	}
	
	@Override
	protected void closeExpiredSessions() {
		
	}

	private Set<Session> activeSessions(){
		Set<Session> sessions = new HashSet<>();
		Authentication.INSTANCE.getSessions().forEach(s -> {
			Session sesh = Authentication.INSTANCE.getIdentity(s.getId(), Session.class);
			sessions.add(sesh);
		});
		return sessions;
	}
}
