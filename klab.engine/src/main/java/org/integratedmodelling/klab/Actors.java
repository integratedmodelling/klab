package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.components.runtime.actors.SessionActor.SessionCommand;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.utils.xtext.KactorsInjectorProvider;

import com.google.inject.Inject;
import com.google.inject.Injector;

import akka.actor.typed.ActorRef;
import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.PostStop;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

public enum Actors implements IActorsService {

	INSTANCE;

	@Inject
	ParseHelper<Model> kActorsParser;
	private ActorSystem<Void> supervisor;

	/**
	 * The actor system entry point at /user and available as getSupervisor(). It
	 * will be the (direct for now) father of all session actors. We create this to
	 * have a visible top-level supervisor reference at the first call to Actors,
	 * which tells us that actors will have a role in the engine.
	 * 
	 * @author Ferd
	 *
	 */
	public static class KlabSupervisor extends AbstractBehavior<Void> {

		public static Behavior<Void> create() {
			return Behaviors.setup(KlabSupervisor::new);
		}

		private KlabSupervisor(ActorContext<Void> context) {
			super(context);
			Logging.INSTANCE.info("k.LAB actor system initialized");
		}

		@Override
		public Receive<Void> createReceive() {
			return newReceiveBuilder()
					// for now just stop. We should have this respond to a onSession message by
					// creating the session actor?
					.onSignal(PostStop.class, signal -> onPostStop()).build();
		}

		private KlabSupervisor onPostStop() {
			getContext().getLog().info("k.LAB actor system stopped");
			return this;
		}
	}

	private Actors() {
		IInjectorProvider injectorProvider = new KactorsInjectorProvider();
		Injector injector = injectorProvider.getInjector();
		if (injector != null) {
			injector.injectMembers(this);
		}
		this.supervisor = ActorSystem.create(KlabSupervisor.create(), "klab-system");
		Services.INSTANCE.registerService(this, IActorsService.class);
	}

	@Override
	public IKActorsBehavior declare(URL url) throws KlabException {
		try (InputStream stream = url.openStream()) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKActorsBehavior declare(File file) throws KlabException {
		try (InputStream stream = new FileInputStream(file)) {
			return declare(stream);
		} catch (Exception e) {
			throw new KlabIOException(e);
		}
	}

	@Override
	public IKActorsBehavior declare(InputStream file) throws KlabValidationException {
		IKActorsBehavior ret = null;
		try {
			String definition = IOUtils.toString(file);
			Model model = kActorsParser.parse(definition);
			ret = KActors.INSTANCE.declare(model);
		} catch (Exception e) {
			throw new KlabValidationException(e);
		}
		return ret;
	}

	/**
	 * The supervisor actor.
	 * 
	 * @return
	 */
	public ActorRef<Void> getSupervisor() {
		return this.supervisor;
	}

	public <T> ActorRef<T> createActor(Behavior<T> create, IIdentity identity) {
		return ActorSystem.create(create, identity.getId());
	}

}
