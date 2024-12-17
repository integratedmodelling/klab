package org.integratedmodelling.klab.engine.rest.api;

import javax.validation.constraints.NotEmpty;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("engine")
public class EngineProperties {

    public EnvProperties env;

    public EnvProperties getEnv() {
        return env;
    }

    public void setEnv(EnvProperties env) {
        this.env = env;
    }

    @Validated
    public static class EnvProperties {

        @NotEmpty
        private String appBaseUrl;

        @NotEmpty
        private String keycloakUrl;

        public String getAppBaseUrl() {
            return appBaseUrl;
        }

        public void setAppBaseUrl(String appBaseUrl) {
            this.appBaseUrl = appBaseUrl;
        }

        public String getKeycloakUrl() {
            return keycloakUrl;
        }

        public void setKeycloakUrl(String keycloakUrl) {
            this.keycloakUrl = keycloakUrl;
        }
    }

}
