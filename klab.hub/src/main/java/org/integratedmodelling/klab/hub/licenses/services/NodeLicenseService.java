package org.integratedmodelling.klab.hub.licenses.services;

import java.util.Properties;

import org.integratedmodelling.klab.hub.nodes.MongoNode;

public interface NodeLicenseService {
	public byte[] generateCert(MongoNode node);
	public byte[] generateCertFile(MongoNode node);
	public Properties getPropertiesString(MongoNode node);
	public Properties readCertFileContent(String certFileContent);
	public String get_NODE_CERT_FILE_NAME();
}
