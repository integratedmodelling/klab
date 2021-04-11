//package org.integratedmodelling.klab.clitool.console.commands;
//
//import java.util.List;
//
//import org.integratedmodelling.kim.api.IServiceCall;
//import org.integratedmodelling.klab.api.cli.ICommand;
//import org.integratedmodelling.klab.api.runtime.ISession;
//
//public class Debug implements ICommand {
//
//	@Override
//	public Object execute(IServiceCall call, ISession session) {
//
//		if (((List<?>) call.getParameters().get("arguments")).size() > 0) {
//			List<?> args = ((List<?>) call.getParameters().get("arguments"));
//			String command = args.get(0).toString();
//			if ("new".equals(command)) {
//				org.integratedmodelling.klab.engine.debugger.Debug.INSTANCE.newDebugger(session);
//			} else {
//				session.getMonitor().error("Debug command " + command + " not recognized");
//			}
//		}
//		return null;
//	}
//
//}
