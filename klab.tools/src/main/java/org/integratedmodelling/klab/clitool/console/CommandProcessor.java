/*******************************************************************************
 * Copyright (C) 2007, 2016:
 * 
 * - Ferdinando Villa <ferdinando.villa@bc3research.org> - integratedmodelling.org - any other
 * authors listed in @author annotations
 *
 * All rights reserved. This file is part of the k.LAB software suite, meant to enable modular,
 * collaborative, integrated development of interoperable data and model components. For details,
 * see http://integratedmodelling.org.
 * 
 * This program is free software; you can redistribute it and/or modify it under the terms of the
 * Affero General Public License Version 3 or any later version.
 *
 * This program is distributed in the hope that it will be useful, but without any warranty; without
 * even the implied warranty of merchantability or fitness for a particular purpose. See the Affero
 * General Public License for more details.
 * 
 * You should have received a copy of the Affero General Public License along with this program; if
 * not, write to the Free Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA. The license is also available at: https://www.gnu.org/licenses/agpl.html
 *******************************************************************************/
package org.integratedmodelling.klab.clitool.console;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IPrototype.Argument;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.provenance.IArtifact.Type;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.cli.ConsoleCommandProvider;
import org.integratedmodelling.klab.cli.ConsoleCommandProvider.Command;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.api.IConsole;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.kim.Prototype;

import joptsimple.OptionParser;
import joptsimple.OptionSpecBuilder;

public class CommandProcessor extends org.integratedmodelling.klab.clitool.contrib.console.CommandProcessor {

    IMonitor monitor;
    protected IConsole terminal;
    Map<String, Map<String, Prototype>> packages = new HashMap<>();
    Stack<String> currentPackage = new Stack<>();

    public CommandProcessor(IConsole console, IMonitor monitor) {

        terminal = console;
        this.monitor = monitor;
        this.currentPackage.push(null);

        // ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        // try {
        // for (Resource res : patternResolver.getResources("cli/commands/*.kdl")) {
        // try (InputStream input = res.getInputStream()) {
        // IKdlDataflow declaration = Dataflows.INSTANCE.declare(input);
        // String namespace = declaration.getPackageName();
        // if (namespace == null) {
        // namespace = "main";
        // }
        // for (IKdlActuator actuator : declaration.getActuators()) {
        // Prototype prototype = new Prototype(actuator, null);
        // Map<String, Prototype> commands = getPackage(namespace);
        // commands.put(prototype.getName(), prototype);
        // }
        // }
        // }
        // } catch (Exception e) {
        // throw new KlabValidationException("cannot parse command specifications: " +
        // e.getMessage());
        // }
    }

    // private Map<String, Prototype> getPackage(String namespace) {
    // Map<String, Prototype> ret = packages.get(namespace);
    // if (ret == null) {
    // ret = new HashMap<>();
    // packages.put(namespace, ret);
    // }
    // return ret;
    // }

    @Override
    public void processCommand(String input) {

        input = input.trim();
        String cpack = getCurrentPackage();
        boolean inline = false;

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
                cpack = pname;
                input = input.substring(pname.length() + (input.charAt(pname.length() + 1) == ':' ? 2 : 1)).trim();
                inline = true;
            }
        }

        /*
         * TODO verify if command starts with a first token that is a package name or includes
         * <package.command>; in that case, exec the package's command without pushing it on the
         * stack.
         */

        if (input.equals("exit")) {

            if (currentPackage.size() == 1) {
                System.exit(0);
            } else {
                currentPackage.pop();
                terminal.setPrompt((getCurrentPackage().equals("main") ? ">" : getCurrentPackage()) + "> ");
            }

        } else if (input.equals("quit")) {

            System.exit(0);

        } else if (packages.containsKey(input) && !input.equals("main")) {

            currentPackage.push(input);
            terminal.setPrompt(getCurrentPackage() + "> ");

        } else if (input.length() > 0) {

            Command cmd = null;
            try {
                cmd = ConsoleCommandProvider.INSTANCE.parseCommandLine(input, cpack);
            } catch (Throwable t) {
                terminal.error("wrong command: " + t.getMessage());
            }
            if (cmd != null) {
                IServiceCall command = null;
                try {
                    command = cmd == null ? null : cmd.call;// parseCommandLine(input, cpack);
                    boolean ok = command != null;
                    if (command == null) {
                        terminal.warning("Command '" + input + "' incorrect or unknown");
                    } else {
                        try {
                            /*
                             * TODO run asynchronously if command requires it.
                             */
                            terminal.echo("> " + ((cpack == null || cpack.equals("main")) ? "" : cpack) + "::" + input);
                            Object ret = execute(cmd, cpack);
                            terminal.outputResult(input, ret);
                        } catch (Throwable e) {
                            ok = false;
                            terminal.error(e);
                        }
                    }
                    terminal.reportCommandResult((inline ? (cpack + " ") : "") + input, ok);
                } catch (KlabException e) {
                    terminal.error(e);
                }
            }
        }
    }

    private Object execute(Command command, String pack) throws Exception {

        if (CliRuntime.INSTANCE.getSession() == null) {
            terminal.error("Please wait until a session is established.");
            return null;
        }

        // Prototype prototype = getPackage(pack).get(command.getName());
        // if (prototype == null || prototype.getExecutorClass() == null
        // || !ICommand.class.isAssignableFrom(prototype.getExecutorClass())) {
        // terminal.error("command " + command.getName() + " unknown or not executable");
        // }
        // ICommand executor = (ICommand)
        // prototype.getExecutorClass().getDeclaredConstructor().newInstance();
        return command.executor.execute(command.call, CliRuntime.INSTANCE.getSession());
    }

    /**
     * Overrides the default output in CommandProcessor to determine if ANSI Colors are processed or
     * DCCC and converts accordingly.
     * 
     * @param s The String to output.
     */
    @Override
    public void output(String s) {
        if (getConsole().isUseANSIColorCodes())
            super.output(convertToANSIColors(s));
        else
            super.output(s);
    }

    // public List<IPrototype> getPrototypes(String pack) {
    // List<IPrototype> ret = new ArrayList<>(getPackage(pack).values());
    // ret.sort(new Comparator<IPrototype>() {
    // @Override
    // public int compare(IPrototype o1, IPrototype o2) {
    // return o1.getName().compareTo(o2.getName());
    // }
    // });
    //
    // return ret;
    // }
    //
    // public IServiceCall parseCommandLine(String line, String pack) throws KlabValidationException
    // {
    //
    // String[] a = line.split("\\s");
    // IServiceCall ret = null;
    //
    // if (a.length < 1) {
    // return null;
    // }
    //
    // IPrototype prototype = getPackage(pack).get(a[0]);
    // if (prototype == null)
    // return null;
    //
    // ret = KimServiceCall.create(prototype.getName());
    //
    // String[] args = new String[a.length - 1];
    // System.arraycopy(a, 1, args, 0, a.length - 1);
    //
    // OptionParser parser = getOptionParser(prototype);
    //
    // try {
    //
    // OptionSet options = parser.parse(args);
    // for (Argument s : prototype.listArguments()) {
    // if (options.has(s.getName())) {
    // ret.getParameters().put(s.getName(), s.getType() == Type.BOOLEAN ? Boolean.TRUE
    // : options.valueOf(s.getName()));
    // } else if (s.getType() == Type.BOOLEAN) {
    // ret.getParameters().put(s.getName(), Boolean.FALSE);
    // } else if (!s.isOptional()) {
    // throw new KlabValidationException("argument " + s.getName() + " is mandatory");
    // }
    // }
    //
    // List<Object> aaa = new ArrayList<>(options.nonOptionArguments());
    // ret.getParameters().put("arguments", aaa);
    //
    // } catch (OptionException e) {
    // throw new KlabValidationException(e.getMessage());
    // }
    //
    // // TODO later
    // // int n = 0;
    // // int argn = 0;
    // // boolean acceptsSubcommand = prototype.getSubcommandNames().size() > 0;
    // // boolean requiresSubcommand = ((Prototype) prototype).getSubcommandMethod("")
    // // == null;
    // // for (Object o : options.nonOptionArguments()) {
    // // // pair with arguments
    // // if (n == 0 && acceptsSubcommand) {
    // // if (prototype.getSubcommandNames().contains(o.toString())) {
    // // ret.setSubcommand(o.toString());
    // // n++;
    // // continue;
    // // } else if (requiresSubcommand) {
    // // throw new KlabValidationException("command " + a[0] + " requires one of "
    // // + StringUtils.join(prototype.getSubcommandNames(), ',') + " as subcommand");
    // // }
    // // }
    //
    // // if (prototype.getArguments().size() <= argn) {
    // // throw new KlabValidationException(
    // // "command " + a[0] + " cannot be called with " + (n + 1) + " arguments");
    // // }
    // //
    // // ret.setArgument(prototype.getArgumentNames().get(argn++), o);
    // //
    // // n++;
    // // }
    //
    // Extensions.INSTANCE.validateArguments(prototype, ret.getParameters());
    //
    // return ret;
    // }

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

    public String getCurrentPackage() {
        return currentPackage.peek();
    }

    public List<String> getPackages() {
        List<String> ret = new ArrayList<>(packages.keySet());
        ret.remove("main");
        Collections.sort(ret);
        ret.add(0, "main");
        return ret;
    }

}
