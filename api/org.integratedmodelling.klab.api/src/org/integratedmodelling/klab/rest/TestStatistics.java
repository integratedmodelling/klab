package org.integratedmodelling.klab.rest;

import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.knowledge.IMetadata;

/**
 * Sent at the BEGINNING of a testcase run with most fields empty.
 * 
 * @author Ferd
 *
 */
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

    public int getFailureCount() {
        int ret = 0;
        for (ActionStatistics action : actions) {
            if (!action.isSkipped() && action.getFailure() > 0) {
                ret++;
            }
        }
        return ret;
    }

    public int getSuccessCount() {
        int ret = 0;
        for (ActionStatistics action : actions) {
            if (!action.isSkipped() && action.getFailure() == 0) {
                ret++;
            }
        }
        return ret;
    }

    /**
     * Sent at the BEGINNING of a run with all fields empty except start. Use to collect test
     * results as they get in.
     * 
     * @author Ferd
     *
     */
    public static class TestRun {

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

    public static class ExceptionReport {

        private String id;
        private String stackTrace;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStackTrace() {
            return stackTrace;
        }

        public void setStackTrace(String stackTrace) {
            this.stackTrace = stackTrace;
        }

    }

    public static class AssertionStatistics {

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

    public static class ActionStatistics {

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