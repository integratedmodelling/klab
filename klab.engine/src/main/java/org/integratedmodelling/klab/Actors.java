package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActors.Notifier;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.extensions.actors.Message;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.kim.Prototype;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.utils.xtext.KactorsInjectorProvider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
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
	private Map<String, IBehavior> behaviors = Collections.synchronizedMap(new HashMap<>());
	private Map<String, BehaviorReference> behaviorDescriptors = Collections.synchronizedMap(new HashMap<>());
	private Map<String, Class<? extends KlabMessage>> messageClasses = Collections.synchronizedMap(new HashMap<>());

	public IBehavior getBehavior(String behaviorId) {
		return behaviors.get(behaviorId);
	}

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

	/**
	 * Install listeners to build behaviors on read and organize them by project
	 */
	public void setup() {
		KActors.INSTANCE.addNotifier(new Notifier() {
			@Override
			public void notify(IKActorsBehavior behavior) {
				behaviors.put(behavior.getName(),
						new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(behavior));
			}
		});
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
	public ActorSystem<Void> getSupervisor() {
		return this.supervisor;
	}

	/**
	 * Create a direct child of the supervisor with the specified identity.
	 * Implementations should only call this for top-level actors and have the
	 * others created through messages to them.
	 * 
	 * @param <T>
	 * @param create
	 * @param identity
	 * @return
	 */
	public <T> ActorRef<T> createActor(Behavior<T> create, IIdentity identity) {
		return ActorSystem.create(create, identity.getId());
	}

	/**
	 * Called when a class annotated as a behavior is found.
	 * 
	 * @param annotation
	 * @param cls
	 */
	@SuppressWarnings("unchecked")
	public void registerBehavior(org.integratedmodelling.klab.api.extensions.actors.Behavior annotation, Class<?> cls) {

		BehaviorReference descriptor = behaviorDescriptors.get(annotation.id());
		if (descriptor == null) {
			descriptor = new BehaviorReference();
			behaviorDescriptors.put(annotation.id(), descriptor);
			descriptor.setName(annotation.id());
		}

		if (!annotation.description().isEmpty()) {
			descriptor.setDescription(annotation.description());
		}
		if (!annotation.color().isEmpty()) {
			descriptor.setColor(annotation.color());
		}

		for (Class<?> cl : cls.getDeclaredClasses()) {
			Message message = cl.getAnnotation(Message.class);
			if (message != null) {
				BehaviorReference.Action ad = new BehaviorReference.Action();
				ad.setName(message.id());
				ad.setDescription(message.description());
				descriptor.getActions().add(ad);
				this.messageClasses.put(message.id(), (Class<? extends KlabMessage>) cl);
			}
		}
	}

	public void exportBehaviors(File file) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
			mapper.setSerializationInclusion(Include.NON_NULL);
			JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class, BehaviorReference.class);
			mapper.writerFor(type).writeValue(file, this.behaviorDescriptors);
		} catch (IOException e) {
			Logging.INSTANCE.error(e);
		}
	}

}
