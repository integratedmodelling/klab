package org.integratedmodelling.klab.api.services;

public interface IAuthenticationService {

    /**
     * Return the public key for certificates.
     * 
     * @return
     */
    String getKeyring();

}
