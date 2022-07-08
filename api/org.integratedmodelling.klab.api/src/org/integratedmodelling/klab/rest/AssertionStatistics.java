package org.integratedmodelling.klab.rest;

public class AssertionStatistics {

    private long start;
    private long end;
    private String descriptor;
    private boolean success;
    public boolean isSuccess() {
        return success;
    }
    public boolean setSuccess(boolean success) {
        this.success = success;
        return success;
    }
    public String getDescriptor() {
        return descriptor;
    }
    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
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
}