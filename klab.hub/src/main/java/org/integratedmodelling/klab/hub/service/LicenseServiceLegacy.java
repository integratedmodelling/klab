package org.integratedmodelling.klab.hub.service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SignatureException;
import java.util.Properties;

import org.apache.commons.codec.DecoderException;
import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.api.Agreement;
import org.integratedmodelling.klab.hub.api.MongoNode;
import org.integratedmodelling.klab.hub.api.User;

public interface LicenseServiceLegacy {

//	byte[] generateCert(User user);

	byte[] generateCert(MongoNode node);

//	byte[] generateCertFile(User user)
//			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, PGPException;

	byte[] generateCertFile(MongoNode node)
			throws IOException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, PGPException;

	//Properties getPropertiesString(User user);

	Properties getPropertiesString(MongoNode node) throws IOException;

	Properties readCertFileContent(String certFileContent) throws IOException, PGPException, DecoderException;

	String get_ENGINE_CERT_FILE_NAME();

	String get_NODE_CERT_FILE_NAME();

    Properties getPropertiesString(User user, Agreement agreement);

    byte[] generateCertFile(User user, Agreement agreement)
            throws IOException, NoSuchAlgorithmException, NoSuchProviderException, SignatureException, PGPException;

    byte[] generateCert(User user, Agreement agreement);

}