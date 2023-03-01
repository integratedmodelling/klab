package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.integratedmodelling.kactors.api.IKActorsStatement.Assert.Assertion;
import org.integratedmodelling.kactors.api.IKActorsStatement.Fail;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Annotations;
import org.integratedmodelling.klab.Time;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.knowledge.IMetadata;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.components.time.extents.TimeInstant;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Option;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Section;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Table;
import org.integratedmodelling.klab.documentation.AsciiDocBuilder.Table.Span;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.rest.ActionStatistics;
import org.integratedmodelling.klab.rest.AssertionStatistics;
import org.integratedmodelling.klab.rest.TestStatistics;
import org.integratedmodelling.klab.utils.LogFile;
import org.integratedmodelling.klab.utils.NameGenerator;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.StringUtils;
import org.integratedmodelling.klab.utils.TemplateUtil;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope implements IKActorsBehavior.TestScope {

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
    // unique, used for communication organization only
    private String testScopeId;

    private ISession session;

    // set only by an explicit fail instruction
    private String failureMessage;

    /*
     * TODO constraint system for URNs to use. Must be part of runtime, not the actor system.
     */
    public TestScope(TestScope other) {

        this.session = other.session;
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
        this.testScopeId = other.testScopeId;
    }

    public TestScope(ISession session) {
        this.session = session;
        this.statistics = new ArrayList<>();
        this.docBuilder = new AsciiDocBuilder("Test report",
                "Run by " + session.getUser() + " on " + TimeInstant.create() + " [k.LAB " + Version.getCurrent() + "]",
                Option.NUMBER_SECTIONS);
        this.docSection = this.docBuilder.getRootSection();
        this.docSection.action(() -> getAsciidocDescription());
        this.testScopeId = NameGenerator.newName("testscope");
    }

    public void onException(Throwable t) {
        exceptions.add(t);
    }

    public String getTestId() {
        return this.testScopeId;
    }

    public List<TestStatistics> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<TestStatistics> statistics) {
        this.statistics = statistics;
    }

    public TestStatistics getTestStatistics() {
        return testStatistics;
    }

    public void setTestStatistics(TestStatistics testStatistics) {
        this.testStatistics = testStatistics;
    }

    public ActionStatistics getActionStatistics() {
        return actionStatistics;
    }

    public void setActionStatistics(ActionStatistics actionStatistics) {
        this.actionStatistics = actionStatistics;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public IBehavior getParentBehavior() {
        return parentBehavior;
    }

    public void setParentBehavior(IBehavior parentBehavior) {
        this.parentBehavior = parentBehavior;
    }

    public List<Throwable> getExceptions() {
        return exceptions;
    }

    public void setExceptions(List<Throwable> exceptions) {
        this.exceptions = exceptions;
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public TestScope getParent() {
        return parent;
    }

    public void setParent(TestScope parent) {
        this.parent = parent;
    }

    public String getTestScopeId() {
        return testScopeId;
    }

    public void setTestScopeId(String testScopeId) {
        this.testScopeId = testScopeId;
    }

    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage = failureMessage;
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

        this.session.getMonitor().send(Message.create(this.session.getId(), IMessage.MessageClass.UnitTests,
                IMessage.Type.TestFinished, this.actionStatistics));

    }

    public ActionStatistics newAction(TestStatistics test, Action action) {

        ActionStatistics ret = new ActionStatistics();
        ret.setPath(action.getName());
        ret.setName(Path.getLast(ret.getPath(), '.'));
        ret.setLabel(ret.getName());
        ret.setTestCaseName(test.getName());
        IAnnotation tann = Annotations.INSTANCE.getAnnotation(action.getAnnotations(), "test");
        if (tann != null) {
            if (tann.containsKey("comment")) {
                ret.setDescription(tann.get("comment", String.class));
            }
            if (tann.containsKey("label")) {
                ret.setLabel(tann.get("label", String.class));
            }
        }
        ret.setSourceCode(action.getStatement().getSourceCode());
        ret.setStart(System.currentTimeMillis());

        this.session.getMonitor()
                .send(Message.create(this.session.getId(), IMessage.MessageClass.UnitTests, IMessage.Type.TestStarted, ret));

        test.getActions().add(ret);
        return ret;
    }

    /**
     * Called at the end of each testcase behavior
     */
    public void finalizeTestRun() {

        /*
         * TODO remove - should be optional after everything has ended
         */
        docBuilder.writeToFile(new File(System.getProperty("user.home") + File.separator + "testoutput.adoc").toPath(),
                Charset.forName("UTF-8"));

        this.session.getMonitor().send(Message.create(this.session.getId(), IMessage.MessageClass.UnitTests,
                IMessage.Type.TestCaseFinished, this.testStatistics));

    }

    public TestScope getChild(Action action) {

        TestScope ret = new TestScope(this);
        ret.docSection = this.docSection.getChild("anchor:" + action.getId() + "[]" + "Test:  " + action.getId());
        ret.action = action;
        ret.parent = this;
        ret.actionStatistics = newAction(ret.testStatistics, action);
        ret.docSection.action(() -> {

            StringBuffer buffer = new StringBuffer();

            if (ret.actionStatistics.getDescription() != null && !ret.actionStatistics.getDescription().isEmpty()) {
                buffer.append(ret.actionStatistics.getDescription() + "\n\n");
            }

            buffer.append("Test completed in "
                    + Time.INSTANCE.printPeriod(ret.actionStatistics.getEnd() - ret.actionStatistics.getStart()) + ": result is "
                    + (ret.actionStatistics.getFailure() == 0 ? "[lime]#SUCCESS#" : "[red]#FAIL#") + "\n\n");

            buffer.append(".Source code\n");
            buffer.append(AsciiDocBuilder.listingBlock(action.getStatement().getSourceCode(), "kactors", Option.COLLAPSIBLE));

            if (ret.actionStatistics.getAssertions().size() > 0) {
                Table table = new Table("Description", "Outcome").title("Assertions").spans(7, 1);
                for (AssertionStatistics assertion : ret.actionStatistics.getAssertions()) {
                    table.addRow(assertion.getDescriptor(), assertion.isSuccess() ? "[lime]#SUCCESS#" : "[red]#FAIL#");
                }
                buffer.append("\n" + table.toString());
            }

            return buffer.toString();
        });
        return ret;
    }

    public TestScope getChild(IBehavior behavior) {
        TestScope ret = new TestScope(this);
        ret.parentBehavior = this.behavior;
        ret.behavior = behavior;
        ret.level = this.level + 1;
        ret.docSection = this.docSection.getChild("anchor:" + behavior.getName() + "[]" + behavior.getName());
        ret.parent = this;
        ret.testStatistics = new TestStatistics(behavior);
        ret.docSection.action(() -> {

            StringBuffer buffer = new StringBuffer();
            int success = ret.testStatistics.successCount();
            int failed = ret.testStatistics.failureCount();
            long elapsed = 0;
            for (ActionStatistics action : ret.testStatistics.getActions()) {
                elapsed += action.getEnd() - action.getStart();
            }

            if (ret.behavior.getMetadata().containsKey(IMetadata.DC_COMMENT)) {
                buffer.append("\n" + ret.behavior.getMetadata().get(IMetadata.DC_COMMENT) + "\n");
            }

            buffer.append("\nTotal tests run: " + (success + failed) + " of which [lime]#" + success + "# successful, [red]#"
                    + failed + "# failed. Total test run time " + Time.INSTANCE.printPeriod(elapsed) + ".\n");

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
                    totalSkipped++;
                } else if (action.getFailure() > 0) {
                    totalFail++;
                } else {
                    totalOk++;
                }
            }
        }

        Table table = new Table(3).spans(1, 7, 1);
        table.addRow(new Span(2, 1), "**Overall test results**", (totalOk + "/" + (totalOk + totalFail)));

        for (TestStatistics child : this.statistics) {

            table.addRow(new Span(2, 1), "<<" + child.getName() + ", Test case **" + child.getName() + "**>>",
                    ((child.successCount() + "/" + (child.successCount() + child.failureCount()))));

            int i = 1;
            for (ActionStatistics action : child.getActions()) {
                table.addRow(">Test #" + (i++), "<<" + action.getName() + ", " + action.getName() + ">>",
                        action.isSkipped() ? "SKIP" : (action.getFailure() == 0 ? "[lime]#SUCCESS#" : "[red]#FAIL#"));
            }
        }

        return (totalOk + totalFail) + " tests run in " + Time.INSTANCE.printPeriod(end - start) + " ([lime]#" + totalOk
                + "# succeeded, [red]#" + totalFail + "# failed, " + totalSkipped + " skipped)\n\n" + table.toString();
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
                desc.setDescriptor(TemplateUtil.substitute(assertion.getMetadata().get("success", String.class), "value", result,
                        "expected", expected));
            } else {
                if (expected != null) {
                    desc.setDescriptor("Expected value " + expected + " was returned");
                }
            }
        } else {
            if (assertion.getMetadata().containsKey("fail")) {
                desc.setDescriptor(TemplateUtil.substitute(assertion.getMetadata().get("fail", String.class), "value", result,
                        "expected", expected));
            } else {
                if (expected != null) {
                    desc.setDescriptor("Expected " + expected + ", got " + result);
                }
            }
        }

        if (desc.getDescriptor() == null) {
            desc.setDescriptor(StringUtils.abbreviate(StringUtils.getFirstLine(assertion.getSourceCode()), 60));
        }

    }

    public void fail(Fail code) {
        this.actionStatistics.setFailure(this.actionStatistics.getFailure() + 1);
        this.failureMessage = code.getMessage();
    }

}
