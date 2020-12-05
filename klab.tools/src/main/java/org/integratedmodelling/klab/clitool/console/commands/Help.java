package org.integratedmodelling.klab.clitool.console.commands;

import org.integratedmodelling.kim.api.IPrototype;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.cli.ICommand;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.utils.StringUtil;

public class Help implements ICommand {

  @Override
  public Object execute(IServiceCall call, ISession session) {
    String output = "";
    for (String pack : CliRuntime.INSTANCE.getCommandProcessor().getPackages()) {
      if (!pack.equals("main")) {
        output += pack + ":\n" + StringUtil.repeat('-', pack.length() + 1) + "\n\n";
      }
      for (IPrototype prototype : CliRuntime.INSTANCE.getCommandProcessor().getPrototypes(pack)) {
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
