package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

/**
 * Sent at the BEGINNING of a run with all fields empty except start. Use to collect test
 * results as they get in. For now the test cases (TestStatistics) do not contain the ID of the
 * run, and collection relies on timing.
 * 
 * @author Ferd
 *
 */
public class TestRun {

    private long start;
    private long end;
    private List<TestStatistics> testCases = new ArrayList<>();
    private String testId;

    public TestRun() {

    }

    public TestRun(String testId) {
        this.setTestId(testId);
        this.start = System.currentTimeMillis();
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
    public List<TestStatistics> getTestCases() {
        return testCases;
    }
    public void setTestCases(List<TestStatistics> testCases) {
        this.testCases = testCases;
    }

    public String getTestId() {
        return testId;
    }

    public void setTestId(String testId) {
        this.testId = testId;
    }

}