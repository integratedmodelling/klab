package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kactors.api.IKActorsStatement;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.actors.IBehavior.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.monitoring.IMessage;
import org.integratedmodelling.klab.components.runtime.actors.SystemBehavior.Load;
import org.integratedmodelling.klab.components.runtime.actors.behavior.BehaviorAction;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.rest.View;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.NameGenerator;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.ReceiveBuilder;

/**
 * The session actor only does small things, like interacting with the monitor
 * and, crucially, spawning all other actors needed in a session. Essentially
 * it's a cow actor dedicated to making actors and running the actions in
 * {@link RuntimeBehavior} - even behaviors running in a session will be
 * launched in a Runtime actor, to prevent deadlocks when an action run within a
 * session requires another actor to spawn in order to terminate.
 * 
 * @author Ferd
 *
 */
public class SessionActor extends KlabActor {

	public static Behavior<KlabMessage> create(Session session) {
		return Behaviors.setup(ctx -> new SessionActor(ctx, session));
	}

	public SessionActor(ActorContext<KlabMessage> context, Session identity) {
		super(context, identity);
	}

	@Override
	protected Behavior<KlabMessage> loadBehavior(Load message) {

		IBehavior behavior = Actors.INSTANCE.getBehavior(message.behavior);

		setupView(behavior);

		/*
		 * spawn a new runtime actor and have it load the behavior
		 */
		ActorRef<KlabMessage> actor = getContext().spawn(RuntimeActor.create((Session) identity),
				identity.getId() + NameGenerator.shortUUID());

		actor.tell(message);

		return Behaviors.same();

	}

	private void setupView(IBehavior behavior) {

		/*
		 * collect info about the UI in a bean. If not empty, send bean so that the UI
		 * can prepare.
		 */
		View view = new View(behavior.getName());
		view.setStyle(behavior.getStatement().getStyle());
		for (Action action : behavior.getActions()) {

			ViewPanel panel = null;

			for (IAnnotation annotation : action.getAnnotations()) {
				if ("panel".equals(annotation.getName())) {
					view.getPanels()
							.add(panel = new ViewPanel(
									annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
									annotation.get("style", String.class)));
				}
				if ("left".equals(annotation.getName())) {
					view.getLeftPanels()
							.add(panel = new ViewPanel(
									annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
									annotation.get("style", String.class)));
				}
				if ("right".equals(annotation.getName())) {
					view.getRightPanels()
							.add(panel = new ViewPanel(
									annotation.containsKey("id") ? annotation.get("id", String.class) : action.getId(),
									annotation.get("style", String.class)));
				}
				if ("header".equals(annotation.getName()) || "header".equals(action.getId())
						|| "top".equals(annotation.getName())) {
					view.setHeader(panel = new ViewPanel(
							annotation.containsKey("id") ? annotation.get("id", String.class) : "header",
							annotation.get("style", String.class)));
				}
				if ("footer".equals(annotation.getName()) || "footer".equals(action.getId())
						|| "bottom".equals(annotation.getName())) {
					view.setFooter(panel = new ViewPanel(
							annotation.containsKey("id") ? annotation.get("id", String.class) : "footer",
							annotation.get("style", String.class)));
				}

			}

			if (panel != null) {
				/*
				 * visit action for view calls: if there is any call to the view actor, add the
				 * "default" panel unless already added
				 */
				visitViewActions(action, panel);
			}
		}

		if (!view.empty()) {
			((Session) identity).getMonitor().send(IMessage.MessageClass.UserInterface, IMessage.Type.SetupInterface,
					view);
		}
	}

	private void visitViewActions(Action action, ViewPanel panel) {
		visitViewActions(((BehaviorAction) action).getStatement().getCode(), panel);
	}

	private void visitViewActions(IKActorsStatement statement, ViewComponent parent) {
		switch(statement.getType()) {
		case ACTION_CALL:
			break;
		case ASSIGNMENT:
			break;
		case CONCURRENT_GROUP:
			break;
		case DO_STATEMENT:
			break;
		case FIRE_VALUE:
			break;
		case FOR_STATEMENT:
			break;
		case IF_STATEMENT:
			break;
		case SEQUENCE:
			break;
		case TEXT_BLOCK:
			break;
		case WHILE_STATEMENT:
			break;
		default:
			break;
		}
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
