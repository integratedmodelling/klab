package org.integratedmodelling.klab.hub.security.oauth2;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {
	
	//could add the family name and given name maybe we should ask for the OpenId?

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }
}