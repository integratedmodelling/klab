package org.integratedmodelling.klab.clitool.console.commands;

import org.integratedmodelling.kim.api.IKimFunctionCall;
import org.integratedmodelling.klab.api.extensions.IPrototype;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.clitool.api.ICommand;
import org.integratedmodelling.klab.clitool.console.Console;
import org.integratedmodelling.klab.utils.StringUtils;

public class Help implements ICommand {

  @Override
  public Object execute(IKimFunctionCall call, ISession session) {
    String output = "";
    for (String pack : Console.INSTANCE.getPackages()) {
      if (!pack.equals("main")) {
        output += pack + ":\n" + StringUtils.repeat('-', pack.length() + 1) + "\n\n";
      }
      for (IPrototype prototype : Console.INSTANCE.getCommandProcessor().getPrototypes(pack)) {
        String synopsis = prototype.getSynopsis();
        if (!pack.equals("main")) {
          synopsis = StringUtils.leftIndent(synopsis, 3);
        }
        output += "* " + synopsis + "\n";
      }
    }
    return output;
  }


}
