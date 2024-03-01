package org.integratedmodelling.klab.hub.api;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.integratedmodelling.klab.rest.CustomProperty;
import org.integratedmodelling.klab.rest.ObservableReference;
import org.springframework.data.annotation.Reference;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Groups")
@TypeAlias("MongoGroup")
public class MongoGroup extends GenericModel {

    private String description;
    private String iconUrl;
    private String sshKey;
    private long defaultExpirationTime;
    private boolean worldview;
    /*
     * If true user should be able to add this
     * group to themselves
     */
    private boolean optIn;
    /* 
     *If true this group is added to new users
     */
    private boolean complimentary;
    /* 
     *Max number of bytes allowed to be uploaded by members of this group.
     *Apples and Apples comparison for the multipart class size method.
     */
    private long maxUpload = 1073741824;
    private List<String> projectUrls = new ArrayList<>();
    @Reference
    private List<Observable> observables = new ArrayList<>();
    private Set<CustomProperty> customProperties = new HashSet<>();
    /* Name of groups that depending on */
    private List<String> dependsOn = new ArrayList<>();

    public MongoGroup() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSshKey() {
        return sshKey;
    }

    public void setSshKey(String sshKey) {
        this.sshKey = sshKey;
    }

    public List<String> getProjectUrls() {
        return projectUrls;
    }

    public void setProjectUrls(List<String> projectUrls) {
        this.projectUrls = projectUrls;
    }

    public List<Observable> getObservables() {
        return observables;
    }

    public void setObservables(List<Observable> observables) {
        this.observables = observables;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public List<ObservableReference> getObservableReferences() {
        List<ObservableReference> observableList = new ArrayList<>();
        for(Observable obs : observables) {
            observableList.add(obs.getObservableReference());
        }
        return observableList;
    }

    public List<String> getDependsOn() {
        return dependsOn;
    }

    public void setDependsOn(List<String> dependsOn) {
        this.dependsOn = dependsOn;
    }

    public boolean isOptIn() {
        return optIn;
    }

    public void setOptIn(boolean optIn) {
        this.optIn = optIn;
    }

    public boolean isComplimentary() {
        return complimentary;
    }

    public void setComplimentary(boolean complimentary) {
        this.complimentary = complimentary;
    }

    /**
     * @return the worldview
     */
    public boolean isWorldview() {
        return worldview;
    }

    /**
     * @param worldview the worldview to set
     */
    public void setWorldview(boolean worldview) {
        this.worldview = worldview;
    }

    public long getMaxUpload() {
        return maxUpload;
    }

    public void setMaxUpload(long maxUpload) {
        this.maxUpload = maxUpload;
    }

    public long getDefaultExpirationTime() {
        return defaultExpirationTime;
    }

    public void setDefaultExpirationTime(long defaultExpirationTime) {
        this.defaultExpirationTime = defaultExpirationTime;
    }

    public Set<CustomProperty> getCustomProperties() {
        return customProperties;
    }

    public void setCustomProperties(Set<CustomProperty> customProperties) {
        this.customProperties = customProperties;
    }
}
