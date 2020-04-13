package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.ViewSetup;
import org.integratedmodelling.klab.rest.ViewSetup.Panel;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

public class SessionActor extends KlabActor {

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity) {
		super(context, identity);
	}

	@Override
	protected IBehavior onLoad(IBehavior behavior) {

		/*
		 * collect info about the UI in a bean. If not empty, send bean so that the UI
		 * can prepare.
		 */
		ViewSetup setup = new ViewSetup();
		setup.setStyle(behavior.getStatement().getStyle());
		for (Action action : behavior.getActions()) {
			for (IAnnotation annotation : action.getAnnotations()) {
				if ("panel".equals(annotation.getName())) {
					setup.getPanels()
							.add(new Panel(
									annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
									annotation.get("style", String.class)));
				}
				if ("header".equals(annotation.getName())) {
					setup.setHeader(
							new Panel(annotation.containsKey("id") ? annotation.get("id", String.class) : "header",
									annotation.get("style", String.class)));
				}
				if ("footer".equals(annotation.getName())) {
					setup.setFooter(
							new Panel(annotation.containsKey("id") ? annotation.get("id", String.class) : "footer",
									annotation.get("style", String.class)));
				}
				// TODO the rest
			}
			
			/*
			 * TODO visit action for view calls: if there is any call to the view actor, add the "default" panel
			 * unless already added
			 */

			
		}

		if (setup.getPanels().size() > 0 || setup.getStyle() != null || setup.getFooter() != null
				|| setup.getHeader() != null) {
			((Session) identity).getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
					setup);
		}

		return super.onLoad(behavior);
	}

	@Override
	protected ReceiveBuilder<KlabMessage> configure() {
		// TODO add all view messages and runtime messages
		return super.configure();
	}

	@Override
	protected SessionActor onPostStop() {
		// TODO do something
		return this;
	}

}
