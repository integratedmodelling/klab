package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.file.Paths;

import org.hsqldb.types.Charset;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.utils.LogFile;
import org.integratedmodelling.klab.utils.MiscUtilities;

import io.github.swagger2markup.markup.builder.MarkupDocBuilder;
import io.github.swagger2markup.markup.builder.MarkupDocBuilders;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

/**
 * Additional scope for actions in test scripts.
 * 
 * @author Ferd
 *
 */
public class TestScope {
    
    /*
     * match for the expected fire, if any
     */
    private Object expect = null;
    private LogFile log_;
    private IBehavior behavior;
    private int level = 0;
    private File logFile = null;
    private IBehavior parentBehavior = null;
    
    /*
     * The root scope will build and pass around a document builder based on the extension of the
     * doc file. Lower-level doc file specs will be ignored.
     */
    MarkupDocBuilder docBuilder_;
    
    public TestScope(TestScope other) {
        this.parentBehavior = other.parentBehavior;
        this.behavior = other.behavior;
        this.level = other.level;
        this.logFile = other.logFile;
        this.log_ = other.log_;
        this.docBuilder_ = other.docBuilder_;
    }

    private LogFile getLog() {
        if (this.log_ == null) {
            this.log_ = new LogFile(logFile);
            this.docBuilder_ = MarkupDocBuilders.documentBuilder(getMarkupLanguage(logFile));
        }
        return this.log_;
    }
    
    public MarkupDocBuilder getDocBuilder() {
        if (this.log_ == null) {
            this.log_ = new LogFile(logFile);
            this.docBuilder_ = MarkupDocBuilders.documentBuilder(getMarkupLanguage(logFile));
        }
        return this.docBuilder_;
    }
    
    private MarkupLanguage getMarkupLanguage(File outfile) {
        MarkupLanguage ret = MarkupLanguage.ASCIIDOC;
        switch (MiscUtilities.getFileExtension(outfile)) {
        case "md":
            ret = MarkupLanguage.MARKDOWN;
        case "confluence":
            ret = MarkupLanguage.CONFLUENCE_MARKUP;
        }
        return ret;
    }

    public TestScope(IBehavior behavior) {
        this.behavior = behavior;
        String pathName = "testoutput.adoc";
        if (behavior.getStatement().getOutput() != null) {
            pathName = behavior.getStatement().getOutput();
        }
        boolean absolute = Paths.get(pathName).isAbsolute();
        this.logFile = new File(absolute ? pathName : (Configuration.INSTANCE.getDataPath("test") + File.separator + pathName));
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
     * @param action
     * @param returnValue
     */
    public void finalizeTest(Action action, Object returnValue) {
        // TODO Auto-generated method stub
        System.out.println("HOHOHO");
    }
    
    /**
     * Called at the end of each testcase behavior
     */
    public void finalizeTestRun() {
        // TODO Auto-generated method stub
        if (this.level == 0) {
            // root test case has finished; output the log
//            getDocBuilder().writeToFileWithoutExtension(null, Charset.UTF8);
        }
        System.out.println(this.behavior.getName() + " DONE");
    }
    
    public TestScope getChild(Action action) {
        TestScope ret = new TestScope(this);
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }

    public TestScope getChild(IBehavior behavior) {
        TestScope ret = new TestScope(this);
        ret.parentBehavior = this.behavior;
        ret.behavior = behavior;
        ret.level = this.level+1;
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }

    public void notifyAssertion(Object result, IKActorsValue expected, boolean ok) {

    }


}
