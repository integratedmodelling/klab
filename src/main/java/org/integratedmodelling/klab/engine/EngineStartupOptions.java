package org.integratedmodelling.klab.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

public class EngineStartupOptions implements IEngineStartupOptions {
    
    @Option(name="-cert",usage="certificate file",metaVar="<CERTIFICATE_PATH>")
    File certificateFile = null;
    
    @Option(name="-workspace",usage="workspace directory",metaVar="<WORKSPACE_PATH>")
    File workspaceLocation = null;
    
    /**
     * All defaults
     */
    public EngineStartupOptions() { }

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
        CmdLineParser parser = new CmdLineParser(this);
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
    
}
