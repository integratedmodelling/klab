package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

/**
 * View messages.
 * 
 * <ul>
 * <li></li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "view", version = Version.CURRENT)
public class ViewBehavior {

	@Action(id = "alert")
	public static class Alert extends KlabAction {

		public Alert(IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
				KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			
			
			
			Session session = this.identity.getParentIdentity(Session.class);
//			session.getMonitor().send(o);
			
			
			
			// TODO Auto-generated method stub
			System.out.println("ALERT! ALERT! SENT BY " + sender);
		}
	}
}
