package org.integratedmodelling.klab.hub.api;

import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("hub")
public class HubProperties {

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
        private String baseUrl;

        private String environment;

        @NotEmpty
        private String keycloakUrl;

        private String staticBaseUrl;

        public String getAppBaseUrl() {
            return appBaseUrl;
        }

        public void setAppBaseUrl(String appBaseUrl) {
            this.appBaseUrl = appBaseUrl;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public String getEnvironment() {
            return environment;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public String getKeycloakUrl() {
            return keycloakUrl;
        }

        public void setKeycloakUrl(String keycloakUrl) {
            this.keycloakUrl = keycloakUrl;
        }

        public String getStaticBaseUrl() {
            return staticBaseUrl;
        }

        public void setStaticBaseUrl(String staticBaseUrl) {
            this.staticBaseUrl = staticBaseUrl;
        }
    }

}
