package org.integratedmodelling.klab.clitool;

import java.io.File;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class CliStartupOptions extends EngineStartupOptions {

    @Option(name = "-output", usage = "output <file.owl>", metaVar = "<FILE_PATH>")
    File    outputFile   = null;

    @Argument
    String arg1 = null;
    
    public File getOutputFile() {
      return outputFile;
    }
   
    public String getArgument() {
      return arg1;
    }
}
