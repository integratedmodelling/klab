package org.integratedmodelling.klab.rest;

import java.util.HashSet;
import java.util.Set;

public class ScenarioSelection {

    private Set<String> scenarios = new HashSet<>();
    private boolean persistent;
    private boolean filtered;

    public Set<String> getScenarios() {
        return scenarios;
    }

    public void setScenarios(Set<String> scenarios) {
        this.scenarios = scenarios;
    }

    public boolean isPersistent() {
        return persistent;
    }

    public void setPersistent(boolean persistent) {
        this.persistent = persistent;
    }

    public boolean isFiltered() {
        return filtered;
    }

    public void setFiltered(boolean filtered) {
        this.filtered = filtered;
    }

}
