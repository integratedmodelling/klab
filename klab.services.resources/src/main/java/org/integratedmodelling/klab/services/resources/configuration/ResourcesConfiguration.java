package org.integratedmodelling.klab.services.resources.configuration;

import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.klab.authentication.ResourcePrivileges;

/**
 * The resource service configuration is a POJO serialized from/to the resources.yaml file in the
 * .klab directory.
 * 
 * @author Ferd
 *
 */
public class ResourcesConfiguration implements Serializable {

    private static final long serialVersionUID = 8407649258899502009L;

    public static class ProjectConfiguration implements Serializable {

        private static final long serialVersionUID = -8989429880321748157L;

        private URL sourceUrl;
        private String referenceWorldview;
        private boolean worldview;
        private ResourcePrivileges privileges;
        private boolean locallyManaged;
        private boolean authoritative;

        public URL getSourceUrl() {
            return sourceUrl;
        }
        public void setSourceUrl(URL sourceUrl) {
            this.sourceUrl = sourceUrl;
        }
        public String getReferenceWorldview() {
            return referenceWorldview;
        }
        public void setReferenceWorldview(String referenceWorldview) {
            this.referenceWorldview = referenceWorldview;
        }
        public boolean isWorldview() {
            return worldview;
        }
        public void setWorldview(boolean worldview) {
            this.worldview = worldview;
        }
        public ResourcePrivileges getPrivileges() {
            return privileges;
        }
        public void setPrivileges(ResourcePrivileges privileges) {
            this.privileges = privileges;
        }
        public boolean isLocallyManaged() {
            return locallyManaged;
        }
        public void setLocallyManaged(boolean locallyManaged) {
            this.locallyManaged = locallyManaged;
        }
        public boolean isAuthoritative() {
            return authoritative;
        }
        public void setAuthoritative(boolean authoritative) {
            this.authoritative = authoritative;
        }

    }

    /**
     * The service work directory path within the work directory
     */
    private String servicePath = "resources";
    private String localResourcePath = "resources/local";
    private String publicResourcePath = "resources/public";

    /**
     * Each workspace name is a subdirectory with a number of projects in them. All are relative to
     * the resource path.
     */
    private Map<String, List<String>> workspaces = new HashMap<>();

    /**
     * Each project managed by this
     */
    private Map<String, ProjectConfiguration> projectConfiguration = new HashMap<>();

    public String getServicePath() {
        return servicePath;
    }

    public void setServicePath(String servicePath) {
        this.servicePath = servicePath;
    }

    public Map<String, List<String>> getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(Map<String, List<String>> workspaces) {
        this.workspaces = workspaces;
    }

    public Map<String, ProjectConfiguration> getProjectConfiguration() {
        return projectConfiguration;
    }

    public void setProjectConfiguration(Map<String, ProjectConfiguration> projectConfiguration) {
        this.projectConfiguration = projectConfiguration;
    }

    public String getLocalResourcePath() {
        return localResourcePath;
    }

    public void setLocalResourcePath(String localResourcePath) {
        this.localResourcePath = localResourcePath;
    }

    public String getPublicResourcePath() {
        return publicResourcePath;
    }

    public void setPublicResourcePath(String publicResourcePath) {
        this.publicResourcePath = publicResourcePath;
    }

}
