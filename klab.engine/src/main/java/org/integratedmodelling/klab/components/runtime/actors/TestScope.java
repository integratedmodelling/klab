package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Section;
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
    private List<Throwable> exceptions = new ArrayList<>();

    /*
     * The root scope will build and pass around a document builder based on the extension of the
     * doc file. Lower-level doc file specs will be ignored.
     */
    AsciiDocBuilder docBuilder;
    private long timestamp;
    private Action action;
    private Section docSection;

    /*
     * TODO constraint system for URNs to use. Must be part of runtime, not the actor system.
     */

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
            this.docBuilder = new AsciiDocBuilder("Test report `" + behavior.getName() + "`");
        }
        return this.log;
    }

    public TestScope(ISession session) {
        this.globalStatistics = new Statistics();
        this.docBuilder = new AsciiDocBuilder(
                "Test report " + new Date() + " (" + session.getUser() + ") [v" + Version.getCurrent() + "]");
        this.docSection = this.docBuilder.getRootSection();
    }

    public void println(String s) {
        getLog().println(s);
    }

    public void onException(Throwable t) {
        exceptions.add(t);
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
        this.docSection
                .paragraph("Test **" + action.getName() + "** completed in " + TestBehavior.printPeriod(duration) + " with "
                        + (assertions > 0
                                ? (localStatistics.success + " successful, " + localStatistics.failure + " failed assertions")
                                : "no assertions, ")
                        + (exceptions.size() + " exceptions")
                        + "\n");

        // TODO compute overall status and add to global statistics: assertions if any, plus any
        // test expectation, plus lack of exceptions or cross-refs.

    }

    /**
     * Called at the end of each testcase behavior
     */
    public void finalizeTestRun() {

        // root test case has finished; output the log
        // NO - should just do this when requested or when the logfile is specified on a testcase
        // basis
        docBuilder.writeToFile(new File(System.getProperty("user.home") + File.separator + "testoutput.adoc").toPath(),
                Charset.forName("UTF-8"));
    }

    public TestScope getChild(Action action) {
        TestScope ret = new TestScope(this);
        ret.docSection = this.docSection.getChild("Test  " + action.getId() + " started " + new Date());
        ret.docSection.listingBlock(action.getStatement().getSourceCode(), "kactors");
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
        ret.docSection = this.docSection.getChild("Test case  " + behavior.getName() + " started " + new Date());
        // ret.docBuilder.sectionTitleLevel(ret.level, "Test namespace `" + behavior.getName() +
        // "`");
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }

    public void notifyAssertion(Object result, IKActorsValue expected, boolean ok, Assertion assertion) {

        // TODO assertions that caused exceptions should insert a cross-reference to the stack trace
        this.docSection.paragraph("Assertion " + (++assertions) + (ok ? " [SUCCESS]" : " [FAIL]") + ":\n");
        this.docSection.listingBlock(assertion.getSourceCode(), "kactors");
        if (ok) {
            this.localStatistics.success++;
        } else {
            this.localStatistics.failure++;
        }
    }

}
