package org.integratedmodelling.klab.cli;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.integratedmodelling.kdl.api.IKdlActuator;
import org.integratedmodelling.kdl.api.IKdlDataflow;
import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimServiceCall;
import org.integratedmodelling.klab.Dataflows;
import org.integratedmodelling.klab.Extensions;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import joptsimple.OptionSpecBuilder;

public enum ConsoleCommandProvider {

    INSTANCE;

    public class Command {
        public ICommand executor;
        public IServiceCall call;
    }

    private Map<String, Map<String, Prototype>> packages = Collections.synchronizedMap(new HashMap<>());

    ConsoleCommandProvider() {

        ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        try {
            for (Resource res : patternResolver.getResources("cli/commands/*.kdl")) {
                try (InputStream input = res.getInputStream()) {
                    IKdlDataflow declaration = Dataflows.INSTANCE.declare(input);
                    String namespace = declaration.getPackageName();
                    if (namespace == null) {
                        namespace = "main";
                    }
                    for (IKdlActuator actuator : declaration.getActuators()) {
                        Prototype prototype = new Prototype(actuator, null);
                        Map<String, Prototype> commands = packages.get(namespace);
                        if (commands == null) {
                            commands = new HashMap<>();
                            packages.put(namespace, commands);
                        }
                        commands.put(prototype.getName(), prototype);
                    }
                } catch (Throwable e) {
                    Logging.INSTANCE.error("cannot parse command specifications from " + res + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new KlabIOException("cannot access CLI command resource definitions at cli/commands: " + e.getMessage());
        }
    }

    private Map<String, Prototype> getPackage(String namespace) {
        Map<String, Prototype> ret = packages.get(namespace);
        if (ret == null) {
            /*
             * see if we have the command in the main package
             */
            ret = packages.get("main");
            if (ret != null && !ret.containsKey(namespace)) {
                ret = null;
            }
        }
        return ret;
    }

    public Command processCommand(String input, String currentPackage) {

        input = input.trim();

        // enable one-off package use with package prefix
        if (input.contains("::") || input.contains(" ")) {
            String pname = "";
            for (int i = 0; i < input.length(); i++) {
                if (input.charAt(i) == ':' || input.charAt(i) == ' ') {
                    break;
                }
                pname += input.charAt(i);
            }
            if (packages.containsKey(pname)) {
                currentPackage = pname;
                input = input.substring(pname.length() + (input.charAt(pname.length() + 1) == ':' ? 2 : 1)).trim();
            }
        }

        /*
         * TODO verify if command starts with a first token that is a package name or includes
         * <package.command>; in that case, exec the package's command without pushing it on the
         * stack.
         */

        if (packages.containsKey(input) && !input.equals("main")) {
            //
            // currentPackage.push(input);
            // terminal.setPrompt(getCurrentPackage() + "> ");
            //
        } else if (input.length() > 0) {
            return parseCommandLine(input, currentPackage);
        }

        return null;

    }
    
    
    public Command parseCommandLine(String line, String pack) throws KlabValidationException {

        String[] a = line.split("\\s");
        IServiceCall call = null;

        if (a.length < 1) {
            return null;
        }

        
        IPrototype prototype = null;
        if (packages.containsKey(a[0]) && a.length > 1) {
            prototype = packages.get(a[0]).get(a[1]);
        } else if (packages.containsKey("main") && packages.get("main").containsKey(a[0])) {
            prototype = packages.get("main").get(a[0]);
        }
        
        if (prototype == null) {
            return null;
        }

        call = KimServiceCall.create(prototype.getName());

        String[] args = new String[a.length - 1];
        System.arraycopy(a, 1, args, 0, a.length - 1);

        OptionParser parser = getOptionParser(prototype);

        try {

            OptionSet options = parser.parse(args);
            for (Argument s : prototype.listArguments()) {
                if (options.has(s.getName())) {
                    call.getParameters().put(s.getName(),
                            s.getType() == Type.BOOLEAN ? Boolean.TRUE : options.valueOf(s.getName()));
                } else if (s.getType() == Type.BOOLEAN) {
                    call.getParameters().put(s.getName(), Boolean.FALSE);
                } else if (!s.isOptional()) {
                    throw new KlabValidationException("argument " + s.getName() + " is mandatory");
                }
            }

            List<Object> aaa = new ArrayList<>(options.nonOptionArguments());
            call.getParameters().put("arguments", aaa);

        } catch (OptionException e) {
            throw new KlabValidationException(e.getMessage());
        }

        Extensions.INSTANCE.validateArguments(prototype, call.getParameters());

        if (call != null) {

            ICommand command = null;
            if (prototype.getExecutorClass() != null && ICommand.class.isAssignableFrom(prototype.getExecutorClass())) {
                try {
                    command = (ICommand) prototype.getExecutorClass().newInstance();
                } catch (Throwable e) {
                    return null;
                }
                
                if (command != null) {
                    Command ret = new Command();
                    ret.call = call;
                    ret.executor = command;
                    return ret;
                }
            }
        }

        return null;
    }

    public OptionParser getOptionParser(IPrototype prototype) {

        OptionParser parser = new OptionParser();

        for (Argument odesc : prototype.listArguments()) {

            OptionSpecBuilder b = parser.acceptsAll(
                    Arrays.asList(
                            new String[]{odesc.getShortName() == null ? odesc.getName() : odesc.getShortName(), odesc.getName()}),
                    odesc.getDescription());

            if (!(odesc.getType().equals(Type.VOID) || odesc.getType().equals(Type.BOOLEAN))) {
                b.withRequiredArg();
            }
        }
        return parser;
    }
}
