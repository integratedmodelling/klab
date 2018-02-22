package org.integratedmodelling.klab.clitool;

import org.integratedmodelling.klab.clitool.console.Console;
import org.integratedmodelling.klab.engine.EngineStartupOptions;

public class Main {

  public static void main(String[] args) throws Exception {
    
    CliStartupOptions options = new CliStartupOptions();
    options.initialize(args);
    
    if (options.isHelp()) {
      System.out.println(new EngineStartupOptions().usage());
      System.exit(0);
    }
    
    if (options.getArgument() == null) {
      Console.INSTANCE.start(options);
    }
  }
}
