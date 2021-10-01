package org.integratedmodelling.klab.rest;

import org.integratedmodelling.klab.api.provenance.IArtifact;

public class CodelistReference {

    private String id;
    private String name;
    private String description;
    private AuthenticatedIdentity source;
    private MappingReference directMapping;
    private MappingReference inverseMapping;
    private String authorityId;
    private String rootConceptId;
    private boolean isAuthority;
    private String worldview;
    private IArtifact.Type type;

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

}
