package org.integratedmodelling.klab;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActors.Notifier;
import org.integratedmodelling.kactors.model.KActors.ValueTranslator;
import org.integratedmodelling.kactors.model.KActorsQuantity;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.kim.model.KimExpression;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.runtime.actors.KlabAction;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.KlabMessage;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.api.IActorIdentity;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.utils.Pair;
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
	private Map<String, Pair<String, Class<? extends KlabAction>>> actionClasses = Collections
			.synchronizedMap(new HashMap<>());

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
		KActors.INSTANCE.setValueTranslator(new ValueTranslator() {
			@Override
			public Object translate(KActorsValue container, Object value) {

				switch (container.getType()) {
				case ANYTHING:
					break;
				case ANYTRUE:
					break;
				case ANYVALUE:
					break;
				case BOOLEAN:
					break;
				case CLASS:
					break;
				case DATE:
					break;
				case ERROR:
					break;
				case EXPRESSION:
					value = new KimExpression(value.toString() + " ", Extensions.DEFAULT_EXPRESSION_LANGUAGE);
					break;
				case IDENTIFIER:
					break;
				case LIST:
					break;
				case MAP:
					break;
				case NODATA:
					break;
				case NUMBER:
					break;
				case NUMBERED_PATTERN:
					break;
				case OBSERVABLE:
					value = Observables.INSTANCE.declare(value.toString());
					break;
				case QUANTITY:
					if (value instanceof KActorsQuantity) {
						if (((KActorsQuantity) value).getUnit() != null) {
							value = Quantity.create(((KActorsQuantity) value).getValue(),
									Unit.create(((KActorsQuantity) value).getUnit()));
						} else if (((KActorsQuantity) value).getCurrency() != null) {
							value = Quantity.create(((KActorsQuantity) value).getValue(),
									Currency.create(((KActorsQuantity) value).getCurrency()));
						}
					}
					break;
				case RANGE:
					break;
				case REGEXP:
					break;
				case STRING:
					break;
				case TABLE:
					break;
				case TYPE:
					break;
				case URN:
					break;
				}

				return value;
			}
		});
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
	 * Install listeners to build behaviors on read and organize them by project.
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
	 * others created through messages to them. The top-level identities for now are
	 * user actors.
	 * 
	 * @param <T>
	 * @param create
	 * @param identity
	 * @return
	 */
	public <T> ActorRef<T> createActor(Behavior<T> create, IIdentity identity) {
		return ActorSystem.create(create,
				identity instanceof IUserIdentity ? sanitize(((IUserIdentity) identity).getUsername())
						: identity.getId());
	}

	private String sanitize(String username) {
		// should be enough
		return username.replace('.', '_');
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
			Action message = cl.getAnnotation(Action.class);
			if (message != null) {
				BehaviorReference.Action ad = new BehaviorReference.Action();
				ad.setName(message.id());
				ad.setDescription(message.description());
				descriptor.getActions().add(ad);
				this.actionClasses.put(message.id(), new Pair<>(annotation.id(), (Class<? extends KlabAction>) cl));
			}
		}
	}

	public void exportBehaviors(File file) {

		/*
		 * Fill this in so we can read actor files with the actual knowledge of the
		 * installed behaviors. This is called at the right time, after loading and
		 * before reading behaviors.
		 */
		KActors.INSTANCE.getBehaviorManifest().putAll(behaviorDescriptors);

		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT); // pretty print
			mapper.setSerializationInclusion(Include.NON_NULL);
			JavaType type = mapper.getTypeFactory().constructMapLikeType(Map.class, String.class,
					BehaviorReference.class);
			mapper.writerFor(type).writeValue(file, this.behaviorDescriptors);
		} catch (IOException e) {
			Logging.INSTANCE.error(e);
		}
	}

	/**
	 * Find the recipient for a message directed to self that does not match any
	 * messages. This done at runtime to support shorthand syntax in k.Actors; any
	 * unknown messages should go to the user actor using the UnknownMessage
	 * pattern.
	 * 
	 * @param message
	 * @param identity the identity that does not have the message. Look it up in
	 *                 the identities above it.
	 * @return an actor reference or null. If null, the asker should send an unknown
	 *         message to the user actor.
	 */
	public Pair<String, ActorRef<KlabMessage>> lookupRecipient(String message, IActorIdentity<KlabMessage> identity) {

		Pair<String, Class<? extends KlabAction>> record = actionClasses.get(message);
		if (record != null) {
			// it's one of the system behaviors: lookup the recipient based on behavior ID
			switch (record.getFirst()) {
			case "view":
			case "session":
				return new Pair<>(record.getFirst(), identity.getParentIdentity(Session.class).getActor());
			case "user":
				return new Pair<>(record.getFirst(), identity.getParentIdentity(EngineUser.class).getActor());
			}
		}

		return null;
	}

	/**
	 * Create and return an action from one of the system behaviors.
	 * 
	 * @param behavior
	 * @param id
	 * @param sender
	 * @param arguments
	 * @param messageId
	 * @param scope
	 * @return
	 */
	public KlabAction getSystemAction(String id, IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
			KlabActor.Scope scope) {

		Pair<String, Class<? extends KlabAction>> cls = actionClasses.get(id);
		if (cls != null) {
			try {
				Constructor<? extends KlabAction> constructor = cls.getSecond().getConstructor(IActorIdentity.class,
						IParameters.class, KlabActor.Scope.class);
				return constructor.newInstance(identity, arguments, scope);
			} catch (Throwable e) {
				scope.getMonitor().error("Error while creating action " + id + ": " + e.getMessage());
			}
		}
		return null;
	}

	public Collection<String> getBehaviorIds() {
		return behaviors.keySet();
	}

	public void instrument(List<IAnnotation> annotations, Observation observation) {
		instrument(annotations, observation, observation.getRuntimeScope());
	}
		
	
	public void instrument(List<IAnnotation> annotations, Observation observation, IRuntimeScope scope) {

		/*
		 * find any bindings made at runtime
		 */
		Pair<String, IKimExpression> rb = observation.getRuntimeScope().getBehaviorBindings()
				.get(observation.getObservable().getType());
		if (rb != null) {
			IBehavior b = getBehavior(rb.getFirst());
			if (b != null) {
				if (rb.getSecond() != null) {
					// TODO filter
				}
				observation.getRuntimeScope().scheduleActions(observation, b);
				((Observation) observation).load(b, scope);
			}
		}

		for (IAnnotation annotation : annotations) {
			if (annotation.getName().equals("bind")) {
				String behavior = annotation.containsKey("behavior") ? annotation.get("behavior", String.class)
						: annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, String.class);
				if (behavior != null) {
					IBehavior b = getBehavior(behavior);
					if (b != null) {

						if (annotation.contains("filter")) {
							// TODO build/cache and run filter, skip if false
						}

						/*
						 * observation will load behavior, schedule all the temporal actions it contains
						 * before main is run
						 */
						observation.getRuntimeScope().scheduleActions(observation, b);

						/*
						 * load the behavior, running any main actions right away
						 */
						((Observation) observation).load(b, scope);
					}
				}
			}
		}
	}

	/**
	 * Find the first unnamed parameter that matches the passed class, resolving
	 * actor values into their correspondent value.
	 * 
	 * @param <T>
	 * @param arguments
	 * @param class1
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getArgument(IParameters<String> arguments, Class<T> cls) {
		for (String key : arguments.getUnnamedKeys()) {
			Object ret = arguments.get(key);
			if (ret instanceof KActorsValue) {
				ret = ((KActorsValue) ret).getValue();
			}
			if (ret != null && cls.isAssignableFrom(ret.getClass())) {
				return (T) ret;
			}
		}
		return null;
	}

	/**
	 * Find the first parameter that matches the passed class, looking up by name
	 * first and checking the unnamed parameters if not found, resolving actor
	 * values into their correspondent value.
	 * 
	 * @param <T>
	 * @param arguments
	 * @param parameterName
	 * @param class1
	 * @return
	 */
	public <T> T getArgument(IParameters<String> arguments, String parameterName, Class<T> class1) {
		// TODO Auto-generated method stub
		return null;
	}

	public void add(File file) {
		// TODO Auto-generated method stub
		
	}

	public void delete(File file) {
		// TODO Auto-generated method stub
		
	}

	public void touch(File file) {
		// TODO Auto-generated method stub
		
	}

}
