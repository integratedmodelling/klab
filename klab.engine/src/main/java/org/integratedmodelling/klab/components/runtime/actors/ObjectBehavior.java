package org.integratedmodelling.klab.components.runtime.actors;

import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.klab.Actors;
import org.integratedmodelling.klab.Version;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Behavior;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.utils.Pair;

@Behavior(id="object", version=Version.CURRENT)
public class ObjectBehavior {

	@Action(id="stop")
	public static class MoveAway extends KlabAction {

		public MoveAway(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub
			
		}
		
	}

	@Action(id="bind")
	public static class Bind extends KlabAction {

		public Bind(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			IObservable what = Actors.INSTANCE.getArgument(arguments, IObservable.class);
			String behavior = Actors.INSTANCE.getArgument(arguments, String.class);
			IKimExpression filter = Actors.INSTANCE.getArgument(arguments, IKimExpression.class);
			if (what == null || behavior == null || Actors.INSTANCE.getBehavior(behavior) == null) {
				// TODO improve message
				error("error in bind action: behavior or observable not specified or recognized");
			} else {
				this.scope.runtimeScope.getBehaviorBindings().put(what.getType(), new Pair<>(behavior, filter));
			}
		}
		
	}

	@Action(id="connect")
	public static class Connect extends KlabAction {

		public Connect(IActorIdentity<KlabMessage> identity, IParameters<String> arguments, KlabActor.Scope scope) {
			super(identity, arguments, scope);
		}

		@Override
		void run() {
			// TODO Auto-generated method stub
			
		}
		
	}

}
