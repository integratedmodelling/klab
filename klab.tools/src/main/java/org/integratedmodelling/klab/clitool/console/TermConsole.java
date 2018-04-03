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

import java.util.Iterator;
import java.util.Map;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.CliStartupOptions;
import org.integratedmodelling.klab.clitool.api.IConsole;
import org.integratedmodelling.klab.clitool.contrib.console.DragonConsoleFrame;

public class TermConsole implements IConsole {

  DragonConsoleFrame terminal;

  // class SessionListener implements ISession.Listener {
  //
  // @Override
  // public void contextEvent(IContext context, boolean isNew) {
  // if (isNew) {
  // client.getMonitor().info("New context available: " + context
  // + ": set as current", Messages.INFOCLASS_NEW_RESOURCE_AVAILABLE);
  // Environment.get().setContext(context);
  // } else {
  // client.getMonitor()
  // .info("Current context modified: " + context, Messages.INFOCLASS_RESOURCE_MODIFIED);
  // }
  // if (((ClientSession) context.getSession()).hasViewer()) {
  // ((ClientSession) context.getSession()).getViewer().show(context);
  // }
  // }
  //
  // @Override
  // public void taskEvent(ITask task, boolean isNew) {
  // if (task.getStatus() == Status.ERROR) {
  // client.getMonitor().error("error in task: " + task.getDescription());
  // } else if (task.getStatus() == Status.RUNNING) {
  // client.getMonitor().info("task started: " + task.getDescription(), Messages.TASK_STARTED);
  // } else
  // if (task.getStatus() == Status.FINISHED) {
  // client.getMonitor().info("task finished: " + task.getDescription(), Messages.TASK_FINISHED);
  // }
  // }
  // }

  @Override
  public void grabCommandLine(String prompt, String endCommand, CommandListener listener) {
    terminal.console.grabCommandLine(prompt, endCommand, listener);
  }

  public static class Monitor implements IMonitor {

    // @Override
    // public void info(Object info, String infoClass) {
    // _this.info(info, infoClass);
    // }
    //
    // @Override
    // public void warn(Object o) {
    // _this.warning(o);
    // }
    //
    // @Override
    // public void error(Object o) {
    // hasErrors = true;
    // _this.error(o);
    // }
    //
    // @Override
    // public void debug(Object o) {
    // }

    @Override
    public void send(Object o) {
      // TODO Auto-generated method stub

    }

    @Override
    public void info(Object... info) {}

    @Override
    public void warn(Object... o) {}

    @Override
    public void error(Object... o) {}

    @Override
    public void debug(Object... o) {}

    @Override
    public IIdentity getIdentity() {
      // TODO Auto-generated method stub
      return null;
    }

    @Override
    public boolean hasErrors() {
      // TODO Auto-generated method stub
      return false;
    }

  }

  public void start(CliStartupOptions options) throws Exception {

    // KLAB.CONFIG = new Configuration(true);
    //
    // String certfile = System.getProperty(IConfiguration.CERTFILE_PROPERTY);
    // if (certfile == null) {
    // certfile = KLAB.CONFIG.getDataPath() + File.separator + "im.cert";
    // }
    //
    // File cert = new File(certfile);
    // if (!cert.exists()) {
    // throw new KlabIOException("IM certificate not found: " + certfile);
    // }
    //
    // client = new ModelingClient(cert, new Monitor());
    //
    // client.addListener(new IModelingEngine.Listener() {
    //
    // @Override
    // public void sessionOpened(ISession session) {
    // session.addListener(new SessionListener());
    // }
    //
    // @Override
    // public void sessionClosed(ISession session) {
    // }
    //
    // @Override
    // public void engineLocked() {
    // }
    //
    // @Override
    // public void engineUnlocked() {
    // }
    //
    // @Override
    // public void engineUserAuthenticated(IUser user) {
    // }
    // });

    try {
      javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
        @Override
        public void run() {
          String buildInfo = "";
          if (!Version.VERSION_BUILD.equals("VERSION_BUILD")) {
            buildInfo = " build " + Version.VERSION_BUILD + " (" + Version.VERSION_BRANCH + " "
                + Version.VERSION_DATE + ")";
          }
          terminal = new DragonConsoleFrame("k.LAB v" + Version.CURRENT + buildInfo, false,
              new CommandHistory());
          terminal.console.setCommandProcessor(new CommandProcessor(TermConsole.this, new Monitor()));
          terminal.console.append("k.LAB command line shell v" + new Version().toString() + "\n");
          terminal.console.append("Work directory: " + Klab.INSTANCE.getWorkDirectory() + "\n");
          terminal.console.append("Enter 'help' for a list of commands; 'exit' quits.\n");
          terminal.setVisible(true);
          terminal.console.setPrompt(">> ");

          new Thread() {
            @Override
            public void run() {

              CliRuntime.INSTANCE.initialize(TermConsole.this, options);
              terminal.console.setCommandProcessor(CliRuntime.INSTANCE.getCommandProcessor());

              // client.addListener(new IModelingEngine.Listener() {
              //
              // @Override
              // public void sessionOpened(ISession session) {
              // IViewer viewer = ((ClientSession) session).getViewer();
              // if (viewer instanceof WebViewer) {
              // ((WebViewer) viewer).start();
              // }
              // }
              //
              // @Override
              // public void sessionClosed(ISession session) {}
              //
              // @Override
              // public void engineLocked() {
              // // if (workspaceCleared) {
              // // _this.info("remote workspace cleared", null);
              // // }
              // }
              //
              // @Override
              // public void engineUnlocked() {
              // // TODO Auto-generated method stub
              //
              // }
              //
              // @Override
              // public void engineUserAuthenticated(IUser user) {
              // // TODO Auto-generated method stub
              //
              // }
              // });
            }
          }.start();
        }

      });
    } catch (Exception exc) {
      exc.printStackTrace();
    }
  }

  @Override
  public void error(Object e) {
    if (e instanceof Throwable) {
      /*
       * TODO log stack trace
       */
      e = ((Throwable) e).getMessage();
    } else {
      e = e.toString();
    }
    terminal.console.append("&R-" + e + "\n");
  }

  @Override
  public void setPrompt(String s) {
    terminal.console.setPrompt(s);
  }
  
  @Override
  public void warning(Object e) {
    terminal.console.append("&D-" + e + "\n");
  }

  @Override
  public void echo(Object e) {
    terminal.console.append("&X-" + e + "\n");
    terminal.console.repaint();
  }

  @Override
  public void info(Object e, String infoClass) {

    if (e == null || terminal == null || terminal.console == null) {
      Klab.INSTANCE.info(e);
      return;
    }

    String color = "&l-";
    if (infoClass != null) {
      switch (infoClass) {
        case "TASK":
          color = "&g-";
          break;
        case "GENERAL":
          color = "&p-";
          break;
      }
    }

    if (terminal.console != null) {
      terminal.console.append(color + e + "\n");
    }
  }

  protected void output(Object e) {
    terminal.console.append("&w-" + e + "\n");
  }

  @Override
  public void outputResult(String input, Object ret) {

    if (ret == null) {
      return;
    }

    if (ret instanceof Map) {
      for (Object o : ((Map<?, ?>) ret).keySet()) {
        output(o + " = " + ((Map<?, ?>) ret).get(o));
      }
    } else if (ret instanceof Iterable) {
      for (Iterator<?> it = ((Iterable<?>) ret).iterator(); it.hasNext();) {
        output(it.next());
      }
    } else {
      output(ret);
    }
  }

  @Override
  public void reportCommandResult(String input, boolean ok) {
    // add to history
    if (terminal.console.getHistory() != null) {
      terminal.console.getHistory().append(input);
    }
  }

}
