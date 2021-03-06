package org.integratedmodelling.docs.samples;

import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.engine.Engine;

public class EmbeddedEngine {

	public static void main() throws Exception {

		Engine engine = Engine.start();

		try (ISession session = engine.createSession()) {

//			IObservation observation = session.getState()
//					.submit("im:ariesteam:example.locations:tanzania>>earth:Region").get();
//			session.getState().submit("geography:Elevation").get();

			// observation.explore();

		} finally {
			engine.stop();
		}
	}

}
