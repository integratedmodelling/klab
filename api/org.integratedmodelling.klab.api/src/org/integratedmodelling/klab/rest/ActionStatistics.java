package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;

public class ActionStatistics {

    private long start;
    private long end;
    private String name;
    private String testCaseName;
    private String label;
    private String description;
    private String path; // unique name, testcase.action
    private int success = 0;
    private int failure = 0;
    private boolean skipped = false;
    private List<ExceptionReport> exceptions = new ArrayList<>();
    private List<AssertionStatistics> assertions = new ArrayList<>();
    private String sourceCode;

    public AssertionStatistics createAssertion(Assertion assertion) {
        AssertionStatistics ret = new AssertionStatistics();
        // TODO
        this.getAssertions().add(ret);
        return ret;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public List<AssertionStatistics> getAssertions() {
        return assertions;
    }

    public void setAssertions(List<AssertionStatistics> assertions) {
        this.assertions = assertions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isSkipped() {
        return skipped;
    }

    public void setSkipped(boolean skipped) {
        this.skipped = skipped;
    }

    public List<ExceptionReport> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<ExceptionReport> exceptions) {
        this.exceptions = exceptions;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getTestCaseName() {
        return testCaseName;
    }

    public void setTestCaseName(String testCaseName) {
        this.testCaseName = testCaseName;
    }
}