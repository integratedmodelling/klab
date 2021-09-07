package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder;
import org.integratedmodelling.klab.utils.LogFile;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope {

    class ExceptionReport {
        String context;
        String stackTrace;
    }

    class Statistics {
        public int success = 0;
        public int failure = 0;
        public List<ExceptionReport> exceptions = new ArrayList<>();
    }

    /*
     * match for the expected fire, if any
     */
    private Object expect = null;
    private LogFile log;
    private IBehavior behavior;
    private int level = 0;
    private File logFile = null;
    private IBehavior parentBehavior = null;
    private boolean success = true;
    private Statistics globalStatistics;
    private Statistics localStatistics;
    private int assertions;

    /*
     * The root scope will build and pass around a document builder based on the extension of the
     * doc file. Lower-level doc file specs will be ignored.
     */
    AsciiDocBuilder docBuilder;
    private long timestamp;
    private Action action;

    public TestScope(TestScope other) {
        this.parentBehavior = other.parentBehavior;
        this.behavior = other.behavior;
        this.level = other.level;
        this.logFile = other.logFile;
        this.log = other.log;
        this.docBuilder = other.docBuilder;
        this.globalStatistics = other.globalStatistics;
    }

    private LogFile getLog() {
        if (this.log == null) {
            this.log = new LogFile(logFile);
            this.docBuilder = new AsciiDocBuilder();
            this.docBuilder.documentTitle("Test report `" + behavior.getName() + "`");
        }
        return this.log;
    }

    /**
     * Root scope
     * 
     * @param behavior
     */
    public TestScope(IBehavior behavior) {
        this.behavior = behavior;
        String pathName = "testoutput.adoc";
        if (behavior.getStatement().getOutput() != null) {
            pathName = behavior.getStatement().getOutput();
        }
        boolean absolute = Paths.get(pathName).isAbsolute();
        this.globalStatistics = new Statistics();
        this.logFile = new File(absolute ? pathName : (Configuration.INSTANCE.getDataPath("test") + File.separator + pathName));
        this.log = new LogFile(logFile);
        this.docBuilder = new AsciiDocBuilder();
        // NO - add this later with the header and statistics, then append the rest of the doc
//        this.docBuilder.documentTitle("Test report for `" + behavior.getName() + "` started on " + DateTime.now());
    }

    public void println(String s) {
        getLog().println(s);
    }

    public void onException(Throwable t) {
        // TODO Auto-generated method stub
        System.out.println("HAHAHA");
    }

    /**
     * Called at end of each @test action
     * 
     * @param action
     * @param returnValue
     */
    public void finalizeTest(Action action, Object returnValue) {

        // TODO add test description if the annotation has any, and note any difference in
        // tabulation or ignore/notify status

        long duration = System.currentTimeMillis() - this.timestamp;
        this.docBuilder.paragraph("Test completed in " + TestBehavior.printPeriod(duration) + " with "
                + (assertions > 0
                        ? (localStatistics.success + " successful, " + localStatistics.failure + " assertions")
                        : "no assertions")
                + "\n");

        // TODO compute overall status and add to global statistics: assertions if any, plus any
        // test expectation, plus lack of exceptions or cross-refs.

    }

    /**
     * Called at the end of each testcase behavior
     */
    public void finalizeTestRun() {

        if (this.level == 0) {
            // root test case has finished; output the log
            // TODO must write the header afterwards and the builder does not allow it. Must write
            // line by line and insert header and final stats after
            // the first.
            docBuilder.writeToFile(new File(FileUtils.removeExtension(logFile.toString())).toPath(), Charset.forName("UTF-8"));
        }
        System.out.println(this.behavior.getName() + " DONE");
    }

    public TestScope getChild(Action action) {
        TestScope ret = new TestScope(this);
        ret.docBuilder.sectionTitleLevel(ret.level + 1, "Test case `" + action.getName() + "`");
        ret.docBuilder.listingBlock(action.getStatement().getSourceCode(), "kactors");
        ret.timestamp = System.currentTimeMillis();
        ret.action = action;
        ret.localStatistics = new Statistics();
        return ret;
    }

    public TestScope getChild(IBehavior behavior) {
        TestScope ret = new TestScope(this);
        ret.parentBehavior = this.behavior;
        ret.behavior = behavior;
        ret.level = this.level + 1;
        ret.docBuilder.sectionTitleLevel(ret.level, "Test namespace `" + behavior.getName() + "`");
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }

    public void notifyAssertion(Object result, IKActorsValue expected, boolean ok, Assertion assertion) {

        // TODO assertions that caused exceptions should insert a cross-reference to the stack trace

        this.docBuilder.paragraph("Assertion " + (++assertions) + (ok ? " [SUCCESS]" : " [FAIL]") + ":\n");
        this.docBuilder.listingBlock(assertion.getSourceCode(), "kactors");
        if (ok) {
            this.localStatistics.success++;
        } else {
            this.localStatistics.failure++;
        }
    }

}
