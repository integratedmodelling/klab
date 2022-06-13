package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

public class TestStatistics {

    public TestStatistics() {
    }

    public TestStatistics(IBehavior behavior) {
        this.name = behavior.getName();
        this.setDescription(behavior.getMetadata().get(IMetadata.DC_COMMENT, (String) null));
        this.setStart(System.currentTimeMillis());
    }

    private String name;
    private String description;
    private List<ActionStatistics> actions = new ArrayList<>();
    private long start;
    private long end;

    public int failureCount() {
        int ret = 0;
        for (ActionStatistics action : actions) {
            if (!action.isSkipped() && action.getFailure() > 0) {
                ret++;
            }
        }
        return ret;
    }

    public int successCount() {
        int ret = 0;
        for (ActionStatistics action : actions) {
            if (!action.isSkipped() && action.getFailure() == 0) {
                ret++;
            }
        }
        return ret;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<ActionStatistics> getActions() {
        return actions;
    }
    public void setActions(List<ActionStatistics> actions) {
        this.actions = actions;
    }
    public long getEnd() {
        return end;
    }
    public void setEnd(long end) {
        this.end = end;
    }
    public long getStart() {
        return start;
    }
    public void setStart(long start) {
        this.start = start;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}