package org.integratedmodelling.klab.api.services;

public interface IAuthService {

    /**
     * Return the public key for certificates.
     * 
     * @return
     */
    String getKeyring();

}
