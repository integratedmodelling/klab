package org.integratedmodelling.klab.clitool;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.engine.EngineStartupOptions;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

public class CliStartupOptions extends EngineStartupOptions {

    @Option(name = "-output", usage = "output <file.owl>", metaVar = "<FILE_PATH>")
    File outputFile = null;

    @Option(name = "-network", usage = "start network services automatically")
    boolean network;

    @Option(name = "-test", usage = "run in test mode: run all test cases in all named projects (clone/sync if git url), produce a report in the output file and exit")
    boolean testing;

    @Argument(multiValued = true)
    List<String> args = new ArrayList<>();

    public File getOutputFile() {
        return outputFile;
    }

    public void setNetwork(boolean b) {
        this.network = b;
    }

    public boolean isNetwork() {
        return network;
    }

    public boolean isTesting() {
        return this.testing;
    }

    public void setTesting(boolean testing) {
        this.testing = testing;
    }

    public String[] getArguments() {
        return args.toArray(new String[args.size()]);
    }
}
