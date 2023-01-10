package org.integratedmodelling.klab.cli.commands;

import java.util.List;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.cli.ConsoleCommandProvider;
import org.integratedmodelling.klab.utils.StringUtil;

public class Help implements ICommand {

	@Override
	public Object execute(IServiceCall call, ISession session) {

		String output = "";
		
		String namespace = null;
		String command = null;
		
		for (Object option : (List<?>) call.getParameters().get("arguments")) {
			if (namespace == null) {
				namespace = option.toString();
			} else if (command == null) {
				command = option.toString();
				break;
			}
		}
		
		if (command == null && namespace != null) {
			command = namespace;
			namespace = "main";
		}
		
		for (String pack : ConsoleCommandProvider.INSTANCE.getPackages()) {
			
			if (namespace != null && !pack.equals(namespace)) {
				continue;
			}
			
			if (!pack.equals("main")) {
				output += pack + ":\n" + StringUtil.repeat('-', pack.length() + 1) + "\n\n";
			}
			
			for (IPrototype prototype : ConsoleCommandProvider.INSTANCE.getPrototypes(pack)) {

				if (command != null && !prototype.getName().equals(command)) {
					continue;
				}
				
				String synopsis = prototype.getSynopsis();
				if (!pack.equals("main")) {
					synopsis = StringUtil.leftIndent(synopsis, 3);
				}
				output += prototype.getName() + ":\n\n" + synopsis + "\n";
			}
		}
		return output;
	}

}
