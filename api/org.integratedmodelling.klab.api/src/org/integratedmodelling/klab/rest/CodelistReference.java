package org.integratedmodelling.klab.rest;

import java.util.HashMap;
import java.util.Map;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class CodelistReference {

    private String id;
    private String name;
    private String description;
    private Map<String, String> codeDescriptions = new HashMap<>();
    private AuthenticatedIdentity source;
    private MappingReference directMapping;
    private MappingReference inverseMapping;
    private String authorityId;
    private String rootConceptId;
    private boolean isAuthority;
    private String worldview;
    private IArtifact.Type type;
    private String agency;
    private String version;
    private Map<String, String> metadata = new HashMap<>();
    
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public AuthenticatedIdentity getSource() {
        return source;
    }
    public void setSource(AuthenticatedIdentity source) {
        this.source = source;
    }
    public MappingReference getDirectMapping() {
        return directMapping;
    }
    public void setDirectMapping(MappingReference directMapping) {
        this.directMapping = directMapping;
    }
    public MappingReference getInverseMapping() {
        return inverseMapping;
    }
    public void setInverseMapping(MappingReference inverseMapping) {
        this.inverseMapping = inverseMapping;
    }
    public String getAuthorityId() {
        return authorityId;
    }
    public void setAuthorityId(String authorityId) {
        this.authorityId = authorityId;
    }
    public String getRootConceptId() {
        return rootConceptId;
    }
    public void setRootConceptId(String rootConceptId) {
        this.rootConceptId = rootConceptId;
    }

    /**
     * If true, this codelist can be exposed as an authority and the authorityId is its identifier.
     * Otherwise, if an authorityId is present, it refers to an external authority this codelist
     * maps to.
     * 
     * @return
     */
    public boolean isAuthority() {
        return isAuthority;
    }
    public void setAuthority(boolean isAuthority) {
        this.isAuthority = isAuthority;
    }
    public String getWorldview() {
        return worldview;
    }
    public void setWorldview(String worldview) {
        this.worldview = worldview;
    }
    public IArtifact.Type getType() {
        return type;
    }
    public void setType(IArtifact.Type type) {
        this.type = type;
    }
    public String getAgency() {
        return agency;
    }
    public void setAgency(String agency) {
        this.agency = agency;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public Map<String, String> getMetadata() {
        return metadata;
    }
    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }
    public Map<String, String> getCodeDescriptions() {
        return codeDescriptions;
    }
    public void setCodeDescriptions(Map<String, String> codeDescriptions) {
        this.codeDescriptions = codeDescriptions;
    }

}
