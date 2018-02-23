package org.integratedmodelling.klab.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

public class EngineStartupOptions implements IEngineStartupOptions {

    @Option(name = "-dataDir", usage = "data directory (default: ~/.klab)", metaVar = "<DIRECTORY_PATH>")
    File    dataDir           = null;

    @Option(name = "-cert", usage = "certificate file (default: <dataDir>/im.cert)", metaVar = "<FILE_PATH>")
    File    certificateFile   = null;

    @Option(
            name = "-workspace",
            usage = "monitored workspace directory (default: ~/.klab/workspace, not monitored)",
            metaVar = "<DIRECTORY_PATH>")
    File    workspaceLocation = null;

    @Option(
            name = "-mcast",
            usage = "multicast channel (default: multicasting off; must be unique within the local network)",
            metaVar = "<STRING>")
    String  multicastChannel;

    @Option(name = "-port", usage = "http port for REST communication", metaVar = "<INT>")
    int     port              = 8183;

    @Option(name = "-help", usage = "print command line options and exit")
    boolean help;

    @Option(name = "-exit", usage = "exit after completing startup and running any scripts from command line")
    boolean exit;

    
    private List<String> arguments = new ArrayList<>();

    
    
    /**
     * All defaults
     */
    public EngineStartupOptions() {
    }
    
    public List<String> getArguments() {
      return this.arguments;
    }

    /**
     * Read the passed arguments and initialize all fields from them.
     * 
     * @param arguments
     * @return true if arguments were OK, false otherwise.
     */
    public boolean initialize(String[] arguments) {
        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(arguments);
        } catch (CmdLineException e) {
            return false;
        }
        return true;
    }

    public String usage() {
        ParserProperties properties = ParserProperties.defaults().withUsageWidth(110);
        CmdLineParser parser = new CmdLineParser(this, properties);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(256);
        parser.printUsage(baos);
        return "Usage:\n\n" + baos.toString();
    }

    @Override
    public File getWorkspaceLocation() {
        if (workspaceLocation == null) {
            workspaceLocation = Configuration.INSTANCE.getDataPath("workspace");
        }
        return workspaceLocation;
    }

    @Override
    public File getCertificateFile() {
        if (certificateFile == null) {
            certificateFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + "im.cert");
        }
        return certificateFile;
    }

    public static void main(String args[]) {
        System.out.println(new EngineStartupOptions().usage());
    }

    @Override
    public String getMulticastChannel() {
        return multicastChannel;
    }

    @Override
    public File getDataDirectory() {
        if (dataDir == null) {
            dataDir = Configuration.INSTANCE.getDataPath();
        }
        return dataDir;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public boolean isHelp() {
        return help;
    }

    @Override
    public boolean isExitAfterStartup() {
        return exit;
    }

}
