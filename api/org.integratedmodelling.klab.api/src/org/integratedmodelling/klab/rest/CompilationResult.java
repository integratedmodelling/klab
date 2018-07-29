package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class CompilationResult {

    private String namespaceId;
    private List<CompileNotificationReference> notifications = new ArrayList<>();

    public CompilationResult() {
    }

    public CompilationResult(String namespaceId, List<CompileNotificationReference> nrefs) {
        this.setNamespaceId(namespaceId);
        this.notifications.addAll(nrefs);
    }

    public List<CompileNotificationReference> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<CompileNotificationReference> notifications) {
        this.notifications = notifications;
    }

    @Override
    public String toString() {
        return "CompilationResult [namespaceId = " + namespaceId + ", notifications=" + notifications + "]";
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.namespaceId = namespaceId;
    }

}
