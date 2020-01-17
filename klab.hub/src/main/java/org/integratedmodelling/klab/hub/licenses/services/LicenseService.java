package org.integratedmodelling.klab.hub.licenses.services;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.license.LicenseConfiguration;
import org.integratedmodelling.klab.hub.nodes.MongoNode;
import org.springframework.stereotype.Service;

@Service
public interface LicenseService {
	public byte[] generateCertFile(MongoNode node) throws GeneralSecurityException, IOException, PGPException;
	public Properties getPropertiesString(MongoNode node);
	public Properties readCertFileContent(String certFileContent) throws IOException, PGPException;
	public String get_NODE_CERT_FILE_NAME();
	public LicenseConfiguration generateNewLicenseConfiguration();
}
