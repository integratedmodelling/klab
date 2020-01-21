package org.integratedmodelling.klab.hub.license.commands;

import java.io.IOException;
import java.util.Properties;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.auth.KlabCertificate;
import org.integratedmodelling.klab.hub.exception.AuthenticationFailedException;
import org.integratedmodelling.klab.hub.exception.BadRequestException;
import org.integratedmodelling.klab.hub.licenses.services.LicenseService;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.joda.time.DateTime;

public class EvaluateNodeLicenseProperties {
	
	private MongoNode node;
	private LicenseService service;
	private String certificate;
	
	public EvaluateNodeLicenseProperties(MongoNode node, LicenseService service, String certificate) {
		super();
		this.node = node;
		this.service = service;
		this.certificate = certificate;
	}
	
	public boolean execute() {
		
		Properties certProperties = null;

		try {
			certProperties = service.readCertFileContent(certificate);
		} catch (IOException | PGPException e) {
			throw new BadRequestException("Problem deciphering certificate");
		}
        
        DateTime expiry = DateTime.parse(certProperties.getProperty(KlabCertificate.KEY_EXPIRATION));
        
		if (expiry.isBeforeNow()) {
			String msg = String.format("The cert file submitted for node %s is expired.", node.getNode());
			throw new AuthenticationFailedException(msg);
		}
        
        Properties nodeProperties = service.getPropertiesString(node);
        
		certProperties.remove(KlabCertificate.KEY_EXPIRATION);
		nodeProperties.remove(KlabCertificate.KEY_EXPIRATION);
		
		if (certProperties.equals(nodeProperties)) {
			return true;
		} else {
			return false;
		}
	}

}
