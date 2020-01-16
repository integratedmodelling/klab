package org.integratedmodelling.klab.hub.licenses.services;

import java.io.IOException;

import org.bouncycastle.openpgp.PGPException;
import org.integratedmodelling.klab.hub.license.ArmoredKeyPair;

public interface PgpKeyService {
	
	public ArmoredKeyPair generateKeys(int keySize, String userId, String email, String password) throws PGPException, IOException;

}
