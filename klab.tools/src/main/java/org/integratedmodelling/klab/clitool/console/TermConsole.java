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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.function.Consumer;

import org.integratedmodelling.klab.Indexing;
import org.integratedmodelling.klab.Klab;
import org.integratedmodelling.klab.Logging;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.api.monitoring.IMessageBus;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.runtime.rest.INotification;
import org.integratedmodelling.klab.api.services.IIndexingService;
import org.integratedmodelling.klab.api.services.IIndexingService.Match;
import org.integratedmodelling.klab.clitool.CliRuntime;
import org.integratedmodelling.klab.clitool.CliStartupOptions;
import org.integratedmodelling.klab.clitool.api.IConsole;
import org.integratedmodelling.klab.clitool.contrib.console.DragonConsole.SearchHandler;
import org.integratedmodelling.klab.clitool.contrib.console.DragonConsoleFrame;
import org.integratedmodelling.klab.engine.debugger.Inspector;
import org.integratedmodelling.klab.engine.indexing.Indexer;
import org.integratedmodelling.klab.monitoring.Message;
import org.integratedmodelling.klab.utils.NotificationUtils;

public class TermConsole implements IConsole {

	DragonConsoleFrame terminal;

	@Override
	public void grabCommandLine(String prompt, String endCommand, CommandListener listener) {
		terminal.console.grabCommandLine(prompt, endCommand, listener);
	}

	public class Monitor implements IMonitor {

		private int waitTime;
		private Inspector inspector;
		
        @Override
		public void send(Object... o) {
			if (o != null && o.length > 0) {
				IMessageBus bus = Klab.INSTANCE.getMessageBus();
				if (bus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						bus.post((IMessage) o[0]);
					} else {
						bus.post(Message.create(CliRuntime.INSTANCE.getSession().getId(), o));
					}
				}
			}
		}

		@Override
		public Future<IMessage> ask(Object... o) {
			if (o != null && o.length > 0) {
				IMessageBus bus = Klab.INSTANCE.getMessageBus();
				if (bus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						bus.ask((IMessage) o[0]);
					} else {
						bus.ask(Message.create(CliRuntime.INSTANCE.getSession().getId(), o));
					}
				}
			}
			return null;
		}

		@Override
		public void post(Consumer<IMessage> handler, Object... o) {
			if (o != null && o.length > 0) {
				IMessageBus bus = Klab.INSTANCE.getMessageBus();
				if (bus != null) {
					if (o.length == 1 && o[0] instanceof IMessage) {
						bus.post((IMessage) o[0], handler);
					} else if (o.length == 1 && o[0] instanceof INotification) {
						bus.post(Message.create((INotification) o[0], CliRuntime.INSTANCE.getSession().getId()),
								handler);
					} else {
						bus.post(Message.create(CliRuntime.INSTANCE.getSession().getId(), o), handler);
					}
				}
			}
		}

		@Override
		public void info(Object... info) {
			TermConsole.this.info(NotificationUtils.getMessage(info), null);
		}

		@Override
		public void warn(Object... o) {
			TermConsole.this.warning(NotificationUtils.getMessage(o));
		}

		@Override
		public void error(Object... o) {
			TermConsole.this.error(NotificationUtils.getMessage(o));
		}

		@Override
		public void debug(Object... o) {
			TermConsole.this.debug(NotificationUtils.getMessage(o));
		}

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

		@Override
		public boolean isInterrupted() {
			return false;
		}

        @Override
        public void addWait(int seconds) {
            // TODO improve with specific messages
            this.waitTime = seconds;
            warn("Please try this operation again in " + seconds + " seconds");
        }

        @Override
        public int getWaitTime() {
            // TODO Auto-generated method stub
            return this.waitTime;
        }

	}

	public void start(CliStartupOptions options) throws Exception {

		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {

					String buildInfo = "";
					if (!Version.VERSION_BUILD.equals("VERSION_BUILD")) {
						buildInfo = " build " + Version.VERSION_BUILD + " (" + Version.VERSION_BRANCH + " "
								+ Version.VERSION_DATE + " @" + Version.VERSION_COMMIT + ")";
					}
					terminal = new DragonConsoleFrame("k.LAB v" + Version.CURRENT + buildInfo, false,
							new CommandHistory());
					terminal.console.setCommandProcessor(new CommandProcessor(TermConsole.this, new Monitor()));
					terminal.console.append("k.LAB command line shell v" + new Version().toString() + "\n");
					terminal.console.append("Work directory: " + Klab.INSTANCE.getWorkDirectory() + "\n");
					terminal.console.append("Enter 'help' for a list of commands; 'exit' quits.\n");
					terminal.setVisible(true);
					terminal.console.setPrompt(">> ");
					terminal.console.setSearchHandler(new SearchHandler() {

						String current = "";
						IIndexingService.Context context;
						List<Match> currentMatches;
						boolean finished;

						@Override
						public void initializeSearch() {
							context = Indexing.INSTANCE.createContext();
						}

						@Override
						public boolean handleBackspace() {
							if (current.isEmpty()) {
								return false;
							}
							current = current.substring(0, current.length() - 1);
							search();
							return true;
						}

						@Override
						public void cancelSearch() {
							current = "";
							context = null;
						}

						@Override
						public String chooseMatch(int i) {
							if (currentMatches != null && currentMatches.size() >= i) {
								Match ret = currentMatches.get(i - 1);
								context = context.accept(ret);
								finished = context.isEnd();
								current = "";
								return ret.getId();
							}
							return null;
						}

						@Override
						public boolean addCharacter(char character) {

							if (!current.isEmpty() && currentMatches != null && currentMatches.size() == 0) {
								return false;
							}

							current += character;
							search();
							return true;
						}

						private void search() {

							if (current.length() > 1) {
								int i = 0;
								currentMatches = new ArrayList<>();
								for (Match match : Indexer.INSTANCE.query(current, context)) {
									if (i == 0) {
										scream("\n==== Search results for '" + current + "' ====\n");
									}
									scream((++i) + "] " + match.getId() + " (" + match.getDescription() + ")");
									currentMatches.add(match);
								}
								if (currentMatches.size() == 0) {
									scream("No matches for '" + current + "'");
								} else {
									echo("\n");
								}
							}
						}

						@Override
						public boolean isFinished() {
							return finished;
						}

						@Override
						public void cancelLastMatch() {
							current = "";
							context = context.previous();
						}

					});

					// redirect notifications to console
					Logging.INSTANCE.setDebugWriter((message) -> debug(message));
					Logging.INSTANCE.setInfoWriter((message) -> info(message, null));
					Logging.INSTANCE.setErrorWriter((message) -> error(message));
					Logging.INSTANCE.setWarningWriter((message) -> warning(message));

					new Thread() {

						@Override
						public void run() {
							CliRuntime.INSTANCE.initialize(TermConsole.this, options);
							terminal.console.setCommandProcessor(CliRuntime.INSTANCE.getCommandProcessor());
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

	public void debug(Object e) {
		if (e instanceof Throwable) {
			/*
			 * TODO log stack trace
			 */
			e = ((Throwable) e).getMessage();
		} else {
			e = e.toString();
		}
		terminal.console.append("&p-" + e + "\n");
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
	public void scream(Object e) {
		terminal.console.append("&w-" + e + "\n");
		terminal.console.repaint();
	}

	@Override
	public void info(Object e, String infoClass) {

		if (e == null || terminal == null || terminal.console == null) {
			Logging.INSTANCE.info(e);
			return;
		}

		String color = "&c-";
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

	@Override
	public void enableInput() {
		terminal.console.setEnabled(true);
	}

	@Override
	public void disableInput() {
		terminal.console.setEnabled(false);
	}

}
