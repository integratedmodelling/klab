package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.file.Paths;

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

    /*
     * The root scope will build and pass around a document builder based on the extension of the
     * doc file. Lower-level doc file specs will be ignored.
     */
    MarkupDocBuilder docBuilder_;
    
    public TestScope(TestScope other) {
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

    public void finalizeTest(Action action, Object returnValue) {
        // TODO Auto-generated method stub
        System.out.println("HOHOHO");
    }

    public TestScope getChild(Action action) {
        TestScope ret = new TestScope(this);
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }

    public TestScope getChild(IBehavior behavior) {
        TestScope ret = new TestScope(this);
        ret.behavior = behavior;
        ret.level = this.level++;
        // TODO take the test annotation and the expectations
        // TODO log
        return ret;
    }
}
