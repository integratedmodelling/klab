package org.integratedmodelling.klab.components.runtime.actors;

import java.util.Random;

import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;

/**
 * 
 * Messages:
 * <ul>
 * <li><b>maybe[(probability, value)]</b> fire value (default TRUE) with probability
 * (default 0.5)</li>
 * <li><b>choose(v1, v2, ..., vn)</b> fire one of the values with same probability; if
 * first value is a distribution and vals are same number, use that to
 * choose</li>
 * <li><b>when</b> fire any new observations made in the context after the call; match
 * by type (concept) or name (string)</li>
 * </ul>
 * 
 * @author Ferd
 *
 */
@Behavior(id = "session", version = Version.CURRENT)
public class RuntimeBehavior {

	@Action(id = "observe")
	public static class Observe extends KlabAction {

		public Observe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub

		}
	}

	@Action(id = "maybe")
	public static class Maybe extends KlabAction {

		Random random = new Random();

		double probability = 0.5;
		Object fired = null;

		public Maybe(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
			boolean pdef = false;
			for (String key : arguments.getUnnamedKeys()) {
				Object o = arguments.get(key);
				if (o instanceof Double && !pdef && ((Double) o) <= 1 && ((Double) o) >= 0) {
					probability = (Double) o;
					pdef = true;
				} else {
					fired = o;
				}
			}
		}

		@Override
		void run() {
			if (random.nextDouble() < probability) {
				fire(fired == null ? DEFAULT_FIRE : fired, true);
			} else {
				// fire anyway so that anything that's waiting can continue
				fire(false, true);
			}
		}
	}


}
