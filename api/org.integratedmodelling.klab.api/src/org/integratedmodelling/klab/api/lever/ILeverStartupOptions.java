package org.integratedmodelling.klab.api.lever;

import org.integratedmodelling.klab.api.engine.IStartupOptions;

/**
 * The Interface ILeverStartupOptions.
 *
 * @author steven.wohl
 * @version $Id: $Id
 */
public interface ILeverStartupOptions extends IStartupOptions{
	
	public static final String BANNER = "\r\n";
	
    String getCertificateResource();
    
    /**
     * URL of the authenticating hub. Default is set in the certificate.
     * 
     * @return the URL of the authenticating hub.
     */
    String getLeverUrl();

    /**
     * Name of this Lever. Tied to -name. Default is in the certificate.
     * 
     * @return the alternative node name
     */
	String getLeverName();

}
