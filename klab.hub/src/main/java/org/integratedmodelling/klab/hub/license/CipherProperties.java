package org.integratedmodelling.klab.hub.license;

import java.io.IOException;
import java.security.NoSuchProviderException;
import java.util.Properties;

import org.bouncycastle.openpgp.PGPException;

public class CipherProperties {	
	   //use getShape method to get object of type shape 
	   public Properties getCipherProperties(LicenseConfiguration configuration, String cipher) throws NoSuchProviderException, IOException, PGPException{
	      if(configuration.getClass() == BouncyConfiguration.class){
	    	  return new BouncyLicense().getPropertiesFromCipher(cipher, configuration);
	      }
	      if(configuration.getClass() == LegacyConfiguration.class){
	    	  return new LegacyLicense().getPropertiesFromCipher(cipher, configuration);
	      }
	      return null;
	   }
}
