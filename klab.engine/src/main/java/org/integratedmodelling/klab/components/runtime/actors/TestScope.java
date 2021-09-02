package org.integratedmodelling.klab.components.runtime.actors;

import java.io.File;
import java.nio.file.Paths;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.utils.LogFile;

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
    public Object expect = null;
    public LogFile logfile;
    public IBehavior behavior;
    int level = 0;

    public TestScope(TestScope other) {
        this.logfile = other.logfile;
    }

    public TestScope(IBehavior behavior) {
        this.behavior = behavior;
        String pathName = "testoutput.adoc";
        if (behavior.getStatement().getOutput() != null) {
            pathName = behavior.getStatement().getOutput();
        }
        boolean absolute = Paths.get(pathName).isAbsolute();
        File log = new File(absolute ? pathName : (Configuration.INSTANCE.getDataPath("test") + File.separator + pathName));
        if (this.logfile == null || !this.logfile.getFile().equals(log)) {
            this.logfile = new LogFile(log);
        }
    }

    public void println(String s) {
        this.logfile.println(s);
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
        ret.level = this.level ++;
        // TODO take the test annotation and the expectations
        // TODO log 
        return ret;
    }
}
