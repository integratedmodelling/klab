package org.integratedmodelling.klab.engine;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.integratedmodelling.klab.Configuration;
import org.integratedmodelling.klab.api.auth.ICertificate;
import org.integratedmodelling.klab.api.engine.IEngineStartupOptions;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.kohsuke.args4j.ParserProperties;

public class EngineStartupOptions implements IEngineStartupOptions {

    @Option(name = "-dataDir", usage = "data directory (default: ~/.klab)", metaVar = "<DIRECTORY_PATH>")
    File dataDir = null;

    @Option(
            name = "-cert",
            usage = "certificate file (default: <dataDir>/" + ICertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME + ")",
            metaVar = "<FILE_PATH>")
    File certificateFile = null;
    
    @Option(
            name = "-certResource",
            usage = "certificate classpath resource (default null)",
            metaVar = "<CLASSPATH_RESOURCE>")
    String certificateResource = null;

    @Option(
            name = "-hub",
            usage = "URL of authenticating hub (default set in certificate)",
            metaVar = "<URL>")
    String authenticatingHub = null;
    
    @Option(
            name = "-workspace",
            usage = "monitored workspace directory (default: ~/.klab/workspace, not monitored)",
            metaVar = "<DIRECTORY_PATH>")
    File workspaceLocation = null;

    @Option(
            name = "-service",
            usage = "service workspace directory (default: ~/.klab/service, not monitored)",
            metaVar = "<DIRECTORY_PATH>")
    File serviceLocation = null;
    
    @Option(name = "-port", usage = "http port for REST communication", metaVar = "<INT>")
    int port = 8183;

    @Option(name = "-anonymous", usage = "force anonymous mode")
    boolean anonymous;
    
    @Option(name = "-help", usage = "print command line options and exit")
    boolean help;
    
    @Option(name = "-cloudConfig", usage = "allow for External Configuration of Node")
    boolean cloudConfig;

    @Option(name = "-exit", usage = "exit after completing startup and running any scripts from command line")
    boolean exit;

    @Option(name = "-components", usage = "paths to any custom component")
    List<File> components = new ArrayList<>();

    private List<String> arguments = new ArrayList<>();

    /**
     * All defaults
     */
    public EngineStartupOptions() {
    }
    
    public EngineStartupOptions(String... args) {
    	initialize(args);
    }
    
    @Override
    public String[] getArguments(String... additionalArguments) {
    	List<String> args = new ArrayList<>(this.arguments);
    	if (additionalArguments != null) {
    		for (String additionalArgument : additionalArguments) {
    			args.add(additionalArgument);
    		}
    	}
        return args.toArray(new String[args.size()]);
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
    public File getServiceLocation() {
        if (serviceLocation == null) {
            serviceLocation = Configuration.INSTANCE.getDataPath("service");
        }
        return serviceLocation;
    }
    
    @Override
    public File getCertificateFile() {
        if (certificateFile == null) {
            certificateFile = new File(Configuration.INSTANCE.getDataPath() + File.separator + ICertificate.DEFAULT_ENGINE_CERTIFICATE_FILENAME);
        }
        return certificateFile;
    }

    public static void main(String args[]) {
        System.out.println(new EngineStartupOptions().usage());
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
    public boolean isAnonymous() {
    	return anonymous;
    }
    
    @Override
    public boolean isExitAfterStartup() {
        return exit;
    }

    @Override
    public Collection<File> getComponentPaths() {
        return components;
    }

    @Override
    public String getCertificateResource() {
        return certificateResource;
    }

    
    public void setDataDir(File dataDir) {
        this.dataDir = dataDir;
    }

    
    public void setCertificateFile(File certificateFile) {
        this.certificateFile = certificateFile;
    }

    
    public void setCertificateResource(String certificateResource) {
        this.certificateResource = certificateResource;
    }

    
    public void setWorkspaceLocation(File workspaceLocation) {
        this.workspaceLocation = workspaceLocation;
    }
    
    public void setServiceLocation(File workspaceLocation) {
        this.serviceLocation = workspaceLocation;
    }
    
    public void setPort(int port) {
        this.port = port;
    }

    
    public void setHelp(boolean help) {
        this.help = help;
    }

    
    public void setExit(boolean exit) {
        this.exit = exit;
    }

    
    public void setComponents(List<File> components) {
        this.components = components;
    }

    
    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

	@Override
	public String getHubUrl() {
		return authenticatingHub;
	}

	@Override
	public boolean isCloudConfig() {
		return cloudConfig;
	}

}
