package org.integratedmodelling.klab.hub.api;

import java.util.Properties;

import org.integratedmodelling.klab.hub.exception.BadRequestException;

//import javax.ws.rs.BadRequestException;

import org.integratedmodelling.klab.hub.licenses.services.LicenseConfigService;

public class LicenseGenerator {
    
    public LicenseGenerator(LicenseConfigService configService) {
		super();
		this.configService = configService;
	}
	
    LicenseConfigService configService;

	public byte[] generate(ProfileResource profile, String configId) {
	    LicenseConfiguration config;
	    
	    if(configId == null) {
	        config = configService.getDefaultConfig();
	    } else {
	        config = configService.getConfigByKey(configId);
	    }
	    
	    Properties props = PropertiesFactory.fromProfile(profile, config).getProperties();
	    
	    //Properties engineProperties = PropertiesFactory.fromProfile(profile, c).getProperties();
	    
		if(config.getClass().getName().equals(BouncyConfiguration.class.getName())) {
			return new BouncyLicense().generate(props, config);
		}
		if(config.getClass().getName().equals(LegacyConfiguration.class.getName())) {
			return new LegacyLicense().generate(props, config);
		} else {
			throw new BadRequestException("Bad request");
		}
	}
	
	public byte[] generate(MongoNode node, String configId) {
	    LicenseConfiguration config;
        
        if(configId == null) {
            config = configService.getDefaultConfig();
        } else {
            config = configService.getConfigByKey(configId);
        }
        
        Properties props = PropertiesFactory.fromNode(node, config).getProperties();
        
        //Properties engineProperties = PropertiesFactory.fromProfile(profile, c).getProperties();
        
        if(config.getClass().getName().equals(BouncyConfiguration.class.getName())) {
            return new BouncyLicense().generate(props, config);
        }
        if(config.getClass().getName().equals(LegacyConfiguration.class.getName())) {
            return new LegacyLicense().generate(props, config);
        } else {
            throw new BadRequestException("Bad request");
        }
    }
   
}
