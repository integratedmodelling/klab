package org.integratedmodelling.klab.rest;

public class DocumentationReference {

    private String docId;
    private String projectName;

    public DocumentationReference() {
    }

    public DocumentationReference(String docId, String name) {
        this.docId = docId;
        this.projectName = name;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
}
