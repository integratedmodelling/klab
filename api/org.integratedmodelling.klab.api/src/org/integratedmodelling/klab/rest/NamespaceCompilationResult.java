package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

public class NamespaceCompilationResult {

    private String namespaceId;
    private List<CompileNotificationReference> notifications = new ArrayList<>();
    private boolean publishable;
    private List<ModelCompilationResult> models = new ArrayList<>();
    
    public NamespaceCompilationResult() {
    }

    public NamespaceCompilationResult(String namespaceId, boolean publishable, List<CompileNotificationReference> nrefs) {
        this.setNamespaceId(namespaceId);
        this.setPublishable(publishable);
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

	public boolean isPublishable() {
		return publishable;
	}

	public void setPublishable(boolean isPublishable) {
		this.publishable = isPublishable;
	}

	public List<ModelCompilationResult> getModels() {
		return models;
	}

	public void setModels(List<ModelCompilationResult> models) {
		this.models = models;
	}

}
