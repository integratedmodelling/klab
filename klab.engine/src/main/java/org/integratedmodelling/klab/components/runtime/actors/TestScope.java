package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Option;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Section;
import org.integratedmodelling.klab.rest.TestStatistics;
import org.integratedmodelling.klab.rest.TestStatistics.ActionStatistics;
import org.integratedmodelling.klab.rest.TestStatistics.AssertionStatistics;
import org.integratedmodelling.klab.utils.LogFile;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.TemplateUtil;
import org.joda.time.Period;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope {

    // created in the root scope and passed on to the children as is
    private List<TestStatistics> statistics;

    // test-scoped scopes make one of these and add it to statistics
    private TestStatistics testStatistics;
    // action-scoped scopes make one of these through the test statistics
    private ActionStatistics actionStatistics;
    private LogFile log;
    private IBehavior behavior;
    private int level = 0;
    private File logFile = null;
    private IBehavior parentBehavior = null;
    private List<Throwable> exceptions = new ArrayList<>();

    /*
     * The root scope will build and pass around a document builder based on the extension of the
     * doc file. Lower-level doc file specs will be ignored.
     */
    AsciiDocBuilder docBuilder;
    private Action action;
    private Section docSection;
    private TestScope parent;

    /*
     * TODO constraint system for URNs to use. Must be part of runtime, not the actor system.
     */

    public TestScope(TestScope other) {

        this.statistics = other.statistics;
        this.testStatistics = other.testStatistics;
        this.actionStatistics = other.actionStatistics;
        this.action = other.action;
        this.parentBehavior = other.parentBehavior;
        this.behavior = other.behavior;
        this.level = other.level;
        this.logFile = other.logFile;
        this.log = other.log;
        this.docBuilder = other.docBuilder;
    }

    public TestScope(ISession session) {
        this.statistics = new ArrayList<>();
        this.docBuilder = new AsciiDocBuilder(
                "Test report", "Run by " + session.getUser() + " [v" + Version.getCurrent() + "]",
                Option.NUMBER_SECTIONS);
        this.docSection = this.docBuilder.getRootSection();
        this.docSection.action(() -> getAsciidocDescription());
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

        this.actionStatistics.setEnd(System.currentTimeMillis());
        for (AssertionStatistics a : this.actionStatistics.getAssertions()) {
            if (a.isSuccess()) {
                this.actionStatistics.setSuccess(this.actionStatistics.getSuccess() + 1);
            } else {
                this.actionStatistics.setFailure(this.actionStatistics.getFailure() + 1);
            }
        }

        // TODO inform clients
    }

    public ActionStatistics newAction(TestStatistics test, Action action) {

        ActionStatistics ret = new ActionStatistics();
        ret.setPath(action.getName());
        ret.setName(Path.getLast(ret.getPath(), '.'));
        IAnnotation tann = Annotations.INSTANCE.getAnnotation(action.getAnnotations(), "test");
        if (tann != null && tann.containsKey("description")) {
            test.setDescription(tann.get("description", String.class));
        }
        ret.setSourceCode(action.getStatement().getSourceCode());
        ret.setStart(System.currentTimeMillis());

        test.getActions().add(ret);
        return ret;
    }

    /**
     * Called at the end of each testcase behavior
     */
    public void finalizeTestRun() {

        /*
         * TODO remove
         */
        docBuilder.writeToFile(
                new File(System.getProperty("user.home") + File.separator + "testoutput.adoc").toPath(),
                Charset.forName("UTF-8"));

        // TODO inform clients

    }

    public TestScope getChild(Action action) {
        TestScope ret = new TestScope(this);
        ret.docSection = this.docSection.getChild("Test:  " + action.getId());
        ret.action = action;
        ret.parent = this;
        ret.actionStatistics = newAction(ret.testStatistics, action);
        ret.docSection.action(() -> {
            StringBuffer buffer = new StringBuffer();
            buffer.append(AsciiDocBuilder.listingBlock(action.getStatement().getSourceCode(), "kactors",
                    Option.COLLAPSIBLE));
            return buffer.toString();
        });
        return ret;
    }

    public TestScope getChild(IBehavior behavior) {
        TestScope ret = new TestScope(this);
        ret.parentBehavior = this.behavior;
        ret.behavior = behavior;
        ret.level = this.level + 1;
        ret.docSection = this.docSection
                .getChild(behavior.getName());
        ret.parent = this;
        ret.testStatistics = new TestStatistics(behavior);
        ret.docSection.action(() -> {
            StringBuffer buffer = new StringBuffer();
            // TODO summary only
            return buffer.toString();
        });

        this.statistics.add(ret.testStatistics);
        return ret;
    }

    /*
     * Top-level test suite description. By now all tests have finished.
     */
    private String getAsciidocDescription() {

        int totalOk = 0, totalFail = 0, totalSkipped = 0;
        long start = Long.MAX_VALUE, end = Long.MIN_VALUE;
        for (TestStatistics child : this.statistics) {
            for (ActionStatistics action : child.getActions()) {
                if (action.getStart() < start) {
                    start = action.getStart();
                }
                if (action.getEnd() > end) {
                    end = action.getEnd();
                }
                if (action.isSkipped()) {
                    totalSkipped ++;
                } else if (action.getFailure() > 0) {
                    totalFail ++;
                } else {
                    totalOk ++;
                }
            }
        }

        return (totalOk + totalFail) + " tests run in " + new Period(end - start) + " (" + totalOk + " succeeded, "
                + totalFail + " failed, " + totalSkipped + " skipped)";
    }

    /**
     * The root scope holds all the statistics
     * 
     * @return
     */
    public TestScope getRoot() {
        return this.parent == null ? this : this.parent.getRoot();
    }

    public void notifyAssertion(Object result, IKActorsValue expected, boolean ok, Assertion assertion) {

        AssertionStatistics desc = this.actionStatistics.createAssertion(assertion);
        if ((desc.setSuccess(ok))) {
            this.actionStatistics.setSuccess(this.actionStatistics.getSuccess() + 1);
        } else {
            this.actionStatistics.setFailure(this.actionStatistics.getFailure() + 1);
        }
        if (ok) {
            if (assertion.getMetadata().containsKey("success")) {
                desc.setDescriptor(TemplateUtil.substitute(
                        assertion.getMetadata().get("success", String.class), "value", result, "expected",
                        expected));
            } else {
                if (expected != null) {
                    desc.setDescriptor("Expected value " + expected + " was returned");
                }
            }
        } else {
            if (assertion.getMetadata().containsKey("fail")) {
                desc.setDescriptor(TemplateUtil.substitute(
                        assertion.getMetadata().get("fail", String.class), "value", result, "expected",
                        expected));
            } else {
                if (expected != null) {
                    desc.setDescriptor("Expected " + expected + ", got " + result);
                }
            }
        }

        if (desc.getDescriptor() == null) {
            desc.setDescriptor(StringUtils.abbreviate(assertion.getSourceCode(), 60));
        }

    }

}
