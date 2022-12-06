package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.klab.api.runtime.monitoring.IActivity;
import org.integratedmodelling.klab.api.runtime.rest.ITaskReference.Status;
import org.integratedmodelling.klab.monitoring.ActivityBuilder;

/**
 * Basic anonymous packet of info for each observation made, sent to statistical services as wished.
 * Computation of the workload and size of the job is entirely at the receiving end.
 * 
 * @author Ferd
 *
 */
public class Activity implements IActivity {

    private String contextId;
    private String geometry;
    private long start;
    private long end;
    private Status status;
    private String observable;
    private List<String> models = new ArrayList<>();
    private List<String> resources = new ArrayList<>();

    public static Builder builder() {
        return new ActivityBuilder();
    }

    @Override
    public String getContextId() {
        return contextId;
    }

    public void setContextId(String contextId) {
        this.contextId = contextId;
    }

    public String getGeometry() {
        return geometry;
    }

    public void setGeometry(String geometry) {
        this.geometry = geometry;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
