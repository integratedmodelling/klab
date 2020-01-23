package org.integratedmodelling.klab.hub.licenses.services;

import name.neuhalfen.projects.crypto.bouncycastle.openpgp.keys.keyrings.KeyringConfig;

public interface BouncyGpgService {
	public KeyringConfig createKeyRing();
}
