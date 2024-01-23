package org.integratedmodelling.klab;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.eclipse.xtext.testing.IInjectorProvider;
import org.eclipse.xtext.testing.util.ParseHelper;
import org.integratedmodelling.kactors.api.IKActorsBehavior;
import org.integratedmodelling.kactors.api.IKActorsBehavior.Type;
import org.integratedmodelling.kactors.api.IKActorsValue;
import org.integratedmodelling.kactors.api.IKActorsValue.ExpressionType;
import org.integratedmodelling.kactors.kactors.Model;
import org.integratedmodelling.kactors.model.KActors;
import org.integratedmodelling.kactors.model.KActors.CodeAssistant.BehaviorId;
import org.integratedmodelling.kactors.model.KActors.Notifier;
import org.integratedmodelling.kactors.model.KActorsQuantity;
import org.integratedmodelling.kactors.model.KActorsValue;
import org.integratedmodelling.kim.api.IKimConcept;
import org.integratedmodelling.kim.api.IKimExpression;
import org.integratedmodelling.kim.api.IKimObservable;
import org.integratedmodelling.kim.api.IParameters;
import org.integratedmodelling.kim.api.IServiceCall;
import org.integratedmodelling.klab.api.actors.IBehavior;
import org.integratedmodelling.klab.api.auth.IActorIdentity;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage;
import org.integratedmodelling.klab.api.auth.IActorIdentity.KlabMessage.Semaphore;
import org.integratedmodelling.klab.api.auth.IEngineUserIdentity;
import org.integratedmodelling.klab.api.auth.IIdentity;
import org.integratedmodelling.klab.api.auth.IUserIdentity;
import org.integratedmodelling.klab.api.data.adapters.IKlabData;
import org.integratedmodelling.klab.api.extensions.actors.Action;
import org.integratedmodelling.klab.api.extensions.actors.Call;
import org.integratedmodelling.klab.api.knowledge.IConcept;
import org.integratedmodelling.klab.api.knowledge.ILocalWorkspace;
import org.integratedmodelling.klab.api.knowledge.IObservable;
import org.integratedmodelling.klab.api.knowledge.IProject;
import org.integratedmodelling.klab.api.model.IAnnotation;
import org.integratedmodelling.klab.api.observations.IObservation;
import org.integratedmodelling.klab.api.observations.IObservationGroup;
import org.integratedmodelling.klab.api.provenance.IArtifact;
import org.integratedmodelling.klab.api.runtime.ISession;
import org.integratedmodelling.klab.api.runtime.monitoring.IMonitor;
import org.integratedmodelling.klab.api.services.IActorsService;
import org.integratedmodelling.klab.auth.EngineUser;
import org.integratedmodelling.klab.common.Urns;
import org.integratedmodelling.klab.common.mediation.Currency;
import org.integratedmodelling.klab.common.mediation.Quantity;
import org.integratedmodelling.klab.common.mediation.Unit;
import org.integratedmodelling.klab.components.runtime.actors.KlabActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor;
import org.integratedmodelling.klab.components.runtime.actors.KlabActor.ActorReference;
import org.integratedmodelling.klab.components.runtime.actors.UserActor;
import org.integratedmodelling.klab.components.runtime.actors.ViewBehavior.KlabWidgetActionExecutor;
import org.integratedmodelling.klab.components.runtime.actors.extensions.Artifact;
import org.integratedmodelling.klab.components.runtime.actors.extensions.IValueProxy;
import org.integratedmodelling.klab.components.runtime.artifacts.ObjectArtifact;
import org.integratedmodelling.klab.components.runtime.observations.Observation;
import org.integratedmodelling.klab.data.encoding.VisitingDataBuilder;
import org.integratedmodelling.klab.engine.runtime.Session;
import org.integratedmodelling.klab.engine.runtime.SimpleRuntimeScope;
import org.integratedmodelling.klab.engine.runtime.api.IRuntimeScope;
import org.integratedmodelling.klab.exceptions.KlabActorException;
import org.integratedmodelling.klab.exceptions.KlabException;
import org.integratedmodelling.klab.exceptions.KlabIOException;
import org.integratedmodelling.klab.exceptions.KlabIllegalStateException;
import org.integratedmodelling.klab.exceptions.KlabValidationException;
import org.integratedmodelling.klab.rest.BehaviorReference;
import org.integratedmodelling.klab.rest.Layout;
import org.integratedmodelling.klab.rest.Localization;
import org.integratedmodelling.klab.rest.ViewComponent;
import org.integratedmodelling.klab.rest.ViewPanel;
import org.integratedmodelling.klab.utils.FileCatalog;
import org.integratedmodelling.klab.utils.GitUtils;
import org.integratedmodelling.klab.utils.MiscUtilities;
import org.integratedmodelling.klab.utils.Pair;
import org.integratedmodelling.klab.utils.Path;
import org.integratedmodelling.klab.utils.Range;
import org.integratedmodelling.klab.utils.StringUtil;
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
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import javassist.Modifier;

public enum Actors implements IActorsService {

    INSTANCE;

    public static final String JAVA_EXTENSION_NAMESPACE = "org.integratedmodelling.klab.components.runtime.actors.extensions";

    /**
     * The possible destinations of a view panel in a layout. The lowercased name of these
     * corresponds to the annotations that attribute view roles to actions in applications.
     * 
     * @author Ferd
     */
    public enum PanelLocation {
        Left, Right, Panel, Header, Footer, Window, Modal
    }

    @Inject
    ParseHelper<Model> kActorsParser;

    private ActorSystem<Void> supervisor;
    private Map<String, IBehavior> behaviors = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Map<String, Map<String, String>>> localizations = Collections.synchronizedMap(new HashMap<>());
    private Map<String, BehaviorReference> behaviorDescriptors = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Pair<String, Class<? extends KlabActionExecutor>>> actionClasses = Collections
            .synchronizedMap(new HashMap<>());
    // the annotations for the classes defined through code
    private Map<Class<?>, Action> actionDefinitions = Collections.synchronizedMap(new HashMap<>());
    private Map<String, Pair<String, Class<? extends KlabWidgetActionExecutor>>> viewActionClasses = Collections
            .synchronizedMap(new HashMap<>());
    AtomicLong semaphoreId = new AtomicLong(1L);
    Map<Semaphore.Type, Set<Long>> semaphores = Collections.synchronizedMap(new HashMap<>());
    static Set<String> layoutMetadata = null;
    private Map<String, Library> libraries = Collections.synchronizedMap(new HashMap<>());
    static Set<String> isoLanguages;

    public class CallDescriptor {

        public CallDescriptor(Call cid, Method method) {
            this.method = method;
            this.descriptor = cid;
        }

        public CallDescriptor(Action action) {
            this.action = action;
        }

        public Method method;
        public Call descriptor;
        public Action action;
    }

    public class Library {

        public Library(String name, Class<?> cls) {
            this.name = name;
            this.cls = cls;
        }

        public String name;
        public Class<?> cls;
        public Map<String, CallDescriptor> methods = Collections.synchronizedMap(new HashMap<>());
        Set<IKActorsBehavior.Type> defaulted = new HashSet<>();

    }

    /**
     * Metadata for layout control
     * <p>
     * No argument:
     * <ul>
     * <li>:right, :left, :top, :bottom</li>
     * <li>:hfill, :vfill, :fill</li>
     * <li>:disabled {!disabled for completeness}</li>
     * <li>:hidden {!hidden}</li>
     * <li>:hbox :vbox :pager :shelf :tabs [:table is the default] to specify the type of
     * arrangement in a group</li>
     * </ul>
     * <p>
     * With argument:
     * <ul>
     * <li>:cspan, :rspan (columns and rows spanned in grid)</li>
     * <li>:fg, :bg (color name for now?)</li>
     * <li>:bstyle {?HTML solid dotted}</li>
     * <li>:bwidth <n> border width (always solid for now)</li>
     * <li>:fstyle {bold|italic|strike|normal}</li>
     * <li>:fsize <n></li>
     * <li>:symbol {font awesome char code}</li>
     * <li>:class (CSS class)</li>
     * <li>:wmin, :hmin (minimum height and width)</li>
     * <li>:cols, :equal for panel grids</li>
     * </ul>
     */
    static {
        layoutMetadata = new HashSet<>();
        layoutMetadata.add("right");
        layoutMetadata.add("left");
        layoutMetadata.add("top");
        layoutMetadata.add("bottom");
        layoutMetadata.add("hfill");
        layoutMetadata.add("vfill");
        layoutMetadata.add("fill");
        layoutMetadata.add("icon");
        layoutMetadata.add("iconname");
        layoutMetadata.add("iconsize");
        layoutMetadata.add("toggle");
        layoutMetadata.add("info");
        layoutMetadata.add("disabled");
        layoutMetadata.add("hidden");
        layoutMetadata.add("hbox");
        layoutMetadata.add("vbox");
        layoutMetadata.add("inputgroup");
        layoutMetadata.add("pager");
        layoutMetadata.add("shelf");
        layoutMetadata.add("tabs");
        layoutMetadata.add("cspan");
        layoutMetadata.add("rspan");
        layoutMetadata.add("fg");
        layoutMetadata.add("bg");
        layoutMetadata.add("bwidth");
        layoutMetadata.add("checked");
        layoutMetadata.add("waiting");
        layoutMetadata.add("computing");
        layoutMetadata.add("error");
        layoutMetadata.add("done");
        layoutMetadata.add("bstyle");
        layoutMetadata.add("fstyle");
        layoutMetadata.add("fsize");
        layoutMetadata.add("symbol");
        layoutMetadata.add("class");
        layoutMetadata.add("wmin");
        layoutMetadata.add("wmax");
        layoutMetadata.add("hmin");
        layoutMetadata.add("hmax");
        layoutMetadata.add("height");
        layoutMetadata.add("width");
        layoutMetadata.add("cols");
        layoutMetadata.add("equal");
        layoutMetadata.add("collapse");
        layoutMetadata.add("remove");
        layoutMetadata.add("altfg");
        layoutMetadata.add("altbg");
        layoutMetadata.add("tooltip");
        layoutMetadata.add("ellipsis");
        layoutMetadata.add("multiple");
        layoutMetadata.add("selected");
        layoutMetadata.add("type");
        layoutMetadata.add("active");
        layoutMetadata.add("timeout");
        layoutMetadata.add("opened");
        layoutMetadata.add("blank");

        isoLanguages = new HashSet<>();
        for (String isoLanguage : Locale.getISOLanguages()) {
            isoLanguages.add(isoLanguage);
        }
    }

    @Override
    public IBehavior getBehavior(String behaviorId) {
        IBehavior ret = behaviors.get(behaviorId);
        if (ret == null && behaviorId.contains(".")) {
            String lang = Path.getLast(behaviorId, '.');
            if (isoLanguages.contains(lang)) {
                Map<String, Map<String, String>> localization = this.localizations.get(Path.getLeading(behaviorId, '.'));
                if (localization != null && localization.containsKey(lang)) {
                    IKActorsBehavior source = KActors.INSTANCE.newBehavior(Path.getLeading(behaviorId, '.'));
                    ret = new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(source, lang,
                            localization.get(lang));
                    behaviors.put(behaviorId, ret);
                }
            }
        }
        return ret;
    }

    @Override
    public IBehavior newBehavior(String behaviorId) {
        IBehavior prototype = getBehavior(behaviorId);
        if (prototype != null) {
            IKActorsBehavior source = KActors.INSTANCE.newBehavior(prototype.getStatement().getName());
            if (prototype.getLocale() == null) {
                return new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(source);
            } else {
                return new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(source, prototype.getLocale(),
                        prototype.getLocalization());
            }
        }
        return null;
    }

    /**
     * The actor system entry point at /user and available as getSupervisor(). It will be the
     * (direct for now) father of all session actors. We create this to have a visible top-level
     * supervisor reference at the first call to Actors, which tells us that actors will have a role
     * in the engine.
     * 
     * @author Ferd
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
                    // for now just stop
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

    Map<String, Set<BehaviorReference>> actionCatalog = null;

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
            String definition = IOUtils.toString(file, StandardCharsets.UTF_8);
            Model model = kActorsParser.parse(definition);
            ret = KActors.INSTANCE.declare(model);
        } catch (Exception e) {
            throw new KlabValidationException(e);
        }
        return ret;
    }

    /**
     * Install listeners to build behaviors on read and organize them by project.
     */
    public void setup() {
        KActors.INSTANCE.addNotifier(new Notifier(){
            @Override
            public void notify(IKActorsBehavior behavior) {
                behaviors.put(behavior.getName(),
                        new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(behavior));
                File file = behavior.getFile();
                if (file != null) {
                    File localizationFile = MiscUtilities.changeExtension(file, "localization");
                    if (localizationFile.exists()) {
                        localizations.put(behavior.getName(), new FileCatalog<>(localizationFile, Map.class, Map.class));
                    }
                }
            }
        });
    }

    /**
     * The supervisor actor.
     * 
     * @return
     */
    public ActorSystem<Void> getSupervisor() {
        return this.supervisor;
    }

    public BehaviorId classifyBehavior(String name) {
        switch(name) {
        case "view":
            return BehaviorId.VIEW;
        case "user":
            return BehaviorId.USER;
        case "object":
            return BehaviorId.OBJECT;
        case "state":
            return BehaviorId.STATE;
        case "session":
            return BehaviorId.SESSION;
        case "test":
            return BehaviorId.TEST;
        case "explorer":
            return BehaviorId.EXPLORER;
        }
        return BehaviorId.IMPORTED;
    }

    /**
     * Create a direct child of the supervisor with the specified identity. Implementations should
     * only call this for top-level actors and have the others created through messages to them. The
     * top-level identities for now are user actors.
     * 
     * @param <T>
     * @param create
     * @param identity
     * @return
     */
    public <T> ActorRef<T> createActor(Behavior<T> create, IIdentity identity) {
        return ActorSystem.create(
                    Behaviors.supervise(create)
                        .onFailure(SupervisorStrategy.resume().withLoggingEnabled(true)),
                        identity instanceof IUserIdentity ? sanitize(((IUserIdentity) identity).getUsername()) : identity.getId());
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
                this.actionClasses.put(message.id(), new Pair<>(annotation.id(), (Class<? extends KlabActionExecutor>) cl));
                this.actionDefinitions.put(cl, message);
                if (KlabActionExecutor.class.isAssignableFrom(cl)) {
                    this.viewActionClasses.put(message.id(),
                            new Pair<>(annotation.id(), (Class<? extends KlabWidgetActionExecutor>) cl));
                }
            }
        }
    }

    public void exportBehaviors(File file) {

        /*
         * Fill this in so we can read actor files with the actual knowledge of the installed
         * behaviors. This is called at the right time, after loading and before reading behaviors.
         */
        KActors.INSTANCE.getBehaviorManifest().putAll(behaviorDescriptors);

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
    public KlabActionExecutor getSystemAction(String id, IActorIdentity<KlabMessage> identity, IParameters<String> arguments,
            IKActorsBehavior.Scope scope, ActorRef<KlabMessage> sender, String callId) {

        Pair<String, Class<? extends KlabActionExecutor>> cls = actionClasses.get(id);
        if (cls != null) {
            try {
                Constructor<? extends KlabActionExecutor> constructor = cls.getSecond().getConstructor(IActorIdentity.class,
                        IParameters.class, IKActorsBehavior.Scope.class, ActorRef.class, String.class);
                KlabActionExecutor ret = constructor.newInstance(identity, arguments, scope, sender, callId);
                ret.notifyDefinition(this.actionDefinitions.get(cls.getSecond()));
                return ret;
            } catch (Throwable e) {
                scope.getMonitor().error("Error while creating action " + id + ": " + e.getMessage());
            }
        }
        return null;
    }

    public Class<? extends KlabActionExecutor> getActionClass(String id) {
        Pair<String, Class<? extends KlabActionExecutor>> ret = actionClasses.get(id);
        return ret == null ? null : ret.getSecond();
    }

    public Class<? extends KlabWidgetActionExecutor> getViewActionClass(String id) {
        Pair<String, Class<? extends KlabWidgetActionExecutor>> ret = viewActionClasses.get(id);
        return ret == null ? null : ret.getSecond();
    }

    @Override
    public Collection<String> getBehaviorIds() {
        return behaviors.keySet();
    }

    @Override
    public Collection<String> getBehaviorIds(IKActorsBehavior.Type type) {
        List<String> ret = new ArrayList<>();
        for (String key : behaviors.keySet()) {
            if (behaviors.get(key).getDestination() == type) {
                ret.add(key);
            }
        }
        return ret;
    }

    @Override
    public Collection<String> getPublicApps() {
        Set<String> ret = new LinkedHashSet<>();
        for (String key : behaviors.keySet()) {
            if (behaviors.get(key).getDestination() == Type.APP && behaviors.get(key).getStatement().isPublic()) {
                // getId() and set semantics ensure that localized instances only appear once with
                // their original name
                ret.add(behaviors.get(key).getId());
            }
        }
        return ret;
    }

    public void instrument(List<IAnnotation> annotations, Observation observation) {
        instrument(annotations, observation, observation.getScope());
    }

    public void instrument(List<IAnnotation> annotations, Observation observation, IRuntimeScope scope) {

        /*
         * find any bindings made at runtime
         */
        Pair<String, IKimExpression> rb = observation.getScope().getBehaviorBindings().get(observation.getObservable().getType());
        if (rb != null) {
            IBehavior b = getBehavior(rb.getFirst());
            if (b != null) {
                if (rb.getSecond() != null) {
                    // TODO filter
                }
                observation.getScope().scheduleActions(observation, b);
                ((Observation) observation).load(b, scope);
            }
        }

        for (IAnnotation annotation : annotations) {
            if (annotation.getName().equals("bind")) {
                String behavior = annotation.containsKey("behavior")
                        ? annotation.get("behavior", String.class)
                        : annotation.get(IServiceCall.DEFAULT_PARAMETER_NAME, String.class);
                if (behavior != null) {
                    IBehavior b = getBehavior(behavior);
                    if (b != null) {

                        if (annotation.contains("filter")) {
                            // TODO build/cache and run filter, skip if false
                        }

                        /*
                         * observation will load behavior, schedule all the temporal actions it
                         * contains before main is run
                         */
                        observation.getScope().scheduleActions(observation, b);

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
     * Find the first unnamed parameter that matches the passed class, resolving actor values into
     * their correspondent value.
     * 
     * @param <T>
     * @param arguments
     * @param class1
     * @return
     */
    @SuppressWarnings("unchecked")
    public <T> T getArgument(IParameters<String> arguments, IKActorsBehavior.Scope scope, IIdentity identity, Class<T> cls) {
        for (String key : arguments.getUnnamedKeys()) {
            Object ret = arguments.get(key);
            if (ret instanceof KActorsValue) {
                ret = ((KActorsValue) ret).evaluate(scope, identity, false);
            }
            if (ret != null && cls.isAssignableFrom(ret.getClass())) {
                return (T) ret;
            }
        }
        return null;
    }

    public List<ViewPanel> getPanels(Layout view) {
        List<ViewPanel> panels = new ArrayList<>();
        panels.addAll(view.getLeftPanels());
        panels.addAll(view.getRightPanels());
        panels.addAll(view.getPanels());
        if (view.getHeader() != null) {
            panels.add(view.getHeader());
        }
        if (view.getFooter() != null) {
            panels.add(view.getFooter());
        }
        return panels;
    }

    public String dumpView(Layout view) {
        StringBuffer ret = new StringBuffer(2048);
        dumpView(view, ret, 0);
        return ret.toString();
    }

    public void dumpView(Layout view, StringBuffer ret, int offset) {

        String spacer = StringUtil.spaces(offset);

        ret.append(spacer + "View " + view.getName() + "\n");

        if (view.getHeader() != null) {
            dumpPanel(view.getHeader(), "Header", ret, offset + 3);
        }

        for (ViewPanel panel : view.getLeftPanels()) {
            dumpPanel(panel, "Left", ret, offset + 3);
        }

        for (ViewPanel panel : view.getPanels()) {
            dumpPanel(panel, "Center", ret, offset + 3);
        }

        for (ViewPanel panel : view.getRightPanels()) {
            dumpPanel(panel, "Right", ret, offset + 3);
        }

        if (view.getFooter() != null) {
            dumpPanel(view.getFooter(), "Footer", ret, offset + 3);
        }

    }

    private void dumpPanel(ViewPanel panel, String title, StringBuffer ret, int offset) {

        String spacer = StringUtil.spaces(offset);

        ret.append(spacer + title + ": " + panel.getName() + " [" + panel.getId() + "]\n");

        for (ViewComponent component : panel.getComponents()) {
            dumpComponent(component, ret, offset + 3);
        }

    }

    private void dumpComponent(ViewComponent component, StringBuffer ret, int offset) {
        if (component instanceof Layout) {
            dumpView((Layout) component, ret, offset);
        }
        if (component instanceof ViewPanel) {
            dumpPanel((ViewPanel) component, "Panel", ret, offset);
        } else {
            String spacer = StringUtil.spaces(offset);
            String name = component.getName() == null ? component.getTitle() : component.getName();
            ret.append(spacer + component.getType() + " " + (name == null ? "<unnamed>" : name) + " [" + component.getId() + "]"
                    + dumpAttributes(component) + (component.getActorPath() == null ? "" : (" -> " + component.getActorPath()))
                    + "\n");
            for (ViewComponent c : component.getComponents()) {
                dumpComponent(c, ret, offset + 3);
            }
        }
    }

    private String dumpAttributes(ViewComponent component) {
        String ret = "";
        if (!component.getAttributes().isEmpty()) {
            String attrs = "";
            for (String key : component.getAttributes().keySet()) {
                attrs += (attrs.isEmpty() ? "" : ", ") + key + ": " + component.getAttributes().get(key);
            }
            ret = " {" + attrs + "}";
        }
        return ret;
    }

    /**
     * Load all the behaviors in <klab>/user. These should be managed through the IDE and there
     * should always be a default one.
     */
    public void loadUserBehaviors() {
        File bspace = Configuration.INSTANCE.getDataPath("user");
        for (File bfile : bspace.listFiles()) {
            if (bfile.toString().endsWith(".kactor")) {
                KActors.INSTANCE.add(bfile);
            }
        }
    }

    public ViewPanel findPanel(Layout view, String id) {
        ViewPanel ret = null;
        for (Collection<?> panels : new Collection[]{view.getPanels(), view.getLeftPanels(), view.getRightPanels(),
                Collections.singleton(view.getFooter()), Collections.singleton(view.getHeader())}) {
            for (Object panel : panels) {
                ret = findPanel((ViewPanel) panel, id);
                if (ret != null) {
                    return ret;
                }
            }
        }
        return null;
    }

    private ViewPanel findPanel(ViewComponent panel, String id) {
        if (panel instanceof ViewPanel && id.equals(panel.getId())) {
            return (ViewPanel) panel;
        }
        for (ViewComponent component : panel.getComponents()) {
            ViewPanel ret = findPanel(component, id);
            if (ret != null) {
                return ret;
            }
        }
        return null;
    }

    public Set<String> getLayoutMetadata() {
        return layoutMetadata;
    }

    public Map<String, Set<String>> getKnownVerbs() {

        Map<String, Set<String>> ret = new HashMap<>();

        for (String key : actionClasses.keySet()) {
            String agent = actionClasses.get(key).getFirst();
            if (agent != null) {
                if (!ret.containsKey(agent)) {
                    ret.put(agent, new HashSet<>());
                }
                ret.get(agent).add(key);
            }
        }

        return ret;
    }

    class SemaphoreImpl implements Semaphore {

        long id;
        Type type;
        boolean warned;

        SemaphoreImpl(Type type, long id) {
            this.type = type;
            this.id = id;
        }

        @Override
        public Type getType() {
            return type;
        }

        public long getId() {
            return id;
        }

        @Override
        public String toString() {
            return type + "-" + id;
        }

        public void setWarned() {
            this.warned = true;
        }

        @Override
        public boolean isWarned() {
            return warned;
        }

    }

    /**
     * Create and keep a semaphore for different actors to share and check. Used to implement serial
     * behavior when needed to serialize chains of fired messages or load operations.
     * 
     * @param type
     * @return
     */
    public Semaphore createSemaphore(final Semaphore.Type type) {

        Set<Long> ids = this.semaphores.get(type);
        if (ids == null) {
            ids = Collections.synchronizedSet(new HashSet<>());
            this.semaphores.put(type, ids);
        }

        final long id = semaphoreId.incrementAndGet();
        ids.add(id);
        return new SemaphoreImpl(type, id);
    }

    /**
     * Make the passed semaphore expire. TODO eventually this will need to work across network
     * boundaries.
     * 
     * @param semaphor
     */
    public void expire(Semaphore semaphore) {
        Set<Long> ids = this.semaphores.get(semaphore.getType());
        if (ids != null) {
            ids.remove(((SemaphoreImpl) semaphore).getId());
        }
    }

    /**
     * Check if the passed semaphore has expired.
     * 
     * @param semaphor
     * @return
     */
    public boolean expired(Semaphore semaphore) {
        Set<Long> ids = this.semaphores.get(semaphore.getType());
        return ids == null || !ids.contains(((SemaphoreImpl) semaphore).getId());
    }

    /**
     * Build a Java object through reflection when invoked by a k.Actors constructor
     * 
     * @param constructor
     * @param scope
     * @param identity
     * @return
     */
    public Object createJavaObject(KActorsValue.Constructor constructor, IKActorsBehavior.Scope scope, IActorIdentity<?> identity) {

        Class<?> cls = null;
        String className = constructor.getClassname();
        Object ret = null;

        if (constructor.getClasspath() == null) {
            className = JAVA_EXTENSION_NAMESPACE + "." + className;
        } else {
            className = constructor.getClasspath() + "." + className;
            throw new KlabIllegalStateException(
                    "k.Actors: creation of Java object with explicit classpath requires a security exception, unimplemented so far.");
        }

        try {

            cls = Class.forName(className);
            if (cls != null) {
                /*
                 * arguments without a key are the constructor argument; keyed arguments will be
                 * handled by looking up setXxxx(arg) through reflection.
                 */
                List<Object> arguments = new ArrayList<>();
                Map<String, Object> settings = new HashMap<>();

                for (Object arg : constructor.getArguments().getUnnamedArguments()) {
                    if (arg instanceof KActorsValue) {
                        arguments.add(((KActorsValue) arg).evaluate(scope, identity, true));
                    } else {
                        arguments.add(arg);
                    }
                }

                for (String key : constructor.getArguments().keySet()) {
                    if (constructor.getArguments().getUnnamedKeys().contains(key)) {
                        continue;
                    }
                    Object arg = constructor.getArguments().get(key);
                    settings.put(key, arg instanceof KActorsValue ? ((KActorsValue) arg).evaluate(scope, identity, true) : arg);
                }

                Constructor<?> constr = null;

                if (arguments.size() == 0) {
                    constr = cls.getConstructor();
                } else {
                    Class<?>[] cclasses = new Class<?>[arguments.size()];
                    int i = 0;
                    for (Object o : arguments) {
                        cclasses[i++] = o == null ? Object.class : o.getClass();
                    }
                    constr = cls.getConstructor(cclasses);
                }

                if (constr == null) {
                    throw new KlabValidationException(
                            "k.Actors: cannot find a constructor for the arguments specified for " + className);
                }

                ret = constr.newInstance(arguments.toArray());

                // shouldn't happen w/o exception
                if (ret != null) {
                    for (String setting : settings.keySet()) {
                        String methodName = setting.startsWith("set") ? setting : ("set" + StringUtil.capitalize(setting));
                        Object argument = settings.get(setting);
                        Method method = null;
                        try {
                            method = cls.getMethod(methodName, argument == null ? Object.class : argument.getClass());
                        } catch (NoSuchMethodException e) {
                            // ok, we dont'have it.
                        }
                        if (method == null) {
                            methodName = "setProperty";
                            try {
                                method = cls.getMethod(methodName, String.class, Object.class);
                            } catch (NoSuchMethodException e) {
                                // not this one, either.
                            }
                        }

                        if (method != null) {
                            if ("setProperty".equals(methodName)) {
                                method.invoke(ret, setting, argument);
                            } else {
                                method.invoke(ret, argument);
                            }
                        } else {
                            if (scope != null) {
                                scope.getMonitor().warn(
                                        "k.Actors: cannot find a " + methodName + " method to invoke on constructed object");
                            } else {
                                Logging.INSTANCE.warn(
                                        "k.Actors: cannot find a " + methodName + " method to invoke on constructed object");
                            }
                        }
                    }

                    // check for void, no-args initialization method to call after all properties
                    // are set
                    try {
                        Method method = cls.getMethod("initialize");
                        if (method != null) {
                            method.invoke(ret);
                        }
                    } catch (NoSuchMethodException e) {
                        // not this one, either.
                    }

                }

            }

        } catch (Throwable e) {
            if (scope != null) {
                scope.getMonitor().error("error creating k.Actors object of class " + className + ": " + e.getMessage());
            } else {
                Logging.INSTANCE.error("error creating k.Actors object of class " + className + ": " + e.getMessage());
            }
        }

        /**
         * If proxy, unproxy.
         */
        if (ret instanceof IValueProxy) {
            ret = ((IValueProxy) ret).getValue();
        }

        return ret;
    }

    @SuppressWarnings("unchecked")
    public Iterable<Object> getIterable(IKActorsValue iterable, IKActorsBehavior.Scope scope, IActorIdentity<?> identity) {
        switch(iterable.getType()) {
        case ANYTHING:
            break;
        case ANYTRUE:
            break;
        case ANYVALUE:
            break;
        case EMPTY:
            break;
        case CONSTANT:
        case DATE:
        case CLASS:
        case BOOLEAN:
        case ERROR:
        case EXPRESSION:
        case NUMBER:
        case OBSERVABLE:
        case OBJECT:
        case IDENTIFIER:
        case LIST:
        case SET:
            Object o = ((KActorsValue) iterable).evaluate(scope, identity, false);
            if (o instanceof Iterable) {
                return (Iterable<Object>) o;
            } else if (o instanceof String && Urns.INSTANCE.isUrn(o.toString())) {
                return iterateResource(o.toString(), scope.getMonitor());
            }
            return Collections.singletonList(o);
        case STRING:
            if (Urns.INSTANCE.isUrn(iterable.getStatedValue().toString())) {
                return iterateResource(iterable.getStatedValue().toString(), scope.getMonitor());
            }
            return Collections.singletonList(((KActorsValue) iterable).evaluate(scope, identity, false));
        case MAP:
            break;
        case NODATA:
            return Collections.singletonList(null);
        case NUMBERED_PATTERN:
            break;
        case OBSERVATION:
            return (Iterable<Object>) ((KActorsValue) iterable).evaluate(scope, identity, false);
        case RANGE:
            // TODO iterate the range
            break;
        case TABLE:
            break;
        case TREE:
            break;
        case TYPE:
            break;
        case URN:
            return iterateResource(iterable.getStatedValue().toString(), scope.getMonitor());
        default:
            break;
        }
        return new ArrayList<>();
    }

    public Iterable<Object> iterateResource(String urn, IMonitor monitor) {

        VisitingDataBuilder builder = new VisitingDataBuilder(1);
        IKlabData data = Resources.INSTANCE.getResourceData(urn, builder, monitor);
        return data.getObjectCount() == 0 ? new ArrayList<>() : new Iterable<Object>(){

            @Override
            public Iterator<Object> iterator() {

                return new Iterator<Object>(){

                    int n = 0;

                    @Override
                    public boolean hasNext() {
                        return n < data.getObjectCount();
                    }

                    @Override
                    public Object next() {
                        // wrap into an Artifact wrapper for reference inside k.Actors
                        Object ret = new Artifact(
                                new ObjectArtifact(data.getObjectName(n), data.getObjectScale(n), data.getObjectMetadata(n)));
                        n++;
                        return ret;
                    }
                };
            }

        };
    }

    /**
     * Invoke a method based on parameters from a call to a Java reactor inside the k.Actors code.
     * 
     * @param reactor
     * @param arguments
     * @param scope
     */
    public Object invokeReactorMethod(Object reactor, String methodName, IParameters<String> arguments, IKActorsBehavior.Scope scope,
            IActorIdentity<?> identity) {

        Object ret = null;
        List<Object> jargs = new ArrayList<>();
        Map<String, Object> kargs = null;
        for (Object v : arguments.getUnnamedArguments()) {
            jargs.add(v instanceof KActorsValue ? ((KActorsValue) v).evaluate(scope, identity, false) : v);
        }
        for (String k : arguments.getNamedKeys()) {
            if (kargs == null) {
                kargs = new HashMap<>();
            }
            Object v = arguments.get(k);
            kargs.put(k, v instanceof KActorsValue ? ((KActorsValue) v).evaluate(scope, identity, false) : v);
        }
        if (kargs != null) {
            jargs.add(kargs);
        }

        Class<?>[] clss = new Class[jargs.size()];

        int i = 0;
        for (Object jarg : jargs) {
            clss[i++] = jarg == null ? Object.class : jarg.getClass();
        }

        Method method = null;
        try {
            method = MethodUtils.getMatchingAccessibleMethod(reactor.getClass(), methodName, clss);
        } catch (Throwable t) {
            Logging.INSTANCE.error("invokeReactorMethod threw exception: " + t.getMessage());
            // leave method = null
        }

        if (method != null) {
            try {
                ret = method.invoke(reactor, jargs.toArray());
            } catch (Throwable e) {
                if (scope != null) {
                    scope.getMonitor().error(e);
                } else {
                    Logging.INSTANCE.error(e);
                }
            }
        } else {

            /*
             * check for no-arg "get" or single arg "set" method.
             */
            if (jargs.size() == 0) {
                try {
                    // getter
                    method = reactor.getClass().getDeclaredMethod("get" + StringUtil.capitalize(methodName));
                    if (method != null) {
                        ret = method.invoke(reactor, jargs.toArray());
                    }
                } catch (Throwable e) {
                    // move on
                }

            } else if (jargs.size() == 1) {
                try {
                    // setter
                    method = new PropertyDescriptor(methodName, reactor.getClass()).getWriteMethod();
                    if (method != null) {
                        ret = method.invoke(reactor, jargs.toArray());
                    }
                } catch (Throwable e) {
                    // move on
                }
            }

            if (method == null && kargs == null) {
                /*
                 * see if we have a method with the same args + a map of options and pass an empty
                 * option map if so.
                 */
                clss = new Class[jargs.size() + 1];

                i = 0;
                for (Object jarg : jargs) {
                    clss[i++] = jarg == null ? Object.class : jarg.getClass();
                }
                clss[i] = Map.class;

                try {
                    method = MethodUtils.getMatchingAccessibleMethod(reactor.getClass(), methodName, clss);
                    if (method != null) {
                        jargs.add(new HashMap<Object, Object>());
                        ret = method.invoke(reactor, jargs.toArray());
                    }

                } catch (Throwable t) {
                    Logging.INSTANCE.error("invokeReactorMethod threw exception: " + t.getMessage());
                    // leave method = null
                }

            }

            if (method == null) {

                /*
                 * last chance: lookup a method taking Object[] and if found, pass whatever we have
                 */
                try {
                    method = reactor.getClass().getDeclaredMethod(methodName, Object[].class);
                    if (method != null) {
                        ret = method.invoke(reactor, (Object) jargs.toArray());
                    }
                } catch (Throwable e) {
                    if (scope != null) {
                        scope.getMonitor().error(e);
                    } else {
                        Logging.INSTANCE.error(e);
                    }
                }
            }

            if (ret != null || method != null) {
                return ret;
            }

            if (scope != null) {
                scope.getMonitor().warn("k.Actors: cannot find a '" + methodName + "' method to invoke on object of class "
                        + reactor.getClass().getCanonicalName());
            } else {
                Logging.INSTANCE.warn("k.Actors: cannot find a '" + methodName + "' method to invoke on object of class "
                        + reactor.getClass().getCanonicalName());
            }
        }

        return ret;
    }

    /**
     * Return the boolean value of the passed object. In k.Actors, anything that isn't a null,
     * false, empty string or zero is true.
     * 
     * @param ret
     * @return
     */
    public boolean asBooleanValue(Object ret) {
        if (ret == null || (ret instanceof String && ((String) ret).trim().isEmpty())
                || (ret instanceof Boolean && !((Boolean) ret) || (ret instanceof Number && ((Number) ret).longValue() == 0))) {
            return false;
        }
        return true;
    }

    @Override
    public IActorIdentity.Reference createUserActor(IEngineUserIdentity user) {
//    	return null;
        return new ActorReference(createActor(UserActor.create((EngineUser) user), user));
    }

    /**
     * Run a script from a file, URL or local resource. Blocks until the script has finished and
     * returns the exit code.
     * 
     * @param argument
     * @param session
     * @return an exit code, normally 0 if all OK, 1 if any error, or number of failed tests if the
     *         input was a test case.
     */
    public int run(String argument, ISession session) {

        String app = null;
        if (behaviors.containsKey(argument)) {
            IBehavior behavior = behaviors.get(argument);
            Logging.INSTANCE
                    .info("Running " + (behavior.getStatement().getType() == Type.SCRIPT ? "k.Actors script " : "unit test ")
                            + behavior.getName() + " [ID=" + behavior.getName() + "]");
            app = session.load(behavior, new SimpleRuntimeScope(session));
        } else {
            File file = Configuration.INSTANCE.findFile(argument);
            if (file != null) {
                IKActorsBehavior behavior = declare(file);
                if (!(behavior.getType() == Type.SCRIPT || behavior.getType() == Type.UNITTEST)) {
                    Logging.INSTANCE.error("cannot run " + behavior.getName() + ": not a script or a unit test");
                }
                org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior b = new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(
                        behavior);
                behaviors.put(behavior.getName(), b);
                Logging.INSTANCE.info("Running " + (behavior.getType() == Type.SCRIPT ? "k.Actors script " : "unit test ")
                        + behavior.getName() + " [ID=" + behavior.getName() + "]");
                app = session.load(b, new SimpleRuntimeScope(session));
            } else {
                URL resource = this.getClass().getClassLoader().getResource(argument);
                if (resource != null) {
                    try (InputStream input = resource.openStream()) {
                        IKActorsBehavior behavior = declare(input);
                        if (!(behavior.getType() == Type.SCRIPT || behavior.getType() == Type.UNITTEST)) {
                            Logging.INSTANCE.error("cannot run " + behavior.getName() + ": not a script or a unit test");
                        }
                        org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior b = new org.integratedmodelling.klab.components.runtime.actors.behavior.Behavior(
                                behavior);
                        behaviors.put(behavior.getName(), b);
                        Logging.INSTANCE.info("Running " + (behavior.getType() == Type.SCRIPT ? "k.Actors script " : "unit test ")
                                + behavior.getName() + " [ID=" + behavior.getName() + "]");
                        app = session.load(b, new SimpleRuntimeScope(session));
                    } catch (Throwable t) {
                        throw new KlabActorException(t);
                    }
                } else {
                    Logging.INSTANCE.error("cannot run " + argument + ": resource not found");
                }
            }
        }

        if (app != null) {
            while(((Session) session).isRunning(app)) {
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    Logging.INSTANCE.error(e);
                }
            }
        }

        return ((Session) session).getScriptReturnValue(app);

    }

    public void registerLibrary(org.integratedmodelling.klab.api.extensions.actors.Library annotation, Class<?> cls) {

        /**
         * Parse methods, create indices, set defaults
         */
        Library library = new Library(annotation.name(), cls);
        for (IKActorsBehavior.Type def : annotation.defaultFor()) {
            library.defaulted.add(def);
        }
        for (Method method : cls.getDeclaredMethods()) {
            if (Modifier.isPublic(method.getModifiers()) && method.isAnnotationPresent(Call.class)) {
                Call cid = method.getAnnotation(Call.class);
                String name = cid.name().isEmpty() ? method.getName() : cid.name();
                library.methods.put(name, new CallDescriptor(cid, method));
            }
        }

        libraries.put(library.name, library);
    }

    /**
     * Return the complete library instrumentation for the passed actor definition.
     * 
     * @param statement
     * @return
     */
    public Map<String, Library> getLibraries(IKActorsBehavior statement, IMonitor monitor) {
        Map<String, Library> ret = new HashMap<>();
        for (Library library : libraries.values()) {
            if (library.defaulted.contains(statement.getType())) {
                ret.put(library.name, library);
            }
        }

        for (String imported : statement.getImports()) {
            Library lib = libraries.get(imported);
            if (lib != null) {
                ret.put(lib.name, lib);
            } else {
                monitor.error("imported library " + imported + " does not exist");
            }
        }
        return ret;
    }

    public boolean matches(IKActorsValue kvalue, Object value, IKActorsBehavior.Scope scope) {
        switch(kvalue.getType()) {
        case ANNOTATION:
            for (IAnnotation annotation : Annotations.INSTANCE.collectAnnotations(value)) {
                if (annotation.getName().equals(kvalue.getStatedValue())) {
                    scope.getSymbolTable().put(annotation.getName(), annotation);
                    return true;
                }
            }
            break;
        case ANYTHING:
            return true;
        case ANYVALUE:
            return value != null && !(value instanceof Throwable);
        case ANYTRUE:
            boolean ret = value != null && !(value instanceof Throwable) && !(value instanceof Boolean && !((Boolean) value));
            // if (ret) {
            // scope.symbolTable.put("$", value);
            // if (value instanceof Collection) {
            // int n = 1;
            // for (Object v : ((Collection<?>)value)) {
            // scope.symbolTable.put("$" + (n++), v);
            // }
            // }
            // }
            return ret;
        case BOOLEAN:
            return value instanceof Boolean && value.equals(kvalue.getStatedValue());
        case CLASS:
            break;
        case DATE:
            break;
        case EXPRESSION:
            System.out.println("ACH AN EXPRESSION");
            break;
        case IDENTIFIER:
            if (scope.getSymbolTable().containsKey(kvalue.getStatedValue())) {
                return kvalue.getStatedValue().equals(scope.getSymbolTable().get(value));
            }
            if (!notMatch(value)) {
                // NO - if defined in scope, match to its value, else just return true.
                // scope.symbolTable.put(kvalue.getValue().toString(), value);
                return true;
            }
            break;
        case SET:
            // TODO OR match for values in list
            break;
        case LIST:
            // TODO multi-identifier match
            break;
        case MAP:
            break;
        case NODATA:
            return value == null || value instanceof Number && Double.isNaN(((Number) value).doubleValue());
        case NUMBER:
            return value instanceof Number && value.equals(kvalue.getStatedValue());
        case NUMBERED_PATTERN:
            break;
        case OBSERVABLE:
            Object obj = kvalue.evaluate(scope, scope.getIdentity(), true);
            if (obj instanceof IObservable) {
                if (value instanceof IObservation) {
                    return ((IObservation) value).getObservable().resolves((IObservable) obj, null);
                }
            }
            break;
        case QUANTITY:
            break;
        case RANGE:
            return value instanceof Number && ((Range) (kvalue.getStatedValue())).contains(((Number) value).doubleValue());
        case REGEXP:
            break;
        case STRING:
            return value instanceof String && value.equals(kvalue.getStatedValue());
        case TABLE:
            break;
        case TYPE:
            return value != null && (kvalue.getStatedValue().equals(value.getClass().getCanonicalName())
                    || kvalue.getStatedValue().equals(Path.getLast(value.getClass().getCanonicalName(), '.')));
        case URN:
            break;
        case ERROR:
            // match any error? any literal for that?
            return value instanceof Throwable;
        case OBSERVATION:
            // might
            break;
        case TREE:
            break;
        case CONSTANT:
            return (value instanceof Enum && ((Enum<?>) value).name().toUpperCase().equals(kvalue.getStatedValue()))
                    || (value instanceof String && ((String) value).equals(kvalue.getStatedValue()));
        case EMPTY:
            return value == null || (value instanceof Collection && ((Collection<?>) value).isEmpty())
                    || (value instanceof String && ((String) value).isEmpty())
                    || (value instanceof IConcept && ((IConcept) value).is(IKimConcept.Type.NOTHING))
                    || (value instanceof IObservable && ((IObservable) value).is(IKimConcept.Type.NOTHING))
                    || (value instanceof IArtifact && !(value instanceof IObservationGroup) && ((IArtifact) value).isEmpty())
                    || (value instanceof IObservation && ((Observation) value).getObservable().is(IKimConcept.Type.NOTHING));
        case OBJECT:
            break;
        default:
            break;
        }
        return false;
    }

    private boolean notMatch(Object value) {
        return value == null || value instanceof Throwable || (value instanceof Boolean && !((Boolean) value));
    }

    @Override
    public Object evaluate(IKActorsValue container, IIdentity identity, IKActorsBehavior.Scope scope) {

        Object value = container.getStatedValue();

        // handle all literals
        if (container.getExpressionType() == ExpressionType.VALUE) {
            switch(container.getType()) {
            case OBJECT:
                if (scope == null) {
                    return createJavaObject(((KActorsValue) container).getConstructor(), null, null);
                }
                break;
            case OBSERVABLE:
                return value instanceof IKimObservable
                        ? Observables.INSTANCE.declare((IKimObservable) value, scope.getMonitor())
                        : Observables.INSTANCE.declare(value.toString());
            case ANYTHING:
            case ANYVALUE:
            case BOOLEAN:
            case CLASS:
            case DATE:
            case ANNOTATION:
            case CONSTANT:
            case EMPTY:
            case NODATA:
            case NUMBER:
            case RANGE:
            case STRING:
            case TYPE:
            case REGEXP:
                return value;
            case ANYTRUE:
                // only used in matching
                return true;
            case QUANTITY:
                if (value instanceof KActorsQuantity) {
                    if (((KActorsQuantity) value).getCurrency() != null) {
                        return Quantity.create(((KActorsQuantity) value).getValue(),
                                Currency.create(((KActorsQuantity) value).getCurrency()));
                    } else if (((KActorsQuantity) value).getUnit() != null) {
                        return Quantity.create(((KActorsQuantity) value).getValue(),
                                Unit.create(((KActorsQuantity) value).getUnit()));
                    }
                }
            default:
                break;
            }
        }

        /*
         * if we get here, it wasn't a literal; loop until all deferred computations are over
         */
        if (scope != null) {
            value = container;
            while(value instanceof KActorsValue) {
                value = KlabActor.evaluateInScope((KActorsValue) value, scope, (IActorIdentity<?>) identity);
            }
        }

        if (value == null && container.getType() == KActorsValue.Type.IDENTIFIER) {
            value = container.getStatedValue();
        }

        return value;
    }

    public int runAllTests(String[] testCaseProjectsOrGitUrls, ISession session, File outputFile) {

        int ret = 0;
        if (outputFile != null) {
            //
        }
        for (String projectUrl : testCaseProjectsOrGitUrls) {

            IProject project = null;
            // turn string into project, if existing call runAllTests on it and sum up the return
            // value.
            if (GitUtils.isRemoteGitURL(projectUrl)) {
                ILocalWorkspace tempWs = Resources.INSTANCE.getServiceWorkspace();
                String projectName = GitUtils.clone(projectUrl, tempWs.getRoot(), true);
                project = tempWs.loadProject(projectName, Klab.INSTANCE.getRootMonitor());
            } else {
                project = Resources.INSTANCE.getProject(projectUrl);
            }

            if (project != null) {
                ret += runAllTests(project);
            }

        }

        return ret;
    }

    @Override
    public List<Localization> getLocalizations(String behavior) {

        List<Localization> ret = new ArrayList<>();
        IKActorsBehavior source = KActors.INSTANCE.getBehavior(behavior);
        Localization english = null;
        if (source != null) {
            File loc = MiscUtilities.changeExtension(source.getFile(), "localization");
            if (loc.exists()) {
                FileCatalog<Map> cat = FileCatalog.create(loc, Map.class, Map.class);
                for (String lang : cat.keySet()) {
                    Localization localization = new Localization();
                    localization.setIsoCode(lang);
                    Locale locale = Locale.forLanguageTag(lang);
                    localization.setLanguageDescription(locale == null ? null : locale.getDisplayLanguage(locale));
                    if (source.getDescription() != null && source.getDescription().startsWith("#")
                            && cat.get(lang).containsKey(source.getDescription().substring(1))) {
                        localization.setLocalizedDescription(cat.get(lang).get(source.getDescription().substring(1)).toString());
                    } else {
                        localization.setLocalizedDescription(source.getDescription());
                    }
                    if (source.getLabel() != null && source.getLabel().startsWith("#")
                            && cat.get(lang).containsKey(source.getLabel().substring(1))) {
                        localization.setLocalizedLabel(cat.get(lang).get(source.getLabel().substring(1)).toString());
                    } else {
                        localization.setLocalizedLabel(source.getLabel());
                    }
                    if ("en".equals(localization.getIsoCode())) {
                        english = localization;
                    } else {
                        ret.add(localization);
                    }
                }
            }

            if (ret.isEmpty()) {
                Localization localization = new Localization();
                localization.setIsoCode("en");
                localization.setLanguageDescription("English");
                localization.setLocalizedDescription(source.getDescription());
                localization.setLocalizedDescription(source.getLabel());
                ret.add(localization);
            }

            if (english != null) {
                /*
                 * Language discrimination!
                 */
                ret.add(0, english);
            }

        }

        return ret;
    }

    /**
     * Run all test cases in a project. Use dependency order to follow imports.
     * 
     * @param project
     * @return
     */
    public int runAllTests(IProject project) {
        return 0;
    }
}
