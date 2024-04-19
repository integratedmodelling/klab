package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SessionReference {

    private String id;
    private long timeEstablished;
    private long timeLastJoined;
    private long timeRetrieved;
    private long timeLastActivity;
    private String baseHelpUrl;
    private IdentityReference owner;
    Map<String, ObservationReference> rootObservations = new HashMap<>();
    @Deprecated
    private List<String> appUrns = new ArrayList<>(); // remove in favor of publicApps descriptors
    private List<String> userAppUrns = new ArrayList<>();
    private List<BehaviorReference> publicApps = new ArrayList<>();

    public long getTimeEstablished() {
        return timeEstablished;
    }

    public void setTimeEstablished(long timeEstablished) {
        this.timeEstablished = timeEstablished;
    }

    public long getTimeLastJoined() {
        return timeLastJoined;
    }

    public void setTimeLastJoined(long timeLastJoined) {
        this.timeLastJoined = timeLastJoined;
    }

    public long getTimeRetrieved() {
        return timeRetrieved;
    }

    public void setTimeRetrieved(long timeRetrieved) {
        this.timeRetrieved = timeRetrieved;
    }

    public Map<String, ObservationReference> getRootObservations() {
        return rootObservations;
    }

    public void setRootObservations(Map<String, ObservationReference> rootObservations) {
        this.rootObservations = rootObservations;
    }

    public long getTimeLastActivity() {
        return timeLastActivity;
    }

    public void setTimeLastActivity(long timeLastActivity) {
        this.timeLastActivity = timeLastActivity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IdentityReference getOwner() {
        return owner;
    }

    public void setOwner(IdentityReference owner) {
        this.owner = owner;
    }

    public String getBaseHelpUrl() {
        return baseHelpUrl;
    }

    public void setBaseHelpUrl(String baseHelpUrl) {
        this.baseHelpUrl = baseHelpUrl;
    }

    @Deprecated
    public List<String> getAppUrns() {
        return appUrns;
    }

    @Deprecated
    public void setAppUrns(List<String> appUrns) {
        this.appUrns = appUrns;
    }

    public List<String> getUserAppUrns() {
        return userAppUrns;
    }

    public void setUserAppUrns(List<String> userAppUrns) {
        this.userAppUrns = userAppUrns;
    }

    public List<BehaviorReference> getPublicApps() {
        return publicApps;
    }

    public void setPublicApps(List<BehaviorReference> publicApps) {
        this.publicApps = publicApps;
    }

}
