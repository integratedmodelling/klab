package org.integratedmodelling.klab.clitool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class CliStartupOptions extends EngineStartupOptions {

    @Option(name = "-output", usage = "output <file.owl>", metaVar = "<FILE_PATH>")
    File    outputFile   = null;

    @Argument(multiValued = true)
    List<String> args = new ArrayList<>();
    
    public File getOutputFile() {
      return outputFile;
    }
   
    public String[] getArguments() {
      return args.toArray(new String[args.size()]);
    }
}
